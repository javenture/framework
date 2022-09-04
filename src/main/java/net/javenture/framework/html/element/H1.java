package net.javenture.framework.html.element;


import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;


/*
	2018/02/07
 */
final public class H1
	extends AHtmlContent<H1>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "h1");


	//
	public H1()
	{
	}


	public H1(AHtmlElement parent)
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
