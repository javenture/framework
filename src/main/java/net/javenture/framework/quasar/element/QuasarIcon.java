package net.javenture.framework.quasar.element;


import net.javenture.framework.css.CssUnit;
import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;
import net.javenture.framework.icon.IIcon;
import net.javenture.framework.quasar.IQuasarColor;


/*
	2019/07/19
 */
final public class QuasarIcon
	extends AHtmlContent<QuasarIcon>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "q-icon");


	//
	public QuasarIcon()
	{
	}


	public QuasarIcon(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	public QuasarIcon name(IIcon icon)
	{
		return attribute(Attributes.NAME, icon);
	}


	public QuasarIcon name(String s)
	{
		return attribute(Attributes.NAME, s);
	}


	public QuasarIcon color(IQuasarColor value)
	{
		return attribute(IQuasarColor.HtmlAttributes.TEMPLATE_COLOR, value);
	}


	public QuasarIcon color(String value)
	{
		return attribute(IQuasarColor.HtmlAttributes.TEMPLATE_COLOR, value);
	}


	public QuasarIcon size(int value, CssUnit unit)                                                                                        // XXX: ?
	{
		HtmlAttribute attribute = new HtmlAttribute(Attributes.SIZE, value);

		if (value != 0) attribute.value(unit);

		return attribute(attribute);
	}


	public QuasarIcon left()
	{
		return attribute(Attributes.LEFT);
	}


	public QuasarIcon left(boolean condition)
	{
		return attribute(condition, Attributes.LEFT);
	}


	public QuasarIcon right()
	{
		return attribute(Attributes.RIGHT);
	}


	public QuasarIcon right(boolean condition)
	{
		return attribute(condition, Attributes.RIGHT);
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template LEFT = new HtmlAttribute.Template("left");
		final public static HtmlAttribute.Template RIGHT = new HtmlAttribute.Template("right");
		final public static HtmlAttribute.Template NAME = new HtmlAttribute.Template("name");
		final public static HtmlAttribute.Template SIZE = new HtmlAttribute.Template("size");
	}

}
