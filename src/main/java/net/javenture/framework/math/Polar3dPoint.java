package net.javenture.framework.math;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.util.DoubleUtil;


/*
	2020/09/13
 */
final public class Polar3dPoint
{
	/*
		https://en.wikipedia.org/wiki/Spherical_coordinate_system

        Cartesian <-> Polar
		[1.0; 0.0; 0.0] <-> [1.0; 0.0; 0.0]
		[-1.0; 0.0; 0.0] <-> [1.0; π; 0.0]
		[0.0; 1.0; 0.0] <-> [1.0; π/2; 0.0]
		[0.0; -1.0; 0.0] <-> [1.0; -π/2; 0.0]
		[0.0; 0.0; 1.0] <-> [1.0; 0.0; π/2]
		[0.0; 0.0; -1.0] <-> [1.0; 0.0; -π/2]
	 */
	final public static double PRECISION_LINEAR = 0.000000000000001;
	final public static double PRECISION_ANGULAR = 0.000000000000001;


	//
	public Cartesian3dOrientation orientation; // ! public
	public double radius; // ! public
	public double azimuth; // ! public; azimuth = phi; (−π, +π]
	public double elevation; // ! public; elevation = theta; [−π/2, +π/2]


	//
	public Polar3dPoint()
	{
		this(Cartesian3dOrientation.DEFAULT, 0, 0, 0);
	}


	public Polar3dPoint(Cartesian3dOrientation orientation)
	{
		this(orientation, 0, 0, 0);
	}


	public Polar3dPoint(Polar3dPoint source)
	{
		this(source.orientation, source.radius, source.azimuth, source.elevation);
	}


	public Polar3dPoint(double radius, double azimuth, double elevation)
	{
		this(Cartesian3dOrientation.DEFAULT, radius, azimuth, elevation);
	}


	public Polar3dPoint(Cartesian3dOrientation orientation, double radius, double azimuth, double elevation)
	{
		this.orientation = orientation;
		this.radius = radius;
		this.azimuth = azimuth;
		this.elevation = elevation;
	}


	//
	@Override
	public int hashCode()
	{
        long result = 7L;
        result = 31L * result + orientation.ordinal();
        result = 31L * result + Double.doubleToLongBits(radius);
        result = 31L * result + Double.doubleToLongBits(azimuth);
        result = 31L * result + Double.doubleToLongBits(elevation);

        return (int) (result ^ (result >> 32));
	}


	@Override
	@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
	public boolean equals(@NullAllow Object object)
	{
		return equals((Polar3dPoint) object);
	}


	public boolean equals(@NullAllow Polar3dPoint object)
	{
		return
			this == object
			||
			object != null
			&&
			this.orientation == object.orientation
			&&
			this.radius == object.radius
			&&
			this.azimuth == object.azimuth
			&&
			this.elevation == object.elevation;
	}


	public boolean equals(@NullAllow Polar3dPoint object, double precisionLinear, double precisionAngular)
	{
		return
			this == object
			||
			object != null
			&&
			this.orientation == object.orientation
			&&
			DoubleUtil.equal(this.radius, object.radius, precisionLinear)
			&&
			DoubleUtil.equal(this.azimuth, object.azimuth, precisionAngular)
			&&
			DoubleUtil.equal(this.elevation, object.elevation, precisionAngular);
	}


	public boolean equals(@NullAllow Polar3dPoint object, boolean approximation)
	{
		return approximation
			? equals(object, PRECISION_LINEAR, PRECISION_ANGULAR)
			: equals(object);
	}


	@Override
	public String toString()
	{
		return "[orientation: " + orientation + "; radius: " + radius + "; azimuth: " + azimuth + "; elevation: " + elevation + "]";
	}


	public Cartesian3dOrientation orientation()
	{
		return orientation;
	}


	public void orientation(Cartesian3dOrientation value)
	{
		orientation = value;
	}


	public double radius()
	{
		return radius;
	}


	public void radius(double value)
	{
		radius = value;
	}


	public double azimuth()
	{
		return azimuth;
	}


	public void azimuth(double value)
	{
		azimuth = value;
	}


	public double elevation()
	{
		return elevation;
	}


	public void elevation(double value)
	{
		elevation = value;
	}


	public void set(double radius, double azimuth, double elevation)
	{
		this.radius = radius;
		this.azimuth = azimuth;
		this.elevation = elevation;
	}


	public void set(Cartesian3dOrientation orientation, double radius, double azimuth, double elevation)
	{
		this.orientation = orientation;
		this.radius = radius;
		this.azimuth = azimuth;
		this.elevation = elevation;
	}


	public void set(@NullDisallow Polar3dPoint point)
	{
		this.orientation = point.orientation;
		this.radius = point.radius;
		this.azimuth = point.azimuth;
		this.elevation = point.elevation;
	}


	public void normalize()
	{
		double radius = this.radius;
		double azimuth = this.azimuth;
		double elevation = this.elevation;

		// 1. elevation
		if (elevation < -MathUtil.PI_HALF)
		{
			int turn = (int) (-elevation / MathUtil.PI_DOUBLE);
			elevation += MathUtil.PI_DOUBLE * turn;

			if (elevation <= 0 && elevation >= -MathUtil.PI_HALF)
			{
				// blank
			}
			if (elevation < -MathUtil.PI_HALF && elevation > -MathUtil.PI_ONE_AND_A_HALF)
			{
				elevation += MathUtil.PI;
				elevation *= -1;
				azimuth += MathUtil.PI;
			}
			else
			{
				elevation += MathUtil.PI_DOUBLE;
			}
		}
		else if (elevation > MathUtil.PI_HALF)
		{
			int turn = (int) (elevation / MathUtil.PI_DOUBLE);
			elevation -= MathUtil.PI_DOUBLE * turn;

			if (elevation >= 0 && elevation <= MathUtil.PI_HALF)
			{
				// blank
			}
			if (elevation > MathUtil.PI_HALF && elevation < MathUtil.PI_ONE_AND_A_HALF)
			{
				elevation -= MathUtil.PI;
				elevation *= -1;
				azimuth += MathUtil.PI;
			}
			else
			{
				elevation -= MathUtil.PI_DOUBLE;
			}
		}

		// 2. azimuth
		if (azimuth <= -MathUtil.PI)
		{
			int turn = (int) (-azimuth / MathUtil.PI_DOUBLE);
			azimuth += MathUtil.PI_DOUBLE * turn;

			if (azimuth <= -MathUtil.PI) azimuth += MathUtil.PI_DOUBLE;
		}
		else if (azimuth > MathUtil.PI)
		{
			int turn = (int) (azimuth / MathUtil.PI_DOUBLE);
			azimuth -= MathUtil.PI_DOUBLE * turn;

			if (azimuth > MathUtil.PI) azimuth -= MathUtil.PI_DOUBLE;
		}

		// 3. radius
		if (radius < 0) radius = 0;

		//
		this.radius = radius;
		this.azimuth = azimuth;
		this.elevation = elevation;
	}

}
