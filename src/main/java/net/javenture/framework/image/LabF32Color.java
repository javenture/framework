package net.javenture.framework.image;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.util.ClassUtil;


/*
	2020/11/27
 */
final public class LabF32Color
	extends AF32Color<Float, LabF32Color.Channel>
{
	//
	public enum Channel
		implements IF32ColorChannel
	{
		//
		L(LabF32Color::l, LabF32Color::l, LabF32Color::l0),
		A(LabF32Color::a, LabF32Color::a, LabF32Color::a0),
		B(LabF32Color::b, LabF32Color::b, LabF32Color::b0);

		//
		final static Channel[] VALUES = values(); // ! package
		final public static int COUNT = VALUES.length;

		//
		final private IGetter<LabF32Color> GETTER;
		final private ISetter<LabF32Color> SETTER;
		final private ISetter<LabF32Color> SETTER0;

		//
		Channel(IGetter<LabF32Color> getter, ISetter<LabF32Color> setter, ISetter<LabF32Color> setter0)
		{
			GETTER = getter;
			SETTER = setter;
			SETTER0 = setter0;
		}
	}

	final public static float L_INIT_FLOAT = 0;
	final public static float L_MIN_FLOAT = 0;
	final public static float L_MAX_FLOAT = 1;
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

	final public static float A_INIT_FLOAT = 0;
	final public static float A_MIN_FLOAT = -1;
	final public static float A_MAX_FLOAT = 1;
	final public static float A_RANGE_FLOAT = A_MAX_FLOAT - A_MIN_FLOAT;
	final static float A_RANGE_DENOMINATOR_FLOAT = 1 / A_RANGE_FLOAT;
	final public static float A_SCALE_FLOAT = 128;
	final static float A_SCALE_DENOMINATOR_FLOAT = 1 / A_SCALE_FLOAT;
	final static double A_MIN_DOUBLE = A_MIN_FLOAT;
	final static double A_MAX_DOUBLE = A_MAX_FLOAT;
	final static double A_RANGE_DOUBLE = A_MAX_DOUBLE - A_MIN_DOUBLE;
	final static double A_RANGE_DENOMINATOR_DOUBLE = 1 / A_RANGE_DOUBLE;
	final static double A_SCALE_DOUBLE = A_SCALE_FLOAT;
	final static double A_SCALE_DENOMINATOR_DOUBLE = 1 / A_SCALE_DOUBLE;

	final public static float B_INIT_FLOAT = 0;
	final public static float B_MIN_FLOAT = -1;
	final public static float B_MAX_FLOAT = 1;
	final public static float B_RANGE_FLOAT = B_MAX_FLOAT - B_MIN_FLOAT;
	final static float B_RANGE_DENOMINATOR_FLOAT = 1 / B_RANGE_FLOAT;
	final public static float B_SCALE_FLOAT = 128;
	final static float B_SCALE_DENOMINATOR_FLOAT = 1 / B_SCALE_FLOAT;
	final static double B_MIN_DOUBLE = B_MIN_FLOAT;
	final static double B_MAX_DOUBLE = B_MAX_FLOAT;
	final static double B_RANGE_DOUBLE = B_MAX_DOUBLE - B_MIN_DOUBLE;
	final static double B_RANGE_DENOMINATOR_DOUBLE = 1 / B_RANGE_DOUBLE;
	final static double B_SCALE_DOUBLE = B_SCALE_FLOAT;
	final static double B_SCALE_DENOMINATOR_DOUBLE = 1 / B_SCALE_DOUBLE;

	final public static IColorInstance<LabF32Color> INSTANCE = LabF32Color::new;


	//
	private float l; // ! private
	private float a; // ! private
	private float b; // ! private


	//
	public LabF32Color()
	{
		this.l = L_INIT_FLOAT;
		this.a = A_INIT_FLOAT;
		this.b = B_INIT_FLOAT;
	}


	public LabF32Color(float l, float a, float b)
	{
		this.l = LabF32ColorUtil.rangeL(l);
		this.a = LabF32ColorUtil.rangeA(a);
		this.b = LabF32ColorUtil.rangeB(b);
	}


	public LabF32Color(double l, double a, double b)
	{
		this.l = LabF32ColorUtil.rangeL(l);
		this.a = LabF32ColorUtil.rangeA(a);
		this.b = LabF32ColorUtil.rangeB(b);
	}


	public LabF32Color(LabF32Color source)
	{
		this.l = source.l;
		this.a = source.a;
		this.b = source.b;
	}


	//
	@Override
	public String toString()
	{
		return "[" + ClassUtil.name(this) + "] (l: " + l + "; a: " + a + "; b: " + b + ")";
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
		destination[Channel.L.ordinal()] = l;
		destination[Channel.A.ordinal()] = a;
		destination[Channel.B.ordinal()] = b;
	}


	@Override
	public void set(float[] source)
	{
		lab(source[Channel.L.ordinal()], source[Channel.A.ordinal()], source[Channel.B.ordinal()]);
	}


	@Override
	public void set0(float[] source)
	{
		lab0(source[Channel.L.ordinal()], source[Channel.A.ordinal()], source[Channel.B.ordinal()]);
	}


	public float l()
	{
		return l;
	}


	public void l(float value)
	{
		l = LabF32ColorUtil.rangeL(value);
	}


	public void l(double value)
	{
		l = LabF32ColorUtil.rangeL(value);
	}


	public void l0(float value)
	{
		l = value;
	}


	public void l0(double value)
	{
		l = (float) value;
	}


	public float a()
	{
		return a;
	}


	public void a(float value)
	{
		a = LabF32ColorUtil.rangeA(value);
	}


	public void a(double value)
	{
		a = LabF32ColorUtil.rangeA(value);
	}


	public void a0(float value)
	{
		a = value;
	}


	public void a0(double value)
	{
		a = (float) value;
	}


	public float b()
	{
		return b;
	}


	public void b(float value)
	{
		b = LabF32ColorUtil.rangeB(value);
	}


	public void b(double value)
	{
		b = LabF32ColorUtil.rangeB(value);
	}


	public void b0(float value)
	{
		b = value;
	}


	public void b0(double value)
	{
		b = (float) value;
	}


	public void lab(float l, float a, float b)
	{
		this.l = LabF32ColorUtil.rangeL(l);
		this.a = LabF32ColorUtil.rangeA(a);
		this.b = LabF32ColorUtil.rangeB(b);
	}


	public void lab0(float l, float a, float b)
	{
		this.l = l;
		this.a = a;
		this.b = b;
	}


	public void lab(double l, double a, double b)
	{
		this.l = LabF32ColorUtil.rangeL(l);
		this.a = LabF32ColorUtil.rangeA(a);
		this.b = LabF32ColorUtil.rangeB(b);
	}


	public void lab0(double l, double a, double b)
	{
		this.l = (float) l;
		this.a = (float) a;
		this.b = (float) b;
	}






	public void set(LabF32Color source)                                           // XXX override
	{
		this.l = LabF32ColorUtil.rangeL(source.l);
		this.a = LabF32ColorUtil.rangeA(source.a);
		this.b = LabF32ColorUtil.rangeB(source.b);
	}


	public void set0(LabF32Color source)                                           // XXX override
	{
		this.l = source.l;
		this.a = source.a;
		this.b = source.b;
	}


	public void normalize()
	{
		l = LabF32ColorUtil.rangeL(l);
		a = LabF32ColorUtil.rangeA(a);
		b = LabF32ColorUtil.rangeB(b);
	}

}
