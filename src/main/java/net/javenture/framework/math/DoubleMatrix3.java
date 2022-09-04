package net.javenture.framework.math;


/*
	2020/01/30
 */
final public class DoubleMatrix3
{
	//
	final public double V00;
	final public double V01;
	final public double V02;
	final public double V10;
	final public double V11;
	final public double V12;
	final public double V20;
	final public double V21;
	final public double V22;


	//
	public DoubleMatrix3(double[] array0, double[] array1, double[] array2)
	{
		V00 = array0[0];
		V01 = array0[1];
		V02 = array0[2];
		V10 = array1[0];
		V11 = array1[1];
		V12 = array1[2];
		V20 = array2[0];
		V21 = array2[1];
		V22 = array2[2];
	}


	public DoubleMatrix3(double v00, double v01, double v02, double v10, double v11, double v12, double v20, double v21, double v22)
	{
		V00 = v00;
		V01 = v01;
		V02 = v02;
		V10 = v10;
		V11 = v11;
		V12 = v12;
		V20 = v20;
		V21 = v21;
		V22 = v22;
	}


	//
/*
	public void process(NormalizedRgbColor source, NormalizedRgbColor destination)
	{
		double r = source.r; // !
		double g = source.g; // !
		double b = source.b; // !

		destination.r(r * V00 + g * V01 + b * V02);
		destination.g(r * V10 + g * V11 + b * V12);
		destination.b(r * V20 + g * V21 + b * V22);
	}
*/

}
