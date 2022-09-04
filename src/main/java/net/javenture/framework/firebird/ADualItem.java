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
abstract public class ADualItem<T0, T1, K extends ADualItem.AKey<T0, T1>, I extends ADualItem<T0, T1, K, I>> // ! I extends ASimpleDualItem<V0, V1, K, I>
	extends AItem<I>
	implements IDualKeyEntity<T0, T1>
{
	//
	final private AConfig<T0, T1, K, I> CONFIG;
	final private K KEY;
	final private Object[] VALUES;


	//
	protected ADualItem(@NullDisallow K key)
	{
		Validator.notNull(key, "[key]");
		AConfig<T0, T1, K, I> config = config();
		DatabaseTable table = config.TABLE;

		CONFIG = config;
		KEY = key;
		VALUES = table.init();
		VALUES[key.field0().NUMBER] = key.id0();
		VALUES[key.field1().NUMBER] = key.id1();
	}


	//
	@Override
	abstract public AConfig<T0, T1, K, I> config();


	//
	@Override
	final public T0 id0()
	{
		return KEY.id0();
	}


	@Override
	final public T1 id1()
	{
		return KEY.id1();
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
		SqlParameters parameters = new SqlParameters(KEY.id0(), KEY.id1());

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
		load0(transaction);
		triggerAfterLoad(transaction);
	}


	private void load0(DatabaseTransaction transaction)
		throws Exception
	{
		ISqlTemplate template = CONFIG.TEMPLATES.LOAD;
		SqlParameters parameters = new SqlParameters(KEY.id0(), KEY.id1());
		ISqlResponse response = transaction.execute(template, parameters);
		IDataset dataset = response.dataset();

		if (dataset.first())
		{
			int i = 0;

			for (ADatabaseField field : CONFIG.TABLE.FIELDS0) VALUES[field.NUMBER] = dataset.get(i++);
		}
		else
		{
			throw new DatabaseException("[key] (", KEY.id0(), "; ", KEY.id1(), ") not found");
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

		{
			result = loadIfExists0(transaction);
		}

		triggerAfterLoad(transaction);


		return result;
	}


	boolean loadIfExists0(DatabaseTransaction transaction)
		throws Exception
	{
		ISqlTemplate template = CONFIG.TEMPLATES.LOAD;
		SqlParameters parameters = new SqlParameters(KEY.id0(), KEY.id1());
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
			ADualItem<T0, T1, K, I> item0 = CONFIG.instance(KEY); // ! ADualItem<T0, T1, K, I>

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
	}


	private void delete0(DatabaseTransaction transaction)
		throws Exception
	{
		CONFIG.TABLE.init(VALUES, CONFIG.TABLE.FIELDS0);
		ISqlTemplate template = CONFIG.TEMPLATES.DELETE;
		SqlParameters parameters = new SqlParameters(KEY.id0(), KEY.id1());
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

		return result;
	}


	private boolean deleteIfExist0(DatabaseTransaction transaction)
		throws Exception
	{
		CONFIG.TABLE.init(VALUES, CONFIG.TABLE.FIELDS0);
		ISqlTemplate template = CONFIG.TEMPLATES.DELETE;
		SqlParameters parameters = new SqlParameters(KEY.id0(), KEY.id1());
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
			.add(KEY.id0(), "KEY0")
			.add(KEY.id1(), "KEY1")
			.add(VALUES, "VALUES");
	}


	@Override
	@SuppressWarnings("unchecked")
	final protected <T> T get(@NullDisallow ADatabaseField<T> field)
	{
		return (T) VALUES[field.NUMBER];
	}


	@Override
	final protected <T> void set(@NullDisallow ADatabaseField<T> field, @NullAllow T value)
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
	final public ScriptObject toVueObject(Iterable<AModel> models)
	{
		AConfig config = config();
		ScriptObject result = new ScriptObject(config.TABLE.NAME);

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
			ADatabaseField key0 = table.KEYS[0];
			ADatabaseField key1 = table.KEYS[1];
			ADatabaseField[] fields = table.FIELDS;
			int length = fields.length;
			ADatabaseField[] fields0 = table.FIELDS0;
			int length0 = fields0.length;

			// exist
			{
				SqlMarkup markup = new SqlMarkup
				(
					"select",
						key0.receiver(0),
						",",
						key1.receiver(1),
					"from",
						table,
					"where",
						key0.sender(0), "=", "?",
						"and",
						key1.sender(1), "=", "?",
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
						key0.sender(0), "=", "?",
						"and",
						key1.sender(1), "=", "?",
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
						key0.sender(0), "=", "?",
						"and",
						key1.sender(1), "=", "?"
				);

				DELETE = SqlTemplate.update(markup);
			}
		}
	}


	abstract protected static class AConfig<T0, T1, K extends AKey<T0, T1>, I extends ADualItem> // static !
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
			Validator.argument(TABLE.KEYS.length == 2, "[TABLE.KEYS.length] (", TABLE.KEYS.length, ") != 2");
			JOURNAL = journal();
			CACHE = cache();
			TEMPLATES = new SqlTemplates(TABLE);
			SIGNATURE = new AtomicReference<>(null);
		}

		//
		abstract protected K key(@NullDisallow T0 id0, @NullDisallow T1 id1);
		abstract protected @NullDisallow byte[] toBytes(@NullDisallow K key);
		abstract protected @NullDisallow K fromBytes(@NullDisallow byte[] array) throws Exception;
		abstract protected I instance(@NullDisallow K key);

		//
		@Override
		final public Pattern pattern()
		{
			return Pattern.ITEM_DUAL;
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


	abstract public static class AKey<T0, T1> // ! public static, extends ASimpleDualItem.AKey<...>
		implements IDualKeyEntity.IKey<T0, T1>
	{
		//
		final private @NullDisallow T0 ID0;
		final private @NullDisallow T1 ID1;

		//
		protected AKey(@NullDisallow T0 id0, @NullDisallow T1 id1)
		{
			Validator.notNull(id0, "[id0]");
			Validator.argument(field0().defined(id0), "[id0] must be defined");
			Validator.notNull(id1, "[id1]");
			Validator.argument(field1().defined(id1), "[id1] must be defined");

			ID0 = id0;
			ID1 = id1;
		}

		//
		abstract protected ADatabaseField<T0> field0();
		abstract protected ADatabaseField<T1> field1();

		//
		@Override
		final public boolean defined()
		{
			return true;
		}

		@Override
		final public @NullDisallow T0 id0()
		{
			return ID0;
		}

		@Override
		final public @NullDisallow T1 id1()
		{
			return ID1;
		}
	}

}
