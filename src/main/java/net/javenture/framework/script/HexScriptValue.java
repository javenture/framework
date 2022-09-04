package net.javenture.framework.script;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.hex.IHex;
import net.javenture.framework.utf8.IUtf8StreamableEntry;
import net.javenture.framework.utf8.Utf8Byte;
import net.javenture.framework.utf8.Utf8OutputStream;
import net.javenture.framework.util.NullUtil;

import java.io.IOException;


/*
	2019/08/02
 */
final public class HexScriptValue
	extends AScriptValue
{
	//
	final private @NullAllow IUtf8StreamableEntry VALUE;
	final private boolean QUOTATION;


	//
	public HexScriptValue(@NullAllow IHex value)
	{
		VALUE = value != null
			? IUtf8StreamableEntry.entry(value.toString(true))
			: NullUtil.ENTRY;

		QUOTATION = false;
	}


	public HexScriptValue(@NullAllow IHex value, boolean quotation)
	{
		if (value != null)
		{
			VALUE = IUtf8StreamableEntry.entry(value.toString(true));
			QUOTATION = quotation;
		}
		else
		{
			VALUE = NullUtil.ENTRY;
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
