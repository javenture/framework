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
import net.javenture.framework.utf8.IUtf8StreamableEntry;

import java.io.InputStream;
import java.io.OutputStream;


/*
	2021/05/28
 */
final public class BooleanUtil
{
	//
	final public static IInstanceFactory<Boolean> FACTORY_INSTANCE = object -> object instanceof Boolean;
	final public static ICopyFactory<Boolean> FACTORY_COPY = object -> object;

	final public static IByteFactory<Boolean> FACTORY_BYTE = new IByteFactory<>()
	{
		@Override
		public void toStream(@NullAllow Boolean object, OutputStream destination)
			throws Exception
		{
			if (IByteFactory.setNullHeader(object, destination)) bytes(object, destination);
		}

		@Override
		public @NullAllow Boolean fromStream(InputStream source)
			throws Exception
		{
			if (IByteFactory.getNullHeader(source)) return parse(source) ? Boolean.TRUE : Boolean.FALSE;
			else return null;
		}
	};

	final public static IStringFactory<Boolean> FACTORY_STRING = new IStringFactory<>()
	{
		@Override
		public @NullDisallow String toString(@NullAllow Boolean object)
		{
			if (object != null) return object ? True.STRING : False.STRING;
			else return NullUtil.STRING;
		}

		@Override
		public @NullDisallow BooleanParser parser(@NullAllow Boolean init, boolean nullable, boolean trim)
		{
			return new BooleanParser(init, nullable, trim);
		}
	};

	final public static IMurmur3HashFactory<Boolean> FACTORY_MURMUR3HASH = (object, destination) ->
	{
		if (IMurmur3HashFactory.updateNullHeader(object, destination)) murmur3hash((boolean) object, destination);
	};

	final public static Factories<Boolean> FACTORIES = new Factories<>(FACTORY_INSTANCE, FACTORY_COPY, FACTORY_BYTE, FACTORY_STRING, FACTORY_MURMUR3HASH);


	//
	private BooleanUtil()
	{
	}


	/*
	 * boolean -> byte[]
	 */
	public static byte[] bytes(boolean value)
	{
		return new byte[] { value ? True.BYTE : False.BYTE };
	}


	public static void bytes(boolean value, byte[] destination, int index)
	{
		destination[index] = value ? True.BYTE : False.BYTE;
	}


	public static void bytes(boolean value, OutputStream destination)
		throws Exception
	{
		destination.write(value ? True.INT : False.INT); // !
	}


	/*
	 * byte[] -> boolean
	 */
	public static boolean parse(byte b)
		throws Exception
	{
		if (b == True.BYTE) return true;
		else if (b == False.BYTE) return false;

		throw new Exception("parse failed: " + b);
	}


	public static boolean parse(byte b, boolean init)
	{
		if (b == True.BYTE) return true;
		else if (b == False.BYTE) return false;
		else return init;
	}


	public static @NullAllow Boolean parse(byte b, @NullAllow Boolean init)
	{
		if (b == True.BYTE) return true;
		else if (b == False.BYTE) return false;
		else return init;
	}


	public static boolean parse(byte[] source, int index)
		throws Exception
	{
		Validator.condition(index >= 0 && index < source.length, "[index] (", index, ") is illegal");
		byte b = source[index];

		return parse(b);
	}


	public static boolean parse(byte[] source, int index, boolean init)
	{
		if (index >= 0 && index < source.length)
		{
			byte b = source[index];

			if (b == True.BYTE) return true;
			else if (b == False.BYTE) return false;
		}

		return init;
	}


	public static @NullAllow Boolean parse(byte[] source, int index, @NullAllow Boolean init)
	{
		if (index >= 0 && index < source.length)
		{
			byte b = source[index];

			if (b == True.BYTE) return Boolean.TRUE;
			else if (b == False.BYTE) return Boolean.FALSE;
		}

		return init;
	}


	public static boolean parse(InputStream source)
		throws Exception
	{
		int i = source.read(); // !

		if (i != -1) return parse((byte) i);
		else throw new Exception("insufficient data");
	}


	public static boolean parse(InputStream source, boolean init)
	{
		try
		{
			int i = source.read(); // !

			if (i != -1) return parse((byte) i, init);
		}
		catch (Exception ignore)
		{
		}

		return init;
	}


	public static @NullAllow Boolean parse(InputStream source, @NullAllow Boolean init)
	{
		try
		{
			int i = source.read(); // !

			if (i != -1) return parse((byte) i, init);
		}
		catch (Exception ignore)
		{
		}

		return init;
	}


