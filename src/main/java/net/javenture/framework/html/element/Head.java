package net.javenture.framework.html.element;


import net.javenture.framework.html.AHtmlElement;


/*
	2018/02/07
 */
final public class Head
	extends AHtmlElement<Head>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "head");


	//
	public Head()
	{
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}

}
