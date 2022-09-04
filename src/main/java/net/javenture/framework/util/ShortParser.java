package net.javenture.framework.util;


import net.javenture.framework.annotation.NullAllow;


/*
	2019/10/03
 */
final public class ShortParser
	implements IParser<Short>
{
	//
	final private @NullAllow Short INIT;
	final private boolean NULLABLE;
	final private boolean TRIM;
	final public Report<Short> REPORT_INIT;
	final public Report<Short> REPORT_ERROR;
	final public Report<Short> REPORT_NULL;


	//
	public ShortParser(@NullAllow Short init, boolean nullable, boolean trim)
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
	public @NullAllow Short init()
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
	public Report<Short> parse(@NullAllow Short value)
	{
		if (value != null)
		{
			return !ShortUtil.equal(value, INIT)
				? new Report<>(true, true, value)
				: REPORT_INIT;
		}
		else if (NULLABLE)
		{
			return REPORT_NULL;
		}

		return REPORT_ERROR;
	}


	@Override
	public Report<Short> parse(@NullAllow CharSequence source)
	{
		if (source != null)
		{
			if (!NULLABLE || NullUtil.notNull(source, TRIM))
			{
				Short value = ShortUtil.primitive(source, TRIM);

				if (value != null)
				{
					return !ShortUtil.equal(value, INIT)
						? new Report<>(true, true, value)
						: REPORT_INIT;
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
	final public static ShortParser ZERO = new ShortParser((short) 0, false, false);
	final public static ShortParser NULL = new ShortParser(null, true, false);

}
