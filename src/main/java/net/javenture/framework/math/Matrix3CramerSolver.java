package net.javenture.framework.math;


/*
	2020/01/23

	[ A0 B0 C0 D0 ]
	[ A1 B1 C1 D1 ] -> [ X0, X1, X2 ]
	[ A2 B2 C2 D2 ]
 */
final public class Matrix3CramerSolver
{
	/*
		https://en.wikipedia.org/wiki/Cramer's_rule
	 */
	public enum Result
	{
		ONE,
		NONE,
		INFINITY
	}


	//
	public static Result solve(double[][] source, double[] destination)
	{
		double[][] d =
		{
			{ source[0][0], source[0][1], source[0][2] },
			{ source[1][0], source[1][1], source[1][2] },
			{ source[2][0], source[2][1], source[2][2] },
		};

		double[][] d0 =
		{
			{ source[0][3], source[0][1], source[0][2] },
			{ source[1][3], source[1][1], source[1][2] },
			{ source[2][3], source[2][1], source[2][2] },
		};

		double[][] d1 =
		{
			{ source[0][0], source[0][3], source[0][2] },
			{ source[1][0], source[1][3], source[1][2] },
			{ source[2][0], source[2][3], source[2][2] },
		};

		double[][] d2 =
		{
			{ source[0][0], source[0][1], source[0][3] },
			{ source[1][0], source[1][1], source[1][3] },
			{ source[2][0], source[2][1], source[2][3] },
		};

		double D = determinant(d);
		double D0 = determinant(d0);
		double D1 = determinant(d1);
		double D2 = determinant(d2);

		if (D != 0)
		{
			destination[0] = D0 / D;
			destination[1] = D1 / D;
			destination[2] = D2 / D;

			return Result.ONE;
		}
		else
		{
			destination[0] = Double.NaN;
			destination[1] = Double.NaN;
			destination[2] = Double.NaN;

			return D0 == 0 && D1 == 0 && D2 == 0
				? Result.INFINITY
				: Result.NONE;
		}
	}


	private static double determinant(double[][] matrix)
	{
		return
			matrix[0][0] * (matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1])
			-
			matrix[0][1] * (matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0])
			+
			matrix[0][2] * (matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0]);
	}

}
