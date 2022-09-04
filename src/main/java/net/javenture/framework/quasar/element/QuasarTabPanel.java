package net.javenture.framework.quasar.element;


import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;
import net.javenture.framework.quasar.AQuasarElement;
import net.javenture.framework.quasar.QuasarTheme;


/*
	2019/07/05
 */
final public class QuasarTabPanel
	extends AQuasarElement<QuasarTabPanel>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "q-tab-panel");


	//
	public QuasarTabPanel()
	{
	}


	public QuasarTabPanel(AHtmlElement parent)
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


	public QuasarTabPanel name(String value)
	{
		return attribute(Attributes.NAME, value);
	}


	public QuasarTabPanel name(Number value)
	{
		return attribute(Attributes.NAME, value);
	}


	public QuasarTabPanel name(Number value, boolean reactive)                                                                  // XXX: ?
	{
		return reactive ? vAttribute(Attributes.NAME, value) : attribute(Attributes.NAME, value);
	}


	public QuasarTabPanel disable()
	{
		return attribute(Attributes.DISABLE);
	}


	public QuasarTabPanel disable(boolean condition)
	{
		return attribute(condition, Attributes.DISABLE);
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template DISABLE = new HtmlAttribute.Template("disable");
		final public static HtmlAttribute.Template NAME = new HtmlAttribute.Template("name");
	}

}
