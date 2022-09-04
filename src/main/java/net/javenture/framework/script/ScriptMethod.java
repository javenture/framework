package net.javenture.framework.script;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.util.Validator;


/*
	2019/06/28
 */
public class ScriptMethod
{
	//
	final private @NullDisallow String NAME;


	//
	public ScriptMethod(@NullDisallow String name)
	{
		Validator.notNull(name, "[name]");
		NAME = name;
	}


	//
	public String name()
	{
		return NAME;
	}

}
