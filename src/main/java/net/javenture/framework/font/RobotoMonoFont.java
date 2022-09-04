package net.javenture.framework.font;


import net.javenture.framework.html.HtmlPreset;


/*
	2017/11/11
 */
public enum RobotoMonoFont
	implements IFont
{
	//
	ALL("RobotoMono"),
	THIN_100("RobotoMono-Thin"),
	LIGHT_300("RobotoMono-Light"),
	REGULAR_400("RobotoMono-Regular"),
	MEDIUM_500("RobotoMono-Medium"),
	BOLD_700("RobotoMono-Bold"),
	THIN_ITALIC_100("RobotoMono-ThinItalic"),
	LIGHT_ITALIC_300("RobotoMono-LightItalic"),
	ITALIC_400("RobotoMono-Italic"),
	MEDIUM_ITALIC_500("RobotoMono-MediumItalic"),
	BOLD_ITALIC_700("RobotoMono-BoldItalic");


	//
	final private static String DIR = "robotomono";


	//
	final private String ALIAS;
	final private HtmlPreset LINK;


	//
	RobotoMonoFont(String alias)
	{
		ALIAS = alias;
		LINK = IFont.link(DIR, alias);
	}


	//
	@Override
	public String alias()
	{
		return ALIAS;
	}


	@Override
	public HtmlPreset link()
	{
		return LINK;
	}

}
