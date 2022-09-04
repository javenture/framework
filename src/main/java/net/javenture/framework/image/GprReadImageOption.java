package net.javenture.framework.image;


/*
	2020/10/18
 */
final public class GprReadImageOption
	extends AReadImageOption
{
	//
	final public LibrawUtil.Preset PRESET;
	final public RgbColorSpace SPACE;


	//
	public GprReadImageOption(LibrawUtil.Preset preset, RgbColorSpace space)
	{
		PRESET = preset;
		SPACE = space;
	}


	//
	@Override
	public ImageFormat format()
	{
		return ImageFormat.GPR;
	}

}
