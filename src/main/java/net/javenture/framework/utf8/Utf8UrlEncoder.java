package net.javenture.framework.utf8;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.hex.HexUtil;


/*
	2018/05/30
 */
final public class Utf8UrlEncoder
{
	//
	final private static int COUNT;
	final private static IEntry[] ENTRIES;

	final private static int COUNT2;
	final private static IEntry[] ENTRIES2;


	//
	static
	{
		//
		{
			COUNT = Utf8.COUNT;
			ENTRIES = new IEntry[COUNT];

			for (int i = 0; i < COUNT; i++)
			{
				Utf8.AEntry entry = Utf8.ENTRIES[i];
				char c = entry.id();

				if
				(
					c >= 'a' && c <= 'z' ||
					c >= 'A' && c <= 'Z' ||
					c >= '0' && c <= '9' ||
					c == '.' ||
					c == '-' ||
					c == '*' ||
					c == '_'
				)
				{
					ENTRIES[i] = new Entry1(c);
				}
				else
				{
					int length = entry.length();

					if (length != 0)
					{
						char[] array = HexUtil.convert(entry.bytes(), true);

						switch (length)
						{
							case 1: ENTRIES[i] = new Entry3(array); break;
							case 2: ENTRIES[i] = new Entry6(array); break;
							case 3: ENTRIES[i] = new Entry9(array); break;
							default: throw new InternalError();
						}
					}
					else
					{
						ENTRIES[i] = new Entry0();
					}
				}
			}
		}

		//
		{
			COUNT2 = Utf8.COUNT2;
			ENTRIES2 = new IEntry[COUNT2];
			System.arraycopy(ENTRIES, 0, ENTRIES2, 0, COUNT2);
		}
	}


	//
	private Utf8UrlEncoder()
	{
	}


	//
	public static @NullDisallow String encode(@NullDisallow String s)
	{
		int length = s.length();
		IEntry[] entries = new IEntry[length];
		int length2 = 0;
		boolean change = false;

		for (int i = 0; i < length; i++)
		{
			IEntry entry = entry(s.charAt(i));
			entries[i] = entry;
			length2 += entry.length();

			if (entry.change()) change = true;
		}

		if (change)
		{
			char[] result = new char[length2];
			int position = 0;

			for (IEntry entry : entries) position += entry.toArray(result, position);

			return new String(result);
		}
		else
		{
			return s;
		}
	}


	private static IEntry entry(char c)
	{
		return c < COUNT2 ? ENTRIES2[c] : ENTRIES[c];
	}


	//
	private interface IEntry
	{
		//
		int length();

		boolean change();

		int toArray(char[] destination, int position);
	}


	final private static class Entry0
		implements IEntry
	{
		//
		private Entry0()
		{
		}

		//
		@Override
		public int length()
		{
			return 0;
		}

		@Override
		public boolean change()
		{
			return false;
		}

		@Override
		public int toArray(char[] destination, int position)
		{
			return 0;
		}
	}


	final private static class Entry1
		implements IEntry
	{
		//
		final private char CHAR;

		//
		private Entry1(char c)
		{
			CHAR = c;
		}

		//
		@Override
		public int length()
		{
			return 1;
		}

		@Override
		public boolean change()
		{
			return false;
		}

		@Override
		public int toArray(char[] destination, int position)
		{
			destination[position] = CHAR;

			return 1;
		}
	}


	final private static class Entry3
		implements IEntry
	{
		//
		final private char CHAR1;
		final private char CHAR2;

		//
		private Entry3(char[] array)
		{
			CHAR1 = array[0];
			CHAR2 = array[1];
		}

		//
		@Override
		public int length()
		{
			return 3;
		}

		@Override
		public boolean change()
		{
			return true;
		}

		@Override
		public int toArray(char[] destination, int position)
		{
			destination[position] = '%';
			destination[position + 1] = CHAR1;
			destination[position + 2] = CHAR2;

			return 3;
		}
	}


	final private static class Entry6
		implements IEntry
	{
		//
		final private char CHAR1;
		final private char CHAR2;
		final private char CHAR4;
		final private char CHAR5;

		//
		private Entry6(char[] array)
		{
			CHAR1 = array[0];
			CHAR2 = array[1];
			CHAR4 = array[2];
			CHAR5 = array[3];
		}

		//
		@Override
		public int length()
		{
			return 6;
		}

		@Override
		public boolean change()
		{
			return true;
		}

		@Override
		public int toArray(char[] destination, int position)
		{
			destination[position] = '%';
			destination[position + 1] = CHAR1;
			destination[position + 2] = CHAR2;
			destination[position + 3] = '%';
			destination[position + 4] = CHAR4;
			destination[position + 5] = CHAR5;

			return 6;
		}
	}


	final private static class Entry9
		implements IEntry
	{
		//
		final private char CHAR1;
		final private char CHAR2;
		final private char CHAR4;
		final private char CHAR5;
		final private char CHAR7;
		final private char CHAR8;

		//
		private Entry9(char[] array)
		{
			CHAR1 = array[0];
			CHAR2 = array[1];
			CHAR4 = array[2];
			CHAR5 = array[3];
			CHAR7 = array[4];
			CHAR8 = array[5];
		}

		//
		@Override
		public int length()
		{
			return 9;
		}

		@Override
		public boolean change()
		{
			return true;
		}

		@Override
		public int toArray(char[] destination, int position)
		{
			destination[position] = '%';
			destination[position + 1] = CHAR1;
			destination[position + 2] = CHAR2;
			destination[position + 3] = '%';
			destination[position + 4] = CHAR4;
			destination[position + 5] = CHAR5;
			destination[position + 6] = '%';
			destination[position + 7] = CHAR7;
			destination[position + 8] = CHAR8;

			return 9;
		}
	}

}

