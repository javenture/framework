package net.javenture.framework.image;


import net.javenture.framework.annotation.SingleThread;
import net.javenture.framework.math.Axis3dRotationUtil;
import net.javenture.framework.math.Cartesian3dVector;
import net.javenture.framework.math.Quaternion;


/*
	2020/04/08
 */
@SingleThread
final public class DualFisheyeNegotiation
{
	//
	final private static double NORMALIZATION_THRESHOLD = 0.001;


	//
	final private Quaternion ROTATION_FRONT;
	final private Cartesian3dVector SHIFT_FRONT;
	final private Quaternion ROTATION_BACK;
	final private Cartesian3dVector SHIFT_BACK;


	//
	public DualFisheyeNegotiation()
	{
		ROTATION_FRONT = new Quaternion();
		SHIFT_FRONT = new Cartesian3dVector();
		ROTATION_BACK = new Quaternion();
		SHIFT_BACK = new Cartesian3dVector();
	}


	//
	public void update(double angleX, double angleY, double angleZ, double shiftX, double shiftY, double shiftZ)
	{
		double angleX2 = angleX * 0.5;
		double angleY2 = angleY * 0.5;
		double angleZ2 = angleZ * 0.5;
		double shiftX2 = shiftX * 0.5;
		double shiftY2 = shiftY * 0.5;
		double shiftZ2 = shiftZ * 0.5;

		Axis3dRotationUtil.local(angleX2, angleY2, -angleZ2, ROTATION_FRONT);
		SHIFT_FRONT.set(shiftX2, shiftY2, -shiftZ2);
		Axis3dRotationUtil.local(angleX2, angleY2, angleZ2, ROTATION_BACK);
		SHIFT_BACK.set(shiftX2, shiftY2, shiftZ2);
	}


	public void front(Cartesian3dVector vector)
	{
		IDualFisheyeOrientation.BACK.local(vector);
		vector.normalize(NORMALIZATION_THRESHOLD);                                             // ???
		vector.apply(ROTATION_BACK);
		vector.add(SHIFT_BACK);
		vector.normalize(NORMALIZATION_THRESHOLD);
		IDualFisheyeOrientation.BACK.global(vector);
	}


	public void back(Cartesian3dVector vector)
	{
		IDualFisheyeOrientation.FRONT.local(vector);
		vector.normalize(NORMALIZATION_THRESHOLD);                                              // ???
		vector.apply(ROTATION_FRONT);
		vector.add(SHIFT_FRONT);
		vector.normalize(NORMALIZATION_THRESHOLD);
		IDualFisheyeOrientation.FRONT.global(vector);
	}

}
