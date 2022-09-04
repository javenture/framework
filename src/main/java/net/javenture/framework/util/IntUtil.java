package net.javenture.framework.util;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.factory.IByteFactory;
import net.javenture.framework.factory.ICopyFactory;
import net.javenture.framework.factory.IMurmur3HashFactory;
import net.javenture.framework.factory.IStringFactory;
import net.javenture.framework.hash.Murmur3Hash;

import java.io.InputStream;
import java.io.OutputStream;


/*
	2021/05/28
 */
final public class IntUtil
{
	//
	final public static ICopyFactory<Integer> FACTORY_COPY = object -> object;

	final public static IByteFactory<Integer> FACTORY_BYTE = new IByteFactory<>()
	{
		@Override
		public void toStream(@NullAllow Integer object, OutputStream destination)
			throws Exception
		{
			if (IByteFactory.setNullHeader(object, destination)) bytes(object, destination);
		}

		@Override
		public @NullAllow Integer fromStream(InputStream source)
			throws Exception
		{
			return IByteFactory.getNullHeader(source) ? parse(source) : null;
		}
	};

	final public static IStringFactory<Integer> FACTORY_STRING = new IStringFactory<>()
	{
		@Override
		public @NullDisallow String toString(@NullAllow Integer object)
		{
			return object != null ? "" + object : NullUtil.STRING;
		}

		@Override
		public @NullDisallow IntParser parser(@NullAllow Integer init, boolean nullable, boolean trim)
		{
			return new IntParser(init, nullable, trim);
		}
	};

	final public static IMurmur3HashFactory<Integer> FACTORY_MURMUR3HASH = (object, destination) ->
	{
		if (IMurmur3HashFactory.updateNullHeader(object, destination)) murmur3hash(object, destination);
	};

	final public static Factories<Integer> FACTORIES = new Factories<>(FACTORY_COPY, FACTORY_BYTE, FACTORY_STRING, FACTORY_MURMUR3HASH);


	//
	private IntUtil()
	{
	}


	/*
	 * int -> byte[]
	 */
	public static byte[] bytes(int value)
	{
		return new byte[]
		{
			(byte) (value >> 24),
			(byte) (value >> 16),
			(byte) (value >>  8),
			(byte) (value)
		};
	}


	public static void bytes(int value, byte[] destination, int index)
	{
		destination[index]     = (byte) (value >> 24);
		destination[index + 1] = (byte) (value >> 16);
		destination[index + 2] = (byte) (value >>  8);
		destination[index + 3] = (byte) (value);
	}


	public static void bytes(int value, OutputStream destination)
		throws Exception
	{
		destination.write(value >> 24);
		destination.write(value >> 16);
		destination.write(value >>  8);
		destination.write(value);
	}


	/*
	 * byte[] -> int
	 */
	public static int parse(byte b0, byte b1, byte b2, byte b3)
	{
		return
			(b0 & 0xff) << 24
			|
			(b1 & 0xff) << 16
			|
			(b2 & 0xff) <<  8
			|
			(b3 & 0xff);
	}


	public static int parse(byte[] source, int index)
		throws Exception
	{
		Validator.condition(index >= 0 && index + 3 < source.length, "[index] (", index, ") is illegal");

		byte b0 = source[index];
		byte b1 = source[index + 1];
		byte b2 = source[index + 2];
		byte b3 = source[index + 3];

		return parse(b0, b1, b2, b3);
	}


	public static int parse(byte[] source, int index, int init)
	{
		if (index >= 0 && index + 3 < source.length)
		{
			byte b0 = source[index];
			byte b1 = source[index + 1];
			byte b2 = source[index + 2];
			byte b3 = source[index + 3];

			return parse(b0, b1, b2, b3);
		}

		return init;
	}


	public static @NullAllow Integer parse(byte[] source, int index, @NullAllow Integer init)
	{
		if (index >= 0 && index + 3 < source.length)
		{
			byte b0 = source[index];
			byte b1 = source[index + 1];
			byte b2 = source[index + 2];
			byte b3 = source[index + 3];

			return parse(b0, b1, b2, b3);
		}

		return init;
	}


	public static int parse(InputStream source)
		throws Exception
	{
		int i0 = source.read();
		int i1 = source.read();
		int i2 = source.read();
		int i3 = source.read();

		if (i0 != -1 && i1 != -1 && i2 != -1 && i3 != -1) return parse((byte) i0, (byte) i1, (byte) i2, (byte) i3);
		else throw new Exception("insufficient data");
	}


