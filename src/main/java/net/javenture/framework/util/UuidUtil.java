package net.javenture.framework.util;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.FactoryObjectUtil;
import net.javenture.framework.hash.Murmur3Hash;

import java.io.InputStream;
import java.io.OutputStream;


/*
	2021/05/29
 */
final public class UuidUtil
{
	//
	private UuidUtil()
	{
	}


	/*
	 * Uuid -> byte[]
	 */
	public static byte[] bytes(@NullDisallow Uuid value) // ! @NullDisallow
	{
		byte[] result = new byte[16];
		LongUtil.bytes(value.getMostSignificantBits(), result, 0);
		LongUtil.bytes(value.getLeastSignificantBits(), result, 8);

		return result;
	}


	public static void bytes(@NullDisallow Uuid value, byte[] destination, int index) // ! @NullDisallow Uuid
	{
		LongUtil.bytes(value.getMostSignificantBits(), destination, index);
		LongUtil.bytes(value.getLeastSignificantBits(), destination, index + 8);
	}


	public static void bytes(@NullDisallow Uuid value, OutputStream destination) // ! @NullDisallow
		throws Exception
	{
		LongUtil.bytes(value.getMostSignificantBits(), destination);
		LongUtil.bytes(value.getLeastSignificantBits(), destination);
	}


	/*
	 * byte[] -> Uuid
	 */
	public static @NullDisallow Uuid parse(byte[] source, int index)
		throws Exception
	{
		long l0 = LongUtil.parse(source, index);
		long l1 = LongUtil.parse(source, index + 8);

		return new Uuid(l0, l1);
	}


	public static @NullAllow Uuid parse(byte[] source, int index, @NullAllow Uuid init)
	{
		Long l0 = LongUtil.parse(source, index, null);
		Long l1 = LongUtil.parse(source, index + 8, null);

		return l0 != null && l1 != null
			? new Uuid(l0, l1)
			: init;
	}


	public static @NullDisallow Uuid parse(InputStream source)
		throws Exception
	{
		long l0 = LongUtil.parse(source);
		long l1 = LongUtil.parse(source);

		return new Uuid(l0, l1);
	}


	public static @NullAllow Uuid parse(InputStream source, @NullAllow Uuid init)
	{
		Long l0 = LongUtil.parse(source, null);
		Long l1 = LongUtil.parse(source, null);

		return l0 != null && l1 != null
			? new Uuid(l0, l1)
			: init;
	}


	/*
	 * Uuid -> murmur3hash
	 */
	public static Murmur3Hash murmur3hash(@NullDisallow Uuid value) // ! @NullDisallow
	{
		Murmur3Hash result = new Murmur3Hash();
		murmur3hash(value, result);

		return result;
	}


	public static void murmur3hash(@NullDisallow Uuid value, Murmur3Hash destination) // ! @NullDisallow
	{
		LongUtil.murmur3hash(value.getMostSignificantBits(), destination);
		LongUtil.murmur3hash(value.getLeastSignificantBits(), destination);
	}


	/*
	 * equal
	 */
	public static boolean equal(@NullAllow Uuid value0, @NullAllow Uuid value1)
	{
		return FactoryObjectUtil.equal(value0, value1);
	}

}
