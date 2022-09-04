package net.javenture.framework.image;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.util.ShortUtil;

import java.util.Arrays;


/*
	2020/10/17
 */
final public class BinaryPpmI16Image
	extends AI16Image<Integer, RgbI16Color, RgbI16Color.Channel>
	implements IBinaryPpmImage<Integer, RgbI16Color, RgbI16Color.Channel>
{
	//
	final public static IImageInstance<BinaryPpmI16Image> INSTANCE = BinaryPpmI16Image::new;
	final public static Type TYPE = Type.I16;


	//
	final private Header HEADER;
	final private byte[] ARRAY;


	//
	public BinaryPpmI16Image(int width, int height)
	{
		super(width, height);

		Header header = new Header(width, height, TYPE.COLOR);
		byte[] array = Arrays.copyOf(header.VALUE, header.OFFSET + width * height * TYPE.DIMENSION);

		HEADER = header;
		ARRAY = array;
	}


	@SuppressWarnings("AssignmentOrReturnOfFieldWithMutableType")
	BinaryPpmI16Image(Header header, byte[] array)
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
	public void get0(int x, int y, RgbI16Color destination)
	{
		int index = index(x, y);
		int r = get0(index);
		int g = get0(index + 2);
		int b = get0(index + 4);
		destination.rgb0(r, g, b);
	}


	@Override
	public Integer get0(int x, int y, RgbI16Color.Channel channel)
	{
		int index = index(x, y);

		return get0(index + channel.ordinal());                           // ???
	}


	@Override
	public void set0(int x, int y, RgbI16Color source)
	{
		// XXX


		throw new UnsupportedOperationException();
	}


	@Override
	public void set0(int x, int y, RgbI16Color.Channel channel, @NullDisallow Integer value)
	{
		// XXX


		throw new UnsupportedOperationException();
	}


	@Override
	public void set0(int x, int y, RgbI16Color.Channel channel, int value)
	{
		// XXX


		throw new UnsupportedOperationException();
	}


	long rgb0(int x, int y)
	{
		int index = index(x, y);
		int r = get0(index);
		int g = get0(index + 2);
		int b = get0(index + 4);

		return RgbI16ColorUtil.rgb0(r, g, b);
	}


	private int index(int x, int y)
	{
		return HEADER.OFFSET + (WIDTH * y + x) * TYPE.DIMENSION;
	}


	private int get0(int position)
	{
		return ShortUtil.parse(ARRAY[position], ARRAY[position + 1]);
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
