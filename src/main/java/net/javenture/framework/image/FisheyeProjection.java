package net.javenture.framework.image;


import net.javenture.framework.annotation.SingleThread;
import net.javenture.framework.boofcv.BoofcvMathUtil;
import net.javenture.framework.math.ACartesian2dTuple;
import net.javenture.framework.math.ACartesian3dTuple;
import net.javenture.framework.math.ImmutableCircle;
import net.javenture.framework.math.Rectangle;

import boofcv.alg.distort.universal.UniOmniPtoS_F64;
import boofcv.alg.distort.universal.UniOmniStoP_F64;
import boofcv.struct.calib.CameraUniversalOmni;
import georegression.struct.point.Point2D_F64;
import georegression.struct.point.Point3D_F64;


/*
	2020/04/07
 */
@SingleThread
final public class FisheyeProjection
{
	//
	final private Config CONFIG;
	final private UniOmniStoP_F64 DISTORTION;
	final private UniOmniPtoS_F64 UNDISTORTION;
	final private Point2D_F64 POINT_BOOFCV_2D;
	final private Point3D_F64 POINT_BOOFCV_3D;


	//
	public FisheyeProjection(Config config, CameraUniversalOmni model)
	{
		CONFIG = config;
		DISTORTION = new UniOmniStoP_F64(model);
		UNDISTORTION = new UniOmniPtoS_F64(model);
		POINT_BOOFCV_2D = new Point2D_F64();
		POINT_BOOFCV_3D = new Point3D_F64();
	}


	//
	public Config config()
	{
		return CONFIG;
	}


	public boolean contains(double x, double y)
	{
		return
			CONFIG.RECTANGLE.contains(x, y)
			&&
			CONFIG.CIRCLE.contains(x, y);
	}


	public boolean contains(ACartesian2dTuple tuple)
	{
		return contains(tuple.x, tuple.y);
	}


	public boolean convert(ACartesian2dTuple source, ACartesian3dTuple destination)
	{
		if (contains(source))
		{
			convert0(source, destination);

			return true;
		}
		else
		{
			return false;
		}
	}


	void convert0(ACartesian2dTuple source, ACartesian3dTuple destination)
	{
		UNDISTORTION.compute(source.x, source.y, POINT_BOOFCV_3D); // BoofCV axis
		BoofcvMathUtil.transform(POINT_BOOFCV_3D, destination);
	}


	public boolean convert(ACartesian3dTuple source, ACartesian2dTuple destination)
	{
		convert0(source, destination);

		return contains(destination);
	}


	void convert0(ACartesian3dTuple source, ACartesian2dTuple destination)
	{
		BoofcvMathUtil.transform(source, POINT_BOOFCV_3D);
		DISTORTION.compute(POINT_BOOFCV_3D.x, POINT_BOOFCV_3D.y, POINT_BOOFCV_3D.z, POINT_BOOFCV_2D);
		BoofcvMathUtil.convert(POINT_BOOFCV_2D, destination);
	}


	//
	final public static class Config                                                // ???
	{
		public int WIDTH;
		public int HEIGHT;
		public Rectangle RECTANGLE;
		public ImmutableCircle CIRCLE;


	}

}
