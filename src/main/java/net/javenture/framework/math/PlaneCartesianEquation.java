package net.javenture.framework.math;


/*
	2020/02/07
 */
final public class PlaneCartesianEquation
{
	/*
		https://stackoverflow.com/a/45362069
	 */
	final public double A;
	final public double B;
	final public double C;
	final public double D;


	//
	public PlaneCartesianEquation(double x0, double y0, double z0, double x1, double y1, double z1, double x2, double y2, double z2)
	{
		A = (y1 - y0) * (z2 - z0) - (y2 - y0) * (z1 - z0);
		B = (z1 - z0) * (x2 - x0) - (z2 - z0) * (x1 - x0);
		C = (x1 - x0) * (y2 - y0) - (x2 - x0) * (y1 - y0);
		D = -(A * x0 + B * y0 + C * z0);
	}

}
