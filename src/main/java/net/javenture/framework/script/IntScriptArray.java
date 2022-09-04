package net.javenture.framework.script;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullContains;
import net.javenture.framework.util.IntContainer;


/*
	2019/07/29
 */
final public class IntScriptArray
	extends ScriptArray
{
	//
	final private boolean QUOTATION;


	//
	public IntScriptArray()
	{
		QUOTATION = false;
	}


	public IntScriptArray(boolean quotation)
	{
		QUOTATION = quotation;
	}


	public IntScriptArray(@NullAllow String name)
	{
		super(name);

		QUOTATION = false;
	}


	public IntScriptArray(@NullAllow String name, boolean quotation)
	{
		super(name);

		QUOTATION = quotation;
	}


	//
	public IntScriptArray add(int value)
	{
		super.add(new IntScriptValue(value, QUOTATION));

		return this;
	}


	public IntScriptArray add(@NullAllow Integer value)
	{
		super.add(new IntScriptValue(value, QUOTATION));

		return this;
	}


	public IntScriptArray add(int... values)
	{
		for (int value : values) super.add(new IntScriptValue(value, QUOTATION));

		return this;
	}


	public IntScriptArray add(@NullContains Integer... values)
	{
		for (Integer value : values) super.add(new IntScriptValue(value, QUOTATION));

		return this;
	}


	public IntScriptArray add(@NullContains IntContainer values)
	{
		for (int value : values) super.add(new IntScriptValue(value, QUOTATION));

		return this;
	}


	public IntScriptArray add(@NullContains Iterable<Integer> values)
	{
		for (Integer value : values) super.add(new IntScriptValue(value, QUOTATION));

		return this;
	}

}
