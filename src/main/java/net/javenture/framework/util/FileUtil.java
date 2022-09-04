package net.javenture.framework.util;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.log.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


/*
	2020/10/17
 */
final public class FileUtil
{
	//
	final private static Log LOG = Log.instance(FileUtil.class);


	//
	private FileUtil()
	{
	}


	//
	public static FileContent<byte[]> read(File file)
	{
		FileContent<byte[]> result;

		try
		{
			if (file.exists())
			{
				long length0 = file.length();

				if (length0 <= Integer.MAX_VALUE)
				{
					@SuppressWarnings("NumericCastThatLosesPrecision")
					int length = (int) length0;
					byte[] array = new byte[length];

					try (FileInputStream stream = new FileInputStream(file))
					{
						int count = StreamUtil.transfer(stream, array);

						result = count == length
							? new FileContent<>(FileContent.Status.OK, array)
							: new FileContent<>(FileContent.Status.ERROR_SOURCE);
					}
				}
				else
				{
					result = new FileContent<>(FileContent.Status.ERROR_LIMIT);
				}
			}
			else
			{
				result = new FileContent<>(FileContent.Status.NOT_FOUND);
			}
		}
		catch (Exception e)
		{
			result = new FileContent<>(FileContent.Status.ERROR, e);
		}

		return result;
	}


	public static FileContent<byte[]> read(File file, byte[] destination)
	{
		return read(file, destination, destination.length);
	}


	public static FileContent<byte[]> read(File file, byte[] destination, int limit)                                           // ???
	{
		FileContent<byte[]> result;

		try
		{
			if (file.exists())
			{
				long length0 = file.length();

				if (length0 <= Integer.MAX_VALUE)
				{
					@SuppressWarnings("NumericCastThatLosesPrecision")
					int length = (int) length0;
					int min = Math.min(length, limit);                                                                               // ???

					try (FileInputStream stream = new FileInputStream(file))
					{                                                                                                                // ???
						int count = StreamUtil.transfer(stream, destination, min);

						result = count == min                                                                                        // ???
							? new FileContent<>(FileContent.Status.OK, destination)
							: new FileContent<>(FileContent.Status.ERROR_SOURCE);
					}
				}
				else
				{
					result = new FileContent<>(FileContent.Status.ERROR_LIMIT);
				}
			}
			else
			{
				result = new FileContent<>(FileContent.Status.NOT_FOUND);
			}
		}
		catch (Exception e)
		{
			result = new FileContent<>(FileContent.Status.ERROR, e);
		}

		return result;
	}


	public static boolean write(@NullDisallow File file, @NullDisallow byte[] bytes)
	{
		return write(file, bytes, false);
	}


	public static boolean write(@NullDisallow File file, @NullDisallow byte[] bytes, boolean overwrite)                                  // XXX: ?
	{
		Validator.notNull(file, "[file]");
		Validator.notNull(bytes, "[bytes]");

		//
		boolean result = false;

		if (!file.exists() || overwrite)
		{
			try (FileOutputStream stream = new FileOutputStream(file))
			{
				stream.write(bytes);
				stream.flush();

				result = true;
			}
			catch (IOException e)
			{
				LOG.error(e);
			}
		}

		return result;
	}







/*
	public static boolean write(File file, InputStream stream)
	{

	}
*/

}
