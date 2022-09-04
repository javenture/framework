package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.exception.DatabaseException;
import net.javenture.framework.identifier.IntIdentifier;
import net.javenture.framework.log.Log;
import net.javenture.framework.log.LogAttachment;
import net.javenture.framework.netty.NettyParameters;
import net.javenture.framework.netty.NettyRequest;
import net.javenture.framework.script.ScriptProperty;
import net.javenture.framework.util.Chain;
import net.javenture.framework.util.Validator;
import net.javenture.framework.model.AModel;
import net.javenture.framework.script.ScriptObject;

import java.util.concurrent.atomic.AtomicReference;


/*
	2021/06/02
 */
abstract public class ASingleItem<T, K extends ASingleItem.AKey<T>, I extends ASingleItem<T, K, I>> // ! I extends ASimpleSingleItem<T, K, I>
	extends AItem<I>
	implements ISingleKeyEntity<T>
{
	//
	final private AConfig<T, K, I> CONFIG;
	final private K KEY;
	final private Object[] VALUES;


	//
	protected ASingleItem(@NullDisallow K key)
	{
		Validator.notNull(key, "[key]");
		AConfig<T, K, I> config = config();
		DatabaseTable table = config.TABLE;

		CONFIG = config;
		KEY = key;
		VALUES = table.init();
		VALUES[key.field().NUMBER] = key.id();
	}


	//
	@Override
	abstract public AConfig<T, K, I> config();


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
		CONFIG.TABLE.init(VALUES, CONFIG.TABLE.FIELDS0);
	}


	@Override
	final public ISnapshot snapshot()
	{
		return new ItemSnapshot(this, () -> VALUES);
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
		boolean result = false;

		// XXX: Cache ?


		result = exists0(transaction);


		return result;
	}


	private boolean exists0(DatabaseTransaction transaction)
		throws Exception
	{
		ISqlTemplate template = CONFIG.TEMPLATES.EXIST;
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
			toJournal(transaction, JournalOperation.ENTITY_CREATE, ItemUtil.toBytes(VALUES, CONFIG.TABLE.FIELDS0));
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
		ItemUtil.validate(CONFIG.TABLE.FIELDS0, VALUES);
		ISqlTemplate template = CONFIG.TEMPLATES.CREATE;
		SqlParameters parameters = new SqlParameters(VALUES);
		ISqlResponse response = transaction.execute(template, parameters);
		Validator.condition(response.count() == 1, "CREATE operation failed");
	}


	@Override
	public void load()
		throws Exception
	{
		try (DatabaseTransaction transaction = DatabaseTransaction.read())
		{
			load(transaction);

			//
			transaction.commit();
		}
	}


	@Override
	public void load(DatabaseTransaction transaction)
		throws Exception
	{
		// XXX: Object[] objects = Cache.get(ITEM_CONFIG.CLASS, IDENTIFIER)
		// XXX: Cache.put(ITEM_CONFIG.CLASS, IDENTIFIER, objects)

		// XXX: CacheRequest & CacheResponse


		triggerBeforeLoad(transaction);

		{
			load0(transaction);
		}

		triggerAfterLoad(transaction);


		// XXX: cache
	}


	void load0(DatabaseTransaction transaction)
		throws Exception
	{
		ISqlTemplate template = CONFIG.TEMPLATES.LOAD;
		SqlParameters parameters = new SqlParameters(KEY.id());
		ISqlResponse response = transaction.execute(template, parameters);
		IDataset dataset = response.dataset();

		if (dataset.first())
		{
			int i = 0;

			for (ADatabaseField field : CONFIG.TABLE.FIELDS0) VALUES[field.NUMBER] = dataset.get(i++);
		}
		else
		{
			throw new DatabaseException("[key] (", KEY.id(), ") not found");
		}
	}


	@Override
	public boolean loadIfExists()
		throws Exception
	{
		boolean result = false;

		try (DatabaseTransaction transaction = DatabaseTransaction.read())
		{
			result = loadIfExists(transaction);

			//
			transaction.commit();
		}

		return result;
	}


	@Override
	public boolean loadIfExists(DatabaseTransaction transaction)
		throws Exception
	{
		boolean result = false;


		// XXX: Object[] objects = Cache.get(ITEM_CONFIG.CLASS, IDENTIFIER)
		// XXX: Cache.put(ITEM_CONFIG.CLASS, IDENTIFIER, objects)

		// XXX: CacheRequest & CacheResponse


		triggerBeforeLoad(transaction);
		result = loadIfExists0(transaction);
		triggerAfterLoad(transaction);

		return result;
	}


	private boolean loadIfExists0(DatabaseTransaction transaction)
		throws Exception
	{
		ISqlTemplate template = CONFIG.TEMPLATES.LOAD;
		SqlParameters parameters = new SqlParameters(KEY.id());
		ISqlResponse response = transaction.execute(template, parameters);
		IDataset dataset = response.dataset();

		if (dataset.first())
		{
			int i = 0;

			for (ADatabaseField field : CONFIG.TABLE.FIELDS0) VALUES[field.NUMBER] = dataset.get(i++);

			return true;
		}
		else
		{
			return false;
		}
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
			ASingleItem<T, K, I> item0 = CONFIG.instance(KEY); // ! ASingleItem<T, K, I>

			if (item0.loadIfExists0(transaction))
			{
				Chain<ADatabaseField> difference = ItemUtil.difference(CONFIG.TABLE.FIELDS0, this.VALUES, item0.VALUES);

				if (difference.exists())
				{
					save0(transaction);
					toJournal(transaction, JournalOperation.ENTITY_SAVE, ItemUtil.toBytes(VALUES, difference));
				}
				else
				{
					toJournal(transaction, JournalOperation.ENTITY_SAVE, null);
				}
			}
			else
			{
				save0(transaction);
				toJournal(transaction, JournalOperation.ENTITY_SAVE, ItemUtil.toBytes(VALUES, CONFIG.TABLE.FIELDS0));
			}
		}
		else
		{
			save0(transaction);
		}

		triggerAfterSave(transaction);
	}


	void save0(DatabaseTransaction transaction)
		throws Exception
	{
		ItemUtil.validate(CONFIG.TABLE.FIELDS0, VALUES);
		ISqlTemplate template = CONFIG.TEMPLATES.SAVE;
		SqlParameters parameters = new SqlParameters(VALUES);
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
		load0(transaction); // !
		triggerBeforeDelete(transaction);

		if (CONFIG.JOURNAL)
		{
			delete0(transaction);
			toJournal(transaction, JournalOperation.ENTITY_DELETE, null);
		}
		else
		{
			delete0(transaction);
		}

		triggerAfterDelete(transaction);


		// XXX: cache ?
	}


	private void delete0(DatabaseTransaction transaction)
		throws Exception
	{
		CONFIG.TABLE.init(VALUES, CONFIG.TABLE.FIELDS0);
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
		loadIfExists0(transaction); // !
		triggerBeforeDelete(transaction);

		if (CONFIG.JOURNAL)
		{
			result = deleteIfExist0(transaction);
			toJournal(transaction, JournalOperation.ENTITY_DELETE_IF_EXISTS, null);
		}
		else
		{
			result = deleteIfExist0(transaction);
		}

		triggerAfterDelete(transaction);


		// XXX: cache ?

		return result;
	}


	private boolean deleteIfExist0(DatabaseTransaction transaction)
		throws Exception
	{
		CONFIG.TABLE.init(VALUES, CONFIG.TABLE.FIELDS0);
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


	protected void triggerBeforeLoad(DatabaseTransaction transaction)
		throws Exception
	{
	}


	protected void triggerAfterLoad(DatabaseTransaction transaction)
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


	private void toJournal(@NullDisallow DatabaseTransaction transaction, @NullDisallow JournalOperation operation, @NullAllow byte[] data)
		throws Exception
	{
		JournalManager.write(transaction, operation, CONFIG.signature(), CONFIG.toBytes(KEY), data);
	}


	@Override
	final public LogAttachment log()
	{
		return new LogAttachment()
			.add(KEY.id(), "KEY")
			.add(VALUES, "VALUES");
	}


	@Override
	@SuppressWarnings("unchecked")
	final protected <V> V get(@NullDisallow ADatabaseField<V> field)
	{
		return (V) VALUES[field.NUMBER];
	}


	@Override
	final protected <V> void set(@NullDisallow ADatabaseField<V> field, @NullAllow V value)
	{
		Validator.argument(!field.KEY, "[field] (", field, "): modification unavailable for field with KEY option");
		VALUES[field.NUMBER] = field.prepare(value);
	}









	@Deprecated
	@SuppressWarnings("unchecked")
	final public void parseNettyRequest(NettyRequest source, Iterable<AModel> models)
	{
		NettyParameters parameters = source.parameters();

		for (AModel model : models) set(model.field(), model.parse(parameters).VALUE);
	}


	@Deprecated
	@SuppressWarnings("unchecked")
	final public ScriptObject toScriptObject(Iterable<AModel> models)
	{
		ScriptObject result = new ScriptObject(CONFIG.TABLE.NAME);

		for (AModel model : models)
		{
			ADatabaseField field = model.field();
			Object value = get(field);
			ScriptProperty property = new ScriptProperty(model.name(), model.toScriptEntry(value));
			result.add(property);
		}

		return result;
	}









	//
	final private static class SqlTemplates
	{
		//
		final private ISqlTemplate EXIST;
		final private ISqlTemplate LOAD;
		final private ISqlTemplate CREATE;
		final private ISqlTemplate SAVE;
		final private ISqlTemplate DELETE;

		//
		private SqlTemplates(DatabaseTable table)
		{
			ADatabaseField key = table.KEYS[0];
			ADatabaseField[] fields = table.FIELDS;
			int length = fields.length;
			ADatabaseField[] fields0 = table.FIELDS0;
			int length0 = fields0.length;

			// exist
			{
				SqlMarkup markup = new SqlMarkup
				(
					"select",
						key.receiver(0),
					"from",
						table,
					"where",
						key.sender(0), "=", "?",
					"fetch first row only"
				);

				EXIST = SqlTemplate.query(markup);
			}

			// load
			{
				SqlMarkup markup = new SqlMarkup();
				int i = 0;

				markup.add
				(
					"select"
				);

				for (ADatabaseField field : fields0)
				{
					markup.add(field.receiver(i), DatabaseUtil.comma(i, length0));
					i++;
				}

				markup.add
				(
					"from",
						table,
					"where",
						key.sender(0), "=", "?",
					"fetch first row only"
				);

				LOAD = SqlTemplate.query(markup);
			}

			// create
			{
				SqlMarkup markup = new SqlMarkup();

				markup.add
				(
					"insert",
					"into",
						table,
					"("
				);

				for (ADatabaseField field : fields) markup.add(field.sender(), DatabaseUtil.comma(field.NUMBER, length));

				markup.add
				(
					")",
					"values",
					"("
				);

				for (ADatabaseField field : fields) markup.add("?", DatabaseUtil.comma(field.NUMBER, length));

				markup.add
				(
					")"
				);

				CREATE = SqlTemplate.update(markup);
			}

			// save
			{
				SqlMarkup markup = new SqlMarkup();

				markup.add
				(
					"update or insert",
					"into",
						table,
					"("
				);

				for (ADatabaseField field : fields) markup.add(field.sender(), DatabaseUtil.comma(field.NUMBER, length));

				markup.add
				(
					")",
					"values",
					"("
				);

				for (ADatabaseField field : fields) markup.add("?", DatabaseUtil.comma(field.NUMBER, length));

				markup.add
				(
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
		}
	}


	abstract protected static class AConfig<T, K extends AKey<T>, I extends ASingleItem> // static !
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
			Validator.argument(TABLE.KEYS.length == 1, "[TABLE.KEYS.length] (", TABLE.KEYS.length, ") != 1");
			JOURNAL = journal();
			CACHE = cache();
			TEMPLATES = new SqlTemplates(TABLE);
			SIGNATURE = new AtomicReference<>(null);
		}

		//
		abstract protected K key(@NullDisallow T id);
		abstract protected @NullDisallow byte[] toBytes(@NullDisallow K key);
		abstract protected @NullDisallow K fromBytes(@NullDisallow byte[] array) throws Exception;
		abstract protected I instance(@NullDisallow K key);

		//
		@Override
		final public Pattern pattern()
		{
			return Pattern.ITEM_SINGLE;
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


	abstract public static class AKey<T> // ! public static, extends ASimpleSingleItem.AKey<...>
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
