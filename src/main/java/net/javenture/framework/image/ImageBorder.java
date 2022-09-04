package net.javenture.framework.image;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.math.Cartesian2dRegion;


/*
	2020/04/21
 */
final public class ImageBorder<V extends Number, C extends IColor, CH extends IColorChannel>
{
	//
	final public IImage<V, C, CH> IMAGE;
	final private Cartesian2dRegion REGION;


	//
	public ImageBorder(IImage<V, C, CH> image)
	{
		IMAGE = image;
		REGION = new Cartesian2dRegion(image.width(), image.height());
	}


	public ImageBorder(IImage<V, C, CH> image, int left, int right, int top, int bottom)
	{
		IMAGE = image;
		REGION = new Cartesian2dRegion(left, right, top, bottom);
	}


	//
	public IImage<V, C, CH> image()
	{
		return IMAGE;
	}


	public int width()
	{
		return REGION.WIDTH;
	}


	public int height()
	{
		return REGION.HEIGHT;
	}


	public int left()
	{
		return REGION.LEFT;
	}


	public int right()
	{
		return REGION.RIGHT;
	}


	public int top()
	{
		return REGION.TOP;
	}


	public int bottom()
	{
		return REGION.BOTTOM;
	}


	public boolean contains(int x, int y)
	{
		return REGION.contains(x, y);
	}


	public int x(int value)
	{
		return Math.min(Math.max(REGION.LEFT, value), REGION.RIGHT);
	}


	public int y(int value)
	{
		return Math.min(Math.max(REGION.TOP, value), REGION.BOTTOM);
	}


	public void get(int x, int y, C destination)
	{
		int x0 = x(x);
		int y0 = y(y);
		IMAGE.get0(x0, y0, destination);
	}


	public @NullDisallow V get(int x, int y, CH channel)
	{
		int x0 = x(x);
		int y0 = y(y);

		return IMAGE.get0(x0, y0, channel);
	}


	public void set(int x, int y, C source)
	{
		int x0 = x(x);
		int y0 = y(y);
		IMAGE.set0(x0, y0, source);
	}


	public void set(int x, int y, CH channel, @NullDisallow V value)
	{
		int x0 = x(x);
		int y0 = y(y);
		IMAGE.set0(x0, y0, channel, value);
	}

}
