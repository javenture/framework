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
import net.javenture.framework.script.LongScriptValue;
import net.javenture.framework.script.NullScriptValue;
import net.javenture.framework.model.AFactoryObjectModel;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;


/*
	2020/01/05
 */
@Immutable
final public class Millisecond
	implements IFactoryObject<Millisecond>
{
	//
	final private static long INIT = -1;
	final public static IInstanceFactory<Millisecond> FACTORY_INSTANCE = object -> object instanceof Millisecond;
	final public static ICopyFactory<Millisecond> FACTORY_COPY = object -> object;

	final public static IByteFactory<Millisecond> FACTORY_BYTE = new IByteFactory<>()
	{
		@Override
		public void toStream(@NullAllow Millisecond object, OutputStream destination)
			throws Exception
		{
			if (IByteFactory.setNullHeader(object, destination)) LongUtil.bytes(object.VALUE, destination);
		}

		@Override
		public @NullAllow Millisecond fromStream(InputStream source)
			throws Exception
		{
			return IByteFactory.getNullHeader(source) ? new Millisecond(LongUtil.parse(source)) : null;
		}
	};

	final public static IStringFactory<Millisecond> FACTORY_STRING = new IStringFactory<>()
	{
		@Override
		public @NullDisallow String toString(@NullAllow Millisecond object)
		{
			return object != null ? "" + object.VALUE : NullUtil.STRING;
		}

		@Override
		public @NullDisallow Parser parser(@NullAllow Millisecond init, boolean nullable, boolean trim)
		{
			return new Parser(init, nullable, trim);
		}
	};

	final public static IDatabaseFactory<Millisecond, Long> FACTORY_DATABASE = new IDatabaseFactory<>()
	{
		@Override
		public DatabaseType getDatabaseType()
		{
			return DatabaseType.LONG;
		}

		@Override
		public @NullAllow Long toDatabase(@NullAllow Millisecond object)
		{
			return object != null ? object.VALUE : null;
		}

		@Override
		public @NullAllow Millisecond fromDatabase(@NullAllow Long object)
		{
			return object != null ? new Millisecond(object) : null;
		}
	};

	final public static Factories<Millisecond> FACTORIES = new Factories<>(FACTORY_INSTANCE, FACTORY_COPY, FACTORY_BYTE, FACTORY_STRING, FACTORY_DATABASE);


	//
	final private long VALUE;


	//
	public Millisecond(long value)
	{
		VALUE = value;
	}


	//
	@Override
	public Factories<Millisecond> factories()
	{
		return FACTORIES;
	}


	@Override
	public int hashCode()
	{
		return LongUtil.hashcode(VALUE);
	}


	@Override
	@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
	public boolean equals(@NullAllow Object object)
	{
        return equals((Millisecond) object);
	}


	@Override
	public boolean equals(@NullAllow Millisecond object)
	{
        return
			this == object
			||
			object != null
			&&
			this.VALUE == object.VALUE;
	}


	@Override
	public String toString()
	{
		return "" + VALUE;
	}


	public long value()
	{
		return VALUE;
	}


	public boolean before(@NullDisallow Millisecond object)
	{
		return this.VALUE < object.VALUE;
	}


	public boolean after(@NullDisallow Millisecond object)
	{
		return this.VALUE > object.VALUE;
	}


	public ZonedDateTime toZonedDateTime(ZoneId zone)
	{
		return ZonedDateTimeUtil.instance(VALUE, zone);
	}


	public Timestamp toTimestamp()
	{
		return new Timestamp(VALUE);
	}


	//
	public static Millisecond now()
	{
		return new Millisecond(UtcTimeUtil.ms());
	}


	public static Millisecond fromZonedDateTime(ZonedDateTime value)
	{
		return new Millisecond(value.toInstant().toEpochMilli());
	}


	public static Millisecond fromLocalDateTime(LocalDateTime value, ZoneId zone)
	{
		return fromZonedDateTime(value.atZone(zone));
	}


	public static Millisecond fromTimestamp(Timestamp value)
	{
		return new Millisecond(value.getTime());
	}


	//
	final public static class DatabaseField
		extends AFactoryObjectDatabaseField<Millisecond>
	{
		//
		final private Model MODEL;

		//
		public DatabaseField(String table, int number, String name, Millisecond init, Option... options)
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
		extends AFactoryObjectModel<Millisecond>
	{
		//
		final private Parser PARSER;

		//
		public Model(@NullDisallow DatabaseField field, boolean quotation, boolean trim)
		{
			super(field, quotation, trim);

			PARSER = new Parser(field.init(), field.nullable(), trim);
		}

		public Model(@NullDisallow String name, boolean nullable, boolean quotation, boolean trim, @NullAllow Millisecond init, @NullDisallow String... objects)
		{
			super(name, nullable, quotation, trim, init, FACTORIES, objects);

			PARSER = new Parser(init, nullable, trim);
		}

		//
		@Override
		public @NullDisallow IScriptEntry toScriptEntry()
		{
			return INIT != null
				? new LongScriptValue(INIT.VALUE, QUOTATION)
				: NullScriptValue.INSTANCE;
		}

		@Override
		public @NullDisallow IScriptEntry toScriptEntry(@NullAllow Millisecond value)
		{
			Validator.argument(NULLABLE || value != null, "[value] null value disallowed");

			return value != null
				? new LongScriptValue(value.VALUE, QUOTATION)
				: NullScriptValue.INSTANCE;
		}

		@Override
		public @NullDisallow IParser.Report<Millisecond> parse(@NullAllow CharSequence source)
		{
			return PARSER.parse(source);
		}

		@Override
		public @NullDisallow IParser.Report<Millisecond> parse(@NullAllow JsonObject source)
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
		implements IParser<Millisecond>
	{
		//
		final private @NullAllow Millisecond INIT;
		final private boolean NULLABLE;
		final private boolean TRIM;
		final private Report<Millisecond> REPORT_INIT;
		final private Report<Millisecond> REPORT_ERROR;
		final private Report<Millisecond> REPORT_NULL;
		final private LongParser PARSER;

		//
		public Parser(@NullAllow Millisecond init, boolean nullable, boolean trim)
		{
			Validator.argument(nullable || init != null, "[init] null value disallowed");

			INIT = init;
			NULLABLE = nullable;
			TRIM = trim;
			REPORT_INIT = new Report<>(true, false, init);
			REPORT_ERROR = new Report<>(false, false, init);
			REPORT_NULL = new Report<>(true, init != null, null);
			PARSER = new LongParser(init != null ? init.VALUE : null, nullable, trim);
		}

		//
		@Override
		public @NullAllow Millisecond init()
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
		public Report<Millisecond> parse(@NullAllow Millisecond value)
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
		public Report<Millisecond> parse(@NullAllow CharSequence source)
		{
			Report<Long> report = PARSER.parse(source);

			if (report.EXISTS)
			{
				if (report.DEFINED)
				{
					if (report.VALUE != null) return new Report<>(true, true, new Millisecond(report.VALUE));
					else return REPORT_NULL;
				}
				else
				{
					return REPORT_INIT;
				}
			}

			return REPORT_ERROR;
		}

		//
		final public static Parser UNKNOWN = new Parser(Millisecond.UNKNOWN, false, false);
		final public static Parser ZERO = new Parser(Millisecond.ZERO, false, false);
		final public static Parser NULL = new Parser(null, true, false);
	}


	//
	final public static Millisecond UNKNOWN = new Millisecond(INIT);
	final public static Millisecond ZERO = new Millisecond(0);

}
