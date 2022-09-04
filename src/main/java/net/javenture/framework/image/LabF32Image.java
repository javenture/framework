package net.javenture.framework.image;


import net.javenture.framework.annotation.NullDisallow;


/*
	2022/02/10
 */
final public class LabF32Image
	extends AF32Image<Float, LabF32Color, LabF32Color.Channel>
{
	//
	final public static ILabF32ImageInstance INSTANCE = LabF32Image::new;


	//
	final private float[][][] ARRAY; // ! float[][][]


	//
	public LabF32Image(int width, int height)
	{
		super(width, height);

		ARRAY = new float[height][width][LabF32Color.Channel.COUNT];
	}


	//
	@Override
	public void get0(int x, int y, LabF32Color destination)
	{
		destination.set0(ARRAY[y][x]);
	}


	@Override
	public Float get0(int x, int y, LabF32Color.Channel channel)
	{
		return ARRAY[y][x][channel.ordinal()];
	}


	@Override
	public void set0(int x, int y, LabF32Color source)
	{
		source.get(ARRAY[y][x]);
	}


	@Override
	public void set0(int x, int y, LabF32Color.Channel channel, @NullDisallow Float value)
	{
		ARRAY[y][x][channel.ordinal()] = value;
	}


	@Override
	public void set0(int x, int y, LabF32Color.Channel channel, float value)
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
	interface ILabF32ImageInstance<I extends LabF32Image>
		extends IImage.IImageInstance<I>
	{
		@Override
		I create(int width, int height);
	}

}
