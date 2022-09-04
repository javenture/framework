package net.javenture.framework.quasar.element;


import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;
import net.javenture.framework.icon.IIcon;
import net.javenture.framework.quasar.AQuasarElement;
import net.javenture.framework.quasar.IQuasarColor;
import net.javenture.framework.quasar.QuasarTheme;


/*
	2019/07/03
 */
final public class QuasarTab
	extends AQuasarElement<QuasarTab>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "q-tab");


	//
	public QuasarTab()
	{
	}


	public QuasarTab(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	@Override
	public void theme(QuasarTheme value)
	{
		// XXX


		throw new UnsupportedOperationException();
	}


	public QuasarTab icon(IIcon value)
	{
		return attribute(Attributes.ICON, value);
	}


	public QuasarTab icon(String value)
	{
		return attribute(Attributes.ICON, value);
	}


	public QuasarTab icon(boolean condition, IIcon value)
	{
		return attribute(condition, Attributes.ICON, value);
	}


	public QuasarTab icon(boolean condition, String value)
	{
		return attribute(condition, Attributes.ICON, value);
	}


	public QuasarTab label(String value)
	{
		return attribute(Attributes.LABEL, value);
	}


	public QuasarTab label(Number value)
	{
		return attribute(Attributes.LABEL, value);
	}


	public QuasarTab alert()
	{
		return attribute(Attributes.ALERT);
	}


	public QuasarTab alert(boolean condition)
	{
		return attribute(condition, Attributes.ALERT);
	}


	public QuasarTab alert(IQuasarColor value)
	{
		return attribute(Attributes.ALERT, value);
	}


	public QuasarTab alert(String value)
	{
		return attribute(Attributes.ALERT, value);
	}


	public QuasarTab name(String value)
	{
		return attribute(Attributes.NAME, value);
	}


	public QuasarTab name(Number value)
	{
		return attribute(Attributes.NAME, value);
	}


	public QuasarTab name(Number value, boolean reactive)                                                                        // XXX: ?
	{
		return reactive ? vAttribute(Attributes.NAME, value) : attribute(Attributes.NAME, value);
	}


	public QuasarTab tabindex(int value)
	{
		return attribute(Attributes.TABINDEX, value);
	}


	public QuasarTab tabindex(String value)
	{
		return attribute(Attributes.TABINDEX, value);
	}


	public QuasarTab noCaps()
	{
		return attribute(Attributes.NO_CAPS);
	}


	public QuasarTab noCaps(boolean condition)
	{
		return attribute(condition, Attributes.NO_CAPS);
	}


	public QuasarTab disable()
	{
		return attribute(Attributes.DISABLE);
	}


	public QuasarTab disable(boolean condition)
	{
		return attribute(condition, Attributes.DISABLE);
	}


	public QuasarTab ripple()
	{
		return attribute(Attributes.RIPPLE);
	}


	public QuasarTab ripple(boolean condition)
	{
		return attribute(condition, Attributes.RIPPLE);
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template ALERT = new HtmlAttribute.Template("alert");
		final public static HtmlAttribute.Template NO_CAPS = new HtmlAttribute.Template("no-caps");
		final public static HtmlAttribute.Template DISABLE = new HtmlAttribute.Template("disable");
		final public static HtmlAttribute.Template RIPPLE = new HtmlAttribute.Template("ripple");
		final public static HtmlAttribute.Template ICON = new HtmlAttribute.Template("icon");
		final public static HtmlAttribute.Template LABEL = new HtmlAttribute.Template("label");
		final public static HtmlAttribute.Template NAME = new HtmlAttribute.Template("name");
		final public static HtmlAttribute.Template TABINDEX = new HtmlAttribute.Template("tabindex");
	}

}
