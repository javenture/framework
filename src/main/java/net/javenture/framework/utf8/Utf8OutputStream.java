package net.javenture.framework.utf8;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.util.ByteOutputStream;
import net.javenture.framework.util.NullUtil;

import java.io.IOException;


/*
	2020/10/09
 */
public class Utf8OutputStream
	extends ByteOutputStream
{
	//
	public Utf8OutputStream()
	{
	}


/*
	public Utf8OutputStream(MemoryManager manager)                                        // XXX: ?
	{
		super(manager);
	}
*/


	//
	@Override
	final public Utf8InputStream toInputStream()
	{
		return new Utf8InputStream(this);
	}


	@Override
	final public String toString()
	{
		return toInputStream().toString();
	}


	public void write(@NullDisallow IUtf8StreamableEntry entry)
		throws IOException
	{
		entry.toUtf8Stream(this);
	}


	public void write(IUtf8StreamableEntry... entries) // ! not Iterable
		throws IOException
	{
		for (IUtf8StreamableEntry entry : entries) entry.toUtf8Stream(this);
	}


	public void write(char c)
		throws IOException
	{
		Utf8.entry(c).toUtf8Stream(this);
	}


	public void write(@NullDisallow char[] array) // ! @NullDisallow
		throws IOException
	{
		for (char c : array) write(c);
	}


	public void write(@NullDisallow char[] array, int from, int to) // ! @NullDisallow
		throws IOException
	{
		for (int i = from; i < to; i++) write(array[i]);
	}


	public void write(@NullAllow CharSequence sequence) // ! @NullAllow
		throws IOException
	{
		if (sequence != null)
		{
			int length = sequence.length();

			for (int i = 0; i < length; i++) write(sequence.charAt(i));
		}
		else
		{
			NullUtil.ENTRY.toUtf8Stream(this);
		}
	}


	public void write(@NullAllow CharSequence sequence, int from, int to) // ! @NullAllow
		throws IOException
	{
		if (sequence != null)
		{
			for (int i = from; i < to; i++) write(sequence.charAt(i));
		}
		else
		{
			NullUtil.ENTRY.toUtf8Stream(this);
		}
	}

}
