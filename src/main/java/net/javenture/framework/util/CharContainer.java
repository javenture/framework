package net.javenture.framework.util;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.factory.IByteFactory;
import net.javenture.framework.factory.ICopyFactory;
import net.javenture.framework.factory.IDatabaseFactory;
import net.javenture.framework.factory.IInstanceFactory;
import net.javenture.framework.factory.IMurmur3HashFactory;
import net.javenture.framework.firebird.DatabaseType;
import net.javenture.framework.hash.Murmur3Hash;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Iterator;


/*
	2021/05/30
 */
final public class CharContainer
	extends AContainer<Character, CharContainer>
{
	//
	final private static int CAPACITY = 2048;                                                                                                               // XXX: 2048 ?
	final public static IInstanceFactory<CharContainer> FACTORY_INSTANCE = object -> object instanceof CharContainer;
	final public static ICopyFactory<CharContainer> FACTORY_COPY = object -> object != null ? new CharContainer(object) : null;

	final public static IByteFactory<CharContainer> FACTORY_BYTE = new IByteFactory<>()
	{
		@Override
		public void toStream(@NullAllow CharContainer object, OutputStream destination)
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
					char[] array = object.array;

					for (int i = 0; i < count; i++) CharUtil.bytes(array[i], destination);
				}
			}
		}

		@Override
		public @NullAllow CharContainer fromStream(InputStream source)
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
					char[] array = new char[count];

					for (int i = 0; i < count; i++) array[i] = CharUtil.parse(source);

					return new CharContainer(array, true);
				}
				else
				{
					return new CharContainer(CharArrayUtil.BLANK, true);
				}
			}
			else
			{
				return null;
			}
		}
	};

	final public static IMurmur3HashFactory<CharContainer> FACTORY_MURMUR3HASH = (object, destination) ->
	{
		if (IMurmur3HashFactory.updateNullHeader(object, destination))
		{
			IntUtil.murmur3hash(object.size, destination);
			CharArrayUtil.FACTORY_MURMUR3HASH.update(object.array, 0, object.size, destination);
		}
	};

	final public static IDatabaseFactory<CharContainer, byte[]> FACTORY_DATABASE = new IDatabaseFactory<>()
	{
		@Override
		public DatabaseType getDatabaseType()
		{
			return DatabaseType.BYTES;
		}

		@Override
		public @NullAllow byte[] toDatabase(@NullAllow CharContainer object)
		{
			if (object != null)
			{
				int count = object.size;

				if (count != 0)
				{
					byte[] array = new byte[Primitives.INT.DIMENSION + Primitives.CHAR.DIMENSION * count];
					int index = 0;

					// count
					IntUtil.bytes(count, array, index);
					index += Primitives.INT.DIMENSION;

					// array
					for (int i = 0; i < count; i++)
					{
						CharUtil.bytes(object.array[i], array, index);
						index += Primitives.CHAR.DIMENSION;
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
		public @NullAllow CharContainer fromDatabase(@NullAllow byte[] object)
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
						char[] array = new char[count];

						for (int i = 0; i < count; i++) array[i] = CharUtil.parse(source);

						return new CharContainer(array, true);
					}
					else
					{
						return new CharContainer(CharArrayUtil.BLANK, true);
					}
				}
			}
			else
			{
				return null;
			}
		}
	};

	final public static Factories<CharContainer> FACTORIES = new Factories<>(FACTORY_INSTANCE, FACTORY_COPY, FACTORY_BYTE, FACTORY_MURMUR3HASH, FACTORY_DATABASE);


	//
	private int capacity;
	private int size;
	private @NullDisallow char[] array;


	//
	private CharContainer(@NullDisallow CharContainer source)
	{
		Validator.notNull(source, "[source]");

		this.capacity = source.capacity;
		this.size = source.size;
		this.array = source.array.clone();
	}


	public CharContainer()
	{
		this(CAPACITY);
	}


	public CharContainer(int capacity)
	{
		this.capacity = capacity;
		this.size = 0;

		this.array = capacity != 0
			? new char[capacity]
			: CharArrayUtil.BLANK;
	}


	public CharContainer(@NullDisallow char[] array)
	{
		this(array, false);
	}


	private CharContainer(@NullDisallow char[] array, boolean wrap)
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


	public char[] array()
	{
		return Arrays.copyOf(array, size);
	}


	public char[] array(int from, int to)
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
		array = CharArrayUtil.BLANK;
	}


	@Override
	public boolean contains(@NullDisallow Character value)
	{
		return index0(value, 0, size) >= 0;
	}


	public boolean contains(char value)
	{
		return index0(value, 0, size) >= 0;
	}


	@Override
	public int index(@NullDisallow Character value)
	{
		return index0(value, 0, size);
	}


	public int index(char value)
	{
		return index0(value, 0, size);
	}


	public int index(char value, int from, int to)
	{
		Validator.argument(from >= 0, "[from] (", from, ") is illegal");
		Validator.argument(to <= size, "[to] (", to, ") is illegal");

		return index0(value, from, to);
	}


	private int index0(char value, int from, int to)
	{
		for (int i = from; i < to; i++)
		{
			if (array[i] == value) return i;
		}

		return -1;
	}


	@Override
	public @NullDisallow Character get(int index)
	{
		Validator.argument(index >= 0 && index < size, "[index] (", index, ") is illegal");

		return array[index];
	}


	char get0(int index)
	{
		return array[index];
	}


	public void set(int index, char value)
	{
		Validator.argument(index >= 0 && index < size, "[index] (", index, ") is illegal");

		array[index] = value;
	}


	@Override
	public void add(@NullDisallow Character value)
	{
		add((char) value);
	}


	public void add(char value)
	{
		ensure(1);
		array[size] = value;
		size++;
	}


	public void add(char value, int index)
	{
		Validator.argument(index >= 0 && index <= size, "[index] (", index, ") is illegal");

		//
		ensure(1);
		char[] array2 = new char[capacity];

		if (index > 0) System.arraycopy(array, 0, array2, 0, index);

		if (index < size) System.arraycopy(array, index, array2, index + 1, size - index);

		array = array2;
		array[index] = value;
		size++;
	}


	public void add(@NullDisallow char[] values)
	{
		add(values, 0, values.length);
	}


	public void add(@NullDisallow char[] values, int from, int to)
	{
		int length = to - from;

		if (length != 0)
		{
			ensure(length);
			System.arraycopy(values, from, array, size, length);
			size += length;
		}
	}


	public void add(@NullDisallow CharContainer values)
	{
		add(values.array, 0, values.size);
	}


	public void add(@NullDisallow CharContainer values, int from, int to)
	{
		Validator.argument(from >= 0, "[from] (", from, ") is illegal");
		Validator.argument(to <= values.size, "[to] (", to, ") is illegal");

		add(values.array, from, to);
	}


	public void add(@NullDisallow String s)
	{
		add(s.toCharArray());
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


	@Override
	public void ensure(int count)
	{
		int requirement = size + count;

		if (requirement > capacity)
		{
			int capacity2 = calculate(requirement);
			char[] array2 = new char[capacity2];

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


	public String string()
	{
		return new String(array, 0, size);
	}


	@Override
	public Factories<CharContainer> factories()
	{
		return FACTORIES;
	}


	@Override
	public Iterator<Character> iterator()
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
			public Character next()
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
	public boolean equals(@NullAllow CharContainer object)
	{
		return
			this == object
			||
			object != null
			&&
			this.size == object.size
			&&
			CharArrayUtil.equal(this.array, object.array, 0, this.size);
	}


	@Override
	public String toString()
	{
		return Arrays.toString(array());
	}


	//
	public static CharContainer wrap(char[] array)
	{
		return new CharContainer(array, true);
	}

}
