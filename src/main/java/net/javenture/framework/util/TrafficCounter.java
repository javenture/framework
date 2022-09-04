package net.javenture.framework.util;


import net.javenture.framework.annotation.SingleThread;

import java.util.ArrayDeque;


@SingleThread
final public class TrafficCounter
{
	final private ArrayDeque<Entry> DEQUE;


	//
	public TrafficCounter()
	{
		DEQUE = new ArrayDeque<>(2);
	}


	//
	public void update(int count)
	{
		long second = UtcTimeUtil.ms() / 1_000;
		Entry last = DEQUE.peekLast();

		if (last != null && last.SECOND == second) last.update(count);
		else DEQUE.addLast(new Entry(second, count));
	}


/*
	public int value()                          // XXX: ?
	{
		return value;
	}
*/


	//
	final private static class Entry
	{
		final private long SECOND;

		private int value;

		//
		private Entry(long second)
		{
			this(second, 0);
		}

		private Entry(long second, int init)
		{
			SECOND = second;
			value = init;
		}

		private void update(int i)
		{
			value += i;
		}

		private int value()
		{
			return value;
		}
	}

}
