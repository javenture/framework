package net.javenture.framework.image;


/*
	2022/01/20
 */
final public class Cr2ReadImageOption
	extends AReadImageOption
{
	//
	final public LibrawUtil.Preset PRESET;
	final public RgbColorSpace SPACE;


	//
	public Cr2ReadImageOption(LibrawUtil.Preset preset, RgbColorSpace space)
	{
		PRESET = preset;
		SPACE = space;
	}


	//
	@Override
	public ImageFormat format()
	{
		return ImageFormat.CR2;
	}

}
