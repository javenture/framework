package net.javenture.framework.util;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullContains;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.factory.FactoryObjectUtil;
import net.javenture.framework.factory.IByteFactory;
import net.javenture.framework.factory.ICopyFactory;
import net.javenture.framework.factory.IDatabaseFactory;
import net.javenture.framework.factory.IInstanceFactory;
import net.javenture.framework.factory.IMurmur3HashFactory;
import net.javenture.framework.firebird.AFactoryObjectDatabaseField;
import net.javenture.framework.firebird.DatabaseType;
import net.javenture.framework.json.IJsonEntry;
import net.javenture.framework.json.JsonArray;
import net.javenture.framework.json.JsonObject;
import net.javenture.framework.json.NumberJsonValue;
import net.javenture.framework.json.StringJsonValue;
import net.javenture.framework.hash.Murmur3Hash;
import net.javenture.framework.script.IScriptEntry;
import net.javenture.framework.script.NullScriptValue;
import net.javenture.framework.script.StringScriptArray;
import net.javenture.framework.model.AFactoryObjectModel;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Iterator;


/*
	2020/01/05
 */
final public class StringContainer
	extends AContainer<String, StringContainer>
{
	//
	final private static int CAPACITY = 64;
	final public static IInstanceFactory<StringContainer> FACTORY_INSTANCE = object -> object instanceof StringContainer;
	final public static ICopyFactory<StringContainer> FACTORY_COPY = object -> object != null ? new StringContainer(object) : null;

	final public static IByteFactory<StringContainer> FACTORY_BYTE = new IByteFactory<>()
	{
		@Override
		public void toStream(@NullAllow StringContainer object, OutputStream destination)
			throws Exception
		{
			if (object != null) StringArrayUtil.FACTORY_BYTE.toStream(object.array, 0, object.size, destination);
			else StringArrayUtil.FACTORY_BYTE.toStream(null, destination);
		}

		@Override
		public @NullAllow StringContainer fromStream(InputStream source)
			throws Exception
		{
			String[] array = StringArrayUtil.FACTORY_BYTE.fromStream(source);

			return array != null ? new StringContainer(array, true) : null;
		}
	};

	final public static IMurmur3HashFactory<StringContainer> FACTORY_MURMUR3HASH = (object, destination) ->
	{
		if (IMurmur3HashFactory.updateNullHeader(object, destination))
		{
			IntUtil.murmur3hash(object.size, destination);
			StringArrayUtil.FACTORY_MURMUR3HASH.update(object.array, 0, object.size, destination);
		}
	};

	final public static IDatabaseFactory<StringContainer, byte[]> FACTORY_DATABASE = new IDatabaseFactory<>()
	{
		@Override
		public DatabaseType getDatabaseType()
		{
			return DatabaseType.BYTES;
		}

		@Override
		public @NullAllow byte[] toDatabase(@NullAllow StringContainer object)
			throws Exception
		{
			if (object != null)
			{
				int count = object.size;

				if (count != 0)
				{
					int capacity = 0;

					for (int i = 0; i < count; i++)
					{
						String s = object.array[i];

						capacity += s != null
							? Primitives.INT.DIMENSION + Primitives.INT.DIMENSION + Primitives.CHAR.DIMENSION * s.length()
							: Primitives.INT.DIMENSION;
					}

					try (ByteOutputStream destination = new ByteOutputStream(Primitives.INT.DIMENSION + capacity))
					{
						// count
						IntUtil.bytes(count, destination);

						// array
						for (int i = 0; i < count; i++) StringUtil.FACTORY_BYTE.toStream(object.array[i], destination);

						return destination.toBytes();
					}
				}
				else
				{
					return IntUtil.bytes(0);
				}
			}
			else
			{
				return null;
			}
		}

		@Override
		public @NullAllow StringContainer fromDatabase(@NullAllow byte[] object)
			throws Exception
		{
			if (object != null)
			{
				try (ByteInputStream source = new ByteInputStream(object))
				{
					// count
					int count = IntUtil.parse(source);
					Validator.condition(count >= 0, "[count] (", count, ") has invalid value");

					// array
					if (count != 0)
					{
						String[] array = new String[count];

						for (int i = 0; i < count; i++) array[i] = StringUtil.FACTORY_BYTE.fromStream(source);

						return new StringContainer(array, true);
					}
					else
					{
						return new StringContainer(StringArrayUtil.BLANK, true);
					}
				}
			}
			else
			{
				return null;
			}
		}
	};

	final public static Factories<StringContainer> FACTORIES = new Factories<>(FACTORY_INSTANCE, FACTORY_COPY, FACTORY_BYTE, FACTORY_MURMUR3HASH, FACTORY_DATABASE);


	//
	private int capacity;
	private int size;
	private @NullDisallow @NullContains String[] array;


	//
	private StringContainer(@NullDisallow StringContainer source)
	{
		Validator.notNull(source, "[source]");

		this.capacity = source.capacity;
		this.size = source.size;
		this.array = source.array.clone();
	}


	public StringContainer()
	{
		this(CAPACITY);
	}


	public StringContainer(int capacity)
	{
		this.capacity = capacity;
		this.size = 0;
		this.array = capacity != 0 ? new String[capacity] : StringArrayUtil.BLANK;
	}


	public StringContainer(@NullDisallow String[] array)
	{
		this(array, false);
	}


	private StringContainer(@NullDisallow String[] array, boolean wrap)
	{
		Validator.notNull(array, "[array]");

		this.capacity = array.length;
		this.size = array.length;
		this.array = wrap ? array : array.clone();
	}


	//
	@Override
	public int capacity()
	{
		return capacity;
	}


	@Override
	public int size()
	{
		return size;
	}


	public String[] array()
	{
		return Arrays.copyOf(array, size);
	}


	public String[] array(int from, int to)
	{
		Validator.argument(from >= 0 && from <= size, "[from] (", from, ") is illegal");
		Validator.argument(to >= 0 && to <= size, "[to] (", to, ") is illegal");

		return Arrays.copyOfRange(array, from, to);
	}


	@Override
	public void clear()
	{
		capacity = 0;
		size = 0;
		array = StringArrayUtil.BLANK;
	}


	@Override
	public boolean contains(@NullAllow String value)
	{
		return index0(value, 0, size) >= 0;
	}


	@Override
	public int index(@NullAllow String value)
	{
		return index0(value, 0, size);
	}


	public int index(@NullAllow String value, int from, int to)
	{
		Validator.argument(from >= 0, "[from] (", from, ") is illegal");
		Validator.argument(to <= size, "[to] (", to, ") is illegal");

		return index0(value, from, to);
	}


	private int index0(@NullAllow String value, int from, int to)
	{
		for (int i = from; i < to; i++)
		{
			if (StringUtil.equal(array[i], value)) return i;
		}

		return -1;
	}


	@Override
	public String get(int index)
	{
		Validator.argument(index >= 0 && index < size, "[index] (", index, ") is illegal");

		return array[index];
	}


	String get0(int index)
	{
		return array[index];
	}


	public void set(int index, @NullAllow String value)
	{
		Validator.argument(index >= 0 && index < size, "[index] (", index, ") is illegal");

		array[index] = value;
	}


	@Override
	public void add(@NullAllow String value)
	{
		ensure(1);
		array[size] = value;
		size++;
	}


	public void add(@NullAllow CharSequence value)
	{
		add(value != null ? value.toString() : null);
	}


	public void add(@NullAllow String value, int index)
	{
		Validator.argument(index >= 0 && index <= size, "[index] (", index, ") is illegal");

		//
		ensure(1);
		String[] array2 = new String[capacity];

		if (index > 0) System.arraycopy(array, 0, array2, 0, index);

		if (index < size) System.arraycopy(array, index, array2, index + 1, size - index);

		array = array2;
		array[index] = value;
		size++;
	}


	public void add(@NullAllow CharSequence value, int index)
	{
		add(value != null ? value.toString() : null, index);
	}


	public void add(@NullDisallow @NullContains String[] values)
	{
		add(values, 0, values.length);
	}


	public void add(@NullDisallow @NullContains String[] values, int from, int to)
	{
		int length = to - from;

		if (length != 0)
		{
			ensure(length);
			System.arraycopy(values, from, array, size, length);
			size += length;
		}
	}


	public void add(@NullDisallow @NullContains StringContainer container)
	{
		add(container.array, 0, container.size);
	}


	public void add(@NullDisallow @NullContains StringContainer container, int from, int to)
	{
		Validator.argument(from >= 0, "[from] (", from, ") is illegal");
		Validator.argument(to <= container.size, "[to] (", to, ") is illegal");

		add(container.array, from, to);
	}


	@Override
	public void remove(int index)
	{
		Validator.argument(index >= 0 && index < size, "[index] (", index, ") is illegal");
		int count = size - 1 - index;

		if (count != 0) System.arraycopy(array, index + 1, array, index, count);

		size--;
		array[size] = null;
	}


	@Override
	public void ensure(int count)
	{
		int requirement = size + count;

		if (requirement > capacity)
		{
			int capacity2 = calculate(requirement);
			String[] array2 = new String[capacity2];

			if (size > 0) System.arraycopy(array, 0, array2, 0, size);

			array = array2;
			capacity = capacity2;
		}
	}


	@Override
	public void sort()
	{
		Arrays.sort(array, 0, size);
	}


	@Override
	public Factories<StringContainer> factories()
	{
		return FACTORIES;
	}


	@Override
	public Iterator<String> iterator()
	{
		return new Iterator<>()
		{
			int index = -1;

			@Override
			public boolean hasNext()
			{
				return index + 1 < size;
			}

			@Override
			public String next()
			{
				return array[++index];
			}

			@Override
			public void remove()
			{
				throw new UnsupportedOperationException();
			}
		};
	}


	@Override
	public int hashCode()
	{
		Murmur3Hash result = new Murmur3Hash();
		FACTORY_MURMUR3HASH.update(this, result);

		return result.getInt();
	}


	@Override
	public boolean equals(@NullAllow StringContainer object)
	{
		return
			this == object
			||
			object != null
			&&
			this.size == object.size
			&&
			StringArrayUtil.equal(this.array, object.array, 0, this.size);
	}


	@Override
	public String toString()
	{
		return Arrays.toString(array());
	}


	//
	public static StringContainer wrap(String[] array)
	{
		return new StringContainer(array, true);
	}


	//
	final public static class DatabaseField
		extends AFactoryObjectDatabaseField<StringContainer>
	{
		//
		final private Model MODEL;

		//
		public DatabaseField(String table, int number, String name, StringContainer init, Option... options)
		{
			super(table, number, name, init, FACTORIES, options);

			MODEL = new Model(this, true, false);
		}

		@Override
		public Model model()
		{
			return MODEL;
		}
	}


	final public static class Model
		extends AFactoryObjectModel<StringContainer>
	{
		//
		final private Parser PARSER;

		//
		public Model(@NullDisallow DatabaseField field, boolean quotation, boolean trim)
		{
			super(field, quotation, trim);

			PARSER = new Parser(field.init(), field.nullable(), trim);
		}

		public Model(@NullDisallow String name, boolean nullable, boolean quotation, boolean trim, @NullAllow StringContainer init, @NullDisallow String... objects)
		{
			super(name, nullable, quotation, trim, init, FACTORIES, objects);

			PARSER = new Parser(init, nullable, trim);
		}

		//
		@Override
		public @NullDisallow IScriptEntry toScriptEntry()
		{
			if (INIT != null)
			{
				StringScriptArray array = new StringScriptArray(QUOTATION);
				array.add(INIT);

				return array;
			}
			else
			{
				return NullScriptValue.INSTANCE;
			}
		}

		@Override
		public @NullDisallow IScriptEntry toScriptEntry(@NullAllow StringContainer value)
		{
			Validator.argument(NULLABLE || value != null, "[value] null value disallowed");

			if (value != null)
			{
				StringScriptArray array = new StringScriptArray(QUOTATION);
				array.add(value);

				return array;
			}
			else
			{
				return NullScriptValue.INSTANCE;
			}
		}

		@Override
		public @NullDisallow IParser.Report<StringContainer> parse(@NullAllow CharSequence source)
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public @NullDisallow IParser.Report<StringContainer> parse(@NullAllow JsonObject source)
		{
			if (source != null)
			{
				IJsonEntry entry0 = source.get(NAME);

				if (entry0 != null)
				{
					if (entry0.type() == IJsonEntry.Type.ARRAY)
					{
						JsonArray array = (JsonArray) entry0;
						StringContainer container = new StringContainer(array.size());
						boolean error = false;

						if (QUOTATION)
						{
							for (IJsonEntry entry : array)
							{
								if (entry.type() == IJsonEntry.Type.STRING)
								{
									StringJsonValue value = (StringJsonValue) entry;
									container.add(TRIM ? StringTrimmer.toString(value) : value);
								}
								else
								{
									error = true;

									break;
								}
							}
						}
						else
						{
							for (IJsonEntry entry : array)
							{
								if (entry.type() == IJsonEntry.Type.NUMBER)
								{
									container.add((NumberJsonValue) entry); // ! no TRIM
								}
								else
								{
									error = true;

									break;
								}
							}
						}

						//
						if (!error) return new IParser.Report<>(true, !FactoryObjectUtil.equal(container, INIT), container);
					}
					else if (entry0.type() == IJsonEntry.Type.NULL && NULLABLE)
					{
						return PARSER.REPORT_NULL;
					}
				}
			}

			return new IParser.Report<>(false, false, FACTORY_COPY.copy(INIT));
		}
	}


	final public static class Parser
		implements IParser<StringContainer>
	{
		//
		final private @NullAllow StringContainer INIT;
		final private boolean NULLABLE;
		final private boolean TRIM;
		final private Report<StringContainer> REPORT_NULL;

		//
		public Parser(@NullAllow StringContainer init, boolean nullable, boolean trim)
		{
			Validator.argument(nullable || init != null, "[init] null value disallowed");

			INIT = FACTORY_COPY.copy(init);
			NULLABLE = nullable;
			TRIM = trim;
			REPORT_NULL = new Report<>(true, init != null, null);
		}

		//
		@Override
		public @NullAllow StringContainer init()
		{
			return FACTORY_COPY.copy(INIT);
		}

		@Override
		public boolean nullable()
		{
			return NULLABLE;
		}

		@Override
		public boolean trim()
		{
			return TRIM;
		}

		@Override
		public Report<StringContainer> parse(@NullAllow StringContainer value)
		{
			if (value != null) return new Report<>(true, !FactoryObjectUtil.equal(value, INIT), value);
			else if (NULLABLE) return REPORT_NULL;

			return new Report<>(false, false, FACTORY_COPY.copy(INIT));
		}

		@Override
		public Report<StringContainer> parse(@NullAllow CharSequence source)
		{
			throw new UnsupportedOperationException();
		}

		//
		final public static Parser BLANK = new Parser(new StringContainer(0), false, false);
		final public static Parser NULL = new Parser(null, true, false);
	}

}
