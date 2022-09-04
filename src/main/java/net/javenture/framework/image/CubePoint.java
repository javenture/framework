package net.javenture.framework.image;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.util.ClassUtil;


/*
	2021/01/23
 */
final public class CubePoint
{
	//
	public @NullAllow CubeFace face; // ! public
	public double x; // ! public double
	public double y; // ! public double


	//
	public CubePoint()
	{
		this(null, -1, -1);
	}


	public CubePoint(CubePoint source)
	{
		this(source.face, source.x, source.y);
	}


	public CubePoint(@NullAllow CubeFace face, double x, double y)
	{
		this.face = face;
		this.x = x;
		this.y = y;
	}


	//
	@Override
	public String toString()
	{
		return "[" + ClassUtil.name(this) + "] (face: " + face + "; x: " + x + "; y: " + y + ")";
	}


	public @NullAllow CubeFace face()
	{
		return face;
	}


	public double x()
	{
		return x;
	}


	public double y()
	{
		return y;
	}


	public void set(@NullAllow CubeFace face, double x, double y)
	{
		this.face = face;
		this.x = x;
		this.y = y;
	}



	// XXX: equals with threshold
}
