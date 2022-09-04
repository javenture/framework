package net.javenture.framework.firebird;


import net.javenture.framework.identifier.IntIdentifier;
import net.javenture.framework.log.Log;


final public class DatabaseConfiguration
{
	//
	//final private static Log LOG = Log.instance(DatabaseConfiguration.class);
	//final private static Object LOCK = new Object();


	//
	private DatabaseConfiguration()
	{
	}


	//
/*
	public static void process(DatabaseTable table)
	{
		// XXX: application ?




		SqlType type = Management.SqlTemplates.LIST.type();


		//
		Status status = new Status();

		synchronized (LOCK)
		{
			boolean difference = false;


			boolean change = false;
			//int number = 0;
			ArrayList<ConfigurationFieldEntry> list = entries(table);


			try (IDatabaseTransaction transaction = DatabaseTransaction.batch())
			{
				IntIdentifier id = SqlTemplates.SEARCH.execute(table.NAME);

				if (!id.unknown())
				{
					ConfigurationFieldGroup group = new ConfigurationFieldGroup(id);
					status.add(group.load());
					ArrayList<ConfigurationFieldEntry> entries = group.entries();
					ArrayList<ConfigurationFieldEntry> entries2 = new ArrayList<>();




					if (!entries.isEmpty())
					{
						SqlTemplate template = Management.SqlTemplates.LIST;
						SqlRequest request = new SqlRequest(template, table.NAME);
						SqlResponse response = transaction.execute(request);

						if (response.RESULT.success())
						{
							Dataset dataset = response.DATASET;

							while (dataset.next())
							{
								int number = dataset.index();


								ConfigurationFieldEntry entry = new ConfigurationFieldEntry();

							}





							if (dataset.size() == entries.size())
							{
								for (int i = 0; i < entries.size(); i++)
								{
									ConfigurationFieldEntry entry = entries.get(i);

									dataset.

								}


							}
							else
							{
								// XXX: exception
							}

							while (dataset.next())
							{
								String rdb$field_name = dataset.getString(template.COLUMN_RDB$FIELD_NAME);
								String rdb$field_source = dataset.getString(COLUMN_RDB$FIELD_SOURCE);



							}
						}



					}
					else
					{
						// XXX: exception
					}


				}
				else
				{
					// XXX: exception
				}
			}
			catch (Exception e)
			{
				Log.exception(CLASS, e);
			}


			//
			if (change)
			{
					ConfigurationTableItem itemTable = new ConfigurationTableItem();
					itemTable.setName(table.NAME);
					itemTable.setNumber(number);
					status.add(itemTable.create());

					//
					if (status.last().ok())
					{
						IntIdentifier id = itemTable.identifier();
						ConfigurationFieldGroup group = new ConfigurationFieldGroup(id);






						status.add(group.save());
					}



			}
		}





	}
*/




	//
	final private static class SqlTemplates
	{
		final private static Log LOG = Log.instance(SqlTemplates.class);

		/*
		 * Search
		 */
		final private static ASqlTemplate.AObjectQuery<IntIdentifier> SEARCH = new ASqlTemplate.AObjectQuery<>()
		{
			//
			@Override
			protected SqlMarkup _markup()
			{
				return new SqlMarkup
				(
/*
					"select",
						"first 1",                                                             // XXX: row
						new SqlReceiver(0, ConfigurationTableItem.FIELD_ID),
					"from",
						ConfigurationTableItem.TABLE,
					"where",
						new SqlSender(0, ConfigurationTableItem.FIELD_NAME), "=", "?",
					"order by",
						ConfigurationTableItem.FIELD_NUMBER + " " + "desc"
*/
				);
			}

			@Override
			protected IntIdentifier init()
			{
				// XXX


				throw new UnsupportedOperationException();
			}

			@Override
			public IntIdentifier execute(Object... values)
			{
				try (DatabaseTransaction transaction = DatabaseTransaction.read())
				{
					SqlParameters parameters = new SqlParameters(values);
					ISqlResponse response = transaction.execute(this, parameters);
					IDataset dataset = response.dataset();

					if (dataset.first()) return dataset.getFactoryObject(0, IntIdentifier.FACTORIES);

					// XXX: del
					{
						DatabaseConfigurationGroup group = new DatabaseConfigurationGroup(null);


						for (DatabaseConfigurationGroup.Entry entry : group)
						{

						}


						DatabaseConfigurationGroup.Entry entry = group.entry();
						group.add(entry);
					}




					transaction.commit();
				}
				catch (Exception e)
				{
					LOG.error(e);
				}

				return IntIdentifier.UNKNOWN;
			}
		};
	}





}
