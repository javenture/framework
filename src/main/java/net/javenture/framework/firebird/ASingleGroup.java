package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.identifier.IntIdentifier;
import net.javenture.framework.json.IJsonEntry;
import net.javenture.framework.json.JsonArray;
import net.javenture.framework.json.JsonContainer;
import net.javenture.framework.json.JsonDocument;
import net.javenture.framework.json.JsonObject;
import net.javenture.framework.log.Log;
import net.javenture.framework.log.LogAttachment;
import net.javenture.framework.script.ScriptArray;
import net.javenture.framework.script.ScriptObject;
import net.javenture.framework.script.ScriptProperty;
import net.javenture.framework.util.ConstUtil;
import net.javenture.framework.util.IConst;
import net.javenture.framework.util.IParser;
import net.javenture.framework.util.Validator;
import net.javenture.framework.model.AModel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;


/*
	2021/06/02
 */
abstract public class ASingleGroup<T, K extends ASingleGroup.AKey<T>, E extends ASingleGroup.AEntry<T, E>, G extends ASingleGroup<T, K, E, G>> // ! G extends ASingleGroup<T, K, E, G>
	extends AGroup<E, G>
	implements ISingleKeyEntity<T>
{
	//
	enum Modification
		implements IConst<Modification>
	{
		//
		UNKNOWN(-1),
		UPDATE(0),
		DELETE(1);

		//
		final public static Factories<Modification> FACTORIES = ConstUtil.factories(values(), object -> object instanceof Modification);

		//
		final private int ID;

		//
		Modification(int id)
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
		public Factories<Modification> factories()
		{
			return FACTORIES;
		}

		@Override
		public boolean equals(Modification value)
		{
			return this == value;
		}

		@Override
		public String toString()
		{
			return "" + ID;
		}
	}


	//
	final private AConfig<T, K, E, G> CONFIG;
	final private K KEY;
	final private ArrayList<E> ENTRIES; // ! ArrayList


	//
	protected ASingleGroup(@NullDisallow K key)
	{
		Validator.notNull(key, "[key]");

		CONFIG = config();
		KEY = key;
		ENTRIES = new ArrayList<>();
	}


	//
	@Override
	abstract public AConfig<T, K, E, G> config();


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
	final public int size()
	{
		return ENTRIES.size();
	}


	@Override
	final public boolean contains(@NullDisallow E entry)
	{
		Validator.notNull(entry, "[entry]");


		// XXX: check id


		for (E entry0 : ENTRIES)
		{
			if (entry.match(entry0)) return true;
		}

		return false;
	}


	@Override
	final public int index(@NullDisallow E entry)
	{
		Validator.notNull(entry, "[entry]");


		// XXX: check id


		for (int i = 0; i < ENTRIES.size(); i++)
		{
			if (ENTRIES.get(i).match(entry)) return i;
		}

		return -1;
	}


	@Override
	final public @NullDisallow E get(int index)
	{
		return ENTRIES.get(index);
	}


	@Override
	final public void add(@NullDisallow E entry)
	{
		Validator.notNull(entry, "[entry]");


		// XXX: check id


		ENTRIES.add(entry);
	}


	@Override
	final public boolean remove(@NullDisallow E entry)
	{
		Validator.notNull(entry, "[entry]");


		// XXX: check id


		Iterator<E> iterator = ENTRIES.iterator();

		while (iterator.hasNext())
		{
			E entry0 = iterator.next();

			if (entry0.match(entry))
			{
				iterator.remove();

				return true;
			}
		}

		return false;
	}


	@Override
	final public void clear()
	{
		ENTRIES.clear();
	}


	@Override
	final public void sort()
	{
		if (CONFIG.COMPARATOR != null) ENTRIES.sort(CONFIG.COMPARATOR);
	}


	@Override
	final public ISnapshot snapshot()
	{
		return new GroupSnapshot(this, () -> ENTRIES);
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

		//
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

		{
			int size = ENTRIES.size();

			if (size != 0)
			{
				if (CONFIG.JOURNAL)
				{
					create0(transaction, ENTRIES);
					toJournal(transaction, JournalOperation.ENTITY_CREATE, GroupUtil.toBytes(this, Modification.UPDATE, null, ENTRIES));
				}
				else
				{
					create0(transaction, ENTRIES);
				}
			}
		}

		triggerAfterCreate(transaction);
	}


	private void create0(DatabaseTransaction transaction, @NullDisallow ArrayList<E> entries)
		throws Exception
	{
		GroupUtil.validate(this, entries);
		ISqlTemplate template = CONFIG.TEMPLATES.CREATE;
		SqlParameters parameters = new SqlParameters();

		for (E entry : entries) parameters.add(entry.VALUES);

		ISqlResponse response = transaction.execute(template, parameters);
		Validator.condition(response.count() == entries.size(), "[response.count()] (", response.count(), ") != [entries.size()] (", entries.size(), ")");
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
		triggerBeforeLoad(transaction);
		load0(transaction);
		triggerAfterLoad(transaction);
	}


	private void load0(DatabaseTransaction transaction)
		throws Exception
	{
		ENTRIES.clear();
		ISqlTemplate template = CONFIG.TEMPLATES.LOAD;
		SqlParameters parameters = new SqlParameters(KEY.id());
		ISqlResponse response = transaction.execute(template, parameters);
		IDataset dataset = response.dataset();

		if (dataset.exists())
		{
			ENTRIES.ensureCapacity(dataset.rows());

			while (dataset.next())
			{
				E entry = entry();
				int i = 0;

				for (ADatabaseField field : CONFIG.TABLE.FIELDS0) entry.VALUES[field.NUMBER] = dataset.get(i++);

				ENTRIES.add(entry);
			}

			sort();
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

		triggerBeforeLoad(transaction);
		result = loadIfExists0(transaction);
		triggerAfterLoad(transaction);

		return result;
	}


	private boolean loadIfExists0(DatabaseTransaction transaction)
		throws Exception
	{
		ENTRIES.clear();
		ISqlTemplate template = CONFIG.TEMPLATES.LOAD;
		SqlParameters parameters = new SqlParameters(KEY.id());
		ISqlResponse response = transaction.execute(template, parameters);
		IDataset dataset = response.dataset();

		if (dataset.exists())
		{
			ENTRIES.ensureCapacity(dataset.rows());

			while (dataset.next())
			{
				E entry = entry();
				int i = 0;

				for (ADatabaseField field : CONFIG.TABLE.FIELDS0) entry.VALUES[field.NUMBER] = dataset.get(i++);

				ENTRIES.add(entry);
			}

			sort();

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

		{
			ArrayList<E> entries = this.ENTRIES;
			int size = entries.size();

			if (size != 0)
			{
				ASingleGroup<T, K, E, G> group0 = CONFIG.instance(KEY); // ! ASingleGroup<T, K, E, G>
				group0.load0(transaction);
				ArrayList<E> entries0 = group0.ENTRIES;
				int size0 = entries0.size();

				if (size0 != 0)
				{
					ArrayList<E> listDeletion = new ArrayList<>(size0);
					ArrayList<E> listAddition = new ArrayList<>(size);
					GroupUtil.conversion(entries0, entries, listDeletion, listAddition);
					boolean deletion = !listDeletion.isEmpty();
					boolean addition = !listAddition.isEmpty();

					if (CONFIG.JOURNAL)
					{
						if (deletion || addition)
						{
							if (deletion) delete0(transaction, listDeletion);

							if (addition) create0(transaction, listAddition);

							toJournal(transaction, JournalOperation.ENTITY_SAVE, GroupUtil.toBytes(this, Modification.UPDATE, listDeletion, listAddition));
						}
					}
					else
					{
						if (deletion || addition)
						{
							if (deletion) delete0(transaction, listDeletion);

							if (addition) create0(transaction, listAddition);
						}
					}
				}
				else
				{
					if (CONFIG.JOURNAL)
					{
						create0(transaction, entries);
						toJournal(transaction, JournalOperation.ENTITY_SAVE, GroupUtil.toBytes(this, Modification.UPDATE, null, entries));
					}
					else
					{
						create0(transaction, entries);
					}
				}
			}
			else
			{
				if (CONFIG.JOURNAL)
				{
					delete0(transaction);
					toJournal(transaction, JournalOperation.ENTITY_SAVE, GroupUtil.toBytes(this, Modification.DELETE, null, null));
				}
				else
				{
					delete0(transaction);
				}
			}
		}

		triggerAfterSave(transaction);
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


		// XXX: cache set not exist
	}


	private void delete0(DatabaseTransaction transaction)
		throws Exception
	{
		ENTRIES.clear();
		ISqlTemplate template = CONFIG.TEMPLATES.DELETE;
		SqlParameters parameters = new SqlParameters(KEY.id());
		transaction.execute(template, parameters);
	}


	private void delete0(DatabaseTransaction transaction, @NullDisallow ArrayList<E> entries)
		throws Exception
	{
		GroupUtil.validate(this, entries);
		ISqlTemplate template = CONFIG.TEMPLATES.DELETE1;
		SqlParameters parameters = new SqlParameters();

		for (E entry : entries) parameters.add(entry.VALUES);

		ISqlResponse response = transaction.execute(template, parameters);
		Validator.condition(response.count() == entries.size(), "[response.count()] (", response.count(), ") != [entries.size()] (", entries.size(), ")");
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


		// XXX: cache set not exist

		return result;
	}


	private boolean deleteIfExist0(DatabaseTransaction transaction)
		throws Exception
	{
		ENTRIES.clear();
		ISqlTemplate template = CONFIG.TEMPLATES.DELETE;
		SqlParameters parameters = new SqlParameters(KEY.id());
		ISqlResponse response = transaction.execute(template, parameters);

		return response.count() != 0;
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
			.add(KEY, "KEY")
			.add(ENTRIES, "ENTRIES");
	}


	@Override
	final public Iterator<E> iterator()
	{
		sort();

		return ENTRIES.iterator();
	}


	@SuppressWarnings("unchecked")
	final public void toJson(JsonContainer destination)
	{
		ScriptArray array = new ScriptArray(CONFIG.TABLE.NAME);

		for (E entry : ENTRIES)
		{
			ScriptObject object = new ScriptObject();

			for (ADatabaseField field : CONFIG.TABLE.FIELDS0)
			{
				AModel model = field.model();
				Object value = entry.get(field);
				ScriptProperty property = new ScriptProperty(model.name(), model.toScriptEntry(value));
				object.add(property);
			}

			array.add(object);
		}

		destination.add(array);
	}


	final public void toJson(Iterable<AModel> models, JsonContainer destination)
	{
		toJson(CONFIG.TABLE.NAME, models, destination);
	}


	@SuppressWarnings("unchecked")
	final public void toJson(String name, Iterable<AModel> models, JsonContainer destination)
	{
		Validator.notNull(name, "[name]");
		ScriptArray array = new ScriptArray(name);

		for (E entry : ENTRIES)
		{
			ScriptObject object = new ScriptObject();

			for (AModel model : models)
			{
				ADatabaseField field = model.field();
				Object value = entry.get(field);
				ScriptProperty property = new ScriptProperty(model.name(), model.toScriptEntry(value));
				object.add(property);
			}

			array.add(object);
		}

		destination.add(array);
	}


	@SuppressWarnings("unchecked")
	final public void fromJson(JsonDocument source)
		throws Exception
	{
		JsonArray array = source.getJsonArray(CONFIG.TABLE.NAME);

		if (array != null)
		{
			for (IJsonEntry entry : array)
			{
				if (entry.type() == IJsonEntry.Type.OBJECT)
				{
					E entry0 = entry();
					JsonObject object = (JsonObject) entry;

					for (ADatabaseField field : CONFIG.TABLE.FIELDS0)
					{
						IParser.Report report = field.model()
							.parse(object);

						if (report.EXISTS)
						{
							entry0.set(field, report.VALUE);
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
		JsonArray array = source.getJsonArray(name);

		if (array != null)
		{
			for (IJsonEntry entry : array)
			{
				if (entry.type() == IJsonEntry.Type.OBJECT)
				{
					E entry0 = entry();
					JsonObject object = (JsonObject) entry;

					for (AModel model : models)
					{
						IParser.Report report = model.parse(object);

						if (report.EXISTS)
						{
							entry0.set(model.field(), report.VALUE);
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
		}
		else
		{
			throw new Exception();


			// XXX ?
		}
	}


	//
	final private static class SqlTemplates
	{
		//
		final private ISqlTemplate EXIST;
		final private ISqlTemplate LOAD;
		final private ISqlTemplate CREATE;
		final private ISqlTemplate DELETE;
		final private ISqlTemplate DELETE1;

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
						key.sender(0), "=", "?"
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

				CREATE = SqlTemplate.batch(markup);
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

			// delete 1
			{
				SqlMarkup markup = new SqlMarkup();

				markup.add
				(
					"delete",
					"from",
						table,
					"where"
				);

				for (ADatabaseField field : fields) markup.add(field.sender(), "=", "?", DatabaseUtil.delimiter(field.NUMBER, length, "and"));

				markup.add
				(
					"rows 1"
				);

				DELETE1 = SqlTemplate.batch(markup);
			}
		}
	}


	abstract protected static class AConfig<V, K extends AKey<V>, E extends AEntry<V, E>, G extends ASingleGroup> // ! static
		implements IConfig<E>
	{
		//
		final Log LOG;
		final DatabaseTable TABLE;
		final boolean JOURNAL;
		final boolean CACHE;
		final @NullAllow Comparator<E> COMPARATOR;
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
			COMPARATOR = comparator();
			TEMPLATES = new SqlTemplates(TABLE);
			SIGNATURE = new AtomicReference<>(null);
		}

		//
		abstract protected @NullDisallow byte[] toBytes(@NullDisallow K key);
		abstract protected @NullDisallow K fromBytes(@NullDisallow byte[] array) throws Exception;
		abstract protected G instance(@NullDisallow K key);

		//
		@Override
		final public Pattern pattern()
		{
			return Pattern.GROUP_SINGLE;
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


	abstract public static class AKey<T> // ! public static, extends ASingleGroup.AKey<...>
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


	abstract protected static class AEntry<T, E extends AEntry> // ! protected static, extends ASingleGroup.AEntry
		extends AGroup.AEntry<E>
	{
		//
		final Object[] VALUES;

		//
		protected AEntry(T id)
		{
			DatabaseTable table = config().TABLE;
			VALUES = table.init();
			VALUES[table.KEYS[0].NUMBER] = id;
		}

		//
		abstract protected AConfig config(); // ! protected

		//
		@Override
		final public void set(@NullDisallow E entry)
		{
			// XXX: check KEY


			ADatabaseField[] fields = config().TABLE.FIELDS0; // ! FIELDS0

			for (ADatabaseField field : fields)
			{
				int number = field.NUMBER;

				this.VALUES[number] = field.factories()
					.getCopyFactory()
					.copy(entry.VALUES[number]);
			}
		}

		@Override
		final public boolean match(@NullDisallow E entry)
		{
			ADatabaseField[] fields = config().TABLE.FIELDS0; // ! FIELDS0

			for (ADatabaseField field : fields)
			{
				int number = field.NUMBER;

				if (!field.equal(this.VALUES[number], entry.VALUES[number])) return false;
			}

			return true;
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
	}

}
