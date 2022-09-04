package net.javenture.framework.utf8;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.util.ByteArrayUtil;
import net.javenture.framework.util.IByteSequence;
import net.javenture.framework.util.IntUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;


/*
	2020/05/15
 */
final public class Utf8
{
	//
	final private static Charset CHARSET = StandardCharsets.UTF_8;
	final private static byte ZERO = 0;
	final private static int UNKNOWN = '?';

	final private static int BYTE_MASK = 0b11111111;
	final private static int BYTES1_LEADING_MASK = 0b10000000;
	final private static int BYTES1_LEADING_PREFIX = 0b00000000; // 0xxxxxxx
	final private static int BYTES2_LEADING_MASK = 0b11100000;
	final private static int BYTES2_LEADING_PREFIX = 0b11000000; // 110xxxxx
	final private static int BYTES3_LEADING_MASK = 0b11110000;
	final private static int BYTES3_LEADING_PREFIX = 0b11100000; // 1110xxxx
	final private static int BYTES_CONTINUATION_MASK = 0b11000000;
	final private static int BYTES_CONTINUATION_PREFIX = 0b10000000; // 10xxxxxx
	final private static int BYTES2_CONTENT0_MASK = 0b00011111;
	final private static int BYTES2_CONTENT0_SHIFT = 6;
	final private static int BYTES2_CONTENT1_MASK = 0b00111111;
	final private static int BYTES3_CONTENT0_MASK = 0b00001111;
	final private static int BYTES3_CONTENT0_SHIFT = 12;
	final private static int BYTES3_CONTENT1_MASK = 0b00111111;
	final private static int BYTES3_CONTENT1_SHIFT = 6;
	final private static int BYTES3_CONTENT2_MASK = 0b00111111;

	final static int DIMENSION = 3; // 1 ... 3
	final static int COUNT = 65536;
	final static int COUNT1 = 128;
	final static int COUNT2 = 2048;

	//
	final static AEntry[] ENTRIES;
	final static AEntry[] ENTRIES1;
	final static AEntry[] ENTRIES2;

	//
	static
	{
		ENTRIES = new AEntry[COUNT];

		for (int i = 0; i < COUNT; i++)
		{
			char c = (char) i;

			if (Character.isSurrogate(c))
			{
				ENTRIES[c] = new Entry0(c);
			}
			else
			{
				byte[] bytes = ("" + c).getBytes(CHARSET);

				switch (bytes.length)
				{
					case 1:
					{
						ENTRIES[c] = new Entry1(c, bytes[0]);

						break;
					}

					case 2:
					{
						ENTRIES[c] = new Entry2(c, bytes[0], bytes[1]);

						break;
					}

					case 3:
					{
						ENTRIES[c] = new Entry3(c, bytes[0], bytes[1], bytes[2]);

						break;
					}

					default: throw new UnsupportedOperationException();
				}
			}
		}

		//
		ENTRIES1 = Arrays.copyOf(ENTRIES, COUNT1);
		ENTRIES2 = Arrays.copyOf(ENTRIES, COUNT2);
	}


	//
	private Utf8()
	{
	}


	//
	static AEntry entry(char c)
	{
		if (c < COUNT1) return ENTRIES1[c];
		else if (c < COUNT2) return ENTRIES2[c];
		else return ENTRIES[c];
	}


