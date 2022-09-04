package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullDisallow;


/*
	2019/12/29
 */
final public class UpdateSqlResponse
	implements ISqlResponse
{
	//
	final private int COUNT;
	final private int[] COUNTS;


	//
	UpdateSqlResponse(int count)
	{
		COUNT = count;
		COUNTS = new int[] { count };
	}


	//
	@Override
	public SqlType type()
	{
		return SqlType.UPDATE;
	}


	@Override
	public @NullDisallow IDataset dataset()
	{
		throw new UnsupportedOperationException(); // !
	}


	@Override
	public int count()
	{
		return COUNT;
	}


	@Override
	@SuppressWarnings("AssignmentOrReturnOfFieldWithMutableType")
	public int[] counts()
	{
		return COUNTS;
	}

}
