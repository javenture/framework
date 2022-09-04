package net.javenture.framework.exception;


import net.javenture.framework.util.StringUtil;


/*
	2017/11/24
 */
final public class IdentifierException
	extends Exception
{
	//
	public IdentifierException(String s)
	{
		super(s);
	}


	public IdentifierException(Object... objects)
	{
		super(StringUtil.combine(objects));
	}

}
