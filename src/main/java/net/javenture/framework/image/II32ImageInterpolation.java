package net.javenture.framework.image;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.math.ACartesian2dTuple;
import net.javenture.framework.util.IConst;


/*
	2020/05/14
 */
public interface II32ImageInterpolation
	extends IConst<II32ImageInterpolation>
{
	//
	@NullAllow
	<V extends Integer, I extends AI16Image<V, C, CH>, C extends AI16Color<V, CH>, CH extends II16ColorChannel>
	Integer
	interpolate(I image, double x, double y, CH channel);


	@SuppressWarnings("unchecked")
	<V extends Integer, I extends AI16Image<V, C, CH>, C extends AI16Color<V, CH>, CH extends II16ColorChannel>
	boolean
	interpolate(I image, double x, double y, C destination, CH... channels);


	//
	default
	@NullAllow
	<V extends Integer, I extends AI16Image<V, C, CH>, C extends AI16Color<V, CH>, CH extends II16ColorChannel>
	Integer
	interpolate(I image, ACartesian2dTuple tuple, CH channel)
	{
		return interpolate(image, tuple.x, tuple.y, channel);
	}


	@SuppressWarnings("unchecked")
	default
	<V extends Integer, I extends AI16Image<V, C, CH>, C extends AI16Color<V, CH>, CH extends II16ColorChannel>
	boolean
	interpolate(I image, ACartesian2dTuple tuple, C destination, CH... channels)
	{
		return interpolate(image, tuple.x, tuple.y, destination, channels);
	}

}
