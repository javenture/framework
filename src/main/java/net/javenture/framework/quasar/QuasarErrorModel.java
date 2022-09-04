package net.javenture.framework.quasar;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.json.BooleanJsonValue;
import net.javenture.framework.json.IJsonEntry;
import net.javenture.framework.json.JsonObject;
import net.javenture.framework.netty.NettyParameters;
import net.javenture.framework.script.BooleanScriptValue;
import net.javenture.framework.util.BooleanParser;
import net.javenture.framework.util.BooleanUtil;
import net.javenture.framework.util.IParser;
import net.javenture.framework.util.Validator;
import net.javenture.framework.model.AModel;
import net.javenture.framework.script.IScriptEntry;


/*
	2019/10/03
 */
final public class QuasarErrorModel
	extends AModel<Boolean>
{
	//
	final private static String SUFFIX = "$ERROR";
	final private static boolean NULLABLE = false;
	final private static boolean QUOTATION = false;
	final private static boolean TRIM = false;


	//
	final private boolean INIT;
	final private BooleanParser PARSER;


	//
	public QuasarErrorModel(AModel<?> model)                                                   // XXX: String suffix
	{
		super(model.name() + SUFFIX, model.objects());

		INIT = false;
		PARSER = new BooleanParser(false, NULLABLE, false);
	}


	public QuasarErrorModel(AModel<?> model, boolean init)
	{
		super(model.name() + SUFFIX, model.objects());

		INIT = init;
		PARSER = new BooleanParser(init, NULLABLE, false);
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
	public @NullDisallow Boolean init()
	{
		return INIT;
	}


	@Override
	public @NullDisallow IScriptEntry toScriptEntry()
	{
		return new BooleanScriptValue(INIT, QUOTATION);
	}


	@Override
	public @NullDisallow IScriptEntry toScriptEntry(@NullAllow Boolean value)
	{
		Validator.notNull(value, "[value]");

		return new BooleanScriptValue(value, QUOTATION);
	}


	@Override
	public void toNettyParameter(NettyParameters destination)
	{
		toNettyParameter(INIT, destination);
	}


	@Override
	public void toNettyParameter(@NullDisallow Boolean value, NettyParameters destination)
	{
		toNettyParameter((boolean) value, destination);
	}


	public void toNettyParameter(boolean value, NettyParameters destination)
	{
		destination.add(ALIAS, BooleanUtil.FACTORY_STRING.toString(value));
	}


	@Override
	public IParser.Report<Boolean> parse(CharSequence source)
	{
		return PARSER.parse(source);
	}


	@Override
	public IParser.Report<Boolean> parse(@NullAllow JsonObject source)
	{
		if (source != null)
		{
			IJsonEntry entry = source.get(NAME);

			if (entry != null)
			{
				IJsonEntry.Type type = entry.type();

				if (type == IJsonEntry.Type.BOOLEAN && !QUOTATION) return PARSER.parse(((BooleanJsonValue) entry).getBoolean());
				else if (type == IJsonEntry.Type.NULL && NULLABLE) return PARSER.REPORT_NULL;
			}
		}

		return PARSER.REPORT_ERROR;
	}


	@Override
	public boolean defined(@NullDisallow Boolean value)
	{
		return defined((boolean) value);
	}


	public boolean defined(boolean value)
	{
		return value != INIT;
	}

}
