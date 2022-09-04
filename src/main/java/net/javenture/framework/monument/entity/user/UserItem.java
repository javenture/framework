package net.javenture.framework.monument.entity.user;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.firebird.ADatabaseField;
import net.javenture.framework.firebird.AGeneratedSingleItem;
import net.javenture.framework.firebird.ASqlTemplate;
import net.javenture.framework.firebird.BooleanDatabaseField;
import net.javenture.framework.firebird.DatabaseFields;
import net.javenture.framework.firebird.DatabaseTable;
import net.javenture.framework.firebird.DatabaseTransaction;
import net.javenture.framework.firebird.IKeyGenerator;
import net.javenture.framework.firebird.IntDatabaseField;
import net.javenture.framework.firebird.SqlMarkup;
import net.javenture.framework.firebird.StringDatabaseField;
import net.javenture.framework.identifier.LongIdentifier;
import net.javenture.framework.identifier.LongIdentifierContainer;
import net.javenture.framework.identifier.LongIdentifierUtil;
import net.javenture.framework.log.Log;
import net.javenture.framework.util.LongContainer;
import net.javenture.framework.util.Validator;

import org.mindrot.jbcrypt.BCrypt;


/*
	TODO
	- фиксирование (IP + name) всех неудачных попыток авторизации и ограничивать попытки при большом количестве ошибок за короткое время


	html:
		UserItem item = new UserItem(id)

	    if (item.lock()) -> HttpSession
	    {
	    	...
	    }
	    else
	    {
	    	...
	    }

	    item.unlock()

 */

