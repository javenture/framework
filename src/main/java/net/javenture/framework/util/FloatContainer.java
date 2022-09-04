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
final public class FloatContainer
	extends AContainer<Float, FloatContainer>
{
	//
	final private static int CAPACITY = 1024;
	final public static IInstanceFactory<FloatContainer> FACTORY_INSTANCE = object -> object instanceof FloatContainer;
	final public static ICopyFactory<FloatContainer> FACTORY_COPY = object -> object != null ? new FloatContainer(object) : null;

	final public static IByteFactory<FloatContainer> FACTORY_BYTE = new IByteFactory<>()
	{
		@Override
		public void toStream(@NullAllow FloatContainer object, OutputStream destination)
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
					float[] array = object.array;

					for (int i = 0; i < count; i++) FloatUtil.bytes(array[i], destination);
				}
			}
		}

		@Override
		public @NullAllow FloatContainer fromStream(InputStream source)
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
					float[] array = new float[count];

					for (int i = 0; i < count; i++) array[i] = FloatUtil.parse(source);

					return new FloatContainer(array, true);
				}
				else
				{
					return new FloatContainer(FloatArrayUtil.BLANK, true);
				}
			}
			else
			{
				return null;
			}
		}
	};

	final public static IMurmur3HashFactory<FloatContainer> FACTORY_MURMUR3HASH = (object, destination) ->
	{
		if (IMurmur3HashFactory.updateNullHeader(object, destination))
		{
			IntUtil.murmur3hash(object.size, destination);
			FloatArrayUtil.FACTORY_MURMUR3HASH.update(object.array, 0, object.size, destination);
		}
	};

	final public static IDatabaseFactory<FloatContainer, byte[]> FACTORY_DATABASE = new IDatabaseFactory<>()
	{
		@Override
		public DatabaseType getDatabaseType()
		{
			return DatabaseType.BYTES;
		}

		@Override
		public @NullAllow byte[] toDatabase(@NullAllow FloatContainer object)
		{
			if (object != null)
			{
				int count = object.size;

				if (count != 0)
				{
					byte[] array = new byte[Primitives.INT.DIMENSION + Primitives.FLOAT.DIMENSION * count];
					int index = 0;

					// count
					IntUtil.bytes(count, array, index);
					index += Primitives.INT.DIMENSION;

					// array
					for (int i = 0; i < count; i++)
					{
						FloatUtil.bytes(object.array[i], array, index);
						index += Primitives.FLOAT.DIMENSION;
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
		public @NullAllow FloatContainer fromDatabase(@NullAllow byte[] object)
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
						float[] array = new float[count];

						for (int i = 0; i < count; i++) array[i] = FloatUtil.parse(source);

						return new FloatContainer(array, true);
					}
					else
					{
						return new FloatContainer(FloatArrayUtil.BLANK, true);
					}
				}
			}
			else
			{
				return null;
			}
		}
	};

	final public static Factories<FloatContainer> FACTORIES = new Factories<>(FACTORY_INSTANCE, FACTORY_COPY, FACTORY_BYTE, FACTORY_MURMUR3HASH, FACTORY_DATABASE);


	//
	private int capacity;
	private int size;
	private @NullDisallow float[] array;


	//
	private FloatContainer(@NullDisallow FloatContainer source)
	{
		Validator.notNull(source, "[source]");

		this.capacity = source.capacity;
		this.size = source.size;
		this.array = source.array.clone();
	}


	public FloatContainer()
	{
		this(CAPACITY);
	}


	public FloatContainer(int capacity)
	{
		this.capacity = capacity;
		this.size = 0;
		this.array = capacity != 0 ? new float[capacity] : FloatArrayUtil.BLANK;
	}


	public FloatContainer(@NullDisallow float[] array)
	{
		this(array, false);
	}


	private FloatContainer(@NullDisallow float[] array, boolean wrap)
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


	public float[] array()
	{
		return Arrays.copyOf(array, size);
	}


	public float[] array(int from, int to)
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
		array = FloatArrayUtil.BLANK;
	}


	@Override
	public boolean contains(@NullDisallow Float value)
	{
		return index0(value, 0, size) >= 0;
	}


	public boolean contains(float value)
	{
		return index0(value, 0, size) >= 0;
	}


	@Override
	public int index(@NullDisallow Float value)
	{
		return index0(value, 0, size);
	}


	public int index(float value)
	{
		return index0(value, 0, size);
	}


	public int index(float value, int from, int to)
	{
		Validator.argument(from >= 0, "[from] (", from, ") is illegal");
		Validator.argument(to <= size, "[to] (", to, ") is illegal");

		return index0(value, from, to);
	}


	private int index0(float value, int from, int to)
	{
		for (int i = from; i < to; i++)
		{
			if (array[i] == value) return i;
		}

		return -1;
	}


	@Override
	public @NullDisallow Float get(int index)
	{
		Validator.argument(index >= 0 && index < size, "[index] (", index, ") is illegal");

		return array[index];
	}


	float get0(int index)
	{
		return array[index];
	}


	public void set(int index, float value)
	{
		Validator.argument(index >= 0 && index < size, "[index] (", index, ") is illegal");

		array[index] = value;
	}


	@Override
	public void add(@NullDisallow Float value)
	{
		add((float) value);
	}


	public void add(float value)
	{
		ensure(1);
		array[size] = value;
		size++;
	}


	public void add(float value, int index)
	{
		Validator.argument(index >= 0 && index <= size, "[index] (", index, ") is illegal");

		//
		ensure(1);
		float[] array2 = new float[capacity];

		if (index > 0) System.arraycopy(array, 0, array2, 0, index);

		if (index < size) System.arraycopy(array, index, array2, index + 1, size - index);

		array = array2;
		array[index] = value;
		size++;
	}


	public void add(float[] values)
	{
		add(values, 0, values.length);
	}


	public void add(float[] values, int from, int to)
	{
		int length = to - from;

		if (length != 0)
		{
			ensure(length);
			System.arraycopy(values, from, array, size, length);
			size += length;
		}
	}


	public void add(FloatContainer container)
	{
		add(container.array, 0, container.size);
	}


	public void add(FloatContainer container, int from, int to)
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


	public float min(float init)
	{
		if (size != 0)
		{
			float result = array[0];

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


	public float max(float init)
	{
		if (size != 0)
		{
			float result = array[size - 1];

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
			float[] array2 = new float[capacity2];

			if (size > 0) System.arraycopy(array, 0, array2, 0, size);

			array = array2;
			capacity = capacity2;
		}
	}


	@Override
	public Factories<FloatContainer> factories()
	{
		return FACTORIES;
	}


	@Override
	public Iterator<Float> iterator()
	{
		return new Iterator<Float>()
		{
			int index = -1;

			@Override
			public boolean hasNext()
			{
				return index + 1 < size;
			}

			@Override
			public Float next()
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
	public boolean equals(@NullAllow FloatContainer object)
	{
		return
			this == object
			||
			object != null
			&&
			this.size == object.size
			&&
			FloatArrayUtil.equal(this.array, object.array, 0, this.size);
	}


	@Override
	public String toString()
	{
		return Arrays.toString(array());
	}


	//
	public static FloatContainer wrap(float[] array)
	{
		return new FloatContainer(array, true);
	}

}
