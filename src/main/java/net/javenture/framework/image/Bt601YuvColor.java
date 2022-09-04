package net.javenture.framework.image;


/*
	2020/04/10
 */
final public class Bt601YuvColor
{
	/*
		https://en.wikipedia.org/wiki/YUV
	 */
	//
	final public static double Y_MIN = 0;
	final public static double Y_MAX = 0.9999999999999999;
	final public static double U_MIN = -0.43599;
	final public static double U_MAX = 0.436;
	final public static double V_MIN = -0.615;
	final public static double V_MAX = 0.615;


	//
	double y;
	double u;
	double v;


	//
	public Bt601YuvColor()
	{
		this(0, 0, 0);
	}


	public Bt601YuvColor(double y, double u, double v)
	{
		this.y = y;
		this.u = u;
		this.v = v;
	}


	//
	public double y()
	{
		return y;
	}


	public void y(double value)
	{
		y = value;
	}


	void y0(double value)
	{
		y = value;
	}


	public double u()
	{
		return u;
	}


	public void u(double value)
	{
		u = value;
	}


	void u0(double value)
	{
		u = value;
	}


	public double v()
	{
		return v;
	}


	public void v(double value)
	{
		v = value;
	}


	void v0(double value)
	{
		v = value;
	}


	//
	public static void convert(RgbI8Color source, Bt601YuvColor destination)
	{
		convert(source.r(), source.g(), source.b(), destination);
	}


	public static void convert(int r, int g, int b, Bt601YuvColor destination)
	{
		double r0 = RgbI8ColorUtil.normalize64(r);
		double g0 = RgbI8ColorUtil.normalize64(g);
		double b0 = RgbI8ColorUtil.normalize64(b);
		double y = Converter.y(r0, g0, b0);
		double u = Converter.u(r0, g0, b0);
		double v = Converter.v(r0, g0, b0);

		destination.y0(y);
		destination.u0(u);
		destination.v0(v);
	}


	public static void convert(Bt601YuvColor source, RgbI8Color destination)
	{
		convert(source.y, source.u, source.v, destination);
	}


	public static void convert(double y, double u, double v, RgbI8Color destination)
	{
		int r = RgbI8ColorUtil.scale64(Converter.r(y, u, v));
		int g = RgbI8ColorUtil.scale64(Converter.g(y, u, v));
		int b = RgbI8ColorUtil.scale64(Converter.b(y, u, v));

		destination.rgb(r, g, b);
	}


	//
	private static class Converter
	{
	    //
		private static double y(double r, double g, double b)
		{
			return 0.299 * r + 0.587 * g + 0.114 * b;
		}

		private static double u(double r, double g, double b)
		{
			return -0.14713 * r - 0.28886 * g + 0.436 * b;
		}

		private static double v(double r, double g, double b)
		{
			return 0.615 * r - 0.51499 * g - 0.10001 * b;
		}

		private static double r(double y, double u, double v)
		{
			return y + 1.13983 * v;
		}

		private static double g(double y, double u, double v)
		{
			return y - 0.39465 * u - 0.58060 * v;
		}

		private static double b(double y, double u, double v)
		{
			return y + 2.03211 * u;
		}
	}

}
