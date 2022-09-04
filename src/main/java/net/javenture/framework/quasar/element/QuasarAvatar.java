package net.javenture.framework.quasar.element;


import net.javenture.framework.css.CssUnit;
import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;
import net.javenture.framework.icon.IIcon;
import net.javenture.framework.quasar.IQuasarColor;


/*
	2019/11/06
 */
final public class QuasarAvatar
	extends AHtmlContent<QuasarAvatar>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "q-avatar");


	//
	public QuasarAvatar()
	{
	}


	public QuasarAvatar(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	public QuasarAvatar fontSize(int value, CssUnit unit)
	{
		HtmlAttribute attribute = new HtmlAttribute(Attributes.FONT_SIZE, value);

		if (value != 0) attribute.value(unit);

		return attribute(attribute);
	}


	public QuasarAvatar icon(IIcon value)
	{
		return attribute(Attributes.ICON, value);
	}


	public QuasarAvatar icon(String value)
	{
		return attribute(Attributes.ICON, value);
	}


	public QuasarAvatar icon(boolean condition, IIcon value)
	{
		return attribute(condition, Attributes.ICON, value);
	}


	public QuasarAvatar icon(boolean condition, String value)
	{
		return attribute(condition, Attributes.ICON, value);
	}


	public QuasarAvatar rounded()
	{
		return attribute(Attributes.ROUNDED);
	}


	public QuasarAvatar rounded(boolean condition)
	{
		return attribute(condition, Attributes.ROUNDED);
	}


	public QuasarAvatar color(IQuasarColor value)
	{
		return attribute(IQuasarColor.HtmlAttributes.TEMPLATE_COLOR, value);
	}


	public QuasarAvatar color(String value)
	{
		return attribute(IQuasarColor.HtmlAttributes.TEMPLATE_COLOR, value);
	}


	public QuasarAvatar textColor(IQuasarColor value)
	{
		return attribute(IQuasarColor.HtmlAttributes.TEMPLATE_TEXT_COLOR, value);
	}


	public QuasarAvatar textColor(String value)
	{
		return attribute(IQuasarColor.HtmlAttributes.TEMPLATE_TEXT_COLOR, value);
	}


	public QuasarAvatar size(Size value)
	{
		return attribute(value);
	}


	public QuasarAvatar size(int value, CssUnit unit)
	{
		HtmlAttribute attribute = new HtmlAttribute(Attributes.SIZE, value);

		if (value != 0) attribute.value(unit);

		return attribute(attribute);
	}


	public QuasarAvatar square()
	{
		return attribute(Attributes.SQUARE);
	}


	public QuasarAvatar square(boolean condition)
	{
		return attribute(condition, Attributes.SQUARE);
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template FONT_SIZE = new HtmlAttribute.Template("font-size");
		final public static HtmlAttribute.Template ICON = new HtmlAttribute.Template("icon");
		final public static HtmlAttribute.Template ROUNDED = new HtmlAttribute.Template("rounded");
		final public static HtmlAttribute.Template SIZE = new HtmlAttribute.Template("size");
		final public static HtmlAttribute.Template SQUARE = new HtmlAttribute.Template("square");
	}


	final public static class Size
		extends HtmlAttribute.Preset
	{
		//
		private Size(String s)
		{
			super(Attributes.SIZE, s);
		}

		//
		final public static Size XS = new Size("xs");
		final public static Size SM = new Size("sm");
		final public static Size MD = new Size("md");
		final public static Size LG = new Size("lg");
		final public static Size XL = new Size("xl");
	}

}
