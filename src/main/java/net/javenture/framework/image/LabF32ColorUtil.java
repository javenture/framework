package net.javenture.framework.image;


import net.javenture.framework.math.TupleUtil;


/*
	2022/01/20
 */
final public class LabF32ColorUtil
{
	//
	private LabF32ColorUtil()
	{
	}


	//
	@SuppressWarnings("ManualMinMaxCalculation")
	static float rangeL(float value)
	{
		if (value < LabF32Color.L_MIN_FLOAT) return LabF32Color.L_MIN_FLOAT;
		else if (value > LabF32Color.L_MAX_FLOAT) return LabF32Color.L_MAX_FLOAT;
		else return value;
	}


	static float rangeL(double value)
	{
		if (value < LabF32Color.L_MIN_DOUBLE) return LabF32Color.L_MIN_FLOAT;
		else if (value > LabF32Color.L_MAX_DOUBLE) return LabF32Color.L_MAX_FLOAT;
		else return (float) value;
	}


	@SuppressWarnings("ManualMinMaxCalculation")
	static float rangeA(float value)
	{
		if (value < LabF32Color.A_MIN_FLOAT) return LabF32Color.A_MIN_FLOAT;
		else if (value > LabF32Color.A_MAX_FLOAT) return LabF32Color.A_MAX_FLOAT;
		else return value;
	}


	static float rangeA(double value)
	{
		if (value < LabF32Color.A_MIN_DOUBLE) return LabF32Color.A_MIN_FLOAT;
		else if (value > LabF32Color.A_MAX_DOUBLE) return LabF32Color.A_MAX_FLOAT;
		else return (float) value;
	}


	@SuppressWarnings("ManualMinMaxCalculation")
	static float rangeB(float value)
	{
		if (value < LabF32Color.B_MIN_FLOAT) return LabF32Color.B_MIN_FLOAT;
		else if (value > LabF32Color.B_MAX_FLOAT) return LabF32Color.B_MAX_FLOAT;
		else return value;
	}


	static float rangeB(double value)
	{
		if (value < LabF32Color.B_MIN_DOUBLE) return LabF32Color.B_MIN_FLOAT;
		else if (value > LabF32Color.B_MAX_DOUBLE) return LabF32Color.B_MAX_FLOAT;
		else return (float) value;
	}


	public static double difference(LabF32Color color0, LabF32Color color1)
	{
		double l0 = color0.l();
		double a0 = color0.a();
		double b0 = color0.b();

		double l1 = color1.l();
		double a1 = color1.a();
		double b1 = color1.b();

		return TupleUtil.distance(l0, a0, b0, l1, a1, b1);
	}


	public static double difference2(LabF32Color color0, LabF32Color color1)
	{
		double l0 = color0.l();
		double a0 = color0.a();
		double b0 = color0.b();

		double l1 = color1.l();
		double a1 = color1.a();
		double b1 = color1.b();

		return TupleUtil.distance2(l0, a0, b0, l1, a1, b1);
	}


	public static void blend(LabF32Color color0, LabF32Color color1, BlendCoefficient coefficient, LabF32Color destination)
	{
		blend(color0, color1, coefficient.VALUE0, coefficient.VALUE1, destination);
	}


	public static void blend(LabF32Color color0, LabF32Color color1, double coefficient0, double coefficient1, LabF32Color destination)
	{
		double l = color0.l() * coefficient0 + color1.l() * coefficient1;
		double a = color0.a() * coefficient0 + color1.a() * coefficient1;
		double b = color0.b() * coefficient0 + color1.b() * coefficient1;
		destination.lab0(l, a, b);
	}

}
