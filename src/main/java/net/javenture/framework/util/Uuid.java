package net.javenture.framework.util;


import net.javenture.framework.annotation.Immutable;
import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.factory.IByteFactory;
import net.javenture.framework.factory.ICopyFactory;
import net.javenture.framework.factory.IDatabaseFactory;
import net.javenture.framework.factory.IFactoryObject;
import net.javenture.framework.factory.IInstanceFactory;
import net.javenture.framework.factory.IMurmur3HashFactory;
import net.javenture.framework.factory.IStringFactory;
import net.javenture.framework.firebird.AFactoryObjectDatabaseField;
import net.javenture.framework.firebird.DatabaseType;
import net.javenture.framework.hex.Hex;
import net.javenture.framework.hex.HexUtil;
import net.javenture.framework.json.IJsonEntry;
import net.javenture.framework.json.JsonObject;
import net.javenture.framework.json.StringJsonValue;
import net.javenture.framework.model.AFactoryObjectModel;
import net.javenture.framework.hash.IMurmur3Hashable;
import net.javenture.framework.hash.Murmur3Entry;
import net.javenture.framework.hash.Murmur3Hash;
import net.javenture.framework.script.HexScriptValue;
import net.javenture.framework.script.IScriptEntry;
import net.javenture.framework.script.NullScriptValue;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;


/*
	2021/05/29
 */
