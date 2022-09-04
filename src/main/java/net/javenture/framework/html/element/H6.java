package net.javenture.framework.html.element;


import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;


/*
	2018/02/07
 */
final public class H6
	extends AHtmlContent<H6>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "h6");


	//
	public H6()
	{
	}


	public H6(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}

}
