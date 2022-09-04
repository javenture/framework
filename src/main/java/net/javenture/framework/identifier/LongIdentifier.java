package net.javenture.framework.identifier;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.factory.FactoryObjectUtil;
import net.javenture.framework.factory.IByteFactory;
import net.javenture.framework.factory.ICopyFactory;
import net.javenture.framework.factory.IDatabaseFactory;
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
import net.javenture.framework.util.IParser;
import net.javenture.framework.util.LongParser;
import net.javenture.framework.util.LongUtil;
import net.javenture.framework.util.StringUtil;
import net.javenture.framework.util.Validator;

import java.io.InputStream;
import java.io.OutputStream;


/*
	2020/01/05
 */
final public class LongIdentifier
	extends AIdentifier<Long, LongIdentifier>
{
	//
	final private static long INIT = -1;
	final public static IInstanceFactory<LongIdentifier> FACTORY_INSTANCE = object -> object instanceof LongIdentifier;
	final public static ICopyFactory<LongIdentifier> FACTORY_COPY = object -> object;

	final public static IByteFactory<LongIdentifier> FACTORY_BYTE = new IByteFactory<>()
	{
		@Override
		public void toStream(@NullAllow LongIdentifier object, OutputStream destination)
			throws Exception
		{
			if (IByteFactory.setNullHeader(object, destination)) LongUtil.bytes(object.VALUE, destination);
		}

		@Override
		public @NullAllow LongIdentifier fromStream(InputStream source)
			throws Exception
		{
			if (IByteFactory.getNullHeader(source))
			{
				long value = LongUtil.parse(source);

				return value != INIT ? new LongIdentifier(value) : UNKNOWN;
			}
			else
			{
				return null;
			}
		}
	};

	final public static IStringFactory<LongIdentifier> FACTORY_STRING = new IStringFactory<>()
	{
		@Override
		public @NullDisallow String toString(@NullAllow LongIdentifier object)
		{
			return StringUtil.toString(object);
		}

		@Override
		public @NullDisallow Parser parser(@NullAllow LongIdentifier init, boolean nullable, boolean trim)
		{
			return new Parser(init, nullable, trim);
		}
	};

	final public static IDatabaseFactory<LongIdentifier, Long> FACTORY_DATABASE = new IDatabaseFactory<>()
	{
		@Override
		public DatabaseType getDatabaseType()
		{
			return DatabaseType.LONG;
		}

		@Override
		public @NullAllow Long toDatabase(@NullAllow LongIdentifier object)
		{
			return object != null ? object.VALUE : null;
		}

		@Override
		public @NullAllow LongIdentifier fromDatabase(@NullAllow Long object)
		{
			if (object != null) return object != INIT ? new LongIdentifier(object) : UNKNOWN;
			else return null;
		}
	};

	final public static Factories<LongIdentifier> FACTORIES = new Factories<>(FACTORY_INSTANCE, FACTORY_COPY, FACTORY_BYTE, FACTORY_STRING, FACTORY_DATABASE);


	//
	final public long VALUE;


	//
	public LongIdentifier(long value)
	{
		VALUE = value;
	}


	//
	@Override
	public boolean defined()
	{
		return VALUE != INIT;
	}


	@Override
	public @NullDisallow Long value()
	{
		return VALUE;
	}


	@Override
	public boolean contains(@NullDisallow Long value)
	{
		return VALUE == value;
	}


	@Override
	public Factories<LongIdentifier> factories()
	{
		return FACTORIES;
	}


	@Override
	public int hashCode()
	{
		return LongUtil.hashcode(VALUE);
	}


	@Override
	public boolean equals(@NullAllow LongIdentifier object)
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


	@Override
	public int compareTo(@NullDisallow LongIdentifier object)
	{
		return Long.compare(this.VALUE, object.VALUE);
	}


	//
	final public static class DatabaseField
		extends AFactoryObjectDatabaseField<LongIdentifier>
	{
		//
		final private Model MODEL;

		//
		public DatabaseField(String table, int number, String name, @NullAllow LongIdentifier init, Option... options)
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
		extends AFactoryObjectModel<LongIdentifier>
	{
		//
		final private Parser PARSER;

		//
		public Model(@NullDisallow DatabaseField field, boolean quotation, boolean trim)
		{
			super(field, quotation, trim);

			PARSER = new Parser(field.init(), field.nullable(), trim);
		}

		public Model(@NullDisallow String name, boolean nullable, boolean quotation, boolean trim, @NullAllow LongIdentifier init, @NullDisallow String... objects)
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
		public @NullDisallow IScriptEntry toScriptEntry(@NullAllow LongIdentifier value)
		{
			Validator.argument(NULLABLE || value != null, "[value] null value disallowed");

			return value != null
				? new LongScriptValue(value.VALUE, QUOTATION)
				: NullScriptValue.INSTANCE;
		}

		@Override
		public @NullDisallow IParser.Report<LongIdentifier> parse(@NullAllow CharSequence source)
		{
			return PARSER.parse(source);
		}

		@Override
		public @NullDisallow IParser.Report<LongIdentifier> parse(@NullAllow JsonObject source)
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
		implements IParser<LongIdentifier>
	{
		//
		final private @NullAllow LongIdentifier INIT;
		final private boolean NULLABLE;
		final private boolean TRIM;
		final private Report<LongIdentifier> REPORT_INIT;
		final private Report<LongIdentifier> REPORT_ERROR;
		final private Report<LongIdentifier> REPORT_NULL;
		final private LongParser PARSER0; // !

		//
		public Parser(@NullAllow LongIdentifier init, boolean nullable, boolean trim)
		{
			Validator.argument(nullable || init != null, "[init] null value disallowed");

			INIT = init;
			NULLABLE = nullable;
			TRIM = trim;
			REPORT_INIT = new Report<>(true, false, init);
			REPORT_ERROR = new Report<>(false, false, init);
			REPORT_NULL = new Report<>(true, init != null, null);
			PARSER0 = new LongParser(init != null ? init.VALUE : null, nullable, trim);
		}

		//
		@Override
		public @NullAllow LongIdentifier init()
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
		public Report<LongIdentifier> parse(@NullAllow LongIdentifier value)
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
		public Report<LongIdentifier> parse(@NullAllow CharSequence source)
		{
			Report<Long> report = PARSER0.parse(source);

			if (report.EXISTS)
			{
				if (report.DEFINED)
				{
					if (report.VALUE != null) return new Report<>(true, true, new LongIdentifier(report.VALUE));
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
		final public static Parser UNKNOWN = new Parser(LongIdentifier.UNKNOWN, false, false);
	}


	//
	final public static LongIdentifier UNKNOWN = new LongIdentifier(INIT);
	final public static LongIdentifier NULL = null;

}
