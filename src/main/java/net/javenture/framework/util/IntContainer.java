package net.javenture.framework.util;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.factory.FactoryObjectUtil;
import net.javenture.framework.factory.IByteFactory;
import net.javenture.framework.factory.ICopyFactory;
import net.javenture.framework.factory.IDatabaseFactory;
import net.javenture.framework.factory.IInstanceFactory;
import net.javenture.framework.factory.IMurmur3HashFactory;
import net.javenture.framework.factory.IStringFactory;
import net.javenture.framework.firebird.AFactoryObjectDatabaseField;
import net.javenture.framework.firebird.DatabaseType;
import net.javenture.framework.json.IJsonEntry;
import net.javenture.framework.json.JsonArray;
import net.javenture.framework.json.JsonObject;
import net.javenture.framework.json.NumberJsonValue;
import net.javenture.framework.json.StringJsonValue;
import net.javenture.framework.hash.Murmur3Hash;
import net.javenture.framework.script.IScriptEntry;
import net.javenture.framework.script.IntScriptArray;
import net.javenture.framework.script.NullScriptValue;
import net.javenture.framework.model.AFactoryObjectModel;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Iterator;


/*
	2021/05/30
 */
final public class IntContainer                                                                         // XXX: + IntRawContainer
	extends AContainer<Integer, IntContainer>
{
	//
	final private static int CAPACITY = 1024;                                                                                                 // XXX: 128 ?
	final public static IInstanceFactory<IntContainer> FACTORY_INSTANCE = object -> object instanceof IntContainer;
	final public static ICopyFactory<IntContainer> FACTORY_COPY = object -> object != null ? new IntContainer(object) : null;

	final public static IByteFactory<IntContainer> FACTORY_BYTE = new IByteFactory<>()
	{
		@Override
		public void toStream(@NullAllow IntContainer object, OutputStream destination)
			throws Exception
		{
			if (IByteFactory.setNullHeader(object, destination))
			{
				// count
				int count = object.size;
				IntUtil.bytes(count, destination);

				// array
				for (int i = 0; i < count; i++) IntUtil.bytes(object.array[i], destination);
			}
		}

		@Override
		public @NullAllow IntContainer fromStream(InputStream source)
			throws Exception
		{
			if (IByteFactory.getNullHeader(source))
			{
				// count
				int count = IntUtil.parse(source);
				Validator.condition(count >= 0, "[count] (", count, ") has invalid value");

				// array
				if (count != 0)
				{
					int[] array = new int[count];

					for (int i = 0; i < count; i++) array[i] = IntUtil.parse(source);

					return new IntContainer(array, true);
				}
				else
				{
					return new IntContainer(IntArrayUtil.BLANK, true);
				}
			}
			else
			{
				return null;
			}
		}
	};

	final public static IStringFactory<IntContainer> FACTORY_STRING = new IStringFactory<>()
	{
		@Override
		public @NullDisallow String toString(@NullAllow IntContainer object)
		{
			if (object != null)
			{
				StringFragmenter fragmenter = new StringFragmenter(Parser.DELIMITER); // !
				fragmenter.add(object);

				return fragmenter.toString();
			}
			else
			{
				return NullUtil.STRING;
			}
		}

		@Override
		public @NullDisallow IParser<IntContainer> parser(@NullAllow IntContainer init, boolean nullable, boolean trim)
		{
			return new Parser(init, nullable, trim);
		}
	};

	final public static IMurmur3HashFactory<IntContainer> FACTORY_MURMUR3HASH = (object, destination) ->
	{
		if (IMurmur3HashFactory.updateNullHeader(object, destination))
		{
			IntUtil.murmur3hash(object.size, destination);                                                                    // ??? IntContainer.hash = int[].hash
			IntArrayUtil.FACTORY_MURMUR3HASH.update(object.array, 0, object.size, destination);
		}
	};

	final public static IDatabaseFactory<IntContainer, byte[]> FACTORY_DATABASE = new IDatabaseFactory<>()
	{
		@Override
		public DatabaseType getDatabaseType()
		{
			return DatabaseType.BYTES;
		}

		@Override
		public @NullAllow byte[] toDatabase(@NullAllow IntContainer object)
		{
			if (object != null)
			{
				int count = object.size;

				if (count != 0)
				{
					byte[] array = new byte[Primitives.INT.DIMENSION + Primitives.INT.DIMENSION * count];
					int index = 0;

					// count
					IntUtil.bytes(count, array, index);
					index += Primitives.INT.DIMENSION;

					// array
					for (int i = 0; i < count; i++)
					{
						IntUtil.bytes(object.array[i], array, index);
						index += Primitives.INT.DIMENSION;
					}

					return array;
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
		public @NullAllow IntContainer fromDatabase(@NullAllow byte[] object)
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
						int[] array = new int[count];

						for (int i = 0; i < count; i++) array[i] = IntUtil.parse(source);

						return new IntContainer(array, true);
					}
					else
					{
						return new IntContainer(IntArrayUtil.BLANK, true);
					}
				}
			}
			else
			{
				return null;
			}
		}
	};

	final public static Factories<IntContainer> FACTORIES = new Factories<>(FACTORY_INSTANCE, FACTORY_COPY, FACTORY_BYTE, FACTORY_STRING, FACTORY_MURMUR3HASH, FACTORY_DATABASE);


	//
	private int capacity;
	private int size;
	private @NullDisallow int[] array;


	//
	private IntContainer(@NullDisallow IntContainer source)
	{
		Validator.notNull(source, "[source]");

		this.capacity = source.capacity;
		this.size = source.size;
		this.array = source.array.clone();
	}


	public IntContainer()
	{
		this(CAPACITY);
	}


	public IntContainer(int capacity)
	{
		this.capacity = capacity;
		this.size = 0;
		this.array = capacity != 0 ? new int[capacity] : IntArrayUtil.BLANK;
	}


	public IntContainer(@NullDisallow int[] array)
	{
		this(array, false);
	}


	private IntContainer(@NullDisallow int[] array, boolean wrap)
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


	public int[] array()
	{
		return Arrays.copyOf(array, size);
	}


	public int[] array(int from, int to)
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
		array = IntArrayUtil.BLANK;
	}


	@Override
	public boolean contains(@NullDisallow Integer value)
	{
		return index0(value, 0, size) >= 0;
	}


	public boolean contains(int value)
	{
		return index0(value, 0, size) >= 0;
	}


	@Override
	public int index(@NullDisallow Integer value)
	{
		return index0(value, 0, size);
	}


	public int index(int value)
	{
		return index0(value, 0, size);
	}


	public int index(int value, int from, int to)
	{
		Validator.argument(from >= 0 && from <= size, "[from] (", from, ") is illegal");
		Validator.argument(to >= 0 && to <= size, "[to] (", to, ") is illegal");

		return index0(value, from, to);
	}


	private int index0(int value, int from, int to)
	{
		for (int i = from; i < to; i++)
		{
			if (array[i] == value) return i;
		}

		return -1;
	}


	@Override
	public @NullDisallow Integer get(int index)
	{
		Validator.argument(index >= 0 && index < size, "[index] (", index, ") is illegal");

		return array[index];
	}


	int get0(int index)
	{
		return array[index];
	}


	public void set(int index, int value)
	{
		Validator.argument(index >= 0 && index < size, "[index] (", index, ") is illegal");

		array[index] = value;
	}


	@Override
	public void add(@NullDisallow Integer value)
	{
		add((int) value);
	}


	public void add(int value)
	{
		ensure(1);
		array[size] = value;
		size++;
	}


	public void add(int value, int index)
	{
		Validator.argument(index >= 0 && index <= size, "[index] (", index, ") is illegal");

		//
		ensure(1);
		int[] array2 = new int[capacity];

		if (index > 0) System.arraycopy(array, 0, array2, 0, index);

		if (index < size) System.arraycopy(array, index, array2, index + 1, size - index);

		array = array2;
		array[index] = value;
		size++;
	}


	public void add(int[] values)
	{
		add(values, 0, values.length);
	}


	public void add(int[] values, int from, int to)
	{
		int length = to - from;                                                                            // XXX: Validator ?

		if (length != 0)
		{
			ensure(length);
			System.arraycopy(values, from, array, size, length);
			size += length;
		}
	}


	public void add(IntContainer container)
	{
		add(container.array, 0, container.size);
	}


	public void add(IntContainer container, int from, int to)
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
		array[size] = 0;
	}


	public int min(int init)
	{
		if (size != 0)
		{
			int result = array[0];

			for (int i = 1; i < size; i++)
			{
				if (array[i] < result) result = array[i];
			}

			return result;
		}
		else
		{
			return init;
		}
	}


	public int max(int init)
	{
		if (size != 0)
		{
			int result = array[size - 1];

			for (int i = size - 2; i >= 0; i--)
			{
				if (array[i] > result) result = array[i];
			}

			return result;
		}
		else
		{
			return init;
		}
	}


	@Override
	public void ensure(int count)
	{
		int requirement = size + count;

		if (requirement > capacity)
		{
			int capacity2 = calculate(requirement);
			int[] array2 = new int[capacity2];

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
	public Factories<IntContainer> factories()
	{
		return FACTORIES;
	}


	@Override
	public Iterator<Integer> iterator()
	{
		return iterator(false);
	}


	public Iterator<Integer> iterator(boolean descend)
	{
		if (descend)
		{
			return new Iterator<>()
			{
				private int index = size;

				@Override
				public boolean hasNext()
				{
					return index - 1 >= 0;
				}

				@Override
				public Integer next()
				{
					return array[--index];
				}
			};
		}
		else
		{
			return new Iterator<>()
			{
				private int index = -1;

				@Override
				public boolean hasNext()
				{
					return index + 1 < size;
				}

				@Override
				public Integer next()
				{
					return array[++index];
				}
			};
		}
	}


	@Override
	public int hashCode()
	{
		Murmur3Hash result = new Murmur3Hash();
		FACTORY_MURMUR3HASH.update(this, result);

		return result.getInt();
	}


	@Override
	public boolean equals(@NullAllow IntContainer object)
	{
		return
			this == object
			||
			object != null
			&&
			this.size == object.size
			&&
			IntArrayUtil.equal(this.array, object.array, 0, this.size);
	}


	@Override
	public String toString()
	{
		return Arrays.toString(array());
	}


	//
	public static IntContainer wrap(int[] array)
	{
		return new IntContainer(array, true);
	}


	//
	final public static class DatabaseField
		extends AFactoryObjectDatabaseField<IntContainer>
	{
		//
		final private Model MODEL;

		//
		public DatabaseField(String table, int number, String name, @NullAllow IntContainer init, Option... options)
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
		extends AFactoryObjectModel<IntContainer>
	{
		//
		final private Parser PARSER;

		//
		public Model(@NullDisallow DatabaseField field, boolean quotation, boolean trim)
		{
			super(field, quotation, trim);

			PARSER = new Parser(field.init(), field.nullable(), trim);
		}

		public Model(@NullDisallow String name, boolean nullable, boolean quotation, boolean trim, @NullAllow IntContainer init, @NullDisallow String... objects)
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
				IntScriptArray array = new IntScriptArray(QUOTATION);
				array.add(INIT);

				return array;
			}
			else
			{
				return NullScriptValue.INSTANCE;
			}
		}

		@Override
		public @NullDisallow IScriptEntry toScriptEntry(@NullAllow IntContainer value)
		{
			Validator.argument(NULLABLE || value != null, "[value] null value disallowed");

			if (value != null)
			{
				IntScriptArray array = new IntScriptArray(QUOTATION);
				array.add(value);

				return array;
			}
			else
			{
				return NullScriptValue.INSTANCE;
			}
		}

		@Override
		public @NullDisallow IParser.Report<IntContainer> parse(@NullAllow CharSequence source)
		{
			return PARSER.parse(source);
		}

		@Override
		public @NullDisallow IParser.Report<IntContainer> parse(@NullAllow JsonObject source)
		{
			if (source != null)
			{
				IJsonEntry entry0 = source.get(NAME);

				if (entry0 != null)
				{
					if (entry0.type() == IJsonEntry.Type.ARRAY)
					{
						JsonArray array = (JsonArray) entry0;
						IntContainer container = new IntContainer(array.size());
						boolean error = false;

						if (QUOTATION)
						{
							for (IJsonEntry entry : array)
							{
								if (entry.type() == IJsonEntry.Type.STRING)
								{
									Integer value = IntUtil.primitive((StringJsonValue) entry, TRIM);

									if (value != null)
									{
										container.add(value);
									}
									else
									{
										error = true;

										break;
									}
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
									Integer value = IntUtil.primitive((NumberJsonValue) entry); // ! no TRIM

									if (value != null)
									{
										container.add(value);
									}
									else
									{
										error = true;

										break;
									}
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
		implements IParser<IntContainer>
	{
		//
		final private static char DELIMITER = ',';

		//
		final private @NullAllow IntContainer INIT;
		final private boolean NULLABLE;
		final private boolean TRIM;
		final private Report<IntContainer> REPORT_NULL;

		//
		public Parser(@NullAllow IntContainer init, boolean nullable, boolean trim)
		{
			Validator.argument(nullable || init != null, "[init] null value disallowed");

			INIT = FACTORY_COPY.copy(init);
			NULLABLE = nullable;
			TRIM = trim;
			REPORT_NULL = new Report<>(true, init != null, null);
		}

		//
		@Override
		public @NullAllow IntContainer init()
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
		public Report<IntContainer> parse(@NullAllow IntContainer value)
		{
			if (value != null) return new Report<>(true, !FactoryObjectUtil.equal(value, INIT), value);
			else if (NULLABLE) return REPORT_NULL;

			return new Report<>(false, false, FACTORY_COPY.copy(INIT));
		}

		@Override
		public Report<IntContainer> parse(@NullAllow CharSequence source)
		{
			if (source != null)
			{
				if (!NULLABLE || NullUtil.notNull(source, TRIM))
				{
					if (source.length() != 0)
					{
						StringSplitter splitter = new StringSplitter(source, DELIMITER);
						IntContainer container = new IntContainer(splitter.size());
						boolean error = false;

						for (CharSequence s : splitter)
						{
							Integer value = IntUtil.primitive(s, TRIM);

							if (value != null)
							{
								container.add(value);
							}
							else
							{
								error = true;

								break;
							}
						}

						if (!error) return new IParser.Report<>(true, !FactoryObjectUtil.equal(container, INIT), container);
					}
					else
					{
						return new IParser.Report<>(true, INIT == null || INIT.size != 0, new IntContainer(0));
					}
				}
				else
				{
					return REPORT_NULL;
				}
			}

			return new IParser.Report<>(false, false, FACTORY_COPY.copy(INIT));
		}

		//
		final public static Parser BLANK = new Parser(new IntContainer(0), false, false);
		final public static Parser NULL = new Parser(null, true, false);
	}

}
