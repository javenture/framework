package net.javenture.framework.firebird;


/*
	2019/08/21
 */
final class DatabaseTransactionThreadLocal
{
	//
	final private static ThreadLocal<DatabaseTransaction> TRANSACTION = new ThreadLocal<>();


	//
	private DatabaseTransactionThreadLocal()
	{
	}


	//
	static DatabaseTransaction get()
	{
		return TRANSACTION.get();
	}


	static void set(DatabaseTransaction transaction)
	{
		TRANSACTION.set(transaction);
	}

}
