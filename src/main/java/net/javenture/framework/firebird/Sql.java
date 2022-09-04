package net.javenture.framework.firebird;


/*
	2019/08/14
 */
final public class Sql
{
	//
	private Sql()
	{
	}


	//
	public static IDataset query(String sql)
		throws Exception
	{
		try (DatabaseTransaction transaction = DatabaseTransaction.read())
		{
			IDataset dataset = transaction.execute(new QuerySqlRequest(sql))
				.dataset();

			//
			transaction.commit();

			return dataset;
		}
	}


	public static IDataset query(String sql, Object... parameters)
		throws Exception
	{
		try (DatabaseTransaction transaction = DatabaseTransaction.read())
		{
			IDataset dataset = transaction.execute(new QuerySqlRequest(sql), new SqlParameters(parameters))
				.dataset();

			//
			transaction.commit();

			return dataset;
		}
	}


	public static int update(String sql)
		throws Exception
	{
		try (DatabaseTransaction transaction = DatabaseTransaction.write())
		{
			int count = transaction.execute(new UpdateSqlRequest(sql))
				.count();

			//
			transaction.commit();

			return count;
		}
	}


	public static int update(String sql, Object... parameters)
		throws Exception
	{
		try (DatabaseTransaction transaction = DatabaseTransaction.write())
		{
			int count = transaction.execute(new UpdateSqlRequest(sql), new SqlParameters(parameters))
				.count();

			//
			transaction.commit();

			return count;
		}
	}

}
