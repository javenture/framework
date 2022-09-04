package net.javenture.framework.html.element;


import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;


/*
	2018/02/07
 */
final public class H5
	extends AHtmlContent<H5>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "h5");


	//
	public H5()
	{
	}


	public H5(AHtmlElement parent)
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
