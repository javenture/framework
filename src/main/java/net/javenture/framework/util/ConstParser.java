package net.javenture.framework.util;


import net.javenture.framework.annotation.NullAllow;


/*
	2019/10/03
 */
final public class ConstParser<C extends IConst> // ! <C extends IConst>
	implements IParser<C>
{
	//
	final private C[] VALUES;
	final private @NullAllow C INIT;
	final private boolean NULLABLE;
	final private boolean TRIM;
	final private Report<C>[] REPORT_VALUES;
	final public Report<C> REPORT_INIT;
	final public Report<C> REPORT_ERROR;
	final public Report<C> REPORT_NULL;


	//
	@SuppressWarnings("unchecked")
	public ConstParser(C[] values, @NullAllow C init, boolean nullable, boolean trim)
	{
		Validator.argument(nullable || init != null, "[init] null value disallowed");

		VALUES = values.clone();
		INIT = init;
		NULLABLE = nullable;
		TRIM = trim;
		REPORT_INIT = new Report<>(true, false, init);
		REPORT_ERROR = new Report<>(false, false, init);
		REPORT_NULL = new Report<>(true, init != null, null);
		REPORT_VALUES = new Report[values.length];

		for (C c : values) REPORT_VALUES[c.ordinal()] = new Report<>(true, true, c);
	}


	//
	@Override
	public @NullAllow C init()
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
	public Report<C> parse(@NullAllow C value)
	{
		if (value != null) return value != INIT ? REPORT_VALUES[value.ordinal()] : REPORT_INIT;
		else if (NULLABLE) return REPORT_NULL;

		return REPORT_ERROR;
	}


	@Override
	public Report<C> parse(@NullAllow CharSequence source)
	{
		if (source != null)
		{
			if (!NULLABLE || NullUtil.notNull(source, TRIM))
			{
				Integer value = IntUtil.primitive(source, TRIM);

				if (value != null)
				{
					for (C c : VALUES)
					{
						if (c.id() == value) return c != INIT ? REPORT_VALUES[c.ordinal()] : REPORT_INIT;
					}
				}
			}
			else
			{
				return REPORT_NULL;
			}
		}

		return REPORT_ERROR;
	}

}
