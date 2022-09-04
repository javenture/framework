package net.javenture.framework.script;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullContains;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.util.StringContainer;


/*
	2019/07/19
 */
final public class StringScriptArray
	extends ScriptArray
{
	//
	final private boolean QUOTATION;


	//
	public StringScriptArray()
	{
		QUOTATION = true;
	}


	public StringScriptArray(boolean quotation)
	{
		QUOTATION = quotation;
	}


	public StringScriptArray(@NullAllow String name)
	{
		super(name);

		QUOTATION = true;
	}


	public StringScriptArray(@NullAllow String name, boolean quotation)
	{
		super(name);

		QUOTATION = quotation;
	}


	//
	public StringScriptArray add(@NullAllow String value)
	{
		super.add(new StringScriptValue(value, QUOTATION));

		return this;
	}


	public StringScriptArray add(@NullContains String... values)
	{
		for (String value : values) super.add(new StringScriptValue(value, QUOTATION));

		return this;
	}


	public StringScriptArray add(@NullDisallow StringContainer values)
	{
		for (String value : values) super.add(new StringScriptValue(value, QUOTATION));

		return this;
	}


	public StringScriptArray add(@NullContains Iterable<String> values)
	{
		for (String value : values) super.add(new StringScriptValue(value, QUOTATION));

		return this;
	}


	// XXX: add(int), add(long)... ?


}
