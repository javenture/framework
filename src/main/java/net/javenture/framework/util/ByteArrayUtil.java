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


/*
	2021/05/28
 */
final public class ByteArrayUtil
{
	//
	final public static byte[] NULL = null;
	final public static byte[] BLANK = new byte[0];
	final public static ICopyFactory<byte[]> FACTORY_COPY = object -> object != null && object.length != 0 ? object.clone() : object;

	final public static IByteFactory<byte[]> FACTORY_BYTE = new IByteFactory<>()
	{
		@Override
		public void toStream(@NullAllow byte[] object, OutputStream destination)
			throws Exception
		{
			if (IByteFactory.setNullHeader(object, destination)) toStream0(object, 0, object.length, destination);
		}

		@Override
		public void toStream(@NullAllow byte[] object, int from, int to, OutputStream destination)
			throws Exception
		{
			if (IByteFactory.setNullHeader(object, destination)) toStream0(object, from, to, destination);
		}

		private void toStream0(@NullDisallow byte[] object, int from, int to, OutputStream destination)
			throws Exception
		{
			// length
			int length = to - from;
			Validator.argument(length >= 0, "[length] (", length, ") has invalid value");
			IntUtil.bytes(length, destination);

			// array
			if (length != 0) destination.write(object, from, length);
		}

		@Override
		public @NullAllow byte[] fromStream(InputStream source)
			throws Exception
		{
			if (IByteFactory.getNullHeader(source))
			{
				// length
				int length = IntUtil.parse(source);
				Validator.condition(length >= 0, "[length] (", length, ") has invalid value");

				// array
				if (length != 0)
				{
					byte[] result = new byte[length];
					source.read(result, 0, length);

					return result;
				}
				else
				{
					return BLANK;
				}
			}
			else
			{
				return null;
			}
		}
	};

	final public static IMurmur3HashFactory<byte[]> FACTORY_MURMUR3HASH = new IMurmur3HashFactory<>()
	{
		@Override
		public void update(@NullAllow byte[] object, Murmur3Hash destination)
		{
			if (IMurmur3HashFactory.updateNullHeader(object, destination)) update0(object, 0, object.length, destination);
		}

		@Override
		public void update(@NullAllow byte[] object, int from, int to, Murmur3Hash destination)
		{
			if (IMurmur3HashFactory.updateNullHeader(object, destination)) update0(object, from, to, destination);
		}

		private void update0(@NullDisallow byte[] object, int from, int to, Murmur3Hash destination)
		{
			int length = to - from;
			Validator.argument(length >= 0, "[length] (", length, ") has invalid value");
			IntUtil.murmur3hash(length, destination);

			if (length != 0) destination.update(object, from, length);
		}
	};

	final public static Factories<byte[]> FACTORIES = new Factories<>(FACTORY_COPY, FACTORY_BYTE, FACTORY_MURMUR3HASH);


	//
	private ByteArrayUtil()
	{
	}


	//
	@SuppressWarnings("ArrayEquality")
	public static boolean equal(@NullAllow byte[] object0, @NullAllow byte[] object1)
	{
		if (object0 == object1) return true;

		if (object0 == null || object1 == null) return false;

		return
			object0.length == object1.length
			&&
			equal0(object0, object1, 0, object0.length);
	}


	static boolean equal0(@NullDisallow byte[] object0, @NullDisallow byte[] object1, int from, int to)
	{
		for (int i = from; i < to; i++)
		{
			if (object0[i] != object1[i]) return false;
		}

		return true;
	}


	public static boolean contains(@NullDisallow byte[] array, @NullDisallow byte[] pattern, int position)
	{
		int length = pattern.length;

		switch (length)
		{
			case 1:
			{
				return array[position] == pattern[0];
			}

			case 2:
			{
				return
					array[position] == pattern[0]
					&&
					array[position + 1] == pattern[1];
			}

			case 4:
			{
				return
					array[position] == pattern[0]
					&&
					array[position + 1] == pattern[1]
					&&
					array[position + 2] == pattern[2]
					&&
					array[position + 3] == pattern[3];
			}

			case 8:
			{
				return
					array[position] == pattern[0]
					&&
					array[position + 1] == pattern[1]
					&&
					array[position + 2] == pattern[2]
					&&
					array[position + 3] == pattern[3]
					&&
					array[position + 4] == pattern[4]
					&&
					array[position + 5] == pattern[5]
					&&
					array[position + 6] == pattern[6]
					&&
					array[position + 7] == pattern[7];
			}

			default:
			{
				for (int i = 0; i < length; i++)
				{
					if (array[position + i] != pattern[i]) return false;
				}

				return true;
			}
		}
	}


	public static int index(@NullDisallow byte[] array, byte pattern, int from, int to)
	{
		for (int i = from; i < to; i++)
		{
			if (array[i] == pattern) return i;
		}

		return -1;
	}


	public static byte[] bytes(byte[]... arrays)
	{
		int capacity = 0;

		for (byte[] array : arrays) capacity += array.length;

		//
		byte[] result = new byte[capacity];
		int i = 0;

		for (byte[] array : arrays)
		{
			int l = array.length;
			System.arraycopy(array, 0, result, i, l);
			i += l;
		}

		return result;
	}

}
