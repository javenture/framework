package net.javenture.framework.util;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.factory.IByteFactory;
import net.javenture.framework.factory.ICopyFactory;
import net.javenture.framework.factory.IInstanceFactory;
import net.javenture.framework.factory.IMurmur3HashFactory;
import net.javenture.framework.factory.IStringFactory;
import net.javenture.framework.hash.Murmur3Hash;

import java.io.InputStream;
import java.io.OutputStream;


/*
	2021/05/28
 */
final public class LongUtil
{
	//
	final private static long VALUE_INTEGER_MIN = Integer.MIN_VALUE;
	final private static long VALUE_INTEGER_MAX = Integer.MAX_VALUE;
	final private static long VALUE_SHORT_MIN = Short.MIN_VALUE;
	final private static long VALUE_SHORT_MAX = Short.MAX_VALUE;
	final public static IInstanceFactory<Long> FACTORY_INSTANCE = object -> object instanceof Long;
	final public static ICopyFactory<Long> FACTORY_COPY = object -> object;

	final public static IByteFactory<Long> FACTORY_BYTE = new IByteFactory<>()
	{
		@Override
		public void toStream(@NullAllow Long object, OutputStream destination)
			throws Exception
		{
			if (IByteFactory.setNullHeader(object, destination)) bytes(object, destination);
		}

		@Override
		public @NullAllow Long fromStream(InputStream source)
			throws Exception
		{
			return IByteFactory.getNullHeader(source) ? parse(source) : null;
		}
	};

	final public static IStringFactory<Long> FACTORY_STRING = new IStringFactory<>()
	{
		@Override
		public @NullDisallow String toString(@NullAllow Long object)
		{
			return object != null ? "" + object : NullUtil.STRING;
		}

		@Override
		public @NullDisallow LongParser parser(@NullAllow Long init, boolean nullable, boolean trim)
		{
			return new LongParser(init, nullable, trim);
		}
	};

	final public static IMurmur3HashFactory<Long> FACTORY_MURMUR3HASH = (object, destination) ->
	{
		if (IMurmur3HashFactory.updateNullHeader(object, destination)) murmur3hash(object, destination);
	};

	final public static Factories<Long> FACTORIES = new Factories<>(FACTORY_INSTANCE, FACTORY_COPY, FACTORY_BYTE, FACTORY_STRING, FACTORY_MURMUR3HASH);


	//
	private LongUtil()
	{
	}


	/*
	 * long -> byte[]
	 */
	public static byte[] bytes(long value)
	{
		return new byte[]
		{
			(byte) (value >> 56),
			(byte) (value >> 48),
			(byte) (value >> 40),
			(byte) (value >> 32),
			(byte) (value >> 24),
			(byte) (value >> 16),
			(byte) (value >>  8),
			(byte) (value)
		};
	}


	public static void bytes(long value, byte[] destination, int index)
	{
		destination[index]     = (byte) (value >> 56);
		destination[index + 1] = (byte) (value >> 48);
		destination[index + 2] = (byte) (value >> 40);
		destination[index + 3] = (byte) (value >> 32);
		destination[index + 4] = (byte) (value >> 24);
		destination[index + 5] = (byte) (value >> 16);
		destination[index + 6] = (byte) (value >>  8);
		destination[index + 7] = (byte) (value);
	}


	public static void bytes(long value, OutputStream destination)
		throws Exception
	{
		destination.write((int) (value >> 56));
		destination.write((int) (value >> 48));
		destination.write((int) (value >> 40));
		destination.write((int) (value >> 32));
		destination.write((int) (value >> 24));
		destination.write((int) (value >> 16));
		destination.write((int) (value >>  8));
		destination.write((int) (value));
	}


	/*
	 * byte[] -> long
	 */
	public static long parse(byte b0, byte b1, byte b2, byte b3, byte b4, byte b5, byte b6, byte b7)
	{
		return
			(b0 & 0xffL) << 56
			|
			(b1 & 0xffL) << 48
			|
			(b2 & 0xffL) << 40
			|
			(b3 & 0xffL) << 32
			|
			(b4 & 0xffL) << 24
			|
			(b5 & 0xffL) << 16
			|
			(b6 & 0xffL) <<  8
			|
			(b7 & 0xffL);
	}


	public static long parse(byte[] source, int index)
		throws Exception
	{
		Validator.condition(index >= 0 && index + 7 < source.length, "[index] (", index, ") is illegal");

		byte b0 = source[index];
		byte b1 = source[index + 1];
		byte b2 = source[index + 2];
		byte b3 = source[index + 3];
		byte b4 = source[index + 4];
		byte b5 = source[index + 5];
		byte b6 = source[index + 6];
		byte b7 = source[index + 7];

		return parse(b0, b1, b2, b3, b4, b5, b6, b7);
	}


