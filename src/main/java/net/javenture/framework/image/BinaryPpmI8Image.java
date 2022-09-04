package net.javenture.framework.image;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;

import java.util.Arrays;


/*
	2020/10/14
 */
final public class BinaryPpmI8Image
	extends AI8Image<Integer, RgbI8Color, RgbI8Color.Channel>
	implements IBinaryPpmImage<Integer, RgbI8Color, RgbI8Color.Channel>
{
	//
	final public static IImageInstance<BinaryPpmI8Image> INSTANCE = BinaryPpmI8Image::new;
	final public static Type TYPE = Type.I8;


	//
	final private Header HEADER;
	final private byte[] ARRAY;


	//
	public BinaryPpmI8Image(int width, int height)
	{
		super(width, height);

		Header header = new Header(width, height, TYPE.COLOR);
		byte[] array = Arrays.copyOf(header.VALUE, header.OFFSET + width * height * TYPE.DIMENSION);

		HEADER = header;
		ARRAY = array;
	}


	@SuppressWarnings("AssignmentOrReturnOfFieldWithMutableType")
	BinaryPpmI8Image(Header header, byte[] array)
	{
		super(header.WIDTH, header.HEIGHT);

		HEADER = header;
		ARRAY = array;
	}


	//
	@Override
	public Type type()
	{
		return TYPE;
	}


	@Override
	public Header header()
	{
		return HEADER;
	}


	@Override
	public void get0(int x, int y, RgbI8Color destination)
	{
		int index = index(x, y);
		int r = get0(index);
		int g = get0(index + 1);
		int b = get0(index + 2);
		destination.rgb0(r, g, b);
	}


	@Override
	public Integer get0(int x, int y, RgbI8Color.Channel channel)
	{
		int index = index(x, y);

		return get0(index + channel.ordinal());                                                   // ???
	}


	@Override
	public void set0(int x, int y, RgbI8Color source)
	{
		// XXX


		throw new UnsupportedOperationException();
	}


	@Override
	public void set0(int x, int y, RgbI8Color.Channel channel, @NullDisallow Integer value)
	{
		// XXX


		throw new UnsupportedOperationException();
	}


	@Override
	public void set0(int x, int y, RgbI8Color.Channel channel, int value)
	{
		// XXX


		throw new UnsupportedOperationException();
	}


	int rgb0(int x, int y)
	{
		int index = index(x, y);
		int r = get0(index);
		int g = get0(index + 1);
		int b = get0(index + 2);

		return RgbI8ColorUtil.rgb0(r, g, b);
	}


	private int index(int x, int y)
	{
		return HEADER.OFFSET + (WIDTH * y + x) * TYPE.DIMENSION;
	}


	private int get0(int position)
	{
		return ((int) ARRAY[position]) & 0xff;
	}


	private void set0(int position, int value)
	{
		// XXX


		throw new UnsupportedOperationException();
	}


	//
	static boolean validate(@NullAllow Header header, @NullDisallow byte[] array)
	{
		return
			header != null
			&&
			header.COLOR == TYPE.COLOR
			&&
			header.OFFSET + header.WIDTH * header.HEIGHT * TYPE.DIMENSION == array.length;
	}

}
