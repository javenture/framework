package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.exception.DatabaseException;
import net.javenture.framework.identifier.IntIdentifier;
import net.javenture.framework.json.JsonContainer;
import net.javenture.framework.json.JsonDocument;
import net.javenture.framework.json.JsonObject;
import net.javenture.framework.log.Log;
import net.javenture.framework.log.LogAttachment;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.netty.NettyParameters;
import net.javenture.framework.netty.NettyRequest;
import net.javenture.framework.script.ScriptProperty;
import net.javenture.framework.model.AModel;
import net.javenture.framework.script.ScriptObject;
import net.javenture.framework.util.Chain;
import net.javenture.framework.util.IParser;
import net.javenture.framework.util.Validator;

import java.util.concurrent.atomic.AtomicReference;


/*
	2021/06/02
 */
abstract public class AGeneratedSingleItem<T, K extends AGeneratedSingleItem.AKey<T>, I extends AGeneratedSingleItem<T, K, I>> // ! I extends AGeneralSingleItem<T, K, I>
	extends AItem<I>
	implements ISingleKeyEntity<T>
{

/*
	final public static IByteFactory<AGeneratedSingleItem> FACTORY_BYTE = new IByteFactory<AGeneratedSingleItem>()
	{
		@Override
		public void toStream(@NullAllow AGeneratedSingleItem object, ByteOutputStream destination)
			throws Exception
		{
			if (IByteFactory.setNullHeader(object, destination))
			{




				IntUtil.bytes(object.SIZE, destination);
				IntArrayUtil.FACTORY_BYTE.toStream(object.ARRAY, destination);
			}
		}

		@Override
		public @NullAllow AGeneratedSingleItem fromStream(ByteInputStream source)
			throws Exception
		{
			if (IByteFactory.getNullHeader(source))
			{



				int size = IntUtil.parse(source);
				Validator.condition(size >= 0, "[size] (", size, ") has invalid value");
				int[] array = IntArrayUtil.FACTORY_BYTE.fromStream(source);

				return new BitHolder(size, array);                                             // XXX: check size & array
			}
			else
			{
				return null;
			}
		}
	};

	final public static Factories<BitHolder> FACTORIES = new Factories<>(FACTORY_BYTE);
*/






	//
	final private AConfig<T, K, I> CONFIG;
	final private K KEY;
	final private Object[] VALUES;


	//
	protected AGeneratedSingleItem(@NullDisallow K key)
	{
		Validator.notNull(key, "[key]");
		AConfig<T, K, I> config = config();
		DatabaseTable table = config.TABLE;

		CONFIG = config;
		KEY = key;
		VALUES = table.init();
		VALUES[key.field().NUMBER] = key.id();


		// XXX: CONFIG.PERMISSION -> ITEM.PERMISSION ?
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
		return KEY.defined();
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

		// cache.get
		{
			// XXX: ?

			// return UNKNOWN|EXIST|NOT_EXIST
		}


		// database
		result = exists0(transaction);



		// cache.set                          // XXX: async !
		/*
		{
			// XXX: ?

			// return UNKNOWN|EXIST|NOT_EXIST
		}
		*/

		return result;
	}


	private boolean exists0(DatabaseTransaction transaction)
		throws Exception
	{
		Validator.condition(KEY.defined(), "[key] must be defined for EXISTS operation");
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
			create0(transaction); // ! #1
			toJournal(transaction, JournalOperation.ENTITY_CREATE, ItemUtil.toBytes(VALUES, CONFIG.TABLE.FIELDS0)); // ! #2
		}
		else
		{
			create0(transaction);
		}

		triggerAfterCreate(transaction);
	}


	private void create0(DatabaseTransaction transaction)
		throws Exception
	{
		Validator.condition(!KEY.defined(), "[key] must be undefined for CREATE operation");
		IKeyGenerator<T> generator = CONFIG.GENERATOR;
		Validator.condition(generator.activity(), "generator disabled");

		DatabaseTable table = CONFIG.TABLE;
		ItemUtil.validate(table.FIELDS0, VALUES);
		Object[] values = new Object[table.COLUMN + 1];
		System.arraycopy(VALUES, 0, values, 0, table.COLUMN);
		ADatabaseField<T> fieldKey = KEY.field();

		while (true)
		{
			T value = generator.next();

			if (fieldKey.defined(value))
			{
				values[fieldKey.NUMBER] = value;
				values[table.COLUMN] = value;

				ISqlTemplate template = CONFIG.TEMPLATES.CREATE;
				SqlParameters parameters = new SqlParameters(values);
				ISqlResponse response = transaction.execute(template, parameters);

				if (response.count() == 1)
				{
					KEY.id(value);
					VALUES[fieldKey.NUMBER] = value;

					break;
				}
			}
		}
	}


	@Override
	public void load()                                                             // XXX: how to support connection to more than one database at time
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
		//
