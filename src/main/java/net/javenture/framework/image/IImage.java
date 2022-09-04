package net.javenture.framework.image;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;


/*
	2020/05/18
 */
public interface IImage<V extends Number, C extends IColor, CH extends IColorChannel>
{
	//
	int width();
	int height();
	ImageBorder<V, C, CH> border();
	boolean contains(int x, int y);

	boolean get(int x, int y, C destination);
	void get0(int x, int y, C destination);
	@NullAllow V get(int x, int y, CH channel);
	@NullDisallow V get0(int x, int y, CH channel);

	boolean set(int x, int y, C source);
	void set0(int x, int y, C source);
	boolean set(int x, int y, CH channel, @NullDisallow V value);
	void set0(int x, int y, CH channel, @NullDisallow V value);

	void init(C color);
	void init(CH channel, V value);


	//
	@FunctionalInterface
	interface IImageInstance<I extends IImage>
	{
		I create(int width, int height);
	}




	// XXX: Config: channels, min/max(channel)

}
