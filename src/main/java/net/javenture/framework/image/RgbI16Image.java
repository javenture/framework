package net.javenture.framework.image;


/*
	2022/02/10
 */
final public class RgbI16Image
	extends AI16Image<Integer, RgbI16Color, RgbI16Color.Channel>
{
	//
	final public static IRgbI16ImageInstance INSTANCE = RgbI16Image::new;


	//
	final private long[][] ARRAY;


	//
	public RgbI16Image(int width, int height)
	{
		super(width, height);

		ARRAY = new long[height][width];
	}


	//
	@Override
	public void get0(int x, int y, RgbI16Color destination)
	{
		destination.rgb(ARRAY[y][x]);
	}


	@Override
	public Integer get0(int x, int y, RgbI16Color.Channel channel)
	{



		throw new UnsupportedOperationException();
	}


	@Override
	public void set0(int x, int y, RgbI16Color source)
	{
		ARRAY[y][x] = source.rgb();
	}


	@Override
	public void set0(int x, int y, RgbI16Color.Channel channel, Integer value)
	{



		throw new UnsupportedOperationException();
	}


	@Override
	public void set0(int x, int y, RgbI16Color.Channel channel, int value)
	{



		throw new UnsupportedOperationException();
	}


	long rgb0(int x, int y)
	{
		return ARRAY[y][x];
	}


	//
	@FunctionalInterface
	interface IRgbI16ImageInstance<I extends RgbI16Image>
		extends IImage.IImageInstance<I>
	{
		@Override
		I create(int width, int height);
	}

}
