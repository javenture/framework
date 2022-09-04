package net.javenture.framework.model;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.firebird.BooleanDatabaseField;
import net.javenture.framework.json.BooleanJsonValue;
import net.javenture.framework.json.IJsonEntry;
import net.javenture.framework.json.JsonObject;
import net.javenture.framework.json.StringJsonValue;
import net.javenture.framework.netty.NettyParameters;
import net.javenture.framework.script.BooleanScriptValue;
import net.javenture.framework.script.IScriptEntry;
import net.javenture.framework.script.NullScriptValue;
import net.javenture.framework.util.BooleanParser;
import net.javenture.framework.util.BooleanUtil;
import net.javenture.framework.util.IParser;
import net.javenture.framework.util.Validator;


/*
	2019/10/03
 */
final public class BooleanModel
	extends AModel<Boolean>
{
	//
	final private boolean NULLABLE;
	final private boolean QUOTATION;
	final private boolean TRIM;
	final private @NullAllow Boolean INIT;
	final private BooleanParser PARSER;


	//
	public BooleanModel(@NullDisallow BooleanDatabaseField field, boolean quotation, boolean trim)
	{
		super(field);

		Boolean init = field.init();
		boolean nullable = field.nullable();
		Validator.argument(nullable || init != null, "[init] null value disallowed");

		NULLABLE = nullable;
		QUOTATION = quotation;
		TRIM = trim;
		INIT = init;
		PARSER = new BooleanParser(init, nullable, trim);
	}


	public BooleanModel(@NullDisallow String name, boolean nullable, boolean quotation, boolean trim, @NullAllow Boolean init, @NullDisallow String... objects)
	{
		super(name, objects);

		Validator.argument(nullable || init != null, "[init] null value disallowed");

		NULLABLE = nullable;
		QUOTATION = quotation;
		TRIM = trim;
		INIT = init;
		PARSER = new BooleanParser(init, nullable, trim);
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
	public @NullAllow Boolean init()
	{
		return INIT;
	}


	@Override
	public @NullDisallow IScriptEntry toScriptEntry()
	{
		return INIT != null
			? new BooleanScriptValue(INIT, QUOTATION)
			: NullScriptValue.INSTANCE;
	}


	@Override
	public @NullDisallow IScriptEntry toScriptEntry(@NullAllow Boolean value)
	{
		Validator.argument(NULLABLE || value != null, "[value] null value disallowed");

		return value != null
			? new BooleanScriptValue(value, QUOTATION)
			: NullScriptValue.INSTANCE;
	}


	@Override
	public void toNettyParameter(NettyParameters destination)
	{
		destination.add(ALIAS, BooleanUtil.FACTORY_STRING.toString(INIT));
	}


	@Override
	public void toNettyParameter(@NullAllow Boolean value, NettyParameters destination)
	{
		Validator.argument(NULLABLE || value != null, "[value] null value disallowed");
		destination.add(ALIAS, BooleanUtil.FACTORY_STRING.toString(value));
	}


	@Override
	public @NullDisallow IParser.Report<Boolean> parse(@NullAllow CharSequence source)
	{
		return PARSER.parse(source);
	}


	@Override
	public @NullDisallow IParser.Report<Boolean> parse(@NullAllow JsonObject source)
	{
		if (source != null)
		{
			IJsonEntry entry = source.get(NAME);

			if (entry != null)
			{
				IJsonEntry.Type type = entry.type();

				if (type == IJsonEntry.Type.BOOLEAN && !QUOTATION) return PARSER.parse(((BooleanJsonValue) entry).getBoolean());
				else if (type == IJsonEntry.Type.STRING && QUOTATION) return PARSER.parse((StringJsonValue) entry);
				else if (type == IJsonEntry.Type.NULL && NULLABLE) return PARSER.REPORT_NULL;
			}
		}

		return PARSER.REPORT_ERROR;
	}


	@Override
	public boolean defined(@NullAllow Boolean value)
	{
		Validator.argument(NULLABLE || value != null, "[value] null value disallowed");

		return !BooleanUtil.equal(value, INIT);
	}

}
