package net.javenture.framework.hex;


import net.javenture.framework.annotation.Immutable;
import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.factory.IByteFactory;
import net.javenture.framework.factory.ICopyFactory;
import net.javenture.framework.factory.IDatabaseFactory;
import net.javenture.framework.factory.IInstanceFactory;
import net.javenture.framework.factory.IMurmur3HashFactory;
import net.javenture.framework.factory.IStringFactory;
import net.javenture.framework.firebird.DatabaseType;
import net.javenture.framework.firebird.AFactoryObjectDatabaseField;
import net.javenture.framework.json.IJsonEntry;
import net.javenture.framework.json.JsonObject;
import net.javenture.framework.json.StringJsonValue;
import net.javenture.framework.hash.Murmur3Entry;
import net.javenture.framework.hash.Murmur3Hash;
import net.javenture.framework.script.HexScriptValue;
import net.javenture.framework.script.IScriptEntry;
import net.javenture.framework.script.NullScriptValue;
import net.javenture.framework.model.AFactoryObjectModel;
import net.javenture.framework.util.ByteArrayUtil;
import net.javenture.framework.util.IParser;
import net.javenture.framework.util.IntUtil;
import net.javenture.framework.util.NullUtil;
import net.javenture.framework.util.Primitives;
import net.javenture.framework.util.StringUtil;
import net.javenture.framework.util.Validator;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;


/*
	2021/05/28
 */
