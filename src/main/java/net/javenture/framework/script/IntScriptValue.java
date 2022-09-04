package net.javenture.framework.script;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.utf8.Utf8Byte;
import net.javenture.framework.utf8.Utf8Number;
import net.javenture.framework.utf8.Utf8OutputStream;

import java.io.IOException;


/*
	2019/07/19
 */
final public class IntScriptValue
	extends AScriptValue
{
	//
	final private @NullAllow Integer VALUE;
	final private boolean QUOTATION;


	//
	public IntScriptValue(int value)
	{
		VALUE = value;
		QUOTATION = false;
	}


	public IntScriptValue(@NullAllow Integer value)
	{
		VALUE = value;
		QUOTATION = false;
	}


	public IntScriptValue(@NullAllow Enum value)
	{
		VALUE = value != null ? value.ordinal() : null;
		QUOTATION = false;
	}


	public IntScriptValue(int value, boolean quotation)
	{
		VALUE = value;
		QUOTATION = quotation;
	}


	public IntScriptValue(@NullAllow Integer value, boolean quotation)
	{
		VALUE = value;
		QUOTATION = quotation && value != null;
	}


	public IntScriptValue(@NullAllow Enum value, boolean quotation)
	{
		VALUE = value != null ? value.ordinal() : null;
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
