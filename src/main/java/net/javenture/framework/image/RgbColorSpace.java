package net.javenture.framework.image;


import Jama.Matrix;

import java.util.Arrays;


/*
	2020/05/21
 */
public enum RgbColorSpace
{
	/*
		https://en.wikipedia.org/wiki/SRGB
		https://en.wikipedia.org/wiki/Adobe_RGB_color_space
		https://en.wikipedia.org/wiki/Wide-gamut_RGB_color_space
		https://en.wikipedia.org/wiki/ProPhoto_RGB_color_space
		https://en.wikipedia.org/wiki/Rec._709
		https://en.wikipedia.org/wiki/Rec._2020
		https://en.wikipedia.org/wiki/Color_spaces_with_RGB_primaries
	 */
	S_RGB("sRGB", WhitePoint.D65, 0.64, 0.33, 0.30, 0.60, 0.15, 0.06, XyzMatrix.S_RGB),
	ADOBE_RGB("Adobe RGB", WhitePoint.D65, 0.64, 0.33, 0.21, 0.71, 0.15, 0.06, XyzMatrix.ADOBE_RGB),
	ADOBE_WIDE_GAMUT_RGB("Adobe Wide Gamut RGB", WhitePoint.D50, 0.7347, 0.2653, 0.1152, 0.8264, 0.1566, 0.0177, XyzMatrix.ADOBE_WIDE_GAMUT_RGB),
	KODAK_PRO_PHOTO_RGB("ProPhoto RGB", WhitePoint.D50, 0.7347, 0.2653, 0.1596, 0.8404, 0.0366, 0.0001, XyzMatrix.KODAK_PRO_PHOTO_RGB),
	REC_709_RGB("Rec. 709 RGB", WhitePoint.D65, 0.64, 0.33, 0.30, 0.60, 0.15, 0.06, XyzMatrix.REC_709_RGB),
	REC_2020_RGB("Rec. 2020 RGB", WhitePoint.D65, 0.708, 0.292, 0.17, 0.797, 0.131, 0.046, XyzMatrix.REC_2020_RGB);


	//
	final public String ALIAS;
	final public WhitePoint POINT;
	final public double XR;
	final public double YR;
	final public double ZR;
	final public double XG;
	final public double YG;
	final public double ZG;
	final public double XB;
	final public double YB;
	final public double ZB;
	final public XyzMatrix MATRIX;


	//
	RgbColorSpace(String alias, WhitePoint point, double xr, double yr, double xg, double yg, double xb, double yb, XyzMatrix matrix)
	{
		ALIAS = alias;
		POINT = point;
		XR = xr;
		YR = yr;
		ZR = 1 - xr - yr;
		XG = xg;
		YG = yg;
		ZG = 1 - xg - yg;
		XB = xb;
		YB = yb;
		ZB = 1 - xb - yb;
		MATRIX = matrix;
	}


	//
	@SuppressWarnings("UnnecessaryLocalVariable")
	public static void matrix(RgbColorSpace space)
	{
		// White Point Correction
		double xr = space.XR;
		double xg = space.XG;
		double xb = space.XB;
		double yr = space.YR;
		double yg = space.YG;
		double yb = space.YB;
		double zr = space.ZR;
		double zg = space.ZG;
		double zb = space.ZB;
		double xw = space.POINT.XW;
		double yw = space.POINT.YW;

		double D = (xr - xb) * (yg - yb) - (yr - yb) * (xg - xb);
		double U = (xw - xb) * (yg - yb) - (yw - yb) * (xg - xb);
		double V = (xr - xb) * (yw - yb) - (yr - yb) * (xw - xb);
		double u = U / D;
		double v = V / D;
		double w = 1 - u - v;

		double[][] arrayCxr =
		{
			{ u * xr / yw, v * xg / yw, w * xb / yw },
			{ u * yr / yw, v * yg / yw, w * yb / yw },
			{ u * zr / yw, v * zg / yw, w * zb / yw }
		};

		Matrix matrixCxr = new Matrix(arrayCxr);
		Matrix matrixCrx = matrixCxr.inverse();
		double[][] arrayCrx = matrixCrx.getArray();

		System.out.println("Cxr");
		System.out.println(Arrays.toString(arrayCxr[0]));
		System.out.println(Arrays.toString(arrayCxr[1]));
		System.out.println(Arrays.toString(arrayCxr[2]));
		System.out.println();
		System.out.println("Crx");
		System.out.println(Arrays.toString(arrayCrx[0]));
		System.out.println(Arrays.toString(arrayCrx[1]));
		System.out.println(Arrays.toString(arrayCrx[2]));
	}


