package net.javenture.framework.math;


import net.javenture.framework.annotation.NullAllow;


/*
	2020/09/08
 */
final public class Polar2dPoint
	extends APolar2dTuple<Polar2dPoint>
{
	//
	public Polar2dPoint()
	{
	}


	public Polar2dPoint(APolar2dTuple source)
	{
		super(source);
	}


	public Polar2dPoint(double radius, double azimuth)
	{
		super(radius, azimuth);
	}


	//
	@Override
	@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
	public boolean equals(@NullAllow Object object)
	{
		return equals((Polar2dPoint) object);
	}


	public boolean equals(@NullAllow Polar2dPoint object)
	{
		return
			this == object
			||
			object != null
			&&
			this.radius == object.radius
			&&
			this.azimuth == object.azimuth;
	}

}
