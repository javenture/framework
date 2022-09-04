package net.javenture.framework.script;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.utf8.Utf8OutputStream;

import java.io.IOException;


/*
	2019/12/16
 */
final public class ScriptOutputStream
	extends Utf8OutputStream
{
	//
	public ScriptOutputStream()
	{
	}


	//
	public void write(@NullDisallow IScriptStreamableEntry entry)
		throws IOException
	{
		entry.toScriptStream(this);
	}


	public void write(IScriptStreamableEntry... entries)
		throws IOException
	{
		for (IScriptStreamableEntry entry : entries) entry.toScriptStream(this);
	}

}
