package net.javenture.framework.script;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullContains;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.util.LongContainer;


/*
	2019/07/19
 */
final public class LongScriptArray
	extends ScriptArray
{
	//
	final private boolean QUOTATION;


	//
	public LongScriptArray()
	{
		QUOTATION = false;
	}


	public LongScriptArray(boolean quotation)
	{
		QUOTATION = quotation;
	}


	public LongScriptArray(@NullAllow String name)
	{
		super(name);

		QUOTATION = false;
	}


	public LongScriptArray(@NullAllow String name, boolean quotation)
	{
		super(name);

		QUOTATION = quotation;
	}


	//
	public LongScriptArray add(long value)
	{
		super.add(new LongScriptValue(value, QUOTATION));

		return this;
	}


	public LongScriptArray add(@NullAllow Long value)
	{
		super.add(new LongScriptValue(value, QUOTATION));

		return this;
	}


	public LongScriptArray add(long... values)
	{
		for (long value : values) super.add(new LongScriptValue(value, QUOTATION));

		return this;
	}


	public LongScriptArray add(@NullContains Long... values)
	{
		for (Long value : values) super.add(new LongScriptValue(value, QUOTATION));

		return this;
	}


	public LongScriptArray add(@NullDisallow LongContainer values)
	{
		for (long value : values) super.add(new LongScriptValue(value, QUOTATION));

		return this;
	}


	public LongScriptArray add(@NullContains Iterable<Long> values)
	{
		for (Long value : values) super.add(new LongScriptValue(value, QUOTATION));

		return this;
	}

}
