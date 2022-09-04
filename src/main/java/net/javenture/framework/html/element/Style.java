package net.javenture.framework.html.element;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlMarkup;
import net.javenture.framework.html.HtmlText;
import net.javenture.framework.utf8.IUtf8StreamableEntry;


/*
	2019/07/04
 */
final public class Style
	extends AHtmlElement<Style> // !
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "style");


	//
	public Style()
	{
	}


	public Style(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	public Style content(@NullDisallow IUtf8StreamableEntry entry)
	{
		return super.child(new HtmlText(entry));
	}


	public Style content(CharSequence text)
	{
		return super.child(new HtmlMarkup(text));
	}

}
