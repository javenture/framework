package net.javenture.framework.exception;


import net.javenture.framework.util.StringUtil;


/*
	2017/11/24
 */
final public class DatabaseException
	extends Exception
{
	//
	public DatabaseException(Exception e)
	{
		super(e);
	}


	public DatabaseException(Object... objects)
	{
		super(StringUtil.combine(objects));
	}

}
