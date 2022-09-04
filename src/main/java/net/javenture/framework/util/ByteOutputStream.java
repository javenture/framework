package net.javenture.framework.util;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.annotation.SingleThread;
import net.javenture.framework.factory.IMurmur3HashFactory;
import net.javenture.framework.hash.Murmur3Entry;
import net.javenture.framework.hash.Murmur3Hash;
import net.javenture.framework.hash.IMurmur3Hashable;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;


/*
	2020/10/16
 */
@SingleThread
public class ByteOutputStream
	extends OutputStream
	implements IMurmur3Hashable
{
	//
	final private static int PAGE = 4 * 1024;                                                      // XXX: 64b -> 256b -> 1K -> 4K -> 16K -> 64K ???
	final private static MemoryManager MANAGER_DEFAULT = new MemoryManager(PAGE);            // XXX: MemoryManager.getDefault() & MemoryManager.setDefault(...) !

	final public static IMurmur3HashFactory<ByteOutputStream> FACTORY_MURMUR3HASH = (object, destination) ->
	{
		if (IMurmur3HashFactory.updateNullHeader(object, destination))
		{
			for (WriteChunk chunk : object.CHUNKS) destination.update(chunk.ARRAY, 0, chunk.LIMIT);
		}
	};


	//
	final private @NullDisallow MemoryManager MANAGER;

	@Murmur3Entry
	final ArrayList<WriteChunk> CHUNKS; // ArrayList !

	private WriteChunk current;


	//
	public ByteOutputStream()
	{
		MANAGER = MANAGER_DEFAULT;
		CHUNKS = new ArrayList<>();
		CHUNKS.add(WriteChunk.BLANK); // !
		current = WriteChunk.BLANK;
	}


	public ByteOutputStream(int capacity)
	{
		byte[] array = new byte[capacity];
		WriteChunk chunk = new WriteChunk(array);

		//
		MANAGER = MANAGER_DEFAULT;
		CHUNKS = new ArrayList<>(1);
		CHUNKS.add(chunk);
		current = chunk;
	}


/*
	public ByteOutputStream(MemoryManager manager)                                                // XXX: ?
	{
		MANAGER = manager;
		CHUNKS = new ArrayList<>();
		CHUNKS.add(WriteChunk.BLANK);

		current = WriteChunk.BLANK;
	}
*/


	//
	@Override
	final public Murmur3Hash murmur3hash()
	{
		return FACTORY_MURMUR3HASH.hash(this);
	}


	@Override
	@SuppressWarnings("NumericCastThatLosesPrecision")
	final public void write(int b)
		throws IOException
	{
		write((byte) b);
	}


	final public void write(byte b)
		throws IOException
	{
		if (current.available() == 0) expand();

		current.write(b);
	}


	final public void write(byte b0, byte b1)
		throws IOException
	{
		if (current.available() < 2) expand();

		current.write(b0);
		current.write(b1);
	}


	final public void write(byte b0, byte b1, byte b2)
		throws IOException
	{
		if (current.available() < 3) expand();

		current.write(b0);
		current.write(b1);
		current.write(b2);
	}


	final public void write(byte b0, byte b1, byte b2, byte b3)
		throws IOException
	{
		if (current.available() < 4) expand();

		current.write(b0);
		current.write(b1);
		current.write(b2);
		current.write(b3);
	}


	final public void write(byte b0, byte b1, byte b2, byte b3, byte b4)
		throws IOException
	{
		if (current.available() < 5) expand();

		current.write(b0);
		current.write(b1);
		current.write(b2);
		current.write(b3);
		current.write(b4);
	}


	final public void write(byte b0, byte b1, byte b2, byte b3, byte b4, byte b5)
		throws IOException
	{
		if (current.available() < 6) expand();

		current.write(b0);
		current.write(b1);
		current.write(b2);
		current.write(b3);
		current.write(b4);
		current.write(b5);
	}


	final public void write(byte b0, byte b1, byte b2, byte b3, byte b4, byte b5, byte b6)
		throws IOException
	{
		if (current.available() < 7) expand();

		current.write(b0);
		current.write(b1);
		current.write(b2);
		current.write(b3);
		current.write(b4);
		current.write(b5);
		current.write(b6);
	}


	final public void write(byte b0, byte b1, byte b2, byte b3, byte b4, byte b5, byte b6, byte b7)
		throws IOException
	{
		if (current.available() < 8) expand();

		current.write(b0);
		current.write(b1);
		current.write(b2);
		current.write(b3);
		current.write(b4);
		current.write(b5);
		current.write(b6);
		current.write(b7);
	}


	final public void write(byte b0, byte b1, byte b2, byte b3, byte b4, byte b5, byte b6, byte b7, byte b8)
		throws IOException
	{
		if (current.available() < 9) expand();

		current.write(b0);
		current.write(b1);
		current.write(b2);
		current.write(b3);
		current.write(b4);
		current.write(b5);
		current.write(b6);
		current.write(b7);
		current.write(b8);
	}


	final public void write(byte b0, byte b1, byte b2, byte b3, byte b4, byte b5, byte b6, byte b7, byte b8, byte b9)
		throws IOException
	{
		if (current.available() < 10) expand();

		current.write(b0);
		current.write(b1);
		current.write(b2);
		current.write(b3);
		current.write(b4);
		current.write(b5);
		current.write(b6);
		current.write(b7);
		current.write(b8);
		current.write(b9);
	}


	@Override
	final public void write(@NullDisallow byte[] array, int offset, int length)
		throws IOException
	{
		while (length > 0)
		{
			int available = current.available();

			if (available >= length)
			{
				current.write(array, offset, length);

				break;
			}
			else if (available == 0)
			{
				expand();                                                             // XXX: expand(length);
			}
			else
			{
				current.write(array, offset, available);
				offset += available;
				length -= available;
				expand();                                                             // XXX: expand(length);
			}
		}
	}


	final public byte[] toBytes()
	{
		int capacity = 0;

		for (WriteChunk chunk : CHUNKS) capacity += chunk.position;

		if (capacity != 0)
		{
			byte[] result = new byte[capacity];
			int i = 0;

			for (WriteChunk chunk : CHUNKS)
			{
				System.arraycopy(chunk.ARRAY, 0, result, i, chunk.position);
				i += chunk.position;
			}

			return result;
		}
		else
		{
			return ByteArrayUtil.BLANK;
		}
	}


	public ByteInputStream toInputStream()
	{
		return new ByteInputStream(this);
	}


	private void expand()
		throws IOException
	{
		byte[] array = MANAGER.allocate();

		if (array != null)
		{
			WriteChunk chunk = new WriteChunk(array);
			CHUNKS.add(chunk);
			current = chunk;
		}
		else
		{
			throw new IOException("out of memory");
		}
	}

}
