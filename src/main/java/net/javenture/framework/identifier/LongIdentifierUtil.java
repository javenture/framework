package net.javenture.framework.identifier;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.util.LongUtil;

import java.io.InputStream;
import java.io.OutputStream;


/*
	2021/05/28
 */
final public class LongIdentifierUtil
{
	//
	private LongIdentifierUtil()
	{
	}


	/*
	 * LongIdentifier -> byte[]
	 */
	public static byte[] bytes(@NullDisallow LongIdentifier value)
	{
		return LongUtil.bytes(value.VALUE);
	}


	public static void bytes(@NullDisallow LongIdentifier value, byte[] destination, int index)
	{
		LongUtil.bytes(value.VALUE, destination, index);
	}


	public static void bytes(@NullDisallow LongIdentifier value, OutputStream destination)
		throws Exception
	{
		LongUtil.bytes(value.VALUE, destination);
	}


	/*
	 * byte[] -> LongIdentifier
	 */
	public static @NullDisallow LongIdentifier parse(byte[] source, int index)
		throws Exception
	{
		return new LongIdentifier(LongUtil.parse(source, index));
	}


	public static @NullAllow LongIdentifier parse(byte[] source, int index, @NullAllow LongIdentifier init)
	{
		Long value = LongUtil.parse(source, index, null);

		return value != null
			? new LongIdentifier(value)
			: init;
	}


	public static LongIdentifier parse(InputStream source)
		throws Exception
	{
		return new LongIdentifier(LongUtil.parse(source));
	}


	public static @NullAllow LongIdentifier parse(InputStream source, @NullAllow LongIdentifier init)
	{
		Long value = LongUtil.parse(source, null);

		return value != null
			? new LongIdentifier(value)
			: init;
	}

}
