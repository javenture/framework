package net.javenture.framework.util;


import net.javenture.framework.annotation.NullAllow;


/*
	2019/10/01
 */
public interface IParser<V>
{
	//
	@NullAllow V init();
	boolean nullable();
	boolean trim();
	Report<V> parse(@NullAllow V value);
	Report<V> parse(@NullAllow CharSequence source);


	//
	final class Report<V>
	{
		//
		final public boolean EXISTS;
		final public boolean DEFINED;
		final public @NullAllow V VALUE;

		//
		public Report(boolean exists, boolean defined, @NullAllow V value)
		{
			EXISTS = exists;
			DEFINED = defined;
			VALUE = value;
		}

		//
		public boolean exists()
		{
			return EXISTS;
		}

		public boolean defined()
		{
			return DEFINED;
		}

		public @NullAllow V value()
		{
			return VALUE;
		}
	}

}
