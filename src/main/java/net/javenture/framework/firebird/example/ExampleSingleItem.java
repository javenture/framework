package net.javenture.framework.firebird.example;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.firebird.ADatabaseField;
import net.javenture.framework.firebird.ASingleItem;
import net.javenture.framework.firebird.DatabaseFields;
import net.javenture.framework.firebird.DatabaseTable;
import net.javenture.framework.identifier.IntIdentifier;
import net.javenture.framework.log.Log;
import net.javenture.framework.util.IntUtil;


final class ExampleSingleItem
	extends ASingleItem<IntIdentifier, ExampleSingleItem.Key, ExampleSingleItem>
{
	//
	final public static Config CONFIG = new Config();
	final private static Log LOG = Log.instance(ExampleSingleItem.class);


	//
	public ExampleSingleItem(IntIdentifier id)
	{
		this(new Key(id));
	}


	private ExampleSingleItem(Key key)
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
		config
	*/
	final public static class Config
		extends ASingleItem.AConfig<IntIdentifier, Key, ExampleSingleItem>
	{
		//
		final public static String NAME_TABLE = "";

		final public static IntIdentifier.DatabaseField FIELD_ID = new IntIdentifier.DatabaseField
		(
			NAME_TABLE,
			0,
			"ID",
			IntIdentifier.UNKNOWN,
			ADatabaseField.Option.KEY,
			ADatabaseField.Option.STRICT
		);

		final public static DatabaseFields FIELDS = new DatabaseFields
		(
			FIELD_ID
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
		protected Key key(@NullDisallow IntIdentifier id)
		{
			return new Key(id);
		}

		@Override
		protected @NullDisallow byte[] toBytes(@NullDisallow Key key)
		{
			IntIdentifier id = key.id();

			return IntUtil.bytes(id.VALUE);
		}

		@Override
		protected @NullDisallow Key fromBytes(@NullDisallow byte[] array)
			throws Exception
		{
			IntIdentifier id = new IntIdentifier(IntUtil.parse(array, 0));

			return new Key(id);
		}

		@Override
		protected ExampleSingleItem instance(@NullDisallow Key key)
		{
			return new ExampleSingleItem(key);
		}
	}


	final static class Key
		extends ASingleItem.AKey<IntIdentifier>
	{
		//
		private Key(@NullDisallow IntIdentifier id)
		{
			super(id);
		}

		//
		@Override
		protected IntIdentifier.DatabaseField field()
		{
			return Config.FIELD_ID;
		}
	}

}
