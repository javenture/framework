package net.javenture.framework.math;


/*
	2020/02/07
 */
abstract public class ACartesian2dTuple
{
	//
	public double x; // ! public
	public double y; // ! public


	//
	public ACartesian2dTuple()
	{
		this(0, 0);
	}


	public ACartesian2dTuple(ACartesian2dTuple source)
	{
		this(source.x, source.y);
	}


	public ACartesian2dTuple(double x, double y)
	{
		this.x = x;
		this.y = y;
	}


	//
	@Override
	final public int hashCode()
	{
        long result = 7L;
        result = 31L * result + Double.doubleToLongBits(x);
        result = 31L * result + Double.doubleToLongBits(y);

        return (int) (result ^ (result >> 32));
	}


	@Override
	final public String toString()
	{
		return "[x: " + x + "; y: " + y + "]";
	}


	final public double x()
	{
		return x;
	}


	final public void x(double value)
	{
		x = value;
	}


	final public double y()
	{
		return y;
	}


	final public void y(double value)
	{
		y = value;
	}


	final public void set(double x, double y)
	{
		this.x = x;
		this.y = y;
	}


	final public void set(ACartesian2dTuple tuple)
	{
		set(tuple.x, tuple.y);
	}

}
