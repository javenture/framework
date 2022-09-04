package net.javenture.framework.hex;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.FactoryObjectUtil;
import net.javenture.framework.util.ByteArrayUtil;
import net.javenture.framework.util.CharArrayUtil;
import net.javenture.framework.util.IntUtil;
import net.javenture.framework.util.NullUtil;
import net.javenture.framework.util.StringUtil;


/*
	2019/12/23
 */
final public class HexUtil
{
	//
	final private static int SHIFT = 4;


	//
	private HexUtil()
	{
	}


	/*
	 * CharSequence -> IHex
	 */
	public static @NullAllow <H extends IHex> H parse(@NullAllow CharSequence sequence, @NullDisallow IHex.IInstance<H> instance)
		throws Exception
	{
		return parse(sequence, false, instance);
	}


	public static @NullAllow <H extends IHex> H parse(@NullAllow CharSequence sequence, boolean trim, @NullDisallow IHex.IInstance<H> instance)
		throws Exception
	{
		if (sequence != null)
		{
			if (NullUtil.notNull(sequence, trim))
			{
				byte[] bytes = primitive(sequence, trim);

				if (bytes != null) return instance.get(bytes);
			}
			else
			{
				return null;
			}
		}

		throw new Exception("parse failed: " + StringUtil.toString(sequence, "\""));
	}


	public static @NullAllow <H extends IHex> H parse(@NullAllow CharSequence sequence, @NullAllow H init, @NullDisallow IHex.IInstance<H> instance)
	{
		return parse(sequence, init, false, instance);
	}


	public static @NullAllow <H extends IHex> H parse(@NullAllow CharSequence sequence, @NullAllow H init, boolean trim, @NullDisallow IHex.IInstance<H> instance)
	{
		if (sequence != null)
		{
			if (NullUtil.notNull(sequence, trim))
			{
				byte[] bytes = primitive(sequence, trim);

				if (bytes != null) return instance.get(bytes);
			}
			else
			{
				return null;
			}
		}

		return init;
	}


	/*
	 * CharSequence -> byte[]
	 */
	public static @NullAllow byte[] bytes(@NullDisallow CharSequence sequence)
		throws Exception
	{
		return bytes(sequence, false);
	}


	public static @NullAllow byte[] bytes(@NullDisallow CharSequence sequence, boolean trim)
		throws Exception
	{
		if (sequence != null)
		{
			if (NullUtil.notNull(sequence, trim))
			{
				byte[] bytes = primitive(sequence, trim);

				if (bytes != null) return bytes;
			}
			else
			{
				return null;
			}
		}

		throw new Exception("parse failed: " + StringUtil.toString(sequence, "\""));
	}


	public static @NullAllow byte[] bytes(@NullAllow CharSequence sequence, @NullAllow byte[] init)
	{
		return bytes(sequence, init, false);
	}


	public static @NullAllow byte[] bytes(@NullAllow CharSequence sequence, @NullAllow byte[] init, boolean trim)
	{
		if (sequence != null)
		{
			if (NullUtil.notNull(sequence, trim))
			{
				byte[] bytes = primitive(sequence, trim);

				if (bytes != null) return bytes;
			}
			else
			{
				return null;
			}
		}

		return init;
	}


	public static @NullAllow byte[] primitive(@NullDisallow CharSequence sequence)
	{
		return primitive(sequence, 0, sequence.length(), false);
	}


	public static @NullAllow byte[] primitive(@NullDisallow CharSequence sequence, boolean trim)
	{
		return primitive(sequence, 0, sequence.length(), trim);
	}


	public static @NullAllow byte[] primitive(@NullDisallow CharSequence sequence, int from, int to)
	{
		return primitive(sequence, from, to, false);
	}


	public static @NullAllow byte[] primitive(@NullDisallow CharSequence sequence, int from, int to, boolean trim)
	{
		if (trim)
		{
			while (from < to && sequence.charAt(from) <= ' ') from++;

			while (from < to && sequence.charAt(to - 1) <= ' ') to--;
		}

		int length = to - from;

		if (length == 0)
		{
			return ByteArrayUtil.BLANK;
		}
		else if (IntUtil.even(length))
		{
			int count = length / 2;
			byte[] result = new byte[count];

			for (int i = 0; i < count; i++)
			{
				int index = from + i * 2;
				Byte b = convert(sequence.charAt(index), sequence.charAt(index + 1));

				if (b != null) result[i] = b;
				else return null;
			}

			return result;
		}
		else
		{
			return null;
		}
	}


	/*
	 * char[] -> byte[]
	 */
	public static @NullAllow byte[] bytes(@NullDisallow char[] array)
		throws Exception
	{
		return bytes(array, false);
	}


	public static @NullAllow byte[] bytes(@NullDisallow char[] array, boolean trim)
		throws Exception
	{
		if (array != null)
		{
			if (NullUtil.notNull(array, trim))
			{
				byte[] bytes = primitive(array, trim);

				if (bytes != null) return bytes;
			}
			else
			{
				return null;
			}
		}

		throw new Exception("parse failed: " + CharArrayUtil.toString(array, "\""));
	}


