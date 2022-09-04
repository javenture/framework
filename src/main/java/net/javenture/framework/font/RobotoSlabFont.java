package net.javenture.framework.font;


import net.javenture.framework.html.HtmlPreset;


/*
	2017/11/11
 */
public enum RobotoSlabFont
	implements IFont
{
	//
	ALL("RobotoSlab"),
	THIN_100("RobotoSlab-Thin"),
	LIGHT_300("RobotoSlab-Light"),
	REGULAR_400("RobotoSlab-Regular"),
	BOLD_700("RobotoSlab-Bold");


	//
	final private static String DIR = "robotoslab";


	//
	final private String ALIAS;
	final private HtmlPreset LINK;


	//
	RobotoSlabFont(String alias)
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
