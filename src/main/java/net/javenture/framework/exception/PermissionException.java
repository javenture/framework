package net.javenture.framework.exception;


import net.javenture.framework.util.StringUtil;


/*
	2017/11/24
 */
final public class PermissionException
	extends Exception
{
	//
	public PermissionException(String s)
	{
		super(s);
	}


	public PermissionException(Object... objects)
	{
		super(StringUtil.combine(objects));
	}

}
