package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullDisallow;


/*
	2019/12/29
 */
final public class BatchSqlResponse
	implements ISqlResponse
{
	//
	final private int COUNT;
	final private int[] COUNTS;


	//
	@SuppressWarnings("AssignmentOrReturnOfFieldWithMutableType")
	BatchSqlResponse(int[] counts)
	{
		int count = 0;

		for (int i : counts)
		{
			if (i > 0) count += i;
		}

		//
		COUNT = count;
		COUNTS = counts;
	}


	//
	@Override
	public SqlType type()
	{
		return SqlType.BATCH;
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
