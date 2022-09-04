package net.javenture.framework.netty;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.annotation.SingleThread;
import net.javenture.framework.util.Chain;
import net.javenture.framework.util.Validator;

import io.netty.handler.codec.http.HttpRequest;

import java.util.Iterator;
import java.util.Map;


/*
	2020/10/02
 */
@SingleThread
final public class NettyHeaders
	implements Iterable<NettyHeaders.Entry>
{
	//
	final private Chain<Entry> ENTRIES;


	//
	public NettyHeaders()
	{
		ENTRIES = new Chain<>();
	}


	public NettyHeaders(HttpRequest request)
	{
		ENTRIES = new Chain<>();

		for (Map.Entry<String, String> header : request.headers())
		{
			String name = header.getKey();
			String value = header.getValue();
			ENTRIES.add(new Entry(name, value));
		}
	}


	//
	public boolean exists()
	{
		return ENTRIES.exists();
	}


	public void add(@NullDisallow CharSequence name, @NullDisallow String value)
	{
		ENTRIES.add(new Entry(name, value));
	}


	public void add(@NullDisallow CharSequence name, @NullDisallow Object value)
	{
		ENTRIES.add(new Entry(name, value));
	}


	public @NullAllow String get(@NullDisallow CharSequence name)
	{
		Validator.notNull(name, "[name]");

		for (Entry entry : ENTRIES)
		{
			if (entry.NAME.contentEquals(name)) return entry.VALUE;
		}

		return null;
	}


	public boolean contains(@NullDisallow CharSequence name, @NullDisallow CharSequence value)
	{
		Validator.notNull(name, "[name]");
		Validator.notNull(value, "[value]");

		for (Entry entry : ENTRIES)
		{
			if (entry.NAME.contentEquals(name) && entry.VALUE.contentEquals(value)) return true;
		}

		return false;
	}


	public void clear()
	{
		ENTRIES.clear();
	}


	@Override
	public Iterator<Entry> iterator()
	{
		return ENTRIES.iterator();
	}


	//
	final public static class Entry
	{
		//
		final public @NullDisallow String NAME;
		final public @NullDisallow String VALUE;

		//
		private Entry(@NullDisallow CharSequence name, @NullDisallow String value)
		{
			Validator.notNull(name, "[name]");
			Validator.notNull(value, "[value]");

			NAME = normalize(name);
			VALUE = value;
		}

		private Entry(@NullDisallow CharSequence name, @NullDisallow Object value)
		{
			Validator.notNull(name, "[name]");
			Validator.notNull(value, "[value]");

			NAME = normalize(name);
			VALUE = value.toString();
		}

		//
		public @NullDisallow String name()
		{
			return NAME;
		}

		public @NullDisallow String value()
		{
			return VALUE;
		}

		//
		private static String normalize(CharSequence name)
		{
			int length = name.length();
			char[] array = new char[length];

			for (int i = 0; i < length; i++)
			{
				char c = name.charAt(i);

				switch (c)
				{
					case 'A': array[i] = 'a'; break;
					case 'B': array[i] = 'b'; break;
					case 'C': array[i] = 'c'; break;
					case 'D': array[i] = 'd'; break;
					case 'E': array[i] = 'e'; break;
					case 'F': array[i] = 'f'; break;
					case 'G': array[i] = 'g'; break;
					case 'H': array[i] = 'h'; break;
					case 'I': array[i] = 'i'; break;
					case 'J': array[i] = 'j'; break;
					case 'K': array[i] = 'k'; break;
					case 'L': array[i] = 'l'; break;
					case 'M': array[i] = 'm'; break;
					case 'N': array[i] = 'n'; break;
					case 'O': array[i] = 'o'; break;
					case 'P': array[i] = 'p'; break;
					case 'Q': array[i] = 'q'; break;
					case 'R': array[i] = 'r'; break;
					case 'S': array[i] = 's'; break;
					case 'T': array[i] = 't'; break;
					case 'U': array[i] = 'u'; break;
					case 'V': array[i] = 'v'; break;
					case 'W': array[i] = 'w'; break;
					case 'X': array[i] = 'x'; break;
					case 'Y': array[i] = 'y'; break;
					case 'Z': array[i] = 'z'; break;
					default: array[i] = c;
				}
			}

			return new String(array);
		}
	}

}
