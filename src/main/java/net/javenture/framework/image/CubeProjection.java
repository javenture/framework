package net.javenture.framework.image;


import net.javenture.framework.annotation.MultiThread;
import net.javenture.framework.math.ACartesian3dTuple;


/*
	2021/01/14
 */
@MultiThread
final public class CubeProjection
{
	//
	final public int SIZE;
	final int FIRST;
	final int LAST;
	final double SHIFT;
	final double STEP_NUMERATOR;
	final double STEP_DENOMINATOR;


	//
	public CubeProjection(int size)
	{
		SIZE = size;
		FIRST = 0;
		LAST = size - 1;
		SHIFT = (double) 1 / size;
		STEP_NUMERATOR = (double) 2 / size;
		STEP_DENOMINATOR = (double) size / 2;
	}


	//
	public int size()
	{
		return SIZE;
	}


	public void convert(CubePoint source, ACartesian3dTuple destination)
	{
		convert(source.face, source.x, source.y, destination);
	}


	public void convert(CubeFace face, double x, double y, ACartesian3dTuple destination)
	{
		double step = STEP_NUMERATOR;
		double shift = SHIFT;

		switch (face)
		{
			case FRONT:
			{
				destination.x = 1;
				destination.y = 1 - shift - step * x;
				destination.z = 1 - shift - step * y;

				break;
			}

			case BACK:
			{
				destination.x = -1;
				destination.y = -1 + shift + step * x;
				destination.z = 1 - shift - step * y;

				break;
			}

			case LEFT:
			{
				destination.x = -1 + shift + step * x;
				destination.y = 1;
				destination.z = 1 - shift - step * y;

				break;
			}

			case RIGHT:
			{
				destination.x = 1 - shift - step * x;
				destination.y = -1;
				destination.z = 1 - shift - step * y;

				break;
			}

			case TOP:
			{
				destination.x = -1 + shift + step * y;
				destination.y = 1 - shift - step * x;
				destination.z = 1;

				break;
			}

			case BOTTOM:
			{
				destination.x = 1 - shift - step * y;
				destination.y = 1 - shift - step * x;
				destination.z = -1;

				break;
			}
		}
	}


	public void convert(ACartesian3dTuple source, CubePoint destination)
	{
		convert(source.x, source.y, source.z, destination);
	}


