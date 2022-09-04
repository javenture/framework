package net.javenture.framework.quasar.element;


import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;


/*
	2019/07/04
 */
final public class QuasarResizeObserver
	extends AHtmlElement<QuasarResizeObserver>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "q-resize-observer");


	//
	public QuasarResizeObserver()
	{
	}


	public QuasarResizeObserver(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	public QuasarResizeObserver debounce(int value)
	{
		return attribute(Attributes.DEBOUNCE, value);
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template DEBOUNCE = new HtmlAttribute.Template("debounce");
	}

}
