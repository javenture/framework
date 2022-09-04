package net.javenture.framework.util;


/*
	2020/04/16
 */
final public class Range
{
	//
	final public int MIN;
	final public int MAX;
	final public int LENGTH;


	//
	public Range(int min, int max)
	{
		MIN = min;
		MAX = max;
		LENGTH = MAX - MIN + 1;
	}


	//
	public int min()
	{
		return MIN;
	}


	public int max()
	{
		return MAX;
	}


	public int length()
	{
		return LENGTH;
	}


	public int index(int value)
	{
		return value - MIN;
	}

}
