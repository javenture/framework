package net.javenture.framework.script;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.utf8.Utf8Byte;
import net.javenture.framework.utf8.Utf8OutputStream;
import net.javenture.framework.util.BooleanUtil;
import net.javenture.framework.util.NullUtil;

import java.io.IOException;


/*
	2019/07/19
 */
final public class BooleanScriptValue
	extends AScriptValue
{
	//
	final private @NullAllow Boolean VALUE;
	final private boolean QUOTATION;


	//
	public BooleanScriptValue(boolean value)
	{
		VALUE = value;
		QUOTATION = false;
	}


	public BooleanScriptValue(@NullAllow Boolean value)
	{
		VALUE = value;
		QUOTATION = false;
	}


	public BooleanScriptValue(boolean value, boolean quotation)
	{
		VALUE = value;
		QUOTATION = quotation;
	}


	public BooleanScriptValue(@NullAllow Boolean value, boolean quotation)
	{
		VALUE = value;
		QUOTATION = quotation && value != null;
	}


	//
	@Override
	public void toUtf8Stream(Utf8OutputStream destination)
		throws IOException
	{
		if (QUOTATION)
		{
			destination.write(Utf8Byte.APOSTROPHE);
			toStream(destination, VALUE);
			destination.write(Utf8Byte.APOSTROPHE);
		}
		else
		{
			toStream(destination, VALUE);
		}
	}


	@Override
	public void toScriptStream(Utf8OutputStream destination)
		throws IOException
	{
		if (QUOTATION)
		{
			destination.write(Utf8Byte.APOSTROPHE);
			toStream(destination, VALUE);
			destination.write(Utf8Byte.APOSTROPHE);
		}
		else
		{
			toStream(destination, VALUE);
		}
	}


	@Override
	public void toJsonStream(Utf8OutputStream destination)
		throws IOException
	{
		if (QUOTATION)
		{
			destination.write(Utf8Byte.QUOTATION);
			toStream(destination, VALUE);
			destination.write(Utf8Byte.QUOTATION);
		}
		else
		{
			toStream(destination, VALUE);
		}
	}


	//
	public static BooleanScriptValue instance(boolean value)
	{
		return value ? TRUE : FALSE;
	}


	public static BooleanScriptValue instance(boolean value, boolean quotation)
	{
		if (quotation) return value ? TRUE_QUOTATION : FALSE_QUOTATION;
		else return value ? TRUE : FALSE;
	}


	public static BooleanScriptValue instance(@NullAllow Boolean value)
	{
		if (value != null) return value ? TRUE : FALSE;
		else return NULL;
	}


	public static BooleanScriptValue instance(@NullAllow Boolean value, boolean quotation)
	{
		if (value != null)
		{
			if (quotation) return value ? TRUE_QUOTATION : FALSE_QUOTATION;
			else return value ? TRUE : FALSE;
		}
		else
		{
			return NULL;
		}
	}


	private static void toStream(Utf8OutputStream destination, @NullAllow Boolean value)
		throws IOException
	{
		destination.write
		(
			value != null
				? (value ? BooleanUtil.True.ENTRY : BooleanUtil.False.ENTRY)
				: NullUtil.ENTRY
		);
	}


	//
	final public static BooleanScriptValue NULL = new BooleanScriptValue(null);

	final public static BooleanScriptValue TRUE = new BooleanScriptValue(true);
	final public static BooleanScriptValue FALSE = new BooleanScriptValue(false);

	final public static BooleanScriptValue TRUE_QUOTATION = new BooleanScriptValue(true, true);
	final public static BooleanScriptValue FALSE_QUOTATION = new BooleanScriptValue(false, true);

}
