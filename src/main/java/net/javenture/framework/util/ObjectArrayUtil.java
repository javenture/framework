package net.javenture.framework.util;


import net.javenture.framework.annotation.NullAllow;

import java.util.Iterator;
import java.util.Objects;


/*
	2020/09/27
 */
final public class ObjectArrayUtil
{
	//
	final public static Object[] BLANK = new Object[0];


	//
	private ObjectArrayUtil()
	{
	}


	//
	public static <T> boolean contains(T[] array, @NullAllow T object)
	{
		for (T t : array)
		{
			if (Objects.equals(t, object)) return true;
		}

		return false;
	}


	public static <T> Iterator<T> iterator(T[] array)
	{
		return new Iterator<>()
		{
			final private int LENGTH = array.length;
			private int index = -1;

			@Override
			public boolean hasNext()
			{
				return index + 1 < LENGTH;
			}

			@Override
			public T next()
			{
				return array[++index];
			}
		};
	}

}
