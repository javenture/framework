package net.javenture.framework.util;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.factory.IByteFactory;
import net.javenture.framework.factory.ICopyFactory;
import net.javenture.framework.factory.IMurmur3HashFactory;
import net.javenture.framework.hash.Murmur3Hash;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Iterator;


/*
	2019/12/04
 */
final public class BooleanContainer
	extends AContainer<Boolean, BooleanContainer>
{
	//
	final private static int CAPACITY = 128;                                                                                            // XXX: ?

	final public static ICopyFactory<BooleanContainer> FACTORY_COPY = object -> object != null ? new BooleanContainer(object) : null;

	final public static IByteFactory<BooleanContainer> FACTORY_BYTE = new IByteFactory<>()
	{
		@Override
		public void toStream(@NullAllow BooleanContainer object, OutputStream destination)
			throws Exception
		{
			if (IByteFactory.setNullHeader(object, destination)) BooleanArrayUtil.FACTORY_BYTE.toStream(object.array, 0, object.size, destination);
		}

		@Override
		public @NullAllow BooleanContainer fromStream(InputStream source)
			throws Exception
		{
			return IByteFactory.getNullHeader(source) ? new BooleanContainer(BooleanArrayUtil.FACTORY_BYTE.fromStream(source), true) : null;
		}
	};

	final public static IMurmur3HashFactory<BooleanContainer> FACTORY_MURMUR3HASH = (object, destination) ->
	{
		if (IMurmur3HashFactory.updateNullHeader(object, destination))
		{
			IntUtil.murmur3hash(object.size, destination);
			BooleanArrayUtil.FACTORY_MURMUR3HASH.update(object.array, 0, object.size, destination);
		}
	};

	final public static Factories<BooleanContainer> FACTORIES = new Factories<>(FACTORY_COPY, FACTORY_BYTE, FACTORY_MURMUR3HASH);


	//
	private int capacity;
	private int size;
	private @NullDisallow boolean[] array;


	//
	private BooleanContainer(@NullDisallow BooleanContainer source)
	{
		Validator.notNull(source, "[source]");

		this.capacity = source.capacity;
		this.size = source.size;
		this.array = source.array.clone();
	}


	public BooleanContainer()
	{
		this(CAPACITY);
	}


	public BooleanContainer(int capacity)
	{
		this.capacity = capacity;
		this.size = 0;
		this.array = capacity != 0 ? new boolean[capacity] : BooleanArrayUtil.BLANK;
	}


	public BooleanContainer(@NullDisallow boolean[] array)
	{
		this(array, false);
	}


	private BooleanContainer(@NullDisallow boolean[] array, boolean wrap)
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


	public boolean[] array()
	{
		return Arrays.copyOf(array, size);
	}


	public boolean[] array(int from, int to)
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
		array = BooleanArrayUtil.BLANK;
	}


	@Override
	public boolean contains(@NullDisallow Boolean value)
	{
		return index0(value, 0, size) >= 0;
	}


	public boolean contains(boolean value)
	{
		return index0(value, 0, size) >= 0;
	}


	@Override
	public int index(@NullDisallow Boolean value)
	{
		return index0(value, 0, size);
	}


	public int index(boolean value)
	{
		return index0(value, 0, size);
	}


	public int index(boolean value, int from, int to)
	{
		Validator.argument(from >= 0, "[from] (", from, ") is illegal");
		Validator.argument(to <= size, "[to] (", to, ") is illegal");

		return index0(value, from, to);
	}


	private int index0(boolean value, int from, int to)
	{
		for (int i = from; i < to; i++)
		{
			if (array[i] == value) return i;
		}

		return -1;
	}


	@Override
	public @NullDisallow Boolean get(int index)
	{
		Validator.argument(index >= 0 && index < size, "[index] (", index, ") is illegal");

		return array[index];
	}


	boolean get0(int index)
	{
		return array[index];
	}


	public void set(int index, boolean value)
	{
		Validator.argument(index >= 0 && index < size, "[index] (", index, ") is illegal");

		array[index] = value;
	}


	@Override
	public void add(@NullDisallow Boolean value)
	{
		add((boolean) value);
	}


	public void add(boolean value)
	{
		ensure(1);
		array[size] = value;
		size++;
	}


	public void add(boolean value, int index)
	{
		Validator.argument(index >= 0 && index <= size, "[index] (", index, ") is illegal");

		//
		ensure(1);
		boolean[] array2 = new boolean[capacity];

		if (index > 0) System.arraycopy(array, 0, array2, 0, index);

		if (index < size) System.arraycopy(array, index, array2, index + 1, size - index);

		array = array2;
		array[index] = value;
		size++;
	}


	public void add(boolean[] values)
	{
		add(values, 0, values.length);
	}


	public void add(boolean[] values, int from, int to)
	{
		int length = to - from;

		if (length != 0)
		{
			ensure(length);
			System.arraycopy(values, from, array, size, length);
			size += length;
		}
	}


	public void add(BooleanContainer values)
	{
		add(values.array, 0, values.size);
	}


	public void add(BooleanContainer values, int from, int to)
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
		array[size] = false;
	}


	@Override
	public void ensure(int count)
	{
		int requirement = size + count;

		if (requirement > capacity)
		{
			int capacity2 = calculate(requirement);
			boolean[] array2 = new boolean[capacity2];

			if (size > 0) System.arraycopy(array, 0, array2, 0, size);

			array = array2;
			capacity = capacity2;
		}
	}


	@Override
	public void sort()
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public Factories<BooleanContainer> factories()
	{
		return FACTORIES;
	}


	@Override
	public Iterator<Boolean> iterator()
	{
		return new Iterator<Boolean>()
		{
			int index = -1;

			@Override
			public boolean hasNext()
			{
				return index + 1 < size;
			}

			@Override
			public Boolean next()
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
	public boolean equals(@NullAllow BooleanContainer object)
	{
		return
			this == object
			||
			object != null
			&&
			this.size == object.size
			&&
			BooleanArrayUtil.equal(this.array, object.array, 0, this.size);
	}


	@Override
	public String toString()
	{
		return Arrays.toString(array());
	}


	//
	public static BooleanContainer wrap(boolean[] array)
	{
		return new BooleanContainer(array, true);
	}

}
