package net.javenture.framework.util;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;


/*
	2019/10/04
 */
final public class EnumParser<E extends Enum> // ! <E extends Enum>
	implements IParser<E>
{
	//
	final private E[] VALUES;
	final private @NullDisallow IEnumAlias<E> ALIAS;
	final private @NullAllow E INIT;
	final private boolean NULLABLE;
	final private boolean TRIM;
	final private Report<E>[] REPORT_VALUES;
	final public Report<E> REPORT_INIT;
	final public Report<E> REPORT_ERROR;
	final public Report<E> REPORT_NULL;


	//
	@SuppressWarnings("unchecked")
	public EnumParser(E[] values, @NullDisallow IEnumAlias<E> alias, @NullAllow E init, boolean nullable, boolean trim)
	{
		Validator.argument(nullable || init != null, "[init] null value disallowed");

		VALUES = values.clone();
		ALIAS = alias;
		INIT = init;
		NULLABLE = nullable;
		TRIM = trim;
		REPORT_INIT = new Report<>(true, false, init);
		REPORT_ERROR = new Report<>(false, false, init);
		REPORT_NULL = new Report<>(true, init != null, null);
		REPORT_VALUES = new Report[values.length];

		for (E e : values) REPORT_VALUES[e.ordinal()] = new Report<>(true, true, e);
	}


	//
	@Override
	public @NullAllow E init()
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
	public Report<E> parse(@NullAllow E value)
	{
		if (value != null) return value != INIT ? REPORT_VALUES[value.ordinal()] : REPORT_INIT;
		else if (NULLABLE) return REPORT_NULL;

		return REPORT_ERROR;
	}


	@Override
	public Report<E> parse(@NullAllow CharSequence source)
	{
		if (source != null)
		{
			CharSequence sequence;

			if (TRIM)
			{
				StringTrimmer trimmer = new StringTrimmer(source);
				sequence = trimmer.CHANGE ? trimmer.toString() : source;
			}
			else
			{
				sequence = source;
			}

			//
			int length = sequence.length();

			if (length != 0)
			{
				for (E value : VALUES)
				{
					String s = ALIAS.get(value);

					if (StringUtil.equal(s, sequence)) return value != INIT ? REPORT_VALUES[value.ordinal()] : REPORT_INIT;
				}
			}
		}

		return REPORT_ERROR;
	}

}
