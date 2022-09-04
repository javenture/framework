package net.javenture.framework.image;


/*
	2020/10/19
 */
final public class DngReadImageOption
	extends AReadImageOption
{
	//
	final public LibrawUtil.Preset PRESET;
	final public RgbColorSpace SPACE;


	//
	public DngReadImageOption(LibrawUtil.Preset preset, RgbColorSpace space)
	{
		PRESET = preset;
		SPACE = space;
	}


	//
	@Override
	public ImageFormat format()
	{
		return ImageFormat.DNG;
	}

}
