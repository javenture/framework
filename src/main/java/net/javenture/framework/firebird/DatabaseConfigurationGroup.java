package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.identifier.IntIdentifier;
import net.javenture.framework.log.Log;
import net.javenture.framework.util.IntUtil;

import java.util.Comparator;


/*
	2019/12/13
 */
final public class DatabaseConfigurationGroup                                                                                                        // XXX: package
	extends ASingleGroup<IntIdentifier, DatabaseConfigurationGroup.Key, DatabaseConfigurationGroup.Entry, DatabaseConfigurationGroup>
{
	//
	final public static Config CONFIG = new Config();


	//
	public DatabaseConfigurationGroup(IntIdentifier idSignature)                                                                // XXX: package
	{
		this(new Key(idSignature));
	}


	private DatabaseConfigurationGroup(Key key)
	{
		super(key);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	@Override
	public @NullDisallow Entry entry()
	{
		return new Entry(id());
	}


	/*
		config
	*/
	final static class Config
		extends AConfig<IntIdentifier, Key, Entry, DatabaseConfigurationGroup>
	{
		//
		final public static String NAME_TABLE = "DATABASE_CONFIGURATION";                                                     // XXX: $DATABASE_CONFIGURATION

		final public static IntIdentifier.DatabaseField FIELD_SIGNATURE_ID = new IntIdentifier.DatabaseField                 // XXX: index, not PK
		(
			NAME_TABLE,
			0,
			"SIGNATURE_ID",
			IntIdentifier.UNKNOWN,
			ADatabaseField.Option.KEY,
			ADatabaseField.Option.STRICT
		);

		final public static IntDatabaseField FIELD_NUMBER = new IntDatabaseField
		(
			NAME_TABLE,
			1,
			"FIELD_NUMBER",
			-1,
			ADatabaseField.Option.STRICT
		);

		final public static StringDatabaseField FIELD_NAME = new StringDatabaseField
		(
			NAME_TABLE,
			2,
			"FIELD_NAME",
			"",
			63,                                                                                       // XXX: ?
			ADatabaseField.Option.STRICT
		);

		final public static DatabaseType.DatabaseField FIELD_TYPE = new DatabaseType.DatabaseField
		(
			NAME_TABLE,
			3,
			"FIELD_TYPE",
			DatabaseType.UNKNOWN,
			ADatabaseField.Option.STRICT
		);

		final public static IntDatabaseField FIELD_LENGTH = new IntDatabaseField
		(
			NAME_TABLE,
			4,
			"FIELD_LENGTH",
			0
		);

		final public static DatabaseFields FIELDS = new DatabaseFields
		(
			FIELD_SIGNATURE_ID,
			FIELD_NUMBER,
			FIELD_NAME,
			FIELD_TYPE,
			FIELD_LENGTH
		);

		final public static DatabaseTable TABLE = new DatabaseTable
		(
			NAME_TABLE,
			FIELDS
		);

		final private static Comparator<Entry> COMPARATOR = Comparator.comparingInt(Entry::getNumber);

		//
		@Override
		public Log log()
		{
			return LOG;
		}

		@Override
		public DatabaseTable table()
		{
			return TABLE;
		}

		@Override
		public boolean journal()
		{
			return false;
		}

		@Override
		public boolean cache()
		{
			return false;
		}

		@Override
		public @NullAllow Comparator<Entry> comparator()
		{
			return COMPARATOR;
		}

		@Override
		protected byte[] toBytes(Key key)
		{
			IntIdentifier id = key.id();

			return IntUtil.bytes(id.VALUE);
		}

		@Override
		protected Key fromBytes(byte[] array)
			throws Exception
		{
			IntIdentifier id = new IntIdentifier(IntUtil.parse(array, 0));

			return new Key(id);
		}

		@Override
		protected DatabaseConfigurationGroup instance(Key key)
		{
			return new DatabaseConfigurationGroup(key);
		}
	}


	final static class Key
		extends ASingleGroup.AKey<IntIdentifier>
	{
		//
		final private static IntIdentifier.DatabaseField FIELD = Config.FIELD_SIGNATURE_ID;

		//
		private Key(IntIdentifier value)
		{
			super(value);
		}

		//
		@Override
		protected IntIdentifier.DatabaseField field()
		{
			return FIELD;
		}
	}


	final public static class Entry // ! public
		extends ASingleGroup.AEntry<IntIdentifier, Entry>
	{
		//
		private Entry(IntIdentifier id)
		{
			super(id);
		}

		//
		@Override
		protected Config config()
		{
			return CONFIG;
		}

		//
		IntIdentifier getSignatureId()
		{
			return get(Config.FIELD_SIGNATURE_ID);
		}

		int getNumber()
		{
			return get(Config.FIELD_NUMBER);
		}

		void setNumber(int i)
		{
			set(Config.FIELD_NUMBER, i);
		}

		String getName()
		{
			return get(Config.FIELD_NAME);
		}

		void setName(String s)
		{
			set(Config.FIELD_NAME, s);
		}

		DatabaseType getType()
		{
			return get(Config.FIELD_TYPE);
		}

		void setType(DatabaseType e)
		{
			set(Config.FIELD_TYPE, e);
		}

		int getLength()
		{
			return get(Config.FIELD_LENGTH);
		}

		void setLength(int i)
		{
			set(Config.FIELD_LENGTH, i);
		}
	}

}
