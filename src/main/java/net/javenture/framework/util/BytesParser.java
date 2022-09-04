package net.javenture.framework.util;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.hex.Hex;


/*
	2019/10/02
 */
final public class BytesParser
	implements IParser<byte[]>
{
	//
	final private @NullAllow byte[] INIT;
	final private boolean NULLABLE;
	final private boolean TRIM;
	final public Report<byte[]> REPORT_NULL;
	final private Hex.Parser PARSER0;


	//
	public BytesParser(@NullAllow byte[] init, boolean nullable, boolean trim)
	{
		Validator.argument(nullable || init != null, "[init] null value disallowed");

		INIT = ByteArrayUtil.FACTORY_COPY.copy(init);
		NULLABLE = nullable;
		TRIM = trim;
		REPORT_NULL = new Report<>(true, init != null, null);
		PARSER0 = new Hex.Parser(init != null ? new Hex(init) : null, nullable, trim);
	}


	@Override
	public byte[] init()
	{
		return ByteArrayUtil.FACTORY_COPY.copy(INIT);
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
	public Report<byte[]> parse(@NullAllow byte[] value)
	{
		if (value != null)
		{
			return !ByteArrayUtil.equal(value, INIT)
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
	public Report<byte[]> parse(@NullAllow CharSequence source)
	{
		Report<Hex> report = PARSER0.parse(source);

		if (report.EXISTS)
		{
			if (report.DEFINED)
			{
				if (report.VALUE != null) return new Report<>(true, true, report.VALUE.value());                     // XXX: report.VALUE.value() = copy ?
				else return REPORT_NULL;
			}
			else
			{
				return new Report<>(true, false, init());
			}
		}

		return new Report<>(false, false, init());
	}

}
