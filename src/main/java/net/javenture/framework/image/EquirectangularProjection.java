package net.javenture.framework.image;


import net.javenture.framework.annotation.MultiThread;
import net.javenture.framework.math.ACartesian2dTuple;
import net.javenture.framework.math.ACartesian3dTuple;
import net.javenture.framework.math.MathUtil;


/*
	2021/06/25
 */
@MultiThread
final public class EquirectangularProjection
{
	//
	final public int WIDTH;
	final public int HEIGHT;
	final private double WIDTH_SCALE;
	final private double HEIGHT_SCALE;
	final private double WIDTH_NORMALIZATOR;
	final private double HEIGHT_NORMALIZATOR;
	final private double SHIFT_WIDTH;
	final private double SHIFT_HEIGHT;
	final private double LONGITUDE_NUMERATOR;
	final private double LONGITUDE_DENOMINATOR;
	final private double LATITUDE_NUMERATOR;
	final private double LATITUDE_DENOMINATOR;
	final private double[] LONGITUDE_SIN;
	final private double[] LONGITUDE_COS;
	final private double[] LATITUDE_SIN;
	final private double[] LATITUDE_COS;


	//
	public EquirectangularProjection(int width)
	{
		int height = width / 2;
		WIDTH = width;
		HEIGHT = height;
		WIDTH_SCALE = WIDTH;
		HEIGHT_SCALE = HEIGHT - 1;
		WIDTH_NORMALIZATOR = 1 / WIDTH_SCALE;
		HEIGHT_NORMALIZATOR = 1 / HEIGHT_SCALE;
		SHIFT_WIDTH = (double) width / 2;
		SHIFT_HEIGHT = (double) (height - 1) / 2;
		LONGITUDE_NUMERATOR = (double) width / 2 / Math.PI;
		LONGITUDE_DENOMINATOR = (double) 2 / width * Math.PI;
		LATITUDE_NUMERATOR = (double) (height - 1) / Math.PI;
		LATITUDE_DENOMINATOR = (double) 1 / (height - 1) * Math.PI;
		LONGITUDE_SIN = new double[width];
		LONGITUDE_COS = new double[width];
		LATITUDE_SIN = new double[height];
		LATITUDE_COS = new double[height];

		for (int x = 0; x < width; x++)
		{
			double value = -(x - SHIFT_WIDTH) * LONGITUDE_DENOMINATOR;
			LONGITUDE_SIN[x] = MathUtil.sin(value);
			LONGITUDE_COS[x] = MathUtil.cos(value);
		}

		for (int y = 0; y < height; y++)
		{
			double value = -(y - SHIFT_HEIGHT) * LATITUDE_DENOMINATOR;
			LATITUDE_SIN[y] = MathUtil.sin(value);
			LATITUDE_COS[y] = MathUtil.cos(value);
		}
	}


	//
	public int width()
	{
		return WIDTH;
	}


	public int height()
	{
		return HEIGHT;
	}


	public void convert(int x, int y, ACartesian3dTuple destination)
	{
		destination.set(LATITUDE_COS[y] * LONGITUDE_COS[x], LATITUDE_COS[y] * LONGITUDE_SIN[x], LATITUDE_SIN[y]);
	}


	public void convert(ACartesian2dTuple source, ACartesian3dTuple destination)
	{
		convert(source.x, source.y, destination);
	}


	public void convert(double x, double y, ACartesian3dTuple destination)
	{
		double longitude = -(x - SHIFT_WIDTH) * LONGITUDE_DENOMINATOR;
		double sinLongitude = MathUtil.sin(longitude);
		double cosLongitude = MathUtil.cos(longitude);

		double latitude = -(y - SHIFT_HEIGHT) * LATITUDE_DENOMINATOR;
		double sinLatitude = MathUtil.sin(latitude);
		double cosLatitude = MathUtil.cos(latitude);

		destination.set(cosLatitude * cosLongitude, cosLatitude * sinLongitude, sinLatitude);
	}


	public void convert(ACartesian3dTuple source, ACartesian2dTuple destination)
	{
		convert(source.x, source.y, source.z, destination);
	}


	public void convert(double x, double y, double z, ACartesian2dTuple destination)
	{
		double r = MathUtil.sqrt(x * x + y * y);
		double longitude = -MathUtil.atan2(y, x); // (−π, +π]
		double latitude = -MathUtil.atan2(z, r); // [−π/2, +π/2]

		destination.set
		(
			longitude != MathUtil.PI ? longitude * LONGITUDE_NUMERATOR + SHIFT_WIDTH : 0,
			latitude * LATITUDE_NUMERATOR + SHIFT_HEIGHT
		);
	}


	public void normalize(ACartesian2dTuple source, EquirectangularPoint destination)
	{
		destination.set(source.x * WIDTH_NORMALIZATOR, source.y * HEIGHT_NORMALIZATOR);
	}


	public void scale(EquirectangularPoint source, ACartesian2dTuple destination)
	{
		destination.set(source.x * WIDTH_SCALE, source.y * HEIGHT_SCALE);
	}


	public double scaleX(EquirectangularPoint source)
	{
		return source.x * WIDTH_SCALE;
	}


	public double scaleY(EquirectangularPoint source)
	{
		return source.y * HEIGHT_SCALE;
	}


	//
	final public static EquirectangularProjection SIZE_1024 = new EquirectangularProjection(1024);
	final public static EquirectangularProjection SIZE_2048 = new EquirectangularProjection(2048);
	final public static EquirectangularProjection SIZE_3072 = new EquirectangularProjection(3072);
	final public static EquirectangularProjection SIZE_4096 = new EquirectangularProjection(4096);

}
