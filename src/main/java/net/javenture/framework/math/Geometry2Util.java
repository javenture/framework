package net.javenture.framework.math;


/*
	2019/03/15
 */
final public class Geometry2Util
{
	//
	private Geometry2Util()
	{
	}


	//
	public static boolean determineCircleCenter(Cartesian2dVector point0, Cartesian2dVector point1, Cartesian2dVector point2, Cartesian2dVector destination)
	{
		// http://www.delphimaster.net/view/9-1172844946

		double a = point1.x - point0.x;
		double b = point1.y - point0.y;
		double c = point2.x - point0.x;
		double d = point2.y - point0.y;
		double e = a * (point0.x + point1.x) + b * (point0.y + point1.y);
		double f = c * (point0.x + point2.x) + d * (point0.y + point2.y);
		double g = 2 * (a * (point2.y - point1.y) - b * (point2.x - point1.x));

		if (g != 0)
		{
			destination.x = (d * e - b * f) / g;
			destination.y = (a * f - c * e) / g;

			return true;
		}
		else
		{
			return false;
		}
	}

}
