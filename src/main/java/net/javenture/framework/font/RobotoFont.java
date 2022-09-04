package net.javenture.framework.font;


import net.javenture.framework.html.HtmlPreset;


/*
	2017/11/11
 */
public enum RobotoFont
	implements IFont
{
	//
	ALL("Roboto"),
	THIN_100("Roboto-Thin"),
	LIGHT_300("Roboto-Light"),
	REGULAR_400("Roboto-Regular"),
	MEDIUM_500("Roboto-Medium"),
	BOLD_700("Roboto-Bold"),
	BLACK_900("Roboto-Black"),
	THIN_ITALIC_100("Roboto-ThinItalic"),
	LIGHT_ITALIC_300("Roboto-LightItalic"),
	ITALIC_400("Roboto-Italic"),
	MEDIUM_ITALIC_500("Roboto-MediumItalic"),
	BOLD_ITALIC_700("Roboto-BoldItalic"),
	BLACK_ITALIC_900("Roboto-BlackItalic");


	//
	final private static String DIR = "roboto";


	//
	final private String ALIAS;
	final private HtmlPreset LINK;


	//
	RobotoFont(String alias)
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
