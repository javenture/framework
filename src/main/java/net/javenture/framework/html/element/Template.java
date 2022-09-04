package net.javenture.framework.html.element;


import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;


/*
	2019/04/24
 */
final public class Template
	extends AHtmlContent<Template>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "template");


	//
	public Template()
	{
	}


	public Template(AHtmlElement parent)
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