	public static @NullAllow byte[] bytes(@NullAllow char[] array, @NullAllow byte[] init)
	{
		return bytes(array, init, false);
	}


	public static @NullAllow byte[] bytes(@NullAllow char[] array, @NullAllow byte[] init, boolean trim)
	{
		if (array != null)
		{
			if (NullUtil.notNull(array, trim))
			{
				byte[] bytes = primitive(array, trim);

				if (bytes != null) return bytes;
			}
			else
			{
				return null;
			}
		}

		return init;
	}


	public static @NullAllow byte[] primitive(@NullDisallow char[] array)
	{
		return primitive(array, 0, array.length, false);
	}


	public static @NullAllow byte[] primitive(@NullDisallow char[] array, boolean trim)
	{
		return primitive(array, 0, array.length, trim);
	}


	public static @NullAllow byte[] primitive(@NullDisallow char[] array, int from, int to)
	{
		return primitive(array, from, to, false);
	}


	public static @NullAllow byte[] primitive(@NullDisallow char[] array, int from, int to, boolean trim)
	{
		if (trim)
		{
			while (from < to && array[from] <= ' ') from++;

			while (from < to && array[to - 1] <= ' ') to--;
		}

		int length = to - from;

		if (length == 0)
		{
			return ByteArrayUtil.BLANK;
		}
		else if (IntUtil.even(length))
		{
			int count = length / 2;
			byte[] result = new byte[count];

			for (int i = 0; i < count; i++)
			{
				int index = from + i * 2;
				Byte b = convert(array[index], array[index + 1]);

				if (b != null) result[i] = b;
				else return null;
			}

			return result;
		}
		else
		{
			return null;
		}
	}


	/*
	 * byte[] -> char[]
	 */
	public static char[] convert(@NullDisallow byte[] array)
	{
		return convert(array, false);
	}


	public static char[] convert(@NullDisallow byte[] array, boolean capitalize)
	{
		int length = array.length;
		char[] result = new char[length * 2];

		if (capitalize)
		{
			for (int i = 0; i < length; i++)
			{
				byte b = array[i];
				int index = i * 2;
				result[index] = toUpperCaseHex(b >> 4);
				result[index + 1] = toUpperCaseHex(b);
			}
		}
		else
		{
			for (int i = 0; i < length; i++)
			{
				byte b = array[i];
				int index = i * 2;
				result[index] = toLowerCaseHex(b >> 4);
				result[index + 1] = toLowerCaseHex(b);
			}
		}

		return result;
	}


	/*
		char + char -> byte
	 */
	public static @NullAllow Byte convert(char c0, char c1)
	{
		int i0 = toInt(c0);
		int i1 = toInt(c1);

		return i0 != -1 && i1 != -1
			? (byte) ((i0 << SHIFT) + i1)
			: null;
	}


	@SuppressWarnings("DuplicateBranchesInSwitch")
	private static int toInt(char c)
	{
		switch (c)
		{
			case '0': return  0;
			case '1': return  1;
			case '2': return  2;
			case '3': return  3;
			case '4': return  4;
			case '5': return  5;
			case '6': return  6;
			case '7': return  7;
			case '8': return  8;
			case '9': return  9;

			case 'A': return 10;
			case 'B': return 11;
			case 'C': return 12;
			case 'D': return 13;
			case 'E': return 14;
			case 'F': return 15;

			case 'a': return 10;
			case 'b': return 11;
			case 'c': return 12;
			case 'd': return 13;
			case 'e': return 14;
			case 'f': return 15;
		}

		return -1;
	}


	private static char toLowerCaseHex(int i)
	{
		switch (i & 0xf)
		{
			case  0: return '0';
			case  1: return '1';
			case  2: return '2';
			case  3: return '3';
			case  4: return '4';
			case  5: return '5';
			case  6: return '6';
			case  7: return '7';
			case  8: return '8';
			case  9: return '9';
			case 10: return 'a';
			case 11: return 'b';
			case 12: return 'c';
			case 13: return 'd';
			case 14: return 'e';
			case 15: return 'f';
		}

		throw new RuntimeException("internal error");
	}


	private static char toUpperCaseHex(int i)
	{
		switch (i & 0xf)
		{
			case  0: return '0';
			case  1: return '1';
			case  2: return '2';
			case  3: return '3';
			case  4: return '4';
			case  5: return '5';
			case  6: return '6';
			case  7: return '7';
			case  8: return '8';
			case  9: return '9';
			case 10: return 'A';
			case 11: return 'B';
			case 12: return 'C';
			case 13: return 'D';
			case 14: return 'E';
			case 15: return 'F';
		}

		throw new RuntimeException("internal error");
	}


	/*
	 * equal
	 */
	public static <V, H extends IHex<V, H>> boolean equal(@NullAllow H value0, @NullAllow H value1) // ! <V, H extends IHex<V, H>>
	{
		return FactoryObjectUtil.equal(value0, value1);
	}

}
