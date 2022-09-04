package net.javenture.framework.firebird;


import net.javenture.framework.annotation.MultiThread;
import net.javenture.framework.annotation.OwnerThread;
import net.javenture.framework.util.Switch;
import net.javenture.framework.util.Task;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;


/*
	2019/08/21
 */
@MultiThread
final class ConnectorPool
	implements Iterable<DatabaseConnector>
{
	// XXX: setTransactionIsolation(int level) ?
	// XXX: получение соединения с указанием времени максимального ожидания, по прошествии которого возвращать ошибку (0 - возврат ошибки если соединение не удается получиться сразу же)



	final private static String DRIVER = "org.firebirdsql.jdbc.FBDriver";
	final private static String PROTOCOL = "jdbc:firebirdsql:";


	//
	final private DatabaseInstance INSTANCE;
	final private int COUNT;
	final private Switch SWITCH;
	final private Interaction INTERACTION;
	final private CopyOnWriteArrayList<DatabaseConnector> CONNECTORS;


	//
	ConnectorPool(DatabaseInstance instance, int count)
	{
		INSTANCE = instance;
		COUNT = count;
		SWITCH = new Switch(true, false);
		INTERACTION = new Interaction();
		CONNECTORS = new CopyOnWriteArrayList<>();

		INTERACTION.start();
	}


	//
	void enable()
	{
		SWITCH.enable();
		INTERACTION.next();
	}


	void disable()
	{
		SWITCH.disable();

		for (DatabaseConnector connector : CONNECTORS) connector.disable();

		INTERACTION.next();
	}


	void next()
	{
		INTERACTION.next();
	}


	@Override
	public Iterator<DatabaseConnector> iterator()
	{
		return CONNECTORS.iterator();
	}


	@Override
	protected void finalize()
	{
		/*
			https://stackoverflow.com/questions/47762986/replacing-finalize-in-java
			https://docs.oracle.com/javase/9/docs/api/java/lang/ref/Cleaner.html
			https://hackernoon.com/java-lang-object-finalize-is-finally-deprecated-f99df40fa71
			http://www.enyo.de/fw/notes/java-finalization-revisited.html
		 */

		System.out.println("POOL.finalize()");                      // XXX: del



		// XXX: ?

		if (SWITCH.activity()) disable();

		INTERACTION.stop();
	}


	//
	//
	@MultiThread
	final private class Interaction
		extends Task
	{
		//
		final private static long PERIODITY = 60 * 1000; // 1 min

		//
		private Interaction()
		{
			super("ConnectionPool.Interaction", PERIODITY);
		}

		//
		@Override
		@OwnerThread
		public void execute()
		{
			// close
			for (int i = 0; i < CONNECTORS.size(); i++)
			{
				DatabaseConnector connector = CONNECTORS.get(i);

				if
				(
					!connector.activity() && connector.lock()
					||
					connector.expired() && connector.lock()
				)
				{
					//System.out.println("ConnectionPool.Interaction.close: " + entry);                      // XXX: del


					CONNECTORS.remove(i);
					DatabaseConnector.close(connector);
					next();

					break; // !
				}
			}

			// open
			if (SWITCH.activity())
			{
				while (true)
				{
					if (CONNECTORS.size() < COUNT)
					{
						DatabaseConnector connector = DatabaseConnector.open(INSTANCE);

						if (connector != null)
						{
							connector.idle();
							CONNECTORS.add(connector);
							//INSTANCE.next();                                                       // XXX: MANAGER


							//System.out.println("ConnectionPool.Interaction.add: " + entry);                      // XXX: del
						}
						else
						{
							break;
						}
					}
					else
					{
						break;
					}
				}
			}
		}

		@Override
		public void exit()
		{
			//System.out.println("ConnectionPool.Interaction.exit 1");                                            // XXX: del


			while (true)
			{
				for (int i = 0; i < CONNECTORS.size(); i++)
				{
					DatabaseConnector connector = CONNECTORS.get(i);

					if (connector.lock())
					{
						//System.out.println("ConnectionPool.Interaction.exit: " + entry);                      // XXX: del


						CONNECTORS.remove(i);
						DatabaseConnector.close(connector);

						break; // !
					}
				}

				//
				if (CONNECTORS.size() != 0)
				{
					try
					{
						Thread.sleep(100);
					}
					catch (InterruptedException ignore)
					{
					}
				}
				else
				{
					break;
				}
			}


			//System.out.println("ConnectionPool.Interaction.exit 2");                                            // XXX: del
		}
	}

}
