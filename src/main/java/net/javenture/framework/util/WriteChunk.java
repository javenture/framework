package net.javenture.framework.util;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.annotation.SingleThread;


/*
	2019/12/16
 */
@SingleThread
final class WriteChunk
{
	//
	final byte[] ARRAY;
	final int LIMIT;

	int position;


	//
	@SuppressWarnings("AssignmentOrReturnOfFieldWithMutableType")
	WriteChunk(@NullDisallow byte[] array)
	{
		Validator.notNull(array, "[array]");

		ARRAY = array;
		LIMIT = array.length;
		position = 0;
	}


	//
	int available()
	{
		return LIMIT - position;
	}


	void write(byte b)
	{
		ARRAY[position++] = b;
	}


	void write(byte[] source, int offset, int length)
	{
		System.arraycopy(source, offset, ARRAY, position, length);
		position += length;
	}


	//
	final static WriteChunk BLANK = new WriteChunk(ByteArrayUtil.BLANK);

}
