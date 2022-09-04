package net.javenture.framework.html.element;


import net.javenture.framework.html.AHtmlElement;


/*
	2018/02/07
 */
final public class Body
	extends AHtmlElement<Body>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "body");


	//
	public Body()
	{
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}

}
