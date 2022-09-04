package net.javenture.framework.util;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.annotation.SingleThread;
import net.javenture.framework.factory.IMurmur3HashFactory;
import net.javenture.framework.hash.Murmur3Entry;
import net.javenture.framework.hash.Murmur3Hash;
import net.javenture.framework.hash.IMurmur3Hashable;

import java.io.InputStream;
import java.util.ArrayList;


/*
	2020/10/08
 */
@SingleThread
public class ByteInputStream
	extends InputStream
	implements IMurmur3Hashable
{
	//
	final public static IMurmur3HashFactory<ByteInputStream> FACTORY_MURMUR3HASH = (object, destination) ->
	{
		if (IMurmur3HashFactory.updateNullHeader(object, destination))
		{
			for (ReadChunk chunk : object.CHUNKS) destination.update(chunk.ARRAY, 0, chunk.LIMIT);
		}
	};


	//
	@Murmur3Entry
	final ArrayList<ReadChunk> CHUNKS; // ArrayList !

	private int available;
	private int index;
	private ReadChunk current;


	//
	public ByteInputStream(@NullDisallow byte[] array) // !
	{
		Validator.notNull(array, "[array]");
		ReadChunk chunk = new ReadChunk(array);

		//
		CHUNKS = new ArrayList<>(1);
		CHUNKS.add(chunk);

		available = array.length;
		index = 0;
		current = chunk;
	}


	public ByteInputStream(@NullDisallow ByteOutputStream stream) // !
	{
		Validator.notNull(stream, "[stream]");
		ArrayList<WriteChunk> chunks = stream.CHUNKS;

		//
		CHUNKS = new ArrayList<>(chunks.size());
		available = 0;

		for (WriteChunk chunk : chunks)
		{
			byte[] array = chunk.ARRAY;
			int limit = chunk.position;
			CHUNKS.add(new ReadChunk(array, limit));
			available += limit;
		}

		index = 0;
		current = CHUNKS.get(0); // always exist
	}


	//
	@Override
	public int available()
	{
		return available;
	}


	@Override
	public int read()
	{
		if (current.available() != 0)
		{
			available--;

			return current.read() & 0xff;
		}
		else if (available != 0)
		{
			while (next())
			{
				if (current.available() != 0)
				{
					available--;

					return current.read() & 0xff;
				}
			}

			throw new RuntimeException(); // ! unreachable statement
		}
		else
		{
			return -1; // end of stream
		}
	}


	@Override
	public int read(@NullDisallow byte[] destination, int offset, int length)
	{
		Validator.notNull(destination, "[destination]");
		Validator.argument(offset >= 0, "[offset] (", offset, "] < 0");
		Validator.argument(length >= 0, "[length] (", length, "] < 0");
		Validator.argument(destination.length >= offset + length, "[destination.length] (", destination.length, ") < [offset] (", offset, ") + [length] (", length, ")");

		if (available != 0)
		{
			int result = 0;

			while (length > 0)
			{
				int a = current.available();

				if (a >= length)
				{
					current.read(destination, offset, length);
					available -= length;
					result += length;

					break;
				}
				else
				{
					current.read(destination, offset, a);
					available -= a;
					offset += a;
					length -= a;
					result += a;

					if (!next()) break;
				}
			}

			return result;
		}
		else
		{
			return -1; // end of stream
		}
	}


	@Override
	public Murmur3Hash murmur3hash()
	{
		return FACTORY_MURMUR3HASH.hash(this);
	}


	private boolean next()
	{
		if (index < CHUNKS.size() - 1)
		{
			current = CHUNKS.get(++index);

			return true;
		}
		else
		{
			current = null;

			return false;
		}
	}


	//
	final public static ByteInputStream NULL = null;

	@SuppressWarnings({"resource", "IOResourceOpenedButNotSafelyClosed"})
	final public static ByteInputStream BLANK = new ByteInputStream(ByteArrayUtil.BLANK);

}
