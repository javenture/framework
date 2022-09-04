package net.javenture.framework.quasar;


import net.javenture.framework.html.HtmlAttribute;
import net.javenture.framework.utf8.IUtf8StreamableEntry;


/*
	2018/03/03
 */
public interface IQuasarColor
	extends IUtf8StreamableEntry
{
	//
	final class HtmlAttributes
	{
		final public static HtmlAttribute.Template TEMPLATE_COLOR = new HtmlAttribute.Template("color");
		final public static HtmlAttribute.Template TEMPLATE_TEXT_COLOR = new HtmlAttribute.Template("text-color");
	}

}
