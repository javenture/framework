package net.javenture.framework.monument.entity.user;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.firebird.ADatabaseField;
import net.javenture.framework.firebird.ASingleRelation;
import net.javenture.framework.firebird.DatabaseFields;
import net.javenture.framework.firebird.DatabaseTable;
import net.javenture.framework.firebird.DatabaseTransaction;
import net.javenture.framework.identifier.LongIdentifier;
import net.javenture.framework.identifier.LongIdentifierSequence;
import net.javenture.framework.identifier.LongIdentifierUtil;
import net.javenture.framework.log.Log;


/*
	2019/12/14
 */
final public class UserActivityRelation
	extends ASingleRelation<LongIdentifier, UserActivityRelation.Key, UserActivityRelation>
{
	//
	final public static Config CONFIG = new Config();
	final private static Log LOG = Log.instance(UserActivityRelation.class);


	//
	private UserActivityRelation(LongIdentifier id)
	{
		this(new Key(id));
	}


	private UserActivityRelation(Key key)
	{
		super(key);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	//
/*
	public static LongIdentifierSequence sequence()
	{
		LongIdentifierSequence result = new LongIdentifierSequence();
		toSequence(CONFIG, result, LongIdentifier.FACTORIES);

		return result
	}
*/


	static void sync(UserItem item, DatabaseTransaction transaction)
		throws Exception
	{
		UserActivityRelation relation = new UserActivityRelation(item.getId());

		if (item.getActivity()) relation.save(transaction);
		else relation.deleteIfExists(transaction);
	}


	//
	final public static class Config
		extends AConfig<LongIdentifier, Key, UserActivityRelation>
	{
		//
		final public static String NAME_TABLE = "USR_ACTIVITY_INDEX";                                                      // XXX: USR_ACTIVITY_RELATION ?

		final public static LongIdentifier.DatabaseField FIELD_ID = new LongIdentifier.DatabaseField
		(
			NAME_TABLE,
			0,
			"ID",
			LongIdentifier.UNKNOWN,
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
			LongIdentifier id = key.id();

			return LongIdentifierUtil.bytes(id);
		}

		@Override
		protected @NullDisallow Key fromBytes(@NullDisallow byte[] array)
			throws Exception
		{
			LongIdentifier id = LongIdentifierUtil.parse(array, 0);

			return new Key(id);
		}

		@Override
		protected UserActivityRelation instance(Key key)
		{
			return new UserActivityRelation(key);
		}

		@Override
		protected LongIdentifierSequence sequence()
		{
			return new LongIdentifierSequence();
		}
	}


	final static class Key
		extends ASingleRelation.AKey<LongIdentifier>
	{
		//
		private Key(LongIdentifier value)
		{
			super(value);
		}

		//
		@Override
		protected LongIdentifier.DatabaseField field()
		{
			return Config.FIELD_ID;
		}
	}

}
