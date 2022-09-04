package net.javenture.framework.util;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullContains;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.factory.IByteFactory;
import net.javenture.framework.factory.ICopyFactory;
import net.javenture.framework.factory.IMurmur3HashFactory;
import net.javenture.framework.factory.IStringFactory;
import net.javenture.framework.hash.Murmur3Hash;
import net.javenture.framework.utf8.Utf8Util;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;


/*
	2021/06/01
 */
final public class StringUtil
{
	//
	final public static ICopyFactory<String> FACTORY_COPY = object -> object;

	final public static IByteFactory<String> FACTORY_BYTE = new IByteFactory<>()
	{
		@Override
		public void toStream(@NullAllow String object, OutputStream destination)
			throws Exception
		{
			if (IByteFactory.setNullHeader(object, destination)) toStream0(object, 0, object.length(), destination);
		}

		@Override
		public void toStream(@NullAllow String object, int from, int to, OutputStream destination)
			throws Exception
		{
			if (IByteFactory.setNullHeader(object, destination)) toStream0(object, from, to, destination);
		}

		private void toStream0(@NullDisallow String object, int from, int to, OutputStream destination)
			throws Exception
		{
			// count
			int count = to - from;
			Validator.argument(count >= 0, "[count] (", count, ") has invalid value");
			IntUtil.bytes(count, destination);

			// object
			for (int i = from; i < to; i++) CharUtil.bytes(object.charAt(i), destination);
		}

		@Override
		public @NullAllow String fromStream(InputStream source)
			throws Exception
		{
			if (IByteFactory.getNullHeader(source))
			{
				// count
				int count = IntUtil.parse(source);
				Validator.condition(count >= 0, "[count] (", count, ") has invalid value");

				// object
				if (count != 0)
				{
					char[] array = new char[count];

					for (int i = 0; i < count; i++) array[i] = CharUtil.parse(source);

					return new String(array);
				}
				else
				{
					return "";
				}
			}
			else
			{
				return null;
			}
		}
	};

	final public static IStringFactory<String> FACTORY_STRING = new IStringFactory<>()
	{
		@Override
		public @NullDisallow String toString(@NullDisallow String object) // !
		{
			Validator.notNull(object, "[object]");

			return object;
		}

		@Override
		public @NullDisallow StringParser parser(@NullAllow String init, boolean nullable, boolean trim)
		{
			return new StringParser(init, nullable, trim);
		}
	};

	final public static IMurmur3HashFactory<String> FACTORY_MURMUR3HASH = new IMurmur3HashFactory<>()
	{
		@Override
		public void update(@NullAllow String object, Murmur3Hash destination)
		{
			if (IMurmur3HashFactory.updateNullHeader(object, destination)) update0(object, 0, object.length(), destination);
		}

		@Override
		public void update(@NullAllow String object, int from, int to, Murmur3Hash destination)
		{
			if (IMurmur3HashFactory.updateNullHeader(object, destination)) update0(object, from, to, destination);
		}

		private void update0(@NullDisallow String object, int from, int to, Murmur3Hash destination)
		{
			int length = to - from;
			Validator.argument(length >= 0, "[length] (", length, ") has invalid value");
			IntUtil.murmur3hash(length, destination); // !

			for (int i = from; i < to; i++) CharUtil.murmur3hash(object.charAt(i), destination);
		}
	};

	final public static Factories<String> FACTORIES = new Factories<>(FACTORY_COPY, FACTORY_BYTE, FACTORY_STRING, FACTORY_MURMUR3HASH);


	//
	private StringUtil()
	{
	}


	//
	public static byte[] bytes(char value)                                              // ???
	{
		return Utf8Util.bytes(value);
	}


	public static byte[] bytes(char[] value)                                            // ???
	{
		return Utf8Util.bytes(value);
	}


	public static byte[] bytes(@NullDisallow CharSequence value)                        // ???
	{
		return Utf8Util.bytes(value);
	}


	public static @NullDisallow String toString(@NullAllow CharSequence sequence, CharSequence enclose)
	{
		return sequence != null ? "" + enclose + sequence + enclose : NullUtil.STRING;
	}


	public static @NullDisallow String toString(@NullAllow Object object)
	{
		return object != null ? object.toString() : NullUtil.STRING;
	}


	public static String combine(@NullContains Object... objects)
	{
		StringFragmenter fragmenter = new StringFragmenter(objects.length);

		for (Object object : objects) fragmenter.wrap(object != null ? object.toString().toCharArray() : NullUtil.ARRAY);

		return fragmenter.toString();
	}


	/*
	 * String -> hashcode
	 */
	public static int hashcode(@NullAllow String value)
	{
		return value != null ? value.hashCode() : 0;
	}


	/*
	 * String -> murmur3hash
	 */
	public static Murmur3Hash murmur3hash(@NullAllow String value)
	{
		return FACTORY_MURMUR3HASH.hash(value);
	}


	public static void murmur3hash(@NullAllow String value, Murmur3Hash destination)
	{
		FACTORY_MURMUR3HASH.update(value, destination);
	}


	/*
	 * equal
	 */
	public static boolean equal(@NullAllow String value0, @NullAllow String value1)
	{
		return Objects.equals(value0, value1);
	}


	public static boolean equal(@NullAllow String value0, @NullAllow char[] value1)
	{
		if (value0 != null && value1 != null)
		{
			int length = value0.length();

			if (value1.length == length)
			{
				for (int i = 0; i < length; i++)
				{
					if (value0.charAt(i) != value1[i]) return false;
				}

				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return value0 == null && value1 == null;
		}
	}


	public static boolean equal(@NullAllow CharSequence value0, @NullAllow CharSequence value1)
	{
		if (value0 != null && value1 != null)
		{
			int length = value0.length();

			if (value1.length() == length)
			{
				for (int i = 0; i < length; i++)
				{
					if (value0.charAt(i) != value1.charAt(i)) return false;
				}

				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return value0 == null && value1 == null;
		}
	}


	/*
	 * prefix, suffix
	 */
	public static <T> String prefix(@NullDisallow T value, IPrefix<T> prefix)
	{
		return prefix.process(value);
	}


	public static <T> String suffix(@NullDisallow T value, ISuffix<T> suffix)
	{
		return suffix.process(value);
	}


	//
	@FunctionalInterface
	public interface IPrefix<T>
	{
		String process(@NullDisallow T value);
	}


	@FunctionalInterface
	public interface ISuffix<T>
	{
		String process(@NullDisallow T value);
	}

}
