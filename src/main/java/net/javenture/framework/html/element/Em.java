package net.javenture.framework.html.element;


import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;


/*
	2018/02/07
 */
final public class Em
	extends AHtmlContent<Em>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "em");


	//
	public Em()
	{
	}


	public Em(AHtmlElement parent)
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
