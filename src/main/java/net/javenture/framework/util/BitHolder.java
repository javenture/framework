package net.javenture.framework.util;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.factory.IByteFactory;
import net.javenture.framework.factory.ICopyFactory;
import net.javenture.framework.factory.IFactoryObject;
import net.javenture.framework.factory.IMurmur3HashFactory;
import net.javenture.framework.hash.Murmur3Hash;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;


/*
	2020/02/19
 */
final public class BitHolder
	implements IFactoryObject<BitHolder>
{
	//
	final private static int DIMENSION = 32; // bit
	final private static int SHIFT = 5;
	final private static int MASK = (1 << SHIFT) - 1;
	final private static int[] BITS;
	final public static ICopyFactory<BitHolder> FACTORY_COPY = object -> object != null ? new BitHolder(object.SIZE, object.ARRAY.clone()) : null;

	final public static IByteFactory<BitHolder> FACTORY_BYTE = new IByteFactory<>()
	{
		@Override
		public void toStream(@NullAllow BitHolder object, OutputStream destination)
			throws Exception
		{
			if (IByteFactory.setNullHeader(object, destination))
			{
				IntUtil.bytes(object.SIZE, destination);
				IntArrayUtil.FACTORY_BYTE.toStream(object.ARRAY, destination);
			}
		}

		@Override
		public @NullAllow BitHolder fromStream(InputStream source)
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

	final public static IMurmur3HashFactory<BitHolder> FACTORY_MURMUR3HASH = (object, destination) ->
	{
		if (IMurmur3HashFactory.updateNullHeader(object, destination))
		{
			IntUtil.murmur3hash(object.SIZE, destination);
			IntArrayUtil.FACTORY_MURMUR3HASH.update(object.ARRAY, destination);
		}
	};

	final public static Factories<BitHolder> FACTORIES = new Factories<>(FACTORY_COPY, FACTORY_BYTE, FACTORY_MURMUR3HASH);


	//
	static
	{
		BITS = new int[DIMENSION];

		for (int i = 0; i < DIMENSION; i++) BITS[i] = 1 << (DIMENSION - 1 - i);
	}


	//
	final private int SIZE;
	final private @NullDisallow int[] ARRAY; // ! int[]


	//
	public BitHolder(int size)
	{
		int capacity = size >>> SHIFT;

		if (capacity << SHIFT < size) capacity++;

		//
		SIZE = size;
		ARRAY = new int[capacity];
	}


	public BitHolder(boolean[] array)
	{
		this(array.length);

		for (int i = 0; i < array.length; i++) set0(i, array[i]);
	}


	public BitHolder(boolean[] array, int from, int to)
	{
		this(to - from);

		for (int i = from; i < to; i++) set0(i, array[i]);
	}


	private BitHolder(int size, @NullDisallow int[] array)
	{
		Validator.notNull(array, "[array]");

		SIZE = size;
		ARRAY = array;
	}


	//
	public int size()
	{
		return SIZE;
	}


	public void init(boolean value)
	{
		int init = value ? -1 : 0; // -1 = 11111111 11111111 11111111 11111111 / 0 = 00000000 00000000 00000000 00000000
		Arrays.fill(ARRAY, init);
	}


	public int count(boolean value)
	{
		int result = 0;

		for (int index = 0; index < SIZE; index++)
		{
			if (get(index) == value) result++;
		}

		return result;
	}


	public boolean get(int index)
	{
		check(index);

		return (ARRAY[index >>> SHIFT] & BITS[index & MASK]) != 0;
	}


	public boolean get0(int index)
	{
		return (ARRAY[index >>> SHIFT] & BITS[index & MASK]) != 0;
	}


	public void set(int index, boolean value)
	{
		check(index);

		if (value) on0(index);
		else off0(index);
	}


	public void set0(int index, boolean value)
	{
		if (value) on0(index);
		else off0(index);
	}


	public void on(int index)
	{
		check(index);
		on0(index);
	}


	public void on0(int index)
	{
		ARRAY[index >>> SHIFT] |= BITS[index & MASK];
	}


	public void on(int... indexes)
	{
		for (int index : indexes) on(index);
	}


	public void on(IntContainer indexes)
	{
		for (int index : indexes) on(index);
	}


	public void off(int index)
	{
		check(index);
		off0(index);
	}


	public void off0(int index)
	{
		ARRAY[index >>> SHIFT] &= ~BITS[index & MASK];
	}


	public void off(int... indexes)
	{
		for (int index : indexes) off(index);
	}


	public void off(IntContainer indexes)
	{
		for (int index : indexes) off(index);
	}


	public void and(BitHolder holder)
	{
		Validator.argument(this.SIZE == holder.SIZE, "size mismatch");

		for (int i = 0; i < this.ARRAY.length; i++) this.ARRAY[i] &= holder.ARRAY[i];
	}


	public void or(BitHolder holder)
	{
		Validator.argument(this.SIZE == holder.SIZE, "size mismatch");

		for (int i = 0; i < this.ARRAY.length; i++) this.ARRAY[i] |= holder.ARRAY[i];
	}


	public void not()
	{
		for (int i = 0; i < this.ARRAY.length; i++) this.ARRAY[i] = ~this.ARRAY[i];
	}


	public boolean[] array()
	{
		boolean[] result = new boolean[SIZE];

		for (int i = 0; i < SIZE; i++) result[i] = get0(i);

		return result;
	}


	private void check(int index)
	{
		Validator.argument(index >= 0 && index < SIZE, "[index] (", index, ") is illegal");
	}


	@Override
	public Factories<BitHolder> factories()
	{
		return FACTORIES;
	}


	@Override
	public int hashCode()
	{
		Murmur3Hash result = new Murmur3Hash();
		FACTORY_MURMUR3HASH.update(this, result);

		return result.getInt();
	}


	@Override
	@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
	public boolean equals(@NullAllow Object object)
	{
		return equals((BitHolder) object);
	}


	@Override
	public boolean equals(@NullAllow BitHolder object)
	{
		return
			this == object
			||
			object != null
			&&
			this.SIZE == object.SIZE
			&&
			Arrays.equals(this.ARRAY, object.ARRAY);
	}


	//
	public static BitHolder and(BitHolder holder1, BitHolder holder2)
	{
		Validator.argument(holder1.SIZE == holder2.SIZE, "size mismatch");

		//
		int size = holder1.SIZE;
		int capacity = holder1.ARRAY.length;
		int[] data = new int[capacity];

		for (int i = 0; i < capacity; i++) data[i] = holder1.ARRAY[i] & holder2.ARRAY[i];

		return new BitHolder(size, data);
	}


	public static BitHolder or(BitHolder array1, BitHolder array2)
	{
		Validator.argument(array1.SIZE == array2.SIZE, "size mismatch");

		//
		int size = array1.SIZE;
		int capacity = array1.ARRAY.length;
		int[] data = new int[capacity];

		for (int i = 0; i < capacity; i++) data[i] = array1.ARRAY[i] | array2.ARRAY[i];

		return new BitHolder(size, data);
	}


	public static BitHolder not(BitHolder array)
	{
		int size = array.SIZE;
		int capacity = array.ARRAY.length;
		int[] data = new int[capacity];

		for (int i = 0; i < capacity; i++) data[i] = ~array.ARRAY[i];

		return new BitHolder(size, data);
	}


	//
	final public static BitHolder BLANK = new BitHolder(0);

}
