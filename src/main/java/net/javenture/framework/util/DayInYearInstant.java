package net.javenture.framework.util;


import net.javenture.framework.annotation.Immutable;
import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.factory.FactoryObjectUtil;
import net.javenture.framework.factory.IByteFactory;
import net.javenture.framework.factory.ICopyFactory;
import net.javenture.framework.factory.IDatabaseFactory;
import net.javenture.framework.factory.IFactoryObject;
import net.javenture.framework.factory.IInstanceFactory;
import net.javenture.framework.factory.IStringFactory;
import net.javenture.framework.firebird.DatabaseType;
import net.javenture.framework.firebird.AFactoryObjectDatabaseField;
import net.javenture.framework.json.IJsonEntry;
import net.javenture.framework.json.JsonObject;
import net.javenture.framework.json.NumberJsonValue;
import net.javenture.framework.json.StringJsonValue;
import net.javenture.framework.script.IScriptEntry;
import net.javenture.framework.script.IntScriptValue;
import net.javenture.framework.script.NullScriptValue;
import net.javenture.framework.model.AFactoryObjectModel;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


/*
	YYYYMMDD

	2020/01/05
*/
@Immutable
final public class DayInYearInstant
	implements IFactoryObject<DayInYearInstant>
{
	//
	final private static int POWER_YEAR = 10000;
	final private static int POWER_MONTH = 100;
	final public static IInstanceFactory<DayInYearInstant> FACTORY_INSTANCE = object -> object instanceof DayInYearInstant;
	final public static ICopyFactory<DayInYearInstant> FACTORY_COPY = object -> object;

	final public static IByteFactory<DayInYearInstant> FACTORY_BYTE = new IByteFactory<>()
	{
		@Override
		public void toStream(@NullAllow DayInYearInstant object, OutputStream destination)
			throws Exception
		{
			if (IByteFactory.setNullHeader(object, destination)) IntUtil.bytes(object.toInt(), destination);
		}

		@Override
		public @NullAllow DayInYearInstant fromStream(InputStream source)
			throws Exception
		{
			return IByteFactory.getNullHeader(source)
				? new DayInYearInstant(IntUtil.parse(source))
				: null;
		}
	};

	final public static IStringFactory<DayInYearInstant> FACTORY_STRING = new IStringFactory<>()
	{
		@Override
		public @NullDisallow String toString(@NullAllow DayInYearInstant object)
		{
			return StringUtil.toString(object);
		}

		@Override
		public @NullDisallow Parser parser(@NullAllow DayInYearInstant init, boolean nullable, boolean trim)
		{
			return new Parser(init, nullable, trim);
		}
	};

	final public static IDatabaseFactory<DayInYearInstant, Integer> FACTORY_DATABASE = new IDatabaseFactory<>()
	{
		@Override
		public DatabaseType getDatabaseType()
		{
			return DatabaseType.INT;
		}

		@Override
		public @NullAllow Integer toDatabase(@NullAllow DayInYearInstant object)
		{
			return object != null ? object.toInt() : null;
		}

		@Override
		public @NullAllow DayInYearInstant fromDatabase(@NullAllow Integer object)
		{
			return object != null ? new DayInYearInstant(object) : null;
		}
	};

	final public static Factories<DayInYearInstant> FACTORIES = new Factories<>(FACTORY_INSTANCE, FACTORY_COPY, FACTORY_BYTE, FACTORY_STRING, FACTORY_DATABASE);


	//
	final private LocalDate DATE;


	//
	public DayInYearInstant(ZoneId zone)
	{
		DATE = LocalDate.now(zone);
	}


	public DayInYearInstant(LocalDate date)
	{
		DATE = date;
	}


	public DayInYearInstant(int year, int month, int day)
	{
		DATE = LocalDate.of(year, month, day);
	}


	public DayInYearInstant(int year, int month, int day, boolean correction)
	{
		if (correction)
		{
			int year0 = DateTimeUtil.validateYear(year);
			int month0 = DateTimeUtil.validateMonthOfYear(month);
			int day0 = DateTimeUtil.validateDayOfMonth(day, month0, year0);
			DATE = LocalDate.of(year0, month0, day0);
		}
		else
		{
			DATE = LocalDate.of(year, month, day);
		}
	}


	private DayInYearInstant(int value)
	{
		Validator.argument(value >= 0, "[value] (", value ,") has negative value");

		int year = value / POWER_YEAR;
		int month = (value - year * POWER_YEAR) / POWER_MONTH;
		int day = value - year * POWER_YEAR - month * POWER_MONTH;
		DATE = LocalDate.of(year, month, day);
	}


	//
	@Override
	public Factories<DayInYearInstant> factories()
	{
		return FACTORIES;
	}


	@Override
	public int hashCode()
	{
		return toInt();
	}


	@Override
	@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
	public boolean equals(Object object)
	{
		return equals((DayInYearInstant) object);
	}


	@Override
	public boolean equals(DayInYearInstant value)
	{
		return this.DATE.equals(value.DATE);
	}


	@Override
	public String toString()
	{
		return "" + toInt();
	}


	public int year()
	{
		return DATE.getYear();
	}


	public int month()
	{
		return DATE.getMonthValue();
	}


	public int day()
	{
		return DATE.getDayOfMonth();
	}


	public boolean before(DayInYearInstant value)
	{
		return this.DATE.isBefore(value.DATE);
	}


	public boolean after(DayInYearInstant value)
	{
		return this.DATE.isAfter(value.DATE);
	}


	public DayInYearInstant previousYear()
	{
		return previousYear(1);
	}


	public DayInYearInstant previousYear(int value)
	{
		return value != 0
			? new DayInYearInstant(DATE.minusYears(value))
			: this;
	}


	public DayInYearInstant nextYear()
	{
		return nextYear(1);
	}


	public DayInYearInstant nextYear(int value)
	{
		return value != 0
			? new DayInYearInstant(DATE.plusYears(value))
			: this;
	}


	public DayInYearInstant previousMonth()
	{
		return previousMonth(1);
	}


	public DayInYearInstant previousMonth(int value)
	{
		return value != 0
			? new DayInYearInstant(DATE.minusMonths(value))
			: this;
	}


	public DayInYearInstant nextMonth()
	{
		return nextMonth(1);
	}


	public DayInYearInstant nextMonth(int value)
	{
		return value != 0
			? new DayInYearInstant(DATE.plusMonths(value))
			: this;
	}


	public DayInYearInstant previousDay()
	{
		return previousDay(1);
	}


	public DayInYearInstant previousDay(int value)
	{
		return value != 0 ? new DayInYearInstant(DATE.minusDays(value)) : this;
	}


	public DayInYearInstant nextDay()
	{
		return nextDay(1);
	}


	public DayInYearInstant nextDay(int value)
	{
		return value != 0
			? new DayInYearInstant(DATE.plusDays(value))
			: this;
	}


	public boolean matchYear(DayInYearInstant instant)
	{
		return this.year() == instant.year();
	}


	public boolean matchMonth(DayInYearInstant instant)
	{
		return this.month() == instant.month();
	}


	public boolean matchDay(DayInYearInstant instant)
	{
		return this.day() == instant.day();
	}


	public String format(DateTimeFormatter formatter)
	{
		return DATE.format(formatter);
	}


	public String format(String pattern)
	{
		return format(DateTimeFormatter.ofPattern(pattern));
	}


	public LocalDate toLocalDate()
	{
		return DATE;
	}


	public Millisecond toMillisecond(ZoneId zone)
	{
		return Millisecond.fromZonedDateTime(toZonedDateTime(zone));
	}


	public Millisecond toMillisecond(int hour, int minute, int second, int nano, ZoneId zone)
	{
		return Millisecond.fromZonedDateTime(toZonedDateTime(hour, minute, second, nano, zone));
	}


	public ZonedDateTime toZonedDateTime(ZoneId zone)
	{
		return DATE.atStartOfDay(zone);
	}


	public ZonedDateTime toZonedDateTime(int hour, int minute, int second, int nano, ZoneId zone)
	{
		return DATE.atTime(hour, minute, second, nano)
			.atZone(zone);
	}


	public Timestamp toTimestamp(ZoneId zone)
	{
		return toMillisecond(zone).toTimestamp();
	}


	public Timestamp toTimestamp(int hour, int minute, int second, int nano, ZoneId zone)
	{
		return toMillisecond(hour, minute, second, nano, zone)
			.toTimestamp();
	}


	public int toInt()
	{
		return year() * POWER_YEAR + month() * POWER_MONTH + day();
	}


	//
	public static DayInYearInstant now(ZoneId zone)
	{
		return fromZonedDateTime(ZonedDateTimeUtil.instance(zone));
	}


	public static DayInYearInstant fromZonedDateTime(ZonedDateTime value)
	{
		return new DayInYearInstant(value.toLocalDate());
	}


	public static DayInYearInstant fromMillisecond(Millisecond value, ZoneId zone)
	{
		return fromZonedDateTime(value.toZonedDateTime(zone));
	}


	public static DayInYearInstant fromMillisecond(long value, ZoneId zone)
	{
		return fromZonedDateTime(ZonedDateTimeUtil.instance(value, zone));
	}


	public static DayInYearInstant fromTimestamp(Timestamp value, ZoneId zone)
	{
		return fromMillisecond(value.getTime(), zone);
	}


	public static DayInYearInstant fromInt(int value)
	{
		return new DayInYearInstant(value);
	}


	public static ArrayList<DayInYearInstant> range(DayInYearInstant begin, DayInYearInstant end)
	{
		ArrayList<DayInYearInstant> result = new ArrayList<>();
		LocalDate dateCurrent = begin.DATE;

		while (!dateCurrent.isAfter(end.DATE))
		{
			result.add(new DayInYearInstant(dateCurrent));
			dateCurrent = dateCurrent.plusDays(1);
		}

		return result;
	}


	//
	final public static class DatabaseField
		extends AFactoryObjectDatabaseField<DayInYearInstant>
	{
		//
		final private Model MODEL;

		//
		public DatabaseField(String table, int number, String name, DayInYearInstant init, Option... options)
		{
			super(table, number, name, init, FACTORIES, options);

			MODEL = new Model(this, true, false);
		}

		@Override
		public Model model()
		{
			return MODEL;
		}
	}


	final public static class Model
		extends AFactoryObjectModel<DayInYearInstant>
	{
		//
		final private Parser PARSER;

		//
		public Model(@NullDisallow DatabaseField field, boolean quotation, boolean trim)
		{
			super(field, quotation, trim);

			PARSER = new Parser(field.init(), field.nullable(), trim);
		}

		public Model(@NullDisallow String name, boolean nullable, boolean quotation, boolean trim, @NullAllow DayInYearInstant init, @NullDisallow String... objects)
		{
			super(name, nullable, quotation, trim, init, FACTORIES, objects);

			PARSER = new Parser(init, nullable, trim);
		}

		//
		@Override
		public @NullDisallow IScriptEntry toScriptEntry()
		{
			return INIT != null
				? new IntScriptValue(INIT.toInt(), QUOTATION)
				: NullScriptValue.INSTANCE;
		}

		@Override
		public @NullDisallow IScriptEntry toScriptEntry(@NullAllow DayInYearInstant value)
		{
			Validator.argument(NULLABLE || value != null, "[value] null value disallowed");

			return value != null
				? new IntScriptValue(value.toInt(), QUOTATION)
				: NullScriptValue.INSTANCE;
		}

		@Override
		public @NullDisallow IParser.Report<DayInYearInstant> parse(@NullAllow CharSequence source)
		{
			return PARSER.parse(source);
		}

		@Override
		public @NullDisallow IParser.Report<DayInYearInstant> parse(@NullAllow JsonObject source)
		{
			if (source != null)
			{
				IJsonEntry entry = source.get(NAME);

				if (entry != null)
				{
					IJsonEntry.Type type = entry.type();

					if (type == IJsonEntry.Type.NUMBER && !QUOTATION) return PARSER.parse((NumberJsonValue) entry);
					else if (type == IJsonEntry.Type.STRING && QUOTATION) return PARSER.parse((StringJsonValue) entry);
					else if (type == IJsonEntry.Type.NULL && NULLABLE) return PARSER.REPORT_NULL;
				}
			}

			return PARSER.REPORT_ERROR;
		}
	}


	final public static class Parser
		implements IParser<DayInYearInstant>
	{
		//
		final private static Report<DayInYearInstant> REPORT_NULL_UNDEFINED = new Report<>(true, false, null);
		final private static Report<DayInYearInstant> REPORT_NULL_DEFINED = new Report<>(true, true, null);

		//
		final private @NullAllow DayInYearInstant INIT;
		final private boolean NULLABLE;
		final private boolean TRIM;
		final private Report<DayInYearInstant> REPORT_INIT;
		final private Report<DayInYearInstant> REPORT_ERROR;
		final private Report<DayInYearInstant> REPORT_NULL;
		final private IntParser PARSER;

		//
		public Parser(@NullAllow DayInYearInstant init, boolean nullable, boolean trim)
		{
			Validator.argument(nullable || init != null, "[init] null value disallowed");

			INIT = init;
			NULLABLE = nullable;
			TRIM = trim;
			REPORT_INIT = new Report<>(true, false, init);
			REPORT_ERROR = new Report<>(false, false, init);
			REPORT_NULL = init != null ? REPORT_NULL_DEFINED : REPORT_NULL_UNDEFINED;
			PARSER = new IntParser(init != null ? init.toInt() : null, nullable, trim);
		}

		//
		@Override
		public @NullAllow DayInYearInstant init()
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
		public Report<DayInYearInstant> parse(@NullAllow DayInYearInstant value)
		{
			if (value != null)
			{
				return !FactoryObjectUtil.equal(value, INIT)
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
		public Report<DayInYearInstant> parse(@NullAllow CharSequence source)
		{
			Report<Integer> report = PARSER.parse(source);

			if (report.EXISTS)
			{
				if (report.DEFINED)
				{
					if (report.VALUE != null) return new Report<>(true, true, new DayInYearInstant(report.VALUE));
					else return REPORT_NULL;
				}
				else
				{
					return REPORT_INIT;
				}
			}
			else
			{
				return REPORT_ERROR;
			}
		}
	}


	//
	final public static DayInYearInstant UNKNOWN = new DayInYearInstant(1, 1, 1);

}
