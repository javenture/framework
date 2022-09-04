package net.javenture.framework.quasar.element;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;
import net.javenture.framework.quasar.IQuasarColor;


/*
	2019/07/04
 */
final public class QuasarBadge
	extends AHtmlContent<QuasarBadge>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "q-badge");


	//
	public QuasarBadge()
	{
	}


	public QuasarBadge(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	public QuasarBadge color(IQuasarColor value)
	{
		return attribute(IQuasarColor.HtmlAttributes.TEMPLATE_COLOR, value);
	}


	public QuasarBadge color(String value)
	{
		return attribute(IQuasarColor.HtmlAttributes.TEMPLATE_COLOR, value);
	}


	public QuasarBadge textColor(IQuasarColor value)
	{
		return attribute(IQuasarColor.HtmlAttributes.TEMPLATE_TEXT_COLOR, value);
	}


	public QuasarBadge textColor(String value)
	{
		return attribute(IQuasarColor.HtmlAttributes.TEMPLATE_TEXT_COLOR, value);
	}


	public QuasarBadge floating()
	{
		return attribute(Attributes.FLOATING);
	}


	public QuasarBadge floating(boolean condition)
	{
		return attribute(condition, Attributes.FLOATING);
	}


	public QuasarBadge transparent()
	{
		return attribute(Attributes.TRANSPARENT);
	}


	public QuasarBadge transparent(boolean condition)
	{
		return attribute(condition, Attributes.TRANSPARENT);
	}


	public QuasarBadge multiLine()
	{
		return attribute(Attributes.MULTI_LINE);
	}


	public QuasarBadge multiLine(boolean condition)
	{
		return attribute(condition, Attributes.MULTI_LINE);
	}


	public QuasarBadge label(@NullAllow String value)
	{
		return attribute(Attributes.LABEL, value);
	}


	public QuasarBadge label(@NullAllow Number value)
	{
		return attribute(Attributes.LABEL, value);
	}


	public QuasarBadge align(Align value)
	{
		return attribute(value);
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template FLOATING = new HtmlAttribute.Template("floating");
		final public static HtmlAttribute.Template TRANSPARENT = new HtmlAttribute.Template("transparent");
		final public static HtmlAttribute.Template MULTI_LINE = new HtmlAttribute.Template("multi-line");
		final public static HtmlAttribute.Template LABEL = new HtmlAttribute.Template("label");
		final public static HtmlAttribute.Template ALIGN = new HtmlAttribute.Template("align");
	}


	final public static class Align
		extends HtmlAttribute.Preset
	{
		//
		private Align(String s)
		{
			super(Attributes.ALIGN, s);
		}

		//
		final public static Align TOP = new Align("top");
		final public static Align MIDDLE = new Align("middle");
		final public static Align BOTTOM = new Align("bottom");
	}

}


