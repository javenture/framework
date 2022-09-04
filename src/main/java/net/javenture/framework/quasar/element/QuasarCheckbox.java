package net.javenture.framework.quasar.element;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;
import net.javenture.framework.quasar.IQuasarColor;


/*
	2019/07/04
 */
final public class QuasarCheckbox
	extends AHtmlContent<QuasarCheckbox>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "q-checkbox");


	//
	public QuasarCheckbox()
	{
	}


	public QuasarCheckbox(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	public QuasarCheckbox val(String s)                                       // XXX: String, Number ?
	{
		return attribute(Attributes.VAL, s);
	}


	public QuasarCheckbox trueValue(String s)                                       // XXX: String, Number ?
	{
		return attribute(Attributes.TRUE_VALUE, s);
	}


	public QuasarCheckbox falseValue(String s)                                       // XXX: String, Number ?
	{
		return attribute(Attributes.FALSE_VALUE, s);
	}


	public QuasarCheckbox indeterminateValue(String s)                                       // XXX: String, Number ?
	{
		return attribute(Attributes.INDETERMINATE_VALUE, s);
	}


	public QuasarCheckbox label(@NullAllow String value)
	{
		return attribute(Attributes.LABEL, value);
	}


	public QuasarCheckbox label(@NullAllow Number value)
	{
		return attribute(Attributes.LABEL, value);
	}


	public QuasarCheckbox leftLabel()
	{
		return attribute(Attributes.LEFT_LABEL);
	}


	public QuasarCheckbox leftLabel(boolean condition)
	{
		return attribute(condition, Attributes.LEFT_LABEL);
	}


	public QuasarCheckbox toggleIndeterminate()
	{
		return attribute(Attributes.TOGGLE_INDETERMINATE);
	}


	public QuasarCheckbox toggleIndeterminate(boolean condition)
	{
		return attribute(condition, Attributes.TOGGLE_INDETERMINATE);
	}


	public QuasarCheckbox color(IQuasarColor value)
	{
		return attribute(IQuasarColor.HtmlAttributes.TEMPLATE_COLOR, value);
	}


	public QuasarCheckbox color(String value)
	{
		return attribute(IQuasarColor.HtmlAttributes.TEMPLATE_COLOR, value);
	}


	public QuasarCheckbox keepColor()
	{
		return attribute(Attributes.KEEP_COLOR);
	}


	public QuasarCheckbox keepColor(boolean condition)
	{
		return attribute(condition, Attributes.KEEP_COLOR);
	}


	public QuasarCheckbox disable()
	{
		return attribute(Attributes.DISABLE);
	}


	public QuasarCheckbox disable(boolean condition)
	{
		return attribute(condition, Attributes.DISABLE);
	}


	public QuasarCheckbox dark()
	{
		return attribute(Attributes.DARK);
	}


	public QuasarCheckbox dark(boolean condition)
	{
		return attribute(condition, Attributes.DARK);
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template LEFT_LABEL = new HtmlAttribute.Template("left-label");
		final public static HtmlAttribute.Template TOGGLE_INDETERMINATE = new HtmlAttribute.Template("toggle-indeterminate");
		final public static HtmlAttribute.Template KEEP_COLOR = new HtmlAttribute.Template("keep-color");
		final public static HtmlAttribute.Template DISABLE = new HtmlAttribute.Template("disable");
		final public static HtmlAttribute.Template DARK = new HtmlAttribute.Template("dark");
		final public static HtmlAttribute.Template VAL = new HtmlAttribute.Template("val");
		final public static HtmlAttribute.Template TRUE_VALUE = new HtmlAttribute.Template("true-value");
		final public static HtmlAttribute.Template FALSE_VALUE = new HtmlAttribute.Template("false-value");
		final public static HtmlAttribute.Template INDETERMINATE_VALUE = new HtmlAttribute.Template("indeterminate-value");
		final public static HtmlAttribute.Template LABEL = new HtmlAttribute.Template("label");
	}

}
