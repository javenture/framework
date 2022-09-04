package net.javenture.framework.image;


/*
	2020/05/12
 */
public interface II16ColorChannel
	extends IColorChannel
{
	//
	@FunctionalInterface
	interface IGetter<C extends AI16Color>
	{
		int get(C color);
	}


	@FunctionalInterface
	interface ISetter<C extends AI16Color>
	{
		void set(C color, int value);
	}

}
