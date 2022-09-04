package net.javenture.framework.model;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.firebird.ADatabaseField;
import net.javenture.framework.json.IJsonEntry;
import net.javenture.framework.json.JsonObject;
import net.javenture.framework.json.StringJsonValue;
import net.javenture.framework.netty.NettyParameters;
import net.javenture.framework.script.BytesScriptValue;
import net.javenture.framework.script.IScriptEntry;
import net.javenture.framework.script.NullScriptValue;
import net.javenture.framework.util.ByteArrayUtil;
import net.javenture.framework.util.BytesParser;
import net.javenture.framework.util.IParser;
import net.javenture.framework.util.Validator;


/*
	2019/10/03
 */
final public class BytesModel
	extends AModel<byte[]>
{
	//
	final private boolean NULLABLE;
	final private boolean QUOTATION;
	final private boolean TRIM;
	final private @NullAllow byte[] INIT;
	final private BytesParser PARSER;


	//
	public BytesModel(@NullDisallow ADatabaseField<byte[]> field, boolean quotation, boolean trim) // ! ADatabaseField<byte[]>
	{
		super(field);

		byte[] init = field.init();
		boolean nullable = field.nullable();
		Validator.argument(nullable || init != null, "[init] null value disallowed");

		NULLABLE = nullable;
		QUOTATION = quotation;
		TRIM = trim;
		INIT = init;
		PARSER = new BytesParser(init, nullable, trim);
	}


	public BytesModel(@NullDisallow String name, boolean nullable, boolean quotation, boolean trim, @NullAllow byte[] init, @NullDisallow String... objects)
	{
		super(name, objects);

		Validator.argument(nullable || init != null, "[init] null value disallowed");

		NULLABLE = nullable;
		QUOTATION = quotation;
		TRIM = trim;
		INIT = ByteArrayUtil.FACTORY_COPY.copy(init);
		PARSER = new BytesParser(init, nullable, trim);
	}


	//
	@Override
	public boolean nullable()
	{
		return NULLABLE;
	}


	@Override
	public boolean quotation()
	{
		return QUOTATION;
	}


	@Override
	public boolean trim()
	{
		return TRIM;
	}


	@Override
	public @NullAllow byte[] init()
	{
		return ByteArrayUtil.FACTORY_COPY.copy(INIT);
	}


	@Override
	public @NullDisallow IScriptEntry toScriptEntry()
	{
		return INIT != null
			? new BytesScriptValue(INIT, QUOTATION)
			: NullScriptValue.INSTANCE;
	}


	@Override
	public @NullDisallow IScriptEntry toScriptEntry(@NullAllow byte[] value)
	{
		Validator.argument(NULLABLE || value != null, "[value] null value disallowed");

		return value != null
			? new BytesScriptValue(value, QUOTATION)
			: NullScriptValue.INSTANCE;
	}


	@Override
	public void toNettyParameter(NettyParameters destination)
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public void toNettyParameter(@NullAllow byte[] value, NettyParameters destination)
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public IParser.Report<byte[]> parse(@NullAllow CharSequence source)
	{
		return PARSER.parse(source);
	}


	@Override
	public IParser.Report<byte[]> parse(@NullAllow JsonObject source)
	{
		if (source != null)
		{
			IJsonEntry entry = source.get(NAME);

			if (entry != null)
			{
				IJsonEntry.Type type = entry.type();

				if (type == IJsonEntry.Type.STRING && QUOTATION) return PARSER.parse((StringJsonValue) entry);
				else if (type == IJsonEntry.Type.NULL && NULLABLE) return PARSER.REPORT_NULL;
			}
		}

		return new IParser.Report<>(false, false, ByteArrayUtil.FACTORY_COPY.copy(INIT));
	}


	@Override
	public boolean defined(@NullAllow byte[] value)
	{
		Validator.argument(NULLABLE || value != null, "[value] null value disallowed");

		return ByteArrayUtil.equal(value, INIT);
	}

}
