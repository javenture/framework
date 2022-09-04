package net.javenture.framework.script;


import net.javenture.framework.annotation.NullAllow;

import java.util.Iterator;


/*
	2019/08/02
 */
abstract public class AScriptValue
	implements IScriptEntry
{
	//
	@Override
	final public Type type()
	{
		return Type.VALUE;
	}


	@Override
	final public @NullAllow String name()
	{
		return null;
	}


	@Override
	final public Iterator<IScriptEntry> iterator()
	{
		throw new UnsupportedOperationException();
	}


	@Override
	final public String toString()
	{
		return ScriptUtil.toString(this);
	}


}
