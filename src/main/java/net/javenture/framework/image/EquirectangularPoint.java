package net.javenture.framework.image;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.math.ACartesian2dTuple;


/*
	2020/04/16

	x: [0 ... 1)
	y: [0 ... 1]
 */
final public class EquirectangularPoint
	extends ACartesian2dTuple                                                                       // ???
{
	//
	public EquirectangularPoint()
	{
	}


	public EquirectangularPoint(ACartesian2dTuple source)
	{
		super(source);
	}


	public EquirectangularPoint(double x, double y)
	{
		super(x, y);
	}


	//
	@Override
	@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
	public boolean equals(@NullAllow Object object)
	{
		return equals((EquirectangularPoint) object);
	}


	public boolean equals(@NullAllow EquirectangularPoint object)
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
