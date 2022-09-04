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
final public class ShortUtil
{
	//
	final public static ICopyFactory<Short> FACTORY_COPY = object -> object;

	final public static IByteFactory<Short> FACTORY_BYTE = new IByteFactory<>()
	{
		@Override
		public void toStream(@NullAllow Short object, OutputStream destination)
			throws Exception
		{
			if (IByteFactory.setNullHeader(object, destination)) bytes(object, destination);
		}

		@Override
		public @NullAllow Short fromStream(InputStream source)
			throws Exception
		{
			return IByteFactory.getNullHeader(source) ? parse(source) : null;
		}
	};

	final public static IStringFactory<Short> FACTORY_STRING = new IStringFactory<>()
	{
		@Override
		public @NullDisallow String toString(@NullAllow Short object)
		{
			return object != null ? "" + object : NullUtil.STRING;
		}

		@Override
		public @NullDisallow ShortParser parser(@NullAllow Short init, boolean nullable, boolean trim)
		{
			return new ShortParser(init, nullable, trim);
		}
	};

	final public static IMurmur3HashFactory<Short> FACTORY_MURMUR3HASH = (object, destination) ->
	{
		if (IMurmur3HashFactory.updateNullHeader(object, destination)) murmur3hash(object, destination);
	};

	final public static Factories<Short> FACTORIES = new Factories<>(FACTORY_COPY, FACTORY_BYTE, FACTORY_STRING, FACTORY_MURMUR3HASH);


	//
	private ShortUtil()
	{
	}


	/*
	 * short -> byte[]
	 */
	public static byte[] bytes(short value)
	{
		return new byte[]
		{
			(byte) (value >> 8),
			(byte) (value)
		};
	}


	public static void bytes(short value, byte[] destination, int index)
	{
		destination[index]     = (byte) (value >>  8);
		destination[index + 1] = (byte) (value);
	}


	public static void bytes(short value, OutputStream destination)
		throws Exception
	{
		destination.write(value >> 8);
		destination.write(value);
	}


	/*
	 * byte[] -> short
	 */
	public static short parse(byte b0, byte b1)
	{
		return (short)
		(
			(b0 & 0xff) << 8
			|
			(b1 & 0xff)
		);
	}


	public static short parse(byte[] source, int index)
		throws Exception
	{
		Validator.condition(index >= 0 && index + 1 < source.length, "[index] (", index, ") is illegal");

		byte b0 = source[index];
		byte b1 = source[index + 1];

		return parse(b0, b1);
	}


	public static short parse(InputStream source)
		throws Exception
	{
		int i0 = source.read();
		int i1 = source.read();

		if (i0 != -1 && i1 != -1) return parse((byte) i0, (byte) i1);
		else throw new Exception("insufficient data");
	}


	public static short parse(InputStream source, short init)
	{
		try
		{
			int i0 = source.read();
			int i1 = source.read();

			if (i0 != -1 && i1 != -1) return parse((byte) i0, (byte) i1);
		}
		catch (Exception ignore)
		{
		}

		return init;
	}


	public static @NullAllow Short parse(InputStream source, @NullAllow Short init)
	{
		try
		{
			int i0 = source.read();
			int i1 = source.read();

			if (i0 != -1 && i1 != -1) return parse((byte) i0, (byte) i1);
		}
		catch (Exception ignore)
		{
		}

		return init;
	}


	/*
	 * CharSequence -> short
	 */
	public static @NullAllow Short parse(@NullDisallow CharSequence sequence)
		throws Exception
	{
		return parse(sequence, false);
	}


