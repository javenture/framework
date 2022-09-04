package net.javenture.framework.util;


import net.javenture.framework.annotation.NullAllow;


/*
	2019/10/03
 */
final public class IntParser
	implements IParser<Integer>
{
	//
	final private @NullAllow Integer INIT;
	final private boolean NULLABLE;
	final private boolean TRIM;
	final public Report<Integer> REPORT_INIT;
	final public Report<Integer> REPORT_ERROR;
	final public Report<Integer> REPORT_NULL;


	//
	public IntParser(@NullAllow Integer init, boolean nullable, boolean trim)
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
	public @NullAllow Integer init()
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
	public Report<Integer> parse(@NullAllow Integer value)
	{
		if (value != null)
		{
			return !IntUtil.equal(value, INIT)
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
	public Report<Integer> parse(@NullAllow CharSequence source)
	{
		if (source != null)
		{
			if (!NULLABLE || NullUtil.notNull(source, TRIM))
			{
				Integer value = IntUtil.primitive(source, TRIM);

				if (value != null)
				{
					return !IntUtil.equal(value, INIT)
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
	final public static IntParser ZERO = new IntParser(0, false, false);
	final public static IntParser MINUS1 = new IntParser(-1, false, false);
	final public static IntParser NULL = new IntParser(null, true, false);

}
