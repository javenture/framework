package net.javenture.framework.image;


/*
	2022/02/12
 */
final public class HslF32ColorUtil
{
	//
	private HslF32ColorUtil()
	{
	}


	//
	@SuppressWarnings("ManualMinMaxCalculation")
	static float rangeH(float value)
	{
		if (value < HslF32Color.H_MIN_FLOAT) return HslF32Color.H_MIN_FLOAT;                           // ???
		else if (value > HslF32Color.H_MAX_FLOAT) return HslF32Color.H_MAX_FLOAT;                      // ???
		else return value;
	}


	static float rangeH(double value)
	{
		if (value < HslF32Color.H_MIN_DOUBLE) return HslF32Color.H_MIN_FLOAT;                          // ???
		else if (value > HslF32Color.H_MAX_DOUBLE) return HslF32Color.H_MAX_FLOAT;                     // ???
		else return (float) value;
	}


	@SuppressWarnings("ManualMinMaxCalculation")
	static float rangeS(float value)
	{
		if (value < HslF32Color.S_MIN_FLOAT) return HslF32Color.S_MIN_FLOAT;
		else if (value > HslF32Color.S_MAX_FLOAT) return HslF32Color.S_MAX_FLOAT;
		else return value;
	}


	static float rangeS(double value)
	{
		if (value < HslF32Color.S_MIN_DOUBLE) return HslF32Color.S_MIN_FLOAT;
		else if (value > HslF32Color.S_MAX_DOUBLE) return HslF32Color.S_MAX_FLOAT;
		else return (float) value;
	}


	@SuppressWarnings("ManualMinMaxCalculation")
	static float rangeL(float value)
	{
		if (value < HslF32Color.L_MIN_FLOAT) return HslF32Color.L_MIN_FLOAT;
		else if (value > HslF32Color.L_MAX_FLOAT) return HslF32Color.L_MAX_FLOAT;
		else return value;
	}


	static float rangeL(double value)
	{
		if (value < HslF32Color.L_MIN_DOUBLE) return HslF32Color.L_MIN_FLOAT;
		else if (value > HslF32Color.L_MAX_DOUBLE) return HslF32Color.L_MAX_FLOAT;
		else return (float) value;
	}

}
