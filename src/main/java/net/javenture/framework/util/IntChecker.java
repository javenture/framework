package net.javenture.framework.util;


/*
	2018/06/03
 */
final public class IntChecker
{
	//
	final private static int TRUE = 1;
	final private static int FALSE = 0;


	//
	final private int[] ARRAY;


	//
	public IntChecker(int size)
	{
		ARRAY = new int[size];
	}


	public IntChecker(Enum[] values)
	{
		ARRAY = new int[values.length];
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


	public boolean get(int index)
	{
		return ARRAY[index] == TRUE;
	}


	public boolean get(Enum value)
	{
		return ARRAY[value.ordinal()] == TRUE;
	}


	public void set(int index)
	{
		ARRAY[index] = TRUE;
	}


	public void set(int... indexes)
	{
		for (int index : indexes) ARRAY[index] = TRUE;
	}


	public void set(Enum value)
	{
		ARRAY[value.ordinal()] = TRUE;
	}


	public void set(Enum... values)
	{
		for (Enum value : values) ARRAY[value.ordinal()] = TRUE;
	}


	public void clear(int index)
	{
		ARRAY[index] = FALSE;
	}


	public void clear(int... indexes)
	{
		for (int index : indexes) ARRAY[index] = FALSE;
	}


	public void clear(Enum value)
	{
		ARRAY[value.ordinal()] = FALSE;
	}


	public void clear(Enum... values)
	{
		for (Enum value : values) ARRAY[value.ordinal()] = FALSE;
	}

}
