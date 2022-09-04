package net.javenture.framework.image;


import net.javenture.framework.annotation.SingleThread;
import net.javenture.framework.math.MathUtil;

import java.awt.*;


/*
	2022/02/11
 */
@SingleThread
final public class ColorConverter
{
	//
	final private static double XYZ_K0_DENOMINATOR = 1.0 / 1.055; // = 0.94786729857819905213270142180095
	final private static double XYZ_K1_DENOMINATOR = 1.0 / 12.92; // = 0.07739938080495356037151702786378
	final private static double LAB_DELTA = 6.0 / 29.0; // = 24.0 / 116.0 = 0.20689655172413793103448275862069
	final private static double LAB_K0 = LAB_DELTA * LAB_DELTA * LAB_DELTA; // = 0.00885645167903563081717167575546
	final private static double LAB_K1 = 1.0 / (3 * LAB_DELTA * LAB_DELTA); // = 7.787037037037037037037037037037
	final private static double LAB_K2 = 16.0 / 116.0; // = 4.0 / 29.0 = 0.13793103448275862068965517241379
	final private static double LAB_K3_DENOMINATOR = 1.0 / 116.0; // = 0.00862068965517241379310344827586
	final private static double LAB_K4_DENOMINATOR = 1.0 / 500.0; // = 0.002
	final private static double LAB_K5_DENOMINATOR = 1.0 / 200.0; // = 0.005
	final private static double LAB_K6_DENOMINATOR = 3 * LAB_DELTA * LAB_DELTA; // = 0.12841854934601664684898929845422


	//
	final private XyzF32Color COLOR_XYZ_F32;
	final private LabF32Color COLOR_LAB_F32;
	final private RgbF32Color COLOR_RGB_F32;


	//
	public ColorConverter() // !
	{
		COLOR_XYZ_F32 = new XyzF32Color();
		COLOR_LAB_F32 = new LabF32Color();
		COLOR_RGB_F32 = new RgbF32Color();
	}


	//
	public void convert(RgbI8Color source, RgbI16Color destination)
	{
		float r = RgbI8ColorUtil.normalize32(source.r());
		float g = RgbI8ColorUtil.normalize32(source.g());
		float b = RgbI8ColorUtil.normalize32(source.b());

		int r2 = RgbI16ColorUtil.scale32(r);
		int g2 = RgbI16ColorUtil.scale32(g);
		int b2 = RgbI16ColorUtil.scale32(b);
		destination.rgb0(r2, g2, b2); // ! rgb0
	}


	public void convert(RgbI16Color source, RgbI8Color destination)
	{
		float r = RgbI16ColorUtil.normalize32(source.r());
		float g = RgbI16ColorUtil.normalize32(source.g());
		float b = RgbI16ColorUtil.normalize32(source.b());

		int r2 = RgbI8ColorUtil.scale32(r);
		int g2 = RgbI8ColorUtil.scale32(g);
		int b2 = RgbI8ColorUtil.scale32(b);
		destination.rgb0(r2, g2, b2); // ! rgb0
	}


	public void convert(RgbI8Color source, RgbF32Color destination)
	{
		float r = RgbI8ColorUtil.normalize32(source.r());
		float g = RgbI8ColorUtil.normalize32(source.g());
		float b = RgbI8ColorUtil.normalize32(source.b());
		destination.rgb0(r, g, b); // ! rgb0
	}


	public void convert(RgbF32Color source, RgbI8Color destination)
	{
		int r = RgbI8ColorUtil.scale32(source.r());
		int g = RgbI8ColorUtil.scale32(source.g());
		int b = RgbI8ColorUtil.scale32(source.b());
		destination.rgb(r, g, b); // ! rgb
	}


	public void convert(RgbI12Color source, RgbF32Color destination)
	{
		float r = RgbI12ColorUtil.normalize32(source.r());
		float g = RgbI12ColorUtil.normalize32(source.g());
		float b = RgbI12ColorUtil.normalize32(source.b());
		destination.rgb0(r, g, b); // ! rgb0
	}


	public void convert(RgbF32Color source, RgbI12Color destination)
	{
		int r = RgbI12ColorUtil.scale32(source.r());
		int g = RgbI12ColorUtil.scale32(source.g());
		int b = RgbI12ColorUtil.scale32(source.b());
		destination.rgb(r, g, b); // ! rgb
	}


	public void convert(RgbI16Color source, RgbF32Color destination)
	{
		float r = RgbI16ColorUtil.normalize32(source.r());
		float g = RgbI16ColorUtil.normalize32(source.g());
		float b = RgbI16ColorUtil.normalize32(source.b());
		destination.rgb0(r, g, b); // ! rgb0
	}


