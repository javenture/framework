package net.javenture.framework.ascii;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.util.ByteArrayUtil;


/*
	2020/05/15
 */
final public class AsciiUtil
{
	//
	private AsciiUtil()
	{
	}


	//
	public static byte[] bytes(char c)
	{
		return new byte[] { Ascii.entry(c).value() };
	}


	public static byte[] bytes(@NullDisallow char[] array)
	{
		return bytes(array, 0, array.length);
	}


	public static byte[] bytes(@NullDisallow char[] array, int from, int to)
	{
		int length = to - from;

		if (length > 1)
		{
			byte[] result = new byte[length * Ascii.DIMENSION];
			int position = 0;

			for (int i = from; i < to; i++) result[position++] = Ascii.entry(array[i]).value();

			return result;
		}
		else if (length == 1)
		{
			return bytes(array[from]);
		}
		else
		{
			return ByteArrayUtil.BLANK;
		}
	}


	public static byte[] bytes(@NullDisallow CharSequence sequence) // ! @NullDisallow
	{
		return bytes(sequence, 0, sequence.length());
	}


	public static byte[] bytes(@NullDisallow CharSequence sequence, int from, int to) // ! @NullDisallow
	{
		int length = to - from;

		if (length > 1)
		{
			byte[] result = new byte[length * Ascii.DIMENSION];
			int position = 0;

			for (int i = from; i < to; i++) result[position++] = Ascii.entry(sequence.charAt(i)).value();

			return result;
		}
		else if (length == 1)
		{
			return bytes(sequence.charAt(from));
		}
		else
		{
			return ByteArrayUtil.BLANK;
		}
	}

}
