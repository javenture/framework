package net.javenture.framework.image;


/*
	2020/05/12
 */
abstract public class AI12Image<V extends Integer, C extends AI12Color, CH extends II12ColorChannel>
	extends AImage<V, C, CH>
{
	//
	protected AI12Image(int width, int height)
	{
		super(width, height);
	}


	//
	abstract public void set0(int x, int y, CH channel, int value);


	//
	@Override
	final public void init(CH channel, V value)
	{
		for (int j = 0; j < HEIGHT; j++)
		{
			for (int i = 0; i < WIDTH; i++) set0(i, j, channel, value);
		}
	}


	final public void set0(int x, int y, CH channel, long value)
	{
		set0(x, y, channel, (int) value);
	}


	final public void get0(int x, int y, int width, int height, CH channel, int[][] destination)
	{
		for (int j = 0; j < height; j++)
		{
			int[] destination2 = destination[j];

			for (int i = 0; i < width; i++) destination2[i] = get0(x + i, y + j, channel);
		}
	}


	final public void set0(int x, int y, int width, int height, CH channel, int[][] source)
	{
		for (int j = 0; j < height; j++)
		{
			int[] source2 = source[j];

			for (int i = 0; i < width; i++) set0(x + i, y + j, channel, source2[i]);
		}
	}

}
