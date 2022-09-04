package net.javenture.framework.util;


import net.javenture.framework.annotation.NullAllow;


/*
	2018/04/11
 */
final public class ClassUtil
{
	//
	final private static String NULL = "null";


	//
	private ClassUtil()
	{
	}


	//
	public static String name(@NullAllow Class<?> c)
	{
		return c != null ? c.getCanonicalName() : NULL;
	}


	public static String name(@NullAllow Class<?>... classes)
	{
		StringFragmenter fragmenter = new StringFragmenter(", ");

		for (Class<?> c : classes) fragmenter.add(name(c));

		return fragmenter.toString();
	}


	public static String name(@NullAllow Object object)
	{
		return object != null ? object.getClass().getCanonicalName() : NULL;
	}


	public static Class<?> value(@NullAllow Object object)
	{
		return object != null ? object.getClass() : null;
	}


	@SuppressWarnings("ObjectEquality")
	public static boolean match(@NullAllow Object object1, @NullAllow Object object2)
	{
		return object1 == object2 || object1 != null && object2 != null && object1.getClass() == object2.getClass();
	}


	//
	final public static class Array
	{
		final public static Class[] BLANK = new Class[0];

	}

}
