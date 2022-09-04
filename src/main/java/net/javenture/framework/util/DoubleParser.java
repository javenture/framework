package net.javenture.framework.util;


import net.javenture.framework.annotation.NullAllow;


/*
	2019/10/03
 */
final public class DoubleParser
	implements IParser<Double>
{
	//
	final private @NullAllow Double INIT;
	final private boolean NULLABLE;
	final private boolean TRIM;
	final public Report<Double> REPORT_INIT;
	final public Report<Double> REPORT_ERROR;
	final public Report<Double> REPORT_NULL;


	//
	public DoubleParser(@NullAllow Double init, boolean nullable, boolean trim)
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
	public @NullAllow Double init()
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
	public Report<Double> parse(@NullAllow Double value)
	{
		if (value != null)
		{
			return !DoubleUtil.equal(value, INIT)
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
	public Report<Double> parse(@NullAllow CharSequence source)
	{
		if (source != null)
		{
			if (!NULLABLE || NullUtil.notNull(source, TRIM))
			{
				Double value = DoubleUtil.primitive(source, TRIM);

				if (value != null)
				{
					return !DoubleUtil.equal(value, INIT)
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
	final public static DoubleParser ZERO = new DoubleParser((double) 0, false, false);
	final public static DoubleParser NULL = new DoubleParser(null, true, false);

}
