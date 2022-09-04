package net.javenture.framework.firebird;


import net.javenture.framework.log.Log;


/*
	XXX: FBStatisticsManager ?
 */
final class Management
{
	final private static Log LOG = Log.instance(Management.class);


	/*
		update RDB$FIELDS set
		RDB$FIELD_LENGTH = 240,
		RDB$CHARACTER_LENGTH = 60
		where RDB$FIELD_NAME = 'RDB$5'
	 */



	public static void check(DatabaseTable table)
	{
		// 1. сравнение версии приложения и версии сохраненной в базе

		// 2. сравнение набора таблиц приложения и базы

		// 3. проверка каждой таблицы


/*
		try (IDatabaseTransaction transaction = DatabaseTransaction.update())                          // XXX: batch ?
		{
			SqlTemplate template1 = SqlTemplates.LIST;
			SqlParameter parameter = new SqlParameter(table.NAME);
			SqlRequest request1 = new SqlRequest(template1, parameter);
			SqlResponse response1 = transaction.execute(request1);

			if (response1.RESULT.success())
			{
				final int COLUMN_RDB$FIELD_NAME = 0;
				final int COLUMN_RDB$FIELD_SOURCE = 1;
				Dataset dataset1 = response1.DATASET;

				if (dataset1.count() == table.FIELDS.length)
				{
					for (int i = 0; i < table.FIELDS.length; i++)
					{
						DatabaseField field = table.FIELDS[i];

						dataset1.index(i);
						String name = dataset1.getString(COLUMN_RDB$FIELD_NAME, "");

						if (field.NAME.equals(name))
						{
							SqlTemplate template2 = SqlTemplates.SEARCH2;
							SqlParameter parameter2 = new SqlParameter(dataset1.getString(COLUMN_RDB$FIELD_SOURCE));
							SqlRequest request2 = new SqlRequest(template2, parameter2);
							SqlResponse response2 = transaction.execute(request2);

							if (response2.RESULT.success())
							{
								final int COLUMN_RDB$FIELD_TYPE = 0;
								final int COLUMN_RDB$CHARACTER_LENGTH = 1;
								Dataset dataset2 = response2.DATASET;

								if (dataset2.first())
								{
									int rdb$field_type = dataset2.getInteger(COLUMN_RDB$FIELD_TYPE, DatabaseType.UNKNOWN.FIREBIRD_RDB$TYPE);
									int rdb$character_length = dataset2.getInteger(COLUMN_RDB$CHARACTER_LENGTH, 0);

									// if (field.config().FIREBIRD == rdb$field_type && )

								}
								else
								{
									// XXX

									break;
								}

							}


						}
						else
						{
							// XXX: ?

							break;
						}

					}


				}
				else
				{

				}




			}


		}
		catch (Exception e)
		{
			LogEvent event = LogEvent.error()
			.method("check")
			.exception(e);

			LOG.write(event);
		}
*/


	}











/*
	final static class SqlTemplates
	{
		final private static ASqlTemplate<String> SEARCH = new ASqlTemplate.AQuery<String>()                                    // XXX: SqlTemplate.query() ?
		{
			final private static int COLUMN_RDB$FIELD_SOURCE = 0;

			@Override
			protected void _markup(SqlMarkup markup)
			{
				markup.add
				(
					"select",
						RdbRelationFields.FIELD_RDB$FIELD_SOURCE.receiver(0),
					"from",
						RdbRelationFields.TABLE,
					"where",
						RdbRelationFields.FIELD_RDB$RELATION_NAME.sender(0), "=", "?",
					"and",
						RdbRelationFields.FIELD_RDB$FIELD_NAME.sender(1), "=", "?"
				);
			}

			@Override
			public String execute(Object ... parameters)
			{
				try (IDatabaseTransaction transaction = DatabaseTransaction.sequence())
				{
					SqlRequest request = new SqlRequest(this, parameters);
					SqlResponse response = transaction.execute(request);

					if (response.RESULT.success())
					{
						Dataset dataset = response.DATASET;

						if (dataset.first()) return dataset.getString(COLUMN_RDB$FIELD_SOURCE);
					}
				}
				catch (Exception e)
				{
					Log.exception(CLASS, e);
				}

				return ;



			}
		};









		final private static ASqlTemplate<String> SEARCH2 = new ASqlTemplate.AQuery<String>()                               // XXX: SqlTemplate.query() ?
		{
			final private static int COLUMN_RDB$FIELD_TYPE = 0;
			final private static int COLUMN_RDB$CHARACTER_LENGTH = 1;

			@Override
			protected void _markup(SqlMarkup markup)
			{
				markup.add
				(
					"select",
						RdbFields.FIELD_RDB$FIELD_TYPE.receiver(0),
						",",
						RdbFields.FIELD_RDB$CHARACTER_LENGTH.receiver(1),
					"from",
						RdbFields.TABLE,
					"where",
						RdbFields.FIELD_RDB$FIELD_NAME.sender(0), "=", "?"
				);
			}
		};







		final static ASqlTemplate LIST0 = new ASqlTemplate.AQuery()                                         // XXX: SqlTemplate.query() ?
		{
			final static int COLUMN_RDB$RELATION_NAME = 0;

			@Override
			protected void _markup(SqlMarkup markup)
			{
				markup.add
				(
					"select",
						"distinct",
						RdbRelationFields.FIELD_RDB$RELATION_NAME.receiver(0),
					"from",
						RdbRelationFields.TABLE,
					"where",
						RdbRelationFields.FIELD_RDB$SYSTEM_FLAG + "=" + "0"
				);
			}




			// XXX: execute
		};





		final private static ASqlTemplate LIST = new ASqlTemplate.AQuery()                                         // XXX: SqlTemplate.query() ?
		{
			final private static int COLUMN_RDB$FIELD_NAME = 0;
			final private static int COLUMN_RDB$FIELD_SOURCE = 1;

			@Override
			protected void _markup(SqlMarkup markup)
			{
				markup.add
				(
					"select",
						RdbRelationFields.FIELD_RDB$FIELD_NAME.receiver(0), // "ID", ...
						",",
						RdbRelationFields.FIELD_RDB$FIELD_SOURCE.receiver(1), // "RDB$1", ...
					"from",
						RdbRelationFields.TABLE,
					"where",
						RdbRelationFields.FIELD_RDB$RELATION_NAME.sender(0), "=", "?", // "USR"
					"order by",
						RdbRelationFields.LABEL_RDB$FIELD_POSITION
				);
			}
		};





		final private static ASqlTemplate REORDER = new ASqlTemplate.AUpdate()                            // XXX: SqlTemplate.update() ?
		{
			@Override
			protected void _markup(SqlMarkup markup)
			{
				markup.add
				(
					"update",
						RdbRelationFields.TABLE,
					"set",
						RdbRelationFields.FIELD_RDB$FIELD_POSITION.sender(0), "=", "?",
					"where",
						RdbRelationFields.FIELD_RDB$FIELD_SOURCE.sender(1), "=", "?"
				);
			}
		};


	}
*/




