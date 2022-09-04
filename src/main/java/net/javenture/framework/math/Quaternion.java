package net.javenture.framework.math;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.util.Validator;


/*
	2021/06/28
 */
final public class Quaternion
{
	/*
		https://en.wikipedia.org/wiki/Quaternion
		https://en.wikipedia.org/wiki/Quaternions_and_spatial_rotation
		http://www.euclideanspace.com/maths/algebra/realNormedAlgebra/quaternions/code/index.htm
		https://www.euclideanspace.com/maths/geometry/rotations/conversions/quaternionToAngle/index.htm
		https://github.com/mrdoob/three.js/blob/dev/src/math/Quaternion.js
		https://github.com/mrdoob/three.js/blob/dev/src/math/Vector3.js
	 */

	//
	public double w; // ! public
	public double x; // ! public
	public double y; // ! public
	public double z; // ! public


	//
	public Quaternion()
	{
		this(1, 0, 0, 0);
	}


	public Quaternion(Quaternion source)
	{
		this(source.w, source.x, source.y, source.z);
	}


	public Quaternion(double w, double x, double y, double z)
	{
		// ! assumes axis is normalized
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
	}


	//
	@Override
	public int hashCode()
	{
        long result = 7L;
        result = 31L * result + Double.doubleToLongBits(w);
        result = 31L * result + Double.doubleToLongBits(x);
        result = 31L * result + Double.doubleToLongBits(y);
        result = 31L * result + Double.doubleToLongBits(z);

        return (int) (result ^ (result >> 32));
	}


	@Override
	@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
	public boolean equals(@NullAllow Object object)
	{
		return equals((Quaternion) object);
	}


	public boolean equals(@NullAllow Quaternion object)
	{
		return
			this == object
			||
			object != null
			&&
			this.w == object.w
			&&
			this.x == object.x
			&&
			this.y == object.y
			&&
			this.z == object.z;
	}


	@Override
	public String toString()
	{
		return "[w: " + w + "; x: " + x + "; y: " + y + "; z: " + z + "]";
	}


	public boolean activity()                                                                                       // ???
	{
		return
			w != 1
			||
			x != 0
			||
			y != 0
			||
			z != 0;
	}


	public void init()
	{
		set(1, 0, 0, 0);
	}


	public double w()
	{
		return w;
	}


	public void w(double value)
	{
		w = value;
	}


	public double x()
	{
		return x;
	}


	public void x(double value)
	{
		x = value;
	}


	public double y()
	{
		return y;
	}


	public void y(double value)
	{
		y = value;
	}


	public double z()
	{
		return z;
	}


	public void z(double value)
	{
		z = value;
	}


	public void set(Quaternion quaternion)
	{
		this.w = quaternion.w;
		this.x = quaternion.x;
		this.y = quaternion.y;
		this.z = quaternion.z;
	}


	public void set(double w, double x, double y, double z)
	{
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
	}


	public double length()
	{
        return MathUtil.sqrt(length2());
    }


	public double length2()
	{
        return w * w + x * x + y * y + z * z;
    }


	public void normalize()
	{
		double length = length();

		if (length != 0)
		{
			double k = 1 / length;
			w *= k;
			x *= k;
			y *= k;
			z *= k;
		}
		else
		{
			w = 1;
			x = 0;
			y = 0;
			z = 0;
		}
	}


	public void conjugate()
	{
		x = -x;
		y = -y;
		z = -z;
	}


	public void multiply(Quaternion quaternion)
	{
		double w0 = this.w; // !
		double x0 = this.x; // !
		double y0 = this.y; // !
		double z0 = this.z; // !

		double w = quaternion.w;
		double x = quaternion.x;
		double y = quaternion.y;
		double z = quaternion.z;

		this.w = w0 * w - x0 * x - y0 * y - z0 * z;
		this.x = w0 * x + x0 * w + y0 * z - z0 * y;
		this.y = w0 * y - x0 * z + y0 * w + z0 * x;
		this.z = w0 * z + x0 * y - y0 * x + z0 * w;
	}


	//
	public static Quaternion fromAxisAngle(AxisAngleConfig config)
	{
		double x = config.X;
		double y = config.Y;
		double z = config.Z;
		double angle = config.ANGLE;

		double a = angle / 2;
		double s = MathUtil.sin(a);
		double c = MathUtil.cos(a);

		return new Quaternion(c, x * s, y * s, z * s);
	}


	public static Quaternion fromAxisAngle(ACartesian3dTuple tuple, double angle, boolean normalized)
	{
		return fromAxisAngle(tuple.x, tuple.y, tuple.z, angle, normalized);
	}


	public static Quaternion fromAxisAngle(double x, double y, double z, double angle, boolean normalized)
	{
		return fromAxisAngle(new AxisAngleConfig(x, y, z, angle, normalized));
	}


	public static AxisAngleConfig toAxisAngle(Quaternion quaternion)
	{
		double qw = quaternion.w;
		double qx = quaternion.x;
		double qy = quaternion.y;
		double qz = quaternion.z;

		if (qw > 1) quaternion.normalize();

		double angle = 2 * MathUtil.acos(qw);
		double s = MathUtil.sqrt(1 - qw * qw);

		if (s < 0.00001)
		{
			return new AxisAngleConfig(qx, qy, qz, angle);
		}
		else
		{
			double k = 1 / s;

			return new AxisAngleConfig(qx * k, qy * k, qz * k, angle);
		}
	}


	//
	final public static class AxisAngleConfig
	{
		//
		final public double X; // ! final public
		final public double Y; // ! final public
		final public double Z; // ! final public
		final public double ANGLE; // ! final public

		//
		private AxisAngleConfig(double x, double y, double z, double angle)
		{
			X = x;
			Y = y;
			Z = z;
			ANGLE = angle;
		}

		public AxisAngleConfig(double x, double y, double z, double angle, boolean normalized)
		{
			if (normalized)
			{
				X = x;
				Y = y;
				Z = z;
				ANGLE = angle;
			}
			else
			{
				if (x != 0 && y != 0 && z != 0)
				{
					double length = MathUtil.sqrt(x * x + y * y + z * z);
					double k = 1 / length;
					X = x * k;
					Y = y * k;
					Z = z * k;
					ANGLE = angle;
				}
				else
				{
					X = 1;
					Y = 0;
					Z = 0;
					ANGLE = 0;
				}
			}
		}
	}

}
