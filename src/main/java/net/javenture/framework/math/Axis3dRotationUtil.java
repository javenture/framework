package net.javenture.framework.math;


/*
	2020/04/07
 */
final public class Axis3dRotationUtil
{
	//
	private Axis3dRotationUtil()
	{
	}


	//
	public static Quaternion local(double angleX, double angleY, double angleZ)
	{
		Quaternion result = new Quaternion();
		local(angleX, angleY, angleZ, result);

		return result;
	}


	public static void local(double angleX, double angleY, double angleZ, Quaternion destination)
	{
		if (angleX != 0 || angleY != 0 || angleZ != 0)
		{
			Cartesian3dVector axisX = new Cartesian3dVector(1, 0, 0); // !
			Cartesian3dVector axisY = new Cartesian3dVector(0, 1, 0); // !
			Cartesian3dVector axisZ = new Cartesian3dVector(0, 0, 1); // !

			Quaternion quaternionX = Quaternion.fromAxisAngle(axisX, angleX, true); // !
			axisY.apply(quaternionX);
			axisZ.apply(quaternionX);
			Quaternion quaternionY = Quaternion.fromAxisAngle(axisY, angleY, true); // !
			axisZ.apply(quaternionY);
			Quaternion quaternionZ = Quaternion.fromAxisAngle(axisZ, angleZ, true); // !

			//
			destination.set(quaternionZ);
			destination.multiply(quaternionY);
			destination.multiply(quaternionX);
		}
		else
		{
			destination.init();
		}
	}

}
