package net.javenture.framework.camera;


import net.javenture.framework.image.LibrawUtil;
import net.javenture.framework.image.RgbColorSpace;


/*
	2020/06/01
 */
final public class GoProFusionCamera
	implements ICamera
{
	//
	final public static int WIDTH = 3104;
	final public static int HEIGHT = 3000;

	//final public static LibrawUtil.Config LIBRAW_CONFIG_DEFAULT = new LibrawUtil.Config("-W", "-g", "1", "1", "-o", "1", "-c", "0");
	//final public static LibrawUtil.Config LIBRAW_CONFIG_DEFAULT = new LibrawUtil.Config("-6", "-W", "-g", "2.222", "4.5", "-o", "1", "-c", "0", "-q", "12");

	final public static LibrawUtil.Preset LIBRAW_CONFIG_DEFAULT = new LibrawUtil.Preset
	(
		LibrawUtil.OPTION_FORMAT_16_BIT,
		//LibrawUtil.,
		LibrawUtil.OPTION_GAMMA_LINEAR,
		LibrawUtil.OPTION_COLORSPACE_RAW,                                    // ???
		LibrawUtil.OPTION_INTERPOLATION_AAHD,
		LibrawUtil.OPTION_DENOISING_200                                  // ???

		//"-W",
		//"-c", "0",
	);


	//
	final private LibrawUtil.Preset LIBRAW_CONFIG;
	final private RgbColorSpace SPACE;


	//
	private GoProFusionCamera()
	{
		LIBRAW_CONFIG = LIBRAW_CONFIG_DEFAULT;
		SPACE = RgbColorSpace.S_RGB;
	}


	public GoProFusionCamera(LibrawUtil.Preset presetLibraw, RgbColorSpace space)
	{
		LIBRAW_CONFIG = presetLibraw;
		SPACE = space;
	}


	//
	@Override
	public LibrawUtil.Preset getLibrawConfig()
	{
		return LIBRAW_CONFIG;
	}


	@Override
	public RgbColorSpace getRgbColorSpace()
	{
		return SPACE;
	}


	//
	final public static GoProFusionCamera DEFAULT = new GoProFusionCamera();

}
