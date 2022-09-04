package net.javenture.framework.netty;


import net.javenture.framework.annotation.MultiThread;
import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.util.UtcTimeUtil;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;


/*
	2020/09/30
 */
@MultiThread
final public class NettySession<I extends INettySessionId>
{
	//
	final private static long DURATION_DEFAULT = 30 * 60 * 1000;


	//
	final private I ID;
	final private AtomicBoolean ACTIVITY;
	final private AtomicBoolean UNKNOWN;
	final private AtomicLong FIRST;
	final private AtomicLong LAST;
	final private AtomicLong DURATION;
	final private AtomicInteger COUNT;
	final private AtomicReference[] PARAMETERS;                                       // XXX: clear after destroy ?


	//
	NettySession(@NullDisallow I id, int parameters)                                                                         // XXX: ?
	{
		long ms = UtcTimeUtil.ms();

		ID = id;
		ACTIVITY = new AtomicBoolean(true);
		UNKNOWN = new AtomicBoolean(true);
		FIRST = new AtomicLong(ms);
		LAST = new AtomicLong(ms);
		DURATION = new AtomicLong(DURATION_DEFAULT);
		COUNT = new AtomicInteger(0);
		PARAMETERS = new AtomicReference[parameters];

		//
		for (int i = 0; i < parameters; i++) PARAMETERS[i] = new AtomicReference();
	}


	//
	public I id()
	{
		return ID;
	}


	public boolean activity()
	{
		return ACTIVITY.get();
	}


	public void invalidate()
	{
		ACTIVITY.set(false);
	}


	public boolean unknown()
	{
		return UNKNOWN.get();
	}


	public void confirm()
	{
		UNKNOWN.set(false);
	}


	public long first()
	{
		return FIRST.get();
	}


	public long last()
	{
		return LAST.get();
	}


	public void refresh()
	{
		LAST.set(UtcTimeUtil.ms());
	}


	public long duration()
	{
		return DURATION.get();
	}


	public void duration(long value)
	{
		DURATION.set(value);
	}


	public int count()
	{
		return COUNT.get();
	}


	public void increment()
	{
		COUNT.incrementAndGet();
	}


	@SuppressWarnings("unchecked")
	public @NullAllow <V> V get(Parameter<V> parameter)
	{
		return (V) PARAMETERS[parameter.INDEX].get();
	}


	@SuppressWarnings("unchecked")
	public @NullAllow <V> V get(Parameter<V> parameter, @NullAllow V init)
	{
		V value = (V) PARAMETERS[parameter.INDEX].get();

		return value != null ? value : init;
	}


	public <V> void set(Parameter<V> parameter, @NullAllow V value)
	{
		PARAMETERS[parameter.INDEX].set(value);
	}


	public void clear()
	{
		ACTIVITY.set(false);
		UNKNOWN.set(false);
		FIRST.set(0);
		LAST.set(0);
		DURATION.set(0);
		COUNT.set(0);

		//
		for (AtomicReference parameter : PARAMETERS) parameter.set(null);
	}


	//
	final public static class Parameter<V> // ! <V>
	{
		//
		final private int INDEX;

		//
		public Parameter(int index)
		{
			INDEX = index;
		}
	}

}
