package net.javenture.framework.image;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.util.ClassUtil;


/*
	2020/09/26
 */
final public class RgbI16Color
	extends AI16Color<Integer, RgbI16Color.Channel>
{
	//
	public enum Channel
		implements II16ColorChannel
	{
		//
		R(RgbI16Color::r, RgbI16Color::r, RgbI16Color::r0),
		G(RgbI16Color::g, RgbI16Color::g, RgbI16Color::g0),
		B(RgbI16Color::b, RgbI16Color::b, RgbI16Color::b0);

		//
		final static Channel[] VALUES = values(); // ! package
		final public static int COUNT = VALUES.length;

		//
		final private IGetter<RgbI16Color> GETTER;
		final private ISetter<RgbI16Color> SETTER;
		final private ISetter<RgbI16Color> SETTER0;

		//
		Channel(II16ColorChannel.IGetter<RgbI16Color> getter, II16ColorChannel.ISetter<RgbI16Color> setter, ISetter<RgbI16Color> setter0)
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
	final static long VALUE_MIN_LONG = 0;
	final static float VALUE_MIN_FLOAT = 0;
	final static double VALUE_MIN_DOUBLE = 0;
	final static int VALUE_MAX_INT = 65535;
	final static long VALUE_MAX_LONG = 65535;
	final static float VALUE_MAX_FLOAT = 65535;
	final static double VALUE_MAX_DOUBLE = 65535;
	final static float DENOMINATOR_FLOAT = 1 / VALUE_MAX_FLOAT;
	final static double DENOMINATOR_DOUBLE = 1 / VALUE_MAX_DOUBLE;
	final static long MASK = 0xFFFF;
	final static int SHIFT_R = 32;
	final static int SHIFT_G = 16;
	final static int SHIFT_B = 0;
	final static long MASK_R = 0x0000_FFFF_0000_0000L;
	final static long MASK_G = 0x0000_0000_FFFF_0000L;
	final static long MASK_B = 0x0000_0000_0000_FFFFL;
	final static long MASK_RGB = 0x0000_FFFF_FFFF_FFFFL;
	final static long MASK_R_INVERT = ~MASK_R;
	final static long MASK_G_INVERT = ~MASK_G;
	final static long MASK_B_INVERT = ~MASK_B;


	//
	private long rgb;


	//
	public RgbI16Color()
	{
		this(R_INIT_INT, G_INIT_INT, B_INIT_INT);
	}


	public RgbI16Color(int r, int g, int b)
	{
		rgb =
			(RgbI16ColorUtil.range(r) << SHIFT_R)
			|
			(RgbI16ColorUtil.range(g) << SHIFT_G)
			|
			(RgbI16ColorUtil.range(b) << SHIFT_B);
	}


	public RgbI16Color(long r, long g, long b)
	{
		rgb =
			(RgbI16ColorUtil.range(r) << SHIFT_R)
			|
			(RgbI16ColorUtil.range(g) << SHIFT_G)
			|
			(RgbI16ColorUtil.range(b) << SHIFT_B);
	}


	public RgbI16Color(int value)
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
		return RgbI16ColorUtil.r(rgb);
	}


	public void r(int value)
	{
		rgb = RgbI16ColorUtil.r(rgb, value);
	}


	public void r(long value)
	{
		rgb = RgbI16ColorUtil.r(rgb, value);
	}


	public void r(double value)
	{
		rgb = RgbI16ColorUtil.r(rgb, value);
	}


	public void r0(int value)
	{
		rgb = RgbI16ColorUtil.r0(rgb, value);
	}


	public int g()
	{
		return RgbI16ColorUtil.g(rgb);
	}


	public void g(int value)
	{
		rgb = RgbI16ColorUtil.g(rgb, value);
	}


	public void g(long value)
	{
		rgb = RgbI16ColorUtil.g(rgb, value);
	}


	public void g(double value)
	{
		rgb = RgbI16ColorUtil.g(rgb, value);
	}


	public void g0(int value)
	{
		rgb = RgbI16ColorUtil.g0(rgb, value);
	}


	public int b()
	{
		return RgbI16ColorUtil.b(rgb);
	}


	public void b(int value)
	{
		rgb = RgbI16ColorUtil.b(rgb, value);
	}


	public void b(long value)
	{
		rgb = RgbI16ColorUtil.b(rgb, value);
	}


	public void b(double value)
	{
		rgb = RgbI16ColorUtil.b(rgb, value);
	}


	public void b0(int value)
	{
		rgb = RgbI16ColorUtil.b0(rgb, value);
	}


	public long rgb()
	{
		return rgb & MASK_RGB;
	}


	public void rgb(long value)
	{
		rgb = value & MASK_RGB;
	}


	public void rgb(int r, int g, int b)
	{
		rgb =
			(RgbI16ColorUtil.range(r) << SHIFT_R)
			|
			(RgbI16ColorUtil.range(g) << SHIFT_G)
			|
			(RgbI16ColorUtil.range(b) << SHIFT_B);
	}


	public void rgb0(int r, int g, int b)
	{
		rgb =
			(RgbI16ColorUtil.mask(r) << SHIFT_R)
			|
			(RgbI16ColorUtil.mask(g) << SHIFT_G)
			|
			(RgbI16ColorUtil.mask(b) << SHIFT_B);
	}

}
