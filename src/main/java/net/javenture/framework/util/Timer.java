package net.javenture.framework.util;


import net.javenture.framework.log.ILog;
import net.javenture.framework.log.LogAttachment;


/*
	XXX: check
 */
final public class Timer implements ILog
{
	final private static Point BLANK = new Point(false);


	//
	private Point start = BLANK;
	private Point stop = BLANK;


	//
	public Timer()
	{
	}


	//
	public void init()
	{
		start = BLANK;
		stop = BLANK;
	}


	public void start()
	{
		start = new Point(true);
	}


	public void stop()
	{
		stop = new Point(true);
	}


	public long runtime()
	{
		Validator.state(start.ACTIVITY, "Timer.start is not active");
		Validator.state(stop.ACTIVITY, "Timer.stop is not active");

		return stop.NANOS - start.NANOS;
	}


	@Override
	public LogAttachment log()
	{
		boolean activity = start.ACTIVITY && stop.ACTIVITY;
		long runtime = stop.NANOS - start.NANOS;

		//
		LogAttachment result = new LogAttachment();
		result.add(start, "start");
		result.add(stop, "stop");
		result.add(activity ? runtime : "?", "runtime (ns)");
		result.add(activity ? runtime / 1_000_000 : "?", "runtime (ms)");
		result.add(activity ? runtime / 1_000_000_000 : "?", "runtime (s)");

		return result;
	}


	//
	final private static class Point implements ILog
	{
		final private boolean ACTIVITY;
		final private long MILLIS;
		final private long NANOS;

		private Point(boolean activity)
		{
			ACTIVITY = activity;
			MILLIS = System.currentTimeMillis();
			NANOS = System.nanoTime();
		}

		@Override
		public LogAttachment log()
		{
			LogAttachment result = new LogAttachment();
			result.add(ACTIVITY, "ACTIVITY");
			result.add(MILLIS, "MILLIS");

			return result;
		}
	}

}
