package net.javenture.framework.util;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.factory.IByteFactory;
import net.javenture.framework.factory.ICopyFactory;
import net.javenture.framework.factory.IMurmur3HashFactory;
import net.javenture.framework.hash.Murmur3Hash;

import java.io.InputStream;
import java.io.OutputStream;


/*
	2021/05/28
 */
final public class CharUtil
{
	//
	final public static ICopyFactory<Character> FACTORY_COPY = object -> object;

	final public static IByteFactory<Character> FACTORY_BYTE = new IByteFactory<>()
	{
		@Override
		public void toStream(@NullAllow Character object, OutputStream destination)
			throws Exception
		{
			if (IByteFactory.setNullHeader(object, destination)) bytes(object, destination);
		}

		@Override
		public @NullAllow Character fromStream(InputStream source)
			throws Exception
		{
			return IByteFactory.getNullHeader(source) ? parse(source) : null;
		}
	};

	final public static IMurmur3HashFactory<Character> FACTORY_MURMUR3HASH = (object, destination) ->
	{
		if (IMurmur3HashFactory.updateNullHeader(object, destination)) murmur3hash(object, destination);
	};

	final public static Factories<Character> FACTORIES = new Factories<>(FACTORY_COPY, FACTORY_BYTE, FACTORY_MURMUR3HASH);


	//
	private CharUtil()
	{
	}


	/*
	 * char -> byte[]
	 */
	public static byte[] bytes(char value)
	{
		return new byte[]
		{
			(byte) (value >> 8),
			(byte) (value)
		};
	}


	public static void bytes(char value, byte[] destination, int index)
	{
		destination[index]     = (byte) (value >> 8);
		destination[index + 1] = (byte) (value);
	}


	public static void bytes(char value, OutputStream destination)
		throws Exception
	{
		destination.write(value >> 8);
		destination.write(value);
	}


	/*
	 * byte[] -> char
	 */
	public static char parse(byte b1, byte b2)
	{
		return
			(char)
			(
				(b1 & 0xff) << 8
				|
				(b2 & 0xff)
			);
	}


	public static char parse(byte[] source, int index)
	{
		byte b0 = source[index];
		byte b1 = source[index + 1];

		return parse(b0, b1);
	}


	public static char parse(InputStream source)
		throws Exception
	{
		int i0 = source.read();
		int i1 = source.read();

		if (i0 != -1 && i1 != -1) return parse((byte) i0, (byte) i1);
		else throw new Exception("insufficient data");
	}


	public static char parse(InputStream source, char init)
	{
		try
		{
			int i0 = source.read();
			int i1 = source.read();

			if (i0 != -1 && i1 != -1) return parse((byte) i0, (byte) i1);
		}
		catch (Exception ignore)
		{
		}

		return init;
	}


	public static @NullAllow Character parse(InputStream source, @NullAllow Character init)
	{
		try
		{
			int i0 = source.read();
			int i1 = source.read();

			if (i0 != -1 && i1 != -1) return parse((byte) i0, (byte) i1);
		}
		catch (Exception ignore)
		{
		}

		return init;
	}


	/*
	 * char -> hashcode
	 */
	public static int hashcode(char value)
	{
		return Character.hashCode(value);
	}


	public static int hashcode(@NullAllow Character value)
	{
		return value != null ? Character.hashCode(value) : 0;
	}


	/*
	 * char -> murmur3hash
	 */
	public static Murmur3Hash murmur3hash(char value)
	{
		Murmur3Hash result = new Murmur3Hash();
		murmur3hash(value, result);

		return result;
	}


	public static void murmur3hash(char value, Murmur3Hash destination)
	{
		destination.update
		(
			(byte) (value >> 8),
			(byte) (value)
		);
	}


	/*
	 * equal
	 */
	@SuppressWarnings("ObjectEquality")
	public static boolean equal(@NullAllow Character c0, @NullAllow Character c1)
	{
		return
			c0 == c1
			||
			c0 != null
			&&
			c1 != null
			&&
			c0.charValue() == c1.charValue();
	}


	/*
	 * char -> digit (0 ... 9) | -1
	 */
	static int digit(char c)
	{
		switch (c)
		{
			case '0': return 0;
			case '1': return 1;
			case '2': return 2;
			case '3': return 3;
			case '4': return 4;
			case '5': return 5;
			case '6': return 6;
			case '7': return 7;
			case '8': return 8;
			case '9': return 9;
		}

		return -1;
	}

}
