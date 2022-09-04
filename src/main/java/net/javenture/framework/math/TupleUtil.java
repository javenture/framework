package net.javenture.framework.math;


/*
	2020/09/08
 */
final public class TupleUtil
{
	//
	private TupleUtil()
	{
	}


	// 2d
	public static double distance(ACartesian2dTuple tuple0, ACartesian2dTuple tuple1)
	{
		return MathUtil.sqrt(distance2(tuple0.x, tuple0.y, tuple1.x, tuple1.y));
	}


	public static double distance2(ACartesian2dTuple tuple0, ACartesian2dTuple tuple1)
	{
		return distance2(tuple0.x, tuple0.y, tuple1.x, tuple1.y);
	}


	public static double distance(double x0, double y0, double x1, double y1)
	{
		return MathUtil.sqrt(distance2(x0, y0, x1, y1));
	}


	public static double distance2(double x0, double y0, double x1, double y1)
	{
		double dx = x0 - x1;
		double dy = y0 - y1;

		return dx * dx + dy * dy;
	}


	@SuppressWarnings("RedundantCast")
	public static float distance(float x0, float y0, float x1, float y1)
	{
		return (float) distance((double) x0, (double) y0, (double) x1, (double) y1);
	}


	@SuppressWarnings("RedundantCast")
	public static float distance2(float x0, float y0, float x1, float y1)
	{
		return (float) distance2((double) x0, (double) y0, (double) x1, (double) y1);
	}


	public static void divide(ACartesian2dTuple tuple0, ACartesian2dTuple tuple1, double ratio, ACartesian2dTuple destination)
	{
		divide(tuple0.x, tuple0.y, tuple1.x, tuple1.y, ratio, destination);
	}


	public static void divide(double x0, double y0, double x1, double y1, double ratio, ACartesian2dTuple destination)
	{
		double dx = x0 - x1;
		double dy = y0 - y1;
		destination.set(x0 - dx * ratio, y0 - dy * ratio);
	}


	// 3d
	public static double distance(ACartesian3dTuple tuple0, ACartesian3dTuple tuple1)
	{
		return MathUtil.sqrt(distance2(tuple0.x, tuple0.y, tuple0.z, tuple1.x, tuple1.y, tuple1.z));
	}


	public static double distance2(ACartesian3dTuple tuple0, ACartesian3dTuple tuple1)
	{
		return distance2(tuple0.x, tuple0.y, tuple0.z, tuple1.x, tuple1.y, tuple1.z);
	}


	public static double distance(double x0, double y0, double z0, double x1, double y1, double z1)
	{
		return MathUtil.sqrt(distance2(x0, y0, z0, x1, y1, z1));
	}


	public static double distance2(double x0, double y0, double z0, double x1, double y1, double z1)
	{
		double dx = x0 - x1;
		double dy = y0 - y1;
		double dz = z0 - z1;

		return dx * dx + dy * dy + dz * dz;
	}


	@SuppressWarnings("RedundantCast")
	public static float distance(float x0, float y0, float z0, float x1, float y1, float z1)
	{
		return (float) distance((double) x0, (double) y0, (double) z0, (double) x1, (double) y1, (double) z1);
	}


	@SuppressWarnings("RedundantCast")
	public static float distance2(float x0, float y0, float z0, float x1, float y1, float z1)
	{
		return (float) distance2((double) x0, (double) y0, (double) z0, (double) x1, (double) y1, (double) z1);
	}










	public static void convert(ACartesian3dTuple source, SphericalPoint destination)                      // ???: orientation                   // TEST
	{
		double x = source.x;
		double y = source.y;
		double z = source.z;
		double r = MathUtil.sqrt(x * x + y * y + z * z);

		if (r != 0)
		{
			destination.radius = r;
			destination.azimuth = MathUtil.atan2(y, x);
			destination.inclination = MathUtil.acos(z / r);
		}
		else
		{
			destination.radius = 0;
			destination.azimuth = 0;
			destination.inclination = 0;
		}
	}


	public static void convert(SphericalPoint source, ACartesian3dTuple destination)
	{
		double radius = source.radius;
		double azimuth = source.azimuth;
		double inclination = source.inclination;

		destination.x = radius * MathUtil.sin(inclination) * MathUtil.cos(azimuth);
		destination.y = radius * MathUtil.sin(inclination) * MathUtil.sin(azimuth);
		destination.z = radius * MathUtil.cos(inclination);
	}











