package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.identifier.IntIdentifier;
import net.javenture.framework.identifier.IntIdentifierUtil;
import net.javenture.framework.log.Log;


/*
	2019/12/13
 */
final public class DatabaseSignatureItem
	extends AGeneratedSingleItem<IntIdentifier, DatabaseSignatureItem.Key, DatabaseSignatureItem>
{
	//
	final public static Config CONFIG = new Config();
	final private static Log LOG = Log.instance(DatabaseSignatureItem.class);


	//
	public DatabaseSignatureItem()
	{
		this(new Key());
	}


	public DatabaseSignatureItem(IntIdentifier id)
	{
		this(new Key(id));
	}


	private DatabaseSignatureItem(Key key)
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
	public static void create(IDatabaseEntity.IConfig config)                                                   // XXX: DatabaseTransaction transaction ?
		throws Exception
	{
		try (DatabaseTransaction transaction = DatabaseTransaction.write())
		{
			DatabaseTable table = config.table();

			DatabaseSignatureItem itemDatabaseSignature = new DatabaseSignatureItem();
			itemDatabaseSignature.setTableName(table.NAME);
			itemDatabaseSignature.setTableVersion(0);
			itemDatabaseSignature.create(transaction);

			//
			DatabaseConfigurationGroup groupDatabaseConfiguration = new DatabaseConfigurationGroup(itemDatabaseSignature.id());

			for (ADatabaseField field : table.FIELDS)
			{
				int length = 0;

				if (field instanceof StringDatabaseField) length = ((StringDatabaseField) field).length();
				else if (field instanceof OctetsDatabaseField) length = ((OctetsDatabaseField) field).length();

				DatabaseConfigurationGroup.Entry entry = groupDatabaseConfiguration.entry();
				entry.setNumber(field.number());
				entry.setName(field.name());
				entry.setType(field.type());
				entry.setLength(length);
				groupDatabaseConfiguration.add(entry);
			}

			groupDatabaseConfiguration.save(transaction);

			//
			transaction.commit();
		}
	}


	public static IntIdentifier search(IDatabaseEntity.IConfig config)                                                              // DEL ?
		throws Exception
	{
		IntIdentifier result;

		try (DatabaseTransaction transaction = DatabaseTransaction.read())
		{
			DatabaseTable table = config.table();
			result = SqlTemplates.SEARCH.execute(transaction, Config.FIELD_TABLE_NAME.prepare(table.NAME));

			//
			transaction.commit();
		}

		return result;
	}


	public static IntIdentifier search(DatabaseTransaction transaction, IDatabaseEntity.IConfig config)
		throws Exception
	{
		DatabaseTable table = config.table();

		return SqlTemplates.SEARCH.execute(transaction, Config.FIELD_TABLE_NAME.prepare(table.NAME));
	}


	/*
		get/set
	*/
	public String getTableName()
	{
		return get(Config.FIELD_TABLE_NAME);
	}

	public void setTableName(String s)
	{
		set(Config.FIELD_TABLE_NAME, s);
	}


	public int getTableVersion()
	{
		return get(Config.FIELD_TABLE_VERSION);
	}

	public void setTableVersion(int i)
	{
		set(Config.FIELD_TABLE_VERSION, i);
	}


	/*
		config
	*/
	final public static class Config
		extends AGeneratedSingleItem.AConfig<IntIdentifier, Key, DatabaseSignatureItem>
	{
		//
		final public static String NAME_TABLE = "DATABASE_SIGNATURE";

		final public static IntIdentifier.DatabaseField FIELD_ID = new IntIdentifier.DatabaseField
		(
			NAME_TABLE,
			0,
			"ID",
			IntIdentifier.UNKNOWN,
			ADatabaseField.Option.KEY,
			ADatabaseField.Option.STRICT
		);

		final public static StringDatabaseField FIELD_TABLE_NAME = new StringDatabaseField
		(
			NAME_TABLE,
			1,
			"TABLE_NAME",
			"",
			63,
			ADatabaseField.Option.STRICT
		);

		final public static IntDatabaseField FIELD_TABLE_VERSION = new IntDatabaseField
		(
			NAME_TABLE,
			2,
			"TABLE_VERSION",
			-1,
			ADatabaseField.Option.STRICT
		);

		final public static DatabaseFields FIELDS = new DatabaseFields
		(
			FIELD_ID,
			FIELD_TABLE_NAME,
			FIELD_TABLE_VERSION
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
		protected IKeyGenerator<IntIdentifier> generator()
		{
			return IKeyGenerator.IDENTIFIER_INT;
		}

		@Override
		protected Key key(IntIdentifier id)
		{
			return new Key(id);
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
		protected DatabaseSignatureItem instance()
		{
			return new DatabaseSignatureItem();
		}

		@Override
		protected DatabaseSignatureItem instance(@NullDisallow Key key)
		{
			return new DatabaseSignatureItem(key);
		}
	}


	final static class Key
		extends AGeneratedSingleItem.AKey<IntIdentifier>
	{
		//
		final private static IntIdentifier.DatabaseField FIELD = Config.FIELD_ID;

		//
		private Key()
		{
			super();
		}

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


	final private static class SqlTemplates
	{
		final private static ASqlTemplate.AObjectQuery<IntIdentifier> SEARCH = new ASqlTemplate.AObjectQuery<>()
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
						Config.FIELD_TABLE_NAME.sender(0), "=", "?",
					"order by",
						Config.FIELD_TABLE_VERSION, "desc",
					"fetch first row only"
				);
			}

			@Override
			protected IntIdentifier init()
			{
				return IntIdentifier.UNKNOWN;
			}
		};
	}

}
