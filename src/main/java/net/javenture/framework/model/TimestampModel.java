package net.javenture.framework.model;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.firebird.TimestampDatabaseField;
import net.javenture.framework.json.IJsonEntry;
import net.javenture.framework.json.JsonObject;
import net.javenture.framework.json.StringJsonValue;
import net.javenture.framework.netty.NettyParameters;
import net.javenture.framework.script.IScriptEntry;
import net.javenture.framework.script.NullScriptValue;
import net.javenture.framework.script.TimestampScriptValue;
import net.javenture.framework.util.IParser;
import net.javenture.framework.util.NullUtil;
import net.javenture.framework.util.TimestampParser;
import net.javenture.framework.util.TimestampUtil;
import net.javenture.framework.util.Validator;

import java.sql.Timestamp;


/*
	2019/10/04
 */
final public class TimestampModel
	extends AModel<Timestamp>
{
	//
	final private boolean NULLABLE;
	final private boolean QUOTATION;
	final private boolean TRIM;
	final private @NullAllow Timestamp INIT;
	final private TimestampParser PARSER;


	//
	public TimestampModel(@NullDisallow TimestampDatabaseField field, boolean quotation, boolean trim)
	{
		super(field);

		Timestamp init = field.init();
		boolean nullable = field.nullable();
		Validator.argument(nullable || init != null, "[init] null value disallowed");

		NULLABLE = nullable;
		QUOTATION = quotation;
		TRIM = trim;
		INIT = init;
		PARSER = new TimestampParser(init, nullable, trim);
	}


	public TimestampModel(@NullDisallow String name, boolean nullable, boolean quotation, boolean trim, @NullAllow Timestamp init, @NullDisallow String... objects)
	{
		super(name, objects);

		Validator.argument(nullable || init != null, "[init] null value disallowed");

		NULLABLE = nullable;
		QUOTATION = quotation;
		TRIM = trim;
		INIT = TimestampUtil.FACTORY_COPY.copy(init);
		PARSER = new TimestampParser(init, nullable, trim);
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
	public @NullAllow Timestamp init()
	{
		return TimestampUtil.FACTORY_COPY.copy(INIT);
	}


	@Override
	public @NullDisallow IScriptEntry toScriptEntry()
	{
		return INIT != null
			? new TimestampScriptValue(INIT, QUOTATION)
			: NullScriptValue.INSTANCE;
	}


	@Override
	public @NullDisallow IScriptEntry toScriptEntry(@NullAllow Timestamp value)
	{
		Validator.argument(NULLABLE || value != null, "[value] null value disallowed");

		return value != null
			? new TimestampScriptValue(value, QUOTATION)
			: NullScriptValue.INSTANCE;
	}


	@Override
	public void toNettyParameter(NettyParameters destination)
	{
		destination.add(ALIAS, INIT != null ? INIT.toString() : NullUtil.STRING);
	}


	@Override
	public void toNettyParameter(@NullAllow Timestamp value, NettyParameters destination)
	{
		Validator.argument(NULLABLE || value != null, "[value] null value disallowed");
		destination.add(ALIAS, value != null ? value.toString() : NullUtil.STRING);
	}


	@Override
	public @NullDisallow IParser.Report<Timestamp> parse(@NullAllow CharSequence source)
	{
		return PARSER.parse(source);
	}


	@Override
	public @NullDisallow IParser.Report<Timestamp> parse(@NullAllow JsonObject source)
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

		return new IParser.Report<>(false, false, TimestampUtil.FACTORY_COPY.copy(INIT));
	}


	@Override
	public boolean defined(@NullAllow Timestamp value)
	{
		Validator.argument(NULLABLE || value != null, "[value] null value disallowed");

		return !TimestampUtil.equal(value, INIT);
	}

}
