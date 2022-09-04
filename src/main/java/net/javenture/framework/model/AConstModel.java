package net.javenture.framework.model;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.firebird.AConstDatabaseField;
import net.javenture.framework.json.IJsonEntry;
import net.javenture.framework.json.JsonObject;
import net.javenture.framework.json.NumberJsonValue;
import net.javenture.framework.json.StringJsonValue;
import net.javenture.framework.netty.NettyParameters;
import net.javenture.framework.script.IScriptEntry;
import net.javenture.framework.script.NullScriptValue;
import net.javenture.framework.script.StringScriptValue;
import net.javenture.framework.util.ConstParser;
import net.javenture.framework.util.IConst;
import net.javenture.framework.util.IParser;
import net.javenture.framework.util.Validator;


/*
	2019/10/03
 */
abstract public class AConstModel<C extends IConst> // ! abstract, <C extends IConst>
	extends AModel<C>
{
	//
	final private boolean NULLABLE;
	final private boolean QUOTATION;
	final private boolean TRIM;
	final private @NullAllow C INIT;
	final private @NullDisallow Factories<C> FACTORIES;
	final private ConstParser<C> PARSER;


	//
	protected AConstModel(@NullDisallow AConstDatabaseField<C> field, boolean quotation, boolean trim)
	{
		super(field);

		C init = field.init();
		boolean nullable = field.nullable();
		Validator.argument(nullable || init != null, "[init] null value disallowed");
		Factories<C> factories = field.factories();
		Validator.notNull(factories, "[factories]");

		NULLABLE = nullable;
		QUOTATION = quotation;
		TRIM = trim;
		INIT = init;
		FACTORIES = factories;

		PARSER = (ConstParser<C>) factories.getStringFactory()
			.parser(init, nullable, trim);
	}


	protected AConstModel(@NullDisallow String name, boolean nullable, boolean quotation, boolean trim, @NullAllow C init, @NullDisallow Factories<C> factories, @NullDisallow String... objects)
	{
		super(name, objects);

		Validator.argument(nullable || init != null, "[init] null value disallowed");
		Validator.notNull(factories, "[factories]");

		NULLABLE = nullable;
		QUOTATION = quotation;
		TRIM = trim;
		INIT = init;
		FACTORIES = factories;

		PARSER = (ConstParser<C>) factories.getStringFactory()
			.parser(init, nullable, trim);
	}


	//
	@Override
	final public boolean nullable()
	{
		return NULLABLE;
	}


	@Override
	final public boolean quotation()
	{
		return QUOTATION;
	}


	@Override
	final public boolean trim()
	{
		return TRIM;
	}


	@Override
	final public @NullAllow C init()
	{
		return INIT;
	}


	@Override
	public @NullDisallow IScriptEntry toScriptEntry()
	{
		if (INIT != null)
		{
			String s = FACTORIES.getStringFactory()
				.toString(INIT);

			return new StringScriptValue(s, QUOTATION);
		}
		else
		{
			return NullScriptValue.INSTANCE;
		}
	}


	@Override
	public @NullDisallow IScriptEntry toScriptEntry(@NullAllow C value)
	{
		Validator.argument(NULLABLE || value != null, "[value] null value disallowed");

		if (value != null)
		{
			String s = FACTORIES.getStringFactory()
				.toString(value);

			return new StringScriptValue(s, QUOTATION);
		}
		else
		{
			return NullScriptValue.INSTANCE;
		}
	}


	@Override
	final public void toNettyParameter(NettyParameters destination)
	{
		String s = FACTORIES.getStringFactory()
			.toString(INIT);

		destination.add(ALIAS, s);
	}


	@Override
	final public void toNettyParameter(@NullAllow C value, NettyParameters destination)
	{
		Validator.argument(NULLABLE || value != null, "[value] null value disallowed");

		String s = FACTORIES.getStringFactory()
			.toString(value);

		destination.add(ALIAS, s);
	}


	@Override
	public @NullDisallow IParser.Report<C> parse(@NullAllow CharSequence source)
	{
		return PARSER.parse(source);
	}


	@Override
	public @NullDisallow IParser.Report<C> parse(@NullAllow JsonObject source)
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
	final public boolean defined(@NullAllow C value)
	{
		Validator.argument(NULLABLE || value != null, "[value] null value disallowed");

		return value != INIT;
	}

}
