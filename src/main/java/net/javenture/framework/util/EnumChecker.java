package net.javenture.framework.util;


/*
	2018/04/06
 */
final public class EnumChecker<E extends Enum>
{
	//
	final private static int TRUE = 1;
	final private static int FALSE = 0;


	//
	final private int[] ARRAY;


	//
	public EnumChecker(E[] init)
	{
		ARRAY = new int[init.length];
	}


	@SafeVarargs
	public EnumChecker(E[] init, E... values)
	{
		ARRAY = new int[init.length];
		set(values);
	}


	//
	public int size()
	{
		return ARRAY.length;
	}


	public void init()
	{
		init(false);
	}


	public void init(boolean value)
	{
		if (value)
		{
			for (int i = 0; i < ARRAY.length; i++) ARRAY[i] = TRUE;
		}
		else
		{
			for (int i = 0; i < ARRAY.length; i++) ARRAY[i] = FALSE;
		}
	}


	public boolean get(E value)
	{
		return ARRAY[value.ordinal()] == TRUE;
	}


	public void set(E value)
	{
		ARRAY[value.ordinal()] = TRUE;
	}


	@SafeVarargs
	public final void set(E... values)
	{
		for (E value : values) ARRAY[value.ordinal()] = TRUE;
	}


	public void clear(E value)
	{
		ARRAY[value.ordinal()] = FALSE;
	}


	@SafeVarargs
	public final void clear(E... values)
	{
		for (E value : values) ARRAY[value.ordinal()] = FALSE;
	}

}
