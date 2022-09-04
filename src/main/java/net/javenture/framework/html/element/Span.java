package net.javenture.framework.html.element;


import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;


/*
	2018/02/07
 */
final public class Span
	extends AHtmlContent<Span>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "span");


	//
	public Span()
	{
	}


	public Span(AHtmlElement parent)
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
