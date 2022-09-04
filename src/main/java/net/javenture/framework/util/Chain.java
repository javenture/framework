package net.javenture.framework.util;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullContains;
import net.javenture.framework.annotation.SingleThread;

import java.util.Iterator;
import java.util.NoSuchElementException;


/*
	2020/09/28
 */
@SingleThread
final public class Chain<T>
	implements Iterable<T>
{
	//
	private @NullAllow Entry first;
	private @NullAllow Entry last;
	private int size;


	//
	public Chain()
	{
		first = null;
		last = null;
		size = 0;
	}


	@SuppressWarnings("CopyConstructorMissesField")
	public Chain(Chain<T> chain)
	{
		this();

		add(chain);
	}


	public Chain(T value)
	{
		this();

		add(value);
	}


	@SafeVarargs
	public Chain(T... values)
	{
		this();

		add(values);
	}


	public Chain(T[] values, int from, int to)
	{
		this();

		add(values, from, to);
	}


	public Chain(Iterable<T> values)
	{
		this();

		add(values);
	}


	//
	@Override
	public Iterator<T> iterator()
	{
		return new Iterator<>()
		{
			private Entry current = new Entry(null, first); // blank

			@Override
			public boolean hasNext()
			{
				return current.next != null;
			}

			@Override
			public T next()
			{
				T value = current.next.VALUE;
				current = current.next;

				return value;
			}
		};
	}


	public boolean exists()
	{
		return size != 0;
	}


	public int size()
	{
		return size;
	}


	public void add(@NullContains Chain<T> chain)
	{
		for (T value : chain) add(value);
	}


	public void add(@NullAllow T value)
	{
		Entry entry = new Entry(value);

		if (first == null) first = entry;
		else last.next = entry;

		last = entry;
		size++;
	}


	@SafeVarargs
	final public void add(@NullContains T... values)
	{
		for (T value : values) add(value);
	}


	public void add(@NullContains T[] values, int from, int to)
	{
		for (int i = from; i < to; i++) add(values[i]);
	}


	public void add(@NullContains Iterable<T> values)
	{
		for (T value : values) add(value);
	}


	public @NullAllow T first()
	{
		if (first != null) return first.VALUE;
		else throw new NoSuchElementException();
	}


	public @NullAllow T last()
	{
		if (last != null) return last.VALUE;
		else throw new NoSuchElementException();
	}


	public void clear()
	{
		Entry current = first;

		while (current != null)
		{
			Entry next = current.next;
			current.next = null;
			current = next;
		}

		first = null;
		last = null;
		size = 0;
	}


	@SuppressWarnings("unchecked")
	public T[] array()
	{
		T[] result = (T[]) new Object[size];
		int i = 0;

		for (T entry : this) result[i++] = entry;

		return result;
	}


	//
	final private class Entry
	{
		//
		final private @NullAllow T VALUE;

		private @NullAllow Entry next;

		//
		private Entry(@NullAllow T value)
		{
			VALUE = value;
			next = null;
		}

		private Entry(@NullAllow T value, Entry next)
		{
			VALUE = value;
			this.next = next;
		}
	}

}
