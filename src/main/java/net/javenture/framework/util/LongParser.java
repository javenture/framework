package net.javenture.framework.util;


import net.javenture.framework.annotation.NullAllow;


/*
	2019/10/03
 */
final public class LongParser
	implements IParser<Long>
{
	//
	final private @NullAllow Long INIT;
	final private boolean NULLABLE;
	final private boolean TRIM;
	final public Report<Long> REPORT_INIT;
	final public Report<Long> REPORT_ERROR;
	final public Report<Long> REPORT_NULL;


	//
	public LongParser(@NullAllow Long init, boolean nullable, boolean trim)
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
	public @NullAllow Long init()
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
	public Report<Long> parse(@NullAllow Long value)
	{
		if (value != null)
		{
			return !LongUtil.equal(value, INIT)
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
	public Report<Long> parse(@NullAllow CharSequence source)
	{
		if (source != null)
		{
			if (!NULLABLE || NullUtil.notNull(source, TRIM))
			{
				Long value = LongUtil.primitive(source, TRIM);

				if (value != null)
				{
					return !LongUtil.equal(value, INIT)
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
	final public static LongParser ZERO = new LongParser((long) 0, false, false);
	final public static LongParser MINUS1 = new LongParser((long) -1, false, false);
	final public static LongParser NULL = new LongParser(null, true, false);

}
