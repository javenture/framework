package net.javenture.framework.script;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.utf8.Utf8Byte;
import net.javenture.framework.utf8.Utf8OutputStream;

import java.io.IOException;
import java.sql.Timestamp;


/*
	2019/08/02
 */
final public class TimestampScriptValue
	extends AScriptValue
{
	//
	final private @NullAllow String VALUE;
	final private boolean QUOTATION;


	//
	public TimestampScriptValue(@NullAllow Timestamp value)
	{
		VALUE = value != null ? value.toString() : null;
		QUOTATION = false;
	}


	public TimestampScriptValue(@NullAllow Timestamp value, boolean quotation)
	{
		if (value != null)
		{
			VALUE = value.toString();
			QUOTATION = quotation;
		}
		else
		{
			VALUE = null;
			QUOTATION = false;
		}
	}


	//
	@Override
	public void toUtf8Stream(Utf8OutputStream destination)
		throws IOException
	{
		if (QUOTATION)
		{
			destination.write(Utf8Byte.APOSTROPHE);
			destination.write(VALUE);
			destination.write(Utf8Byte.APOSTROPHE);
		}
		else
		{
			destination.write(VALUE);
		}
	}


	@Override
	public void toScriptStream(Utf8OutputStream destination)
		throws IOException
	{
		if (QUOTATION)
		{
			destination.write(Utf8Byte.APOSTROPHE);
			destination.write(VALUE);
			destination.write(Utf8Byte.APOSTROPHE);
		}
		else
		{
			destination.write(VALUE);
		}
	}


	@Override
	public void toJsonStream(Utf8OutputStream destination)
		throws IOException
	{
		if (QUOTATION)
		{
			destination.write(Utf8Byte.QUOTATION);
			destination.write(VALUE);
			destination.write(Utf8Byte.QUOTATION);
		}
		else
		{
			destination.write(VALUE);
		}
	}

}
