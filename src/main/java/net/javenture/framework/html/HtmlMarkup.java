package net.javenture.framework.html;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.utf8.IUtf8StreamableEntry;
import net.javenture.framework.utf8.Utf8Number;
import net.javenture.framework.utf8.Utf8OutputStream;
import net.javenture.framework.utf8.Utf8Replacement;
import net.javenture.framework.utf8.Utf8Util;
import net.javenture.framework.util.BooleanUtil;
import net.javenture.framework.util.NullUtil;
import net.javenture.framework.util.Validator;

import java.io.IOException;


/*
	2018/12/18
 */
final public class HtmlMarkup
	implements IHtmlEntry
{
	//
	final private static Utf8Replacement REPLACEMENT = Utf8Replacement.BLANK;


	//
	final private @NullDisallow IUtf8StreamableEntry ENTRY;


	//
	public HtmlMarkup(@NullDisallow IUtf8StreamableEntry entry)
	{
		Validator.notNull(entry, "[entry]");

		ENTRY = entry;
	}


	public HtmlMarkup(char value)
	{
		ENTRY = REPLACEMENT.entry(value);
	}


	public HtmlMarkup(@NullDisallow char[] value)
	{
		ENTRY = REPLACEMENT.entry(value);
	}


	public HtmlMarkup(boolean value)
	{
		ENTRY = value ? BooleanUtil.True.ENTRY : BooleanUtil.False.ENTRY;
	}


	public HtmlMarkup(@NullAllow Boolean value)
	{
		if (value != null) ENTRY = value ? BooleanUtil.True.ENTRY : BooleanUtil.False.ENTRY;
		else ENTRY = NullUtil.ENTRY;
	}


	public HtmlMarkup(short value)
	{
		ENTRY = Utf8Number.entry(value);
	}


	public HtmlMarkup(int value)
	{
		ENTRY = Utf8Number.entry(value);
	}


	public HtmlMarkup(long value)
	{
		ENTRY = Utf8Number.entry(value);
	}


	public HtmlMarkup(float value)
	{
		ENTRY = Utf8Number.entry(value);
	}


	public HtmlMarkup(double value)
	{
		ENTRY = Utf8Number.entry(value);
	}


	public HtmlMarkup(@NullAllow Number value)
	{
		ENTRY = Utf8Number.entry(value);
	}


	public HtmlMarkup(@NullAllow CharSequence value)
	{
		ENTRY = REPLACEMENT.entry(value);
	}


	//
	@Override
	public void toUtf8Stream(Utf8OutputStream destination)
		throws IOException
	{
		ENTRY.toUtf8Stream(destination);
	}


	@Override
	public String toString()
	{
		return Utf8Util.toString(ENTRY);
	}

}
