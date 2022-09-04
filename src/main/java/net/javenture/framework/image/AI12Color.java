package net.javenture.framework.image;


/*
	2020/05/12
 */
abstract public class AI12Color<V extends Integer, CH extends II12ColorChannel>
	implements IColor<V, CH>
{
	//
	abstract public void set(CH channel, int value);
	abstract public void set0(CH channel, int value);
	abstract public void get(int[] destination);
	abstract public void set(int[] source);
	abstract public void set0(int[] source);


	//
	final public void set(CH channel, long value)
	{
		set(channel, (int) value);
	}


	final public void set0(CH channel, long value)
	{
		set0(channel, (int) value);
	}

}
