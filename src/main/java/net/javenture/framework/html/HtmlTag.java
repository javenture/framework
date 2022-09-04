package net.javenture.framework.html;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.utf8.IUtf8StreamableEntry;
import net.javenture.framework.utf8.Utf8OutputStream;
import net.javenture.framework.util.Validator;

import java.io.IOException;


/*
	2019/03/05
 */
final public class HtmlTag
	implements IUtf8StreamableEntry
{
	//
	final private @NullDisallow String NAME;
	final private @NullDisallow IUtf8StreamableEntry ENTRY;


	//
	private HtmlTag()
	{
		NAME = "";
		ENTRY = IUtf8StreamableEntry.BLANK;
	}


	public HtmlTag(@NullDisallow String name)
	{
		Validator.notNull(name, "[name]");
		Validator.argument(!name.isEmpty(), "[name] is empty");

		NAME = name;
		ENTRY = IUtf8StreamableEntry.entry(name, true);
	}


	//
	@Override
	public void toUtf8Stream(Utf8OutputStream destination)
		throws IOException
	{
		ENTRY.toUtf8Stream(destination);
	}


	public String name()
	{
		return NAME;
	}


	//
	final public static HtmlTag BLANK = new HtmlTag();

}
