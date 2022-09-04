package net.javenture.framework.script;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.utf8.IUtf8StreamableEntry;
import net.javenture.framework.utf8.Utf8Byte;
import net.javenture.framework.utf8.Utf8OutputStream;
import net.javenture.framework.util.Validator;

import java.io.IOException;


/*
	2019/07/19
 */
final public class Utf8StreamableScriptValue
	extends AScriptValue
{
	//
	final private @NullDisallow IUtf8StreamableEntry VALUE;
	final private boolean QUOTATION;


	//
	public Utf8StreamableScriptValue(@NullDisallow IUtf8StreamableEntry value)
	{
		Validator.notNull(value, "[value]");

		VALUE = value;
		QUOTATION = true;
	}


	public Utf8StreamableScriptValue(@NullDisallow IUtf8StreamableEntry value, boolean quotation)
	{
		Validator.notNull(value, "[value]");

		VALUE = value;
		QUOTATION = quotation;
	}


	//
	@Override
	public void toUtf8Stream(Utf8OutputStream destination)
		throws IOException
	{
		if (QUOTATION)
		{
			destination.write(Utf8Byte.APOSTROPHE);
			VALUE.toUtf8Stream(destination);
			destination.write(Utf8Byte.APOSTROPHE);
		}
		else
		{
			VALUE.toUtf8Stream(destination);
		}
	}


	@Override
	public void toScriptStream(Utf8OutputStream destination)
		throws IOException
	{
		if (QUOTATION)
		{
			destination.write(Utf8Byte.APOSTROPHE);
			VALUE.toUtf8Stream(destination);
			destination.write(Utf8Byte.APOSTROPHE);
		}
		else
		{
			VALUE.toUtf8Stream(destination);
		}
	}


	@Override
	public void toJsonStream(Utf8OutputStream destination)
		throws IOException
	{
		if (QUOTATION)
		{
			destination.write(Utf8Byte.QUOTATION);
			VALUE.toUtf8Stream(destination);
			destination.write(Utf8Byte.QUOTATION);
		}
		else
		{
			VALUE.toUtf8Stream(destination);
		}
	}

}
