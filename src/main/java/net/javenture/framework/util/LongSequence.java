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
import net.javenture.framework.firebird.AFactoryObjectDatabaseField;
import net.javenture.framework.firebird.DatabaseType;
import net.javenture.framework.json.IJsonEntry;
import net.javenture.framework.json.JsonArray;
import net.javenture.framework.json.JsonObject;
import net.javenture.framework.json.NumberJsonValue;
import net.javenture.framework.json.StringJsonValue;
import net.javenture.framework.model.AFactoryObjectModel;
import net.javenture.framework.hash.Murmur3Hash;
import net.javenture.framework.script.IScriptEntry;
import net.javenture.framework.script.LongScriptArray;
import net.javenture.framework.script.NullScriptValue;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Iterator;


/*
	2021/05/30
 */
final public class LongSequence
	extends ASequence<Long, LongSequence>
{
	//
	final private static int CAPACITY = 512;                                                                                           // XXX: 512 ?
	final public static IInstanceFactory<LongSequence> FACTORY_INSTANCE = object -> object instanceof LongSequence;
	final public static ICopyFactory<LongSequence> FACTORY_COPY = object -> object != null ? new LongSequence(object) : null;

	final public static IByteFactory<LongSequence> FACTORY_BYTE = new IByteFactory<>()
	{
		@Override
		public void toStream(@NullAllow LongSequence object, OutputStream destination)
			throws Exception
		{
			if (IByteFactory.setNullHeader(object, destination))
			{
				// count
				int count = object.size;
				IntUtil.bytes(count, destination);

				// array
				if (count != 0)
				{
					long[] array = object.array;

					for (int i = 0; i < count; i++) LongUtil.bytes(array[i], destination);
				}
			}
		}

		@Override
		public @NullAllow LongSequence fromStream(InputStream source)
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
					long[] array = new long[count];

					for (int i = 0; i < count; i++) array[i] = LongUtil.parse(source);

					return new LongSequence(array, true);
				}
				else
				{
					return new LongSequence(LongArrayUtil.BLANK, true);
				}
			}
			else
			{
				return null;
			}
		}
	};

	final public static IMurmur3HashFactory<LongSequence> FACTORY_MURMUR3HASH = (object, destination) ->
	{
		if (IMurmur3HashFactory.updateNullHeader(object, destination))
		{
			IntUtil.murmur3hash(object.size, destination);
			LongArrayUtil.FACTORY_MURMUR3HASH.update(object.array, 0, object.size, destination);
		}
	};

	final public static IDatabaseFactory<LongSequence, byte[]> FACTORY_DATABASE = new IDatabaseFactory<>()
	{
		@Override
		public DatabaseType getDatabaseType()
		{
			return DatabaseType.BYTES;
		}

		@Override
		public @NullAllow byte[] toDatabase(@NullAllow LongSequence object)
			throws Exception
		{
			if (object != null)
			{
				int count = object.size;

				if (count != 0)
				{
					byte[] array = new byte[Primitives.INT.DIMENSION + Primitives.LONG.DIMENSION * count];
					int index = 0;

					// count
					IntUtil.bytes(count, array, index);
					index += Primitives.INT.DIMENSION;

					// array
					for (int i = 0; i < count; i++)
					{
						LongUtil.bytes(object.array[i], array, index);
						index += Primitives.LONG.DIMENSION;
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
		public @NullAllow LongSequence fromDatabase(@NullAllow byte[] object)
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
						long[] array = new long[count];

						for (int i = 0; i < count; i++) array[i] = LongUtil.parse(source);

						return new LongSequence(array, true);
					}
					else
					{
						return new LongSequence(LongArrayUtil.BLANK, true);
					}
				}
			}
			else
			{
				return null;
			}
		}
	};

	final public static Factories<LongSequence> FACTORIES = new Factories<>(FACTORY_INSTANCE, FACTORY_COPY, FACTORY_BYTE, FACTORY_MURMUR3HASH, FACTORY_DATABASE);


	//
	private int capacity;

	int size;
	@NullDisallow long[] array;


	//
	public LongSequence()
	{
		this(CAPACITY);
	}


	public LongSequence(int capacity)
	{
		this.capacity = capacity;
		this.size = 0;
		this.array = capacity != 0 ? new long[capacity] : LongArrayUtil.BLANK;
	}


	public LongSequence(@NullDisallow LongSequence source)
	{
		Validator.notNull(source, "[source]");

		this.capacity = source.capacity;
		this.size = source.size;
		this.array = source.array.clone();
	}


	public LongSequence(@NullDisallow long[] array)
	{
		this(array, false);
	}


	private LongSequence(@NullDisallow long[] array, boolean wrap)
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


	public long[] array()
	{
		return Arrays.copyOf(array, size);
	}


	@Override
	public void clear()
	{
		capacity = 0;
		size = 0;
		array = LongArrayUtil.BLANK;
	}


	@Override
	public boolean contains(@NullDisallow Long value)
	{
		return index0(value, 0, size) >= 0;
	}


	public boolean contains(long value)
	{
		return index0(value, 0, size) >= 0;
	}


	@Override
	public int index(@NullDisallow Long value)
	{
		return index0(value, 0, size);
	}


	public int index(long value)
	{
		return index0(value, 0, size);
	}


	public int index(long value, int from, int to)
	{
		Validator.argument(from >= 0, "[from] (", from, ") is illegal");
		Validator.argument(to <= size, "[to] (", to, ") is illegal");

		return index0(value, from, to);
	}


	int index0(long value, int from, int to)
	{
		return Arrays.binarySearch(array, from, to, value);
	}


	@Override
	public Long get(int index)
	{
		Validator.argument
		(
			index >= 0 && index < size,
			"[index] (", index, ") is illegal"
		);

		return array[index];
	}


	@Override
	public boolean add(@NullDisallow Long value)
	{
		return add((long) value);
	}


	public boolean add(long value)
	{
		if (size != 0)
		{
			long first = array[0];
			long last = array[size - 1];

			if (value > last)
			{
				add0(value, size);

				return true;
			}
			else if (value < first)
			{
				add0(value, 0);

				return true;
			}
			else if (value > first && value < last)
			{
				int index = index0(value, 1, size - 1);

				if (index < 0)
				{
					add0(value, -(index + 1));

					return true;
				}
			}
		}
		else
		{
			add0(value, 0);

			return true;
		}

		return false;
	}


	private void add0(long value, int index)
	{
		ensure(1);

		if (index == size)
		{
			// !
		}
		else if (index == 0)
		{
			long[] array2 = new long[capacity];
			System.arraycopy(array, 0, array2, 1, size);
			array = array2;
		}
		else
		{
			long[] array2 = new long[capacity];
			System.arraycopy(array, 0, array2, 0, index);
			System.arraycopy(array, index, array2, index + 1, size - index);
			array = array2;
		}

		array[index] = value;
		size++;
	}


	public void add(long[] values)
	{
		add(values, 0, values.length);
	}


	public void add(long[] values, int from, int to)
	{
		Validator.argument(from >= 0, "[from] (", from, ") is illegal");
		Validator.argument(to <= values.length, "[to] (", to, ") is illegal");
		Validator.argument(from <= to, "[from] (", from, ") > [to] (", to, ")");

		//
		int length = to - from;

		if (length != 0)
		{
			ensure(length);

			for (int i = from; i < to; i++) add(values[i]);
		}
	}


	public void add(LongSequence sequence)
	{
		add(sequence, 0, sequence.size);
	}


	public void add(LongSequence sequence, int from, int to)
	{
		Validator.argument(from >= 0, "[from] (", from, ") is illegal");
		Validator.argument(to <= sequence.size, "[to] (", to, ") is illegal");
		Validator.argument(from <= to, "[from] (", from, ") > [to] (", to, ")");

		//
		int length = to - from;

		if (length != 0)
		{
			ensure(length);

			for (int i = from; i < to; i++) add(sequence.array[i]);
		}
	}


	@Override
	public void remove(int index)
	{
		Validator.argument
		(
			index >= 0 && index < size,
			"[index] (", index, ") is illegal"
		);

		int count = size - 1 - index;

		if (count != 0) System.arraycopy(array, index + 1, array, index, count);

		size--;
		array[size] = 0;
	}


	public long min(long init)
	{
		return size != 0 ? array[0] : init;
	}


	public long max(long init)
	{
		return size != 0 ? array[size - 1] : init;
	}


	@Override
	public boolean valid()
	{
		if (size > 1)
		{
			long value0 = array[0];

			for (int i = 1; i < size; i++)
			{
				long value = array[i];

				if (value > value0) value0 = value;
				else return false;
			}
		}

		return true;
	}


	@Override
	public void sort()
	{
		Arrays.sort(array, 0, size);
	}


	@Override
	public void ensure(int count)
	{
		int requirement = size + count;

		if (requirement > capacity)
		{
			int capacity2 = calculate(requirement);
			long[] array2 = new long[capacity2];

			if (size > 0) System.arraycopy(array, 0, array2, 0, size);

			array = array2;
			capacity = capacity2;
		}
	}


	@Override
	public Factories<LongSequence> factories()
	{
		return FACTORIES;
	}


	@Override
	public Iterator<Long> iterator()
	{
		return iterator(false);
	}


	public Iterator<Long> iterator(boolean descend)
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
				public Long next()
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
				public Long next()
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
	public boolean equals(@NullAllow LongSequence object)
	{
		return
			this == object
			||
			object != null
			&&
			this.size == object.size
			&&
			LongArrayUtil.equal(this.array, object.array, 0, this.size);
	}


	@Override
	public String toString()
	{
		return Arrays.toString(array());
	}


	//
	public static LongSequence wrap(long[] array)
	{
		return new LongSequence(array, true);
	}


	//
	final public static class DatabaseField
		extends AFactoryObjectDatabaseField<LongSequence>
	{
		//
		final private LongSequence.Model MODEL;

		//
		public DatabaseField(String table, int number, String name, @NullAllow LongSequence init, Option... options)
		{
			super(table, number, name, init, FACTORIES, options);

			MODEL = new LongSequence.Model(this, true, false);
		}

		@Override
		public LongSequence.Model model()
		{
			return MODEL;
		}
	}


	final public static class Model
		extends AFactoryObjectModel<LongSequence>
	{
		//
		final private LongSequence.Parser PARSER;

		//
		public Model(@NullDisallow LongSequence.DatabaseField field, boolean quotation, boolean trim)
		{
			super(field, quotation, trim);

			PARSER = new LongSequence.Parser(field.init(), field.nullable(), trim);
		}

		public Model(@NullDisallow String name, boolean nullable, boolean quotation, boolean trim, @NullAllow LongSequence init, @NullDisallow String... objects)
		{
			super(name, nullable, quotation, trim, init, FACTORIES, objects);

			PARSER = new LongSequence.Parser(init, nullable, trim);
		}

		//
		@Override
		public @NullDisallow IScriptEntry toScriptEntry()
		{
			if (INIT != null)
			{
				LongScriptArray array = new LongScriptArray(QUOTATION);
				array.add(INIT);

				return array;
			}
			else
			{
				return NullScriptValue.INSTANCE;
			}
		}

		@Override
		public @NullDisallow IScriptEntry toScriptEntry(@NullAllow LongSequence value)
		{
			Validator.argument(NULLABLE || value != null, "[value] null value disallowed");

			if (value != null)
			{
				LongScriptArray array = new LongScriptArray(QUOTATION);
				array.add(value);

				return array;
			}
			else
			{
				return NullScriptValue.INSTANCE;
			}
		}

		@Override
		public @NullDisallow IParser.Report<LongSequence> parse(@NullAllow CharSequence source)
		{
			return PARSER.parse(source);
		}

		@Override
		public @NullDisallow IParser.Report<LongSequence> parse(@NullAllow JsonObject source)
		{
			if (source != null)
			{
				IJsonEntry entry0 = source.get(NAME);

				if (entry0 != null)
				{
					if (entry0.type() == IJsonEntry.Type.ARRAY)
					{
						JsonArray array = (JsonArray) entry0;
						LongSequence sequence = new LongSequence(array.size());
						boolean error = false;

						if (QUOTATION)
						{
							for (IJsonEntry entry : array)
							{
								if (entry.type() == IJsonEntry.Type.STRING)
								{
									Long value = LongUtil.primitive((StringJsonValue) entry, TRIM);

									if (value != null)
									{
										sequence.add(value);
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
									Long value = LongUtil.primitive((NumberJsonValue) entry); // ! no TRIM

									if (value != null)
									{
										sequence.add(value);
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
						if (!error) return new IParser.Report<>(true, !FactoryObjectUtil.equal(sequence, INIT), sequence);
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
		implements IParser<LongSequence>
	{
		//
		final private static char DELIMITER = ',';

		//
		final private @NullAllow LongSequence INIT;
		final private boolean NULLABLE;
		final private boolean TRIM;
		final private Report<LongSequence> REPORT_NULL;

		//
		public Parser(@NullAllow LongSequence init, boolean nullable, boolean trim)
		{
			Validator.argument(nullable || init != null, "[init] null value disallowed");

			INIT = FACTORY_COPY.copy(init);
			NULLABLE = nullable;
			TRIM = trim;
			REPORT_NULL = new Report<>(true, init != null, null);
		}

		//
		@Override
		public @NullAllow LongSequence init()
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
		public Report<LongSequence> parse(@NullAllow LongSequence value)
		{
			if (value != null) return new Report<>(true, !FactoryObjectUtil.equal(value, INIT), value);
			else if (NULLABLE) return REPORT_NULL;

			return new Report<>(false, false, FACTORY_COPY.copy(INIT));
		}

		@Override
		public Report<LongSequence> parse(@NullAllow CharSequence source)
		{
			if (source != null)
			{
				if (!NULLABLE || NullUtil.notNull(source, TRIM))
				{
					if (source.length() != 0)
					{
						StringSplitter splitter = new StringSplitter(source, DELIMITER);
						LongSequence sequence = new LongSequence(splitter.size());
						boolean error = false;

						for (CharSequence s : splitter)
						{
							Long value = LongUtil.primitive(s, TRIM);

							if (value != null)
							{
								sequence.add(value);
							}
							else
							{
								error = true;

								break;
							}
						}

						if (!error) return new IParser.Report<>(true, !FactoryObjectUtil.equal(sequence, INIT), sequence);
					}
					else
					{
						return new IParser.Report<>(true, INIT == null || INIT.size != 0, new LongSequence(0));
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
		final public static LongSequence.Parser BLANK = new LongSequence.Parser(new LongSequence(0), false, false);
		final public static LongSequence.Parser NULL = new LongSequence.Parser(null, true, false);
	}

}
