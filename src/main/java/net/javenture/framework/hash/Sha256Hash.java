package net.javenture.framework.hash;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.hex.Hex;
import net.javenture.framework.util.BooleanUtil;
import net.javenture.framework.util.DoubleUtil;
import net.javenture.framework.util.FloatUtil;
import net.javenture.framework.util.IntUtil;
import net.javenture.framework.util.LongUtil;
import net.javenture.framework.util.ShortUtil;
import net.javenture.framework.util.StringUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/*
	2021/06/02
 */
final public class Sha256Hash
	implements IHash
{
	/*
		"1" = '1' = [ '1' ]
	 */

	//
	final private static String TYPE = "SHA-256";


	//
	final private MessageDigest DIGEST;


	//
	public Sha256Hash()
	{
		try
		{
			DIGEST = MessageDigest.getInstance(TYPE);
		}
		catch (NoSuchAlgorithmException e)
		{
			throw new RuntimeException();
		}
	}


	//
	@Override
	public void update(byte b)
	{
		DIGEST.update(b);
	}


	@Override
	public void update(@NullDisallow byte... array)
	{
		DIGEST.update(array);
	}


	@Override
	public void update(@NullDisallow byte[] array, int offset, int length)
	{
		DIGEST.update(array, offset, length);
	}


	public void update(boolean value)
	{
		DIGEST.update(BooleanUtil.bytes(value));
	}


	public void update(char value)
	{
		DIGEST.update(StringUtil.bytes(value));
	}


	public void update(short value)
	{
		DIGEST.update(ShortUtil.bytes(value));
	}


	public void update(int value)
	{
		DIGEST.update(IntUtil.bytes(value));
	}


	public void update(long value)
	{
		DIGEST.update(LongUtil.bytes(value));
	}


	public void update(float value)
	{
		DIGEST.update(FloatUtil.bytes(value));
	}


	public void update(double value)
	{
		DIGEST.update(DoubleUtil.bytes(value));
	}


	public void update(char[] value)
	{
		DIGEST.update(StringUtil.bytes(value));
	}


	public void update(@NullDisallow CharSequence value)
	{
		DIGEST.update(StringUtil.bytes(value));
	}


	@Override
	public String value()
	{
		return new Hex(DIGEST.digest())
			.toString(true);
	}

}
