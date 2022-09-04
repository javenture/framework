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
import net.javenture.framework.identifier.LongIdentifier;
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
import net.javenture.framework.util.LongUtil;
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
final public class LongIdentifierHex
	implements IHex<LongIdentifier, LongIdentifierHex>
{
	//
	final public static IInstanceFactory<LongIdentifierHex> FACTORY_INSTANCE = object -> object instanceof LongIdentifierHex;
	final public static ICopyFactory<LongIdentifierHex> FACTORY_COPY = object -> object;

	final public static IByteFactory<LongIdentifierHex> FACTORY_BYTE = new IByteFactory<>()
	{
		@Override
		public void toStream(@NullAllow LongIdentifierHex object, OutputStream destination)
			throws Exception
		{
			if (IByteFactory.setNullHeader(object, destination)) ByteArrayUtil.FACTORY_BYTE.toStream(object.ARRAY, destination);
		}

		@Override
		public @NullAllow LongIdentifierHex fromStream(InputStream source)
			throws Exception
		{
			return IByteFactory.getNullHeader(source)
				? new LongIdentifierHex(ByteArrayUtil.FACTORY_BYTE.fromStream(source), true)
				: null;
		}
	};

	final public static IStringFactory<LongIdentifierHex> FACTORY_STRING = new IStringFactory<>()
	{
		@Override
		public @NullDisallow String toString(@NullAllow LongIdentifierHex object)
		{
			return StringUtil.toString(object);
		}

		@Override
		public @NullDisallow Parser parser(@NullAllow LongIdentifierHex init, boolean nullable, boolean trim)
		{
			return new Parser(init, nullable, trim);
		}
	};

	final public static IMurmur3HashFactory<LongIdentifierHex> FACTORY_MURMUR3HASH = (object, destination) ->
	{
		if (IMurmur3HashFactory.updateNullHeader(object, destination))
		{
			byte[] array = object.ARRAY;
			IntUtil.murmur3hash(array.length, destination);
			destination.update(array);
		}
	};

	final public static IDatabaseFactory<LongIdentifierHex, byte[]> FACTORY_DATABASE = new IDatabaseFactory<>()
	{
		@Override
		public DatabaseType getDatabaseType()
		{
			return DatabaseType.BYTES;
		}

		@Override
		public @NullAllow byte[] toDatabase(@NullAllow LongIdentifierHex object)
		{
			return object != null ? object.ARRAY : null;
		}

		@Override
		@SuppressWarnings("RedundantThrows")
		public @NullAllow LongIdentifierHex fromDatabase(@NullAllow byte[] object)
			throws Exception
		{
			return object != null ? new LongIdentifierHex(object, true) : null;
		}
	};

	final public static Factories<LongIdentifierHex> FACTORIES = new Factories<>(FACTORY_INSTANCE, FACTORY_COPY, FACTORY_BYTE, FACTORY_STRING, FACTORY_MURMUR3HASH, FACTORY_DATABASE);


	//
	@Murmur3Entry
	final private @NullDisallow byte[] ARRAY;


	//
	public LongIdentifierHex(@NullDisallow LongIdentifier value)
	{
		ARRAY = LongUtil.bytes(value.VALUE);
	}


	private LongIdentifierHex(@NullDisallow byte[] array, boolean wrap)
	{
		Validator.notNull(array, "[array]");

		Validator.argument
		(
			array.length == Primitives.LONG.DIMENSION,
			"[array.length] (", array.length, ") != [Primitives.LONG.DIMENSION] (", Primitives.LONG.DIMENSION, ")"
		);

		ARRAY = wrap ? array : array.clone();
	}


	//
	@Override
	public Factories<LongIdentifierHex> factories()
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
		return equals((LongIdentifierHex) object);
	}


	@Override
	public boolean equals(@NullAllow LongIdentifierHex object)
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
		return Primitives.LONG.DIMENSION;
	}


	@Override
	public LongIdentifier value()
	{
		return new LongIdentifier(LongUtil.parse(ARRAY, 0, LongIdentifier.UNKNOWN.VALUE)); // !
	}


	//
	public static LongIdentifierHex wrap(@NullDisallow byte[] array)
	{
		return new LongIdentifierHex(array, true);
	}


	public static String toString(@NullDisallow LongIdentifier value)
	{
		return toString(value, false);
	}


	public static String toString(@NullDisallow LongIdentifier value, boolean capitalize)
	{
		byte[] bytes = LongUtil.bytes(value.VALUE);

		return new String(HexUtil.convert(bytes, capitalize));
	}


	//
	final public static class DatabaseField
		extends AFactoryObjectDatabaseField<LongIdentifierHex>
	{
		//
		final private Model MODEL;

		//
		public DatabaseField(String table, int number, String name, LongIdentifierHex init, Option... options)
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
		extends AFactoryObjectModel<LongIdentifierHex>
	{
		//
		final private Parser PARSER;

		//
		public Model(@NullDisallow DatabaseField field, boolean quotation, boolean trim)
		{
			super(field, quotation, trim);

			PARSER = new Parser(field.init(), field.nullable(), trim);
		}

		public Model(@NullDisallow String name, boolean nullable, boolean quotation, boolean trim, @NullAllow LongIdentifierHex init, @NullDisallow String... objects)
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
		public @NullDisallow IScriptEntry toScriptEntry(@NullAllow LongIdentifierHex value)
		{
			Validator.argument(NULLABLE || value != null, "[value] null value disallowed");

			return value != null
				? new HexScriptValue(value, QUOTATION)
				: NullScriptValue.INSTANCE;
		}

		@Override
		public @NullDisallow IParser.Report<LongIdentifierHex> parse(@NullAllow CharSequence source)
		{
			return PARSER.parse(source);
		}

		@Override
		public @NullDisallow IParser.Report<LongIdentifierHex> parse(@NullAllow JsonObject source)
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
		implements IParser<LongIdentifierHex>
	{
		//
		final private @NullAllow LongIdentifierHex INIT;
		final private boolean NULLABLE;
		final private boolean TRIM;
		final private Report<LongIdentifierHex> REPORT_INIT;
		final private Report<LongIdentifierHex> REPORT_ERROR;
		final private Report<LongIdentifierHex> REPORT_NULL;

		//
		public Parser(@NullAllow LongIdentifierHex init, boolean nullable, boolean trim)
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
		public @NullAllow LongIdentifierHex init()
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
		public Report<LongIdentifierHex> parse(@NullAllow LongIdentifierHex value)
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
		public Report<LongIdentifierHex> parse(@NullAllow CharSequence source)
		{
			if (source != null)
			{
				if (!NULLABLE || NullUtil.notNull(source, TRIM))
				{
					byte[] bytes = HexUtil.primitive(source, TRIM);

					if (bytes != null)
					{
						return INIT == null || !ByteArrayUtil.equal(bytes, INIT.ARRAY)
							? new Report<>(true, true, new LongIdentifierHex(bytes, true))
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
		final public static Parser UNKNOWN = new Parser(LongIdentifierHex.UNKNOWN, false, false);
		final public static Parser NULL = new Parser(null, true, false);
	}


	//
	final public static LongIdentifierHex UNKNOWN = new LongIdentifierHex(LongIdentifier.UNKNOWN);

}
