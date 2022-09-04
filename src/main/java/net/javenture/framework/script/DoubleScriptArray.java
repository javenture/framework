package net.javenture.framework.script;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullContains;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.util.DoubleContainer;


/*
	2019/07/19
 */
final public class DoubleScriptArray
	extends ScriptArray
{
	//
	final private boolean QUOTATION;


	//
	public DoubleScriptArray()
	{
		QUOTATION = false;
	}


	public DoubleScriptArray(boolean quotation)
	{
		QUOTATION = quotation;
	}


	public DoubleScriptArray(@NullAllow String name)
	{
		super(name);

		QUOTATION = false;
	}


	public DoubleScriptArray(@NullAllow String name, boolean quotation)
	{
		super(name);

		QUOTATION = quotation;
	}


	//
	public DoubleScriptArray add(double value)
	{
		super.add(new DoubleScriptValue(value, QUOTATION));

		return this;
	}


	public DoubleScriptArray add(@NullAllow Double value)
	{
		super.add(new DoubleScriptValue(value, QUOTATION));

		return this;
	}


	public DoubleScriptArray add(double... values)
	{
		for (double value : values) super.add(new DoubleScriptValue(value, QUOTATION));

		return this;
	}


	public DoubleScriptArray add(@NullContains Double... values)
	{
		for (Double value : values) super.add(new DoubleScriptValue(value, QUOTATION));

		return this;
	}


	public DoubleScriptArray add(@NullDisallow DoubleContainer values)
	{
		for (double value : values) super.add(new DoubleScriptValue(value, QUOTATION));

		return this;
	}


	public DoubleScriptArray add(@NullContains Iterable<Double> values)
	{
		for (Double value : values) super.add(new DoubleScriptValue(value, QUOTATION));

		return this;
	}

}
