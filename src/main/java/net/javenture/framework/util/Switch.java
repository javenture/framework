package net.javenture.framework.util;


import net.javenture.framework.annotation.MultiThread;

import java.util.concurrent.atomic.AtomicBoolean;


/*
	2017/10/08
 */
@MultiThread
final public class Switch
{
	final private boolean STRICT;
	final private AtomicBoolean ACTIVITY;


	//
	public Switch(boolean strict, boolean init)
	{
		STRICT = strict;
		ACTIVITY = new AtomicBoolean(init);
	}


	//
	public boolean activity()
	{
		return ACTIVITY.get();
	}


	public void activity(boolean value)
	{
		if (value) enable();
		else disable();
	}


	public void enable()
	{
		if (STRICT)
		{
			if (!ACTIVITY.compareAndSet(false, true)) throw new IllegalStateException("Switch already enabled");
		}
		else
		{
			ACTIVITY.set(true);
		}
	}


	public void disable()
	{
		if (STRICT)
		{
			if (!ACTIVITY.compareAndSet(true, false)) throw new IllegalStateException("Switch already disabled");
		}
		else
		{
			ACTIVITY.set(false);
		}
	}

}
