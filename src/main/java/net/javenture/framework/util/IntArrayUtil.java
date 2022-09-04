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
	2020/04/30
 */
final public class IntArrayUtil
{
	//
	final public static int[] BLANK = new int[0];
	final public static ICopyFactory<int[]> FACTORY_COPY = object -> object != null ? object.clone() : null;

	final public static IByteFactory<int[]> FACTORY_BYTE = new IByteFactory<>()
	{
		@Override
		public void toStream(@NullAllow int[] object, OutputStream destination)
			throws Exception
		{
			if (IByteFactory.setNullHeader(object, destination)) toStream0(object, 0, object.length, destination);
		}

		@Override
		public void toStream(@NullAllow int[] object, int from, int to, OutputStream destination)
			throws Exception
		{
			if (IByteFactory.setNullHeader(object, destination)) toStream0(object, from, to, destination);
		}

		private void toStream0(@NullDisallow int[] object, int from, int to, OutputStream destination)
			throws Exception
		{
			// count
			int count = to - from;
			Validator.argument(count >= 0, "[count] (", count, ") has invalid value");
			IntUtil.bytes(count, destination);

			// array
			for (int i = from; i < to; i++) IntUtil.bytes(object[i], destination);
		}

		@Override
		public @NullAllow int[] fromStream(InputStream source)
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
					int[] result = new int[count];

					for (int i = 0; i < count; i++) result[i] = IntUtil.parse(source);

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

	final public static IMurmur3HashFactory<int[]> FACTORY_MURMUR3HASH = new IMurmur3HashFactory<>()
	{
		@Override
		public void update(@NullAllow int[] object, Murmur3Hash destination)
		{
			if (IMurmur3HashFactory.updateNullHeader(object, destination)) update0(object, 0, object.length, destination);
		}

		@Override
		public void update(@NullAllow int[] object, int from, int to, Murmur3Hash destination)
		{
			if (IMurmur3HashFactory.updateNullHeader(object, destination)) update0(object, from, to, destination);
		}

		private void update0(@NullDisallow int[] object, int from, int to, Murmur3Hash destination)
		{
			int length = to - from;
			Validator.argument(length >= 0, "[length] (", length, ") has invalid value");
			IntUtil.murmur3hash(length, destination); // !

			for (int i = from; i < to; i++) IntUtil.murmur3hash(object[i], destination);
		}
	};

	final public static Factories<int[]> FACTORIES = new Factories<>(FACTORY_COPY, FACTORY_BYTE, FACTORY_MURMUR3HASH);


	//
	private IntArrayUtil()
	{
	}


	//
	@SuppressWarnings("ArrayEquality")
	public static boolean equal(@NullAllow int[] object0, @NullAllow int[] object1)
	{
		if (object0 == object1) return true;
		else if (object0 == null || object1 == null) return false;

		return
			object0.length == object1.length
			&&
			equal(object0, object1, 0, object0.length);
	}


	public static boolean equal(@NullDisallow int[] object0, @NullDisallow int[] object1, int from, int to)
	{
		for (int i = from; i < to; i++)
		{
			if (object0[i] != object1[i]) return false;
		}

		return true;
	}


	public static int min(@NullDisallow int[] array, int length)
	{
		if (length == 0)
		{
			return -1;
		}
		else if (length == 1)
		{
			return 0;
		}
		else
		{
			int result = 0;
			int value0 = array[0];

			for (int i = 1; i < length; i++)
			{
				int value = array[i];

				if (value < value0)
				{
					result = i;
					value0 = value;
				}
			}

			return result;
		}
	}


	public static int max(@NullDisallow int[] array, int length)
	{
		if (length == 0)
		{
			return -1;
		}
		else if (length == 1)
		{
			return 0;
		}
		else
		{
			int result = 0;
			int value0 = array[0];

			for (int i = 1; i < length; i++)
			{
				int value = array[i];

				if (value > value0)
				{
					result = i;
					value0 = value;
				}
			}

			return result;
		}
	}

}
