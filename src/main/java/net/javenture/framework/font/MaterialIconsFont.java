package net.javenture.framework.font;


import net.javenture.framework.html.HtmlPreset;


/*
	2017/11/11
 */
public enum MaterialIconsFont
	implements IFont
{
	//
	REGULAR_400("MaterialIcons");


	//
	final private static String DIR = "materialicons";


	//
	final private String ALIAS;
	final private HtmlPreset LINK;


	//
	MaterialIconsFont(String alias)
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
