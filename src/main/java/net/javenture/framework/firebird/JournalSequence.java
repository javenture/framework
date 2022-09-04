package net.javenture.framework.firebird;


import net.javenture.framework.exception.DatabaseException;


/*
	2019/12/09
 */
final class JournalSequence
{
	//
	final private static int INIT = 0; // !
	final private static String SEQUENCE = "JOURNAL_SEQUENCE";                                   // ???


	//
	static void create(DatabaseTransaction transaction)
		throws Exception
	{
		transaction.execute(SqlTemplates.CREATE);
	}


	static void delete(DatabaseTransaction transaction)
		throws Exception
	{
		transaction.execute(SqlTemplates.DELETE);
	}


	static long get(DatabaseTransaction transaction)
		throws Exception
	{
		ISqlResponse response = transaction.execute(SqlTemplates.GET);
		IDataset dataset = response.dataset();

		if (dataset.first()) return dataset.getLong(0);
		else throw new DatabaseException("ID sequence not available");
	}


	static void set(DatabaseTransaction transaction, long value)
		throws Exception
	{
		SqlMarkup markup = new SqlMarkup
		(
			"alter sequence",
				SEQUENCE,
			"restart with",
				value // !
		);

		transaction.execute(new UpdateSqlRequest(markup));
	}


	static void init(DatabaseTransaction transaction)
		throws Exception
	{
		delete(transaction);
		create(transaction);
		set(transaction, INIT);
	}


	//
	final private static class SqlTemplates
	{
		/*
			https://firebirdsql.org/manual/generatorguide-sqlsyntax.html
		 */
		//
		final private static ASqlTemplate.AUpdate CREATE = new ASqlTemplate.AUpdate()
		{
			@Override
			protected SqlMarkup _markup()
			{
				return new SqlMarkup
				(
					"create sequence",
						SEQUENCE
				);
			}
		};

		final private static ASqlTemplate.AUpdate DELETE = new ASqlTemplate.AUpdate()
		{
			@Override
			protected SqlMarkup _markup()
			{
				return new SqlMarkup
				(
					"drop sequence",
						SEQUENCE
				);
			}
		};

		final private static ASqlTemplate.AQuery GET = new ASqlTemplate.AQuery()
		{
			@Override
			protected SqlMarkup _markup()
			{
				return new SqlMarkup
				(
					SqlReceivers.entry(0, DatabaseType.LONG), // !

					"select next value for",
						SEQUENCE,
					"from",
						"RDB$DATABASE"                                                     // XXX
				);
			}
		};
	}

}
