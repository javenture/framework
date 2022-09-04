package net.javenture.framework.html.element;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;
import net.javenture.framework.html.HtmlMarkup;
import net.javenture.framework.html.HtmlText;
import net.javenture.framework.utf8.IUtf8StreamableEntry;


/*
	2019/07/05
 */
final public class Script
	extends AHtmlElement<Script> // !
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "script");


	//
	public Script()
	{
	}


	public Script(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	public Script src(String s)
	{
		return super.attribute(Attributes.SRC, s);
	}


	public Script content(@NullDisallow IUtf8StreamableEntry entry)
	{
		return super.child(new HtmlText(entry));
	}


	public Script content(CharSequence text)
	{
		return super.child(new HtmlMarkup(text));
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template SRC = new HtmlAttribute.Template("src");
	}

}