	//
	final public static class XyzMatrix
	{
		//
		final public double XR;
		final public double XG;
		final public double XB;
		final public double YR;
		final public double YG;
		final public double YB;
		final public double ZR;
		final public double ZG;
		final public double ZB;

		final public double RX;
		final public double GX;
		final public double BX;
		final public double RY;
		final public double GY;
		final public double BY;
		final public double RZ;
		final public double GZ;
		final public double BZ;

		//
		private XyzMatrix
		(
			double xr, double xg, double xb,
			double yr, double yg, double yb,
			double zr, double zg, double zb,

			double rx, double ry, double rz,
			double gx, double gy, double gz,
			double bx, double by, double bz
		)
		{
			XR = xr;
			XG = xg;
			XB = xb;
			YR = yr;
			YG = yg;
			YB = yb;
			ZR = zr;
			ZG = zg;
			ZB = zb;

			RX = rx;
			GX = gx;
			BX = bx;
			RY = ry;
			GY = gy;
			BY = by;
			RZ = rz;
			GZ = gz;
			BZ = bz;
		}

		//
		final public static XyzMatrix S_RGB = new XyzMatrix
		(
			0.41239079926595934, 0.357584339383878, 0.18048078840183426,
			0.21263900587151027, 0.715168678767756, 0.07219231536073371,
			0.019330818715591825, 0.11919477979462596, 0.9505321522496607,

			3.2409699419045226, -1.537383177570094, -0.4986107602930033,
			-0.9692436362808797, 1.8759675015077204, 0.041555057407175584,
			0.055630079696993635, -0.2039769588889765, 1.0569715142428784
		);

		final public static XyzMatrix ADOBE_RGB = new XyzMatrix
		(
			0.5766690429101307, 0.18555823790654632, 0.18822864623499463,
			0.29734497525053616, 0.6273635662554662, 0.07529145849399786,
			0.027031361386412347, 0.07068885253582724, 0.9913375368376386,

			2.041587903810746, -0.5650069742788596, -0.34473135077832945,
			-0.9692436362808797, 1.8759675015077202, 0.041555057407175605,
			0.013444280632031166, -0.11836239223101842, 1.0151749943912056
		);

		final public static XyzMatrix ADOBE_WIDE_GAMUT_RGB = new XyzMatrix
		(
			0.7165007167793861, 0.10102057439747676, 0.14677438525270484,
			0.2587282430401131, 0.7246823149485659, 0.016589442011321048,
			0.0, 0.05121181896538753, 0.7738927835450727,

			1.4623041819736533, -0.1845256396156741, -0.2733810476878729,
			-0.5228682776866814, 1.4479884041326982, 0.06812616874480507,
			0.034600446145748004, -0.09581962978997763, 1.2876604565487555
		);

		final public static XyzMatrix KODAK_PRO_PHOTO_RGB = new XyzMatrix
		(
			0.7977604896723025, 0.13518583717574034, 0.03134934958152481,
			0.28807112822929337, 0.7118432178101015, 8.565396060525903E-5,
			0.0, 0.0, 0.8251046025104602,

			1.3457989731028284, -0.25558010007997545, -0.05110628506753402,
			-0.5446224939028347, 1.508232741313278, 0.02053603239147973,
			0.0, 0.0, 1.2119675456389452
		);

		final public static XyzMatrix REC_709_RGB = new XyzMatrix
		(
			0.41239079926595934, 0.357584339383878, 0.18048078840183426,
			0.21263900587151027, 0.715168678767756, 0.07219231536073371,
			0.019330818715591825, 0.11919477979462596, 0.9505321522496607,

			3.2409699419045226, -1.537383177570094, -0.4986107602930033,
			-0.9692436362808797, 1.8759675015077204, 0.041555057407175584,
			0.055630079696993635, -0.2039769588889765, 1.0569715142428784
		);

		final public static XyzMatrix REC_2020_RGB = new XyzMatrix
		(
			0.6369580483012912, 0.1446169035862084, 0.16888097516417208,
			0.262700212011267, 0.677998071518871, 0.059301716469861945,
			4.994106574466074E-17, 0.028072693049087438, 1.0609850577107909,

			1.716651187971268, -0.3556707837763925, -0.2533662813736598,
			-0.6666843518324889, 1.6164812366349388, 0.01576854581391113,
			0.017639857445310783, -0.042770613257808524, 0.9421031212354739
		);
	}

}
