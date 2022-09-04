package net.javenture.framework.image;


/*
	2022/02/10
 */
final public class RgbI12Image
	extends AI12Image<Integer, RgbI12Color, RgbI12Color.Channel>
{
	//
	final public static IRgbI12ImageInstance INSTANCE = RgbI12Image::new;


	//
	final private long[][] ARRAY;


	//
	public RgbI12Image(int width, int height)
	{
		super(width, height);

		ARRAY = new long[height][width];
	}


	//
	@Override
	public void get0(int x, int y, RgbI12Color destination)
	{
		destination.rgb(ARRAY[y][x]);
	}


	@Override
	public Integer get0(int x, int y, RgbI12Color.Channel channel)
	{



		throw new UnsupportedOperationException();
	}


	@Override
	public void set0(int x, int y, RgbI12Color source)
	{
		ARRAY[y][x] = source.rgb();
	}


	@Override
	public void set0(int x, int y, RgbI12Color.Channel channel, Integer value)
	{



		throw new UnsupportedOperationException();
	}


	@Override
	public void set0(int x, int y, RgbI12Color.Channel channel, int value)
	{



		throw new UnsupportedOperationException();
	}


	long rgba0(int x, int y)
	{
		return ARRAY[y][x];
	}


	//
	@FunctionalInterface
	interface IRgbI12ImageInstance<I extends RgbI12Image>
		extends IImage.IImageInstance<I>
	{
		@Override
		I create(int width, int height);
	}

}
