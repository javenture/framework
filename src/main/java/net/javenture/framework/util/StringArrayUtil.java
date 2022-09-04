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
import java.util.Objects;


/*
	2021/05/28
 */
final public class StringArrayUtil
{
	//
	final public static String[] BLANK = new String[0];
	final public static ICopyFactory<String[]> FACTORY_COPY = object -> object != null ? object.clone() : null;

	final public static IByteFactory<String[]> FACTORY_BYTE = new IByteFactory<>()
	{
		@Override
		public void toStream(@NullAllow String[] object, OutputStream destination)
			throws Exception
		{
			if (IByteFactory.setNullHeader(object, destination)) toStream0(object, 0, object.length, destination);
		}

		@Override
		public void toStream(@NullAllow String[] object, int from, int to, OutputStream destination)
			throws Exception
		{
			if (IByteFactory.setNullHeader(object, destination)) toStream0(object, from, to, destination);
		}

		private void toStream0(@NullDisallow String[] object, int from, int to, OutputStream destination)
			throws Exception
		{
			// count
			int count = to - from;
			Validator.argument(count >= 0, "[count] (", count, ") has invalid value");
			IntUtil.bytes(count, destination);

			// array
			for (int i = from; i < to; i++) StringUtil.FACTORY_BYTE.toStream(object[i], destination);
		}

		@Override
		public @NullAllow String[] fromStream(InputStream source)
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
					String[] result = new String[count];

					for (int i = 0; i < count; i++) result[i] = StringUtil.FACTORY_BYTE.fromStream(source);

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

	final public static IMurmur3HashFactory<String[]> FACTORY_MURMUR3HASH = new IMurmur3HashFactory<>()
	{
		@Override
		public void update(@NullAllow String[] object, Murmur3Hash destination)
		{
			if (IMurmur3HashFactory.updateNullHeader(object, destination)) update0(object, 0, object.length, destination);
		}

		@Override
		public void update(@NullAllow String[] object, int from, int to, Murmur3Hash destination)
		{
			if (IMurmur3HashFactory.updateNullHeader(object, destination)) update0(object, from, to, destination);
		}

		private void update0(@NullDisallow String[] object, int from, int to, Murmur3Hash destination)
		{
			int length = to - from;
			Validator.argument(length >= 0, "[length] (", length, ") has invalid value");
			IntUtil.murmur3hash(length, destination);

			for (int i = from; i < to; i++) StringUtil.FACTORY_MURMUR3HASH.update(object[i], destination);
		}
	};

	final public static IDatabaseFactory<String[], byte[]> FACTORY_DATABASE = new IDatabaseFactory<>()
	{
		@Override
		public DatabaseType getDatabaseType()
		{
			return DatabaseType.BYTES;
		}

		@Override
		public @NullAllow byte[] toDatabase(@NullAllow String[] object)
			throws Exception
		{
			if (object != null)
			{
				int count = object.length;

				if (count != 0)
				{
					int capacity = 0;

					for (String s : object)
					{
						capacity += s != null
							? Primitives.INT.DIMENSION + Primitives.INT.DIMENSION + Primitives.CHAR.DIMENSION * s.length()
							: Primitives.INT.DIMENSION;
					}

					try (ByteOutputStream destination = new ByteOutputStream(Primitives.INT.DIMENSION + capacity))
					{
						// count
						IntUtil.bytes(count, destination);

						// array
						for (String s : object) StringUtil.FACTORY_BYTE.toStream(s, destination);

						return destination.toBytes();
					}
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
		public @NullAllow String[] fromDatabase(@NullAllow byte[] object)
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
						String[] array = new String[count];

						for (int i = 0; i < count; i++) array[i] = StringUtil.FACTORY_BYTE.fromStream(source);

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

	final public static Factories<String[]> FACTORIES = new Factories<>(FACTORY_COPY, FACTORY_BYTE, FACTORY_MURMUR3HASH, FACTORY_DATABASE);


	//
	private StringArrayUtil()
	{
	}


	//
	@SuppressWarnings("ArrayEquality")
	public static boolean equal(@NullAllow String[] object0, @NullAllow String[] object1)
	{
		if (object0 == object1) return true;
		else if (object0 == null || object1 == null) return false;

		return
			object0.length == object1.length
			&&
			equal(object0, object1, 0, object0.length);
	}


	public static boolean equal(@NullDisallow String[] object0, @NullDisallow String[] object1, int from, int to)
	{
		for (int i = from; i < to; i++)
		{
			if (!Objects.equals(object0[i], object1[i])) return false;
		}

		return true;
	}

}
