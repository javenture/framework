package net.javenture.framework.image;


/*
	2020/05/12
 */
public interface II8ColorChannel
	extends IColorChannel
{
	//
	@FunctionalInterface
	interface IGetter<C extends AI8Color>
	{
		int get(C color);
	}


	@FunctionalInterface
	interface ISetter<C extends AI8Color>
	{
		void set(C color, int value);
	}

}