	/**
	 *	@return char | -1 (end of stream)
	 */
	public static int parse(IByteSequence sequence)
	{
		int i0 = sequence.get();

		if (i0 == -1) // end of stream
		{
			return -1;
		}
		else
		{
			i0 &= BYTE_MASK;

			if ((i0 & BYTES1_LEADING_MASK) == BYTES1_LEADING_PREFIX) // one byte
			{
				return i0;
			}
			else if ((i0 & BYTES2_LEADING_MASK) == BYTES2_LEADING_PREFIX) // two bytes
			{
				int i1 = sequence.get();

				if (i1 == -1) // end of stream
				{
					return UNKNOWN;
				}
				else
				{
					i1 &= BYTE_MASK;

					if ((i1 & BYTES_CONTINUATION_MASK) == BYTES_CONTINUATION_PREFIX)
					{
						return
							(i0 & BYTES2_CONTENT0_MASK) << BYTES2_CONTENT0_SHIFT
							|
							(i1 & BYTES2_CONTENT1_MASK);
					}
					else
					{
						return UNKNOWN;
					}
				}
			}
			else if ((i0 & BYTES3_LEADING_MASK) == BYTES3_LEADING_PREFIX) // three bytes
			{
				int i1 = sequence.get();
				int i2 = sequence.get();

				if (i1 == -1 || i2 == -1) // end of stream
				{
					return UNKNOWN;
				}
				else
				{
					i1 &= BYTE_MASK;
					i2 &= BYTE_MASK;

					if
					(
						(i1 & BYTES_CONTINUATION_MASK) == BYTES_CONTINUATION_PREFIX
						&&
						(i2 & BYTES_CONTINUATION_MASK) == BYTES_CONTINUATION_PREFIX
					)
					{
						return
							(i0 & BYTES3_CONTENT0_MASK) << BYTES3_CONTENT0_SHIFT
							|
							(i1 & BYTES3_CONTENT1_MASK) << BYTES3_CONTENT1_SHIFT
							|
							(i2 & BYTES3_CONTENT2_MASK);
					}
					else
					{
						return UNKNOWN;
					}
				}
			}
			else
			{
				return UNKNOWN;
			}
		}
	}


	/**
	 *	@return char | -1 (end of stream)
	 */
	public static int parse(InputStream stream)
		throws IOException
	{
		int i0 = stream.read();

		if (i0 == -1) // end of stream
		{
			return -1;
		}
		else
		{
			i0 &= BYTE_MASK;

			if ((i0 & BYTES1_LEADING_MASK) == BYTES1_LEADING_PREFIX) // one byte
			{
				return i0;
			}
			else if ((i0 & BYTES2_LEADING_MASK) == BYTES2_LEADING_PREFIX) // two bytes
			{
				int i1 = stream.read();

				if (i1 == -1) // end of stream
				{
					return UNKNOWN;
				}
				else
				{
					i1 &= BYTE_MASK;

					if ((i1 & BYTES_CONTINUATION_MASK) == BYTES_CONTINUATION_PREFIX)
					{
						return
							(i0 & BYTES2_CONTENT0_MASK) << BYTES2_CONTENT0_SHIFT
							|
							(i1 & BYTES2_CONTENT1_MASK);
					}
					else
					{
						return UNKNOWN;
					}
				}
			}
			else if ((i0 & BYTES3_LEADING_MASK) == BYTES3_LEADING_PREFIX) // three bytes
			{
				int i1 = stream.read();
				int i2 = stream.read();

				if (i1 == -1 || i2 == -1) // end of stream
				{
					return UNKNOWN;
				}
				else
				{
					i1 &= BYTE_MASK;
					i2 &= BYTE_MASK;

					if
					(
						(i1 & BYTES_CONTINUATION_MASK) == BYTES_CONTINUATION_PREFIX
						&&
						(i2 & BYTES_CONTINUATION_MASK) == BYTES_CONTINUATION_PREFIX
					)
					{
						return
							(i0 & BYTES3_CONTENT0_MASK) << BYTES3_CONTENT0_SHIFT
							|
							(i1 & BYTES3_CONTENT1_MASK) << BYTES3_CONTENT1_SHIFT
							|
							(i2 & BYTES3_CONTENT2_MASK);
					}
					else
					{
						return UNKNOWN;
					}
				}
			}
			else
			{
				return UNKNOWN;
			}
		}
	}


