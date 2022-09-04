package net.javenture.framework.json;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.utf8.Utf8OutputStream;

import java.io.IOException;


/*
	2019/12/16
 */
final public class JsonOutputStream
	extends Utf8OutputStream
{
	//
	public JsonOutputStream()
	{
	}


	//
	public void write(@NullDisallow IJsonStreamableEntry entry)
		throws IOException
	{
		entry.toJsonStream(this);
	}


	public void write(IJsonStreamableEntry... entries)
		throws IOException
	{
		for (IJsonStreamableEntry entry : entries) entry.toJsonStream(this);
	}

}