final public class UserItem
	extends AGeneratedSingleItem<LongIdentifier, UserItem.Key, UserItem>
{
	//
	final public static Config CONFIG = new Config();
	final private static Log LOG = Log.instance(UserItem.class);
	final private static Object LOCK = new Object();

	final private static int BCRYPT_ROUNDS = 10;                                // XXX: 10 ?
	final private static int BCRYPT_HASHCODE_LENGTH = 60;


	final public static LongIdentifier SYSTEM = new LongIdentifier(0);                            // XXX ?


	//
	final public static int ACCESS_UNKNOWN = -1;                                                                            // XXX: enum
	final public static int ACCESS_ADMIN = 0;
	final public static int ACCESS_DIRECTOR = 1;
	final public static int ACCESS_MANAGER = 2;
	final public static int ACCESS_OPERATOR = 3;
	final public static int ACCESS_GUEST = 4;


	//
	public UserItem()
	{
		this(new Key());
	}


	public UserItem(LongIdentifier id)
	{
		this(new Key(id));
	}


	public UserItem(Key key)
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
	public boolean exists()
		throws Exception
	{
		return super.exists();
	}


	@Override
	public boolean exists(DatabaseTransaction transaction)
		throws Exception
	{
		return super.exists(transaction);
	}


	@Override
	public void create()
		throws Exception
	{
		super.create();
	}


	@Override
	public void create(DatabaseTransaction transaction)
		throws Exception
	{
		super.create(transaction);
	}


	@Override
	public void load()
		throws Exception
	{
		super.load();
	}


	@Override
	public void load(DatabaseTransaction transaction)
		throws Exception
	{
		super.load(transaction);
	}


	@Override
	public boolean loadIfExists()
		throws Exception
	{
		return super.loadIfExists();
	}


	@Override
	public boolean loadIfExists(DatabaseTransaction transaction)
		throws Exception
	{
		return super.loadIfExists(transaction);
	}


	@Override
	public void save()
		throws Exception
	{
		super.save();
	}


	@Override
	public void save(DatabaseTransaction transaction)
		throws Exception
	{
		super.save(transaction);
	}


	@Override
	public void recycle()
		throws Exception
	{
		super.recycle();
	}


	@Override
	public void recycle(DatabaseTransaction transaction)
		throws Exception
	{
		super.recycle(transaction);
	}


	@Override
	public boolean recycleIfExists()
		throws Exception
	{
		return super.recycleIfExists();
	}


	@Override
	public boolean recycleIfExists(DatabaseTransaction transaction)
		throws Exception
	{
		return super.recycleIfExists(transaction);
	}


	@Override
	public void delete()
		throws Exception
	{
		super.delete();
	}


	@Override
	public void delete(DatabaseTransaction transaction)
		throws Exception
	{
		super.delete(transaction);
	}


	@Override
	public boolean deleteIfExists()
		throws Exception
	{
		return super.deleteIfExists();
	}


	@Override
	public boolean deleteIfExists(DatabaseTransaction transaction)
		throws Exception
	{
		return super.deleteIfExists(transaction);
	}


	@Override
	protected void triggerAfterCreate(DatabaseTransaction transaction)
		throws Exception
	{
		UserActivityRelation.sync(this, transaction);
	}


	@Override
	protected void triggerAfterSave(DatabaseTransaction transaction)
		throws Exception
	{
		UserActivityRelation.sync(this, transaction);
	}


	@Override
	protected void triggerBeforeRecycle(DatabaseTransaction transaction)
		throws Exception
	{
		UserActivityRelation.sync(this, transaction);
	}


	@Override
	protected void triggerBeforeDelete(DatabaseTransaction transaction)
		throws Exception
	{
		UserActivityRelation.sync(this, transaction);
	}





	public static boolean logon(String name, String password)
		throws Exception                                                                                          // XXX: throws?
	{
		Validator.argument(!name.isEmpty(), "[name] is empty");
		Validator.argument(!password.isEmpty(), "[password] is empty");

		//
		LongIdentifier id = UserItem.SqlTemplates.SEARCH.execute(Config.FIELD_NAME.prepare(name));

		return id.defined() && logon(id, password);

	}


	public static boolean logon(LongIdentifier id, String password)
		throws Exception                                                                                          // XXX: throws?
	{
		Validator.argument(id.defined(), "[id] is unknown");

		//
		synchronized (LOCK)
		{
			UserItem item = new UserItem(id);
			item.load();

			if (item.getActivity())
			{
				String hashcode = item.getHashcode();

				return BCrypt.checkpw(password, hashcode);
			}
		}

		return false;
	}


	public static boolean change(LongIdentifier id, String password)
		throws Exception                                                                     // XXX: throws?
	{
		Validator.argument(id.defined(), "[id] is unknown");
		Validator.argument(!password.isEmpty(), "[password] is empty");

		synchronized (LOCK)                                                                // XXX: ?
		{
			String salt = BCrypt.gensalt(BCRYPT_ROUNDS);
			String hashcode = BCrypt.hashpw(password, salt);

			try (DatabaseTransaction transaction = DatabaseTransaction.write())
			{
				UserItem item = new UserItem(id);                                     // XXX: sync (lock/unlock) on user id
				item.load();
				item.setHashcode(hashcode);
				item.save();

				//
				transaction.commit();
			}
		}

		return false;
	}


	public static LongIdentifierContainer list()
	{
		return UserItem.SqlTemplates.LIST.execute();
	}


	/*
		get/set
	*/
	public LongIdentifier getId()
	{
		return get(Config.FIELD_ID);
	}


	public boolean getActivity()
	{
		return get(Config.FIELD_ACTIVITY);
	}


	public void setActivity(boolean b)
	{
		set(Config.FIELD_ACTIVITY, b);
	}


	public String getName()
	{
		return get(Config.FIELD_NAME);
	}


	public void setName(String s)
	{
		set(Config.FIELD_NAME, s);
	}


	public String getHashcode()
	{
		return get(Config.FIELD_HASHCODE);
	}


	public void setHashcode(String s)
	{
		set(Config.FIELD_HASHCODE, s);
	}


	public int getAccess()
	{
		return get(Config.FIELD_ACCESS);
	}


	public void setAccess(int i)
	{
		set(Config.FIELD_ACCESS, i);
	}


	/*
		config
	*/
	final public static class Config
		extends AGeneratedSingleItem.AConfig<LongIdentifier, Key, UserItem>
	{
		//
		final public static String NAME_TABLE = "USR";

		final public static LongIdentifier.DatabaseField FIELD_ID = new LongIdentifier.DatabaseField
		(
			NAME_TABLE,
			0,
			"ID",
			LongIdentifier.UNKNOWN,
			ADatabaseField.Option.KEY,
			ADatabaseField.Option.STRICT
		);

		final public static BooleanDatabaseField FIELD_ACTIVITY = new BooleanDatabaseField
		(
			NAME_TABLE,
			1,
			"ACTIVITY",
			false
		);

		final public static StringDatabaseField FIELD_NAME = new StringDatabaseField
		(
			NAME_TABLE,
			2,
			"NAME",
			"",
			32,
			ADatabaseField.Option.STRICT
		);

		final public static StringDatabaseField FIELD_HASHCODE = new StringDatabaseField
		(
			NAME_TABLE,
			3,
			"HASHCODE",
			"",
			BCRYPT_HASHCODE_LENGTH,
			ADatabaseField.Option.STRICT
		);

		final public static IntDatabaseField FIELD_ACCESS = new IntDatabaseField                         // XXX: ACCESS -> LEVEL ?
		(
			NAME_TABLE,
			4,
			"ACCESS",
			ACCESS_UNKNOWN,
			ADatabaseField.Option.STRICT
		);

		final public static DatabaseFields FIELDS = new DatabaseFields
		(
			FIELD_ID,
			FIELD_ACTIVITY,
			FIELD_NAME,
			FIELD_HASHCODE,
			FIELD_ACCESS
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
			return true;
		}

		@Override
		public boolean cache()
		{
			return false;
		}

		@Override
		protected IKeyGenerator<LongIdentifier> generator()
		{
			return IKeyGenerator.IDENTIFIER_LONG;
		}

		@Override
		protected Key key(LongIdentifier id)
		{
			return new Key(id);
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
		protected UserItem instance()
		{
			return new UserItem();
		}

		@Override
		protected UserItem instance(@NullDisallow Key key)
		{
			return new UserItem(key);
		}
	}


	final public static class Key
		extends AGeneratedSingleItem.AKey<LongIdentifier>
	{
		//
		public Key()
		{
			super();
		}

		public Key(LongIdentifier id)
		{
			super(id);
		}

		//
		@Override
		protected LongIdentifier.DatabaseField field()
		{
			return Config.FIELD_ID;
		}
	}


	final private static class SqlTemplates
	{
		final private static ASqlTemplate.AObjectQuery<LongIdentifier> SEARCH = new ASqlTemplate.AObjectQuery<>()
		{
			@Override
			protected SqlMarkup _markup()
			{
				return new SqlMarkup
				(
					"select",
						Config.FIELD_ID.receiver(0),
					"from",
						Config.TABLE,
					"where",
						Config.FIELD_NAME.sender(0), "=", "?",
					"fetch first row only"
				);
			}

			@Override
			protected LongIdentifier init()
			{
				return LongIdentifier.UNKNOWN;
			}
		};

		final private static ASqlTemplate.ARawContainerQuery<LongContainer, LongIdentifierContainer> LIST = new ASqlTemplate.ARawContainerQuery<>()   // XXX: LIST_ALL & LIST_ACTIVE
		{
			@Override
			protected SqlMarkup _markup()
			{
				return new SqlMarkup
				(
					"select",
						Config.FIELD_ID.receiver(0, true),                                   // XXX: Config.FIELD_ID | IndexItem.Config.FIELD_ID
					"from",
						Config.TABLE                                                                              // XXX: Config.TABLE | IndexItem.Config.TABLE
				);
			}

			@Override
			protected LongContainer raw()
			{
				return new LongContainer();
			}

			@Override
			protected LongIdentifierContainer convert(LongContainer raw)
			{
				return LongIdentifierContainer.wrap(raw);
			}

			@Override
			protected LongIdentifierContainer init()
			{
				return new LongIdentifierContainer(0);
			}
		};
	}

}
