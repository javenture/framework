package net.javenture.framework.image;


/*
	2020/04/12
 */
public interface IF32ColorChannel
	extends IColorChannel
{
	//
	@FunctionalInterface
	interface IGetter<C extends AF32Color>
	{
		float get(C color);
	}


	@FunctionalInterface
	interface ISetter<C extends AF32Color>
	{
		void set(C color, float value);
	}

}
