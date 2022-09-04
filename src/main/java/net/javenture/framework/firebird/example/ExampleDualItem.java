package net.javenture.framework.firebird.example;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.firebird.ADatabaseField;
import net.javenture.framework.firebird.ADualItem;
import net.javenture.framework.firebird.DatabaseFields;
import net.javenture.framework.firebird.DatabaseTable;
import net.javenture.framework.firebird.IntDatabaseField;
import net.javenture.framework.firebird.LongDatabaseField;
import net.javenture.framework.log.Log;
import net.javenture.framework.util.IntUtil;
import net.javenture.framework.util.LongUtil;
import net.javenture.framework.util.Primitives;


final class ExampleDualItem
	extends ADualItem<Long, Integer, ExampleDualItem.Key, ExampleDualItem>
{
	//
	final public static Config CONFIG = new Config();
	final private static Log LOG = Log.instance(ExampleDualItem.class);


	//
	ExampleDualItem(long id, int number)
	{
		this(new Key(id, number));
	}


	private ExampleDualItem(Key key)
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
	long getId()
	{
		return get(Config.FIELD_ID);
	}


	int getNumber()
	{
		return get(Config.FIELD_NUMBER);
	}


	/*
		config
	*/
	final public static class Config
		extends AConfig<Long, Integer, Key, ExampleDualItem>
	{
		//
		final public static String NAME_TABLE = "?";

		final public static LongDatabaseField FIELD_ID = new LongDatabaseField
		(
			NAME_TABLE,
			0,
			"?",
			-1L,
			ADatabaseField.Option.KEY,
			ADatabaseField.Option.STRICT
		);

		final public static IntDatabaseField FIELD_NUMBER = new IntDatabaseField
		(
			NAME_TABLE,
			1,
			"?",
			-1,
			ADatabaseField.Option.KEY,
			ADatabaseField.Option.STRICT
		);

		final public static DatabaseFields FIELDS = new DatabaseFields
		(
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
		public ExampleDualItem instance(Key key)
		{
			return new ExampleDualItem(key);
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

}
