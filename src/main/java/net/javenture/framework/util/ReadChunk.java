package net.javenture.framework.util;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.annotation.SingleThread;


/*
	2017/12/12
 */
@SingleThread
final class ReadChunk
{
	//
	final byte[] ARRAY;
	final int LIMIT;

	int position;


	//
	ReadChunk(@NullDisallow byte[] array)
	{
		this(array, array.length);
	}


	@SuppressWarnings("AssignmentOrReturnOfFieldWithMutableType")
	ReadChunk(@NullDisallow byte[] array, int limit)
	{
		Validator.notNull(array, "[array]");
		Validator.argument(limit >= 0, "[limit] (", limit, ") < 0");
		Validator.argument(limit <= array.length, "[limit] (", limit, ") > [array.length] (", array.length, ")");

		//
		ARRAY = array;
		LIMIT = limit;
		position = 0;
	}


	//
	int available()
	{
		return LIMIT - position;
	}


	byte read()
	{
		return ARRAY[position++];
	}


	void read(byte[] destination, int offset, int length)
	{
		System.arraycopy(ARRAY, position, destination, offset, length);
		position += length;
	}


	//
	final static ReadChunk BLANK = new ReadChunk(ByteArrayUtil.BLANK);

}
