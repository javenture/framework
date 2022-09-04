package net.javenture.framework.math;


import Jama.Matrix;


/*
	2020/02/02
 */
final public class LagrangeInterpolation
{
	/*
		https://en.wikipedia.org/wiki/Interpolation
		https://en.wikipedia.org/wiki/Polynomial_interpolation
		https://en.wikipedia.org/wiki/Lagrange_polynomial
		https://geosoft.no/software/lagrange/LagrangeInterpolation.java.html
	 */
	final private int N;
	final private double[] K;


	//
	public LagrangeInterpolation(double[] x, double[] y)
	{
		int n = x.length;
		double[][] data = new double[n][n];
		double[] rhs = new double[n];

		for (int i = 0; i < n; i++)
		{
			double v = 1;

			for (int j = 0; j < n; j++)
			{
				data[i][n - j - 1] = v;
				v *= x[i];
			}

			rhs[i] = y[i];
		}

		Matrix m = new Matrix(data);
		Matrix b = new Matrix(rhs, n);
		Matrix s = m.solve(b);

		//
		N = n;
		K = s.getRowPackedCopy();
	}


	//
	public double f(double x)
	{
		double result = 0;

		for (int i = 0; i < N; i++) result += K[i] * MathUtil.pow(x, N - i - 1);

		return result;
	}

}
