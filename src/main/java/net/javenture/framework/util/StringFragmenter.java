package net.javenture.framework.util;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullContains;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.ICopyFactory;
import net.javenture.framework.log.ILog;
import net.javenture.framework.log.LogAttachment;

import java.util.ArrayList;
import java.util.List;


/*
	2020/10/06
 */
final public class StringFragmenter
	implements ILog
{
	//
	final private static int CAPACITY = 64;
	final public static ICopyFactory<StringFragmenter> FACTORY_COPY = object -> object != null ? new StringFragmenter(object) : null;


	//
	final private @NullDisallow ArrayList<char[]> LIST; // ! ArrayList
	final private @NullAllow char[] DELIMITER;


	//
	public StringFragmenter()
	{
		LIST = new ArrayList<>(CAPACITY);
		DELIMITER = null;
	}


	public StringFragmenter(int capacity)
	{
		LIST = new ArrayList<>(capacity);
		DELIMITER = null;
	}


	public StringFragmenter(char delimiter)
	{
		LIST = new ArrayList<>(CAPACITY);
		DELIMITER = new char[] { delimiter };
	}


	public StringFragmenter(@NullAllow CharSequence delimiter)
	{
		LIST = new ArrayList<>(CAPACITY);
		DELIMITER = delimiter != null ? delimiter.toString().toCharArray() : null;
	}


	public StringFragmenter(int capacity, char delimiter)
	{
		LIST = new ArrayList<>(capacity);
		DELIMITER = new char[] { delimiter };
	}


	public StringFragmenter(int capacity, @NullAllow CharSequence delimiter)
	{
		LIST = new ArrayList<>(capacity);
		DELIMITER = delimiter != null ? delimiter.toString().toCharArray() : null;
	}


	private StringFragmenter(@NullDisallow StringFragmenter source)
	{
		Validator.notNull(source, "[source]");

		this.LIST = new ArrayList<>(source.LIST);
		this.DELIMITER = source.DELIMITER;
	}


	//
	@Override
	public LogAttachment log()
	{
		LogAttachment result = new LogAttachment();
		result.add(LIST, "LIST");
		result.add(DELIMITER, "DELIMITER");

		return result;
	}


	@Override
	public String toString()
	{
		return toString(false);
	}


	public String toString(boolean reverse)
	{
		int size = LIST.size();

		if (size == 0)
		{
			return "";
		}
		else if (size == 1)
		{
			return new String(LIST.get(0));
		}
		else if (DELIMITER != null)
		{
			int capacity = DELIMITER.length * (size - 1);

			for (char[] array : LIST) capacity += array.length;

			//
			CharContainer result = new CharContainer(capacity);

			if (reverse)
			{
				int first = size - 1;
				int last = 0;

				for (int i = first; i > last; i--)
				{
					result.add(LIST.get(i));
					result.add(DELIMITER);
				}

				{
					result.add(LIST.get(last));
				}
			}
			else
			{
				int first = 0;
				int last = size - 1;

				for (int i = first; i < last; i++)
				{
					result.add(LIST.get(i));
					result.add(DELIMITER);
				}

				{
					result.add(LIST.get(last));
				}
			}

			return result.string(); // ! .string()
		}
		else
		{
			int capacity = 0;

			for (char[] array : LIST) capacity += array.length;

			//
			CharContainer result = new CharContainer(capacity);

			if (reverse)
			{
				for (int i = size - 1; i >= 0; i--) result.add(LIST.get(i));
			}
			else
			{
				for (char[] array : LIST) result.add(array);
			}

			return result.string(); // ! .string()
		}
	}


	public boolean exists()
	{
		return !LIST.isEmpty();
	}


	public int size()
	{
		return LIST.size();
	}


	public void ensure(int count)
	{
		int size = LIST.size();
		LIST.ensureCapacity(size + count);
	}


	public void add(char c)
	{
		LIST.add(new char[] { c });
	}


	public void add(@NullAllow char[] array)
	{
		if (array != null) LIST.add(array.length != 0 ? array.clone() : CharArrayUtil.BLANK);
		else LIST.add(NullUtil.ARRAY);
	}


	public void add(@NullAllow CharSequence value)
	{
		if (value != null) LIST.add(value.length() != 0 ? value.toString().toCharArray() : CharArrayUtil.BLANK);
		else LIST.add(NullUtil.ARRAY);
	}


	public void add(@NullContains CharSequence... values)
	{
		ensure(values.length);

		for (CharSequence value : values) add(value);
	}


	public void add(@NullAllow Object value)
	{
		if (value != null)
		{
			String s = value.toString();
			LIST.add(!s.isEmpty() ? s.toCharArray() : CharArrayUtil.BLANK);
		}
		else
		{
			LIST.add(NullUtil.ARRAY);
		}
	}


	public void add(@NullContains Object... values)
	{
		ensure(values.length);

		for (Object value : values) add(value);
	}


	public void add(@NullContains List values)
	{
		ensure(values.size());

		for (Object value : values) add(value);
	}


	public <V, C extends IContainer> void add(@NullDisallow IContainer<V, C> container) // ! not Iterable
	{
		Validator.notNull(container, "[container]");
		ensure(container.size());

		for (V value : container) add(value);
	}


	public <V, S extends ISequence> void add(@NullDisallow ISequence<V, S> sequence) // ! not Iterable
	{
		Validator.notNull(sequence, "[sequence]");
		ensure(sequence.size());

		for (V value : sequence) add(value);
	}


	public void wrap(@NullAllow char[] array)
	{
		if (array != null) LIST.add(array.length != 0 ? array : CharArrayUtil.BLANK);
		else LIST.add(NullUtil.ARRAY);
	}

}
