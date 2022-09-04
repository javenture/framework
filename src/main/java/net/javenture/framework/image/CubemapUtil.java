package net.javenture.framework.image;


import net.javenture.framework.math.MathUtil;
import net.javenture.framework.math.TupleUtil;


/*
	2020/03/12
 */
final public class CubemapUtil
{
	//
	private CubemapUtil()
	{
	}


	//
	public static double distance(CubePoint point0, CubePoint point1)
	{
		double d = distance2(point0, point1);

		return !Double.isNaN(d) ? MathUtil.sqrt(d) : Double.NaN;
	}


	public static double distance2(CubePoint point0, CubePoint point1)
	{
		return point0.face == point1.face
			? TupleUtil.distance2(point0.x, point0.y, point1.x, point1.y)
			: Double.NaN;
	}

}
