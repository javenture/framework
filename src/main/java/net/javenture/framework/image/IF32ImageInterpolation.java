package net.javenture.framework.image;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.math.ACartesian2dTuple;
import net.javenture.framework.util.IConst;


/*
	2020/10/20
 */
public interface IF32ImageInterpolation
	extends IConst<IF32ImageInterpolation>
{
	//
	@NullAllow
	<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
	Float
	interpolate(I image, double x, double y, CH channel);


	<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
	boolean
	interpolate(I image, double x, double y, C destination, CH[] channels); // ! CH[]


	//
	default
	@NullAllow
	<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
	Float
	interpolate(I image, ACartesian2dTuple tuple, CH channel)
	{
		return interpolate(image, tuple.x, tuple.y, channel);
	}


	default
	<V extends Float, I extends AF32Image<V, C, CH>, C extends AF32Color<V, CH>, CH extends IF32ColorChannel>
	boolean
	interpolate(I image, ACartesian2dTuple tuple, C destination, CH[] channels) // ! CH[]
	{
		return interpolate(image, tuple.x, tuple.y, destination, channels);
	}

}
