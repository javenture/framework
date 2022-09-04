package net.javenture.framework.image;


/*
	2020/04/28
 */
public enum BlendType
{
	/*
		http://paulbourke.net/miscellaneous/edgeblend/
		https://en.wikipedia.org/wiki/Alpha_compositing
		https://en.wikipedia.org/wiki/Blend_modes
		https://en.wikipedia.org/wiki/Cubic_Hermite_spline
	 */

	//
	LINEAR(BlendType::linear),
	QUADRATIC(BlendType::quadratic),
	CUBIC(BlendType::cubic);


	//
	final private ICoefficient COEFFICIENT;


	//
	BlendType(ICoefficient coefficient)
	{
		COEFFICIENT = coefficient;
	}


	//
	public BlendCoefficient coefficient(double d)
	{
		return COEFFICIENT.get(d);
	}


	//
	public static BlendCoefficient linear(double d)
	{
		return new BlendCoefficient(d, 1 - d);
	}


	public static BlendCoefficient quadratic(double d)
	{
		double value0;
		double value1;

		if (d >= 0.5)
		{
			double k = 2 * (1 - d);
			value0 = 1 - 0.5 * k * k;
			value1 = 1 - value0;
		}
		else
		{
			double k = 2 * d;
			value0 = 0.5 * k * k;
			value1 = 1 - value0;
		}

		return new BlendCoefficient(value0, value1);
	}


	public static BlendCoefficient cubic(double d)
	{
		double value0;
		double value1;

		if (d >= 0.5)
		{
			double k = 2 * (1 - d);
			value0 = 1 - 0.5 * k * k * k;
			value1 = 1 - value0;
		}
		else
		{
			double k = 2 * d;
			value0 = 0.5 * k * k * k;
			value1 = 1 - value0;
		}

		return new BlendCoefficient(value0, value1);
	}


	//
	@FunctionalInterface
	private interface ICoefficient
	{
		BlendCoefficient get(double d);
	}

}
