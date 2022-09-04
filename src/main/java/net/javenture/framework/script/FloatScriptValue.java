package net.javenture.framework.script;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.utf8.Utf8Byte;
import net.javenture.framework.utf8.Utf8Number;
import net.javenture.framework.utf8.Utf8OutputStream;

import java.io.IOException;


/*
	2019/07/19
 */
final public class FloatScriptValue
	extends AScriptValue
{
	//
	final private @NullAllow Float VALUE;
	final private boolean QUOTATION;


	//
	public FloatScriptValue(float value)
	{
		VALUE = value;
		QUOTATION = false;
	}


	public FloatScriptValue(@NullAllow Float value)
	{
		VALUE = value;
		QUOTATION = false;
	}


	public FloatScriptValue(float value, boolean quotation)
	{
		VALUE = value;
		QUOTATION = quotation;
	}


	public FloatScriptValue(@NullAllow Float value, boolean quotation)
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
