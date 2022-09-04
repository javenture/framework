package net.javenture.framework.utf8;


import net.javenture.framework.util.IByteSequence;


/*
	2019/06/03
 */
final public class Utf8Coder
{
	//
	final private byte[] ARRAY;
	final private IByteSequence SEQUENCE;

	private int write;
	private int read;


	//
	public Utf8Coder(int capacity)
	{
		ARRAY = new byte[capacity];
		SEQUENCE = this::read0;

		init();
	}


	//
	public void init()
	{
		write = 0;
		read = 0;
	}


	public void write(byte value)
	{
		ARRAY[write++] = value;
	}


	/**
	 *	@return char | -1 (end of stream)
	 */
	public int read()
	{
		return Utf8.parse(SEQUENCE);
	}


	private int read0()
	{
		return read < write ? ARRAY[read++] : -1;
	}

}
