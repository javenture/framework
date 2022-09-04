package net.javenture.framework.model;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.firebird.IntDatabaseField;
import net.javenture.framework.json.IJsonEntry;
import net.javenture.framework.json.JsonObject;
import net.javenture.framework.json.NumberJsonValue;
import net.javenture.framework.json.StringJsonValue;
import net.javenture.framework.netty.NettyParameters;
import net.javenture.framework.script.IScriptEntry;
import net.javenture.framework.script.IntScriptValue;
import net.javenture.framework.script.NullScriptValue;
import net.javenture.framework.util.IParser;
import net.javenture.framework.util.IntParser;
import net.javenture.framework.util.IntUtil;
import net.javenture.framework.util.NullUtil;
import net.javenture.framework.util.Validator;


/*
	2019/10/03
 */
final public class IntModel
	extends AModel<Integer>
{
	//
	final private boolean NULLABLE;
	final private boolean QUOTATION;
	final private boolean TRIM;
	final private @NullAllow Integer INIT;
	final private IntParser PARSER;


	//
	public IntModel(@NullDisallow IntDatabaseField field, boolean quotation, boolean trim)
	{
		super(field);

		Integer init = field.init();
		boolean nullable = field.nullable();
		Validator.argument(nullable || init != null, "[init] null value disallowed");

		NULLABLE = nullable;
		QUOTATION = quotation;
		TRIM = trim;
		INIT = init;
		PARSER = new IntParser(init, nullable, trim);
	}


	public IntModel(@NullDisallow String name, boolean nullable, boolean quotation, boolean trim, @NullAllow Integer init, @NullDisallow String... objects)
	{
		super(name, objects);

		Validator.argument(nullable || init != null, "[init] null value disallowed");

		NULLABLE = nullable;
		QUOTATION = quotation;
		TRIM = trim;
		INIT = init;
		PARSER = new IntParser(init, nullable, trim);
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
	public @NullAllow Integer init()
	{
		return INIT;
	}


	@Override
	public @NullDisallow IScriptEntry toScriptEntry()
	{
		return INIT != null
			? new IntScriptValue(INIT, QUOTATION)
			: NullScriptValue.INSTANCE;
	}


	@Override
	public @NullDisallow IScriptEntry toScriptEntry(@NullAllow Integer value)
	{
		Validator.argument(NULLABLE || value != null, "[value] null value disallowed");

		return value != null
			? new IntScriptValue(value, QUOTATION)
			: NullScriptValue.INSTANCE;
	}


	@Override
	public void toNettyParameter(NettyParameters destination)
	{
		destination.add(ALIAS, INIT != null ? "" + INIT : NullUtil.STRING);
	}


	@Override
	public void toNettyParameter(@NullAllow Integer value, NettyParameters destination)
	{
		Validator.argument(NULLABLE || value != null, "[value] null value disallowed");
		destination.add(ALIAS, value != null ? "" + value : NullUtil.STRING);
	}


	@Override
	public @NullDisallow IParser.Report<Integer> parse(@NullAllow CharSequence source)
	{
		return PARSER.parse(source);
	}


	@Override
	public @NullDisallow IParser.Report<Integer> parse(@NullAllow JsonObject source)
	{
		if (source != null)
		{
			IJsonEntry entry = source.get(NAME);

			if (entry != null)
			{
				IJsonEntry.Type type = entry.type();

				if (type == IJsonEntry.Type.NUMBER && !QUOTATION) return PARSER.parse((NumberJsonValue) entry);
				else if (type == IJsonEntry.Type.STRING && QUOTATION) return PARSER.parse((StringJsonValue) entry);
				else if (type == IJsonEntry.Type.NULL && NULLABLE) return PARSER.REPORT_NULL;
			}
		}

		return PARSER.REPORT_ERROR;
	}


	@Override
	public boolean defined(@NullAllow Integer value)
	{
		Validator.argument(NULLABLE || value != null, "[value] null value disallowed");

		return !IntUtil.equal(value, INIT);
	}

}
