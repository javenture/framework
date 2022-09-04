package net.javenture.framework.html.element;


import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;


/*
	2018/02/07
 */
final public class Title
	extends AHtmlContent<Title>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "title");


	//
	public Title()
	{
	}


	public Title(AHtmlElement parent)
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
