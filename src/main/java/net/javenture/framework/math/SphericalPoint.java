package net.javenture.framework.math;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.util.ClassUtil;


/*
	2021/01/23
 */
final public class SphericalPoint                                                                                                     // TEST
{
	/*
		https://en.wikipedia.org/wiki/Spherical_coordinate_system
	 */
	public Cartesian3dOrientation orientation; // ! public
	public double radius; // ! public                                                                  ??? public
	public double azimuth; // ! public; azimuth = phi; (−π, +π]                                        ??? public
	public double inclination; // ! public; inclination = theta; [0, +π]                               ??? public


	//
	public SphericalPoint()
	{
		this(Cartesian3dOrientation.DEFAULT, 0, 0, 0);
	}


	public SphericalPoint(Cartesian3dOrientation orientation)
	{
		this(orientation, 0, 0, 0);
	}


	public SphericalPoint(double radius, double azimuth, double inclination)
	{
		this(Cartesian3dOrientation.DEFAULT, radius, azimuth, inclination);
	}


	public SphericalPoint(Cartesian3dOrientation orientation, double radius, double azimuth, double inclination)
	{
		this.orientation = orientation;
		this.radius = radius;
		this.azimuth = azimuth;
		this.inclination = inclination;
	}


	public SphericalPoint(Polar3dPoint source)
	{
		this(source.orientation, source.radius, source.azimuth, source.elevation);
	}


	//
	@Override
	public int hashCode()
	{
        long result = 7L;
        result = 31L * result + orientation.ordinal();
        result = 31L * result + Double.doubleToLongBits(radius);
        result = 31L * result + Double.doubleToLongBits(azimuth);
        result = 31L * result + Double.doubleToLongBits(inclination);

        return (int) (result ^ (result >> 32));
	}


	@Override
	@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
	public boolean equals(@NullAllow Object object)
	{
		return equals((SphericalPoint) object);
	}


	public boolean equals(@NullAllow SphericalPoint object)
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
			this.inclination == object.inclination;
	}


	@Override
	public String toString()
	{
		return ClassUtil.name(this) + " [orientation: " + orientation + "; radius: " + radius + "; azimuth: " + azimuth + "; inclination: " + inclination + "]";
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


	public double inclination()
	{
		return inclination;
	}


	public void inclination(double value)
	{
		inclination = value;
	}


	public void set(double radius, double azimuth, double inclination)
	{
		this.radius = radius;
		this.azimuth = azimuth;
		this.inclination = inclination;
	}


	public void set(Cartesian3dOrientation orientation, double radius, double azimuth, double inclination)
	{
		this.orientation = orientation;
		this.radius = radius;
		this.azimuth = azimuth;
		this.inclination = inclination;
	}


	public void set(@NullDisallow Polar3dPoint tuple)
	{
		this.orientation = tuple.orientation;
		this.radius = tuple.radius;
		this.azimuth = tuple.azimuth;
		this.inclination = tuple.elevation;
	}

}
