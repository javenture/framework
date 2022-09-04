package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.log.Log;
import net.javenture.framework.model.AConstModel;
import net.javenture.framework.util.ConstUtil;
import net.javenture.framework.util.IConst;
import net.javenture.framework.util.LongSequence;
import net.javenture.framework.util.LongUtil;
import net.javenture.framework.util.Uuid;


/*
	2020/01/05
 */
final public class JournalQueueItem
	extends ASingleItem<Long, JournalQueueItem.Key, JournalQueueItem>
{
	//
	final public static JournalQueueItem.Config CONFIG = new JournalQueueItem.Config();

	enum State
		implements IConst<State>
	{
		//
		UNKNOWN(-1),
		PROCESS(0),
		COMPLETE(1);

		//
		final public static Factories<State> FACTORIES = ConstUtil.factories(values(), object -> object instanceof State);

		//
		final private int ID;

		//
		State(int id)
		{
			ID = id;
		}

		//
		@Override
		public boolean defined()
		{
			return this.ID != UNKNOWN.ID;
		}

		@Override
		public int id()
		{
			return ID;
		}

		@Override
		public Factories<State> factories()
		{
			return FACTORIES;
		}

		@Override
		public boolean equals(State value)
		{
			return this == value;
		}

		@Override
		public String toString()
		{
			return "" + ID;
		}

		//
		final static class DatabaseField
			extends AConstDatabaseField<State>
		{
			//
			final private State.Model MODEL;

			//
			DatabaseField(String table, int number, String name, @NullAllow State init, Option... options)
			{
				super(table, number, name, init, FACTORIES, options);

				MODEL = new State.Model(this, true, false);
			}

			//
			@Override
			public State.Model model()
			{
				return MODEL;
			}
		}

		final static class Model
			extends AConstModel<State>
		{
			Model(State.DatabaseField field, boolean quotation, boolean trim)
			{
				super(field, quotation, trim);
			}

			Model(@NullDisallow String name, boolean nullable, boolean quotation, boolean trim, @NullAllow State init, @NullDisallow String... objects)
			{
				super(name, nullable, quotation, trim, init, FACTORIES, objects);
			}
		}
	}


	//
	JournalQueueItem(long id)
	{
		this(new JournalQueueItem.Key(id));
	}


	private JournalQueueItem(JournalQueueItem.Key key)
	{
		super(key);
	}


	//
	@Override
	public JournalQueueItem.Config config()
	{
		return CONFIG;
	}


	//
	static long last(DatabaseTransaction transaction)
		throws Exception
	{
		return JournalQueueItem.SqlTemplates.LAST.execute(transaction);
	}


	static LongSequence processed(DatabaseTransaction transaction)
		throws Exception
	{
		LongSequence result = new LongSequence();
		IColumnSqlProcessor<Long> processor = IColumnSqlProcessor.fill(result);
		transaction.execute(SqlTemplates.LIST_PROCESS, processor);

		return result;
	}


	/*
		get/set
	 */
	long getId()
	{
		return get(Config.FIELD_ID);
	}


	Uuid getTransactionUuid()
	{
		return get(Config.FIELD_TRANSACTION_UUID);
	}


	void setTransactionUuid(Uuid value)
	{
		set(Config.FIELD_TRANSACTION_UUID, value);
	}


	State getState()
	{
		return get(Config.FIELD_STATE);
	}


	void setState(State value)
	{
		set(Config.FIELD_STATE, value);
	}


	/*
		config
	*/
	final public static class Config
		extends ASingleItem.AConfig<Long, JournalQueueItem.Key, JournalQueueItem>
	{
		//
		final public static String NAME_TABLE = "JOURNAL_QUEUE";

		final public static LongDatabaseField FIELD_ID = new LongDatabaseField
		(
			NAME_TABLE,
			0,
			"ID",
			(long) -1,
			ADatabaseField.Option.KEY,
			ADatabaseField.Option.STRICT
		);

		final public static Uuid.DatabaseField FIELD_TRANSACTION_UUID = new Uuid.DatabaseField
		(
			NAME_TABLE,
			1,
			"TRANSACTION_UUID",
			Uuid.UNKNOWN
			// XXX: ADatabaseField.Option.STRICT
		);

		final public static State.DatabaseField FIELD_STATE = new State.DatabaseField
		(
			NAME_TABLE,
			2,
			"STATE",
			State.UNKNOWN,
			ADatabaseField.Option.STRICT
		);

		final public static DatabaseFields FIELDS = new DatabaseFields
		(
			FIELD_ID,
			FIELD_TRANSACTION_UUID,
			FIELD_STATE
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
		protected JournalQueueItem.Key key(@NullDisallow Long id)
		{
			return new JournalQueueItem.Key(id);
		}

		@Override
		protected @NullDisallow byte[] toBytes(@NullDisallow JournalQueueItem.Key key)
		{
			long id = key.id();

			return LongUtil.bytes(id);
		}

		@Override
		protected @NullDisallow JournalQueueItem.Key fromBytes(@NullDisallow byte[] array)
			throws Exception
		{
			long id = LongUtil.parse(array, 0);

			return new JournalQueueItem.Key(id);
		}

		@Override
		protected JournalQueueItem instance(@NullDisallow JournalQueueItem.Key key)
		{
			return new JournalQueueItem(key);
		}
	}


	final static class Key
		extends ASingleItem.AKey<Long>
	{
		//
		private Key(@NullDisallow Long id)
		{
			super(id);
		}

		//
		@Override
		protected LongDatabaseField field()
		{
			return JournalQueueItem.Config.FIELD_ID;
		}
	}


	final private static class SqlTemplates
	{
		final private static ASqlTemplate.AObjectQuery<Long> LAST = new ASqlTemplate.AObjectQuery<>()
		{
			@Override
			protected SqlMarkup _markup()
			{
				return new SqlMarkup
				(
					"select",
						JournalQueueItem.Config.FIELD_ID.receiver(0),
					"from",
						JournalQueueItem.Config.TABLE,
					"order by",
						JournalQueueItem.Config.FIELD_ID, "desc",
					"fetch first row only"
				);
			}

			@Override
			protected Long init()
			{
				return (long) 0; // ! 0
			}
		};

		final private static ASqlTemplate.AQuery LIST_PROCESS = new ASqlTemplate.AQuery()
		{
			@Override
			protected SqlMarkup _markup()
			{
				return new SqlMarkup
				(
					"select",
						JournalQueueItem.Config.FIELD_ID.receiver(0),
					"from",
						JournalQueueItem.Config.TABLE,
					"where",
						JournalQueueItem.Config.FIELD_STATE, "=", State.PROCESS,
					"order by",
						JournalQueueItem.Config.FIELD_ID
				);
			}
		};
	}

}
