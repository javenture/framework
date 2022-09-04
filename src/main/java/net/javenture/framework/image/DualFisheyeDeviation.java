package net.javenture.framework.image;


import net.javenture.framework.math.Axis3dRotationUtil;
import net.javenture.framework.math.Cartesian3dVector;
import net.javenture.framework.math.Quaternion;


/*
	2020/04/09
 */
final public class DualFisheyeDeviation
{
	//
	final private static double NORMALIZATION_THRESHOLD2 = 0.001;


	//
	final private IAction ACTION;


	//
	private DualFisheyeDeviation()
	{
		ACTION = IAction.DEFAULT;
	}


	public DualFisheyeDeviation(DualFisheyeType type, double angleX, double angleY, double angleZ, double shiftX, double shiftY, double shiftZ)
	{
		double angleX2 = angleX * 0.5;
		double angleY2 = angleY * 0.5;
		double angleZ2 = angleZ * 0.5;
		double shiftX2 = shiftX * 0.5;
		double shiftY2 = shiftY * 0.5;
		double shiftZ2 = shiftZ * 0.5;

		//
		IAction action;

		switch (type)
		{
			case FRONT:
			{
				Quaternion rotation = Axis3dRotationUtil.local(angleX2, angleY2, -angleZ2);
				Cartesian3dVector shift = new Cartesian3dVector(shiftX2, shiftY2, -shiftZ2);

				action = vector ->
				{
					IDualFisheyeOrientation.FRONT.local(vector);
					vector.normalize(NORMALIZATION_THRESHOLD2); // !
					vector.apply(rotation);
					vector.add(shift);
					vector.normalize(NORMALIZATION_THRESHOLD2); // !
					IDualFisheyeOrientation.FRONT.global(vector);
				};

				break;
			}

			case BACK:
			{
				Quaternion rotation = Axis3dRotationUtil.local(angleX2, angleY2, angleZ2);
				Cartesian3dVector shift = new Cartesian3dVector(shiftX2, shiftY2, shiftZ2);

				action = vector ->
				{
					IDualFisheyeOrientation.BACK.local(vector);
					vector.normalize(NORMALIZATION_THRESHOLD2); // !
					vector.apply(rotation);
					vector.add(shift);
					vector.normalize(NORMALIZATION_THRESHOLD2); // !
					IDualFisheyeOrientation.BACK.global(vector);
				};

				break;
			}

			default: throw new UnsupportedOperationException();
		}

		//
		ACTION = action;
	}


	//
	public void apply(Cartesian3dVector vector)                               // XXX: compute ?
	{
		ACTION.execute(vector);
	}


	//
	@FunctionalInterface
	private interface IAction
	{
		//
		void execute(Cartesian3dVector vector);

		//
		IAction DEFAULT = vector -> vector.normalize(NORMALIZATION_THRESHOLD2);
	}


	//
	final public static DualFisheyeDeviation DEFAULT = new DualFisheyeDeviation();

}
