package net.javenture.framework.camera;


import net.javenture.framework.image.LibrawUtil;
import net.javenture.framework.image.RgbColorSpace;


/*
	2020/05/19
 */
public interface ICamera                                              // ???
{
	//
	LibrawUtil.Preset getLibrawConfig();
	RgbColorSpace getRgbColorSpace();

}
