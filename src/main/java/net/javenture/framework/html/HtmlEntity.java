package net.javenture.framework.html;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.utf8.IUtf8StreamableEntry;
import net.javenture.framework.utf8.Utf8OutputStream;
import net.javenture.framework.util.Validator;

import java.io.IOException;


/*
	2018/02/07
 */
final public class HtmlEntity
	implements IHtmlEntry
{
	//
	final private String NAME;
	final private String VALUE;
	final private IUtf8StreamableEntry ENTRY;


	//
	public HtmlEntity(@NullDisallow String name)
	{
		Validator.argument(!name.startsWith("&"), "'&' at start is redundant");
		Validator.argument(!name.endsWith(";"), "';' at end is redundant");

		NAME = name;
		VALUE = "&" + name + ";";
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


	public String name()
	{
		return NAME;
	}


	/*
		https://www.w3.org/TR/html4/sgml/entities.html
	 */
	final public static HtmlEntity QUOT = new HtmlEntity("quot");
	final public static HtmlEntity AMP = new HtmlEntity("amp");
	final public static HtmlEntity LT = new HtmlEntity("lt");
	final public static HtmlEntity GT = new HtmlEntity("gt");
	final public static HtmlEntity NBSP = new HtmlEntity("nbsp");
	final public static HtmlEntity BULL = new HtmlEntity("bull");
	final public static HtmlEntity HELLIP = new HtmlEntity("hellip");

}
