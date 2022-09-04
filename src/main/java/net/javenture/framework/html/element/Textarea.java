package net.javenture.framework.html.element;


import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;


/*
	2019/07/05

	https://developer.mozilla.org/en-US/docs/Web/HTML/Element/textarea
 */
final public class Textarea
	extends AHtmlContent<Textarea>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "textarea");


	//
	public Textarea()
	{
	}


	public Textarea(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	public Textarea rows(int value)
	{
		return super.attribute(Attributes.ROWS, value);
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template ROWS = new HtmlAttribute.Template("rows");
	}

}
