package net.javenture.framework.math;


/*
	2020/09/12
 */
final public class Cartesian3dVector
	extends ACartesian3dTuple
{
	//
	public Cartesian3dVector()
	{
	}


	public Cartesian3dVector(ACartesian3dTuple source)
	{
		super(source);
	}


	public Cartesian3dVector(double x, double y, double z)
	{
		super(x, y, z);
	}


	//
	public double length()
	{
        return MathUtil.sqrt(length2());
    }


	public double length2()
	{
        return x * x + y * y + z * z;
    }


    public void normalize()
	{
        double length = length();

        if (length != 0 && length != 1)
		{
			double k = 1 / length;
			x *= k;
			y *= k;
			z *= k;
		}
    }


    public void normalize(double threshold2)
	{
        double length2 = length2();

        if (length2 != 0 && Math.abs(1 - length2) > threshold2)
		{
			double k = 1 / MathUtil.sqrt(length2);
			x *= k;
			y *= k;
			z *= k;
		}
    }


	public void multiply(double value)
	{
        x *= value;
        y *= value;
        z *= value;
    }


	public void add(Cartesian3dVector object)
	{
        this.x += object.x;
        this.y += object.y;
        this.z += object.z;
	}


	public void sub(Cartesian3dVector object)
	{
        this.x -= object.x;
        this.y -= object.y;
        this.z -= object.z;
	}


	public double dot(Cartesian3dVector vector)
	{
		return Cartesian3dVectorUtil.dot(this, vector);
	}

}
