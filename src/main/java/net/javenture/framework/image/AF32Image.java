package net.javenture.framework.image;


/*
	2020/04/28
 */
abstract public class AF32Image<V extends Float, C extends AF32Color, CH extends IF32ColorChannel>
	extends AImage<V, C, CH>
{
	//
	protected AF32Image(int width, int height)
	{
		super(width, height);
	}


	//
	abstract public void set0(int x, int y, CH channel, float value);
	abstract float[] array0(int x, int y);


	//
	@Override
	final public void init(CH channel, V value)
	{
		int ordinal = channel.ordinal();

		for (int j = 0; j < HEIGHT; j++)
		{
			for (int i = 0; i < WIDTH; i++) array0(i, j)[ordinal] = value;
		}
	}


	final public void set0(int x, int y, CH channel, double value)
	{
		set0(x, y, channel, (float) value);
	}


	final public void get0(int x, int y, int width, int height, CH channel, float[][] destination)
	{
		for (int j = 0; j < height; j++)
		{
			float[] destination2 = destination[j];

			for (int i = 0; i < width; i++) destination2[i] = get0(x + i, y + j, channel);
		}
	}


	final public void get0(int x, int y, int width, int height, CH channel, double[][] destination)
	{
		for (int j = 0; j < height; j++)
		{
			double[] destination2 = destination[j];

			for (int i = 0; i < width; i++) destination2[i] = get0(x + i, y + j, channel);
		}
	}


	final public void set0(int x, int y, int width, int height, CH channel, float[][] source)
	{
		for (int j = 0; j < height; j++)
		{
			float[] source2 = source[j];

			for (int i = 0; i < width; i++) set0(x + i, y + j, channel, source2[i]);
		}
	}


	final public void set0(int x, int y, int width, int height, CH channel, double[][] source)
	{
		for (int j = 0; j < height; j++)
		{
			double[] source2 = source[j];

			for (int i = 0; i < width; i++) set0(x + i, y + j, channel, source2[i]);
		}
	}

}
