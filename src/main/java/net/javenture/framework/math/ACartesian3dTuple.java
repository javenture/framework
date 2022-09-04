package net.javenture.framework.math;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.util.ClassUtil;
import net.javenture.framework.util.DoubleUtil;


/*
	2021/01/23
 */
abstract public class ACartesian3dTuple
{
	//
	final public static double PRECISION_DEFAULT = 0.000000000000001;


	//
	public double x; // ! public
	public double y; // ! public
	public double z; // ! public


	//
	protected ACartesian3dTuple()
	{
		this(0, 0, 0);
	}


	protected ACartesian3dTuple(ACartesian3dTuple source)
	{
		this(source.x, source.y, source.z);
	}


	protected ACartesian3dTuple(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}


	//
	@Override
	final public String toString()
	{
		return "[" + ClassUtil.name(this) + "] (x: " + x + "; y: " + y + "; z: " + z + ")";
	}


	@Override
	final public int hashCode()
	{
        long result = 7L;
        result = 31L * result + Double.doubleToLongBits(x);
        result = 31L * result + Double.doubleToLongBits(y);
        result = 31L * result + Double.doubleToLongBits(z);

        return (int) (result ^ (result >> 32));
	}


	@Override
	@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
	final public boolean equals(@NullAllow Object object)
	{
		return equals((ACartesian3dTuple) object);
	}


	final public boolean equals(@NullAllow ACartesian3dTuple object)
	{
		return
			this == object
			||
			object != null
			&&
			this.x == object.x
			&&
			this.y == object.y
			&&
			this.z == object.z;
	}


	public boolean equals(@NullAllow ACartesian3dTuple object, double precision)
	{
		return
			this == object
			||
			object != null
			&&
			DoubleUtil.equal(this.x, object.x, precision)
			&&
			DoubleUtil.equal(this.y, object.y, precision)
			&&
			DoubleUtil.equal(this.z, object.z, precision);

	}


	public boolean equals(@NullAllow ACartesian3dTuple object, boolean approximation)
	{
		return approximation
			? equals(object, PRECISION_DEFAULT)
			: equals(object);
	}


	final public double x()
	{
		return x;
	}


	final public void x(double value)
	{
		x = value;
	}


	final public double y()
	{
		return y;
	}


	final public void y(double value)
	{
		y = value;
	}


	final public double z()
	{
		return z;
	}


	final public void z(double value)
	{
		z = value;
	}


	final public void set(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}


	final public void set(@NullDisallow ACartesian3dTuple tuple)
	{
		this.x = tuple.x;
		this.y = tuple.y;
		this.z = tuple.z;
	}


	final public void apply(Quaternion quaternion)
	{
		double x = this.x; // !
		double y = this.y; // !
		double z = this.z; // !

		double qw = quaternion.w;
		double qx = quaternion.x;
		double qy = quaternion.y;
		double qz = quaternion.z;

		double iw = -qx * x - qy * y - qz * z;
		double ix =  qw * x + qy * z - qz * y;
		double iy =  qw * y + qz * x - qx * z;
		double iz =  qw * z + qx * y - qy * x;

		this.x = ix * qw + iw * -qx + iy * -qz - iz * -qy;
		this.y = iy * qw + iw * -qy + iz * -qx - ix * -qz;
		this.z = iz * qw + iw * -qz + ix * -qy - iy * -qx;
	}


	//
	@FunctionalInterface
	public interface ITransformer
	{
		//
		ITransformer DEFAULT = tuple -> {};

		//
		void transform(ACartesian3dTuple tuple);
	}

}
