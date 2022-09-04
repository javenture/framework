package net.javenture.framework.math;


import net.javenture.framework.annotation.NullAllow;


/*
	2020/09/08
 */
final public class CylindricalPoint                                                                         // TEST
{
	/*
		https://en.wikipedia.org/wiki/Cylindrical_coordinate_system
	 */
	public Cartesian3dOrientation orientation; // ! public
	public double radius; // ! public
	public double azimuth; // ! public; -PI ... +PI
	public double height; // ! public


	//
	protected CylindricalPoint()
	{
		this(Cartesian3dOrientation.DEFAULT, 0, 0, 0);
	}


	protected CylindricalPoint(Cartesian3dOrientation orientation)
	{
		this(orientation, 0, 0, 0);
	}


	protected CylindricalPoint(CylindricalPoint source)
	{
		this(source.orientation, source.radius, source.azimuth, source.height);
	}


	protected CylindricalPoint(double radius, double azimuth, double height)
	{
		this(Cartesian3dOrientation.DEFAULT, radius, azimuth, height);
	}


	protected CylindricalPoint(Cartesian3dOrientation orientation, double radius, double azimuth, double height)
	{
		this.orientation = orientation;
		this.radius = radius;
		this.azimuth = azimuth;
		this.height = height;
	}


	//
	@Override
	final public int hashCode()
	{
        long result = 7L;
        result = 31L * result + orientation.ordinal();
        result = 31L * result + Double.doubleToLongBits(radius);
        result = 31L * result + Double.doubleToLongBits(azimuth);
        result = 31L * result + Double.doubleToLongBits(height);

        return (int) (result ^ (result >> 32));
	}


	@Override
	@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
	public boolean equals(@NullAllow Object object)
	{
		return equals((CylindricalPoint) object);
	}


	public boolean equals(@NullAllow CylindricalPoint object)
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
			this.height == object.height;
	}


	@Override
	final public String toString()
	{
		return "[orientation: " + orientation + "; radius: " + radius + "; azimuth: " + azimuth + "; height: " + height + "]";
	}


	final public Cartesian3dOrientation orientation()
	{
		return orientation;
	}


	final public void orientation(Cartesian3dOrientation value)
	{
		orientation = value;
	}


	final public double radius()
	{
		return radius;
	}


	final public void radius(double value)
	{
		radius = value;
	}


	final public double azimuth()
	{
		return azimuth;
	}


	final public void azimuth(double value)
	{
		azimuth = value;
	}


	final public double height()
	{
		return height;
	}


	final public void height(double value)
	{
		height = value;
	}


	final public void set(double radius, double azimuth, double height)
	{
		this.radius = radius;
		this.azimuth = azimuth;
		this.height = height;
	}


	final public void set(Cartesian3dOrientation orientation, double radius, double azimuth, double height)
	{
		this.orientation = orientation;
		this.radius = radius;
		this.azimuth = azimuth;
		this.height = height;
	}


	final public void set(CylindricalPoint tuple)
	{
		set(tuple.orientation, tuple.radius, tuple.azimuth, tuple.height);
	}

}
