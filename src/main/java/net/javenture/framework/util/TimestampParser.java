package net.javenture.framework.util;


import net.javenture.framework.annotation.NullAllow;

import java.sql.Timestamp;


/*
	2019/10/03
 */
final public class TimestampParser
	implements IParser<Timestamp>
{
	//
	final private @NullAllow Timestamp INIT;
	final private boolean NULLABLE;
	final private boolean TRIM;
	final public Report<Timestamp> REPORT_NULL;


	//
	public TimestampParser(@NullAllow Timestamp init, boolean nullable, boolean trim)
	{
		Validator.argument(nullable || init != null, "[init] null value disallowed");

		INIT = TimestampUtil.FACTORY_COPY.copy(init);
		NULLABLE = nullable;
		TRIM = trim;
		REPORT_NULL = new Report<>(true, init != null, null);
	}


	//
	@Override
	public @NullAllow Timestamp init()
	{
		return TimestampUtil.FACTORY_COPY.copy(INIT);
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
	public Report<Timestamp> parse(@NullAllow Timestamp value)
	{
		if (value != null)
		{
			return !TimestampUtil.equal(value, INIT)
				? new Report<>(true, true, value)
				: new Report<>(true, false, init());
		}
		else if (NULLABLE)
		{
			return REPORT_NULL;
		}

		return new Report<>(false, false, init());
	}


	@Override
	public Report<Timestamp> parse(@NullAllow CharSequence source)
	{
		if (source != null)
		{
			if (!NULLABLE || NullUtil.notNull(source, TRIM))
			{
				int length = source.length();

				if (length != 0)
				{
					Timestamp value = TimestampUtil.parse0(source);                                         // XXX: TRIM ?

					if (value != null)
					{
						return !TimestampUtil.equal(value, INIT)
							? new Report<>(true, true, value)
							: new Report<>(true, false, init());
					}
				}
			}
			else
			{
				return REPORT_NULL;
			}
		}

		return new Report<>(false, false, init());
	}


	//
	final public static TimestampParser ZERO = new TimestampParser(new Timestamp(0), false, false);
	final public static TimestampParser NULL = new TimestampParser(null, true, false);

}
