package net.javenture.framework.quasar.element;


import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;


/*
	2019/07/04
 */
final public class QuasarHeader
	extends AHtmlContent<QuasarHeader>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "q-header");


	//
	public QuasarHeader()
	{
	}


	public QuasarHeader(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	public QuasarHeader reveal()
	{
		return attribute(Attributes.REVEAL);
	}


	public QuasarHeader reveal(boolean condition)
	{
		return attribute(condition, Attributes.REVEAL);
	}


	public QuasarHeader revealOffset(int value)
	{
		return attribute(Attributes.REVEAL_OFFSET, value);
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template REVEAL = new HtmlAttribute.Template("reveal");
		final public static HtmlAttribute.Template REVEAL_OFFSET = new HtmlAttribute.Template("reveal-offset");                     // XXX: vue ?
	}

}
