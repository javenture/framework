package net.javenture.framework.firebird;


import net.javenture.framework.annotation.CurrentThread;
import net.javenture.framework.annotation.ForeingThread;
import net.javenture.framework.annotation.MultiThread;
import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.OwnerThread;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.util.ConstUtil;
import net.javenture.framework.util.IConst;
import net.javenture.framework.util.Switch;
import net.javenture.framework.util.Task;
import net.javenture.framework.util.Uuid;
import net.javenture.framework.util.Validator;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;


/*
	2021/06/02
 */
@MultiThread
final public class DatabaseInstance
{
	// XXX: DatabaseStat ?
	// XXX: readonly ?


	//
	public enum Type
		implements IConst<Type>
	{
		//
		UNKNOWN(-1),
		ORIGINAL(0),
		REPLICA(1);

		//
		final public static Factories<Type> FACTORIES = ConstUtil.factories(values(), object -> object instanceof Type);

		//
		final private int ID;

		//
		Type(int id)
		{
			ID = id;
		}

		//
		@Override
		public boolean defined()
		{
			return this.ID != UNKNOWN.ID;
		}

		@Override
		public int id()
		{
			return ID;
		}

		@Override
		public Factories<Type> factories()
		{
			return FACTORIES;
		}

		@Override
		public boolean equals(Type value)
		{
			return this == value;
		}

		@Override
		public String toString()
		{
			return "" + ID;
		}
	}


	//
	final private DatabaseProperties PROPERTIES;
	final private int CONNECTION;
	final private Object LOCK;
	final private Switch SWITCH;
	final private ConnectorPool POOL;
	final TransactionManager MANAGER;


	//
	public DatabaseInstance(DatabaseProperties properties, int connection)
	{
		PROPERTIES = properties;
		CONNECTION = connection;
		LOCK = new Object();
		SWITCH = new Switch(true, false);
		POOL = new ConnectorPool(this, connection);
		MANAGER = new TransactionManager();

		MANAGER.start();
	}


	//
	public DatabaseProperties properties()
	{
		return PROPERTIES;
	}


	public int connection()
	{
		return CONNECTION;
	}


	public void init()
		throws Exception            // XXX: ?
	{
		// XXX: create pool + check configuration

		Validator.condition(SWITCH.activity(), "");                              // XXX:




	}


	public boolean available()
	{
		return SWITCH.activity();
	}


	public void enable()
	{
		synchronized (LOCK)
		{
			SWITCH.enable();
			POOL.enable();
			MANAGER.next();
		}
	}


	public void disable()
	{
		synchronized (LOCK)
		{
			SWITCH.disable();
			POOL.disable();

			// XXX: MANAGER ?
		}
	}


	void queue(DatabaseTransaction transaction)
	{
		MANAGER.queue(transaction);
	}


/*                                                                                         // XXX: MANAGER
	@Override
	protected void finalize()                                                                                           // XXX: ?
	{
		// XXX: ?

		if (SWITCH.activity()) disable();

		MANAGER.stop();
	}
*/


	//
	@MultiThread
	final public static class Default
	{
		final private static AtomicReference<DatabaseInstance> INSTANCE = new AtomicReference<>(null);

		//
		public static @NullAllow DatabaseInstance get()
		{
			return INSTANCE.get();
		}

		public static void set(@NullAllow DatabaseInstance instance)
		{
			INSTANCE.set(instance);
		}
	}


	@CurrentThread
	final public static class Local                                                                        // XXX: up level ?
	{
		final private static ThreadLocal<DatabaseInstance> INSTANCE = new ThreadLocal<>();                                // XXX: set null after usage! (memory leak)

		//
		@CurrentThread
		static DatabaseInstance get()
		{
			return INSTANCE.get();
		}

		@CurrentThread
		@SuppressWarnings("ObjectEquality")
		public static void set(DatabaseInstance current, DatabaseInstance next)
		{
			DatabaseInstance instance = INSTANCE.get();
			Validator.state(instance == current, "[instance] (", instance, ") != [current] (", current, ")");
			INSTANCE.set(next);
		}
	}


	@MultiThread
	final class TransactionManager
		extends Task
	{
		//
		final private static long PERIODITY = 100;

		//
		@OwnerThread
		@ForeingThread
		final private ConcurrentLinkedQueue<DatabaseTransaction> QUEUE;

		//
		private TransactionManager()
		{
			super("DatabaseInstance.Manager", PERIODITY);

			QUEUE = new ConcurrentLinkedQueue<>();
		}

		@Override
		@OwnerThread
		protected void execute()
		{
			// DONE
			{
				Iterator<DatabaseTransaction> iterator = QUEUE.iterator();

				while (iterator.hasNext())
				{
					DatabaseTransaction transaction = iterator.next();

					if (transaction.stage() == DatabaseTransaction.Stage.DONE)
					{
						iterator.remove();
						DatabaseConnector connector = transaction.recycle();

						if (transaction.pool())
						{
							if (!connector.error())
							{
								connector.unlock();
								connector.idle();
								next();
							}
							else
							{
								connector.disable();
								connector.unlock();
								POOL.next();
							}
						}




						// XXX: del
						//System.out.println("transaction.DONE. init: " + (transaction.init - transaction.begin) / 1000000 + "ms; runtime: " + (transaction.end - transaction.begin) / 1000000 + "ms");
					}
				}
			}

			// IDLE
			{
				for (DatabaseTransaction transaction : QUEUE)
				{
					if (transaction.pool() && transaction.stage() == DatabaseTransaction.Stage.IDLE)
					{
						boolean assign = false;

						for (DatabaseConnector connector : POOL)
						{
							if (connector.activity() && connector.lock())
							{
								transaction.assign(connector);
								transaction.ready(); // !
								transaction.release();
								next();
								assign = true;

								break;
							}
						}

						if (!assign)
						{
							POOL.next();

							break;
						}
					}
				}
			}

			// ACTIVE
			// ???
		}

		@Override
		protected void exit()
		{
			//System.out.println("DatabaseInstance.Manager.exit 1");                                            // XXX: del


			while (true)
			{
				Iterator<DatabaseTransaction> iterator = QUEUE.iterator();

				while (iterator.hasNext())
				{
					DatabaseTransaction transaction = iterator.next();

					switch (transaction.stage())
					{
						case IDLE:
						{
							transaction.done();
							transaction.release();

							break;
						}

						case READY:
						case ACTIVE:
						{
							// wait

							break;
						}

						case DONE:
						{
							iterator.remove();
							DatabaseConnector connector = transaction.recycle();
							connector.disable();
							connector.unlock();                                                            // XXX: ?

							break;
						}
					}
				}

				//
				if (QUEUE.peek() != null)
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


			//System.out.println("DatabaseInstance.Manager.exit 2");                                            // XXX: del
		}

		@ForeingThread
		private void queue(DatabaseTransaction transaction)
		{
			//System.out.println("Manager.queue: " + transaction);                                            // XXX: del


			QUEUE.offer(transaction);
			next();
		}

		@ForeingThread
		boolean exists(Uuid value)
		{
			for (DatabaseTransaction transaction : QUEUE)
			{
				if (transaction.uuid().equals(value)) return true;
			}

			return false;
		}
	}

}