@Immutable
final public class Uuid
	implements IFactoryObject<Uuid>, IMurmur3Hashable, Comparable<Uuid>
{
	//
	final public static int DIMENSION = Primitives.LONG.DIMENSION * 2;
	final public static IInstanceFactory<Uuid> FACTORY_INSTANCE = object -> object instanceof Uuid;
	final public static ICopyFactory<Uuid> FACTORY_COPY = object -> object;

	final public static IByteFactory<Uuid> FACTORY_BYTE = new IByteFactory<>()
	{
		@Override
		public void toStream(@NullAllow Uuid object, OutputStream destination)
			throws Exception
		{
			if (IByteFactory.setNullHeader(object, destination)) UuidUtil.bytes(object, destination);
		}

		@Override
		public @NullAllow Uuid fromStream(InputStream source)
			throws Exception
		{
			return IByteFactory.getNullHeader(source)
				? UuidUtil.parse(source)
				: null;
		}
	};

	final public static IStringFactory<Uuid> FACTORY_STRING = new IStringFactory<>()
	{
		@Override
		public @NullDisallow String toString(@NullAllow Uuid object)
		{
			return StringUtil.toString(object);
		}

		@Override
		public Parser parser(@NullAllow Uuid init, boolean nullable, boolean trim)
		{
			return new Parser(init, nullable, trim);
		}
	};

	final public static IMurmur3HashFactory<Uuid> FACTORY_MURMUR3HASH = (object, destination) ->
	{
		if (IMurmur3HashFactory.updateNullHeader(object, destination)) UuidUtil.murmur3hash(object, destination);
	};

	final public static IDatabaseFactory<Uuid, byte[]> FACTORY_DATABASE = new IDatabaseFactory<>()
	{
		@Override
		public DatabaseType getDatabaseType()
		{
			return DatabaseType.BYTES;
		}

		@Override
		public @NullAllow byte[] toDatabase(@NullAllow Uuid object)
		{
			return object != null ? UuidUtil.bytes(object) : null;
		}

		@Override
		public @NullAllow Uuid fromDatabase(@NullAllow byte[] object)
		{
			return object != null ? new Uuid(object) : null;
		}
	};

	final public static Factories<Uuid> FACTORIES = new Factories<>(FACTORY_INSTANCE, FACTORY_COPY, FACTORY_BYTE, FACTORY_STRING, FACTORY_MURMUR3HASH, FACTORY_DATABASE);


	//
	@Murmur3Entry
	final private @NullDisallow UUID VALUE;


	//
	public Uuid(@NullDisallow UUID value)
	{
		Validator.notNull(value, "[value]");
		VALUE = value;
	}


	public Uuid(long l0, long l1)
	{
		VALUE = new UUID(l0, l1);
	}


	public Uuid(@NullDisallow byte[] array)
	{
		Validator.notNull(array, "[array]");
		Validator.argument(array.length == DIMENSION, "[array.length] (", array.length, ") != [DIMENSION] (", DIMENSION, ")");

		long l0 = LongUtil.parse(array, 0, -1);
		long l1 = LongUtil.parse(array, 8, -1);
		VALUE = new UUID(l0, l1);
	}


	//
	@Override
	public Factories<Uuid> factories()
	{
		return FACTORIES;
	}


	@Override
	public Murmur3Hash murmur3hash()
	{
		return FACTORY_MURMUR3HASH.hash(this);
	}


	@Override
	public int compareTo(@NullDisallow Uuid object)
	{
		return this.VALUE.compareTo(object.VALUE);
	}


	@Override
	public int hashCode()
	{
		return VALUE.hashCode();
	}


	@Override
	@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
	public boolean equals(@NullAllow Object object)
	{
		return equals((Uuid) object);
	}


	@Override
	public boolean equals(@NullAllow Uuid object)
	{
		return
			this == object
			||
			object != null
			&&
			this.VALUE.getMostSignificantBits() == object.VALUE.getMostSignificantBits()
			&&
			this.VALUE.getLeastSignificantBits() == object.VALUE.getLeastSignificantBits();
	}


	@Override
	public String toString()
	{
		return toString(false);
	}


	public String toString(boolean capitalize)
	{
		return Hex.toString(UuidUtil.bytes(this), capitalize);
	}


	public long getMostSignificantBits()
	{
		return VALUE.getMostSignificantBits();
	}


	public long getLeastSignificantBits()
	{
		return VALUE.getLeastSignificantBits();
	}


	public Hex toHex()
	{
		return Hex.wrap(UuidUtil.bytes(this));
	}


	//
	public static Uuid generate()
	{
		return new Uuid(UUID.randomUUID());
	}


	//
	final public static class DatabaseField
		extends AFactoryObjectDatabaseField<Uuid>
	{
		//
		final private Model MODEL;

		//
		public DatabaseField(String table, int number, String name, @NullAllow Uuid init, Option... options)
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
		extends AFactoryObjectModel<Uuid>
	{
		//
		final private Parser PARSER;

		//
		public Model(@NullDisallow DatabaseField field, boolean quotation, boolean trim)
		{
			super(field, quotation, trim);

			PARSER = new Parser(field.init(), field.nullable(), trim);
		}

		public Model(@NullDisallow String name, boolean nullable, boolean quotation, boolean trim, @NullAllow Uuid init, @NullDisallow String... objects)
		{
			super(name, nullable, quotation, trim, init, FACTORIES, objects);

			PARSER = new Parser(init, nullable, trim);
		}

		//
		@Override
		public @NullDisallow IScriptEntry toScriptEntry()
		{
			return INIT != null
				? new HexScriptValue(INIT.toHex(), QUOTATION)
				: NullScriptValue.INSTANCE;
		}

		@Override
		public @NullDisallow IScriptEntry toScriptEntry(@NullAllow Uuid value)
		{
			Validator.argument(NULLABLE || value != null, "[value] null value disallowed");

			return value != null
				? new HexScriptValue(value.toHex(), QUOTATION)
				: NullScriptValue.INSTANCE;
		}

		@Override
		public @NullDisallow IParser.Report<Uuid> parse(@NullAllow CharSequence source)
		{
			return PARSER.parse(source);
		}

		@Override
		public @NullDisallow IParser.Report<Uuid> parse(@NullAllow JsonObject source)
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
		implements IParser<Uuid>
	{
		//
		final private @NullAllow Uuid INIT;
		final private boolean NULLABLE;
		final private boolean TRIM;
		final private Report<Uuid> REPORT_INIT;
		final private Report<Uuid> REPORT_ERROR;
		final private Report<Uuid> REPORT_NULL;

		//
		public Parser(@NullAllow Uuid init, boolean nullable, boolean trim)
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
		public @NullAllow Uuid init()
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
		public Report<Uuid> parse(@NullAllow Uuid value)
		{
			if (value != null)
			{
				return !UuidUtil.equal(value, INIT)
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
		public Report<Uuid> parse(@NullAllow CharSequence source)
		{
			if (source != null)
			{
				if (!NULLABLE || NullUtil.notNull(source, TRIM))
				{
					byte[] array = HexUtil.primitive(source, TRIM);

					if (array != null)
					{
						Uuid uuid = new Uuid(array);

						return INIT == null || !UuidUtil.equal(uuid, INIT)
							? new Report<>(true, true, uuid)
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
		final public static Parser UNKNOWN = new Parser(Uuid.UNKNOWN, false, false);
		final public static Parser NULL = new Parser(null, true, false);
	}


	//
	final public static Uuid UNKNOWN = new Uuid(-1, -1);

}
