package net.javenture.framework.html.element;


import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;


/*
	2019/05/06
 */
final public class Label
	extends AHtmlContent<Label>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "label");


	//
	public Label()
	{
	}


	public Label(AHtmlElement parent)
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