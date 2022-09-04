package net.javenture.framework.html;


import net.javenture.framework.utf8.IUtf8StreamableEntry;
import net.javenture.framework.utf8.Utf8OutputStream;

import java.io.IOException;


/*
	2018/02/07
 */
final public class HtmlReference
	implements IHtmlEntry
{
	//
	final private int CODEPOINT;
	final private String VALUE;
	final private IUtf8StreamableEntry ENTRY;


	//
	public HtmlReference(int codepoint)
	{
		CODEPOINT = codepoint;
		VALUE = "&#" + codepoint + ";";
		ENTRY = IUtf8StreamableEntry.entry(VALUE, true);
	}


	//
	@Override
	public void toUtf8Stream(Utf8OutputStream destination) throws IOException
	{
		destination.write(ENTRY);
	}


	@Override
	public String toString()
	{
		return VALUE;
	}


	public int codepoint()
	{
		return CODEPOINT;
	}


	/*
	 	https://en.wikipedia.org/wiki/Numeric_character_reference
	 	https://en.wikipedia.org/wiki/ASCII
	 */
	final public static HtmlReference APOSTROPHE = new HtmlReference(39); // '
	final public static HtmlReference HYPHEN = new HtmlReference(45); // -

}
