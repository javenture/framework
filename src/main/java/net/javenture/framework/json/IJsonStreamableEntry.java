package net.javenture.framework.json;


import net.javenture.framework.utf8.Utf8OutputStream;

import java.io.IOException;


/*
	2018/05/01
 */
public interface IJsonStreamableEntry
{
	//
	void toJsonStream(Utf8OutputStream destination) throws IOException;

}
