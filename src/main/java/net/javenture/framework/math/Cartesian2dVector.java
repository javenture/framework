package net.javenture.framework.math;


import net.javenture.framework.annotation.NullAllow;


/*
	2020/09/08
 */
final public class Cartesian2dVector
	extends ACartesian2dTuple
{
	//
	public Cartesian2dVector()
	{
	}


	public Cartesian2dVector(ACartesian2dTuple source)
	{
		super(source);
	}


	public Cartesian2dVector(double x, double y)
	{
		super(x, y);
	}


	//
	@Override
	@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
	public boolean equals(@NullAllow Object object)
	{
		return equals((Cartesian2dVector) object);
	}


	public boolean equals(@NullAllow Cartesian2dVector object)
	{
		return
			this == object
			||
			object != null
			&&
			this.x == object.x
			&&
			this.y == object.y;
	}


	public double length()
	{
        return MathUtil.sqrt(length2());
    }


	public double length2()
	{
        return x * x + y * y;
    }


    public void normalize()
	{
        double length = length();

        if (length != 0 && length != 1)
		{
			double k = 1 / length;
			x *= k;
			y *= k;
		}
    }


	public void multiply(double value)
	{
        x *= value;
        y *= value;
    }


	public void add(Cartesian2dVector object)
	{
        this.x += object.x;
        this.y += object.y;
	}


	public void sub(Cartesian2dVector object)
	{
        this.x -= object.x;
        this.y -= object.y;
	}

}
