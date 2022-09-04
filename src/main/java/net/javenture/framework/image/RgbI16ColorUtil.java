package net.javenture.framework.image;


import net.javenture.framework.util.IntUtil;


/*
	2020/05/18
 */
final public class RgbI16ColorUtil
{
	//
	private RgbI16ColorUtil()
	{
	}


	//
	public static int r(long rgb)
	{
		return (int) ((rgb >> RgbI16Color.SHIFT_R) & RgbI16Color.MASK);
	}


	public static long r(long rgb, int value)
	{
		return (rgb & RgbI16Color.MASK_R_INVERT) | (range(value) << RgbI16Color.SHIFT_R);
	}


	public static long r(long rgb, long value)
	{
		return (rgb & RgbI16Color.MASK_R_INVERT) | (range(value) << RgbI16Color.SHIFT_R);
	}


	public static long r(long rgb, double value)
	{
		return (rgb & RgbI16Color.MASK_R_INVERT) | (range(value) << RgbI16Color.SHIFT_R);
	}


	static long r0(long rgb, long value)
	{
		return (rgb & RgbI16Color.MASK_R_INVERT) | (mask(value) << RgbI16Color.SHIFT_R);
	}


	public static int g(long rgb)
	{
		return (int) ((rgb >> RgbI16Color.SHIFT_G) & RgbI16Color.MASK);
	}


	public static long g(long rgb, int value)
	{
		return (rgb & RgbI16Color.MASK_G_INVERT) | (range(value) << RgbI16Color.SHIFT_G);
	}


	public static long g(long rgb, long value)
	{
		return (rgb & RgbI16Color.MASK_G_INVERT) | (range(value) << RgbI16Color.SHIFT_G);
	}


	public static long g(long rgb, double value)
	{
		return (rgb & RgbI16Color.MASK_G_INVERT) | (range(value) << RgbI16Color.SHIFT_G);
	}


	static long g0(long rgb, long value)
	{
		return (rgb & RgbI16Color.MASK_G_INVERT) | (mask(value) << RgbI16Color.SHIFT_G);
	}


	public static int b(long rgb)
	{
		return (int) ((rgb >> RgbI16Color.SHIFT_B) & RgbI16Color.MASK);
	}


	public static long b(long rgb, int value)
	{
		return (rgb & RgbI16Color.MASK_B_INVERT) | (range(value) << RgbI16Color.SHIFT_B);
	}


	public static long b(long rgb, long value)
	{
		return (rgb & RgbI16Color.MASK_B_INVERT) | (range(value) << RgbI16Color.SHIFT_B);
	}


	public static long b(long rgb, double value)
	{
		return (rgb & RgbI16Color.MASK_B_INVERT) | (range(value) << RgbI16Color.SHIFT_B);
	}


	static long b0(long rgb, long value)
	{
		return (rgb & RgbI16Color.MASK_B_INVERT) | (mask(value) << RgbI16Color.SHIFT_B);
	}


	public static long rgb(long r, long g, long b)
	{
		return
			(range(r) << RgbI16Color.SHIFT_R)
			|
			(range(g) << RgbI16Color.SHIFT_G)
			|
			(range(b) << RgbI16Color.SHIFT_B);
	}


	static long rgb0(long r, long g, long b)
	{
		return
			(mask(r) << RgbI16Color.SHIFT_R)
			|
			(mask(g) << RgbI16Color.SHIFT_G)
			|
			(mask(b) << RgbI16Color.SHIFT_B);
	}


	public static float normalize32(int value)
	{
		return value * RgbI16Color.DENOMINATOR_FLOAT;
	}


	public static double normalize64(int value)
	{
		return value * RgbI16Color.DENOMINATOR_DOUBLE;
	}


	public static int scale32(float value)
	{
		return IntUtil.round(value * RgbI16Color.VALUE_MAX_FLOAT);
	}


	public static int scale64(double value)
	{
		return IntUtil.round(value * RgbI16Color.VALUE_MAX_DOUBLE);
	}


	static long range(int value)
	{
		if (value < RgbI16Color.VALUE_MIN_INT) return RgbI16Color.VALUE_MIN_LONG;
		else if (value > RgbI16Color.VALUE_MAX_INT) return RgbI16Color.VALUE_MAX_LONG;
		else return value;
	}


	static long range(long value)
	{
		if (value < RgbI16Color.VALUE_MIN_LONG) return RgbI16Color.VALUE_MIN_LONG;
		else if (value > RgbI16Color.VALUE_MAX_LONG) return RgbI16Color.VALUE_MAX_LONG;
		else return (int) value;
	}


	static long range(float value)
	{
		if (value < RgbI16Color.VALUE_MIN_FLOAT) return RgbI16Color.VALUE_MIN_LONG;
		else if (value > RgbI16Color.VALUE_MAX_FLOAT) return RgbI16Color.VALUE_MAX_LONG;
		else return (int) value;
	}


	static long range(double value)
	{
		if (value < RgbI16Color.VALUE_MIN_DOUBLE) return RgbI16Color.VALUE_MIN_LONG;
		else if (value > RgbI16Color.VALUE_MAX_DOUBLE) return RgbI16Color.VALUE_MAX_LONG;
		else return (int) value;
	}


	static long mask(long value)
	{
		return value & RgbI16Color.MASK;
	}

}
