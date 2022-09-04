package net.javenture.framework.util;


import net.javenture.framework.annotation.MultiThread;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;


/*
	2019/12/21
 */
@MultiThread
abstract public class Task
	implements Runnable
{
	// XXX: file://localhost/D:/Java8/docs/api/java/lang/Thread.UncaughtExceptionHandler.html ?

	//
	final private static AtomicInteger GENERATOR = new AtomicInteger(0);
	final private static CopyOnWriteArrayList<Task> LIST = new CopyOnWriteArrayList<>();
	final private static long TIMEOUT = 100;

	// IDLE -> ACTIVE -> DONE
	public enum Stage
	{
		//
		IDLE,
		ACTIVE,
		DONE
	}


	//
	final private String NAME;
	final private long PERIODITY;
	final private int ID;
	final private AtomicReference<Stage> STAGE;
	final private Object LOCK;
	final private AtomicBoolean NEXT;
	final private Thread THREAD;


	//
	protected Task(String name, long periodity)
	{
		Validator.argument(periodity >= 0, "[periodity] (", periodity, ") < 0");

		//
		NAME = name;
		PERIODITY = periodity;
		ID = GENERATOR.getAndIncrement();
		STAGE = new AtomicReference<>(Stage.IDLE);
		LOCK = new Object();
		NEXT = new AtomicBoolean(false);
		THREAD = new Thread(this);

		LIST.add(this);
	}


	//
	abstract protected void execute();
	abstract protected void exit();


	//
	final public String name()
	{
		return NAME;
	}


	final public long periodity()
	{
		return PERIODITY;
	}


	final public int id()
	{
		return ID;
	}


	final public Stage stage()
	{
		return STAGE.get();
	}


	final public void start()
	{
		if (!STAGE.compareAndSet(Stage.IDLE, Stage.ACTIVE)) throw new IllegalStateException();

		THREAD.start();
	}


	final public void stop()
	{
		if (!STAGE.compareAndSet(Stage.ACTIVE, Stage.DONE)) throw new IllegalStateException();

		next();
	}


	final public void next()
	{
		NEXT.set(true);

		synchronized (LOCK)
		{
			LOCK.notify();
		}
	}


	private void sleep(long timeout)
	{
		if (!NEXT.getAndSet(false))
		{
			synchronized (LOCK)
			{
				try
				{
					LOCK.wait(timeout);
				}
				catch (InterruptedException ignore)
				{
				}
			}

			NEXT.set(false);
		}
	}


	@Override
	final public void run()
	{
		while (true)
		{
			Stage stage = STAGE.get();

			if (stage == Stage.IDLE)
			{
				sleep(1000);
			}
			else if (stage == Stage.ACTIVE)
			{
				execute();
				sleep(PERIODITY);
			}
			else if (stage == Stage.DONE)
			{
				exit();

				for (int i = 0; i < LIST.size(); i++)
				{
					Task task = LIST.get(i);

					if (task.ID == this.ID)
					{
						LIST.remove(i);

						break;
					}
				}

				break;
			}
		}
	}


	//
	public static void list()
	{
		for (Task task : LIST)
		{
			System.out.println("ID: " + task.ID + "; NAME: " + task.NAME);
		}



	}


	public static void stop(int id)
	{
		for (Task task : LIST)
		{
			if (task.ID == id)
			{
				task.STAGE.set(Stage.DONE);
				task.next();

				break;
			}
		}
	}

}
