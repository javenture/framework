package net.javenture.framework.vue;


import net.javenture.framework.script.ScriptMethod;
import net.javenture.framework.utf8.IUtf8StreamableEntry;
import net.javenture.framework.utf8.Utf8OutputStream;

import java.io.IOException;


/*
	2019/06/28
 */
final public class VueRef
	implements IUtf8StreamableEntry
{
	//
	final private String NAME;


	//
	public VueRef(String name)
	{
		NAME = name;
	}


	//
	@Override
	public void toUtf8Stream(Utf8OutputStream destination)
		throws IOException
	{
		destination.write(NAME);
	}


	public String invoke(ScriptMethod value)
	{
		return "$refs." + NAME + "." + value.name() + "()";
	}


	public String invoke(CharSequence value)
	{
		return "$refs." + NAME + "." + value;
	}

}
