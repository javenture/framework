package net.javenture.framework.identifier;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.util.IntUtil;
import net.javenture.framework.util.Primitives;

import java.io.InputStream;
import java.io.OutputStream;


/*
	2021/05/28
 */
final public class IntIdentifierUtil
{
	//
	private IntIdentifierUtil()
	{
	}


	/*
	 * IntIdentifier -> byte[]
	 */
	public static byte[] bytes(@NullDisallow IntIdentifier value)
	{
		return IntUtil.bytes(value.VALUE);
	}


	public static void bytes(@NullDisallow IntIdentifier value, byte[] destination, int index)
	{
		IntUtil.bytes(value.VALUE, destination, index);
	}


	public static void bytes(@NullDisallow IntIdentifier value, OutputStream destination)
		throws Exception
	{
		IntUtil.bytes(value.VALUE, destination);
	}


	/*
	 * byte[] -> IntIdentifier
	 */
	public static @NullDisallow IntIdentifier parse(byte[] source, int index)
		throws Exception
	{
		return new IntIdentifier(IntUtil.parse(source, index));
	}


	public static @NullAllow IntIdentifier parse(byte[] source, int index, @NullAllow IntIdentifier init)
	{
		Integer value = IntUtil.parse(source, index, null);

		return value != null ? new IntIdentifier(value) : init;
	}


	public static IntIdentifier parse(InputStream source)
		throws Exception
	{
		return new IntIdentifier(IntUtil.parse(source));
	}


	public static @NullAllow IntIdentifier parse(InputStream source, @NullAllow IntIdentifier init)
	{
		Integer value = IntUtil.parse(source, null);

		return value != null ? new IntIdentifier(value) : init;
	}

}
