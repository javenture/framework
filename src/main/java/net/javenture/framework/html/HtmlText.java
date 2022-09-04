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
	2019/06/25
 */
final public class HtmlText
	implements IHtmlEntry
{
	//
	final private static Utf8Replacement REPLACEMENT = new Utf8Replacement
	(
		new Utf8Replacement.Pair('"', HtmlEntity.QUOT),
		new Utf8Replacement.Pair('&', HtmlEntity.AMP),
		new Utf8Replacement.Pair('\'', HtmlReference.APOSTROPHE),
		new Utf8Replacement.Pair('<', HtmlEntity.LT),
		new Utf8Replacement.Pair('>', HtmlEntity.GT)
	);


	//
	final private @NullDisallow IUtf8StreamableEntry ENTRY;


	//
	public HtmlText(@NullDisallow IUtf8StreamableEntry entry)
	{
		Validator.notNull(entry, "[entry]");

		ENTRY = entry;
	}


	public HtmlText(char value)
	{
		ENTRY = REPLACEMENT.entry(value);
	}


	public HtmlText(@NullDisallow char[] value)
	{
		ENTRY = REPLACEMENT.entry(value);
	}


	public HtmlText(boolean value)
	{
		ENTRY = value ? BooleanUtil.True.ENTRY : BooleanUtil.False.ENTRY;
	}


	public HtmlText(@NullAllow Boolean value)
	{
		if (value != null) ENTRY = value ? BooleanUtil.True.ENTRY : BooleanUtil.False.ENTRY;
		else ENTRY = NullUtil.ENTRY;
	}


	public HtmlText(short value)
	{
		ENTRY = Utf8Number.entry(value);
	}


	public HtmlText(@NullAllow Short value)
	{
		ENTRY = Utf8Number.entry(value);
	}


	public HtmlText(int value)
	{
		ENTRY = Utf8Number.entry(value);
	}


	public HtmlText(@NullAllow Integer value)
	{
		ENTRY = Utf8Number.entry(value);
	}


	public HtmlText(long value)
	{
		ENTRY = Utf8Number.entry(value);
	}


	public HtmlText(@NullAllow Long value)
	{
		ENTRY = Utf8Number.entry(value);
	}


	public HtmlText(float value)
	{
		ENTRY = Utf8Number.entry(value);
	}


	public HtmlText(@NullAllow Float value)
	{
		ENTRY = Utf8Number.entry(value);
	}


	public HtmlText(double value)
	{
		ENTRY = Utf8Number.entry(value);
	}


	public HtmlText(@NullAllow Double value)
	{
		ENTRY = Utf8Number.entry(value);
	}


	public HtmlText(@NullAllow Number value)
	{
		ENTRY = Utf8Number.entry(value);
	}


	public HtmlText(@NullAllow CharSequence value)
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