	public void convert(RgbF32Color source, RgbI16Color destination)
	{
		int r = RgbI16ColorUtil.scale32(source.r());
		int g = RgbI16ColorUtil.scale32(source.g());
		int b = RgbI16ColorUtil.scale32(source.b());
		destination.rgb(r, g, b); // ! rgb
	}


	public void convert(RgbI8Color source, LabF32Color destination, RgbColorSpace space)
	{
		convert(source, COLOR_XYZ_F32, space);
		convert(COLOR_XYZ_F32, destination, space.POINT);
	}


	public void convert(LabF32Color source, RgbI8Color destination, RgbColorSpace space)
	{
		COLOR_LAB_F32.set(source); // ! set
		convert(COLOR_LAB_F32, COLOR_XYZ_F32, space.POINT);
		convert(COLOR_XYZ_F32, destination, space);
	}


	public void convert(RgbI12Color source, LabF32Color destination, RgbColorSpace space)
	{
		convert(source, COLOR_XYZ_F32, space);
		convert(COLOR_XYZ_F32, destination, space.POINT);
	}


	public void convert(LabF32Color source, RgbI12Color destination, RgbColorSpace space)
	{
		COLOR_LAB_F32.set(source); // ! set
		convert(COLOR_LAB_F32, COLOR_XYZ_F32, space.POINT);
		convert(COLOR_XYZ_F32, destination, space);
	}


	public void convert(RgbI16Color source, LabF32Color destination, RgbColorSpace space)
	{
		convert(source, COLOR_XYZ_F32, space);
		convert(COLOR_XYZ_F32, destination, space.POINT);
	}


	public void convert(LabF32Color source, RgbI16Color destination, RgbColorSpace space)
	{
		COLOR_LAB_F32.set(source); // ! set
		convert(COLOR_LAB_F32, COLOR_XYZ_F32, space.POINT);
		convert(COLOR_XYZ_F32, destination, space);
	}


	public void convert(RgbF32Color source, XyzF32Color destination, RgbColorSpace space)
	{
		double r = source.r();
		double g = source.g();
		double b = source.b();
		RgbColorSpace.XyzMatrix matrix = space.MATRIX;

		r = r > 0.03928
			? MathUtil.pow((r + 0.055) * XYZ_K0_DENOMINATOR, 2.4)
			: r * XYZ_K1_DENOMINATOR;

		g = g > 0.03928
			? MathUtil.pow((g + 0.055) * XYZ_K0_DENOMINATOR, 2.4)
			: g * XYZ_K1_DENOMINATOR;

		b = b > 0.03928
			? MathUtil.pow((b + 0.055) * XYZ_K0_DENOMINATOR, 2.4)
			: b * XYZ_K1_DENOMINATOR;

		double x = matrix.XR * r + matrix.XG * g + matrix.XB * b;
		double y = matrix.YR * r + matrix.YG * g + matrix.YB * b;
		double z = matrix.ZR * r + matrix.ZG * g + matrix.ZB * b;
		destination.xyz0(x, y, z);
	}


	public void convert(RgbI8Color source, XyzF32Color destination, RgbColorSpace space)
	{
		convert(source, COLOR_RGB_F32);
		convert(COLOR_RGB_F32, destination, space);
	}


	public void convert(RgbI12Color source, XyzF32Color destination, RgbColorSpace space)
	{
		convert(source, COLOR_RGB_F32);
		convert(COLOR_RGB_F32, destination, space);
	}


	public void convert(RgbI16Color source, XyzF32Color destination, RgbColorSpace space)
	{
		convert(source, COLOR_RGB_F32);
		convert(COLOR_RGB_F32, destination, space);
	}


	public void convert(XyzF32Color source, RgbF32Color destination, RgbColorSpace space)
	{
		double x = source.x();
		double y = source.y();
		double z = source.z();
		RgbColorSpace.XyzMatrix matrix = space.MATRIX;

		double r = matrix.RX * x + matrix.RY * y + matrix.RZ * z;
		double g = matrix.GX * x + matrix.GY * y + matrix.GZ * z;
		double b = matrix.BX * x + matrix.BY * y + matrix.BZ * z;

		r = r > 0.00304
			? 1.055 * Math.pow(r, 1.0 / 2.4) - 0.055
			: 12.92 * r;

		g = g > 0.00304
			? 1.055 * Math.pow(g, 1.0 / 2.4) - 0.055
			: 12.92 * g;

		b = b > 0.00304
			? 1.055 * Math.pow(b, 1.0 / 2.4) - 0.055
			: 12.92 * b;

		destination.rgb0(r, g, b); // ! rgb0
	}


	public void convert(XyzF32Color source, RgbI8Color destination, RgbColorSpace space)
	{
		convert(source, COLOR_RGB_F32, space);
		convert(COLOR_RGB_F32, destination);
	}


	public void convert(XyzF32Color source, RgbI12Color destination, RgbColorSpace space)
	{
		convert(source, COLOR_RGB_F32, space);
		convert(COLOR_RGB_F32, destination);
	}