/*
		DatabaseTable table = CONFIG.TABLE;
		int column = table.COLUMN;

		// cache
		if (CONFIG.CACHE)                                                                            // XXX: cache & trigger => inside transaction !
		{
			// XXX: Object[] objects = Cache.get(ITEM_CONFIG.CLASS, IDENTIFIER)
			// XXX: Cache.put(ITEM_CONFIG.CLASS, IDENTIFIER, objects)
			// XXX: CacheRequest & CacheResponse


			try
			{
				// Cache.get
				{
					// XXX: instance, signature, remaining == 0

					byte[] hash0 = new byte[0];                           // XXX: hash check -> CACHE
					byte[] data = new byte[0];

					ByteInputStream source = new ByteInputStream(data);
					byte[] hash = source.murmur3hash().getBytesValue();
					Validator.condition(Arrays.equals(hash, hash0), "");                           // XXX: ?

					//
					Object[] values = table.FACTORY.fromStream(source);                       // XXX: void fromStream(source, Object[] destination)            // XXX: ItemUtil ?
					System.arraycopy(values, 0, VALUES, 0, column);

					//return;                                                                                   // XXX: ?

					// XXX: set EXIST = true
					// cache hold info: EXIST|NOT_EXIST ?

				}
			}
			catch (Exception e)
			{
			}
		}
*/

		triggerBeforeLoad(transaction);
		load0(transaction);
		triggerAfterLoad(transaction);


		// XXX: Cache.set
