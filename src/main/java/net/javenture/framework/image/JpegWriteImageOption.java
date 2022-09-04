package net.javenture.framework.image;


/*
	2020/10/19
 */
final public class JpegWriteImageOption
	extends AWriteImageOption
{
	//
	final public float QUALITY;


	//
	public JpegWriteImageOption(float quality)
	{
		QUALITY = quality;
	}


	public JpegWriteImageOption(double quality)
	{
		this((float) quality);
	}


	//
	@Override
	public ImageFormat format()
	{
		return ImageFormat.JPEG;
	}


	//
	final public static JpegWriteImageOption DEFAULT = new JpegWriteImageOption(1); // 1 = 100%

}
