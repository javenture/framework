package net.javenture.framework.quasar.element;


import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;


/*
	2019/07/04
 */
final public class QuasarScrollArea
	extends AHtmlContent<QuasarScrollArea>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "q-scroll-area");


	//
	public QuasarScrollArea()
	{
	}


	public QuasarScrollArea(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	public QuasarScrollArea delay(int value)
	{
		return vAttribute(Attributes.DELAY, value);
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template DELAY = new HtmlAttribute.Template("delay");
	}

}
