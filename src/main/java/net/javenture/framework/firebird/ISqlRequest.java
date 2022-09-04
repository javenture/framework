package net.javenture.framework.firebird;


import net.javenture.framework.log.ILog;


/*
	2019/08/14
 */
public interface ISqlRequest
	extends ILog
{
	//
	SqlType type();
	String text();
	boolean metadata();

}
