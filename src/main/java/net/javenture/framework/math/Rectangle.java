package net.javenture.framework.math;


/*
	2020/02/12
 */
final public class Rectangle
{
	//
	public double x0; // ! public
	public double y0; // ! public
	public double x1; // ! public
	public double y1; // ! public


	//
	public Rectangle()
	{
		this(0, 0, 0, 0);
	}


	public Rectangle(double x0, double y0, double x1, double y1)
	{
		this.x0 = x0;
		this.y0 = y0;
		this.x1 = x1;
		this.y1 = y1;
	}


	//
	public double x0()
	{
		return x0;
	}


	public void x0(double value)
	{
		x0 = value;
	}


	public double y0()
	{
		return y0;
	}


	public void y0(double value)
	{
		y0 = value;
	}


	public double x1()
	{
		return x1;
	}


	public void x1(double value)
	{
		x1 = value;
	}


	public double y1()
	{
		return y1;
	}


	public void y1(double value)
	{
		y1 = value;
	}


	public void set(double x0, double y0, double x1, double y1)
	{
		this.x0 = x0;
		this.y0 = y0;
		this.x1 = x1;
		this.y1 = y1;
	}


	public boolean contains(double x, double y)
	{
		return x >= x0 && x <= x1 && y >= y0 && y <= y1;
	}


	public boolean contains(ACartesian2dTuple tuple)
	{
		return contains(tuple.x, tuple.y);
	}

}
