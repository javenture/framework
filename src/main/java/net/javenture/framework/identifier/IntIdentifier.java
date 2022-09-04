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
import net.javenture.framework.script.IntScriptValue;
import net.javenture.framework.script.NullScriptValue;
import net.javenture.framework.util.IParser;
import net.javenture.framework.util.IntParser;
import net.javenture.framework.util.IntUtil;
import net.javenture.framework.util.StringUtil;
import net.javenture.framework.util.Validator;
import net.javenture.framework.model.AFactoryObjectModel;

import java.io.InputStream;
import java.io.OutputStream;


/*
	2020/01/05
 */
final public class IntIdentifier
	extends AIdentifier<Integer, IntIdentifier>
{
	//
	final private static int INIT = -1;
	final public static IInstanceFactory<IntIdentifier> FACTORY_INSTANCE = object -> object instanceof IntIdentifier;
	final public static ICopyFactory<IntIdentifier> FACTORY_COPY = object -> object;

	final public static IByteFactory<IntIdentifier> FACTORY_BYTE = new IByteFactory<>()
	{
		@Override
		public void toStream(@NullAllow IntIdentifier object, OutputStream destination)
			throws Exception
		{
			if (IByteFactory.setNullHeader(object, destination)) IntUtil.bytes(object.VALUE, destination);
		}

		@Override
		public @NullAllow IntIdentifier fromStream(InputStream source)
			throws Exception
		{
			if (IByteFactory.getNullHeader(source))
			{
				int value = IntUtil.parse(source);

				return value != INIT ? new IntIdentifier(value) : UNKNOWN;
			}
			else
			{
				return null;
			}
		}
	};

	final public static IStringFactory<IntIdentifier> FACTORY_STRING = new IStringFactory<>()
	{
		@Override
		public @NullDisallow String toString(@NullAllow IntIdentifier object)
		{
			return StringUtil.toString(object);
		}

		@Override
		public @NullDisallow Parser parser(@NullAllow IntIdentifier init, boolean nullable, boolean trim)
		{
			return new Parser(init, nullable, trim);
		}
	};

	final public static IDatabaseFactory<IntIdentifier, Integer> FACTORY_DATABASE = new IDatabaseFactory<>()
	{
		@Override
		public DatabaseType getDatabaseType()
		{
			return DatabaseType.INT;
		}

		@Override
		public @NullAllow Integer toDatabase(@NullAllow IntIdentifier object)
		{
			return object != null ? object.VALUE : null;
		}

		@Override
		public @NullAllow IntIdentifier fromDatabase(@NullAllow Integer object)
		{
			if (object != null) return object != INIT ? new IntIdentifier(object) : UNKNOWN;
			else return null;
		}
	};

	final public static Factories<IntIdentifier> FACTORIES = new Factories<>(FACTORY_INSTANCE, FACTORY_COPY, FACTORY_BYTE, FACTORY_STRING, FACTORY_DATABASE);


	//
	final public int VALUE;


	//
	public IntIdentifier(int value)
	{
		VALUE = value;
	}


	@Override
	public boolean defined()
	{
		return VALUE != INIT;
	}


	@Override
	public @NullDisallow Integer value()
	{
		return VALUE;
	}


	@Override
	public boolean contains(@NullDisallow Integer value)
	{
		return VALUE == value;
	}


	@Override
	public Factories<IntIdentifier> factories()
	{
		return FACTORIES;
	}


	@Override
	public int hashCode()
	{
		return IntUtil.hashcode(VALUE);
	}


	@Override
	public boolean equals(@NullAllow IntIdentifier object)
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
	public int compareTo(@NullDisallow IntIdentifier object)
	{
		return Integer.compare(this.VALUE, object.VALUE);
	}


	//
	final public static class DatabaseField
		extends AFactoryObjectDatabaseField<IntIdentifier>
	{
		//
		final private Model MODEL;

		//
		public DatabaseField(String table, int number, String name, IntIdentifier init, Option... options)
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
		extends AFactoryObjectModel<IntIdentifier>
	{
		//
		final private Parser PARSER;

		//
		public Model(@NullDisallow DatabaseField field, boolean quotation, boolean trim)
		{
			super(field, quotation, trim);

			PARSER = new Parser(field.init(), field.nullable(), trim);
		}

		public Model(@NullDisallow String name, boolean nullable, boolean quotation, boolean trim, @NullAllow IntIdentifier init, @NullDisallow String... objects)
		{
			super(name, nullable, quotation, trim, init, FACTORIES, objects);

			PARSER = new Parser(init, nullable, trim);
		}

		//
		@Override
		public @NullDisallow IScriptEntry toScriptEntry()
		{
			return INIT != null
				? new IntScriptValue(INIT.VALUE, QUOTATION)
				: NullScriptValue.INSTANCE;
		}

		@Override
		public @NullDisallow IScriptEntry toScriptEntry(@NullAllow IntIdentifier value)
		{
			Validator.argument(NULLABLE || value != null, "[value] null value disallowed");

			return value != null
				? new IntScriptValue(value.VALUE, QUOTATION)
				: NullScriptValue.INSTANCE;
		}

		@Override
		public @NullDisallow IParser.Report<IntIdentifier> parse(@NullAllow CharSequence source)
		{
			return PARSER.parse(source);
		}

		@Override
		public @NullDisallow IParser.Report<IntIdentifier> parse(@NullAllow JsonObject source)
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
		implements IParser<IntIdentifier>
	{
		//
		final private @NullAllow IntIdentifier INIT;
		final private boolean NULLABLE;
		final private boolean TRIM;
		final private Report<IntIdentifier> REPORT_INIT;
		final private Report<IntIdentifier> REPORT_ERROR;
		final private Report<IntIdentifier> REPORT_NULL;
		final private IntParser PARSER0; // !

		//
		public Parser(@NullAllow IntIdentifier init, boolean nullable, boolean trim)
		{
			Validator.argument(nullable || init != null, "[init] null value disallowed");

			INIT = init;
			NULLABLE = nullable;
			TRIM = trim;
			REPORT_INIT = new Report<>(true, false, init);
			REPORT_ERROR = new Report<>(false, false, init);
			REPORT_NULL = new Report<>(true, init != null, null);
			PARSER0 = new IntParser(init != null ? init.VALUE : null, nullable, trim);
		}

		//
		@Override
		public @NullAllow IntIdentifier init()
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
		public Report<IntIdentifier> parse(@NullAllow IntIdentifier value)
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
		public Report<IntIdentifier> parse(@NullAllow CharSequence source)
		{
			Report<Integer> report = PARSER0.parse(source);

			if (report.EXISTS)
			{
				if (report.DEFINED)
				{
					if (report.VALUE != null) return new Report<>(true, true, new IntIdentifier(report.VALUE));
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
		final public static Parser UNKNOWN = new Parser(IntIdentifier.UNKNOWN, false, false);
	}


	//
	final public static IntIdentifier UNKNOWN = new IntIdentifier(INIT);
	final public static IntIdentifier NULL = null;

}
