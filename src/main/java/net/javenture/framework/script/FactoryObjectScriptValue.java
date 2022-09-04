package net.javenture.framework.script;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.factory.IFactoryObject;
import net.javenture.framework.json.JsonUtil;
import net.javenture.framework.utf8.Utf8Byte;
import net.javenture.framework.utf8.Utf8OutputStream;

import java.io.IOException;


/*
	2019/11/21
 */
final public class FactoryObjectScriptValue
	extends AScriptValue
{
	//
	final private @NullAllow CharSequence VALUE;
	final private boolean QUOTATION;


	//
	public FactoryObjectScriptValue(@NullAllow IFactoryObject value)
	{
		VALUE = value != null ? value.toString() : null;
		QUOTATION = false;
	}


	public FactoryObjectScriptValue(@NullAllow IFactoryObject value, boolean quotation)
	{
		VALUE = value != null ? value.toString() : null;
		QUOTATION = quotation && value != null;
	}


	//
	@Override
	public void toUtf8Stream(Utf8OutputStream destination)
		throws IOException
	{
		if (QUOTATION)
		{
			destination.write(Utf8Byte.APOSTROPHE);
			ScriptUtil.REPLACEMENT.toStream(destination, VALUE);
			destination.write(Utf8Byte.APOSTROPHE);
		}
		else
		{
			ScriptUtil.REPLACEMENT.toStream(destination, VALUE);
		}
	}


	@Override
	public void toScriptStream(Utf8OutputStream destination)
		throws IOException
	{
		if (QUOTATION)
		{
			destination.write(Utf8Byte.APOSTROPHE);
			ScriptUtil.REPLACEMENT.toStream(destination, VALUE);
			destination.write(Utf8Byte.APOSTROPHE);
		}
		else
		{
			ScriptUtil.REPLACEMENT.toStream(destination, VALUE);
		}
	}


	@Override
	public void toJsonStream(Utf8OutputStream destination)
		throws IOException
	{
		if (QUOTATION)
		{
			destination.write(Utf8Byte.QUOTATION);
			JsonUtil.REPLACEMENT.toStream(destination, VALUE);
			destination.write(Utf8Byte.QUOTATION);
		}
		else
		{
			JsonUtil.REPLACEMENT.toStream(destination, VALUE);
		}
	}

}
