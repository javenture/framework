package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.identifier.IntIdentifier;
import net.javenture.framework.log.Log;
import net.javenture.framework.util.*;


/*
	2019/12/24
 */
final class JournalDataItem
	extends ADualItem<Long, Integer, JournalDataItem.Key, JournalDataItem>
{
	/*
		(SIGNATURE_ID + ITEM_KEY) -> Long HASH (для ускорения поиска требуемого объекта, вместо индекса по SIGNATURE_ID + ITEM_KEY)
	 */








	//
	final public static Config CONFIG = new Config();


	//
	JournalDataItem(long id, int number)
	{
		this(new Key(id, number));
	}


	JournalDataItem(JournalManager.Event event)
	{
		this(new Key(event.ID, event.NUMBER));
	}


	private JournalDataItem(Key key)
	{
		super(key);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	static Chain<JournalManager.Event> events(@NullDisallow DatabaseTransaction transaction, long id)
		throws Exception
	{
		Chain<JournalManager.Event> result = new Chain<>();
		ASqlTemplate.AQuery template = SqlTemplates.LIST;
		SqlParameters parameters = new SqlParameters(id);

		IRowSqlProcessor processor = objects ->
		{
			JournalManager.Event event = new JournalManager.Event((long) objects[0], (int) objects[1]);
			result.add(event);
		};

		transaction.execute(template, parameters, processor);

		return result;
	}


	/*
		get/set
	 */
	long getId()
	{
		return get(Config.FIELD_ID);
	}


	int getNumber()
	{
		return get(Config.FIELD_NUMBER);
	}


	long getPreviousIterationId()
	{
		return get(Config.FIELD_PREVIOUS_ITERATION_ID);
	}


	void setPreviousIterationId(long value)
	{
		set(Config.FIELD_PREVIOUS_ITERATION_ID, value);
	}


	int getPreviousIterationNumber()
	{
		return get(Config.FIELD_PREVIOUS_ITERATION_NUMBER);
	}


	void setPreviousIterationNumber(int value)
	{
		set(Config.FIELD_PREVIOUS_ITERATION_NUMBER, value);
	}


	long getPreviousVersionId()
	{
		return get(Config.FIELD_PREVIOUS_VERSION_ID);
	}


	void setPreviousVersionId(long value)
	{
		set(Config.FIELD_PREVIOUS_VERSION_ID, value);
	}


	int getPreviousVersionNumber()
	{
		return get(Config.FIELD_PREVIOUS_VERSION_NUMBER);
	}


	void setPreviousVersionNumber(int value)
	{
		set(Config.FIELD_PREVIOUS_VERSION_NUMBER, value);
	}


	JournalOperation getOperation()
	{
		return get(Config.FIELD_OPERATION);
	}


	void setOperation(JournalOperation value)
	{
		set(Config.FIELD_OPERATION, value);
	}


	IntIdentifier getSignatureId()
	{
		return get(Config.FIELD_SIGNATURE_ID);
	}


	void setSignatureId(IntIdentifier value)
	{
		set(Config.FIELD_SIGNATURE_ID, value);
	}


	byte[] getItemKey()
	{
		return get(Config.FIELD_ITEM_KEY);
	}


	void setItemKey(byte[] value)
	{
		set(Config.FIELD_ITEM_KEY, value);
	}


	byte[] getItemData()
	{
		return get(Config.FIELD_ITEM_DATA);
	}


	void setItemData(byte[] value)
	{
		set(Config.FIELD_ITEM_DATA, value);
	}


	byte[] getUserKey()
	{
		return get(Config.FIELD_USER_KEY);
	}


	void setUserKey(byte[] value)
	{
		set(Config.FIELD_USER_KEY, value);
	}


	Millisecond getDate()
	{
		return get(Config.FIELD_DATE);
	}


	void setDate(Millisecond value)
	{
		set(Config.FIELD_DATE, value);
	}


	/*
		config
	*/
	final public static class Config
		extends AConfig<Long, Integer, Key, JournalDataItem>
	{
		//
		final public static String NAME_TABLE = "JOURNAL_DATA";                          // XXX: $JOURNAL_DATA                                      // XXX: customize ?

		final public static LongDatabaseField FIELD_ID = new LongDatabaseField
		(
			NAME_TABLE,
			0,
			"ID",
			-1L,
			ADatabaseField.Option.KEY,
			ADatabaseField.Option.STRICT
		);

		final public static IntDatabaseField FIELD_NUMBER = new IntDatabaseField
		(
			NAME_TABLE,
			1,
			"NUMBER",
			-1,
			ADatabaseField.Option.KEY,
			ADatabaseField.Option.STRICT
		);

		final public static LongDatabaseField FIELD_PREVIOUS_ITERATION_ID = new LongDatabaseField
		(
			NAME_TABLE,
			2,
			"PREVIOUS_ITERATION_ID",
			-1L
		);

		final public static IntDatabaseField FIELD_PREVIOUS_ITERATION_NUMBER = new IntDatabaseField
		(
			NAME_TABLE,
			3,
			"PREVIOUS_ITERATION_NUMBER",
			-1
		);

		final public static LongDatabaseField FIELD_PREVIOUS_VERSION_ID = new LongDatabaseField
		(
			NAME_TABLE,
			4,
			"PREVIOUS_VERSION_ID",
			-1L
		);

		final public static IntDatabaseField FIELD_PREVIOUS_VERSION_NUMBER = new IntDatabaseField
		(
			NAME_TABLE,
			5,
			"PREVIOUS_VERSION_NUMBER",
			-1
		);

		final public static JournalOperation.DatabaseField FIELD_OPERATION = new JournalOperation.DatabaseField
		(
			NAME_TABLE,
			6,
			"OPERATION",
			JournalOperation.UNKNOWN,
			ADatabaseField.Option.STRICT
		);

		final public static IntIdentifier.DatabaseField FIELD_SIGNATURE_ID = new IntIdentifier.DatabaseField               // !!!: index (SIGNATURE_ID + ITEM_KEY)
		(
			NAME_TABLE,
			7,
			"SIGNATURE_ID",
			IntIdentifier.UNKNOWN // ! not null
		);

		final public static OctetsDatabaseField FIELD_ITEM_KEY = new OctetsDatabaseField                                  // !!!: index (SIGNATURE_ID + ITEM_KEY)
		(
			NAME_TABLE,
			8,
			"ITEM_KEY",
			ByteArrayUtil.BLANK, // ! not null
			16 // !
		);

		final public static BytesDatabaseField FIELD_ITEM_DATA = new BytesDatabaseField
		(
			NAME_TABLE,
			9,
			"ITEM_DATA",
			ByteArrayUtil.NULL,
			ADatabaseField.Option.NULLABLE
		);

		final public static OctetsDatabaseField FIELD_USER_KEY = new OctetsDatabaseField
		(
			NAME_TABLE,
			10,
			"USER_KEY",
			ByteArrayUtil.BLANK,
			16 // !
		);

		final public static Millisecond.DatabaseField FIELD_DATE = new Millisecond.DatabaseField                           // ???: index
		(
			NAME_TABLE,
			11,
			"DT",
			Millisecond.ZERO,
			ADatabaseField.Option.STRICT
		);

		final public static DatabaseFields FIELDS = new DatabaseFields
		(
			FIELD_ID,
			FIELD_NUMBER,
			FIELD_PREVIOUS_ITERATION_ID,
			FIELD_PREVIOUS_ITERATION_NUMBER,
			FIELD_PREVIOUS_VERSION_ID,
			FIELD_PREVIOUS_VERSION_NUMBER,
			FIELD_OPERATION,
			FIELD_SIGNATURE_ID,
			FIELD_ITEM_KEY,
			FIELD_ITEM_DATA,
			FIELD_USER_KEY,
			FIELD_DATE
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
		protected Key key(@NullDisallow Long id0, @NullDisallow Integer id1)
		{
			return new Key(id0, id1);
		}

		@Override
		protected @NullDisallow byte[] toBytes(@NullDisallow Key key)
		{
			byte[] result = new byte[Primitives.LONG.DIMENSION + Primitives.INT.DIMENSION];
			LongUtil.bytes(key.id0(), result, 0);
			IntUtil.bytes(key.id1(), result, Primitives.LONG.DIMENSION);

			return result;
		}

		@Override
		protected @NullDisallow Key fromBytes(@NullDisallow byte[] array)
			throws Exception
		{
			long value0 = LongUtil.parse(array, 0);
			int value1 = IntUtil.parse(array, Primitives.LONG.DIMENSION);

			return new Key(value0, value1);
		}

		@Override
		public JournalDataItem instance(Key key)
		{
			return new JournalDataItem(key);
		}
	}


	final static class Key
		extends ADualItem.AKey<Long, Integer>
	{
		//
		private Key(long id, int number)
		{
			super(id, number);
		}

		//
		@Override
		protected LongDatabaseField field0()
		{
			return Config.FIELD_ID;
		}

		@Override
		protected IntDatabaseField field1()
		{
			return Config.FIELD_NUMBER;
		}
	}


	final private static class SqlTemplates
	{
		final private static ASqlTemplate.AQuery LIST = new ASqlTemplate.AQuery()
		{
			@Override
			protected SqlMarkup _markup()
			{
				return new SqlMarkup
				(
					"select",
						JournalDataItem.Config.FIELD_ID.receiver(0),
						",",
						JournalDataItem.Config.FIELD_NUMBER.receiver(1),
					"from",
						JournalDataItem.Config.TABLE,
					"where",
						JournalDataItem.Config.FIELD_ID.sender(0), "=", "?",
					"order by",
						JournalDataItem.Config.FIELD_NUMBER
				);
			}
		};
	}

}
