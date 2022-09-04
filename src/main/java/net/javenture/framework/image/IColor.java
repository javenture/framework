package net.javenture.framework.image;


import net.javenture.framework.annotation.NullDisallow;


/*
	2020/11/27
 */
public interface IColor<V extends Number, CH extends IColorChannel>
{
	//
	@NullDisallow V get(CH channel);
	void set(CH channel, @NullDisallow V value);
	void set0(CH channel, @NullDisallow V value);


	//
	@FunctionalInterface
	interface IColorInstance<C extends IColor>
	{
		C create();
	}

}
