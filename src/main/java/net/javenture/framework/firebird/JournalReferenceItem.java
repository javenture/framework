package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.identifier.IntIdentifier;
import net.javenture.framework.identifier.IntIdentifierUtil;
import net.javenture.framework.log.Log;
import net.javenture.framework.util.ByteArrayUtil;
import net.javenture.framework.util.IntUtil;
import net.javenture.framework.util.Primitives;


/*
	2019/12/13
 */
final class JournalReferenceItem
	extends ADualItem<IntIdentifier, byte[], JournalReferenceItem.Key, JournalReferenceItem>
{
	//
	final public static Config CONFIG = new Config();


	//
	JournalReferenceItem(IntIdentifier idSignature, byte[] key)
	{
		this(new Key(idSignature, key));
	}


	private JournalReferenceItem(Key key)
	{
		super(key);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	/*
		get/set
	 */
	IntIdentifier getItemSignature()
	{
		return get(Config.FIELD_SIGNATURE_ID);
	}


	byte[] getItemKey()
	{
		return get(Config.FIELD_ITEM_KEY);
	}


	long getId()
	{
		return get(Config.FIELD_ID);
	}


	void setId(long value)
	{
		set(Config.FIELD_ID, value);
	}


	int getNumber()
	{
		return get(Config.FIELD_NUMBER);
	}


	void setNumber(int value)
	{
		set(Config.FIELD_NUMBER, value);
	}


	/*
		config
	*/
	final public static class Config
		extends AConfig<IntIdentifier, byte[], Key, JournalReferenceItem>
	{
		//
		final public static String NAME_TABLE = "JOURNAL_REFERENCE";                                                             // XXX: customize ?

		final public static IntIdentifier.DatabaseField FIELD_SIGNATURE_ID = new IntIdentifier.DatabaseField
		(
			NAME_TABLE,
			0,
			"SIGNATURE_ID",
			IntIdentifier.UNKNOWN,
			ADatabaseField.Option.KEY,
			ADatabaseField.Option.STRICT
		);

		final public static OctetsDatabaseField FIELD_ITEM_KEY = new OctetsDatabaseField
		(
			NAME_TABLE,
			1,
			"ITEM_KEY",
			ByteArrayUtil.BLANK,
			256,
			ADatabaseField.Option.KEY,
			ADatabaseField.Option.STRICT
		);

		final public static LongDatabaseField FIELD_ID = new LongDatabaseField
		(
			NAME_TABLE,
			2,
			"ID",
			Long.MIN_VALUE,
			ADatabaseField.Option.STRICT
		);

		final public static IntDatabaseField FIELD_NUMBER = new IntDatabaseField
		(
			NAME_TABLE,
			3,
			"NUMBER",
			-1,
			ADatabaseField.Option.STRICT
		);

		final public static DatabaseFields FIELDS = new DatabaseFields
		(
			FIELD_SIGNATURE_ID,
			FIELD_ITEM_KEY,
			FIELD_ID,
			FIELD_NUMBER
		);

		final public static DatabaseTable TABLE = new DatabaseTable
		(
			NAME_TABLE,
			FIELDS
		);

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
		protected Key key(@NullDisallow IntIdentifier id0, @NullDisallow byte[] id1)
		{
			return new Key(id0, id1);
		}

		@Override
		protected @NullDisallow byte[] toBytes(@NullDisallow Key key)
		{
			IntIdentifier value0 = key.id0();
			byte[] value1 = key.id1();
			int length = value1.length;

			byte[] result = new byte[Primitives.INT.DIMENSION + Primitives.INT.DIMENSION + length];
			IntIdentifierUtil.bytes(value0, result, 0);
			IntUtil.bytes(length, result, Primitives.INT.DIMENSION);
			System.arraycopy(value1, 0, result, Primitives.INT.DIMENSION + Primitives.INT.DIMENSION, length);

			return result;
		}

		@Override
		protected @NullDisallow Key fromBytes(@NullDisallow byte[] array)
			throws Exception
		{
			IntIdentifier value0 = IntIdentifierUtil.parse(array, 0);
			int length = IntUtil.parse(array, Primitives.INT.DIMENSION);
			byte[] value1 = new byte[length];
			System.arraycopy(array, Primitives.INT.DIMENSION + Primitives.INT.DIMENSION, value1, 0, length);

			return new Key(value0, value1);
		}

		@Override
		protected JournalReferenceItem instance(@NullDisallow Key key)
		{
			return new JournalReferenceItem(key);
		}
	}


	final static class Key
		extends ADualItem.AKey<IntIdentifier, byte[]>
	{
		//
		private Key(@NullDisallow IntIdentifier idSignature, @NullDisallow byte[] key)
		{
			super(idSignature, key);
		}

		//
		@Override
		protected IntIdentifier.DatabaseField field0()
		{
			return Config.FIELD_SIGNATURE_ID;
		}

		@Override
		protected OctetsDatabaseField field1()
		{
			return Config.FIELD_ITEM_KEY;
		}
	}

}