@Immutable
final public class IntHex
	implements IHex<Integer, IntHex>
{
	//
	final public static IInstanceFactory<IntHex> FACTORY_INSTANCE = object -> object instanceof IntHex;
	final public static ICopyFactory<IntHex> FACTORY_COPY = object -> object;

	final public static IByteFactory<IntHex> FACTORY_BYTE = new IByteFactory<>()
	{
		@Override
		public void toStream(@NullAllow IntHex object, OutputStream destination)
			throws Exception
		{
			if (IByteFactory.setNullHeader(object, destination)) ByteArrayUtil.FACTORY_BYTE.toStream(object.ARRAY, destination);
		}

		@Override
		public @NullAllow IntHex fromStream(InputStream source)
			throws Exception
		{
			return IByteFactory.getNullHeader(source)
				? new IntHex(ByteArrayUtil.FACTORY_BYTE.fromStream(source), true)
				: null;
		}
	};

	final public static IStringFactory<IntHex> FACTORY_STRING = new IStringFactory<>()
	{
		@Override
		public @NullDisallow String toString(@NullAllow IntHex object)
		{
			return StringUtil.toString(object);
		}

		@Override
		public Parser parser(@NullAllow IntHex init, boolean nullable, boolean trim)
		{
			return new Parser(init, nullable, trim);
		}
	};

	final public static IMurmur3HashFactory<IntHex> FACTORY_MURMUR3HASH = (object, destination) ->
	{
		if (IMurmur3HashFactory.updateNullHeader(object, destination))
		{
			byte[] array = object.ARRAY;
			IntUtil.murmur3hash(array.length, destination);
			destination.update(array);
		}
	};

	final public static IDatabaseFactory<IntHex, byte[]> FACTORY_DATABASE = new IDatabaseFactory<>()
	{
		@Override
		public DatabaseType getDatabaseType()
		{
			return DatabaseType.BYTES;
		}

		@Override
		public @NullAllow byte[] toDatabase(@NullAllow IntHex object)
		{
			return object != null ? object.ARRAY : null;
		}

		@Override
		public @NullAllow IntHex fromDatabase(@NullAllow byte[] object)
			throws Exception // !
		{
			return object != null ? new IntHex(object, true) : null;
		}
	};

	final public static Factories<IntHex> FACTORIES = new Factories<>(FACTORY_INSTANCE, FACTORY_COPY, FACTORY_BYTE, FACTORY_STRING, FACTORY_MURMUR3HASH, FACTORY_DATABASE);


	//
	@Murmur3Entry
	final private @NullDisallow byte[] ARRAY;


	//
	public IntHex(int value)
	{
		ARRAY = IntUtil.bytes(value);
	}


	private IntHex(@NullDisallow byte[] array, boolean wrap)
	{
		Validator.notNull(array, "[array]");
		Validator.argument(array.length == Primitives.INT.DIMENSION, "[array.length] (", array.length, ") != [Primitives.INT.DIMENSION] (", Primitives.INT.DIMENSION, ")");
		ARRAY = wrap ? array : array.clone();
	}


	//
	@Override
	public Factories<IntHex> factories()
	{
		return FACTORIES;
	}


	@Override
	public Murmur3Hash murmur3hash()
	{
		return FACTORY_MURMUR3HASH.hash(this);
	}


	@Override
	public int hashCode()
	{
		return murmur3hash().getInt();
	}


	@Override
	@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
	public boolean equals(@NullAllow Object object)
	{
		return equals((IntHex) object);
	}


	@Override
	public boolean equals(@NullAllow IntHex object)
	{
		return
			this == object
			||
			object != null
			&&
			Arrays.equals(this.ARRAY, object.ARRAY);
	}


	@Override
	public String toString()
	{
		return new String(HexUtil.convert(ARRAY, false));
	}


	@Override
	public String toString(boolean capitalize)
	{
		return new String(HexUtil.convert(ARRAY, capitalize));
	}


	@Override
	public int capacity()
	{
		return Primitives.INT.DIMENSION;
	}


	@Override
	public @NullDisallow Integer value()
	{
		return IntUtil.parse(ARRAY, 0, 0);
	}


	//
	public static IntHex wrap(@NullDisallow byte[] array)
	{
		return new IntHex(array, true);
	}


	public static String toString(int value)
	{
		return toString(value, false);
	}


	public static String toString(int value, boolean capitalize)
	{
		byte[] bytes = IntUtil.bytes(value);

		return new String(HexUtil.convert(bytes, capitalize));
	}


	//
	final public static class DatabaseField
		extends AFactoryObjectDatabaseField<IntHex>
	{
		//
		final private Model MODEL;

		//
		public DatabaseField(String table, int number, String name, IntHex init, Option... options)
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
		extends AFactoryObjectModel<IntHex>
	{
		//
		final private Parser PARSER;

		//
		public Model(@NullDisallow DatabaseField field, boolean quotation, boolean trim)
		{
			super(field, quotation, trim);

			PARSER = new Parser(field.init(), field.nullable(), trim);
		}

		public Model(@NullDisallow String name, boolean nullable, boolean quotation, boolean trim, @NullAllow IntHex init, @NullDisallow String... objects)
		{
			super(name, nullable, quotation, trim, init, FACTORIES, objects);

			PARSER = new Parser(init, nullable, trim);
		}

		//
		@Override
		public @NullDisallow IScriptEntry toScriptEntry()
		{
			return INIT != null
				? new HexScriptValue(INIT, QUOTATION)
				: NullScriptValue.INSTANCE;
		}

		@Override
		public @NullDisallow IScriptEntry toScriptEntry(@NullAllow IntHex value)
		{
			Validator.argument(NULLABLE || value != null, "[value] null value disallowed");

			return value != null
				? new HexScriptValue(value, QUOTATION)
				: NullScriptValue.INSTANCE;
		}

		@Override
		public @NullDisallow IParser.Report<IntHex> parse(@NullAllow CharSequence source)
		{
			return PARSER.parse(source);
		}

		@Override
		public @NullDisallow IParser.Report<IntHex> parse(@NullAllow JsonObject source)
		{
			if (source != null)
			{
				IJsonEntry entry = source.get(NAME);

				if (entry != null)
				{
					IJsonEntry.Type type = entry.type();

					if (type == IJsonEntry.Type.STRING && QUOTATION) return PARSER.parse((StringJsonValue) entry);
					else if (type == IJsonEntry.Type.NULL && NULLABLE) return PARSER.REPORT_NULL;
				}
			}

			return PARSER.REPORT_ERROR;
		}
	}

	final public static class Parser
		implements IParser<IntHex>
	{
		//
		final private @NullAllow IntHex INIT;
		final private boolean NULLABLE;
		final private boolean TRIM;
		final private Report<IntHex> REPORT_INIT;
		final private Report<IntHex> REPORT_ERROR;
		final private Report<IntHex> REPORT_NULL;

		//
		public Parser(@NullAllow IntHex init, boolean nullable, boolean trim)
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
		public @NullAllow IntHex init()
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
		public Report<IntHex> parse(@NullAllow IntHex value)
		{
			if (value != null)
			{
				return !HexUtil.equal(value, INIT)
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
		public Report<IntHex> parse(@NullAllow CharSequence source)
		{
			if (source != null)
			{
				if (!NULLABLE || NullUtil.notNull(source, TRIM))
				{
					byte[] bytes = HexUtil.primitive(source, TRIM);

					if (bytes != null)
					{
						return INIT == null || !ByteArrayUtil.equal(bytes, INIT.ARRAY)                                                        // TEST
							? new Report<>(true, true, new IntHex(bytes, true))
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
		final public static Parser ZERO = new Parser(IntHex.ZERO, false, false);
		final public static Parser MINUS1 = new Parser(IntHex.MINUS1, false, false);
		final public static Parser NULL = new Parser(null, true, false);
	}


	//
	final public static IntHex ZERO = new IntHex(0);
	final public static IntHex MINUS1 = new IntHex(-1);

}
