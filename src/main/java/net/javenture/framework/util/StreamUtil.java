package net.javenture.framework.util;


import net.javenture.framework.annotation.NullDisallow;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/*
	2020/05/18
 */
final public class StreamUtil
{
	//
	final private static int BUFFER_LENGTH = 64 * 1024;


	//
	private StreamUtil()
	{
	}


	//
	public static int transfer(@NullDisallow InputStream source, @NullDisallow byte[] destination)
		throws IOException
	{
		return transfer(source, destination, destination.length);
	}


	public static int transfer(@NullDisallow InputStream source, @NullDisallow byte[] destination, int limit)
		throws IOException
	{
		int result = 0;

		while (result < limit)
		{
			int read = source.read(destination, result, limit - result);

			if (read == -1) break;
			else result += read;
		}

		return result;
	}


	public static int transfer(@NullDisallow InputStream source, @NullDisallow OutputStream destination)
		throws IOException
	{
		return transfer(source, destination, new byte[BUFFER_LENGTH]);
	}


	public static int transfer(@NullDisallow InputStream source, @NullDisallow OutputStream destination, @NullDisallow byte[] buffer)
		throws IOException
	{
		Validator.notNull(source, "[source]");
		Validator.notNull(destination, "[destination]");
		Validator.notNull(buffer, "[buffer]");

		//
		int result = 0;

		while (true)
		{
			int read = source.read(buffer);

			if (read == -1)
			{
				break;
			}
			else
			{
				destination.write(buffer, 0, read);
				result += read;
			}
		}

		return result;
	}

}
