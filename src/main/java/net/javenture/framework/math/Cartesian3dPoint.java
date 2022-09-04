package net.javenture.framework.math;


/*
	2020/09/09
 */
final public class Cartesian3dPoint
	extends ACartesian3dTuple
{
	//
	public Cartesian3dPoint()
	{
	}


	public Cartesian3dPoint(ACartesian3dTuple source)
	{
		super(source);
	}


	public Cartesian3dPoint(double x, double y, double z)
	{
		super(x, y, z);
	}

}