	// RDB$FIELDS
	final private static class RdbFields
	{
		final public static String TABLE_NAME = "RDB$FIELDS";

		final public static StringDatabaseField FIELD_RDB$FIELD_NAME = new StringDatabaseField
		(
			TABLE_NAME,
			-1,
			"RDB$FIELD_NAME",
			"",
			31,
			ADatabaseField.Option.STRICT
		);

		final public static IntDatabaseField FIELD_RDB$FIELD_LENGTH = new IntDatabaseField
		(
			TABLE_NAME,
			-1,
			"RDB$FIELD_LENGTH",
			-1,
			ADatabaseField.Option.STRICT
		);

		final public static IntDatabaseField FIELD_RDB$FIELD_TYPE = new IntDatabaseField
		(
			TABLE_NAME,
			-1,
			"RDB$FIELD_TYPE",
			-1,
			ADatabaseField.Option.STRICT
		);

		final public static IntDatabaseField FIELD_RDB$CHARACTER_LENGTH = new IntDatabaseField
		(
			TABLE_NAME,
			-1,
			"RDB$CHARACTER_LENGTH",
			-1,
			ADatabaseField.Option.STRICT
		);

		final public static DatabaseFields FIELDS = new DatabaseFields
		(
			FIELD_RDB$FIELD_NAME,
			FIELD_RDB$FIELD_LENGTH,
			FIELD_RDB$FIELD_TYPE,
			FIELD_RDB$CHARACTER_LENGTH
		);

		final public static DatabaseTable TABLE = new DatabaseTable
		(
			TABLE_NAME,
			FIELDS
		);
	}


	// RDB$RELATION_FIELDS
	final private static class RdbRelationFields
	{
		final public static String TABLE_NAME = "RDB$RELATION_FIELDS";

		final public static String LABEL_RDB$FIELD_NAME = "RDB$FIELD_NAME";
		final public static String LABEL_RDB$RELATION_NAME = "RDB$RELATION_NAME";
		final public static String LABEL_RDB$FIELD_SOURCE = "RDB$FIELD_SOURCE";
		final public static String LABEL_RDB$FIELD_POSITION = "RDB$FIELD_POSITION";
		final public static String LABEL_RDB$SYSTEM_FLAG = "RDB$SYSTEM_FLAG";

		final public static StringDatabaseField FIELD_RDB$FIELD_NAME = new StringDatabaseField
		(
			TABLE_NAME,
			-1,
			LABEL_RDB$FIELD_NAME,
			"",
			31,
			ADatabaseField.Option.STRICT
		);

		final public static StringDatabaseField FIELD_RDB$RELATION_NAME = new StringDatabaseField
		(
			TABLE_NAME,
			-1,
			LABEL_RDB$RELATION_NAME,
			"",
			31,
			ADatabaseField.Option.STRICT
		);

		final public static StringDatabaseField FIELD_RDB$FIELD_SOURCE = new StringDatabaseField
		(
			TABLE_NAME,
			-1,
			LABEL_RDB$FIELD_SOURCE,
			"",
			31,
			ADatabaseField.Option.STRICT
		);

		final public static IntDatabaseField FIELD_RDB$FIELD_POSITION = new IntDatabaseField
		(
			TABLE_NAME,
			-1,
			LABEL_RDB$FIELD_POSITION,
			-1,
			ADatabaseField.Option.STRICT
		);

		final public static IntDatabaseField FIELD_RDB$SYSTEM_FLAG = new IntDatabaseField
		(
			TABLE_NAME,
			-1,
			LABEL_RDB$SYSTEM_FLAG,
			-1,
			ADatabaseField.Option.STRICT
		);

		final public static DatabaseFields FIELDS = new DatabaseFields
		(
			FIELD_RDB$FIELD_NAME,
			FIELD_RDB$RELATION_NAME,
			FIELD_RDB$FIELD_SOURCE,
			FIELD_RDB$FIELD_POSITION,
			FIELD_RDB$SYSTEM_FLAG
		);

		final public static DatabaseTable TABLE = new DatabaseTable
		(
			TABLE_NAME,
			FIELDS
		);
	}

}
