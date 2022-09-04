package net.javenture.framework.math;


/*
	2020/09/13
 */
final public class MathUtil
{
	//
	final public static double PI = Math.PI;
	final public static double PI_HALF = Math.PI / 2;
	final public static double PI_ONE_AND_A_HALF = Math.PI + Math.PI / 2;
	final public static double PI_DOUBLE = Math.PI * 2;
	final public static double ACOS_PLUS_1 = Math.acos(1.0);
	final public static double ACOS_MINUS_1 = Math.acos(-1.0);


	//
	private MathUtil()
	{
	}


	//
	public static double sqrt(double value)
	{
		return Math.sqrt(value);
	}


	public static double pow(double a, double b)
	{
		return Math.pow(a, b);
	}


	public static double sin(double value)
	{
		return Math.sin(value);
	}


	public static double cos(double value)
	{
		return Math.cos(value);
	}


	public static double tan(double value)
	{
		return Math.tan(value);
	}


	public static double asin(double value)
	{
		return Math.asin(value);
	}


	public static double acos(double value)
	{
		return Math.acos(value);
	}


	public static double atan(double value)
	{
		return Math.atan(value);
	}


	public static double atan2(double y, double x)
	{
		return Math.atan2(y, x);
	}


	public static double log(double value)
	{
		return Math.log(value);
	}

}
