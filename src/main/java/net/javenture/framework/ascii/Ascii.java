package net.javenture.framework.ascii;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.utf8.IUtf8StreamableEntry;
import net.javenture.framework.utf8.Utf8OutputStream;
import net.javenture.framework.util.IByteSequence;
import net.javenture.framework.util.IntUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;


/*
	2020/05/15
 */
final public class Ascii
{
	//
	final private static Charset CHARSET = StandardCharsets.US_ASCII;
	final private static byte ZERO = 0;
	final private static int UNKNOWN = '?';

	final private static int BYTE_MASK = 0b11111111;
	final private static int BYTE_LEADING_MASK = 0b10000000;
	final private static int BYTE_LEADING_PREFIX = 0b00000000; // 0xxxxxxx

	final static int DIMENSION = 1;
	final static int COUNT = 128;

	//
	final static Entry[] ENTRIES;

	//
	static
	{
		ENTRIES = new Entry[COUNT];

		for (int i = 0; i < COUNT; i++)
		{
			char c = (char) i;
			byte[] bytes = ("" + c).getBytes(CHARSET);
			ENTRIES[c] = new Entry(c, bytes[0]);
		}
	}


	//
	private Ascii()
	{
	}


	//
	static Entry entry(char c)
	{
		return ENTRIES[c];
	}


	/**
	 *	@return char | -1 (end of stream)
	 */
	public static int parse(IByteSequence sequence)
	{
		int i = sequence.get();

		if (i == -1) // end of stream
		{
			return -1;
		}
		else
		{
			i &= BYTE_MASK;

			return (i & BYTE_LEADING_MASK) == BYTE_LEADING_PREFIX
				? i
				: UNKNOWN;
		}
	}


	/**
	 *	@return char | -1 (end of stream)
	 */
	public static int parse(InputStream stream)
		throws IOException
	{
		int i = stream.read();

		if (i == -1) // end of stream
		{
			return -1;
		}
		else
		{
			i &= BYTE_MASK;

			return (i & BYTE_LEADING_MASK) == BYTE_LEADING_PREFIX
				? i
				: UNKNOWN;
		}
	}


	static Iterator<Entry> iterator()
	{
		return new Iterator<>()
		{
			//
			int index = -1;

			//
			@Override
			public boolean hasNext()
			{
				return index + 1 < COUNT;
			}

			@Override
			public Entry next()
			{
				return ENTRIES[++index];
			}
		};
	}


	//
	final static class Entry
		implements IUtf8StreamableEntry
	{
		//
		final private int LENGTH;
		final private char CHAR;
		final private byte VALUE;
		final private String STRING;
		final private int HASHCODE;

		//
		private Entry(char c, byte b)
		{
			LENGTH = 1;
			CHAR = c;
			VALUE = b;
			STRING = "" + c;
			HASHCODE = IntUtil.parse(ZERO, ZERO, ZERO, b);
		}

		//
		@Override
		public int hashCode()
		{
			return HASHCODE;
		}

		@Override
		@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
		public boolean equals(@NullAllow Object object)
		{
			return equals((Entry) object);
		}

		public boolean equals(@NullAllow Entry entry)
		{
			return
				this == entry
				||
				entry != null && entry.CHAR == this.CHAR;
		}

		@Override
		public String toString()
		{
			return STRING;
		}

		@Override
		public void toUtf8Stream(Utf8OutputStream destination)
			throws IOException
		{
			destination.write(VALUE);
		}

		public int length()
		{
			return LENGTH;
		}

		public char id()
		{
			return CHAR;
		}

		public byte value()
		{
			return VALUE;
		}

		int toArray(byte[] destination, int position)
		{
			destination[position] = VALUE;

			return 1;
		}
	}

}