	public static long parse(byte[] source, int index, long init)
	{
		if (index >= 0 && index + 7 < source.length)
		{
			byte b0 = source[index];
			byte b1 = source[index + 1];
			byte b2 = source[index + 2];
			byte b3 = source[index + 3];
			byte b4 = source[index + 4];
			byte b5 = source[index + 5];
			byte b6 = source[index + 6];
			byte b7 = source[index + 7];

			return parse(b0, b1, b2, b3, b4, b5, b6, b7);
		}

		return init;
	}


	public static @NullAllow Long parse(byte[] source, int index, @NullAllow Long init)
	{
		if (index >= 0 && index + 7 < source.length)
		{
			byte b0 = source[index];
			byte b1 = source[index + 1];
			byte b2 = source[index + 2];
			byte b3 = source[index + 3];
			byte b4 = source[index + 4];
			byte b5 = source[index + 5];
			byte b6 = source[index + 6];
			byte b7 = source[index + 7];

			return parse(b0, b1, b2, b3, b4, b5, b6, b7);
		}

		return init;
	}


	public static long parse(InputStream source)
		throws Exception
	{
		int i0 = source.read();
		int i1 = source.read();
		int i2 = source.read();
		int i3 = source.read();
		int i4 = source.read();
		int i5 = source.read();
		int i6 = source.read();
		int i7 = source.read();

		if (i0 != -1 && i1 != -1 && i2 != -1 && i3 != -1 && i4 != -1 && i5 != -1 && i6 != -1 && i7 != -1) return parse((byte) i0, (byte) i1, (byte) i2, (byte) i3, (byte) i4, (byte) i5, (byte) i6, (byte) i7);
		else throw new Exception("insufficient data");
	}


	public static long parse(InputStream source, long init)
	{
		try
		{
			int i0 = source.read();
			int i1 = source.read();
			int i2 = source.read();
			int i3 = source.read();
			int i4 = source.read();
			int i5 = source.read();
			int i6 = source.read();
			int i7 = source.read();

			if (i0 != -1 && i1 != -1 && i2 != -1 && i3 != -1 && i4 != -1 && i5 != -1 && i6 != -1 && i7 != -1) return parse((byte) i0, (byte) i1, (byte) i2, (byte) i3, (byte) i4, (byte) i5, (byte) i6, (byte) i7);
		}
		catch (Exception ignore)
		{
		}

		return init;
	}


	public static @NullAllow Long parse(InputStream source, @NullAllow Long init)
	{
		try
		{
			int i0 = source.read();
			int i1 = source.read();
			int i2 = source.read();
			int i3 = source.read();
			int i4 = source.read();
			int i5 = source.read();
			int i6 = source.read();
			int i7 = source.read();

			if (i0 != -1 && i1 != -1 && i2 != -1 && i3 != -1 && i4 != -1 && i5 != -1 && i6 != -1 && i7 != -1) return parse((byte) i0, (byte) i1, (byte) i2, (byte) i3, (byte) i4, (byte) i5, (byte) i6, (byte) i7);
		}
		catch (Exception ignore)
		{
		}

		return init;
	}


	/*
	 * CharSequence -> long
	 */
	public static @NullAllow Long parse(@NullDisallow CharSequence sequence)
		throws Exception
	{
		return parse(sequence, false);
	}


