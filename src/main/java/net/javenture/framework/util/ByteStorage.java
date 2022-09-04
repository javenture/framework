package net.javenture.framework.util;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.annotation.SingleThread;

import java.util.Arrays;


/*
	2018/12/20
 */
@SingleThread
final public class ByteStorage
{
	//
	final private Chain<Entry> ENTRIES;

	private int size;


	//
	public ByteStorage()
	{
		ENTRIES = new Chain<>();
		size = 0;
	}


	private ByteStorage(boolean wrap, byte[]... arrays)
	{
		ENTRIES = new Chain<>();

		if (wrap)
		{
			for (byte[] array : arrays) wrap(array);
		}
		else
		{
			for (byte[] array : arrays) add(array);
		}
	}


	//
	public void add(ByteStorage storage)
	{
		ENTRIES.add(storage.ENTRIES);
		size += storage.size;
	}


	public void add(byte value)
	{
		ENTRIES.add
		(
			(destination, offset) ->
			{
				destination[offset] = value;

				return 1;
			}
		);

		size++;
	}


	public void add(@NullDisallow byte[] value)
	{
		byte[] array = value.clone();
		entry(array, 0, array.length);
	}


	public void add(@NullDisallow byte[] value, int from, int to)
	{
		byte[] array = Arrays.copyOfRange(value, from, to);
		entry(array, 0, array.length);
	}


	public void wrap(@NullDisallow byte[] value)
	{
		Validator.notNull(value, "[value]");
		entry(value, 0, value.length);
	}


	public void wrap(@NullDisallow byte[] value, int from, int to)
	{
		Validator.notNull(value, "[value]");
		entry(value, from, to);
	}


	public byte[] array()
	{
		if (size != 0)
		{
			byte[] result = new byte[size];
			int position = 0;

			for (Entry entry : ENTRIES) position += entry.transfer(result, position);

			return result;
		}
		else
		{
			return ByteArrayUtil.BLANK;
		}
	}


	void entry(@NullDisallow byte[] value, int from, int to)
	{
		int length = to - from;

		if (length != 0)
		{
			ENTRIES.add
			(
				(destination, offset) ->
				{
					System.arraycopy(value, from, destination, offset, length);

					return length;
				}
			);

			size += length;
		}
	}


	//
	public static ByteStorage wrap(byte[]... arrays)
	{
		return new ByteStorage(true, arrays);
	}


	//
	@FunctionalInterface
	private interface Entry
	{
		int transfer(byte[] destination, int offset);
	}

}