	public void convert(double x, double y, double z, CubePoint destination)
	{
		if (x == 0 && y == 0 && z == 0)
		{
			destination.face = CubeFace.FRONT;
			destination.x = FIRST;
			destination.y = FIRST;
		}
		else
		{
			double step = STEP_DENOMINATOR;
			double ax = Math.abs(x);
			double ay = Math.abs(y);
			double az = Math.abs(z);
			boolean xgt0 = x > 0;
			boolean ygt0 = y > 0;
			boolean zgt0 = z > 0;

			if (ax == ay && ay == az) // vertex
			{
				if (xgt0)
				{
					destination.face = CubeFace.FRONT;

					if (ygt0)
					{
						if (zgt0)
						{
							destination.x = FIRST;
							destination.y = FIRST;
						}
						else
						{
							destination.x = FIRST;
							destination.y = LAST;
						}
					}
					else
					{
						if (zgt0)
						{
							destination.x = LAST;
							destination.y = FIRST;
						}
						else
						{
							destination.x = LAST;
							destination.y = LAST;
						}
					}
				}
				else
				{
					destination.face = CubeFace.BACK;

					if (ygt0)
					{
						if (zgt0)
						{
							destination.x = LAST;
							destination.y = FIRST;
						}
						else
						{
							destination.x = LAST;
							destination.y = LAST;
						}
					}
					else
					{
						if (zgt0)
						{
							destination.x = FIRST;
							destination.y = FIRST;
						}
						else
						{
							destination.x = FIRST;
							destination.y = LAST;
						}
					}
				}
			}
			else if (ax == ay)
			{
				if (az > ax)
				{
					if (zgt0)
					{
						double k = 1 / az;
						destination.face = CubeFace.TOP;
						destination.x = (y * k - 1) * -step - 0.5;
						destination.y = (x * k + 1) * step - 0.5;
					}
					else
					{
						double k = 1 / az;
						destination.face = CubeFace.BOTTOM;
						destination.x = (y * k - 1) * -step - 0.5;
						destination.y = (x * k - 1) * -step - 0.5;
					}
				}
				else
				{
					if (xgt0)
					{
						destination.face = CubeFace.FRONT;
						destination.x = ygt0 ? FIRST : LAST;
						destination.y = (z / ax - 1) * -step - 0.5;
					}
					else
					{
						destination.face = CubeFace.BACK;
						destination.x = ygt0 ? LAST : FIRST;
						destination.y = (z / ax - 1) * -step - 0.5;
					}
				}
			}
			else if (ay == az)
			{
				if (ax > ay)
				{
					if (xgt0)
					{
						double k = 1 / ax;
						destination.face = CubeFace.FRONT;
						destination.x = (y * k - 1) * -step - 0.5;
						destination.y = (z * k - 1) * -step - 0.5;
					}
					else
					{
						double k = 1 / ax;
						destination.face = CubeFace.BACK;
						destination.x = (y * k + 1) * step - 0.5;
						destination.y = (z * k - 1) * -step - 0.5;
					}
				}
				else
				{
					if (ygt0)
					{
						destination.face = CubeFace.LEFT;
						destination.x = (x / ay + 1) * step - 0.5;
						destination.y = zgt0 ? FIRST : LAST;
					}
					else
					{
						destination.face = CubeFace.RIGHT;
						destination.x = (x / ay - 1) * -step - 0.5;
						destination.y = zgt0 ? FIRST : LAST;
					}
				}
			}
			else if (az == ax)
			{
				if (ay > az)
				{
					if (ygt0)
					{
						double k = 1 / ay;
						destination.face = CubeFace.LEFT;
						destination.x = (x * k + 1) * step - 0.5;
						destination.y = (z * k - 1) * -step - 0.5;
					}
					else
					{
						double k = 1 / ay;
						destination.face = CubeFace.RIGHT;
						destination.x = (x * k - 1) * -step - 0.5;
						destination.y = (z * k - 1) * -step - 0.5;
					}
				}
				else
				{
					if (xgt0)
					{
						destination.face = CubeFace.FRONT;
						destination.x = (y / ax - 1) * -step - 0.5;
						destination.y = zgt0 ? FIRST : LAST;
					}
					else
					{
						destination.face = CubeFace.BACK;
						destination.x = (y / ax + 1) * step - 0.5;
						destination.y = zgt0 ? FIRST : LAST;
					}
				}
			}
			else if (ax > ay && ax > az)
			{
				if (xgt0)
				{
					double k = 1 / ax;
					destination.face = CubeFace.FRONT;
					destination.x = (y * k - 1) * -step - 0.5;
					destination.y = (z * k - 1) * -step - 0.5;
				}
				else
				{
					double k = 1 / ax;
					destination.face = CubeFace.BACK;
					destination.x = (y * k + 1) * step - 0.5;
					destination.y = (z * k - 1) * -step - 0.5;
				}
			}
			else if (ay > az && ay > ax)
			{
				if (ygt0)
				{
					double k = 1 / ay;
					destination.face = CubeFace.LEFT;
					destination.x = (x * k + 1) * step - 0.5;
					destination.y = (z * k - 1) * -step - 0.5;
				}
				else
				{
					double k = 1 / ay;
					destination.face = CubeFace.RIGHT;
					destination.x = (x * k - 1) * -step - 0.5;
					destination.y = (z * k - 1) * -step - 0.5;
				}
			}
			else if (az > ax && az > ay)
			{
				if (zgt0)
				{
					double k = 1 / az;
					destination.face = CubeFace.TOP;
					destination.x = (y * k - 1) * -step - 0.5;
					destination.y = (x * k + 1) * step - 0.5;
				}
				else
				{
					double k = 1 / az;
					destination.face = CubeFace.BOTTOM;
					destination.x = (y * k - 1) * -step - 0.5;
					destination.y = (x * k - 1) * -step - 0.5;
				}
			}
			else
			{
				throw new IllegalStateException();
			}

			//
			if (destination.x < FIRST) destination.x = FIRST;
			else if (destination.x > LAST) destination.x = LAST;

			if (destination.y < FIRST) destination.y = FIRST;
			else if (destination.y > LAST) destination.y = LAST;
		}
	}


	//
	final public static CubeProjection SIZE_512 = new CubeProjection(512);
	final public static CubeProjection SIZE_768 = new CubeProjection(768);
	final public static CubeProjection SIZE_1024 = new CubeProjection(1024);
	final public static CubeProjection SIZE_1536 = new CubeProjection(1536);
	final public static CubeProjection SIZE_2048 = new CubeProjection(2048);
	final public static CubeProjection SIZE_3072 = new CubeProjection(3072);
	final public static CubeProjection SIZE_4096 = new CubeProjection(4096);
	final public static CubeProjection SIZE_8192 = new CubeProjection(8192);

}