	public static @NullAllow Long parse(@NullDisallow CharSequence sequence, boolean trim)
		throws Exception
	{
		if (sequence != null)
		{
			if (NullUtil.notNull(sequence, trim))
			{
				Long result = primitive(sequence, trim);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		throw new Exception("parse failed: " + StringUtil.toString(sequence, "\""));
	}


	public static long parse(@NullAllow CharSequence sequence, long init)
	{
		return parse(sequence, init, false);
	}


	public static long parse(@NullAllow CharSequence sequence, long init, boolean trim)
	{
		if (sequence != null)
		{
			Long result = primitive(sequence, trim);

			if (result != null) return result;
		}

		return init;
	}


	public static @NullAllow Long parse(@NullAllow CharSequence sequence, @NullAllow Long init)
	{
		return parse(sequence, init, false);
	}


	public static @NullAllow Long parse(@NullAllow CharSequence sequence, @NullAllow Long init, boolean trim)
	{
		if (sequence != null)
		{
			if (NullUtil.notNull(sequence, trim))
			{
				Long result = primitive(sequence, trim);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		return init;
	}


	public static @NullAllow Long parse(@NullDisallow CharSequence sequence, int from, int to)
		throws Exception
	{
		return parse(sequence, from, to, false);
	}


	public static @NullAllow Long parse(@NullDisallow CharSequence sequence, int from, int to, boolean trim)
		throws Exception
	{
		if (sequence != null)
		{
			if (NullUtil.notNull(sequence, from, to, trim))
			{
				Long result = primitive(sequence, from, to, trim);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		throw new Exception("parse failed: " + StringUtil.toString(sequence, "\""));
	}


	public static long parse(@NullAllow CharSequence sequence, int from, int to, long init)
	{
		return parse(sequence, from, to, init, false);
	}


	public static long parse(@NullAllow CharSequence sequence, int from, int to, long init, boolean trim)
	{
		if (sequence != null)
		{
			Long result = primitive(sequence, from, to, trim);

			if (result != null) return result;
		}

		return init;
	}


	public static @NullAllow Long parse(@NullAllow CharSequence sequence, int from, int to, @NullAllow Long init)
	{
		return parse(sequence, from, to, init, false);
	}


	public static @NullAllow Long parse(@NullAllow CharSequence sequence, int from, int to, @NullAllow Long init, boolean trim)
	{
		if (sequence != null)
		{
			if (NullUtil.notNull(sequence, from, to, trim))
			{
				Long result = primitive(sequence, from, to, trim);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		return init;
	}


	public static @NullAllow Long primitive(@NullDisallow CharSequence sequence)
	{
		return primitive(sequence, 0, sequence.length(), false);
	}


	public static @NullAllow Long primitive(@NullDisallow CharSequence sequence, boolean trim)
	{
		return primitive(sequence, 0, sequence.length(), trim);
	}


	public static @NullAllow Long primitive(@NullDisallow CharSequence sequence, int from, int to)
	{
		return primitive(sequence, from, to, false);
	}


	public static @NullAllow Long primitive(@NullDisallow CharSequence sequence, int from, int to, boolean trim)
	{
		if (trim)
		{
			while (from < to && sequence.charAt(from) <= ' ') from++;

			while (from < to && sequence.charAt(to - 1) <= ' ') to--;
		}

		if (from < to)
		{
			boolean negative = sequence.charAt(from) == '-';
			boolean positive = sequence.charAt(from) == '+';
			int index = negative || positive ? from + 1 : from;

			if (index == to) return null;

			int digit = CharUtil.digit(sequence.charAt(index++));

			if (digit == -1) return null;

			long result = -digit;
			long limit = Long.MIN_VALUE / 10;

			while (index < to)
			{
				digit = CharUtil.digit(sequence.charAt(index++));

				if (digit == -1 || result < limit) return null;

				result *= 10;

				if (result < Long.MIN_VALUE + digit) return null;

				result -= digit;
			}

			if (negative) return result;
			else return result != Long.MIN_VALUE ? -result : null;
		}
		else
		{
			return null;
		}
	}


	/*
	 * char[] -> long
	 */
	public static @NullAllow Long parse(@NullDisallow char[] array)
		throws Exception
	{
		return parse(array, false);
	}


	public static @NullAllow Long parse(@NullDisallow char[] array, boolean trim)
		throws Exception
	{
		if (array != null)
		{
			if (NullUtil.notNull(array, trim))
			{
				Long result = primitive(array, trim);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		throw new Exception("parse failed: " + CharArrayUtil.toString(array, "\""));
	}


	public static long parse(@NullAllow char[] array, long init)
	{
		return parse(array, init, false);
	}


	public static long parse(@NullAllow char[] array, long init, boolean trim)
	{
		if (array != null)
		{
			Long result = primitive(array, trim);

			if (result != null) return result;
		}

		return init;
	}


	public static @NullAllow Long parse(@NullAllow char[] array, @NullAllow Long init)
	{
		return parse(array, init, false);
	}


	public static @NullAllow Long parse(@NullAllow char[] array, @NullAllow Long init, boolean trim)
	{
		if (array != null)
		{
			if (NullUtil.notNull(array, trim))
			{
				Long result = primitive(array, trim);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		return init;
	}


	public static @NullAllow Long parse(@NullDisallow char[] array, int from, int to)
		throws Exception
	{
		return parse(array, from, to, false);
	}


	public static @NullAllow Long parse(@NullDisallow char[] array, int from, int to, boolean trim)
		throws Exception
	{
		if (array != null)
		{
			if (NullUtil.notNull(array, from, to, trim))
			{
				Long result = primitive(array, from, to, trim);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		throw new Exception("parse failed: " + CharArrayUtil.toString(array, "\""));
	}


	public static long parse(@NullAllow char[] array, int from, int to, long init)
	{
		return parse(array, from, to, init, false);
	}


	public static long parse(@NullAllow char[] array, int from, int to, long init, boolean trim)
	{
		if (array != null)
		{
			Long result = primitive(array, from, to, trim);

			if (result != null) return result;
		}

		return init;
	}


	public static @NullAllow Long parse(@NullAllow char[] array, int from, int to, @NullAllow Long init)
	{
		return parse(array, from, to, init, false);
	}


	public static @NullAllow Long parse(@NullAllow char[] array, int from, int to, @NullAllow Long init, boolean trim)
	{
		if (array != null)
		{
			if (NullUtil.notNull(array, from, to, trim))
			{
				Long result = primitive(array, from, to, trim);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		return init;
	}


	public static @NullAllow Long primitive(@NullDisallow char[] array)
	{
		return primitive(array, 0, array.length, false);
	}


	public static @NullAllow Long primitive(@NullDisallow char[] array, boolean trim)
	{
		return primitive(array, 0, array.length, trim);
	}


	public static @NullAllow Long primitive(@NullDisallow char[] array, int from, int to)
	{
		return primitive(array, from, to, false);
	}


	public static @NullAllow Long primitive(@NullDisallow char[] array, int from, int to, boolean trim)
	{
		if (trim)
		{
			while (from < to && array[from] <= ' ') from++;

			while (from < to && array[to - 1] <= ' ') to--;
		}

		if (from < to)
		{
			boolean negative = array[from] == '-';
			boolean positive = array[from] == '+';
			int index = negative || positive ? from + 1 : from;

			if (index == to) return null;

			int digit = CharUtil.digit(array[index++]);

			if (digit == -1) return null;

			long result = -digit;
			long limit = Long.MIN_VALUE / 10;

			while (index < to)
			{
				digit = CharUtil.digit(array[index++]);

				if (digit == -1 || result < limit) return null;

				result *= 10;

				if (result < Long.MIN_VALUE + digit) return null;

				result -= digit;
			}

			if (negative) return result;
			else return result != Long.MIN_VALUE ? -result : null;
		}
		else
		{
			return null;
		}
	}


	/*
	 * long -> hashcode
	 */
	public static int hashcode(long value)
	{
		return Long.hashCode(value);
	}


	public static int hashcode(@NullAllow Long value)
	{
		return value != null ? Long.hashCode(value) : 0;
	}


	/*
	 * long -> murmur3hash
	 */
	public static Murmur3Hash murmur3hash(long value)
	{
		Murmur3Hash result = new Murmur3Hash();
		murmur3hash(value, result);

		return result;
	}


	public static void murmur3hash(long value, Murmur3Hash destination)
	{
		destination.update
		(
			(byte) (value >> 56),
			(byte) (value >> 48),
			(byte) (value >> 40),
			(byte) (value >> 32),
			(byte) (value >> 24),
			(byte) (value >> 16),
			(byte) (value >>  8),
			(byte) (value)
		);
	}


	/*
	 * equal
	 */
	@SuppressWarnings("NumberEquality")
	public static boolean equal(@NullAllow Long value0, @NullAllow Long value1)
	{
		return
			value0 == value1
			||
			value0 != null
			&&
			value1 != null
			&&
			value0.longValue() == value1.longValue();
	}


	/*
	 * convert
	 */
	public static @NullAllow Short toShort(@NullAllow Long value)
	{
		return value != null ? value.shortValue() : null;
	}


	public static @NullAllow Integer toInteger(@NullAllow Long value)
	{
		return value != null ? value.intValue() : null;
	}


	/*
	 * range
	 */
	public static boolean rangeShort(long value)
	{
		return value >= VALUE_SHORT_MIN && value <= VALUE_SHORT_MAX;
	}


	public static boolean rangeInteger(long value)
	{
		return value >= VALUE_INTEGER_MIN && value <= VALUE_INTEGER_MAX;
	}


	/*
	 *
	 */
	public static boolean even(long value)
	{
		return (value & 1) == 0;
	}


	public static boolean odd(long value)
	{
		return (value & 1) != 0;
	}


	public static long round(float value)
	{
		int value0 = (int) value;

		return value - value0 < 0.5F ? value0 : value0 + 1;
	}


	public static long round(double value)
	{
		long value0 = (long) value;

		return value - value0 < 0.5 ? value0 : value0 + 1;
	}


	public static long min(long value0, long value1, long value2)
	{
		return Math.min(value0, Math.min(value1, value2));
	}


	public static long max(long value0, long value1, long value2)
	{
		return Math.max(value0, Math.max(value1, value2));
	}

}
