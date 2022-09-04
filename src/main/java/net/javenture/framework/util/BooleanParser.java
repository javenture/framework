package net.javenture.framework.util;


import net.javenture.framework.annotation.NullAllow;


/*
	2019/10/03
 */
final public class BooleanParser
	implements IParser<Boolean>
{
	//
	final public static Report<Boolean> REPORT_TRUE = new Report<>(true, true, true);
	final public static Report<Boolean> REPORT_FALSE = new Report<>(true, true, false);


	//
	final private @NullAllow Boolean INIT;
	final private boolean NULLABLE;
	final private boolean TRIM;
	final public Report<Boolean> REPORT_INIT;
	final public Report<Boolean> REPORT_ERROR;
	final public Report<Boolean> REPORT_NULL;


	//
	public BooleanParser(@NullAllow Boolean init, boolean nullable, boolean trim)
	{
		Validator.argument(nullable || init != null, "[init] null value disallowed");

		INIT = init;
		NULLABLE = nullable;
		TRIM = trim;
		REPORT_INIT = new Report<>(true, false, init);
		REPORT_ERROR = new Report<>(false, false, init);
		REPORT_NULL = new Report<>(true, init != null, null);
	}


	//
	@Override
	public @NullAllow Boolean init()
	{
		return INIT;
	}


	@Override
	public boolean nullable()
	{
		return NULLABLE;
	}


	@Override
	public boolean trim()
	{
		return TRIM;
	}


	@Override
	public Report<Boolean> parse(@NullAllow Boolean value)
	{
		if (value != null)
		{
			if (!BooleanUtil.equal(value, INIT)) return value ? REPORT_TRUE : REPORT_FALSE;
			else return REPORT_INIT;
		}
		else if (NULLABLE)
		{
			return REPORT_NULL;
		}

		return REPORT_ERROR;
	}


	@Override
	public Report<Boolean> parse(@NullAllow CharSequence source)
	{
		if (source != null)
		{
			if (!NULLABLE || NullUtil.notNull(source, TRIM))
			{
				Boolean value = BooleanUtil.primitive(source, TRIM);

				if (value != null)
				{
					if (!BooleanUtil.equal(value, INIT)) return value ? REPORT_TRUE : REPORT_FALSE;
					else return REPORT_INIT;
				}
			}
			else
			{
				return REPORT_NULL;
			}
		}

		return REPORT_ERROR;
	}


	//
	final public static BooleanParser FALSE = new BooleanParser(false, false, false);
	final public static BooleanParser NULL = new BooleanParser(null, true, false);

}
