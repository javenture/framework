package net.javenture.framework.image;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;


/*
	2020/04/20
 */
abstract public class AImage<V extends Number, C extends IColor, CH extends IColorChannel>
	implements IImage<V, C, CH>
{
	//
	final protected int WIDTH;
	final protected int HEIGHT;
	final protected ImageBorder<V, C, CH> BORDER;


	//
	protected AImage(int width, int height)
	{
		WIDTH = width;
		HEIGHT = height;
		BORDER = new ImageBorder<>(this);
	}


	//
	@Override
	final public int width()
	{
		return WIDTH;
	}


	@Override
	final public int height()
	{
		return HEIGHT;
	}


	@Override
	final public ImageBorder<V, C, CH> border()
	{
		return BORDER;
	}


	@Override
	final public boolean contains(int x, int y)
	{
		return BORDER.contains(x, y);
	}


	@Override
	final public boolean get(int x, int y, C destination)
	{
		if (contains(x, y))
		{
			get0(x, y, destination);

			return true;
		}
		else
		{
			return false;
		}
	}


	@Override
	final public @NullAllow V get(int x, int y, CH channel)
	{
		return contains(x, y) ? get0(x, y, channel) : null;
	}


	@Override
	final public boolean set(int x, int y, C source)
	{
		if (contains(x, y))
		{
			set0(x, y, source);

			return true;
		}
		else
		{
			return false;
		}
	}


	@Override
	final public boolean set(int x, int y, CH channel, @NullDisallow V value)
	{
		if (contains(x, y))
		{
			set0(x, y, channel, value);

			return true;
		}
		else
		{
			return false;
		}
	}


	@Override
	final public void init(C color)
	{
		for (int j = 0; j < HEIGHT; j++)
		{
			for (int i = 0; i < WIDTH; i++) set0(i, j, color);
		}
	}

}
