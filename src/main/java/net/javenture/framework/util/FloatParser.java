package net.javenture.framework.util;


import net.javenture.framework.annotation.NullAllow;


/*
	2019/10/03
 */
final public class FloatParser
	implements IParser<Float>
{
	//
	final private @NullAllow Float INIT;
	final private boolean NULLABLE;
	final private boolean TRIM;
	final public Report<Float> REPORT_INIT;
	final public Report<Float> REPORT_ERROR;
	final public Report<Float> REPORT_NULL;


	//
	public FloatParser(@NullAllow Float init, boolean nullable, boolean trim)
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
	public @NullAllow Float init()
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
	public Report<Float> parse(@NullAllow Float value)
	{
		if (value != null)
		{
			return !FloatUtil.equal(value, INIT)
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
	public Report<Float> parse(@NullAllow CharSequence source)
	{
		if (source != null)
		{
			if (!NULLABLE || NullUtil.notNull(source, TRIM))
			{
				Float value = FloatUtil.primitive(source, TRIM);

				if (value != null)
				{
					return !FloatUtil.equal(value, INIT)
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
	final public static FloatParser ZERO = new FloatParser((float) 0, false, false);
	final public static FloatParser NULL = new FloatParser(null, true, false);

}
