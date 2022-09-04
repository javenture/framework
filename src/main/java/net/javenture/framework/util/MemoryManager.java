package net.javenture.framework.util;


import net.javenture.framework.annotation.MultiThread;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;


@MultiThread
final public class MemoryManager
{
	final private static int UNLIMITED = -1;
	final private static Object OBJECT = new Object();


	// XXX: MemoryManager.getDefault() & MemoryManager.setDefault(...) !


	//
	final public int PAGE; // byte
	final public int COUNT;

	final private ReferenceQueue<byte[]> QUEUE;
	final private ConcurrentLinkedDeque<WeakReference<byte[]>> REFERENCES;
	final private ConcurrentLinkedDeque<Object> AVAILABLE;
	final private Task TASK;


	//
	public MemoryManager(int page)
	{
		PAGE = page;
		COUNT = UNLIMITED;

		QUEUE = new ReferenceQueue<>();
		REFERENCES = new ConcurrentLinkedDeque<>();
		AVAILABLE = new ConcurrentLinkedDeque<>();
		TASK = null;
	}


	public MemoryManager(int page, int count)
	{
		Validator.argument(count > 0, "[count] (", count, ") <= 0");

		//
		PAGE = page;
		COUNT = count;

		QUEUE = new ReferenceQueue<>();
		REFERENCES = new ConcurrentLinkedDeque<>();
		AVAILABLE = new ConcurrentLinkedDeque<>();

		for (int i = 0; i < count; i++) AVAILABLE.addLast(OBJECT);

		//
		TASK = new Task("", 0)
		{
			@Override
			public void execute()
			{
				try
				{
					if (QUEUE.remove() != null)
					{
						System.out.println("QUEUE.remove()");


						AVAILABLE.addLast(OBJECT);
						clean();
					}
				}
				catch (InterruptedException ignore)
				{
				}
			}

			@Override
			public void exit()
			{
			}

			private void clean()
			{
				// XXX: timeout


				Iterator<WeakReference<byte[]>> iterator = REFERENCES.iterator();

				while (iterator.hasNext())
				{
					WeakReference<byte[]> reference = iterator.next();

					if (reference.get() == null)
					{
						System.out.println("iterator.remove()");


						iterator.remove();
					}
				}
			}
		};

		TASK.start();
	}


	//
	public boolean available()
	{
		return !AVAILABLE.isEmpty();
	}


	public byte[] allocate()
	{
		if (COUNT == UNLIMITED)
		{
			return new byte[PAGE];
		}
		else
		{
			if (AVAILABLE.pollLast() != null)
			{
				byte[] bytes = new byte[PAGE];
				REFERENCES.addLast(new WeakReference<>(bytes, QUEUE));

				return bytes;
			}
			else
			{
				return null;
			}
		}
	}

}
