package net.javenture.framework.math;


/*
	2020/02/06
 */
public enum Cartesian3dOrientation
{
	/*
		https://en.wikipedia.org/wiki/Cartesian_coordinate_system
	 */

	/*
			  Z
			|
			|___
		   /     Y
		X /
	 */
	XYZ,

	/*
			  X
			|
			|___
		   /     Z
		Y /
	 */
	YZX,

	/*
			  Y
			|
			|___
		   /     X
		Z /
	 */
	ZXY;


	//
	final public static Cartesian3dOrientation DEFAULT = Cartesian3dOrientation.XYZ;

}
