package net.javenture.framework.image;


/*
	2020/10/19
 */
final public class PngWriteImageOption
	extends AWriteImageOption
{
	//
	final public float COMPRESSION;


	//
	public PngWriteImageOption(float compression)
	{
		COMPRESSION = compression;
	}


	public PngWriteImageOption(double compression)
	{
		this((float) compression);
	}


	//
	@Override
	public ImageFormat format()
	{
		return ImageFormat.PNG;
	}


	//
	final public static PngWriteImageOption DEFAULT = new PngWriteImageOption(0); // 0 = 100%

}
