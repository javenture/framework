package net.javenture.framework.quasar.element;


import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;
import net.javenture.framework.quasar.IQuasarColor;
import net.javenture.framework.script.IntScriptArray;
import net.javenture.framework.vue.VueEvent;


/*
	2019/07/20
 */
final public class QuasarTime
	extends AHtmlContent<QuasarTime>
{
	//
	final public static Config CONFIG = new Config(AHtmlElement.Type.PAIR, "q-time");


	//
	public QuasarTime()
	{
	}


	public QuasarTime(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	public QuasarTime color(IQuasarColor value)
	{
		return attribute(IQuasarColor.HtmlAttributes.TEMPLATE_COLOR, value);
	}


	public QuasarTime color(String value)
	{
		return attribute(IQuasarColor.HtmlAttributes.TEMPLATE_COLOR, value);
	}


	public QuasarTime textColor(IQuasarColor value)
	{
		return attribute(IQuasarColor.HtmlAttributes.TEMPLATE_TEXT_COLOR, value);
	}


	public QuasarTime textColor(String value)
	{
		return attribute(IQuasarColor.HtmlAttributes.TEMPLATE_TEXT_COLOR, value);
	}


	public QuasarTime dark()
	{
		return attribute(Attributes.DARK);
	}


	public QuasarTime dark(boolean condition)
	{
		return attribute(condition, Attributes.DARK);
	}


	public QuasarTime landscape()
	{
		return super.attribute(Attributes.LANDSCAPE);
	}


	public QuasarTime landscape(boolean condition)
	{
		return super.attribute(condition, Attributes.LANDSCAPE);
	}


	public QuasarTime format24h()
	{
		return super.attribute(Attributes.FORMAT24H);
	}


	public QuasarTime format24h(boolean condition)
	{
		return super.attribute(condition, Attributes.FORMAT24H);
	}


	public QuasarTime withSeconds()
	{
		return super.attribute(Attributes.WITH_SECONDS);
	}


	public QuasarTime withSeconds(boolean condition)
	{
		return super.attribute(condition, Attributes.WITH_SECONDS);
	}


	public QuasarTime mask(String value)
	{
		return super.attribute(Attributes.MASK, value);
	}


	public QuasarTime hourOptions(int... values)
	{
		IntScriptArray array = new IntScriptArray()
			.add(values);

		return super.attribute(Attributes.HOUR_OPTIONS, array);
	}


	public QuasarTime minuteOptions(int... values)
	{
		IntScriptArray array = new IntScriptArray()
			.add(values);

		return super.attribute(Attributes.MINUTE_OPTIONS, array);
	}


	public QuasarTime secondOptions(int... values)
	{
		IntScriptArray array = new IntScriptArray()
			.add(values);

		return super.attribute(Attributes.SECOND_OPTIONS, array);
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template DARK = new HtmlAttribute.Template("dark");
		final public static HtmlAttribute.Template LANDSCAPE = new HtmlAttribute.Template("landscape");
		final public static HtmlAttribute.Template FORMAT24H = new HtmlAttribute.Template("format24h");
		final public static HtmlAttribute.Template WITH_SECONDS = new HtmlAttribute.Template("with-seconds");
		final public static HtmlAttribute.Template MASK = new HtmlAttribute.Template("mask");
		final public static HtmlAttribute.Template HOUR_OPTIONS = new HtmlAttribute.Template("hour-options", true);
		final public static HtmlAttribute.Template MINUTE_OPTIONS = new HtmlAttribute.Template("minute-options", true);
		final public static HtmlAttribute.Template SECOND_OPTIONS = new HtmlAttribute.Template("second-options", true);
	}


	final public static class Events
	{
		final public static VueEvent INPUT = new VueEvent("input");
	}

}
