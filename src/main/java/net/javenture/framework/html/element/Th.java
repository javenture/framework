package net.javenture.framework.html.element;


import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.HtmlAttribute;


/*
	2019/07/05
 */
final public class Th
	extends AHtmlContent<Th>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "th");


	//
	public Th()
	{
	}


	public Th(Tr parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	public Th rowspan(int value)
	{
		return super.attribute(Attributes.ROWSPAN, value);
	}


	public Th colspan(int value)
	{
		return super.attribute(Attributes.COLSPAN, value);
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template ROWSPAN = new HtmlAttribute.Template("rowspan");
		final public static HtmlAttribute.Template COLSPAN = new HtmlAttribute.Template("colspan");
	}

}
