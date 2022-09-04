package net.javenture.framework.math;


import net.javenture.framework.annotation.NullAllow;


/*
	2020/09/08
 */
final public class Polar2dVector
	extends APolar2dTuple<Polar2dVector>
{
	//
	public Polar2dVector()
	{
	}


	public Polar2dVector(APolar2dTuple source)
	{
		super(source);
	}


	public Polar2dVector(double radius, double azimuth)
	{
		super(radius, azimuth);
	}


	//
	@Override
	@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
	public boolean equals(@NullAllow Object object)
	{
		return equals((Polar2dVector) object);
	}


	public boolean equals(@NullAllow Polar2dVector object)
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
