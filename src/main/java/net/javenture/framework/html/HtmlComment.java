package net.javenture.framework.html;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.utf8.IUtf8StreamableEntry;
import net.javenture.framework.utf8.Utf8Byte;
import net.javenture.framework.utf8.Utf8OutputStream;
import net.javenture.framework.utf8.Utf8Replacement;
import net.javenture.framework.utf8.Utf8Util;

import java.io.IOException;


/*
	2018/12/18
 */
final public class HtmlComment
	implements IHtmlEntry
{
	//
	final private static Utf8Replacement REPLACEMENT = new Utf8Replacement
	(
		new Utf8Replacement.Pair('"', HtmlEntity.QUOT),
		new Utf8Replacement.Pair('&', HtmlEntity.AMP),
		new Utf8Replacement.Pair('\'', HtmlReference.APOSTROPHE),
		new Utf8Replacement.Pair('<', HtmlEntity.LT),
		new Utf8Replacement.Pair('>', HtmlEntity.GT),
		new Utf8Replacement.Pair('-', HtmlReference.HYPHEN)
	);


	//
	final private IUtf8StreamableEntry ENTRY;


	//
	public HtmlComment(@NullDisallow char[] value)
	{
		ENTRY = REPLACEMENT.entry(value);
	}


	public HtmlComment(@NullAllow CharSequence value)
	{
		ENTRY = REPLACEMENT.entry(value);
	}


	//
	@Override
	public void toUtf8Stream(Utf8OutputStream destination)
		throws IOException
	{
		destination.write(Utf8Byte.LESS_THAN, Utf8Byte.EXCLAMATION, Utf8Byte.MINUS, Utf8Byte.MINUS, Utf8Byte.SPACE);
		destination.write(ENTRY);
		destination.write(Utf8Byte.SPACE, Utf8Byte.MINUS, Utf8Byte.MINUS, Utf8Byte.GREATER_THAN);
	}


	@Override
	public String toString()
	{
		return Utf8Util.toString(ENTRY);
	}


}
