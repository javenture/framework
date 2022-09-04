package net.javenture.framework.firebird;


import net.javenture.framework.log.Log;

import java.util.ArrayList;


/*
	2019/08/28
 */
final public class DatabaseUtil
{
	//
	final private static Log LOG = Log.instance(DatabaseUtil.class);
	final private static String BLANK = "";
	final private static String COMMA = ",";


	// XXX: database validator: check & correct value

/*
	final public static IScheduler SCHEDULER = new AbstractScheduler()                                   // XXX
	{
		@Override
		public void execute()
		{
			indexes();
			sweep();
		}
	};
*/


	//
	private DatabaseUtil()
	{
	}


	//
	public static String comma(int current, int total)
	{
		return current < total - 1 ? COMMA : BLANK;
	}


	public static String delimiter(int current, int total, String delimiter)
	{
		return current < total - 1 ? delimiter : BLANK;
	}






	public static void list(DatabaseTable table, boolean order)                  // XXX: processor (lambda)
	{
		// return new ArrayList<IKey>()
	}


	public static void toContainer(ADatabaseField field, boolean order)
	{

	}


	public static void toSequence(ADatabaseField field)
	{

	}


	public static void count(ADatabaseField field, boolean distinct)
	{

	}



	public static void updateIndexes()                                                                       // XXX: name ?
	{
		try (DatabaseTransaction transaction = DatabaseTransaction.write())
		{
			ArrayList<String> list = new ArrayList<>();

			{
				SqlMarkup markup = new SqlMarkup                                                                       // XXX: template
				(
					"select",
						"RDB$INDEX_NAME", // column
					"from",
						"RDB$INDICES",
					"where",
						"RDB$INDEX_NAME not starts with 'RDB$'"
				);

				QuerySqlRequest request = new QuerySqlRequest(markup);
				IColumnSqlProcessor<String> processor = IColumnSqlProcessor.fill(list);
				transaction.execute(request, processor);
			}

			for (String s : list)
			{
				SqlMarkup markup = new SqlMarkup                                                                         // XXX: template
				(
					"set statistics index",
					s
				);

				UpdateSqlRequest request = new UpdateSqlRequest(markup);
				transaction.execute(request);
			}

			//
			transaction.commit();
		}
		catch (Exception e)
		{
			LOG.error(e);
		}
	}


	private static void sweep()
	{
/*
		for (DatabaseTable table : DatabaseManager.TABLES)
		{
			try (DatabaseInteraction.SequenceInteraction statement = DatabaseInteraction.sequence())
			{
				SqlRequest sql = new SqlRequest(SqlType.QUERY);

				sql.text
				(
					"select",
						"count(*)",
					"from",
						table.NAME
				);

				statement.execute(sql);
			}
			catch (Exception e)
			{
				Log.exception(CLASS, e);
			}
		}
*/
	}

}