	/*
	 * CharSequence -> boolean
	 */
	public static @NullAllow Boolean parse(@NullDisallow CharSequence sequence)
		throws Exception
	{
		if (sequence != null)
		{
			if (NullUtil.notNull(sequence))
			{
				Boolean result = primitive(sequence);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		throw new Exception("parse failed: " + StringUtil.toString(sequence, "\""));
	}


	public static boolean parse(@NullAllow CharSequence sequence, boolean init)
	{
		return parse(sequence, init, false);
	}


	public static boolean parse(@NullAllow CharSequence sequence, boolean init, boolean trim)
	{
		if (sequence != null)
		{
			Boolean result = primitive(sequence, trim);

			if (result != null) return result;
		}

		return init;
	}


	public static @NullAllow Boolean parse(@NullAllow CharSequence sequence, @NullAllow Boolean init)
	{
		return parse(sequence, init, false);
	}


	public static @NullAllow Boolean parse(@NullAllow CharSequence sequence, @NullAllow Boolean init, boolean trim)
	{
		if (sequence != null)
		{
			if (NullUtil.notNull(sequence, trim))
			{
				Boolean result = primitive(sequence, trim);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		return init;
	}


	public static @NullAllow Boolean parse(@NullDisallow CharSequence sequence, int from, int to)
		throws Exception
	{
		if (sequence != null)
		{
			if (NullUtil.notNull(sequence, from, to))
			{
				Boolean result = primitive(sequence, from, to);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		throw new Exception("parse failed: " + StringUtil.toString(sequence, "\""));
	}


	public static boolean parse(@NullAllow CharSequence sequence, int from, int to, boolean init)
	{
		return parse(sequence, from, to, init, false);
	}


	public static boolean parse(@NullAllow CharSequence sequence, int from, int to, boolean init, boolean trim)
	{
		if (sequence != null)
		{
			Boolean result = primitive(sequence, from, to, trim);

			if (result != null) return result;
		}

		return init;
	}


	public static @NullAllow Boolean parse(@NullAllow CharSequence sequence, int from, int to, @NullAllow Boolean init)
	{
		return parse(sequence, from, to, init, false);
	}


	public static @NullAllow Boolean parse(@NullAllow CharSequence sequence, int from, int to, @NullAllow Boolean init, boolean trim)
	{
		if (sequence != null)
		{
			if (NullUtil.notNull(sequence, from, to, trim))
			{
				Boolean result = primitive(sequence, from, to, trim);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		return init;
	}


	public static @NullAllow Boolean primitive(@NullDisallow CharSequence sequence)
	{
		return primitive(sequence, 0, sequence.length(), false);
	}


	public static @NullAllow Boolean primitive(@NullDisallow CharSequence sequence, boolean trim)
	{
		return primitive(sequence, 0, sequence.length(), trim);
	}


	public static @NullAllow Boolean primitive(@NullDisallow CharSequence sequence, int from, int to)
	{
		return primitive(sequence, from, to, false);
	}


	public static @NullAllow Boolean primitive(@NullDisallow CharSequence sequence, int from, int to, boolean trim)
	{
		if (trim)
		{
			while (from < to && sequence.charAt(from) <= ' ') from++;

			while (from < to && sequence.charAt(to - 1) <= ' ') to--;
		}

		int length = to - from;

		if (length == 1)
		{
			char c = sequence.charAt(from);

			if (c == '1') return Boolean.TRUE;
			else if (c == '0') return Boolean.FALSE;
		}
		else if (length == 4)
		{
			char c0 = sequence.charAt(from);
			char c1 = sequence.charAt(from + 1);
			char c2 = sequence.charAt(from + 2);
			char c3 = sequence.charAt(from + 3);

			if
			(
				(c0 == 't' || c0 == 'T')
				&&
				(c1 == 'r' || c1 == 'R')
				&&
				(c2 == 'u' || c2 == 'U')
				&&
				(c3 == 'e' || c3 == 'E')
			)
			{
				return Boolean.TRUE;
			}
		}
		else if (length == 5)
		{
			char c0 = sequence.charAt(from);
			char c1 = sequence.charAt(from + 1);
			char c2 = sequence.charAt(from + 2);
			char c3 = sequence.charAt(from + 3);
			char c4 = sequence.charAt(from + 4);

			if
			(
				(c0 == 'f' || c0 == 'F')
				&&
				(c1 == 'a' || c1 == 'A')
				&&
				(c2 == 'l' || c2 == 'L')
				&&
				(c3 == 's' || c3 == 'S')
				&&
				(c4 == 'e' || c4 == 'E')
			)
			{
				return Boolean.FALSE;
			}
		}

		return null;
	}


	/*
	 * char[] -> boolean
	 */
	public static @NullAllow Boolean parse(@NullDisallow char[] array)
		throws Exception
	{
		if (array != null)
		{
			if (NullUtil.notNull(array))
			{
				Boolean result = primitive(array);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		throw new Exception("parse failed: " + CharArrayUtil.toString(array, "\""));
	}


	public static boolean parse(@NullAllow char[] array, boolean init)
	{
		return parse(array, init, false);
	}


	public static boolean parse(@NullAllow char[] array, boolean init, boolean trim)
	{
		if (array != null)
		{
			Boolean result = primitive(array, trim);

			if (result != null) return result;
		}

		return init;
	}


	public static @NullAllow Boolean parse(@NullAllow char[] array, @NullAllow Boolean init)
	{
		return parse(array, init, false);
	}


	public static @NullAllow Boolean parse(@NullAllow char[] array, @NullAllow Boolean init, boolean trim)
	{
		if (array != null)
		{
			if (NullUtil.notNull(array, trim))
			{
				Boolean result = primitive(array, trim);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		return init;
	}


	public static @NullAllow Boolean parse(@NullDisallow char[] array, int from, int to)
		throws Exception
	{
		if (array != null)
		{
			if (NullUtil.notNull(array, from, to))
			{
				Boolean result = primitive(array, from, to);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		throw new Exception("parse failed: " + CharArrayUtil.toString(array, "\""));
	}


	public static boolean parse(@NullAllow char[] array, int from, int to, boolean init)
	{
		return parse(array, from, to, init, false);
	}


	public static boolean parse(@NullAllow char[] array, int from, int to, boolean init, boolean trim)
	{
		if (array != null)
		{
			Boolean result = primitive(array, from, to, trim);

			if (result != null) return result;
		}

		return init;
	}


	public static @NullAllow Boolean parse(@NullAllow char[] array, int from, int to, @NullAllow Boolean init)
	{
		return parse(array, from, to, init, false);
	}


	public static @NullAllow Boolean parse(@NullAllow char[] array, int from, int to, @NullAllow Boolean init, boolean trim)
	{
		if (array != null)
		{
			if (NullUtil.notNull(array, from, to, trim))
			{
				Boolean result = primitive(array, from, to, trim);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		return init;
	}


	public static @NullAllow Boolean primitive(@NullDisallow char[] array)
	{
		return primitive(array, 0, array.length, false);
	}


	public static @NullAllow Boolean primitive(@NullDisallow char[] array, boolean trim)
	{
		return primitive(array, 0, array.length, trim);
	}


	public static @NullAllow Boolean primitive(@NullDisallow char[] array, int from, int to)
	{
		return primitive(array, from, to, false);
	}


	public static @NullAllow Boolean primitive(@NullDisallow char[] array, int from, int to, boolean trim)
	{
		if (trim)
		{
			while (from < to && array[from] <= ' ') from++;

			while (from < to && array[to - 1] <= ' ') to--;
		}

		int length = to - from;

		if (length == 1)
		{
			char c = array[from];

			if (c == '1') return Boolean.TRUE;
			else if (c == '0') return Boolean.FALSE;
		}
		else if (length == 4)
		{
			char c0 = array[from];
			char c1 = array[from + 1];
			char c2 = array[from + 2];
			char c3 = array[from + 3];

			if
			(
				(c0 == 't' || c0 == 'T')
				&&
				(c1 == 'r' || c1 == 'R')
				&&
				(c2 == 'u' || c2 == 'U')
				&&
				(c3 == 'e' || c3 == 'E')
			)
			{
				return Boolean.TRUE;
			}
		}
		else if (length == 5)
		{
			char c0 = array[from];
			char c1 = array[from + 1];
			char c2 = array[from + 2];
			char c3 = array[from + 3];
			char c4 = array[from + 4];

			if
			(
				(c0 == 'f' || c0 == 'F')
				&&
				(c1 == 'a' || c1 == 'A')
				&&
				(c2 == 'l' || c2 == 'L')
				&&
				(c3 == 's' || c3 == 'S')
				&&
				(c4 == 'e' || c4 == 'E')
			)
			{
				return Boolean.FALSE;
			}
		}

		return null;
	}


	/*
	 * boolean -> hashcode
	 */
	public static int hashcode(boolean value)
	{
		return Boolean.hashCode(value);
	}


	public static int hashcode(@NullAllow Boolean value)
	{
		return value != null ? Boolean.hashCode(value) : 0;
	}


	/*
	 * boolean -> murmur3hash
	 */
	public static Murmur3Hash murmur3hash(boolean value)
	{
		Murmur3Hash result = new Murmur3Hash();
		murmur3hash(value, result);

		return result;
	}


	public static void murmur3hash(boolean value, Murmur3Hash destination)
	{
		destination.update(value ? True.BYTE : False.BYTE);
	}


	public static Murmur3Hash murmur3hash(@NullAllow Boolean value)
	{
		return FACTORY_MURMUR3HASH.hash(value);
	}


	public static void murmur3hash(@NullAllow Boolean value, Murmur3Hash destination)
	{
		FACTORY_MURMUR3HASH.update(value, destination);
	}


	/*
	 * equal
	 */
	public static boolean equal(@NullAllow Boolean value0, @NullAllow Boolean value1)
	{
		return
			value0 == value1
			||
			value0 != null
			&&
			value1 != null
			&&
			value0.booleanValue() == value1.booleanValue();
	}


	//
	final public static class True
	{
		final public static byte BYTE = 1;
		final public static int INT = 1;
		final public static String STRING = "true";
		final public static IUtf8StreamableEntry ENTRY = IUtf8StreamableEntry.entry(STRING, true);
	}


	final public static class False
	{
		final public static byte BYTE = 0;
		final public static int INT = 0;
		final public static String STRING = "false";
		final public static IUtf8StreamableEntry ENTRY = IUtf8StreamableEntry.entry(STRING, true);
	}

}
