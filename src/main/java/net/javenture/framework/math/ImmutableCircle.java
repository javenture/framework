package net.javenture.framework.math;


/*
	2020/02/12
 */
final public class ImmutableCircle
{
	//
	final public double X; // ! public
	final public double Y; // ! public
	final public double R; // ! public
	final private double R2;


	//
	public ImmutableCircle()
	{
		this(0, 0, 0);
	}


	public ImmutableCircle(double x, double y, double r)
	{
		X = x;
		Y = y;
		R = r;
		R2 = r * r;
	}


	//
	public double x()
	{
		return X;
	}


	public double y()
	{
		return Y;
	}


	public double r()
	{
		return R;
	}


	public boolean contains(double x, double y)
	{
		return TupleUtil.distance2(X, Y, x, y) <= R2;
	}


	public boolean contains(ACartesian2dTuple tuple)
	{
		return contains(tuple.x, tuple.y);
	}

}
