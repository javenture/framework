package net.javenture.framework.html.element;


import net.javenture.framework.css.CssUnit;
import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;


/*
	2019/07/04
 */
public class Table                                                                                    // XXX: final ?
	extends AHtmlContent<Table>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "table");


	//
	public Table()
	{
	}


	public Table(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	final public Table width(int value, CssUnit unit)
	{
		HtmlAttribute attribute = new HtmlAttribute(Attributes.WIDTH, value);

		if (value != 0) attribute.value(unit);

		return super.attribute(attribute);
	}


	final public Table cellspacing(int value, CssUnit unit)
	{
		HtmlAttribute attribute = new HtmlAttribute(Attributes.CELLSPACING, value);

		if (value != 0) attribute.value(unit);

		return super.attribute(attribute);
	}


	final public Table cellpadding(int value, CssUnit unit)
	{
		HtmlAttribute attribute = new HtmlAttribute(Attributes.CELLPADDING, value);

		if (value != 0) attribute.value(unit);

		return super.attribute(attribute);
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template WIDTH = new HtmlAttribute.Template("width");
		final public static HtmlAttribute.Template CELLSPACING = new HtmlAttribute.Template("cellspacing");
		final public static HtmlAttribute.Template CELLPADDING = new HtmlAttribute.Template("cellpadding");
	}

}
