package net.javenture.framework.image;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.util.ClassUtil;


/*
	2020/09/26
 */
final public class RgbF32Color
	extends AF32Color<Float, RgbF32Color.Channel>
{
	//
	public enum Channel
		implements IF32ColorChannel
	{
		//
		R(RgbF32Color::r, RgbF32Color::r, RgbF32Color::r0),
		G(RgbF32Color::g, RgbF32Color::g, RgbF32Color::g0),
		B(RgbF32Color::b, RgbF32Color::b, RgbF32Color::b0);

		//
		final static Channel[] VALUES = values(); // ! package
		final public static int COUNT = VALUES.length;

		//
		final private IGetter<RgbF32Color> GETTER;
		final private ISetter<RgbF32Color> SETTER;
		final private ISetter<RgbF32Color> SETTER0;

		//
		Channel(IGetter<RgbF32Color> getter, ISetter<RgbF32Color> setter, ISetter<RgbF32Color> setter0)
		{
			GETTER = getter;
			SETTER = setter;
			SETTER0 = setter0;
		}
	}

	final static float R_INIT_FLOAT = 0;
	final static float G_INIT_FLOAT = 0;
	final static float B_INIT_FLOAT = 0;
	final static float VALUE_MIN_FLOAT = 0;
	final static float VALUE_MIN_DOUBLE = 0;
	final static float VALUE_MAX_FLOAT = 1;
	final static float VALUE_MAX_DOUBLE = 1;


	//
	private float r; // ! private
	private float g; // ! private
	private float b; // ! private


	//
	public RgbF32Color()
	{
		this.r = R_INIT_FLOAT;
		this.g = G_INIT_FLOAT;
		this.b = B_INIT_FLOAT;
	}


	public RgbF32Color(float r, float g, float b)
	{
		this.r = RgbF32ColorUtil.range(r);
		this.g = RgbF32ColorUtil.range(g);
		this.b = RgbF32ColorUtil.range(b);
	}


	public RgbF32Color(double r, double g, double b)
	{
		this.r = RgbF32ColorUtil.range(r);
		this.g = RgbF32ColorUtil.range(g);
		this.b = RgbF32ColorUtil.range(b);
	}


	//
	@Override
	public String toString()
	{
		return "[" + ClassUtil.name(this) + "] (r: " + r + "; g: " + g + "; b: " + b + ")";
	}


	@Override
	public @NullDisallow Float get(Channel channel)
	{
		return channel.GETTER.get(this);
	}


	@Override
	public void set(Channel channel, @NullDisallow Float value)
	{
		channel.SETTER.set(this, value);
	}


	@Override
	public void set0(Channel channel, @NullDisallow Float value)
	{
		channel.SETTER0.set(this, value);
	}


	@Override
	public void set(Channel channel, float value)
	{
		channel.SETTER.set(this, value);
	}


	@Override
	public void set0(Channel channel, float value)
	{
		channel.SETTER0.set(this, value);
	}


	@Override
	public void get(float[] destination)
	{
		destination[Channel.R.ordinal()] = r;
		destination[Channel.G.ordinal()] = g;
		destination[Channel.B.ordinal()] = b;
	}


	@Override
	public void set(float[] source)
	{
		rgb
		(
			source[Channel.R.ordinal()],
			source[Channel.G.ordinal()],
			source[Channel.B.ordinal()]
		);
	}


	@Override
	public void set0(float[] source)
	{
		rgb0
		(
			source[Channel.R.ordinal()],
			source[Channel.G.ordinal()],
			source[Channel.B.ordinal()]
		);
	}


	public float r()
	{
		return r;
	}


	public void r(float value)
	{
		r = RgbF32ColorUtil.range(value);
	}


	public void r(double value)
	{
		r = RgbF32ColorUtil.range(value);
	}


	public void r0(float value)
	{
		r = value;
	}


	public void r0(double value)
	{
		r = (float) value;
	}


	public float g()
	{
		return g;
	}


	public void g(float value)
	{
		g = RgbF32ColorUtil.range(value);
	}


	public void g(double value)
	{
		g = RgbF32ColorUtil.range(value);
	}


	public void g0(float value)
	{
		g = value;
	}


	public void g0(double value)
	{
		g = (float) value;
	}


	public float b()
	{
		return b;
	}


	public void b(float value)
	{
		b = RgbF32ColorUtil.range(value);
	}


	public void b(double value)
	{
		b = RgbF32ColorUtil.range(value);
	}


	public void b0(float value)
	{
		b = value;
	}


	public void b0(double value)
	{
		b = (float) value;
	}


	public void rgb(float r, float g, float b)
	{
		this.r = RgbF32ColorUtil.range(r);
		this.g = RgbF32ColorUtil.range(g);
		this.b = RgbF32ColorUtil.range(b);
	}


	public void rgb0(float r, float g, float b)
	{
		this.r = r;
		this.g = g;
		this.b = b;
	}


	public void rgb(double r, double g, double b)
	{
		this.r = RgbF32ColorUtil.range(r);
		this.g = RgbF32ColorUtil.range(g);
		this.b = RgbF32ColorUtil.range(b);
	}


	public void rgb0(double r, double g, double b)
	{
		this.r = (float) r;
		this.g = (float) g;
		this.b = (float) b;
	}

}
