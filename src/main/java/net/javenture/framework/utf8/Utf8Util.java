package net.javenture.framework.utf8;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.log.Log;
import net.javenture.framework.util.ByteArrayUtil;
import net.javenture.framework.util.CharArrayUtil;
import net.javenture.framework.util.CharContainer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;


/*
	2020/10/09
 */
final public class Utf8Util
{
	//
	final private static Log LOG = Log.instance(Utf8Util.class);


	//
	private Utf8Util()
	{
	}


	//
	public static byte[] bytes(char c)
	{
		return Utf8.entry(c).bytes();
	}


	public static byte[] bytes(@NullDisallow char[] array)
	{
		return bytes(array, 0, array.length);
	}


	public static byte[] bytes(@NullDisallow char[] array, int from, int to)
	{
		int length = to - from;

		if (length > 1)
		{
			byte[] result = new byte[length * Utf8.DIMENSION];
			int position = 0;

			for (int i = from; i < to; i++)
			{
				position += Utf8.entry(array[i])
					.toArray(result, position);
			}

			return position == result.length
				? result
				: Arrays.copyOf(result, position);
		}
		else if (length == 1)
		{
			return bytes(array[from]);
		}
		else
		{
			return ByteArrayUtil.BLANK;
		}
	}


	public static byte[] bytes(@NullDisallow CharSequence sequence) // ! @NullDisallow
	{
		return bytes(sequence, 0, sequence.length());
	}


	public static byte[] bytes(@NullDisallow CharSequence sequence, int from, int to) // ! @NullDisallow
	{
		int length = to - from;

		if (length > 1)
		{
			byte[] result = new byte[length * Utf8.DIMENSION];
			int position = 0;

			for (int i = from; i < to; i++)
			{
				position += Utf8.entry(sequence.charAt(i))
					.toArray(result, position);
			}

			return position == result.length
				? result
				: Arrays.copyOf(result, position);
		}
		else if (length == 1)
		{
			return bytes(sequence.charAt(from));
		}
		else
		{
			return ByteArrayUtil.BLANK;
		}
	}


	public static byte[] bytes(@NullDisallow IUtf8StreamableEntry entry)
	{
		try (Utf8OutputStream stream = new Utf8OutputStream())
		{
			stream.write(entry);

			return stream.toBytes();
		}
		catch (IOException e)
		{
			LOG.error(e);

			return ByteArrayUtil.BLANK;
		}
	}


	public static byte[] bytes(IUtf8StreamableEntry... entries)
	{
		try (Utf8OutputStream stream = new Utf8OutputStream())
		{
			stream.write(entries);

			return stream.toBytes();
		}
		catch (IOException e)
		{
			LOG.error(e);

			return ByteArrayUtil.BLANK;
		}
	}


	public static Utf8InputStream stream(@NullDisallow char[] array)
	{
		return stream(array, 0, array.length);
	}


	public static Utf8InputStream stream(@NullDisallow char[] array, int from, int to)
	{
		try (Utf8OutputStream stream = new Utf8OutputStream())
		{
			stream.write(array, from, to);

			return stream.toInputStream();
		}
		catch (IOException e)
		{
			LOG.error(e);

			return Utf8InputStream.BLANK;
		}

	}


	public static Utf8InputStream stream(@NullDisallow CharSequence sequence) // ! @NullDisallow
	{
		return stream(sequence, 0, sequence.length());
	}


	public static Utf8InputStream stream(@NullDisallow CharSequence sequence, int from, int to) // ! @NullDisallow
	{
		try (Utf8OutputStream stream = new Utf8OutputStream())
		{
			stream.write(sequence, from, to);

			return stream.toInputStream();
		}
		catch (IOException e)
		{
			LOG.error(e);

			return Utf8InputStream.BLANK;
		}
	}


	public static Utf8InputStream stream(@NullDisallow IUtf8StreamableEntry entry)
	{
		try (Utf8OutputStream stream = new Utf8OutputStream())
		{
			stream.write(entry);

			return stream.toInputStream();
		}
		catch (IOException e)
		{
			LOG.error(e);

			return Utf8InputStream.BLANK;
		}
	}


	public static Utf8InputStream stream(IUtf8StreamableEntry... entries)
	{
		try (Utf8OutputStream stream = new Utf8OutputStream())
		{
			stream.write(entries);

			return stream.toInputStream();
		}
		catch (IOException e)
		{
			LOG.error(e);

			return Utf8InputStream.BLANK;
		}
	}


	public static String toString(@NullDisallow IUtf8StreamableEntry entry)
	{
		try (Utf8OutputStream output = new Utf8OutputStream())
		{
			output.write(entry);

			try (Utf8InputStream input = new Utf8InputStream(output))
			{
				return input.toString();
			}
		}
		catch (IOException e)
		{
			LOG.error(e);

			return "";
		}
	}


	public static String toString(IUtf8StreamableEntry... entries)
	{
		try (Utf8OutputStream output = new Utf8OutputStream())
		{
			output.write(entries);

			try (Utf8InputStream input = new Utf8InputStream(output))
			{
				return input.toString();
			}
		}
		catch (IOException e)
		{
			LOG.error(e);

			return "";
		}
	}


	public static @NullDisallow char[] parse(@NullDisallow byte[] array)
	{
		try (Utf8InputStream stream = new Utf8InputStream(array))
		{
			return stream.toCharArray();
		}
		catch (Exception ignore)
		{
			return CharArrayUtil.BLANK; // ! unreachable
		}
	}


	public static @NullDisallow char[] parse(@NullDisallow byte[] array, int from, int to)
	{
		int length = to - from;
		byte[] array2 = new byte[length];
		System.arraycopy(array, from, array2, 0, length);

		return parse(array2);
	}


	public static char[] parse(@NullDisallow InputStream stream)
		throws IOException
	{
		CharContainer container = new CharContainer(stream.available());

		while (true)
		{
			int c = Utf8.parse(stream);

			if (c == -1) break;
			else container.add((char) c);
		}

		return container.array();
	}

}
