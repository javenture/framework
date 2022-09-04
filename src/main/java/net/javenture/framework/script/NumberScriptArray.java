package net.javenture.framework.script;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullContains;


/*
	2019/07/19
 */
final public class NumberScriptArray
	extends ScriptArray
{
	//
	final private boolean QUOTATION;


	//
	public NumberScriptArray()
	{
		QUOTATION = false;
	}


	public NumberScriptArray(boolean quotation)
	{
		QUOTATION = quotation;
	}


	public NumberScriptArray(@NullAllow String name)
	{
		super(name);

		QUOTATION = false;
	}


	public NumberScriptArray(@NullAllow String name, boolean quotation)
	{
		super(name);

		QUOTATION = quotation;
	}


	//
	public NumberScriptArray add(@NullAllow Number value)
	{
		super.add(new NumberScriptValue(value, QUOTATION));

		return this;
	}


	public NumberScriptArray add(@NullContains Number... values)
	{
		for (Number value : values) super.add(new NumberScriptValue(value, QUOTATION));

		return this;
	}


	public NumberScriptArray add(@NullContains Iterable<Number> values)
	{
		for (Number value : values) super.add(new NumberScriptValue(value, QUOTATION));

		return this;
	}

}
