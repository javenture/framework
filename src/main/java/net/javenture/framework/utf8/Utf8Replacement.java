package net.javenture.framework.utf8;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.util.NullUtil;
import net.javenture.framework.util.Validator;

import java.io.IOException;
import java.util.Arrays;


/*
	2019/06/18
 */
final public class Utf8Replacement
{
	//
	final private IUtf8StreamableEntry[] ENTRIES;
	final private IUtf8StreamableEntry[] ENTRIES1;
	final private IUtf8StreamableEntry[] ENTRIES2;


	//
	private Utf8Replacement()
	{
		ENTRIES = Utf8.ENTRIES;
		ENTRIES1 = Utf8.ENTRIES1;
		ENTRIES2 = Utf8.ENTRIES2;
	}


	public Utf8Replacement(Pair... pairs)
	{
		Validator.argument(pairs.length != 0, "empty list");

		//
		IUtf8StreamableEntry[] entries = new IUtf8StreamableEntry[Utf8.COUNT];

		for (Utf8.AEntry entry : Utf8.ENTRIES) entries[entry.id()] = entry;

		for (Pair pair : pairs) entries[pair.PATTERN] = pair.REPLACEMENT;

		//
		ENTRIES = entries;
		ENTRIES1 = Arrays.copyOf(entries, Utf8.COUNT1);
		ENTRIES2 = Arrays.copyOf(entries, Utf8.COUNT2);
	}


	//
	public IUtf8StreamableEntry entry(char c)
	{
		if (c < Utf8.COUNT1) return ENTRIES1[c];
		else if (c < Utf8.COUNT2) return ENTRIES2[c];
		else return ENTRIES[c];
	}


	public IUtf8StreamableEntry entry(@NullDisallow char[] array)
	{
		return destination -> toStream(destination, array);
	}


	public IUtf8StreamableEntry entry(@NullDisallow char[] array, int from, int to)
	{
		return destination -> toStream(destination, array, from, to);
	}


	public IUtf8StreamableEntry entry(@NullAllow CharSequence sequence)
	{
		return destination -> toStream(destination, sequence);
	}


	public IUtf8StreamableEntry entry(@NullAllow CharSequence sequence, int from, int to)
	{
		return destination -> toStream(destination, sequence, from, to);
	}


	public void toStream(Utf8OutputStream destination, char c)
		throws IOException
	{
		entry(c).toUtf8Stream(destination);
	}


	public void toStream(Utf8OutputStream destination, @NullAllow CharSequence sequence)
		throws IOException
	{
		if (sequence != null)
		{
			int length = sequence.length();

			if (length > 1)
			{
				for (int i = 0; i < length; i++) toStream(destination, sequence.charAt(i));
			}
			else if (length == 1)
			{
				toStream(destination, sequence.charAt(0));
			}
		}
		else
		{
			NullUtil.ENTRY.toUtf8Stream(destination);
		}
	}


	public void toStream(Utf8OutputStream destination, @NullAllow CharSequence sequence, int from, int to)
		throws IOException
	{
		if (sequence != null)
		{
			int length = to - from;

			if (length > 1)
			{
				for (int i = from; i < to; i++) toStream(destination, sequence.charAt(i));
			}
			else if (length == 1)
			{
				toStream(destination, sequence.charAt(0));
			}
		}
		else
		{
			NullUtil.ENTRY.toUtf8Stream(destination);
		}
	}


	public void toStream(Utf8OutputStream destination, @NullDisallow char[] array)
		throws IOException
	{
		for (char c : array) entry(c).toUtf8Stream(destination);
	}


	public void toStream(Utf8OutputStream destination, @NullDisallow char[] array, int from, int to)
		throws IOException
	{
		for (int i = from; i < to; i++) entry(array[i]).toUtf8Stream(destination);
	}


	//
	final public static class Pair
	{
		//
		final private char PATTERN;
		final private IUtf8StreamableEntry REPLACEMENT;

		//
		public Pair(char pattern, @NullDisallow IUtf8StreamableEntry replacement)
		{
			Validator.notNull(replacement, "[replacement]");

			PATTERN = pattern;
			REPLACEMENT = replacement;
		}

		public Pair(char pattern, @NullDisallow String replacement)
		{
			Validator.notNull(replacement, "[replacement]");

			PATTERN = pattern;
			REPLACEMENT = IUtf8StreamableEntry.entry(replacement, true);
		}
	}


	//
	final public static Utf8Replacement BLANK = new Utf8Replacement();

}
