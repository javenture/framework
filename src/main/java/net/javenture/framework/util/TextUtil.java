package net.javenture.framework.util;


import net.javenture.framework.annotation.NullDisallow;


/*
	2019/07/13
 */
final public class TextUtil
{
	//
	private TextUtil()
	{
	}


	//
	public static String replace(String source, char pattern, char replacement)
	{
		int length = source.length();
		char[] array = new char[length];
		boolean change = false;

		for (int i = 0; i < length; i++)
		{
			if (source.charAt(i) == pattern)
			{
				array[i] = replacement;
				change = true;
			}
		}

		return change ? new String(array) : source;
	}


	public static CharSequence replace(CharSequence source, char pattern, char replacement)
	{
		int length = source.length();
		char[] array = new char[length];
		boolean change = false;

		for (int i = 0; i < length; i++)
		{
			if (source.charAt(i) == pattern)
			{
				array[i] = replacement;
				change = true;
			}
		}

		return change ? new String(array) : source;
	}


	public static int index(@NullDisallow CharSequence s, char c, int from, int to)
	{
		int length = s.length();

		if (from < length)
		{
			Validator.argument(from >= 0, "[from] (", from, ") < 0");
			Validator.argument(to <= length, "[to] (", to, ") > [length] (", length, ")");

			return from < to ? index0(s, c, from, to) : -1;
		}
		else
		{
			return -1;
		}
	}


	static int index0(@NullDisallow CharSequence s, char c, int from, int to)
	{
		for (int i = from; i < to; i++)
		{
			if (s.charAt(i) == c) return i;
		}

		return -1;
	}

}
