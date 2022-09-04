package net.javenture.framework.html.element;


import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;


/*
	2018/02/07
 */
final public class H2
	extends AHtmlContent<H2>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "h2");


	//
	public H2()
	{
	}


	public H2(AHtmlElement parent)
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