	public void convert(XyzF32Color source, RgbI16Color destination, RgbColorSpace space)
	{
		convert(source, COLOR_RGB_F32, space);
		convert(COLOR_RGB_F32, destination);
	}


	public void convert(XyzF32Color source, LabF32Color destination, WhitePoint point)
	{
		double x = source.x() * point.XWN_DENOMINATOR;
		double y = source.y() * point.YWN_DENOMINATOR;
		double z = source.z() * point.ZWN_DENOMINATOR;

		x = x > LAB_K0
			? MathUtil.pow(x, 1.0 / 3.0)
			: LAB_K1 * x + LAB_K2;

		y = y > LAB_K0
			? MathUtil.pow(y, 1.0 / 3.0)
			: LAB_K1 * y + LAB_K2;

		z = z > LAB_K0
			? MathUtil.pow(z, 1.0 / 3.0)
			: LAB_K1 * z + LAB_K2;

		double l = (116 * y - 16) * LabF32Color.L_SCALE_DENOMINATOR_DOUBLE;
		double a = 500 * (x - y) * LabF32Color.A_SCALE_DENOMINATOR_DOUBLE;
		double b = 200 * (y - z) * LabF32Color.B_SCALE_DENOMINATOR_DOUBLE;
		destination.lab0(l, a, b); // ! lab0
	}


	public void convert(LabF32Color source, XyzF32Color destination, WhitePoint point)
	{
		double l = source.l();
		double a = source.a();
		double b = source.b();

		double y = (l * LabF32Color.L_SCALE_DOUBLE + 16) * LAB_K3_DENOMINATOR;
		double x = y + a * LabF32Color.A_SCALE_DOUBLE * LAB_K4_DENOMINATOR;
		double z = y - b * LabF32Color.B_SCALE_DOUBLE * LAB_K5_DENOMINATOR;

		x = x > LAB_DELTA
			? x * x * x
			: (x - LAB_K2) * LAB_K6_DENOMINATOR;

		y = y > LAB_DELTA
			? y * y * y
			: (y - LAB_K2) * LAB_K6_DENOMINATOR;

		z = z > LAB_DELTA
			? z * z * z
			: (z - LAB_K2) * LAB_K6_DENOMINATOR;

		x *= point.XWN;
		y *= point.YWN;
		z *= point.ZWN;
		destination.xyz0(x, y, z); // ! xyz0
	}


	public void convert(RgbI8Color source, HslF32Color destination)
	{
		convert(source, COLOR_RGB_F32);
		convert(COLOR_RGB_F32, destination);
	}


	public void convert(RgbF32Color source, HslF32Color destination)
	{
		float r = source.r();
		float g = source.g();
		float b = source.b();

		float min = Math.min(r, Math.min(g, b));
		float max = Math.max(r, Math.max(g, b));

		// Hue
		float h = 0;

		if (max == min) h = 0;
		else if (max == r) h = (60 * (g - b) / (max - min) + 360) % 360;
		else if (max == g) h = 60 * (b - r) / (max - min) + 120;
		else if (max == b) h = 60 * (r - g) / (max - min) + 240;

		// Luminance
		float l = (max + min) / 2;

		// Saturation
		float s = 0;

		if (max == min) s = 0;
		else if (l <= 0.5f) s = (max - min) / (max + min);
		else s = (max - min) / (2 - max - min);

		destination.hsl0(h, s * 100, l * 100);
	}


	public void convert(HslF32Color source, RgbI8Color destination)
	{
		convert(source, COLOR_RGB_F32);
		convert(COLOR_RGB_F32, destination);
	}


	public void convert(HslF32Color source, RgbF32Color destination)
	{
		float h = source.h();
		float s = source.s();
		float l = source.l();

		h = h % 360.0f;
		h /= 360f;
		s /= 100f;
		l /= 100f;

		float q = l < 0.5 ? l * (1 + s) : (l + s) - (s * l);
		float p = 2 * l - q;

		float r = Math.max(0, convertHueToRgb0(p, q, h + 1.0f / 3.0f));
		float g = Math.max(0, convertHueToRgb0(p, q, h));
		float b = Math.max(0, convertHueToRgb0(p, q, h - 1.0f / 3.0f));

		r = Math.min(r, 1.0f);
		g = Math.min(g, 1.0f);
		b = Math.min(b, 1.0f);

		destination.rgb0(r, g, b);
	}


	private static float convertHueToRgb0(float p, float q, float h)
	{
		if (h < 0) h += 1;

		if (h > 1) h -= 1;

		if (6 * h < 1) return p + ((q - p) * 6 * h);

		if (2 * h < 1) return  q;

		if (3 * h < 2) return p + ( (q - p) * 6 * (2.0f / 3.0f - h));

		return p;
	}

}
