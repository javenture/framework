package net.javenture.framework.quasar.element;


import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;


/*
	2019/07/04
 */
final public class QuasarFooter
	extends AHtmlContent<QuasarFooter>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "q-footer");


	//
	public QuasarFooter()
	{
	}


	public QuasarFooter(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	public QuasarFooter reveal()
	{
		return attribute(Attributes.REVEAL);
	}


	public QuasarFooter reveal(boolean condition)
	{
		return attribute(condition, Attributes.REVEAL);
	}


	public QuasarFooter revealOffset(int value)
	{
		return attribute(Attributes.REVEAL_OFFSET, value);
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template REVEAL = new HtmlAttribute.Template("reveal");
		final public static HtmlAttribute.Template REVEAL_OFFSET = new HtmlAttribute.Template("reveal-offset");                         // XXX: vue ?
	}

}
