package net.javenture.framework.util;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;


/*
	2019/10/04
 */
final public class StringParser
	implements IParser<String>
{
	//
	final private @NullAllow String INIT;
	final private boolean NULLABLE;
	final private boolean TRIM;
	final public Report<String> REPORT_INIT;
	final public Report<String> REPORT_ERROR;
	final public Report<String> REPORT_NULL;


	//
	public StringParser(@NullAllow String init, boolean nullable, boolean trim)
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
	public @NullAllow String init()
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
	public Report<String> parse(@NullAllow String value)
	{
		if (value != null)
		{
			String s = TRIM ? StringTrimmer.toString(value) : value;

			return !StringUtil.equal(s, INIT)
				? new Report<>(true, true, s)
				: REPORT_INIT;
		}
		else if (NULLABLE)
		{
			return REPORT_NULL;
		}

		return REPORT_ERROR;
	}


	@Override
	public @NullDisallow Report<String> parse(@NullAllow CharSequence source)
	{
		if (source != null)
		{
			String s = TRIM ? StringTrimmer.toString(source) : source.toString();

			return !StringUtil.equal(s, INIT)
				? new Report<>(true, true, s)
				: REPORT_INIT;
		}

		return REPORT_ERROR;
	}


	//
	final public static StringParser BLANK = new StringParser("", false, false);
	final public static StringParser NULL = new StringParser(null, true, false);

}
