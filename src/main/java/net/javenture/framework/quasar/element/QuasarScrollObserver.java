package net.javenture.framework.quasar.element;


import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;


/*
	2019/07/04
 */
final public class QuasarScrollObserver
	extends AHtmlContent<QuasarScrollObserver>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "q-scroll-observer");


	//
	public QuasarScrollObserver()
	{
	}


	public QuasarScrollObserver(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	public QuasarScrollObserver debounce(int value)
	{
		return attribute(Attributes.DEBOUNCE, value);
	}


	public QuasarScrollObserver horizontal()
	{
		return attribute(Attributes.HORIZONTAL);
	}


	public QuasarScrollObserver horizontal(boolean condition)
	{
		return attribute(condition, Attributes.HORIZONTAL);
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template HORIZONTAL = new HtmlAttribute.Template("horizontal");
		final public static HtmlAttribute.Template DEBOUNCE = new HtmlAttribute.Template("debounce");
	}

}
