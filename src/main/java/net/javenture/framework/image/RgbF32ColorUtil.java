package net.javenture.framework.image;


/*
	2020/05/13
 */
final public class RgbF32ColorUtil
{
	//
	private RgbF32ColorUtil()
	{
	}


	//
	@SuppressWarnings("ManualMinMaxCalculation")
	static float range(float value)
	{
		if (value < RgbF32Color.VALUE_MIN_FLOAT) return RgbF32Color.VALUE_MIN_FLOAT;
		else if (value > RgbF32Color.VALUE_MAX_FLOAT) return RgbF32Color.VALUE_MAX_FLOAT;
		else return value;
	}


	static float range(double value)
	{
		if (value < RgbF32Color.VALUE_MIN_DOUBLE) return RgbF32Color.VALUE_MIN_FLOAT;
		else if (value > RgbF32Color.VALUE_MAX_DOUBLE) return RgbF32Color.VALUE_MAX_FLOAT;
		else return (float) value;
	}

}
