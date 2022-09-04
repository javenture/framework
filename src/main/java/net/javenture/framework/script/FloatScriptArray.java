package net.javenture.framework.script;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullContains;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.util.FloatContainer;


/*
	2019/07/19
 */
final public class FloatScriptArray
	extends ScriptArray
{
	//
	final private boolean QUOTATION;


	//
	public FloatScriptArray()
	{
		QUOTATION = false;
	}


	public FloatScriptArray(boolean quotation)
	{
		QUOTATION = quotation;
	}


	public FloatScriptArray(@NullAllow String name)
	{
		super(name);

		QUOTATION = false;
	}


	public FloatScriptArray(@NullAllow String name, boolean quotation)
	{
		super(name);

		QUOTATION = quotation;
	}


	//
	public FloatScriptArray add(float value)
	{
		super.add(new FloatScriptValue(value, QUOTATION));

		return this;
	}


	public FloatScriptArray add(@NullAllow Float value)
	{
		super.add(new FloatScriptValue(value, QUOTATION));

		return this;
	}


	public FloatScriptArray add(float... values)
	{
		for (float value : values) super.add(new FloatScriptValue(value, QUOTATION));

		return this;
	}


	public FloatScriptArray add(@NullContains Float... values)
	{
		for (Float value : values) super.add(new FloatScriptValue(value, QUOTATION));

		return this;
	}


	public FloatScriptArray add(@NullDisallow FloatContainer values)
	{
		for (float value : values) super.add(new FloatScriptValue(value, QUOTATION));

		return this;
	}


	public FloatScriptArray add(@NullContains Iterable<Float> values)
	{
		for (Float value : values) super.add(new FloatScriptValue(value, QUOTATION));

		return this;
	}

}
