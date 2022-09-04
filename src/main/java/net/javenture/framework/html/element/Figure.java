package net.javenture.framework.html.element;


import net.javenture.framework.html.AHtmlElement;


/*
	2018/02/22
 */
final public class Figure
	extends AHtmlElement<Figure>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "figure");


	//
	public Figure()
	{
	}


	public Figure(AHtmlElement parent)
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
