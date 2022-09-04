package net.javenture.framework.quasar.element;


import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;
import net.javenture.framework.quasar.IQuasarColor;


/*
	2019/07/04
 */
final public class QuasarSeparator
	extends AHtmlContent<QuasarSeparator>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "q-separator");


	//
	public QuasarSeparator()
	{
	}


	public QuasarSeparator(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	public QuasarSeparator color(IQuasarColor value)
	{
		return attribute(IQuasarColor.HtmlAttributes.TEMPLATE_COLOR, value);
	}


	public QuasarSeparator color(String value)
	{
		return attribute(IQuasarColor.HtmlAttributes.TEMPLATE_COLOR, value);
	}


	public QuasarSeparator dark()
	{
		return attribute(Attributes.DARK);
	}


	public QuasarSeparator dark(boolean condition)
	{
		return attribute(condition, Attributes.DARK);
	}


	public QuasarSeparator spaced()
	{
		return attribute(Attributes.SPACED);
	}


	public QuasarSeparator spaced(boolean condition)
	{
		return attribute(condition, Attributes.SPACED);
	}


	public QuasarSeparator inset()
	{
		return attribute(Attributes.INSET);
	}


	public QuasarSeparator inset(boolean condition)
	{
		return attribute(condition, Attributes.INSET);
	}


	public QuasarSeparator inset(Inset value)
	{
		return attribute(value);
	}


	public QuasarSeparator vertical()
	{
		return attribute(Attributes.VERTICAL);
	}


	public QuasarSeparator vertical(boolean condition)
	{
		return attribute(condition, Attributes.VERTICAL);
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template DARK = new HtmlAttribute.Template("dark");
		final public static HtmlAttribute.Template SPACED = new HtmlAttribute.Template("spaced");
		final public static HtmlAttribute.Template INSET = new HtmlAttribute.Template("inset");
		final public static HtmlAttribute.Template VERTICAL = new HtmlAttribute.Template("vertical");
	}


	final public static class Inset
		extends HtmlAttribute.Preset
	{
		//
		private Inset(String s)
		{
			super(Attributes.INSET, s);
		}

		//
		final public static Inset ITEM = new Inset("item");
		final public static Inset ITEM_THUMBNAIL = new Inset("item-thumbnail");
	}

}
