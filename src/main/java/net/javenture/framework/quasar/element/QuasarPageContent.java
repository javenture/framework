package net.javenture.framework.quasar.element;


import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;


/*
	2019/05/21
 */
final public class QuasarPageContent
	extends AHtmlContent<QuasarPageContent>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "q-page");


	//
	public QuasarPageContent()
	{
	}


	public QuasarPageContent(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	public QuasarPageContent padding()
	{
		attribute(Attributes.PADDING);

		return this;
	}


	public QuasarPageContent padding(boolean condition)
	{
		if (condition) attribute(Attributes.PADDING);

		return this;
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template PADDING = new HtmlAttribute.Template("padding");
	}

}
