package net.javenture.framework.image;


/*
	2020/02/19
 */
final class FisheyeToCubemapRelation
{
	//
	final public short FISHEYE_X;
	final public short FISHEYE_Y;
	final public double CUBEMAP_X;
	final public double CUBEMAP_Y;

	//
	public FisheyeToCubemapRelation(int xFisheye, int yFisheye, double xCubemap, double yCubemap)
	{
		FISHEYE_X = (short) xFisheye;
		FISHEYE_Y = (short) yFisheye;
		CUBEMAP_X = xCubemap;
		CUBEMAP_Y = yCubemap;
	}

}
