package net.javenture.framework.boofcv;


import net.javenture.framework.math.ACartesian2dTuple;
import net.javenture.framework.math.ACartesian3dTuple;

import georegression.struct.GeoTuple2D_F32;
import georegression.struct.GeoTuple2D_F64;
import georegression.struct.GeoTuple3D_F32;
import georegression.struct.GeoTuple3D_F64;


/*
	2020/04/09
 */
final public class BoofcvMathUtil
{
	//
	private BoofcvMathUtil()
	{
	}


	//
	public static void convert(GeoTuple2D_F32 source, ACartesian2dTuple destination)
	{
		destination.x = source.x;
		destination.y = source.y;
	}


	public static void convert(GeoTuple2D_F64 source, ACartesian2dTuple destination)
	{
		destination.x = source.x;
		destination.y = source.y;
	}


	public static void convert(ACartesian2dTuple source, GeoTuple2D_F32 destination)
	{
		destination.x = (float) source.x;
		destination.y = (float) source.y;
	}


	public static void convert(ACartesian2dTuple source, GeoTuple2D_F64 destination)
	{
		destination.x = source.x;
		destination.y = source.y;
	}


	public static void convert(GeoTuple3D_F32 source, ACartesian3dTuple destination)
	{
		destination.x = source.x;
		destination.y = source.y;
		destination.z = source.z;
	}


	public static void convert(GeoTuple3D_F64 source, ACartesian3dTuple destination)
	{
		destination.x = source.x;
		destination.y = source.y;
		destination.z = source.z;
	}


	public static void convert(ACartesian3dTuple source, GeoTuple3D_F32 destination)
	{
		destination.x = (float) source.x;
		destination.y = (float) source.y;
		destination.z = (float) source.z;
	}


	public static void convert(ACartesian3dTuple source, GeoTuple3D_F64 destination)
	{
		destination.x = source.x;
		destination.y = source.y;
		destination.z = source.z;
	}


	public static void transform(GeoTuple3D_F64 source, ACartesian3dTuple destination)
	{
		destination.x = source.z;
		destination.y = -source.x;
		destination.z = -source.y;
	}


	public static void transform(ACartesian3dTuple source, GeoTuple3D_F64 destination)
	{
		destination.x = -source.y;
		destination.y = -source.z;
		destination.z = source.x;
	}

}
