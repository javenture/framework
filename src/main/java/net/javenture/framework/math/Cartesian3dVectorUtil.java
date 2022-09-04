package net.javenture.framework.math;


/*
	2020/03/17
 */
final public class Cartesian3dVectorUtil
{
	//
	private Cartesian3dVectorUtil()
	{
	}


	//
	public static double dot(Cartesian3dVector vector0, Cartesian3dVector vector1)
	{
		return vector0.x * vector1.x + vector0.y * vector1.y + vector0.z * vector1.z;
	}


	public static double angle(Cartesian3dVector vector0, Cartesian3dVector vector1)
	{
		double value = dot(vector0, vector1) / (vector0.length() * vector1.length());                          // XXX: divide by 0

		if (value > 1) return MathUtil.ACOS_PLUS_1;
		else if (value < -1) return MathUtil.ACOS_MINUS_1;
		else return MathUtil.acos(value);
	}


	public static Cartesian3dVector add(Cartesian3dVector vector0, Cartesian3dVector vector1)
	{
		return new Cartesian3dVector(vector0.x + vector1.x, vector0.y + vector1.y, vector0.z + vector1.z);
    }


	public static void add(Cartesian3dVector vector0, Cartesian3dVector vector1, Cartesian3dVector destination)
	{
        destination.x = vector0.x + vector1.x;
        destination.y = vector0.y + vector1.y;
        destination.z = vector0.z + vector1.z;
    }


	public static Cartesian3dVector sub(Cartesian3dVector vector0, Cartesian3dVector vector1)
	{
		return new Cartesian3dVector(vector1.x - vector0.x, vector1.y - vector0.y, vector1.z - vector0.z);
    }


	public static void sub(Cartesian3dVector vector0, Cartesian3dVector vector1, Cartesian3dVector destination)
	{
        destination.x = vector1.x - vector0.x;
        destination.y = vector1.y - vector0.y;
        destination.z = vector1.z - vector0.z;
    }

}
