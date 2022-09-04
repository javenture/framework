package net.javenture.framework.firebird;


/*
	2019/09/07
 */
@FunctionalInterface
public interface IRowSqlProcessor
	extends ISqlProcessor
{
	//
	void process(Object... objects) throws Exception;

}
