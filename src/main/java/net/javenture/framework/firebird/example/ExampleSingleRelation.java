package net.javenture.framework.firebird.example;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.firebird.ADatabaseField;
import net.javenture.framework.firebird.ASingleRelation;
import net.javenture.framework.firebird.DatabaseFields;
import net.javenture.framework.firebird.DatabaseTable;
import net.javenture.framework.identifier.IntIdentifier;
import net.javenture.framework.identifier.IntIdentifierSequence;
import net.javenture.framework.identifier.IntIdentifierUtil;
import net.javenture.framework.log.Log;
import net.javenture.framework.util.ISequence;
import net.javenture.framework.util.IntUtil;


final class ExampleSingleRelation
	extends ASingleRelation<IntIdentifier, ExampleSingleRelation.Key, ExampleSingleRelation>
{
	//
	final public static Config CONFIG = new Config();
	final private static Log LOG = Log.instance(ExampleSingleRelation.class);


	//
	private ExampleSingleRelation(IntIdentifier id)
	{
		this(new Key(id));
	}


	private ExampleSingleRelation(Key key)
	{
		super(key);
	}


	//
/*
	static void sync(UserItem1 item, DatabaseTransaction transaction)
		throws Exception
	{
		ExampleSingleRelation relation = new ExampleSingleRelation(item.getId());

		if (item.getActivity()) relation.create(transaction);
		else relation.delete(transaction);
	}
*/


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
		extends AConfig<IntIdentifier, Key, ExampleSingleRelation>
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
		protected @NullDisallow byte[] toBytes(@NullDisallow Key key)
		{
			IntIdentifier id = key.id();

			return IntIdentifierUtil.bytes(id);
		}

		@Override
		protected @NullDisallow Key fromBytes(@NullDisallow byte[] array)
			throws Exception
		{
			IntIdentifier id = IntIdentifierUtil.parse(array, 0);

			return new Key(id);
		}

		@Override
		protected ExampleSingleRelation instance(Key key)
		{
			return new ExampleSingleRelation(key);
		}

		@Override
		protected IntIdentifierSequence sequence()
		{
			return new IntIdentifierSequence();
		}
	}


	final static class Key
		extends ASingleRelation.AKey<IntIdentifier>
	{
		//
		private Key(IntIdentifier value)
		{
			super(value);
		}

		//
		@Override
		protected IntIdentifier.DatabaseField field()
		{
			return Config.FIELD_ID;
		}
	}

}
