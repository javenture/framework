package net.javenture.framework.script;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullContains;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.util.BooleanContainer;


/*
	2019/07/19
 */
final public class BooleanScriptArray
	extends ScriptArray
{
	//
	final private boolean QUOTATION;


	//
	public BooleanScriptArray()
	{
		QUOTATION = false;
	}


	public BooleanScriptArray(boolean quotation)
	{
		QUOTATION = quotation;
	}


	public BooleanScriptArray(@NullAllow String name)
	{
		super(name);

		QUOTATION = false;
	}


	public BooleanScriptArray(@NullAllow String name, boolean quotation)
	{
		super(name);

		QUOTATION = quotation;
	}


	//
	public BooleanScriptArray add(boolean value)
	{
		super.add(BooleanScriptValue.instance(value, QUOTATION));

		return this;
	}


	public BooleanScriptArray add(@NullAllow Boolean value)
	{
		super.add(BooleanScriptValue.instance(value, QUOTATION));

		return this;
	}


	public BooleanScriptArray add(boolean... values)
	{
		for (boolean value : values) super.add(BooleanScriptValue.instance(value, QUOTATION));

		return this;
	}


	public BooleanScriptArray add(@NullContains Boolean... values)
	{
		for (Boolean value : values) super.add(BooleanScriptValue.instance(value, QUOTATION));

		return this;
	}


	public BooleanScriptArray add(@NullDisallow BooleanContainer values)
	{
		for (boolean value : values) super.add(BooleanScriptValue.instance(value, QUOTATION));

		return this;
	}


	public BooleanScriptArray add(@NullContains Iterable<Boolean> values)
	{
		for (Boolean value : values) super.add(BooleanScriptValue.instance(value, QUOTATION));

		return this;
	}

}
