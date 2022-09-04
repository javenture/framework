package net.javenture.framework.image;


import net.javenture.framework.annotation.NullDisallow;


/*
	2022/02/10
 */
final public class RgbI8Image
	extends AI8Image<Integer, RgbI8Color, RgbI8Color.Channel>
{
	//
	final public static IRgbI8ImageInstance INSTANCE = RgbI8Image::new;


	//
	final private int[][] ARRAY;


	//
	public RgbI8Image(int width, int height)
	{
		super(width, height);

		ARRAY = new int[height][width];
	}


	//
	@Override
	public void get0(int x, int y, RgbI8Color destination)
	{
		destination.rgb(ARRAY[y][x]);
	}


	@Override
	public Integer get0(int x, int y, RgbI8Color.Channel channel)
	{


		throw new UnsupportedOperationException();
	}


	@Override
	public void set0(int x, int y, RgbI8Color source)
	{
		ARRAY[y][x] = source.rgb();
	}


	@Override
	public void set0(int x, int y, RgbI8Color.Channel channel, @NullDisallow Integer value)
	{



		throw new UnsupportedOperationException();
	}


	@Override
	public void set0(int x, int y, RgbI8Color.Channel channel, int value)
	{



		throw new UnsupportedOperationException();
	}


	int rgb0(int x, int y)
	{
		return ARRAY[y][x];
	}


	//
	@FunctionalInterface
	interface IRgbI8ImageInstance<I extends RgbI8Image>
		extends IImage.IImageInstance<I>
	{
		@Override
		I create(int width, int height);
	}

}
