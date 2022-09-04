package net.javenture.framework.quasar.element;


import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;


/*
	2019/07/04
 */
final public class QuasarItemLabel
	extends AHtmlContent<QuasarItemLabel>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "q-item-label");


	//
	public QuasarItemLabel()
	{
	}


	public QuasarItemLabel(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	public QuasarItemLabel overline()
	{
		return attribute(Attributes.OVERLINE);
	}


	public QuasarItemLabel overline(boolean condition)
	{
		return attribute(condition, Attributes.OVERLINE);
	}


	public QuasarItemLabel caption()
	{
		return attribute(Attributes.CAPTION);
	}


	public QuasarItemLabel caption(boolean condition)
	{
		return attribute(condition, Attributes.CAPTION);
	}


	public QuasarItemLabel header()
	{
		return attribute(Attributes.HEADER);
	}


	public QuasarItemLabel header(boolean condition)
	{
		return attribute(condition, Attributes.HEADER);
	}


	public QuasarItemLabel lines(int value)
	{
		return attribute(Attributes.LINES, value);
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template OVERLINE = new HtmlAttribute.Template("overline");
		final public static HtmlAttribute.Template CAPTION = new HtmlAttribute.Template("caption");
		final public static HtmlAttribute.Template HEADER = new HtmlAttribute.Template("header");
		final public static HtmlAttribute.Template LINES = new HtmlAttribute.Template("lines");
	}

}
