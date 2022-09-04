package net.javenture.framework.html.element;


import net.javenture.framework.html.AHtmlElement;


/*
	2018/02/07
 */
final public class Root
	extends AHtmlElement<Root>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "html");


	//
	public Root()
	{
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}

}
