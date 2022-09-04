package net.javenture.framework.html.element;


import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;


/*
	2018/02/07
 */
final public class H4
	extends AHtmlContent<H4>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "h4");


	//
	public H4()
	{
	}


	public H4(AHtmlElement parent)
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
