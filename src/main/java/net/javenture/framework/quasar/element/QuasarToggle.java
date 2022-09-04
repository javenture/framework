package net.javenture.framework.quasar.element;


import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;
import net.javenture.framework.icon.IIcon;
import net.javenture.framework.quasar.IQuasarColor;


/*
	2019/07/05
 */
final public class QuasarToggle
	extends AHtmlContent<QuasarToggle>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "q-toggle");


	//
	public QuasarToggle()
	{
	}


	public QuasarToggle(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	public QuasarToggle val(String s)                                       // XXX: String, Number ?
	{
		return attribute(Attributes.VAL, s);
	}


	public QuasarToggle trueValue(String s)                                       // XXX: String, Number ?
	{
		return attribute(Attributes.TRUE_VALUE, s);
	}


	public QuasarToggle falseValue(String s)                                       // XXX: String, Number ?
	{
		return attribute(Attributes.FALSE_VALUE, s);
	}


	public QuasarToggle label(String s)
	{
		return attribute(Attributes.LABEL, s);
	}


	public QuasarToggle leftLabel()
	{
		return attribute(Attributes.LEFT_LABEL);
	}


	public QuasarToggle leftLabel(boolean condition)
	{
		return attribute(condition, Attributes.LEFT_LABEL);
	}


	public QuasarToggle icon(IIcon value)
	{
		return attribute(Attributes.ICON, value);
	}


	public QuasarToggle icon(String value)
	{
		return attribute(Attributes.ICON, value);
	}


	public QuasarToggle icon(boolean condition, IIcon value)
	{
		return attribute(condition, Attributes.ICON, value);
	}


	public QuasarToggle icon(boolean condition, String value)
	{
		return attribute(condition, Attributes.ICON, value);
	}


	public QuasarToggle checkedIcon(IIcon value)
	{
		return attribute(Attributes.CHECKED_ICON, value);
	}


	public QuasarToggle checkedIcon(String value)
	{
		return attribute(Attributes.CHECKED_ICON, value);
	}


	public QuasarToggle checkedIcon(boolean condition, IIcon value)
	{
		return attribute(condition, Attributes.CHECKED_ICON, value);
	}


	public QuasarToggle checkedIcon(boolean condition, String value)
	{
		return attribute(condition, Attributes.CHECKED_ICON, value);
	}


	public QuasarToggle uncheckedIcon(IIcon value)
	{
		return attribute(Attributes.UNCHECKED_ICON, value);
	}


	public QuasarToggle uncheckedIcon(String value)
	{
		return attribute(Attributes.UNCHECKED_ICON, value);
	}


	public QuasarToggle uncheckedIcon(boolean condition, IIcon value)
	{
		return attribute(condition, Attributes.UNCHECKED_ICON, value);
	}


	public QuasarToggle uncheckedIcon(boolean condition, String value)
	{
		return attribute(condition, Attributes.UNCHECKED_ICON, value);
	}


	public QuasarToggle color(IQuasarColor value)
	{
		return attribute(IQuasarColor.HtmlAttributes.TEMPLATE_COLOR, value);
	}


	public QuasarToggle color(String value)
	{
		return attribute(IQuasarColor.HtmlAttributes.TEMPLATE_COLOR, value);
	}


	public QuasarToggle keepColor()
	{
		return attribute(Attributes.KEEP_COLOR);
	}


	public QuasarToggle keepColor(boolean condition)
	{
		return attribute(condition, Attributes.KEEP_COLOR);
	}


	public QuasarToggle readonly()
	{
		return attribute(Attributes.READONLY);
	}


	public QuasarToggle readonly(boolean condition)
	{
		return attribute(condition, Attributes.READONLY);
	}


	public QuasarToggle disable()
	{
		return attribute(Attributes.DISABLE);
	}


	public QuasarToggle disable(boolean condition)
	{
		return attribute(condition, Attributes.DISABLE);
	}


	public QuasarToggle dark()
	{
		return attribute(Attributes.DARK);
	}


	public QuasarToggle dark(boolean condition)
	{
		return attribute(condition, Attributes.DARK);
	}


	public QuasarToggle noFocus()
	{
		return attribute(Attributes.NO_FOCUS);
	}


	public QuasarToggle noFocus(boolean condition)
	{
		return attribute(condition, Attributes.NO_FOCUS);
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template LEFT_LABEL = new HtmlAttribute.Template("left-label");
		final public static HtmlAttribute.Template KEEP_COLOR = new HtmlAttribute.Template("keep-color");
		final public static HtmlAttribute.Template READONLY = new HtmlAttribute.Template("readonly");
		final public static HtmlAttribute.Template DISABLE = new HtmlAttribute.Template("disable");
		final public static HtmlAttribute.Template DARK = new HtmlAttribute.Template("dark");
		final public static HtmlAttribute.Template NO_FOCUS = new HtmlAttribute.Template("no-focus");
		final public static HtmlAttribute.Template VAL = new HtmlAttribute.Template("val");
		final public static HtmlAttribute.Template TRUE_VALUE = new HtmlAttribute.Template("true-value");
		final public static HtmlAttribute.Template FALSE_VALUE = new HtmlAttribute.Template("false-value");
		final public static HtmlAttribute.Template LABEL = new HtmlAttribute.Template("label");
		final public static HtmlAttribute.Template ICON = new HtmlAttribute.Template("icon");
		final public static HtmlAttribute.Template CHECKED_ICON = new HtmlAttribute.Template("checked-icon");
		final public static HtmlAttribute.Template UNCHECKED_ICON = new HtmlAttribute.Template("unchecked-icon");
	}

}
