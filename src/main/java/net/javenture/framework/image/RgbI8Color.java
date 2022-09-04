package net.javenture.framework.image;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.util.ClassUtil;


/*
	2020/09/26
 */
final public class RgbI8Color
	extends AI8Color<Integer, RgbI8Color.Channel>
{
	//
	public enum Channel
		implements II8ColorChannel
	{
		//
		R(RgbI8Color::r, RgbI8Color::r, RgbI8Color::r0),
		G(RgbI8Color::g, RgbI8Color::g, RgbI8Color::g0),
		B(RgbI8Color::b, RgbI8Color::b, RgbI8Color::b0);

		//
		final static Channel[] VALUES = values(); // ! package
		final public static int COUNT = VALUES.length;

		//
		final private IGetter<RgbI8Color> GETTER;
		final private ISetter<RgbI8Color> SETTER;
		final private ISetter<RgbI8Color> SETTER0;

		//
		Channel(IGetter<RgbI8Color> getter, ISetter<RgbI8Color> setter, ISetter<RgbI8Color> setter0)
		{
			GETTER = getter;
			SETTER = setter;
			SETTER0 = setter0;
		}
	}

	final static int R_INIT_INT = 0;
	final static int G_INIT_INT = 0;
	final static int B_INIT_INT = 0;
	final static int VALUE_MIN_INT = 0;
	final static long VALUE_MIN_LONG = VALUE_MIN_INT;
	final static float VALUE_MIN_FLOAT = VALUE_MIN_INT;
	final static double VALUE_MIN_DOUBLE = VALUE_MIN_INT;
	final static int VALUE_MAX_INT = 255;
	final static long VALUE_MAX_LONG = VALUE_MAX_INT;
	final static float VALUE_MAX_FLOAT = VALUE_MAX_INT;
	final static double VALUE_MAX_DOUBLE = VALUE_MAX_INT;
	final static float DENOMINATOR_FLOAT = 1 / VALUE_MAX_FLOAT;
	final static double DENOMINATOR_DOUBLE = 1 / VALUE_MAX_DOUBLE;
	final static int MASK = 0xff;
	final static int SHIFT_R = 16;
	final static int SHIFT_G = 8;
	final static int SHIFT_B = 0;
	final static int MASK_R = 0x00ff_0000;
	final static int MASK_G = 0x0000_ff00;
	final static int MASK_B = 0x0000_00ff;
	final static int MASK_RGB = 0x00ff_ffff;
	final static int MASK_R_INVERT = ~MASK_R;
	final static int MASK_G_INVERT = ~MASK_G;
	final static int MASK_B_INVERT = ~MASK_B;


	//
	private int rgb; // ! private


	//
	public RgbI8Color()
	{
		this(R_INIT_INT, G_INIT_INT, B_INIT_INT);
	}


	public RgbI8Color(long r, long g, long b)
	{
		rgb = RgbI8ColorUtil.rgb(r, g, b);
	}


	public RgbI8Color(int value)
	{
		rgb = value;
	}


	//
	@Override
	public String toString()
	{
		return "[" + ClassUtil.name(this) + "] (r: " + r() + "; g: " + g() + "; b: " + b() + ")";
	}


	@Override
	public @NullDisallow Integer get(Channel channel)
	{
		return channel.GETTER.get(this);
	}


	@Override
	public void set(Channel channel, @NullDisallow Integer value)
	{
		channel.SETTER.set(this, value);
	}


	@Override
	public void set0(Channel channel, @NullDisallow Integer value)
	{
		channel.SETTER0.set(this, value);
	}


	@Override
	public void set(Channel channel, int value)
	{
		channel.SETTER.set(this, value);
	}


	@Override
	public void set0(Channel channel, int value)
	{
		channel.SETTER0.set(this, value);
	}


	@Override
	public void get(int[] destination)
	{
		destination[Channel.R.ordinal()] = r();
		destination[Channel.G.ordinal()] = g();
		destination[Channel.B.ordinal()] = b();
	}


	@Override
	public void set(int[] source)
	{
		rgb(source[Channel.R.ordinal()], source[Channel.G.ordinal()], source[Channel.B.ordinal()]);
	}


	@Override
	public void set0(int[] source)
	{
		rgb0(source[Channel.R.ordinal()], source[Channel.G.ordinal()], source[Channel.B.ordinal()]);
	}


	public int r()
	{
		return RgbI8ColorUtil.r(rgb);
	}


	public void r(int value)
	{
		rgb = RgbI8ColorUtil.r(rgb, value);
	}


	public void r(long value)
	{
		rgb = RgbI8ColorUtil.r(rgb, value);
	}


	public void r(double value)
	{
		rgb = RgbI8ColorUtil.r(rgb, value);
	}


	public void r0(int value)
	{
		rgb = RgbI8ColorUtil.r0(rgb, value);
	}


	public int g()
	{
		return RgbI8ColorUtil.g(rgb);
	}


	public void g(int value)
	{
		rgb = RgbI8ColorUtil.g(rgb, value);
	}


	public void g(long value)
	{
		rgb = RgbI8ColorUtil.g(rgb, value);
	}


	public void g(double value)
	{
		rgb = RgbI8ColorUtil.g(rgb, value);
	}


	public void g0(int value)
	{
		rgb = RgbI8ColorUtil.g0(rgb, value);
	}


	public int b()
	{
		return RgbI8ColorUtil.b(rgb);
	}


	public void b(int value)
	{
		rgb = RgbI8ColorUtil.b(rgb, value);
	}


	public void b(long value)
	{
		rgb = RgbI8ColorUtil.b(rgb, value);
	}


	public void b(double value)
	{
		rgb = RgbI8ColorUtil.b(rgb, value);
	}


	public void b0(int value)
	{
		rgb = RgbI8ColorUtil.b0(rgb, value);
	}


	public int rgb()
	{
		return rgb & MASK_RGB;
	}


	public void rgb(int value)
	{
		rgb = value & MASK_RGB;
	}


	public void rgb(int r, int g, int b)
	{
		rgb = RgbI8ColorUtil.rgb(r, g, b);
	}


	public void rgb0(int r, int g, int b)
	{
		rgb = RgbI8ColorUtil.rgb0(r, g, b);
	}

}
