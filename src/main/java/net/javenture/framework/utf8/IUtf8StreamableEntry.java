package net.javenture.framework.utf8;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.util.NullUtil;

import java.io.IOException;


/*
	2020/10/09
 */
@FunctionalInterface
public interface IUtf8StreamableEntry
{
	//
	void toUtf8Stream(Utf8OutputStream destination) throws IOException;


	//
	static IUtf8StreamableEntry entry(@NullDisallow byte[] array) // @NullDisallow !
	{
		return destination -> destination.write(array, 0, array.length);
	}


	static IUtf8StreamableEntry entry(@NullDisallow byte[] array, int from, int to) // @NullDisallow !
	{
		return destination -> destination.write(array, from, to - from);
	}


	static IUtf8StreamableEntry entry(@NullDisallow char[] array) // @NullDisallow !
	{
		return destination -> destination.write(array);
	}


	static IUtf8StreamableEntry entry(@NullDisallow char[] array, int from, int to) // @NullDisallow !
	{
		return destination -> destination.write(array, from, to);
	}


	static IUtf8StreamableEntry entry(@NullAllow CharSequence sequence)
	{
		return destination -> destination.write(sequence);
	}


	static IUtf8StreamableEntry entry(@NullAllow CharSequence sequence, int from, int to)
	{
		return destination -> destination.write(sequence, from, to);
	}


	static IUtf8StreamableEntry entry(@NullAllow CharSequence sequence, boolean bytes)
	{
		if (bytes)
		{
			return sequence != null
				? entry(Utf8Util.bytes(sequence))
				: NullUtil.ENTRY;
		}
		else
		{
			return destination -> destination.write(sequence);
		}
	}


	static IUtf8StreamableEntry entry(@NullAllow CharSequence sequence, int from, int to, boolean bytes)
	{
		if (bytes)
		{
			return sequence != null
				? entry(Utf8Util.bytes(sequence, from, to))
				: NullUtil.ENTRY;
		}
		else
		{
			return destination -> destination.write(sequence, from, to);
		}
	}


	//
	IUtf8StreamableEntry NULL = null;
	IUtf8StreamableEntry BLANK = destination -> {};

}
