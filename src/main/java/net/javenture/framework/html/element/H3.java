package net.javenture.framework.html.element;


import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;


/*
	2018/02/07
 */
final public class H3
	extends AHtmlContent<H3>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "h3");


	//
	public H3()
	{
	}


	public H3(AHtmlElement parent)
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
