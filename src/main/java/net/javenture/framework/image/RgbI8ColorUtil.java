package net.javenture.framework.image;


import net.javenture.framework.util.IntUtil;


/*
	2020/05/18
 */
final public class RgbI8ColorUtil
{
	//
	private RgbI8ColorUtil()
	{
	}


	//
	public static int r(int rgb)
	{
		return (rgb >> RgbI8Color.SHIFT_R) & RgbI8Color.MASK;
	}


	public static int r(int rgb, int value)
	{
		return (rgb & RgbI8Color.MASK_R_INVERT) | (range(value) << RgbI8Color.SHIFT_R);
	}


	public static int r(int rgb, long value)
	{
		return (rgb & RgbI8Color.MASK_R_INVERT) | (range(value) << RgbI8Color.SHIFT_R);
	}


	public static int r(int rgb, double value)
	{
		return (rgb & RgbI8Color.MASK_R_INVERT) | (range(value) << RgbI8Color.SHIFT_R);
	}


	static int r0(int rgb, int value)
	{
		return (rgb & RgbI8Color.MASK_R_INVERT) | (mask(value) << RgbI8Color.SHIFT_R);
	}


	public static int g(int rgb)
	{
		return (rgb >> RgbI8Color.SHIFT_G) & RgbI8Color.MASK;
	}


	public static int g(int rgb, int value)
	{
		return (rgb & RgbI8Color.MASK_G_INVERT) | (range(value) << RgbI8Color.SHIFT_G);
	}


	public static int g(int rgb, long value)
	{
		return (rgb & RgbI8Color.MASK_G_INVERT) | (range(value) << RgbI8Color.SHIFT_G);
	}


	public static int g(int rgb, double value)
	{
		return (rgb & RgbI8Color.MASK_G_INVERT) | (range(value) << RgbI8Color.SHIFT_G);
	}


	static int g0(int rgb, int value)
	{
		return (rgb & RgbI8Color.MASK_G_INVERT) | (mask(value) << RgbI8Color.SHIFT_G);
	}


	public static int b(int rgb)
	{
		return (rgb >> RgbI8Color.SHIFT_B) & RgbI8Color.MASK;
	}


	public static int b(int rgb, int value)
	{
		return (rgb & RgbI8Color.MASK_B_INVERT) | (range(value) << RgbI8Color.SHIFT_B);
	}


	public static int b(int rgb, long value)
	{
		return (rgb & RgbI8Color.MASK_B_INVERT) | (range(value) << RgbI8Color.SHIFT_B);
	}


	public static int b(int rgb, double value)
	{
		return (rgb & RgbI8Color.MASK_B_INVERT) | (range(value) << RgbI8Color.SHIFT_B);
	}


	static int b0(int rgb, int value)
	{
		return (rgb & RgbI8Color.MASK_B_INVERT) | (mask(value) << RgbI8Color.SHIFT_B);
	}


	public static int rgb(long r, long g, long b)
	{
		return
			(range(r) << RgbI8Color.SHIFT_R)
			|
			(range(g) << RgbI8Color.SHIFT_G)
			|
			(range(b) << RgbI8Color.SHIFT_B);
	}


	static int rgb0(long r, long g, long b)
	{
		return
			(mask(r) << RgbI8Color.SHIFT_R)
			|
			(mask(g) << RgbI8Color.SHIFT_G)
			|
			(mask(b) << RgbI8Color.SHIFT_B);
	}


	public static float normalize32(int value)
	{
		return value * RgbI8Color.DENOMINATOR_FLOAT;
	}


	public static double normalize64(int value)
	{
		return value * RgbI8Color.DENOMINATOR_DOUBLE;
	}


	public static int scale32(float value)
	{
		return IntUtil.round(value * RgbI8Color.VALUE_MAX_FLOAT);
	}


	public static int scale64(double value)
	{
		return IntUtil.round(value * RgbI8Color.VALUE_MAX_DOUBLE);
	}


	@SuppressWarnings("ManualMinMaxCalculation")
	static int range(int value)
	{
		if (value < RgbI8Color.VALUE_MIN_INT) return RgbI8Color.VALUE_MIN_INT;
		else if (value > RgbI8Color.VALUE_MAX_INT) return RgbI8Color.VALUE_MAX_INT;
		else return value;
	}


	static int range(long value)
	{
		if (value < RgbI8Color.VALUE_MIN_LONG) return RgbI8Color.VALUE_MIN_INT;
		else if (value > RgbI8Color.VALUE_MAX_LONG) return RgbI8Color.VALUE_MAX_INT;
		else return (int) value;
	}


	static int range(float value)
	{
		if (value < RgbI8Color.VALUE_MIN_FLOAT) return RgbI8Color.VALUE_MIN_INT;
		else if (value > RgbI8Color.VALUE_MAX_FLOAT) return RgbI8Color.VALUE_MAX_INT;
		else return (int) value;
	}


	static int range(double value)
	{
		if (value < RgbI8Color.VALUE_MIN_DOUBLE) return RgbI8Color.VALUE_MIN_INT;
		else if (value > RgbI8Color.VALUE_MAX_DOUBLE) return RgbI8Color.VALUE_MAX_INT;
		else return (int) value;
	}


	static int mask(int value)
	{
		return value & RgbI8Color.MASK;
	}


	static int mask(long value)
	{
		return (int) value & RgbI8Color.MASK;
	}

}
