package net.javenture.framework.script;


import net.javenture.framework.utf8.Utf8OutputStream;

import java.io.IOException;


/*
	2018/12/20
 */
public interface IScriptStreamableEntry
{
	//
	void toScriptStream(Utf8OutputStream destination) throws IOException;

}
