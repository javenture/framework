package net.javenture.framework.image;


/*
	2020/04/20
 */
abstract public class AF32Color<V extends Float, CH extends IF32ColorChannel>
	implements IColor<V, CH>
{
	//
	abstract public void set(CH channel, float value);
	abstract public void set0(CH channel, float value);
	abstract public void get(float[] destination);
	abstract public void set(float[] source);
	abstract public void set0(float[] source);


	//
	final public void set(CH channel, double value)
	{
		set(channel, (float) value);
	}


	final public void set0(CH channel, double value)
	{
		set0(channel, (float) value);
	}

}
