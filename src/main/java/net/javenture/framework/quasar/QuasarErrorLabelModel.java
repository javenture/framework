package net.javenture.framework.quasar;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.json.IJsonEntry;
import net.javenture.framework.json.JsonObject;
import net.javenture.framework.json.StringJsonValue;
import net.javenture.framework.netty.NettyParameters;
import net.javenture.framework.script.NullScriptValue;
import net.javenture.framework.script.StringScriptValue;
import net.javenture.framework.util.IParser;
import net.javenture.framework.util.StringParser;
import net.javenture.framework.util.Validator;
import net.javenture.framework.model.AModel;
import net.javenture.framework.script.IScriptEntry;


/*
	2019/10/04
 */
final public class QuasarErrorLabelModel                                        // XXX: ?
	extends AModel<String>
{
	//
	final private static String SUFFIX = "$ERROR_LABEL";
	final private static boolean NULLABLE = false;
	final private static boolean QUOTATION = true;
	final private static boolean TRIM = false;


	//
	final private @NullDisallow String INIT;
	final private StringParser PARSER;


	//
	public QuasarErrorLabelModel(@NullDisallow AModel<?> model)                                 // XXX: AVueModel<Object> ?         // XXX: String suffix
	{
		super(model.name() + SUFFIX, model.objects());

		INIT = "";
		PARSER = new StringParser("", NULLABLE, TRIM);
	}


	public QuasarErrorLabelModel(@NullDisallow AModel<?> model, @NullDisallow String init)
	{
		super(model.name() + SUFFIX, model.objects());

		Validator.notNull(init, "[init]");
		INIT = init;
		PARSER = new StringParser(init, NULLABLE, TRIM);
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
	public @NullDisallow String init()
	{
		return INIT;
	}


	@Override
	public @NullDisallow IScriptEntry toScriptEntry()
	{
		return INIT != null
			? new StringScriptValue(INIT, QUOTATION)
			: NullScriptValue.INSTANCE;
	}


	@Override
	public @NullDisallow IScriptEntry toScriptEntry(@NullAllow String value)
	{
		Validator.argument(NULLABLE || value != null, "[value] null value disallowed");

		return value != null
			? new StringScriptValue(value, QUOTATION)
			: NullScriptValue.INSTANCE;
	}


	@Override
	public void toNettyParameter(NettyParameters destination)
	{
		destination.add(ALIAS, INIT);
	}


	@Override
	public void toNettyParameter(@NullDisallow String value, NettyParameters destination)
	{
		Validator.notNull(value, "[value]");
		destination.add(ALIAS, value);
	}


	@Override
	public IParser.Report<String> parse(CharSequence source)
	{
		return PARSER.parse(source);
	}

	@Override
	public IParser.Report<String> parse(@NullAllow JsonObject source)
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

		return PARSER.REPORT_ERROR;
	}


	@Override
	public boolean defined(@NullDisallow String value)
	{
		Validator.notNull(value, "[value]");

		return !value.equals(INIT);
	}

}
