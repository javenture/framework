package net.javenture.framework.math;


import net.javenture.framework.annotation.NullDisallow;


/*
	2020/09/08
 */
abstract public class APolar2dTuple<T extends APolar2dTuple>
{
	/*
		https://en.wikipedia.org/wiki/Polar_coordinate_system
	 */
	public double radius; // ! public
	public double azimuth; // ! public; azimuth = phi; -PI ... +PI


	//
	protected APolar2dTuple()
	{
		this(0, 0);
	}


	protected APolar2dTuple(APolar2dTuple source)
	{
		this(source.radius, source.azimuth);
	}


	protected APolar2dTuple(double radius, double azimuth)
	{
		this.radius = radius;
		this.azimuth = azimuth;
	}


	//
	@Override
	final public int hashCode()
	{
        long result = 7L;
        result = 31L * result + Double.doubleToLongBits(radius);
        result = 31L * result + Double.doubleToLongBits(azimuth);

        return (int) (result ^ (result >> 32));
	}


	@Override
	final public String toString()
	{
		return "[radius: " + radius + "; azimuth: " + azimuth + "]";
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


	final public void set(double radius, double azimuth)
	{
		this.radius = radius;
		this.azimuth = azimuth;
	}


	final public void set(@NullDisallow APolar2dTuple tuple)
	{
		this.radius = tuple.radius;
		this.azimuth = tuple.azimuth;
	}

}
