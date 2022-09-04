package net.javenture.framework.util;


import net.javenture.framework.annotation.NullDisallow;


/*
	2019/10/04
 */
final public class StringTrimmer
{
	//
	final public @NullDisallow CharSequence SEQUENCE;
	final public int FROM;
	final public int TO;
	final public int LENGTH;
	final public boolean CHANGE;


	//
	public StringTrimmer(@NullDisallow CharSequence sequence)
	{
		int length = sequence.length();
		int from = 0;
		int to = length;

		if (length != 0)
		{
			while (from < to && sequence.charAt(to - 1) <= ' ') to--;

			while (from < to && sequence.charAt(from) <= ' ') from++;
		}

		//
		SEQUENCE = sequence;
		FROM = from;
		TO = to;
		LENGTH = to - from;
		CHANGE = from != 0 || to != length;
	}


	//
	@Override
	public String toString()
	{
		if (LENGTH != 0)
		{
			return CHANGE
				? SEQUENCE.subSequence(FROM, TO).toString()
				: SEQUENCE.toString();
		}
		else
		{
			return "";
		}
	}


	public CharSequence sequence()
	{
		return SEQUENCE;
	}


	public int from()
	{
		return FROM;
	}


	public int to()
	{
		return TO;
	}


	public int length()
	{
		return LENGTH;
	}


	public boolean change()
	{
		return CHANGE;
	}


	//
	public static String toString(@NullDisallow CharSequence sequence)
	{
		int length = sequence.length();

		if (length != 0)
		{
			int from = 0;
			int to = length;

			while (from < to && sequence.charAt(to - 1) <= ' ') to--;

			while (from < to && sequence.charAt(from) <= ' ') from++;

			if (to - from != 0)
			{
				return from != 0 || to != length
					? sequence.subSequence(from, to).toString()
					: sequence.toString();
			}
		}

		return "";
	}

}