/*
		if (CONFIG.CACHE)
		{
			// XXX: instance, signature

			try (ByteOutputStream destination = new ByteOutputStream())
			{
				table.FACTORY.toStream(VALUES, destination);
				byte[] hash = destination.murmur3hash().getBytesValue();                   // XXX: hash check -> CACHE
			}
		}
*/
	}


	private void load0(DatabaseTransaction transaction)
		throws Exception
	{
		Validator.condition(KEY.defined(), "[key] must be defined for LOAD operation");
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

		//
		triggerBeforeLoad(transaction);
		result = loadIfExists0(transaction);
		triggerAfterLoad(transaction);

		return result;
	}


	private boolean loadIfExists0(DatabaseTransaction transaction)
		throws Exception
	{
		Validator.condition(KEY.defined(), "[key] must be defined for LOAD operation");
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
			AGeneratedSingleItem<T, K, I> item = CONFIG.instance(KEY); // ! AGeneratedSingleItem<T, K, I>
			item.load0(transaction);
			Chain<ADatabaseField> difference = ItemUtil.difference(CONFIG.TABLE.FIELDS0, this.VALUES, item.VALUES);

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
		}

		triggerAfterSave(transaction);


		// XXX: cache
	}


	private void save0(DatabaseTransaction transaction)
		throws Exception
	{
		Validator.condition(KEY.defined(), "[key] must be defined for SAVE operation");
		DatabaseTable table = CONFIG.TABLE;
		Object[] values = new Object[table.COLUMN];

		{
			int i = 0;

			for (ADatabaseField field : table.FIELDS0)
			{
				Object value = VALUES[field.NUMBER];
				field.validate(value);
				values[i++] = value;
			}

			values[i] = KEY.id();
		}

		//
		ISqlTemplate template = CONFIG.TEMPLATES.SAVE;
		SqlParameters parameters = new SqlParameters(values);
		ISqlResponse response = transaction.execute(template, parameters);
		Validator.condition(response.count() == 1, "SAVE operation failed");
	}


	@Override
	public void recycle()
		throws Exception
	{
		try (DatabaseTransaction transaction = DatabaseTransaction.write())
		{
			recycle(transaction);

			//
			transaction.commit();
		}
	}


	@Override
	public void recycle(DatabaseTransaction transaction)
		throws Exception
	{
		triggerBeforeRecycle(transaction);

		if (CONFIG.JOURNAL)
		{
			recycle0(transaction);
			toJournal(transaction, JournalOperation.ENTITY_RECYCLE, null);
		}
		else
		{
			recycle0(transaction);
		}

		triggerAfterRecycle(transaction);


		// XXX: cache ?
	}


	private void recycle0(DatabaseTransaction transaction)
		throws Exception
	{
		Validator.condition(KEY.defined(), "[key] must be defined for RECYCLE operation");
		DatabaseTable table = CONFIG.TABLE;
		Object[] values = new Object[table.COLUMN];

		{
			int i = 0;

			for (ADatabaseField field : table.FIELDS0)
			{
				Object init = field.init();
				values[i++] = init;
				VALUES[field.NUMBER] = init; // !
			}

			values[i] = KEY.id(); // !
		}

		//
		ISqlTemplate template = CONFIG.TEMPLATES.SAVE;
		SqlParameters parameters = new SqlParameters(values);
		ISqlResponse response = transaction.execute(template, parameters);
		Validator.condition(response.count() == 1, "RECYCLE operation failed");
	}


	@Override
	public boolean recycleIfExists()
		throws Exception
	{
		boolean result = false;

		try (DatabaseTransaction transaction = DatabaseTransaction.write())
		{
			result = recycleIfExists(transaction);

			//
			transaction.commit();
		}

		return result;
	}


	@Override
	public boolean recycleIfExists(DatabaseTransaction transaction)
		throws Exception
	{
		boolean result = false;

		//
		triggerBeforeRecycle(transaction);

		if (CONFIG.JOURNAL)
		{
			result = recycleIfExist0(transaction);
			toJournal(transaction, JournalOperation.ENTITY_RECYCLE_IF_EXISTS, null);
		}
		else
		{
			result = recycleIfExist0(transaction);
		}

		triggerAfterRecycle(transaction);


		// XXX: cache set not exist ?

		return result;
	}


	private boolean recycleIfExist0(DatabaseTransaction transaction)
		throws Exception
	{
		Validator.condition(KEY.defined(), "[key] must be defined for RECYCLE operation");
		DatabaseTable table = CONFIG.TABLE;
		Object[] values = new Object[table.COLUMN];

		{
			int i = 0;

			for (ADatabaseField field : table.FIELDS0)
			{
				Object init = field.init();
				values[i++] = init;
				VALUES[field.NUMBER] = init; // !
			}

			values[i] = KEY.id();
		}

		//
		ISqlTemplate template = CONFIG.TEMPLATES.SAVE;
		SqlParameters parameters = new SqlParameters(values);
		ISqlResponse response = transaction.execute(template, parameters);

		return response.count() == 1;
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
			toJournal(transaction, JournalOperation.ENTITY_DELETE, null); // ! #1
			delete0(transaction); // ! #2
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
		Validator.condition(KEY.defined(), "[key] must be defined for DELETE operation");
		ISqlTemplate template = CONFIG.TEMPLATES.DELETE;
		SqlParameters parameters = new SqlParameters(KEY.id());
		ISqlResponse response = transaction.execute(template, parameters);
		Validator.condition(response.count() == 1, "DELETE operation failed");

		KEY.init(); // !
		CONFIG.TABLE.init(VALUES);
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
			toJournal(transaction, JournalOperation.ENTITY_DELETE_IF_EXISTS, null); // ! #1
			result = deleteIfExist0(transaction); // ! #2
		}
		else
		{
			result = deleteIfExist0(transaction);
		}

		triggerAfterDelete(transaction);


		// XXX: cache set not exist

		return result;
	}


	private boolean deleteIfExist0(DatabaseTransaction transaction)
		throws Exception
	{
		Validator.condition(KEY.defined(), "[key] must be defined for DELETE operation");
		ISqlTemplate template = CONFIG.TEMPLATES.DELETE;
		SqlParameters parameters = new SqlParameters(KEY.id());
		ISqlResponse response = transaction.execute(template, parameters);

		KEY.init(); // !
		CONFIG.TABLE.init(VALUES);

		return response.count() == 1;
	}


	private void allocate0(DatabaseTransaction transaction)
		throws Exception
	{
		ISqlTemplate template = CONFIG.TEMPLATES.ALLOCATE;
		SqlParameters parameters = new SqlParameters(VALUES);
		ISqlResponse response = transaction.execute(template, parameters);
		Validator.condition(response.count() == 1, "ALLOCATE operation failed");
	}


	protected void triggerBeforeCreate(DatabaseTransaction transaction)
		throws Exception
	{
		// ! KEY is undefined
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


	protected void triggerBeforeRecycle(DatabaseTransaction transaction)
		throws Exception
	{
	}


	protected void triggerAfterRecycle(DatabaseTransaction transaction)
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
		// ! KEY is undefined
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


	@SuppressWarnings("unchecked")
	final public void toJson(JsonContainer destination)                                                                                     // XXX: ItemUtil | JsonFactory ?
	{
		ScriptObject object = new ScriptObject(CONFIG.TABLE.NAME);                   // XXX: ScriptObject -> ScriptArray

		for (ADatabaseField field : CONFIG.TABLE.FIELDS0)
		{
			AModel model = field.model();
			Object value = get(field);
			ScriptProperty property = new ScriptProperty(model.name(), model.toScriptEntry(value));
			object.add(property);
		}

		destination.add(object);
	}


	final public void toJson(Iterable<AModel> models, JsonContainer destination)
	{
		toJson(CONFIG.TABLE.NAME, models, destination);
	}


	@SuppressWarnings("unchecked")
	final public void toJson(String name, Iterable<AModel> models, JsonContainer destination)
	{
		Validator.notNull(name, "[name]");
		ScriptObject object = new ScriptObject(name);

		for (AModel model : models)
		{
			ADatabaseField field = model.field();
			Object value = get(field);
			ScriptProperty property = new ScriptProperty(model.name(), model.toScriptEntry(value));
			object.add(property);
		}

		destination.add(object);
	}


	@SuppressWarnings("unchecked")
	final public void fromJson(JsonDocument source)
		throws Exception
	{
		JsonObject object = source.getJsonObject(CONFIG.TABLE.NAME);

		if (object != null)
		{
			for (ADatabaseField field : CONFIG.TABLE.FIELDS0)
			{
				IParser.Report report = field.model()
					.parse(object);

				if (report.EXISTS)
				{
					set(field, report.VALUE);
				}
				else
				{
					throw new Exception();


					// XXX ?
				}
			}
		}
		else
		{
			throw new Exception();


			// XXX ?
		}
	}


	final public void fromJson(JsonDocument source, Iterable<AModel> models)
		throws Exception
	{
		fromJson(source, CONFIG.TABLE.NAME, models);
	}


	@SuppressWarnings("unchecked")
	final public void fromJson(JsonDocument source, String name, Iterable<AModel> models)
		throws Exception
	{
		JsonObject object = source.getJsonObject(name);

		if (object != null)
		{
			for (AModel model : models)
			{
				IParser.Report report = model.parse(object);

				if (report.EXISTS)
				{
					set(model.field(), report.VALUE);
				}
				else
				{
					throw new Exception();


					// XXX ?
				}
			}
		}
		else
		{
			throw new Exception();


			// XXX ?
		}
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
		final private ISqlTemplate EXISTS;
		final private ISqlTemplate LOAD;
		final private ISqlTemplate CREATE;
		final private ISqlTemplate SAVE;
		final private ISqlTemplate DELETE;
		final private ISqlTemplate ALLOCATE;

		//
		private SqlTemplates(DatabaseTable table)
		{
			ADatabaseField key = table.KEYS[0];
			ADatabaseField[] fields = table.FIELDS;
			int length = fields.length;
			ADatabaseField[] fields0 = table.FIELDS0;
			int length0 = fields0.length;

			// exists
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

				EXISTS = SqlTemplate.query(markup);
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
					"select"
				);

				for (ADatabaseField field : fields) markup.add(field.sender("?"), DatabaseUtil.comma(field.NUMBER, length));

				markup.add
				(
					"from",
						"RDB$DATABASE", // !
					"where",
						"not exists",
						"(",
							"select",
								key,
							"from",
								table,
							"where",
								key.sender(length), "=", "?",
						")"
				);

				CREATE = SqlTemplate.update(markup);
			}

			// save
			{
				SqlMarkup markup = new SqlMarkup();
				int i = 0;

				markup.add
				(
					"update",
						table,
					"set"
				);

				for (ADatabaseField field : fields0)
				{
					markup.add(field.sender(i), "=", "?", DatabaseUtil.comma(i, length0));
					i++;
				}

				markup.add
				(
					"where",
						key.sender(i), "=", "?"
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

			// allocate
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

				ALLOCATE = SqlTemplate.update(markup);
			}
		}
	}


	abstract protected static class AConfig<T, K extends AKey<T>, I extends AGeneratedSingleItem> // ! protected static, I extends AGeneralSingleItem
		implements IConfig
	{
		//
		final Log LOG;
		final DatabaseTable TABLE;
		final boolean JOURNAL;
		final boolean CACHE;
		final IKeyGenerator<T> GENERATOR;
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
			GENERATOR = generator();
			TEMPLATES = new SqlTemplates(TABLE);
			SIGNATURE = new AtomicReference<>(null);
		}

		//
		abstract protected IKeyGenerator<T> generator();
		abstract protected K key(@NullDisallow T id);
		abstract protected @NullDisallow byte[] toBytes(@NullDisallow K key);
		abstract protected @NullDisallow K fromBytes(@NullDisallow byte[] array) throws Exception;
		abstract protected I instance();
		abstract protected I instance(@NullDisallow K key);

		//
		@Override
		final public Pattern pattern()
		{
			return Pattern.ITEM_SINGLE_GENERATED;
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


	abstract public static class AKey<T> // ! public static, extends AGeneratedSingleItem.AKey<...>
		implements ISingleKeyEntity.IKey<T>
	{
		//
		final private AtomicReference<T> ID;

		//
		protected AKey()
		{
			ID = new AtomicReference<>(field().init());
		}

		protected AKey(@NullDisallow T id)
		{
			Validator.notNull(id, "[id]");
			ID = new AtomicReference<>(id);
		}

		//
		abstract protected ADatabaseField<T> field();

		//
		@Override
		final public boolean defined()
		{
			return field().defined(ID.get());
		}

		@Override
		final public @NullDisallow T id()
		{
			return ID.get();
		}

		final void id(@NullDisallow T id)
		{
			Validator.notNull(id, "[id]");
			ID.set(id);
		}

		final void init()
		{
			ID.set(field().init());
		}
	}

}
