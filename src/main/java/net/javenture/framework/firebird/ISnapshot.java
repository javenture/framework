package net.javenture.framework.firebird;


/*
	2019/12/02
 */
@FunctionalInterface
public interface ISnapshot
{
	//
	boolean change();


	//
	ISnapshot BLANK = () -> false;

}
