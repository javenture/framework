package net.javenture.framework.image;


import net.javenture.framework.annotation.NullDisallow;


/*
	2022/02/10
 */
final public class RgbF32Image
	extends AF32Image<Float, RgbF32Color, RgbF32Color.Channel>
{
	//
	final public static IRgbF32ImageInstance INSTANCE = RgbF32Image::new;


	//
	final private float[][][] ARRAY;


	//
	public RgbF32Image(int width, int height)
	{
		super(width, height);

		ARRAY = new float[height][width][RgbF32Color.Channel.COUNT];
	}


	//
	@Override
	public void get0(int x, int y, RgbF32Color destination)
	{
		destination.set0(ARRAY[y][x]);
	}


	@Override
	public Float get0(int x, int y, RgbF32Color.Channel channel)
	{
		return ARRAY[y][x][channel.ordinal()];
	}


	@Override
	public void set0(int x, int y, RgbF32Color source)
	{
		source.get(ARRAY[y][x]);
	}


	@Override
	public void set0(int x, int y, RgbF32Color.Channel channel, @NullDisallow Float value)
	{
		ARRAY[y][x][channel.ordinal()] = value;
	}


	@Override
	public void set0(int x, int y, RgbF32Color.Channel channel, float value)
	{
		ARRAY[y][x][channel.ordinal()] = value;
	}


	@Override
	float[] array0(int x, int y)
	{
		return ARRAY[y][x];
	}


	//
	@FunctionalInterface
	interface IRgbF32ImageInstance<I extends RgbF32Image>
		extends IImage.IImageInstance<I>
	{
		@Override
		I create(int width, int height);
	}

}
