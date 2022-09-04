package net.javenture.framework.quasar.element;


import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;
import net.javenture.framework.quasar.IQuasarColor;


/*
	2019/07/04
 */
final public class QuasarRadio
	extends AHtmlContent<QuasarRadio>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "q-radio");


	//
	public QuasarRadio()
	{
	}


	public QuasarRadio(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	public QuasarRadio val(String s)                                                                                     // XXX: String, Number ?
	{
		return attribute(Attributes.VAL, s);
	}


	public QuasarRadio label(String s)
	{
		return attribute(Attributes.LABEL, s);
	}


	public QuasarRadio leftLabel()
	{
		return attribute(Attributes.LEFT_LABEL);
	}


	public QuasarRadio leftLabel(boolean condition)
	{
		return attribute(condition, Attributes.LEFT_LABEL);
	}


	public QuasarRadio color(IQuasarColor value)
	{
		return attribute(IQuasarColor.HtmlAttributes.TEMPLATE_COLOR, value);
	}


	public QuasarRadio color(String value)
	{
		return attribute(IQuasarColor.HtmlAttributes.TEMPLATE_COLOR, value);
	}


	public QuasarRadio keepColor()
	{
		return attribute(Attributes.KEEP_COLOR);
	}


	public QuasarRadio keepColor(boolean condition)
	{
		return attribute(condition, Attributes.KEEP_COLOR);
	}


	public QuasarRadio disable()
	{
		return attribute(Attributes.DISABLE);
	}


	public QuasarRadio disable(boolean condition)
	{
		return attribute(condition, Attributes.DISABLE);
	}


	public QuasarRadio dark()
	{
		return attribute(Attributes.DARK);
	}


	public QuasarRadio dark(boolean condition)
	{
		return attribute(condition, Attributes.DARK);
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template LEFT_LABEL = new HtmlAttribute.Template("left-label");
		final public static HtmlAttribute.Template KEEP_COLOR = new HtmlAttribute.Template("keep-color");
		final public static HtmlAttribute.Template DISABLE = new HtmlAttribute.Template("disable");
		final public static HtmlAttribute.Template DARK = new HtmlAttribute.Template("dark");
		final public static HtmlAttribute.Template VAL = new HtmlAttribute.Template("val");
		final public static HtmlAttribute.Template LABEL = new HtmlAttribute.Template("label");
	}

}
