package net.javenture.framework.script;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.hex.HexUtil;
import net.javenture.framework.utf8.IUtf8StreamableEntry;
import net.javenture.framework.utf8.Utf8Byte;
import net.javenture.framework.utf8.Utf8OutputStream;
import net.javenture.framework.util.NullUtil;

import java.io.IOException;


/*
	2019/11/21
 */
final public class BytesScriptValue
	extends AScriptValue
{
	//
	final private @NullDisallow IUtf8StreamableEntry ENTRY;
	final private boolean QUOTATION;


	//
	public BytesScriptValue(@NullAllow byte[] value)
	{
		ENTRY = value != null
			? IUtf8StreamableEntry.entry(HexUtil.convert(value, false))
			: NullUtil.ENTRY;

		QUOTATION = false;
	}


	public BytesScriptValue(@NullAllow byte[] value, boolean quotation)
	{
		if (value != null)
		{
			ENTRY = IUtf8StreamableEntry.entry(HexUtil.convert(value, false));
			QUOTATION = quotation;
		}
		else
		{
			ENTRY = NullUtil.ENTRY;
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
			destination.write(ENTRY);
			destination.write(Utf8Byte.APOSTROPHE);
		}
		else
		{
			destination.write(ENTRY);
		}
	}


	@Override
	public void toScriptStream(Utf8OutputStream destination)
		throws IOException
	{
		if (QUOTATION)
		{
			destination.write(Utf8Byte.APOSTROPHE);
			destination.write(ENTRY);
			destination.write(Utf8Byte.APOSTROPHE);
		}
		else
		{
			destination.write(ENTRY);
		}
	}


	@Override
	public void toJsonStream(Utf8OutputStream destination)
		throws IOException
	{
		if (QUOTATION)
		{
			destination.write(Utf8Byte.QUOTATION);
			destination.write(ENTRY);
			destination.write(Utf8Byte.QUOTATION);
		}
		else
		{
			destination.write(ENTRY);
		}
	}

}
