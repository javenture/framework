package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.util.IntArrayUtil;


/*
	2019/12/29
 */
final public class QuerySqlResponse
	implements ISqlResponse
{
	//
	final private @NullDisallow IDataset DATASET;


	//
	QuerySqlResponse(@NullDisallow IDataset dataset)
	{
		DATASET = dataset;
	}


	//
	@Override
	public SqlType type()
	{
		return SqlType.QUERY;
	}


	@Override
	public @NullDisallow IDataset dataset()
	{
		return DATASET;
	}


	@Override
	public int count()
	{
		throw new UnsupportedOperationException(); // !
	}


	@Override
	public int[] counts()
	{
		throw new UnsupportedOperationException(); // !
	}

}
