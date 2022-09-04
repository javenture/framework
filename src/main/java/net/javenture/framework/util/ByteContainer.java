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
final public class ByteContainer
	extends AContainer<Byte, ByteContainer>
{
	//
	final private static int CAPACITY = 4096;                                                                                                        // XXX: 4096 ?
	final public static IInstanceFactory<ByteContainer> FACTORY_INSTANCE = object -> object instanceof ByteContainer;
	final public static ICopyFactory<ByteContainer> FACTORY_COPY = object -> object != null ? new ByteContainer(object) : null;

	final public static IByteFactory<ByteContainer> FACTORY_BYTE = new IByteFactory<>()
	{
		@Override
		public void toStream(@NullAllow ByteContainer object, OutputStream destination)
			throws Exception
		{
			if (IByteFactory.setNullHeader(object, destination))
			{
				// count
				int count = object.size;
				IntUtil.bytes(count, destination);

				// array
				if (count != 0) destination.write(object.array, 0, count);
			}
		}

		@Override
		public @NullAllow ByteContainer fromStream(InputStream source)
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
					byte[] array = new byte[count];
					source.read(array, 0, count);

					return new ByteContainer(array, true);
				}
				else
				{
					return new ByteContainer(ByteArrayUtil.BLANK, true);
				}
			}
			else
			{
				return null;
			}
		}
	};

	final public static IMurmur3HashFactory<ByteContainer> FACTORY_MURMUR3HASH = (object, destination) ->
	{
		if (IMurmur3HashFactory.updateNullHeader(object, destination))
		{
			IntUtil.murmur3hash(object.size, destination);
			ByteArrayUtil.FACTORY_MURMUR3HASH.update(object.array, 0, object.size, destination);
		}
	};

	final public static IDatabaseFactory<ByteContainer, byte[]> FACTORY_DATABASE = new IDatabaseFactory<>()
	{
		@Override
		public DatabaseType getDatabaseType()
		{
			return DatabaseType.BYTES;
		}

		@Override
		public @NullAllow byte[] toDatabase(@NullAllow ByteContainer object)
		{
			if (object != null)
			{
				int count = object.size;

				if (count != 0)
				{
					byte[] array = new byte[Primitives.INT.DIMENSION + Primitives.BYTE.DIMENSION * count];
					int index = 0;

					// count
					IntUtil.bytes(count, array, index);
					index += Primitives.INT.DIMENSION;

					// array
					System.arraycopy(object.array, 0, array, index, count);

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
		public @NullAllow ByteContainer fromDatabase(@NullAllow byte[] object)
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
						byte[] array = new byte[count];
						source.read(array, 0, count);

						return new ByteContainer(array, true);
					}
					else
					{
						return new ByteContainer(ByteArrayUtil.BLANK, true);
					}
				}
			}
			else
			{
				return null;
			}
		}
	};

	final public static Factories<ByteContainer> FACTORIES = new Factories<>(FACTORY_INSTANCE, FACTORY_COPY, FACTORY_BYTE, FACTORY_MURMUR3HASH, FACTORY_DATABASE);


	//
	private int capacity;
	private int size;
	private @NullDisallow byte[] array;


	//
	private ByteContainer(@NullDisallow ByteContainer source)
	{
		Validator.notNull(source, "[source]");

		this.capacity = source.capacity;
		this.size = source.size;
		this.array = source.array.clone();
	}


	public ByteContainer()
	{
		this(CAPACITY);
	}


	public ByteContainer(int capacity)
	{
		this.capacity = capacity;
		this.size = 0;
		this.array = capacity != 0 ? new byte[capacity] : ByteArrayUtil.BLANK;
	}


	public ByteContainer(@NullDisallow byte[] array)
	{
		this(array, false);
	}


	private ByteContainer(@NullDisallow byte[] array, boolean wrap)
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


	public byte[] array()
	{
		return Arrays.copyOf(array, size);
	}


	public byte[] array(int from, int to)
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
		array = ByteArrayUtil.BLANK;
	}


	@Override
	public boolean contains(@NullDisallow Byte value)
	{
		return index0(value, 0, size) >= 0;
	}


	public boolean contains(byte value)
	{
		return index0(value, 0, size) >= 0;
	}


	@Override
	public int index(@NullDisallow Byte value)
	{
		return index0(value, 0, size);
	}


	public int index(byte value)
	{
		return index0(value, 0, size);
	}


	public int index(byte value, int from, int to)
	{
		Validator.argument(from >= 0, "[from] (", from, ") is illegal");
		Validator.argument(to <= size, "[to] (", to, ") is illegal");

		return index0(value, from, to);
	}


	private int index0(byte value, int from, int to)
	{
		for (int i = from; i < to; i++)
		{
			if (array[i] == value) return i;
		}

		return -1;
	}


	@Override
	public @NullDisallow Byte get(int index)
	{
		Validator.argument(index >= 0 && index < size, "[index] (", index, ") is illegal");

		return array[index];
	}


	byte get0(int index)
	{
		return array[index];
	}


	public void set(int index, byte value)
	{
		Validator.argument(index >= 0 && index < size, "[index] (", index, ") is illegal");

		array[index] = value;
	}


	@Override
	public void add(@NullDisallow Byte value)
	{
		add((byte) value);
	}


	public void add(byte value)
	{
		ensure(1);
		array[size] = value;
		size++;
	}


	public void add(byte value, int index)
	{
		Validator.argument(index >= 0 && index <= size, "[index] (", index, ") is illegal");

		//
		ensure(1);
		byte[] array2 = new byte[capacity];

		if (index > 0) System.arraycopy(array, 0, array2, 0, index);

		if (index < size) System.arraycopy(array, index, array2, index + 1, size - index);

		array = array2;
		array[index] = value;
		size++;
	}


	public void add(byte[] values)
	{
		add(values, 0, values.length);
	}


	public void add(byte[] values, int from, int to)
	{
		int length = to - from;

		if (length != 0)
		{
			ensure(length);
			System.arraycopy(values, from, array, size, length);
			size += length;
		}
	}


	public void add(ByteContainer values)
	{
		add(values.array, 0, values.size);
	}


	public void add(ByteContainer values, int from, int to)
	{
		Validator.argument(from >= 0, "[from] (", from, ") is illegal");
		Validator.argument(to <= values.size, "[to] (", to, ") is illegal");

		add(values.array, from, to);
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
			byte[] array2 = new byte[capacity2];

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
	public Factories<ByteContainer> factories()
	{
		return FACTORIES;
	}


	@Override
	public Iterator<Byte> iterator()
	{
		return new Iterator<Byte>()
		{
			int index = -1;

			@Override
			public boolean hasNext()
			{
				return index + 1 < size;
			}

			@Override
			public Byte next()
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
	public boolean equals(@NullAllow ByteContainer object)
	{
		return
			this == object
			||
			object != null
			&&
			this.size == object.size
			&&
			ByteArrayUtil.equal0(this.array, object.array, 0, this.size);
	}


	@Override
	public String toString()
	{
		return Arrays.toString(array());
	}


	//
	public static ByteContainer wrap(byte[] array)
	{
		return new ByteContainer(array, true);
	}

}