	static Iterator<AEntry> iterator()
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
			public AEntry next()
			{
				return ENTRIES[++index];
			}
		};
	}


	//
	abstract static class AEntry
		implements IUtf8StreamableEntry
	{
		//
		final private int LENGTH;
		final private char CHAR;
		final private byte[] BYTES;
		final private String STRING;
		final private int HASHCODE;

		//
		private AEntry(char c)
		{
			LENGTH = 0;
			CHAR = c;
			BYTES = ByteArrayUtil.BLANK;
			STRING = "" + c;
			HASHCODE = 0;
		}

		private AEntry(char c, byte b0)
		{
			LENGTH = 1;
			CHAR = c;
			BYTES = new byte[] { b0 };
			STRING = "" + c;
			HASHCODE = IntUtil.parse(ZERO, ZERO, ZERO, b0);
		}

		private AEntry(char c, byte b0, byte b1)
		{
			LENGTH = 2;
			CHAR = c;
			BYTES = new byte[] { b0, b1 };
			STRING = "" + c;
			HASHCODE = IntUtil.parse(ZERO, ZERO, b0, b1);
		}

		private AEntry(char c, byte b0, byte b1, byte b2)
		{
			LENGTH = 3;
			CHAR = c;
			BYTES = new byte[] { b0, b1, b2 };
			STRING = "" + c;
			HASHCODE = IntUtil.parse(ZERO, b0, b1, b2);
		}

		//
		@Override
		final public int hashCode()
		{
			return HASHCODE;
		}

		@Override
		@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
		final public boolean equals(@NullAllow Object object)
		{
			return equals((AEntry) object);
		}

		final public boolean equals(@NullAllow AEntry entry)
		{
			return
				this == entry
				||
				entry != null && entry.CHAR == this.CHAR;
		}

		@Override
		final public String toString()
		{
			return STRING;
		}

		final public int length()
		{
			return LENGTH;
		}

		final public char id()
		{
			return CHAR;
		}

		final public byte[] bytes()
		{
			return BYTES.clone();
		}

		//
		abstract int toArray(byte[] destination, int position);
	}


	final private static class Entry0
		extends AEntry
	{
		//
		private Entry0(char c)
		{
			super(c);
		}

		//
		@Override
		int toArray(byte[] destination, int position)
		{
			return 0;
		}

		@Override
		public void toUtf8Stream(Utf8OutputStream destination)
		{
		}
	}


	final private static class Entry1
		extends AEntry
	{
		//
		final private byte BYTE0;

		//
		private Entry1(char c, byte b0)
		{
			super(c, b0);

			BYTE0 = b0;
		}

		//
		@Override
		int toArray(byte[] destination, int position)
		{
			destination[position] = BYTE0;

			return 1;
		}

		@Override
		public void toUtf8Stream(Utf8OutputStream destination)
			throws IOException
		{
			destination.write(BYTE0);
		}
	}


	final private static class Entry2
		extends AEntry
	{
		//
		final private byte BYTE0;
		final private byte BYTE1;

		//
		private Entry2(char c, byte b0, byte b1)
		{
			super(c, b0, b1);

			BYTE0 = b0;
			BYTE1 = b1;
		}

		//
		@Override
		int toArray(byte[] destination, int position)
		{
			destination[position] = BYTE0;
			destination[position + 1] = BYTE1;

			return 2;
		}

		@Override
		public void toUtf8Stream(Utf8OutputStream destination)
			throws IOException
		{
			destination.write(BYTE0, BYTE1);
		}
	}


	final private static class Entry3
		extends AEntry
	{
		//
		final private byte BYTE0;
		final private byte BYTE1;
		final private byte BYTE2;

		//
		private Entry3(char c, byte b0, byte b1, byte b2)
		{
			super(c, b0, b1, b2);

			BYTE0 = b0;
			BYTE1 = b1;
			BYTE2 = b2;
		}

		//
		@Override
		int toArray(byte[] destination, int position)
		{
			destination[position] = BYTE0;
			destination[position + 1] = BYTE1;
			destination[position + 2] = BYTE2;

			return 3;
		}

		@Override
		public void toUtf8Stream(Utf8OutputStream destination)
			throws IOException
		{
			destination.write(BYTE0, BYTE1, BYTE2);
		}
	}

}
