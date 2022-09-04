package net.javenture.framework.math;


import net.javenture.framework.annotation.Immutable;


/*
	2020/03/10
 */
@Deprecated
@Immutable
final public class PointTransformation
{
	//
	final private static Quaternion QUATERNION_DEFAULT = new Quaternion();
	final private static Cartesian3dVector SHIFT_DEFAULT = new Cartesian3dVector();


	// transformation from ideal to real position in local axis
	final private double ANGLE_X; // 0X-axis clockwise rotation
	final private double ANGLE_Y; // 0Y-axis clockwise rotation
	final private double ANGLE_Z; // 0Z-axis clockwise rotation
	final private double SHIFT_X; // 0X-axis shift
	final private double SHIFT_Y; // 0Y-axis shift
	final private double SHIFT_Z; // 0Z-axis shift
	final private IEntry ENTRY_FORWARD;
	final private IEntry ENTRY_BACKWARD;


	//
	private PointTransformation()
	{
		ANGLE_X = 0;
		ANGLE_Y = 0;
		ANGLE_Z = 0;
		SHIFT_X = 0;
		SHIFT_Y = 0;
		SHIFT_Z = 0;
		ENTRY_FORWARD = IEntry.DEFAULT;
		ENTRY_BACKWARD = IEntry.DEFAULT;
	}


	public PointTransformation(double angleX, double angleY, double angleZ, double shiftX, double shiftY, double shiftZ)
	{
		Quaternion quaternionForward;
		Quaternion quaternionBackward;
		Cartesian3dVector vector3dForward;
		Cartesian3dVector vector3dBackward;

		//
		boolean rotation = angleX != 0 || angleY != 0 || angleZ != 0;
		boolean shift = shiftX != 0 || shiftY != 0 || shiftZ != 0;

		if (rotation)
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

			quaternionForward = new Quaternion(quaternionZ);
			quaternionForward.multiply(quaternionY);
			quaternionForward.multiply(quaternionX);

			quaternionBackward = new Quaternion(quaternionForward);
			quaternionBackward.conjugate();
		}
		else
		{
			quaternionForward = QUATERNION_DEFAULT;
			quaternionBackward = QUATERNION_DEFAULT;
		}

		if (shift)
		{
			vector3dForward = new Cartesian3dVector(shiftX, shiftY, shiftZ); // !
			vector3dBackward = new Cartesian3dVector(-shiftX, -shiftY, -shiftZ); // !
		}
		else
		{
			vector3dForward = SHIFT_DEFAULT;
			vector3dBackward = SHIFT_DEFAULT;
		}

		//
		ANGLE_X = angleX;
		ANGLE_Y = angleY;
		ANGLE_Z = angleZ;
		SHIFT_X = shiftX;
		SHIFT_Y = shiftY;
		SHIFT_Z = shiftZ;

		if (rotation && shift)
		{
			ENTRY_FORWARD = vector ->
			{
				vector.normalize();
				vector.apply(quaternionForward);
				vector.add(vector3dForward);
				vector.normalize();
			};

			ENTRY_BACKWARD = vector ->
			{
				vector.normalize();
				vector.apply(quaternionBackward);
				vector.add(vector3dBackward);
				vector.normalize();
			};
		}
		else if (rotation)
		{
			ENTRY_FORWARD = vector ->
			{
				vector.normalize();
				vector.apply(quaternionForward);
			};

			ENTRY_BACKWARD = vector ->
			{
				vector.normalize();
				vector.apply(quaternionBackward);
			};
		}
		else if (shift)
		{
			ENTRY_FORWARD = vector ->
			{
				vector.normalize();
				vector.add(vector3dForward);
				vector.normalize();
			};

			ENTRY_BACKWARD = vector ->
			{
				vector.normalize();
				vector.add(vector3dBackward);
				vector.normalize();
			};
		}
		else
		{
			ENTRY_FORWARD = IEntry.DEFAULT;
			ENTRY_BACKWARD = IEntry.DEFAULT;
		}
	}


	// from real to ideal
	public void forward(Cartesian3dVector vector)
	{
		ENTRY_FORWARD.convert(vector);
	}


	// from ideal to real
	public void backward(Cartesian3dVector vector)                                                        // DEL: backward != forward
	{
		ENTRY_BACKWARD.convert(vector);
	}


	//
	@FunctionalInterface
	private interface IEntry
	{
		//
		void convert(Cartesian3dVector vector);

		//
		IEntry DEFAULT = Cartesian3dVector::normalize;
	}


	//
	final public static PointTransformation DEFAULT = new PointTransformation();

}
