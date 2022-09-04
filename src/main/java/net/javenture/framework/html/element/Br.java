package net.javenture.framework.html.element;


import net.javenture.framework.html.AHtmlElement;


/*
	2018/02/08
 */
final public class Br
	extends AHtmlElement<Br>
{
	//
	final public static Config CONFIG = new Config(Type.SINGLE, "br");


	//
	public Br()
	{
	}


	public Br(AHtmlElement parent)
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
