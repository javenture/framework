package net.javenture.framework.util;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.factory.IByteFactory;
import net.javenture.framework.factory.ICopyFactory;
import net.javenture.framework.factory.IDatabaseFactory;
import net.javenture.framework.factory.IMurmur3HashFactory;
import net.javenture.framework.firebird.DatabaseType;
import net.javenture.framework.hash.Murmur3Hash;

import java.io.InputStream;
import java.io.OutputStream;


/*
	2021/05/30
 */
final public class DoubleArrayUtil
{
	//
	final public static double[] BLANK = new double[0];
	final public static ICopyFactory<double[]> FACTORY_COPY = object -> object != null ? object.clone() : null;

	final public static IByteFactory<double[]> FACTORY_BYTE = new IByteFactory<>()
	{
		@Override
		public void toStream(@NullAllow double[] object, OutputStream destination)
			throws Exception
		{
			if (IByteFactory.setNullHeader(object, destination)) toStream0(object, 0, object.length, destination);
		}

		@Override
		public void toStream(@NullAllow double[] object, int from, int to, OutputStream destination)
			throws Exception
		{
			if (IByteFactory.setNullHeader(object, destination)) toStream0(object, from, to, destination);
		}

		private void toStream0(@NullDisallow double[] object, int from, int to, OutputStream destination)
			throws Exception
		{
			// count
			int count = to - from;
			Validator.argument(count >= 0, "[count] (", count, ") has invalid value");
			IntUtil.bytes(count, destination);

			// array
			if (count > 0)
			{
				for (int i = from; i < to; i++) DoubleUtil.bytes(object[i], destination);
			}
		}

		@Override
		public @NullAllow double[] fromStream(InputStream source)
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
					double[] result = new double[count];

					for (int i = 0; i < count; i++) result[i] = DoubleUtil.parse(source);

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

	final public static IMurmur3HashFactory<double[]> FACTORY_MURMUR3HASH = new IMurmur3HashFactory<>()
	{
		@Override
		public void update(@NullAllow double[] object, Murmur3Hash destination)
		{
			if (IMurmur3HashFactory.updateNullHeader(object, destination)) update0(object, 0, object.length, destination);
		}

		@Override
		public void update(@NullAllow double[] object, int from, int to, Murmur3Hash destination)
		{
			if (IMurmur3HashFactory.updateNullHeader(object, destination)) update0(object, from, to, destination);
		}

		private void update0(@NullDisallow double[] object, int from, int to, Murmur3Hash destination)
		{
			int length = to - from;
			Validator.argument(length >= 0, "[length] (", length, ") has invalid value");
			IntUtil.murmur3hash(length, destination);

			if (length != 0)
			{
				for (int i = from; i < to; i++) DoubleUtil.murmur3hash(object[i], destination);
			}
		}
	};

	final public static IDatabaseFactory<double[], byte[]> FACTORY_DATABASE = new IDatabaseFactory<>()
	{
		@Override
		public DatabaseType getDatabaseType()
		{
			return DatabaseType.BYTES;
		}

		@Override
		public @NullAllow byte[] toDatabase(@NullAllow double[] object)
		{
			if (object != null)
			{
				int count = object.length;

				if (count != 0)
				{
					byte[] array = new byte[Primitives.INT.DIMENSION + Primitives.DOUBLE.DIMENSION * count];
					int index = 0;

					// count
					IntUtil.bytes(count, array, index);
					index += Primitives.INT.DIMENSION;

					// array
					for (double value : object)
					{
						DoubleUtil.bytes(value, array, index);
						index += Primitives.DOUBLE.DIMENSION;
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
		public @NullAllow double[] fromDatabase(@NullAllow byte[] object)
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
						double[] array = new double[count];

						for (int i = 0; i < count; i++) array[i] = DoubleUtil.parse(source);

						return array;
					}
					else
					{
						return BLANK;
					}
				}
			}
			else
			{
				return null;
			}
		}
	};

	final public static Factories<double[]> FACTORIES = new Factories<>(FACTORY_COPY, FACTORY_BYTE, FACTORY_MURMUR3HASH, FACTORY_DATABASE);


	//
	private DoubleArrayUtil()
	{
	}


	//
	@SuppressWarnings("ArrayEquality")
	public static boolean equal(@NullAllow double[] object0, @NullAllow double[] object1)
	{
		if (object0 == object1) return true;
		else if (object0 == null || object1 == null) return false;

		return
			object0.length == object1.length
			&&
			equal(object0, object1, 0, object0.length);
	}


	public static boolean equal(@NullDisallow double[] object0, @NullDisallow double[] object1, int from, int to)
	{
		for (int i = from; i < to; i++)
		{
			if (object0[i] != object1[i]) return false;
		}

		return true;
	}


	public static int min(@NullDisallow double[] array, int length)
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
			double value0 = array[0];

			for (int i = 1; i < length; i++)
			{
				double value = array[i];

				if (value < value0)
				{
					result = i;
					value0 = value;
				}
			}

			return result;
		}
	}


	public static int max(@NullDisallow double[] array, int length)
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
			double value0 = array[0];

			for (int i = 1; i < length; i++)
			{
				double value = array[i];

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
