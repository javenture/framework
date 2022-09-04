package net.javenture.framework.script;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.utf8.Utf8Byte;
import net.javenture.framework.utf8.Utf8Number;
import net.javenture.framework.utf8.Utf8OutputStream;

import java.io.IOException;


/*
	2019/07/19
 */
final public class ShortScriptValue
	extends AScriptValue
{
	//
	final private @NullAllow Short VALUE;
	final private boolean QUOTATION;


	//
	public ShortScriptValue(short value)
	{
		VALUE = value;
		QUOTATION = false;
	}


	public ShortScriptValue(@NullAllow Short value)
	{
		VALUE = value;
		QUOTATION = false;
	}


	public ShortScriptValue(short value, boolean quotation)
	{
		VALUE = value;
		QUOTATION = quotation;
	}


	public ShortScriptValue(@NullAllow Short value, boolean quotation)
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
			Utf8Number.toStream(VALUE, destination);
			destination.write(Utf8Byte.APOSTROPHE);
		}
		else
		{
			Utf8Number.toStream(VALUE, destination);
		}
	}


	@Override
	public void toScriptStream(Utf8OutputStream destination)
		throws IOException
	{
		if (QUOTATION)
		{
			destination.write(Utf8Byte.APOSTROPHE);
			Utf8Number.toStream(VALUE, destination);
			destination.write(Utf8Byte.APOSTROPHE);
		}
		else
		{
			Utf8Number.toStream(VALUE, destination);
		}
	}


	@Override
	public void toJsonStream(Utf8OutputStream destination)
		throws IOException
	{
		if (QUOTATION)
		{
			destination.write(Utf8Byte.QUOTATION);
			Utf8Number.toStream(VALUE, destination);
			destination.write(Utf8Byte.QUOTATION);
		}
		else
		{
			Utf8Number.toStream(VALUE, destination);
		}
	}

}
