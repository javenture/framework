package net.javenture.framework.css;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.utf8.IUtf8StreamableEntry;
import net.javenture.framework.utf8.Utf8OutputStream;
import net.javenture.framework.utf8.Utf8Util;
import net.javenture.framework.util.Validator;

import java.io.IOException;


/*
	2018/02/12
 */
final public class CssClass
	implements ICssClass
{
	//
	final private @NullDisallow IUtf8StreamableEntry ENTRY;


	//
	public CssClass(@NullDisallow String name)
	{
		Validator.notNull(name, "[name]");

		ENTRY = IUtf8StreamableEntry.entry(name, true);
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
