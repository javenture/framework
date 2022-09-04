package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullDisallow;


/*
	2019/08/14
 */
public interface ISqlResponse
{
	//
	SqlType type();
	@NullDisallow IDataset dataset();
	int count();
	int[] counts();

}
