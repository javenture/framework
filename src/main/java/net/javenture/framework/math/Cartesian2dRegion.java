package net.javenture.framework.math;


/*
	2020/04/21
 */
final public class Cartesian2dRegion
{
	//
	final public int WIDTH;
	final public int HEIGHT;
	final public int LEFT;
	final public int RIGHT;
	final public int TOP;
	final public int BOTTOM;


	//
	public Cartesian2dRegion(int width, int height)
	{
		WIDTH = width;
		HEIGHT = height;
		LEFT = 0;
		RIGHT = WIDTH - 1;
		TOP = 0;
		BOTTOM = HEIGHT - 1;
	}


	public Cartesian2dRegion(int left, int right, int top, int bottom)
	{
		WIDTH = right - left + 1;
		HEIGHT = bottom - top + 1;
		LEFT = left;
		RIGHT = right;
		TOP = top;
		BOTTOM = bottom;
	}


	//
	public int width()
	{
		return WIDTH;
	}


	public int height()
	{
		return HEIGHT;
	}


	public int left()
	{
		return LEFT;
	}


	public int right()
	{
		return RIGHT;
	}


	public int top()
	{
		return TOP;
	}


	public int bottom()
	{
		return BOTTOM;
	}


	public boolean contains(int x, int y)
	{
		return
			x >= LEFT
			&&
			x <= RIGHT
			&&
			y >= TOP
			&&
			y <= BOTTOM;
	}

}
