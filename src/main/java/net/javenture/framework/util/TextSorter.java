package net.javenture.framework.util;


import java.util.ArrayList;
import java.util.Collections;


final public class TextSorter<E>                                                   // XXX: check
{
	private enum Type
	{
		NUMBER(0),
		CHAR(1);

		final private int VALUE;

		private Type(int value)
		{
			VALUE = value;
		}

		public int value()
		{
			return VALUE;
		}
	}


	//
	final private ArrayList<Entry> ENTRIES;


	//
	public TextSorter()
	{
		ENTRIES = new ArrayList<>();
	}


	public TextSorter(int capacity)
	{
		ENTRIES = new ArrayList<>(capacity);
	}


	//
	public void add(E element, String label)
	{
		Entry entry = new Entry(element, label);
		ENTRIES.add(entry);
	}


	public ArrayList<E> build()
	{
		ArrayList<E> result = new ArrayList<>(ENTRIES.size());
		Collections.sort(ENTRIES);

		for (Entry entry : ENTRIES)
		{
			@SuppressWarnings("unchecked")
			E element = (E) entry.ELEMENT;

			result.add(element);
		}

		return result;
	}


	//
	private static class Entry implements Comparable<Entry>
	{
		final private Object ELEMENT;
		final private ArrayList<Token> TOKENS;

		private Entry(Object element, String label)
		{
			ELEMENT = element;
			TOKENS = parse(label);
		}

		private static ArrayList<Token> parse(String label)
		{
			ArrayList<Token> result = new ArrayList<>();

			int length = label.length();
			int i = 0;

			while (true)
			{
				// whitespace
				{
					int l = 0;

					while (i + l < length)
					{
						char c = label.charAt(i + l);

						if (c == ' ' || c == '\t' || c == '\r' || c == '\n') l++;
						else break;
					}

					if (l != 0)
					{
						i += l;

						continue;
					}
				}

				// number
				{
					int l = 0;

					while (i + l < length)
					{
						char c = label.charAt(i + l);

						if (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9') l++;
						else break;
					}

					if (l != 0)
					{
						String s = label.substring(i, i + l);
						int number = IntUtil.parse(s, Integer.MAX_VALUE);
						Token token = new Token(Type.NUMBER, number);
						result.add(token);
						i += l;

						continue;
					}
				}

				// char
				{
					if (i < length)
					{
						char c = Character.toUpperCase(label.charAt(i));
						Token token = new Token(Type.CHAR, c);
						result.add(token);
						i++;

						continue;
					}
				}

				break;
			}

			return result;
		}

		@Override
		public int compareTo(Entry entry)
		{
			ArrayList<Token> tokens1 = this.TOKENS;
			ArrayList<Token> tokens2 = entry.TOKENS;
			int size1 = tokens1.size();
			int size2 = tokens2.size();
			int size = Math.min(size1, size2);

			for (int i = 0; i < size; i++)
			{
				Token token1 = tokens1.get(i);
				Token token2 = tokens2.get(i);
				int compare = token1.compareTo(token2);

				if (compare != 0) return compare;
			}

			if (size1 < size2) return -1;
			else if (size1 > size2) return 1;

			return 0;
		}
	}


	private static class Token implements Comparable<Token>
	{
		final private Type TYPE;
		final private Object VALUE;

		private Token(Type type, Object value)
		{
			TYPE = type;
			VALUE = value;
		}

		@Override
		public int compareTo(Token token)
		{
			Type type1 = this.TYPE;
			Object value1 = this.VALUE;

			Type type2 = token.TYPE;
			Object value2 = token.VALUE;

			//
			if (type1.VALUE < type2.VALUE)
			{
				return -1;
			}
			else if (type1.VALUE > type2.VALUE)
			{
				return 1;
			}
			else
			{
				@SuppressWarnings("UnnecessaryLocalVariable")
				Type type = type1;

				if (type == Type.NUMBER) return Integer.compare((Integer) value1, (Integer) value2);
				else if (type == Type.CHAR) return Character.compare((Character) value1, (Character) value2);
			}

			return 0;
		}
	}

}
