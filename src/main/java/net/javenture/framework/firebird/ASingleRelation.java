package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.IFactoryObject;
import net.javenture.framework.identifier.IntIdentifier;
import net.javenture.framework.log.Log;
import net.javenture.framework.log.LogAttachment;
import net.javenture.framework.util.ISequence;
import net.javenture.framework.util.Validator;

import java.util.concurrent.atomic.AtomicReference;


/*
	2021/02/06
 */
abstract public class ASingleRelation<T, K extends ASingleRelation.AKey<T>, R extends ASingleRelation<T, K, R>> // ! R extends ASingleIndexItem<T, K, R>
	extends ARelation<R>
	implements ISingleKeyEntity<T>
{
	//
	final private AConfig<T, K, R> CONFIG;
	final private K KEY;


	//
	protected ASingleRelation(@NullDisallow K key)
	{
		Validator.notNull(key, "[key]");

		CONFIG = config();
		KEY = key;
	}


	//
	@Override
	abstract public AConfig<T, K, R> config();


	//
	@Override
	final public T id()
	{
		return KEY.id();
	}


	@Override
	final public boolean defined()
	{
		return true;
	}


	@Override
	final public void clear()
	{
	}


	@Override
	final public ISnapshot snapshot()
	{
		return ISnapshot.BLANK;
	}


	@Override
	public boolean exists()
		throws Exception
	{
		boolean result = false;

		try (DatabaseTransaction transaction = DatabaseTransaction.read())
		{
			result = exists(transaction);

			//
			transaction.commit();
		}

		return result;
	}


	@Override
	public boolean exists(DatabaseTransaction transaction)
		throws Exception
	{
		// XXX: Cache

		boolean result = exists0(transaction);

		// XXX: Cache

		return result;
	}


	private boolean exists0(DatabaseTransaction transaction)
		throws Exception
	{
		ISqlTemplate template = CONFIG.TEMPLATES.EXISTS;
		SqlParameters parameters = new SqlParameters(KEY.id());

		return transaction.execute(template, parameters)
			.dataset()
			.exists();
	}


	@Override
	public void create()
		throws Exception
	{
		try (DatabaseTransaction transaction = DatabaseTransaction.write())
		{
			create(transaction);

			//
			transaction.commit();
		}
	}


	@Override
	public void create(DatabaseTransaction transaction)
		throws Exception
	{
		triggerBeforeCreate(transaction);

		if (CONFIG.JOURNAL)
		{
			create0(transaction);
			toJournal(transaction, JournalOperation.ENTITY_CREATE);
		}
		else
		{
			create0(transaction);
		}

		triggerAfterCreate(transaction);
	}


	void create0(DatabaseTransaction transaction)
		throws Exception
	{
		ISqlTemplate template = CONFIG.TEMPLATES.CREATE;
		SqlParameters parameters = new SqlParameters(KEY.id());
		ISqlResponse response = transaction.execute(template, parameters);
		Validator.condition(response.count() == 1, "CREATE operation failed");
	}


	@Override
	public void load()
		throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException(); // !
	}


	@Override
	public void load(DatabaseTransaction transaction)
		throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException(); // !
	}


	@Override
	public boolean loadIfExists()
		throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException(); // !
	}


	@Override
	public boolean loadIfExists(DatabaseTransaction transaction)
		throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException(); // !
	}


	@Override
	public void save()
		throws Exception
	{
		try (DatabaseTransaction transaction = DatabaseTransaction.write())
		{
			save(transaction);

			//
			transaction.commit();
		}
	}


	@Override
	public void save(DatabaseTransaction transaction)
		throws Exception
	{
		triggerBeforeSave(transaction);

		if (CONFIG.JOURNAL)
		{
			save0(transaction);
			toJournal(transaction, JournalOperation.ENTITY_SAVE);
		}
		else
		{
			save0(transaction);
		}

		triggerAfterSave(transaction);
	}


	private void save0(DatabaseTransaction transaction)
		throws Exception
	{
		ISqlTemplate template = CONFIG.TEMPLATES.SAVE;
		SqlParameters parameters = new SqlParameters(KEY.id());
		ISqlResponse response = transaction.execute(template, parameters);
		Validator.condition(response.count() == 1, "SAVE operation failed");
	}


	@Override
	public void recycle()
		throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException(); // !
	}


	@Override
	public void recycle(DatabaseTransaction transaction)
		throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException(); // !
	}


	@Override
	public boolean recycleIfExists()
		throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException(); // !
	}


	@Override
	public boolean recycleIfExists(DatabaseTransaction transaction)
		throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException(); // !
	}


	@Override
	public void delete()
		throws Exception
	{
		try (DatabaseTransaction transaction = DatabaseTransaction.write())
		{
			delete(transaction);

			//
			transaction.commit();
		}
	}


	@Override
	public void delete(DatabaseTransaction transaction)
		throws Exception
	{
		triggerBeforeDelete(transaction);

		if (CONFIG.JOURNAL)
		{
			delete0(transaction);
			toJournal(transaction, JournalOperation.ENTITY_DELETE);
		}
		else
		{
			delete0(transaction);
		}

		triggerAfterDelete(transaction);
	}


	private void delete0(DatabaseTransaction transaction)
		throws Exception
	{
		ISqlTemplate template = CONFIG.TEMPLATES.DELETE;
		SqlParameters parameters = new SqlParameters(KEY.id());
		ISqlResponse response = transaction.execute(template, parameters);
		Validator.condition(response.count() == 1, "DELETE operation failed");
	}


	@Override
	public boolean deleteIfExists()
		throws Exception
	{
		boolean result = false;

		try (DatabaseTransaction transaction = DatabaseTransaction.write())
		{
			result = deleteIfExists(transaction);

			//
			transaction.commit();
		}

		return result;
	}


	@Override
	public boolean deleteIfExists(DatabaseTransaction transaction)
		throws Exception
	{
		boolean result = false;

		//
		triggerBeforeDelete(transaction);

		if (CONFIG.JOURNAL)
		{
			result = deleteIfExist0(transaction);
			toJournal(transaction, JournalOperation.ENTITY_DELETE_IF_EXISTS);
		}
		else
		{
			result = deleteIfExist0(transaction);
		}

		triggerAfterDelete(transaction);

		return result;
	}


	private boolean deleteIfExist0(DatabaseTransaction transaction)
		throws Exception
	{
		ISqlTemplate template = CONFIG.TEMPLATES.DELETE;
		SqlParameters parameters = new SqlParameters(KEY.id());
		ISqlResponse response = transaction.execute(template, parameters);

		return response.count() == 1;
	}


	protected void triggerBeforeCreate(DatabaseTransaction transaction)
		throws Exception
	{
	}


	protected void triggerAfterCreate(DatabaseTransaction transaction)
		throws Exception
	{
	}


	protected void triggerBeforeSave(DatabaseTransaction transaction)
		throws Exception
	{
	}


	protected void triggerAfterSave(DatabaseTransaction transaction)
		throws Exception
	{
	}


	protected void triggerBeforeDelete(DatabaseTransaction transaction)
		throws Exception
	{
	}


	protected void triggerAfterDelete(DatabaseTransaction transaction)
		throws Exception
	{
	}


	private void toJournal(@NullDisallow DatabaseTransaction transaction, @NullDisallow JournalOperation operation)
		throws Exception
	{
		JournalManager.write(transaction, operation, CONFIG.signature(), CONFIG.toBytes(KEY), null);
	}


	@Override
	final public LogAttachment log()
	{
		return new LogAttachment()
			.add(KEY.id(), "KEY");
	}


	//
	protected static <O extends IFactoryObject, S extends ISequence> void toSequence(AConfig config, @NullDisallow ISequence<O, S> destination)
		throws Exception
	{
		try (DatabaseTransaction transaction = DatabaseTransaction.read())
		{
			toSequence(config, destination, transaction);

			//
			transaction.commit();
		}
	}


	protected static <O extends IFactoryObject, S extends ISequence> void toSequence(AConfig config, @NullDisallow ISequence<O, S> destination, DatabaseTransaction transaction)
		throws Exception
	{
		ISqlTemplate template = config.TEMPLATES.SEQUENCE;
		IColumnSqlProcessor processor = IColumnSqlProcessor.fill(destination);
		transaction.execute(template, processor);
	}


	//
	final private static class SqlTemplates
	{
		//
		final private ISqlTemplate EXISTS;
		final private ISqlTemplate CREATE;
		final private ISqlTemplate SAVE;
		final private ISqlTemplate DELETE;
		final private ISqlTemplate SEQUENCE;

		//
		private SqlTemplates(DatabaseTable table)
		{
			ADatabaseField key = table.KEYS[0];

			// exists
			{
				SqlMarkup markup = new SqlMarkup
				(
					"select",
						key.receiver(0),
					"from",
						table,
					"where",
						key.sender(0), "=", "?"
				);

				EXISTS = SqlTemplate.query(markup);
			}

			// create
			{
				SqlMarkup markup = new SqlMarkup
				(
					"insert",
					"into",
						table,
					"(",
						key.sender(0),
					")",
					"values",
					"(",
						"?",
					")"
				);

				CREATE = SqlTemplate.update(markup);
			}

			// save
			{
				SqlMarkup markup = new SqlMarkup
				(
					"update or insert",
					"into",
						table,
					"(",
						key.sender(0),
					")",
					"values",
					"(",
						"?",
					")"
				);

				SAVE = SqlTemplate.update(markup);
			}

			// delete
			{
				SqlMarkup markup = new SqlMarkup
				(
					"delete",
					"from",
						table,
					"where",
						key.sender(0), "=", "?"
				);

				DELETE = SqlTemplate.update(markup);
			}

			// sequence
			{
				SqlMarkup markup = new SqlMarkup
				(
					"select",
						key.receiver(0),
					"from",
						table,
					"order by",
						key
				);

				SEQUENCE = SqlTemplate.query(markup);
			}
		}
	}


	abstract protected static class AConfig<T, K extends AKey<T>, R extends ASingleRelation> // static !
		implements IConfig
	{
		//
		final Log LOG;
		final DatabaseTable TABLE;
		final boolean JOURNAL;
		final boolean CACHE;
		final private SqlTemplates TEMPLATES;
		final private AtomicReference<IntIdentifier> SIGNATURE;

		//
		protected AConfig()
		{
			LOG = log();
			TABLE = table();
			JOURNAL = journal();
			CACHE = cache();
			TEMPLATES = new SqlTemplates(TABLE);
			SIGNATURE = new AtomicReference<>(null);
		}

		//
		abstract protected @NullDisallow byte[] toBytes(@NullDisallow K key);
		abstract protected @NullDisallow K fromBytes(@NullDisallow byte[] array) throws Exception;
		abstract protected R instance(@NullDisallow K key);
		abstract protected ISequence<T, ?> sequence();

		//
		@Override
		final public Pattern pattern()
		{
			return Pattern.RELATION_SINGLE;
		}

		@Override
		final public void init(DatabaseTransaction transaction)
			throws Exception
		{
			// XXX: trigger only once ?


			if (JOURNAL)
			{
				IntIdentifier idDatabaseSignature = DatabaseSignatureItem.search(this);
				Validator.condition(idDatabaseSignature.defined(), "signature not defined");
				Validator.condition(SIGNATURE.compareAndSet(null, idDatabaseSignature), "signature already assigned");
			}
		}

		@Override
		final public @NullAllow IntIdentifier signature()
		{
			return SIGNATURE.get();
		}
	}


	abstract public static class AKey<T> // ! public static, extends ASingleRelation.AKey<...>
		implements ISingleKeyEntity.IKey<T>
	{
		//
		final private @NullDisallow T ID;

		//
		protected AKey(@NullDisallow T id)
		{
			Validator.notNull(id, "[id]");
			Validator.argument(field().defined(id), "[id] must be defined");

			ID = id;
		}

		//
		abstract protected ADatabaseField<T> field();

		//
		@Override
		final public boolean defined()
		{
			return true;
		}

		@Override
		final public @NullDisallow T id()
		{
			return ID;
		}
	}

}