	public static @NullAllow Short parse(@NullDisallow CharSequence sequence, boolean trim)
		throws Exception
	{
		if (sequence != null)
		{
			if (NullUtil.notNull(sequence, trim))
			{
				Short result = primitive(sequence, trim);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		throw new Exception("parse failed: " + StringUtil.toString(sequence, "\""));
	}


	public static short parse(@NullAllow CharSequence sequence, short init)
	{
		return parse(sequence, init, false);
	}


	public static short parse(@NullAllow CharSequence sequence, short init, boolean trim)
	{
		if (sequence != null)
		{
			Short result = primitive(sequence, trim);

			if (result != null) return result;
		}

		return init;
	}


	public static @NullAllow Short parse(@NullAllow CharSequence sequence, @NullAllow Short init)
	{
		return parse(sequence, init, false);
	}


	public static @NullAllow Short parse(@NullAllow CharSequence sequence, @NullAllow Short init, boolean trim)
	{
		if (sequence != null)
		{
			if (NullUtil.notNull(sequence, trim))
			{
				Short result = primitive(sequence, trim);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		return init;
	}


	public static @NullAllow Short parse(@NullDisallow CharSequence sequence, int from, int to)
		throws Exception
	{
		return parse(sequence, from, to, false);
	}


	public static @NullAllow Short parse(@NullDisallow CharSequence sequence, int from, int to, boolean trim)
		throws Exception
	{
		if (sequence != null)
		{
			if (NullUtil.notNull(sequence, from, to, trim))
			{
				Short result = primitive(sequence, from, to, trim);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		throw new Exception("parse failed: " + StringUtil.toString(sequence, "\""));
	}


	public static short parse(@NullAllow CharSequence sequence, int from, int to, short init)
	{
		return parse(sequence, from, to, init, false);
	}


	public static short parse(@NullAllow CharSequence sequence, int from, int to, short init, boolean trim)
	{
		if (sequence != null)
		{
			Short result = primitive(sequence, from, to, trim);

			if (result != null) return result;
		}

		return init;
	}


	public static @NullAllow Short parse(@NullAllow CharSequence sequence, int from, int to, @NullAllow Short init)
	{
		return parse(sequence, from, to, init, false);
	}


	public static @NullAllow Short parse(@NullAllow CharSequence sequence, int from, int to, @NullAllow Short init, boolean trim)
	{
		if (sequence != null)
		{
			if (NullUtil.notNull(sequence, from, to, trim))
			{
				Short result = primitive(sequence, from, to, trim);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		return init;
	}


	public static @NullAllow Short primitive(@NullDisallow CharSequence sequence)
	{
		return primitive(sequence, 0, sequence.length(), false);
	}


	public static @NullAllow Short primitive(@NullDisallow CharSequence sequence, boolean trim)
	{
		return primitive(sequence, 0, sequence.length(), trim);
	}


	public static @NullAllow Short primitive(@NullDisallow CharSequence sequence, int from, int to)
	{
		return primitive(sequence, from, to, false);
	}


	public static @NullAllow Short primitive(@NullDisallow CharSequence sequence, int from, int to, boolean trim)
	{
		Long l = LongUtil.primitive(sequence, from, to, trim);

		return l != null && LongUtil.rangeShort(l)
			? l.shortValue()
			: null;
	}


	/*
	 * char[] -> short
	 */
	public static @NullAllow Short parse(@NullDisallow char[] array)
		throws Exception
	{
		return parse(array, false);
	}


	public static @NullAllow Short parse(@NullDisallow char[] array, boolean trim)
		throws Exception
	{
		if (array != null)
		{
			if (NullUtil.notNull(array, trim))
			{
				Short result = primitive(array, trim);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		throw new Exception("parse failed: " + CharArrayUtil.toString(array, "\""));
	}


	public static short parse(@NullAllow char[] array, short init)
	{
		return parse(array, init, false);
	}


	public static short parse(@NullAllow char[] array, short init, boolean trim)
	{
		if (array != null)
		{
			Short result = primitive(array, trim);

			if (result != null) return result;
		}

		return init;
	}


	public static @NullAllow Short parse(@NullAllow char[] array, @NullAllow Short init)
	{
		return parse(array, init, false);
	}


	public static @NullAllow Short parse(@NullAllow char[] array, @NullAllow Short init, boolean trim)
	{
		if (array != null)
		{
			if (NullUtil.notNull(array, trim))
			{
				Short result = primitive(array, trim);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		return init;
	}


	public static @NullAllow Short parse(@NullDisallow char[] array, int from, int to)
		throws Exception
	{
		return parse(array, from, to, false);
	}


	public static @NullAllow Short parse(@NullDisallow char[] array, int from, int to, boolean trim)
		throws Exception
	{
		if (array != null)
		{
			if (NullUtil.notNull(array, from, to, trim))
			{
				Short result = primitive(array, from, to, trim);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		throw new Exception("parse failed: " + CharArrayUtil.toString(array, "\""));
	}


	public static short parse(@NullAllow char[] array, int from, int to, short init)
	{
		return parse(array, from, to, init, false);
	}


	public static short parse(@NullAllow char[] array, int from, int to, short init, boolean trim)
	{
		if (array != null)
		{
			Short result = primitive(array, from, to, trim);

			if (result != null) return result;
		}

		return init;
	}


	public static @NullAllow Short parse(@NullAllow char[] array, int from, int to, @NullAllow Short init)
	{
		return parse(array, from, to, init, false);
	}


	public static @NullAllow Short parse(@NullAllow char[] array, int from, int to, @NullAllow Short init, boolean trim)
	{
		if (array != null)
		{
			if (NullUtil.notNull(array, from, to, trim))
			{
				Short result = primitive(array, from, to, trim);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		return init;
	}


	public static @NullAllow Short primitive(@NullDisallow char[] array)
	{
		return primitive(array, 0, array.length, false);
	}


	public static @NullAllow Short primitive(@NullDisallow char[] array, boolean trim)
	{
		return primitive(array, 0, array.length, trim);
	}


	public static @NullAllow Short primitive(@NullDisallow char[] array, int from, int to)
	{
		return primitive(array, from, to, false);
	}


	public static @NullAllow Short primitive(@NullDisallow char[] array, int from, int to, boolean trim)
	{
		Long l = LongUtil.primitive(array, from, to, trim);

		return l != null && LongUtil.rangeShort(l)
			? l.shortValue()
			: null;
	}


	/*
	 * short -> hashcode
	 */
	public static int hashcode(short value)
	{
		return Short.hashCode(value);
	}


	public static int hashcode(@NullAllow Short value)
	{
		return value != null ? Short.hashCode(value) : 0;
	}


	/*
	 * short -> murmur3hash
	 */
	public static Murmur3Hash murmur3hash(short value)
	{
		Murmur3Hash result = new Murmur3Hash();
		murmur3hash(value, result);

		return result;
	}


	public static void murmur3hash(short value, Murmur3Hash destination)
	{
		destination.update
		(
			(byte) (value >> 8),
			(byte) (value)
		);
	}


	/*
	 * equal
	 */
	@SuppressWarnings("NumberEquality")
	public static boolean equal(@NullAllow Short value0, @NullAllow Short value1)
	{
		return
			value0 == value1
			||
			value0 != null
			&&
			value1 != null
			&&
			value0.shortValue() == value1.shortValue();
	}


	public static short min(short value0, short value1, short value2)
	{
		return (short) Math.min(value0, Math.min(value1, value2));
	}


	public static short max(short value0, short value1, short value2)
	{
		return (short) Math.max(value0, Math.max(value1, value2));
	}

}
