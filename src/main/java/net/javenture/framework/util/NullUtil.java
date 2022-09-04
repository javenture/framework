package net.javenture.framework.util;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.utf8.IUtf8StreamableEntry;
import net.javenture.framework.utf8.Utf8Byte;


/*
	2019/10/03
 */
final public class NullUtil
{
	//
	final static char[] ARRAY = { 'n', 'u', 'l', 'l' };
	final public static String STRING = "null";
	final public static IUtf8StreamableEntry ENTRY = destination -> destination.write(Utf8Byte.N_SMALL, Utf8Byte.U_SMALL, Utf8Byte.L_SMALL, Utf8Byte.L_SMALL);


	//
	private NullUtil()
	{
	}


	//
	public static boolean isNull(@NullDisallow CharSequence sequence)
	{
		return isNull(sequence, 0, sequence.length(), false);
	}


	public static boolean isNull(@NullDisallow CharSequence sequence, boolean trim)
	{
		return isNull(sequence, 0, sequence.length(), trim);
	}


	public static boolean isNull(@NullDisallow CharSequence sequence, int from, int to)
	{
		return isNull(sequence, from, to, false);
	}


	public static boolean isNull(@NullDisallow CharSequence sequence, int from, int to, boolean trim)
	{
		if (trim)
		{
			while (from < to && sequence.charAt(from) <= ' ') from++;

			while (from < to && sequence.charAt(to - 1) <= ' ') to--;
		}

		return
			to - from == 4
			&&
			sequence.charAt(from) == 'n'
			&&
			sequence.charAt(from + 1) == 'u'
			&&
			sequence.charAt(from + 2) == 'l'
			&&
			sequence.charAt(from + 3) == 'l';
	}


	public static boolean isNull(@NullDisallow char[] array)
	{
		return isNull(array, 0, array.length, false);
	}


	public static boolean isNull(@NullDisallow char[] array, boolean trim)
	{
		return isNull(array, 0, array.length, trim);
	}


	public static boolean isNull(@NullDisallow char[] array, int from, int to)
	{
		return isNull(array, from, to, false);
	}


	public static boolean isNull(@NullDisallow char[] array, int from, int to, boolean trim)
	{
		if (trim)
		{
			while (from < to && array[from] <= ' ') from++;

			while (from < to && array[to - 1] <= ' ') to--;
		}

		return
			to - from == 4
			&&
			array[from] == 'n'
			&&
			array[from + 1] == 'u'
			&&
			array[from + 2] == 'l'
			&&
			array[from + 3] == 'l';
	}


	public static boolean notNull(@NullDisallow CharSequence sequence)
	{
		return notNull(sequence, 0, sequence.length(), false);
	}


	public static boolean notNull(@NullDisallow CharSequence sequence, boolean trim)
	{
		return notNull(sequence, 0, sequence.length(), trim);
	}


	public static boolean notNull(@NullDisallow CharSequence sequence, int from, int to)
	{
		return notNull(sequence, from, to, false);
	}


	public static boolean notNull(@NullDisallow CharSequence sequence, int from, int to, boolean trim)
	{
		if (trim)
		{
			while (from < to && sequence.charAt(from) <= ' ') from++;

			while (from < to && sequence.charAt(to - 1) <= ' ') to--;
		}

		return
			to - from != 4
			||
			sequence.charAt(from) != 'n'
			||
			sequence.charAt(from + 1) != 'u'
			||
			sequence.charAt(from + 2) != 'l'
			||
			sequence.charAt(from + 3) != 'l';
	}


	public static boolean notNull(@NullDisallow char[] array)
	{
		return notNull(array, 0, array.length, false);
	}


	public static boolean notNull(@NullDisallow char[] array, boolean trim)
	{
		return notNull(array, 0, array.length, trim);
	}


	public static boolean notNull(@NullDisallow char[] array, int from, int to)
	{
		return notNull(array, from, to, false);
	}


	public static boolean notNull(@NullDisallow char[] array, int from, int to, boolean trim)
	{
		if (trim)
		{
			while (from < to && array[from] <= ' ') from++;

			while (from < to && array[to - 1] <= ' ') to--;
		}

		return
			to - from != 4
			||
			array[from] != 'n'
			||
			array[from + 1] != 'u'
			||
			array[from + 2] != 'l'
			||
			array[from + 3] != 'l';
	}

}