	public static void convert(ACartesian3dTuple source, Polar3dPoint destination)                                  // ???: orientation
	{
		double x = source.x;
		double y = source.y;
		double z = source.z;
		double r = MathUtil.sqrt(x * x + y * y + z * z);

		if (r != 0)
		{
			destination.radius = r;
			destination.azimuth = MathUtil.atan2(y, x);
			destination.elevation = MathUtil.acos(-z / r) - MathUtil.PI_HALF;
		}
		else
		{
			destination.radius = 0;
			destination.azimuth = 0;
			destination.elevation = 0;
		}
	}


	public static void convert(Polar3dPoint source, ACartesian3dTuple destination)
	{
		double radius = source.radius;
		double azimuth = source.azimuth;
		double elevation = source.elevation + MathUtil.PI_HALF;

		double sinAzimuth = MathUtil.sin(azimuth);
		double cosAzimuth = MathUtil.cos(azimuth);
		double sinElevation = MathUtil.sin(elevation);
		double cosElevation = MathUtil.cos(elevation);
		destination.x = radius * sinElevation * cosAzimuth;
		destination.y = radius * sinElevation * sinAzimuth;
		destination.z = -radius * cosElevation;
	}






	public static void convert(ACartesian3dTuple source, CylindricalPoint destination)
	{
		convert(source, destination, destination.orientation);
	}


	@SuppressWarnings("SuspiciousNameCombination")
	public static void convert(ACartesian3dTuple source, CylindricalPoint destination, Cartesian3dOrientation orientation)                   // TEST
	{
		double x = source.x;
		double y = source.y;
		double z = source.z;

		//
		switch (orientation)
		{
			case XYZ:
			{
				double r = MathUtil.sqrt(x * x + y * y);

				destination.orientation = orientation;
				destination.radius = r;
				destination.azimuth = MathUtil.atan2(y, x);
				destination.height = z;

				break;
			}

			case YZX:
			{
				double r = MathUtil.sqrt(y * y + z * z);

				destination.orientation = orientation;
				destination.radius = r;
				destination.azimuth = MathUtil.atan2(z, y);
				destination.height = x;

				break;
			}

			case ZXY:
			{
				double r = MathUtil.sqrt(z * z + x * x);

				destination.orientation = orientation;
				destination.radius = r;
				destination.azimuth = MathUtil.atan2(x, z);
				destination.height = y;

				break;
			}

			default: throw new UnsupportedOperationException();
		}
	}


	@SuppressWarnings("SuspiciousNameCombination")
	public static void convert(CylindricalPoint source, ACartesian3dTuple destination)                                // TEST
	{
		Cartesian3dOrientation orientation = source.orientation;
		double radius = source.radius;
		double azimuth = source.azimuth;
		double height = source.height;

		switch (orientation)
		{
			case XYZ:
			{
				destination.x = radius * MathUtil.cos(azimuth);
				destination.y = radius * MathUtil.sin(azimuth);
				destination.z = height;

				break;
			}

			case YZX:
			{
				destination.x = height;
				destination.y = radius * MathUtil.cos(azimuth);
				destination.z = radius * MathUtil.sin(azimuth);

				break;
			}

			case ZXY:
			{
				destination.x = radius * MathUtil.sin(azimuth);
				destination.y = height;
				destination.z = radius * MathUtil.cos(azimuth);

				break;
			}

			default: throw new UnsupportedOperationException();
		}
	}


	public static void orientation(Cartesian3dOrientationTransformation transformation, ACartesian3dTuple tuple)
	{
		orientation(transformation, tuple, tuple);
	}


	public static void orientation(Cartesian3dOrientationTransformation transformation, ACartesian3dTuple source, ACartesian3dTuple destination)
	{
		transformation.ACTION.execute(source, destination);
	}


	public static void rotate(Cartesian3dRotation rotation, ACartesian3dTuple tuple)
	{
		rotate(rotation, tuple, tuple);
	}


	public static void rotate(Cartesian3dRotation rotation, ACartesian3dTuple source, ACartesian3dTuple destination)
	{
		rotation.ACTION.execute(source, destination);
	}

}