	public static int parse(InputStream source, int init)
	{
		try
		{
			int i0 = source.read();
			int i1 = source.read();
			int i2 = source.read();
			int i3 = source.read();

			if (i0 != -1 && i1 != -1 && i2 != -1 && i3 != -1) return parse((byte) i0, (byte) i1, (byte) i2, (byte) i3);
		}
		catch (Exception ignore)
		{
		}

		return init;
	}


	public static @NullAllow Integer parse(InputStream source, @NullAllow Integer init)
	{
		try
		{
			int i0 = source.read();
			int i1 = source.read();
			int i2 = source.read();
			int i3 = source.read();

			if (i0 != -1 && i1 != -1 && i2 != -1 && i3 != -1) return parse((byte) i0, (byte) i1, (byte) i2, (byte) i3);
		}
		catch (Exception ignore)
		{
		}

		return init;
	}


	/*
	 * CharSequence -> int
	 */
	public static @NullAllow Integer parse(@NullDisallow CharSequence sequence)
		throws Exception
	{
		return parse(sequence, false);
	}


	public static @NullAllow Integer parse(@NullDisallow CharSequence sequence, boolean trim)
		throws Exception
	{
		if (sequence != null)
		{
			if (NullUtil.notNull(sequence, trim))
			{
				Integer result = primitive(sequence, trim);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		throw new Exception("parse failed: " + StringUtil.toString(sequence, "\""));
	}


	public static int parse(@NullAllow CharSequence sequence, int init)
	{
		return parse(sequence, init, false);
	}


	public static int parse(@NullAllow CharSequence sequence, int init, boolean trim)
	{
		if (sequence != null)
		{
			Integer result = primitive(sequence, trim);

			if (result != null) return result;
		}

		return init;
	}


	public static @NullAllow Integer parse(@NullAllow CharSequence sequence, @NullAllow Integer init)
	{
		return parse(sequence, init, false);
	}


	public static @NullAllow Integer parse(@NullAllow CharSequence sequence, @NullAllow Integer init, boolean trim)
	{
		if (sequence != null)
		{
			if (NullUtil.notNull(sequence, trim))
			{
				Integer result = primitive(sequence, trim);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		return init;
	}


	public static @NullAllow Integer parse(@NullDisallow CharSequence sequence, int from, int to)
		throws Exception
	{
		return parse(sequence, from, to, false);
	}


	public static @NullAllow Integer parse(@NullDisallow CharSequence sequence, int from, int to, boolean trim)
		throws Exception
	{
		if (sequence != null)
		{
			if (NullUtil.notNull(sequence, from, to, trim))
			{
				Integer result = primitive(sequence, from, to, trim);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		throw new Exception("parse failed: " + StringUtil.toString(sequence, "\""));
	}


	public static int parse(@NullAllow CharSequence sequence, int from, int to, int init)
	{
		return parse(sequence, from, to, init, false);
	}


	public static int parse(@NullAllow CharSequence sequence, int from, int to, int init, boolean trim)
	{
		if (sequence != null)
		{
			Integer result = primitive(sequence, from, to, trim);

			if (result != null) return result;
		}

		return init;
	}


	public static @NullAllow Integer parse(@NullAllow CharSequence sequence, int from, int to, @NullAllow Integer init)
	{
		return parse(sequence, from, to, init, false);
	}


	public static @NullAllow Integer parse(@NullAllow CharSequence sequence, int from, int to, @NullAllow Integer init, boolean trim)
	{
		if (sequence != null)
		{
			if (NullUtil.notNull(sequence, from, to, trim))
			{
				Integer result = primitive(sequence, from, to, trim);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		return init;
	}


	public static @NullAllow Integer primitive(@NullDisallow CharSequence sequence)
	{
		return primitive(sequence, 0, sequence.length(), false);
	}


	public static @NullAllow Integer primitive(@NullDisallow CharSequence sequence, boolean trim)
	{
		return primitive(sequence, 0, sequence.length(), trim);
	}


	public static @NullAllow Integer primitive(@NullDisallow CharSequence sequence, int from, int to)
	{
		return primitive(sequence, from, to, false);
	}


	public static @NullAllow Integer primitive(@NullDisallow CharSequence sequence, int from, int to, boolean trim)
	{
		Long l = LongUtil.primitive(sequence, from, to, trim);

		return l != null && LongUtil.rangeInteger(l)
			? l.intValue()
			: null;
	}


	/*
	 * char[] -> int
	 */
	public static @NullAllow Integer parse(@NullDisallow char[] array)
		throws Exception
	{
		return parse(array, false);
	}


	public static @NullAllow Integer parse(@NullDisallow char[] array, boolean trim)
		throws Exception
	{
		if (array != null)
		{
			if (NullUtil.notNull(array, trim))
			{
				Integer result = primitive(array, trim);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		throw new Exception("parse failed: " + CharArrayUtil.toString(array, "\""));
	}


	public static int parse(@NullAllow char[] array, int init)
	{
		return parse(array, init, false);
	}


	public static int parse(@NullAllow char[] array, int init, boolean trim)
	{
		if (array != null)
		{
			Integer result = primitive(array, trim);

			if (result != null) return result;
		}

		return init;
	}


	public static @NullAllow Integer parse(@NullAllow char[] array, @NullAllow Integer init)
	{
		return parse(array, init, false);
	}


	public static @NullAllow Integer parse(@NullAllow char[] array, @NullAllow Integer init, boolean trim)
	{
		if (array != null)
		{
			if (NullUtil.notNull(array, trim))
			{
				Integer result = primitive(array, trim);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		return init;
	}


	public static @NullAllow Integer parse(@NullDisallow char[] array, int from, int to)
		throws Exception
	{
		return parse(array, from, to, false);
	}


	public static @NullAllow Integer parse(@NullDisallow char[] array, int from, int to, boolean trim)
		throws Exception
	{
		if (array != null)
		{
			if (NullUtil.notNull(array, from, to, trim))
			{
				Integer result = primitive(array, from, to, trim);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		throw new Exception("parse failed: " + CharArrayUtil.toString(array, "\""));
	}


	public static int parse(@NullAllow char[] array, int from, int to, int init)
	{
		return parse(array, from, to, init, false);
	}


	public static int parse(@NullAllow char[] array, int from, int to, int init, boolean trim)
	{
		if (array != null)
		{
			Integer result = primitive(array, from, to, trim);

			if (result != null) return result;
		}

		return init;
	}


	public static @NullAllow Integer parse(@NullAllow char[] array, int from, int to, @NullAllow Integer init)
	{
		return parse(array, from, to, init, false);
	}


	public static @NullAllow Integer parse(@NullAllow char[] array, int from, int to, @NullAllow Integer init, boolean trim)
	{
		if (array != null)
		{
			if (NullUtil.notNull(array, from, to, trim))
			{
				Integer result = primitive(array, from, to, trim);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		return init;
	}


	public static @NullAllow Integer primitive(@NullDisallow char[] array)
	{
		return primitive(array, 0, array.length, false);
	}


	public static @NullAllow Integer primitive(@NullDisallow char[] array, boolean trim)
	{
		return primitive(array, 0, array.length, trim);
	}


	public static @NullAllow Integer primitive(@NullDisallow char[] array, int from, int to)
	{
		return primitive(array, from, to, false);
	}


	public static @NullAllow Integer primitive(@NullDisallow char[] array, int from, int to, boolean trim)
	{
		Long l = LongUtil.primitive(array, from, to, trim);

		return l != null && LongUtil.rangeInteger(l)
			? l.intValue()
			: null;
	}


	/*
	 * int -> hashcode
	 */
	public static int hashcode(int value)
	{
		return Integer.hashCode(value);
	}


	public static int hashcode(@NullAllow Integer value)
	{
		return value != null ? Integer.hashCode(value) : 0;
	}


	/*
	 * int -> murmur3hash
	 */
	public static Murmur3Hash murmur3hash(int value)
	{
		Murmur3Hash result = new Murmur3Hash();
		murmur3hash(value, result);

		return result;
	}


	public static void murmur3hash(int value, Murmur3Hash destination)
	{
		destination.update
		(
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
	public static boolean equal(@NullAllow Integer value0, @NullAllow Integer value1)
	{
		return
			value0 == value1
			||
			value0 != null
			&&
			value1 != null
			&&
			value0.intValue() == value1.intValue();
	}


	/*
	 *
	 */
	public static boolean even(int value)
	{
		return (value & 1) == 0;
	}


	public static boolean odd(int value)
	{
		return (value & 1) != 0;
	}


	public static String prefixZero1(int value)
	{
		return StringUtil.prefix(value, PREFIX_ZERO1);
	}


	public static int round(float value)
	{
		int value0 = (int) value;

		return value - value0 < 0.5F ? value0 : value0 + 1;
	}


	public static int round(double value)
	{
		int value0 = (int) value;

		return value - value0 < 0.5 ? value0 : value0 + 1;
	}


	public static int min(int value0, int value1, int value2)
	{
		return Math.min(value0, Math.min(value1, value2));
	}


	public static int max(int value0, int value1, int value2)
	{
		return Math.max(value0, Math.max(value1, value2));
	}


	//
	final private static StringUtil.IPrefix<Integer> PREFIX_ZERO1 = value -> value >= 0 && value < 10 ? "0" + value : "" + value;

}
