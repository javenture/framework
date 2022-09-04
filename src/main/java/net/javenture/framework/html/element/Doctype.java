package net.javenture.framework.html.element;


import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;
import net.javenture.framework.html.HtmlPreset;


/*
	2019/07/02
 */
final public class Doctype
	extends AHtmlElement<Doctype>
{
	//
	final public static Config CONFIG = new Config(Type.SINGLE, "!doctype");


	//
	final public static HtmlPreset HTML5 = new HtmlPreset
	(
		new Doctype()
			.html()
	);


	//
	public Doctype()
	{
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	public Doctype html()
	{
		return attribute(Attributes.HTML);
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template HTML = new HtmlAttribute.Template("html");
	}

}
