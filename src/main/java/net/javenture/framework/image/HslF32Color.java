package net.javenture.framework.image;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.util.ClassUtil;


/**
 * 	http://www.camick.com/java/source/HSLColor.java
 *
 *  The HSLColor class provides methods to manipulate HSL (Hue, Saturation
 *  Luminance) values to create a corresponding Color object using the RGB
 *  ColorSpace.
 *
 *  The HUE is the color, the Saturation is the purity of the color (with
 *  respect to grey) and Luminance is the brightness of the color (with respect
 *  to black and white)
 *
 *  The Hue is specified as an angel between 0 - 360 degrees where red is 0,
 *  green is 120 and blue is 240. In between you have the colors of the rainbow.
 *  Saturation is specified as a percentage between 0 - 100 where 100 is fully
 *  saturated and 0 approaches gray. Luminance is specified as a percentage
 *  between 0 - 100 where 0 is black and 100 is white.
 *
 *  In particular the HSL color space makes it easier change the Tone or Shade
 *  of a color by adjusting the luminance value.
 */
final public class HslF32Color
	extends AF32Color<Float, HslF32Color.Channel>
{
	//
	public enum Channel
		implements IF32ColorChannel
	{
		//
		H(HslF32Color::h, HslF32Color::h, HslF32Color::h0),
		S(HslF32Color::s, HslF32Color::s, HslF32Color::s0),
		L(HslF32Color::l, HslF32Color::l, HslF32Color::l0);

		//
		final static HslF32Color.Channel[] VALUES = values(); // ! package
		final public static int COUNT = VALUES.length;

		//
		final private IGetter<HslF32Color> GETTER;
		final private ISetter<HslF32Color> SETTER;
		final private ISetter<HslF32Color> SETTER0;

		//
		Channel(IGetter<HslF32Color> getter, ISetter<HslF32Color> setter, ISetter<HslF32Color> setter0)
		{
			GETTER = getter;
			SETTER = setter;
			SETTER0 = setter0;
		}
	}

	final public static float H_INIT_FLOAT = 0;
	final public static float H_MIN_FLOAT = 0;
	final public static float H_MAX_FLOAT = 360;
	final public static float H_RANGE_FLOAT = H_MAX_FLOAT - H_MIN_FLOAT;
	final static float H_RANGE_DENOMINATOR_FLOAT = 1 / H_RANGE_FLOAT;
	final public static float H_SCALE_FLOAT = 360;
	final static float H_SCALE_DENOMINATOR_FLOAT = 1 / H_SCALE_FLOAT;
	final static double H_MIN_DOUBLE = H_MIN_FLOAT;
	final static double H_MAX_DOUBLE = H_MAX_FLOAT;
	final static double H_RANGE_DOUBLE = H_MAX_DOUBLE - H_MIN_DOUBLE;
	final static double H_RANGE_DENOMINATOR_DOUBLE = 1 / H_RANGE_DOUBLE;
	final static double H_SCALE_DOUBLE = H_SCALE_FLOAT;
	final static double H_SCALE_DENOMINATOR_DOUBLE = 1 / H_SCALE_DOUBLE;

	final public static float S_INIT_FLOAT = 0;
	final public static float S_MIN_FLOAT = 0;
	final public static float S_MAX_FLOAT = 100;
	final public static float S_RANGE_FLOAT = S_MAX_FLOAT - S_MIN_FLOAT;
	final static float S_RANGE_DENOMINATOR_FLOAT = 1 / S_RANGE_FLOAT;
	final public static float S_SCALE_FLOAT = 100;
	final static float S_SCALE_DENOMINATOR_FLOAT = 1 / S_SCALE_FLOAT;
	final static double S_MIN_DOUBLE = S_MIN_FLOAT;
	final static double S_MAX_DOUBLE = S_MAX_FLOAT;
	final static double S_RANGE_DOUBLE = S_MAX_DOUBLE - S_MIN_DOUBLE;
	final static double S_RANGE_DENOMINATOR_DOUBLE = 1 / S_RANGE_DOUBLE;
	final static double S_SCALE_DOUBLE = S_SCALE_FLOAT;
	final static double S_SCALE_DENOMINATOR_DOUBLE = 1 / S_SCALE_DOUBLE;

	final public static float L_INIT_FLOAT = 0;
	final public static float L_MIN_FLOAT = 0;
	final public static float L_MAX_FLOAT = 100;
	final public static float L_RANGE_FLOAT = L_MAX_FLOAT - L_MIN_FLOAT;
	final static float L_RANGE_DENOMINATOR_FLOAT = 1 / L_RANGE_FLOAT;
	final public static float L_SCALE_FLOAT = 100;
	final static float L_SCALE_DENOMINATOR_FLOAT = 1 / L_SCALE_FLOAT;
	final static double L_MIN_DOUBLE = L_MIN_FLOAT;
	final static double L_MAX_DOUBLE = L_MAX_FLOAT;
	final static double L_RANGE_DOUBLE = L_MAX_DOUBLE - L_MIN_DOUBLE;
	final static double L_RANGE_DENOMINATOR_DOUBLE = 1 / L_RANGE_DOUBLE;
	final static double L_SCALE_DOUBLE = L_SCALE_FLOAT;
	final static double L_SCALE_DENOMINATOR_DOUBLE = 1 / L_SCALE_DOUBLE;

	final public static IColorInstance<HslF32Color> INSTANCE = HslF32Color::new;


	//
	private float h; // ! private
	private float s; // ! private
	private float l; // ! private


	//
	public HslF32Color()
	{
		this.h = H_INIT_FLOAT;
		this.s = S_INIT_FLOAT;
		this.l = L_INIT_FLOAT;
	}


	public HslF32Color(float h, float s, float l)
	{
		this.h = HslF32ColorUtil.rangeH(h);
		this.s = HslF32ColorUtil.rangeS(s);
		this.l = HslF32ColorUtil.rangeL(l);
	}


	public HslF32Color(double h, double s, double l)
	{
		this.h = HslF32ColorUtil.rangeH(h);
		this.s = HslF32ColorUtil.rangeS(s);
		this.l = HslF32ColorUtil.rangeL(l);
	}


	public HslF32Color(HslF32Color source)
	{
		this.h = source.h;
		this.s = source.s;
		this.l = source.l;
	}


	//
	@Override
	public String toString()
	{
		return "[" + ClassUtil.name(this) + "] (h: " + h + "; h: " + s + "; l: " + l + ")";
	}


	@Override
	public @NullDisallow Float get(HslF32Color.Channel channel)
	{
		return channel.GETTER.get(this);
	}


	@Override
	public void set(HslF32Color.Channel channel, @NullDisallow Float value)
	{
		channel.SETTER.set(this, value);
	}


	@Override
	public void set0(HslF32Color.Channel channel, @NullDisallow Float value)
	{
		channel.SETTER0.set(this, value);
	}


	@Override
	public void set(HslF32Color.Channel channel, float value)
	{
		channel.SETTER.set(this, value);
	}


	@Override
	public void set0(HslF32Color.Channel channel, float value)
	{
		channel.SETTER0.set(this, value);
	}


	@Override
	public void get(float[] destination)
	{
		destination[HslF32Color.Channel.H.ordinal()] = h;
		destination[HslF32Color.Channel.S.ordinal()] = s;
		destination[HslF32Color.Channel.L.ordinal()] = l;
	}


	@Override
	public void set(float[] source)
	{
		hsl(source[HslF32Color.Channel.H.ordinal()], source[HslF32Color.Channel.S.ordinal()], source[HslF32Color.Channel.L.ordinal()]);
	}


	@Override
	public void set0(float[] source)
	{
		hsl0(source[HslF32Color.Channel.H.ordinal()], source[HslF32Color.Channel.S.ordinal()], source[HslF32Color.Channel.L.ordinal()]);
	}


	public float h()
	{
		return h;
	}


	public void h(float value)
	{
		h = HslF32ColorUtil.rangeH(value);
	}


	public void h(double value)
	{
		h = HslF32ColorUtil.rangeH(value);
	}


	public void h0(float value)
	{
		h = value;
	}


	public void h0(double value)
	{
		h = (float) value;
	}


	public float s()
	{
		return s;
	}


	public void s(float value)
	{
		s = HslF32ColorUtil.rangeS(value);
	}


	public void s(double value)
	{
		s = HslF32ColorUtil.rangeS(value);
	}


	public void s0(float value)
	{
		s = value;
	}


	public void s0(double value)
	{
		s = (float) value;
	}


	public float l()
	{
		return l;
	}


	public void l(float value)
	{
		l = HslF32ColorUtil.rangeL(value);
	}


	public void l(double value)
	{
		l = HslF32ColorUtil.rangeL(value);
	}


	public void l0(float value)
	{
		l = value;
	}


	public void l0(double value)
	{
		l = (float) value;
	}


	public void hsl(float h, float s, float l)
	{
		this.h = HslF32ColorUtil.rangeH(h);
		this.s = HslF32ColorUtil.rangeS(s);
		this.l = HslF32ColorUtil.rangeL(l);
	}


	public void hsl0(float h, float s, float l)
	{
		this.h = h;
		this.s = s;
		this.l = l;
	}


	public void hsl(double h, double s, double l)
	{
		this.h = HslF32ColorUtil.rangeH(h);
		this.s = HslF32ColorUtil.rangeS(s);
		this.l = HslF32ColorUtil.rangeL(l);
	}


	public void hsl0(double h, double s, double l)
	{
		this.h = (float) h;
		this.s = (float) s;
		this.l = (float) l;
	}






	public void set(HslF32Color source)                                           // XXX override
	{
		this.h = HslF32ColorUtil.rangeH(source.h);
		this.s = HslF32ColorUtil.rangeS(source.s);
		this.l = HslF32ColorUtil.rangeL(source.l);
	}


	public void set0(HslF32Color source)                                           // XXX override
	{
		this.h = source.h;
		this.s = source.s;
		this.l = source.l;
	}


	public void normalize()
	{
		h = HslF32ColorUtil.rangeH(h);
		s = HslF32ColorUtil.rangeS(s);
		l = HslF32ColorUtil.rangeL(l);
	}

}
