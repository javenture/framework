package net.javenture.framework.html.element;


import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;


/*
	2018/02/07
 */
final public class Div
	extends AHtmlContent<Div>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "div");                        // XXX: public ?


	//
	public Div()
	{
	}


	public Div(AHtmlElement parent)
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
