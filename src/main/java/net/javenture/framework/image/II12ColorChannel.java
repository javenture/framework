package net.javenture.framework.image;


/*
	2020/05/12
 */
public interface II12ColorChannel
	extends IColorChannel
{
	//
	@FunctionalInterface
	interface IGetter<C extends AI12Color>
	{
		int get(C color);
	}


	@FunctionalInterface
	interface ISetter<C extends AI12Color>
	{
		void set(C color, int value);
	}

}
