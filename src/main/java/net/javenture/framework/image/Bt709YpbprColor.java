package net.javenture.framework.image;


/*
	2020/01/31
 */
final public class Bt709YpbprColor
{
	/*
		https://en.wikipedia.org/wiki/YUV
	 */
	final public static double Y_MIN = 0;
	final public static double Y_MAX = 1;
	final public static double PB_MIN = -0.5;
	final public static double PB_MAX = 0.5;
	final public static double PR_MIN = -0.5;
	final public static double PR_MAX = 0.5;



	//
	private double y;
	private double pb;
	private double pr;


	//
	public Bt709YpbprColor()
	{
		this(0, 0, 0);
	}


	public Bt709YpbprColor(double y, double pb, double pr)
	{
		this.y = y;
		this.pb = pb;
		this.pr = pr;
	}


	//
	public double y()
	{
		return y;
	}


	public void y(double value)
	{
		y = value;                                                    // XXX: range
	}


	void y0(double value)
	{
		y = value;
	}


	public double pb()
	{
		return pb;
	}


	public void pb(double value)
	{
		pb = value;                                          // XXX: range
	}


	void pb0(double value)
	{
		pb = value;
	}


	public double pr()
	{
		return pr;
	}


	public void pr(double value)
	{
		pr = value;                                                     // XXX: range
	}


	void pr0(double value)
	{
		pr = value;
	}


	//
	public static void convert(RgbI8Color source, Bt709YpbprColor destination)
	{
		convert(source.r(), source.g(), source.b(), destination);
	}


	public static void convert(int r, int g, int b, Bt709YpbprColor destination)
	{
		double r0 = RgbI8ColorUtil.normalize64(r);
		double g0 = RgbI8ColorUtil.normalize64(g);
		double b0 = RgbI8ColorUtil.normalize64(b);
		double y = Converter.y(r0, g0, b0);
		double pb = Converter.pb(r0, g0, b0);
		double pr = Converter.pr(r0, g0, b0);

		destination.y0(y);
		destination.pb0(pb);
		destination.pr0(pr);
	}


	public static void convert(Bt709YpbprColor source, RgbI8Color destination)
	{
		convert(source.y, source.pb, source.pr, destination);
	}


	public static void convert(double y, double pb, double pr, RgbI8Color destination)
	{
		int r = RgbI8ColorUtil.scale64(Converter.r(y, pb, pr));
		int g = RgbI8ColorUtil.scale64(Converter.g(y, pb, pr));
		int b = RgbI8ColorUtil.scale64(Converter.b(y, pb, pr));
		destination.rgb(r, g, b);
	}


	public static void convert(Bt709YpbprColor source, RgbF32Color destination)
	{
		convert(source.y, source.pb, source.pr, destination);
	}


	public static void convert(double y, double pb, double pr, RgbF32Color destination)
	{
		double r = Converter.r(y, pb, pr);
		double g = Converter.g(y, pb, pr);
		double b = Converter.b(y, pb, pr);

		destination.r0((float) r);                                                                              // XXX
		destination.g0((float) g);
		destination.b0((float) b);
	}


	//
	private static class Converter
	{
		//
		final private static double KB = 0.0722;
		final private static double KR = 0.2126;
		final private static double KG = 0.7152;

		final private static double[][] MATRIX_RGB_YPBPR = new double[][]
		{
			{ KR, KG, KB },
			{ -0.5 * KR / (1 - KB), -0.5 * KG / (1 - KB), 0.5 },
			{ 0.5, -0.5 * KG / (1 - KR), -0.5 * KB / (1 - KR) }
		};

		final private static double[][] MATRIX_YPBPR_RGB = new double[][]
		{
			{ 1, 0, 2 - 2 * KR },
			{ 1, -KB / KG * (2 - 2 * KB), -KR / KG * (2 - 2 * KR) },
			{ 1, 2 - 2 * KB, 0 }
		};

	    //
		private static double y(double r, double g, double b)
		{
			return r * MATRIX_RGB_YPBPR[0][0] + g * MATRIX_RGB_YPBPR[0][1] + b * MATRIX_RGB_YPBPR[0][2];
		}

		private static double pb(double r, double g, double b)
		{
			return r * MATRIX_RGB_YPBPR[1][0] + g * MATRIX_RGB_YPBPR[1][1] + b * MATRIX_RGB_YPBPR[1][2];
		}

		private static double pr(double r, double g, double b)
		{
			return r * MATRIX_RGB_YPBPR[2][0] + g * MATRIX_RGB_YPBPR[2][1] + b * MATRIX_RGB_YPBPR[2][2];
		}

		private static double r(double y, double pb, double pr)
		{
			return y * MATRIX_YPBPR_RGB[0][0] + pb * MATRIX_YPBPR_RGB[0][1] + pr * MATRIX_YPBPR_RGB[0][2];
		}

		private static double g(double y, double pb, double pr)
		{
			return y * MATRIX_YPBPR_RGB[1][0] + pb * MATRIX_YPBPR_RGB[1][1] + pr * MATRIX_YPBPR_RGB[1][2];
		}

		private static double b(double y, double pb, double pr)
		{
			return y * MATRIX_YPBPR_RGB[2][0] + pb * MATRIX_YPBPR_RGB[2][1] + pr * MATRIX_YPBPR_RGB[2][2];
		}
	}

}
