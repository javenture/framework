package net.javenture.framework.util;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;


/*
	2018/12/25
 */
final public class Validator
{
	//
	private Validator()
	{
	}


	//
	public static @NullAllow <T> T ifNotNull(@NullAllow T object, @NullAllow T init)
	{
		return object != null ? object : init;
	}


	public static void notNull(@NullAllow Object object)
	{
		if (object == null) throw new NullPointerException();
	}


	public static void notNull(@NullAllow Object object, @NullAllow String message)
	{
		if (object == null) throw new NullPointerException(message);
	}


	public static void notNull(@NullAllow Object object, Object... messages)
	{
		if (object == null) throw new NullPointerException(message(messages));
	}


	public static void argument(boolean expression, @NullAllow String message)
	{
		if (!expression) throw new IllegalArgumentException(message);
	}


	public static void argument(boolean expression, Object... messages)
	{
		if (!expression) throw new IllegalArgumentException(message(messages));
	}


	public static void state(boolean expression, @NullAllow String message)
	{
		if (!expression) throw new IllegalStateException(message);
	}


	public static void state(boolean expression, Object... messages)
	{
		if (!expression) throw new IllegalStateException(message(messages));
	}


	public static void instance(Object object, @NullDisallow Class<?> cl)
	{
		if (!cl.isInstance(object)) throw new ClassCastException("class mismatch: found (" + ClassUtil.name(object) + "); expected (" + ClassUtil.name(cl) + ")");
	}


	public static void condition(boolean expression)
		throws Exception
	{
		if (!expression) throw new IllegalStateException();
	}


	public static void condition(boolean expression, String message)
		throws Exception
	{
		if (!expression) throw new IllegalStateException(message);
	}


	public static void condition(boolean expression, Object... messages)
		throws Exception
	{
		if (!expression) throw new IllegalStateException(message(messages));
	}


	private static String message(Object... objects)
	{
		return objects.length != 0 ? StringUtil.combine(objects) : "";
	}

}
