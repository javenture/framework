package net.javenture.framework.math;


import net.javenture.framework.annotation.NullAllow;


/*
	2020/02/05
 */
final public class Cartesian2dPoint
	extends ACartesian2dTuple
{
	//
	public Cartesian2dPoint()
	{
	}


	public Cartesian2dPoint(ACartesian2dTuple source)
	{
		super(source);
	}


	public Cartesian2dPoint(double x, double y)
	{
		super(x, y);
	}


	//
	@Override
	@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
	public boolean equals(@NullAllow Object object)
	{
		return equals((Cartesian2dPoint) object);
	}


	public boolean equals(@NullAllow Cartesian2dPoint object)
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

}
