package net.javenture.framework.math;


/*
	2020/04/14

	[ A0 B0 C0 ] -> [ X0, X1 ]
	[ A1 B1 C1 ]
 */
final public class Matrix2CramerSolver
{
	/*
		https://en.wikipedia.org/wiki/Cramer's_rule
	 */
	public enum Report
	{
		ONE,
		NONE,
		INFINITY
	}


	//
	public static Report solve(double[][] source, double[] destination)
	{
		return solve(source[0][0], source[0][1], source[0][2], source[1][0], source[1][1], source[1][2], destination);
	}


	public static Report solve(double a0, double b0, double c0, double a1, double b1, double c1, double[] destination)
	{
		double d = determinant(a0, b0, a1, b1);
		double d0 = determinant(c0, b0, c1, b1);
		double d1 = determinant(a0, c0, a1, c1);

		if (d != 0)
		{
			destination[0] = d0 / d;
			destination[1] = d1 / d;

			return Report.ONE;
		}
		else
		{
			destination[0] = Double.NaN;
			destination[1] = Double.NaN;

			return d0 == 0 && d1 == 0
				? Report.INFINITY
				: Report.NONE;
		}
	}


	private static double determinant(double v00, double v01, double v10, double v11)
	{
		return v00 * v11 - v01 * v10;
	}

}
