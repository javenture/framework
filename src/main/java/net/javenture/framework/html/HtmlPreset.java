package net.javenture.framework.html;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.utf8.IUtf8StreamableEntry;
import net.javenture.framework.utf8.Utf8OutputStream;
import net.javenture.framework.utf8.Utf8Util;
import net.javenture.framework.util.Validator;

import java.io.IOException;


/*
	2018/02/07
 */
final public class HtmlPreset
	implements IHtmlEntry
{
	//
	final private @NullDisallow IUtf8StreamableEntry ENTRY;


	//
	private HtmlPreset(@NullDisallow byte[] array)
	{
		Validator.notNull(array, "[array]");
		ENTRY = IUtf8StreamableEntry.entry(array);
	}


	public HtmlPreset(IHtmlEntry... entries)
	{
		ENTRY = IUtf8StreamableEntry.entry(Utf8Util.bytes(entries));
	}


	//
	@Override
	public void toUtf8Stream(Utf8OutputStream destination)
		throws IOException
	{
		destination.write(ENTRY);
	}


	@Override
	public String toString()
	{
		return Utf8Util.toString(this);
	}

}
