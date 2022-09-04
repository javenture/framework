package net.javenture.framework.utf8;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.util.ByteArrayUtil;
import net.javenture.framework.util.ByteInputStream;
import net.javenture.framework.util.IByteSequence;


/*
	2020/10/09
 */
final public class Utf8InputStream
	extends ByteInputStream
{
	//
	final private IByteSequence SEQUENCE;


	//
	public Utf8InputStream(@NullDisallow byte[] array)
	{
		super(array);

		SEQUENCE = super::read;
	}


	public Utf8InputStream(@NullDisallow Utf8OutputStream stream) // !
	{
		super(stream);

		SEQUENCE = super::read;
	}


	//
	@Override
	public String toString()
	{
		int available = available(); // available() = bytes.length -> max ASCII char length
		char[] array = new char[available];
		int count = 0;

		while (true)
		{
			int c = readCharacter();

			if (c != -1) array[count++] = (char) c;
			else break;
		}

		return new String(array, 0, count);
	}


	public char[] toCharArray()
	{
		int available = available(); // available() = bytes.length -> max ASCII char length
		char[] array = new char[available];
		int count = 0;

		while (true)
		{
			int c = readCharacter();

			if (c != -1) array[count++] = (char) c;
			else break;
		}

		//
		if (count != available)
		{
			char[] result = new char[count];
			System.arraycopy(array, 0, result, 0, count);

			return result;
		}
		else
		{
			return array;
		}
	}


	/**
	 *	@return char | -1 (end of stream)
	 */
	public int readCharacter()
	{
		return Utf8.parse(SEQUENCE);
	}


	//
	final public static Utf8InputStream NULL = null;

	@SuppressWarnings({"resource", "IOResourceOpenedButNotSafelyClosed"})
	final public static Utf8InputStream BLANK = new Utf8InputStream(ByteArrayUtil.BLANK);

}
