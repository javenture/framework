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
final public class CharArrayUtil
{
	//
	final public static char[] BLANK = new char[0];
	final public static ICopyFactory<char[]> FACTORY_COPY = object -> object != null ? object.clone() : null;

	final public static IByteFactory<char[]> FACTORY_BYTE = new IByteFactory<>()
	{
		@Override
		public void toStream(@NullAllow char[] object, OutputStream destination)
			throws Exception
		{
			if (IByteFactory.setNullHeader(object, destination)) toStream0(object, 0, object.length, destination);
		}

		@Override
		public void toStream(@NullAllow char[] object, int from, int to, OutputStream destination)
			throws Exception
		{
			if (IByteFactory.setNullHeader(object, destination)) toStream0(object, from, to, destination);
		}

		private void toStream0(@NullDisallow char[] object, int from, int to, OutputStream destination)
			throws Exception
		{
			// count
			int count = to - from;
			Validator.condition(count >= 0, "[count] (", count, ") has invalid value");
			IntUtil.bytes(count, destination);

			// array
			if (count != 0)
			{
				for (int i = from; i < to; i++) CharUtil.bytes(object[i], destination);
			}
		}

		@Override
		public @NullAllow char[] fromStream(InputStream source)
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
					char[] result = new char[count];

					for (int i = 0; i < count; i++) result[i] = CharUtil.parse(source);

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

	final public static IMurmur3HashFactory<char[]> FACTORY_MURMUR3HASH = new IMurmur3HashFactory<>()
	{
		@Override
		public void update(@NullAllow char[] object, Murmur3Hash destination)
		{
			if (IMurmur3HashFactory.updateNullHeader(object, destination)) update0(object, 0, object.length, destination);
		}

		@Override
		public void update(@NullAllow char[] object, int from, int to, Murmur3Hash destination)
		{
			if (IMurmur3HashFactory.updateNullHeader(object, destination)) update0(object, from, to, destination);
		}

		private void update0(@NullDisallow char[] object, int from, int to, Murmur3Hash destination)
		{
			int length = to - from;
			Validator.argument(length >= 0, "[length] (", length, ") has invalid value");
			IntUtil.murmur3hash(length, destination);

			if (length != 0)
			{
				for (int i = from; i < to; i++) CharUtil.murmur3hash(object[i], destination);
			}
		}
	};

	final public static IDatabaseFactory<char[], byte[]> FACTORY_DATABASE = new IDatabaseFactory<>()
	{
		@Override
		public DatabaseType getDatabaseType()
		{
			return DatabaseType.BYTES;
		}

		@Override
		public @NullAllow byte[] toDatabase(@NullAllow char[] object)
		{
			if (object != null)
			{
				int count = object.length;

				if (count != 0)
				{
					byte[] array = new byte[Primitives.INT.DIMENSION + Primitives.CHAR.DIMENSION * count];
					int index = 0;

					// count
					IntUtil.bytes(count, array, index);
					index += Primitives.INT.DIMENSION;

					// array
					for (char value : object)
					{
						CharUtil.bytes(value, array, index);
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
		public @NullAllow char[] fromDatabase(@NullAllow byte[] object)
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

	final public static Factories<char[]> FACTORIES = new Factories<>(FACTORY_COPY, FACTORY_BYTE, FACTORY_MURMUR3HASH, FACTORY_DATABASE);


	//
	private CharArrayUtil()
	{
	}


	//
	public static @NullDisallow String toString(@NullAllow char[] array)
	{
		return array != null
			? new String(array)
			: NullUtil.STRING;
	}


	public static @NullDisallow String toString(@NullAllow char[] array, int from, int to)
	{
		return array != null
			? new String(array, from, to - from)
			: NullUtil.STRING;
	}


	public static @NullDisallow String toString(@NullAllow char[] array, CharSequence enclose)
	{
		return array != null
			? enclose + new String(array) + enclose
			: NullUtil.STRING;
	}


	public static @NullDisallow String toString(@NullAllow char[] array, int from, int to, CharSequence enclose)
	{
		return array != null
			? enclose + new String(array, from, to - from) + enclose
			: NullUtil.STRING;
	}


	@SuppressWarnings("ArrayEquality")
	public static boolean equal(@NullAllow char[] object0, @NullAllow char[] object1)
	{
		if (object0 == object1)
		{
			return true;
		}
		else if (object0 != null && object1 != null)
		{
			int length = object0.length;

			return object1.length == length && equal(object0, object1, 0, length);
		}
		else
		{
			return false;
		}
	}


	public static boolean equal(@NullDisallow char[] object0, @NullDisallow char[] object1, int from, int to)
	{
		for (int i = from; i < to; i++)
		{
			if (object0[i] != object1[i]) return false;
		}

		return true;
	}

}
