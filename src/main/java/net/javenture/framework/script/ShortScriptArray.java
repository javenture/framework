package net.javenture.framework.script;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullContains;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.util.ShortContainer;


/*
	2019/07/19
 */
final public class ShortScriptArray
	extends ScriptArray
{
	//
	final private boolean QUOTATION;


	//
	public ShortScriptArray()
	{
		QUOTATION = false;
	}


	public ShortScriptArray(boolean quotation)
	{
		QUOTATION = quotation;
	}


	public ShortScriptArray(@NullAllow String name)
	{
		super(name);

		QUOTATION = false;
	}


	public ShortScriptArray(@NullAllow String name, boolean quotation)
	{
		super(name);

		QUOTATION = quotation;
	}


	//
	public ShortScriptArray add(short value)
	{
		super.add(new ShortScriptValue(value, QUOTATION));

		return this;
	}


	public ShortScriptArray add(@NullAllow Short value)
	{
		super.add(new ShortScriptValue(value, QUOTATION));

		return this;
	}


	public ShortScriptArray add(short... values)
	{
		for (short value : values) super.add(new ShortScriptValue(value, QUOTATION));

		return this;
	}


	public ShortScriptArray add(@NullContains Short... values)
	{
		for (Short value : values) super.add(new ShortScriptValue(value, QUOTATION));

		return this;
	}


	public ShortScriptArray add(@NullDisallow ShortContainer values)
	{
		for (short value : values) super.add(new ShortScriptValue(value, QUOTATION));

		return this;
	}


	public ShortScriptArray add(@NullContains Iterable<Short> values)
	{
		for (Short value : values) super.add(new ShortScriptValue(value, QUOTATION));

		return this;
	}

}
