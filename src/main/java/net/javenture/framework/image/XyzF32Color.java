package net.javenture.framework.image;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.util.ClassUtil;


/*
	2020/09/26
 */
final public class XyzF32Color
	extends AF32Color<Float, XyzF32Color.Channel>
{
	//
	public enum Channel
		implements IF32ColorChannel
	{
		//
		X(XyzF32Color::x, XyzF32Color::x, XyzF32Color::x0),
		Y(XyzF32Color::y, XyzF32Color::y, XyzF32Color::y0),
		Z(XyzF32Color::z, XyzF32Color::z, XyzF32Color::z0);

		//
		final static Channel[] VALUES = values(); // ! package
		final public static int COUNT = VALUES.length;

		//
		final private IGetter<XyzF32Color> GETTER;
		final private ISetter<XyzF32Color> SETTER;
		final private ISetter<XyzF32Color> SETTER0;

		//
		Channel(IGetter<XyzF32Color> getter, ISetter<XyzF32Color> setter, ISetter<XyzF32Color> setter0)
		{
			GETTER = getter;
			SETTER = setter;
			SETTER0 = setter0;
		}
	}


	//
	private float x; // ! private
	private float y; // ! private
	private float z; // ! private


	//
	public XyzF32Color()
	{
		this(0, 0, 0);
	}


	public XyzF32Color(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}


	public XyzF32Color(double x, double y, double z)
	{
		this.x = (float) x;
		this.y = (float) y;
		this.z = (float) z;
	}


	//
	@Override
	public String toString()
	{
		return "[" + ClassUtil.name(this) + "] (x: " + x + "; y: " + y + "; z: " + z + ")";
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
		destination[Channel.X.ordinal()] = x;
		destination[Channel.Y.ordinal()] = y;
		destination[Channel.Z.ordinal()] = z;
	}


	@Override
	public void set(float[] source)
	{
		xyz(source[Channel.X.ordinal()], source[Channel.Y.ordinal()], source[Channel.Z.ordinal()]);
	}


	@Override
	public void set0(float[] source)
	{
		xyz0(source[Channel.X.ordinal()], source[Channel.Y.ordinal()], source[Channel.Z.ordinal()]);
	}


	public float x()
	{
		return x;
	}


	public void x(float value)
	{
		x = value;
	}


	public void x(double value)
	{
		x = (float) value;
	}


	public void x0(float value)
	{
		x = value;
	}


	public void x0(double value)
	{
		x = (float) value;
	}


	public float y()
	{
		return y;
	}


	public void y(float value)
	{
		y = value;
	}


	public void y(double value)
	{
		y = (float) value;
	}


	public void y0(float value)
	{
		y = value;
	}


	public void y0(double value)
	{
		y = (float) value;
	}


	public float z()
	{
		return z;
	}


	public void z(float value)
	{
		z = value;
	}


	public void z(double value)
	{
		z = (float) value;
	}


	public void z0(float value)
	{
		z = value;
	}


	public void z0(double value)
	{
		z = (float) value;
	}


	public void xyz(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}


	public void xyz0(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}


	public void xyz(double x, double y, double z)
	{
		this.x = (float) x;
		this.y = (float) y;
		this.z = (float) z;
	}


	public void xyz0(double x, double y, double z)
	{
		this.x = (float) x;
		this.y = (float) y;
		this.z = (float) z;
	}

}
