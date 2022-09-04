package net.javenture.framework.quasar.element;


import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;


/*
	2019/07/04
 */
final public class QuasarList
	extends AHtmlContent<QuasarList>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "q-list");


	//
	public QuasarList()
	{
	}


	public QuasarList(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	public QuasarList dense()
	{
		return attribute(Attributes.DENSE);
	}


	public QuasarList dense(boolean condition)
	{
		return attribute(condition, Attributes.DENSE);
	}


	public QuasarList separator()
	{
		return attribute(Attributes.SEPARATOR);
	}


	public QuasarList separator(boolean condition)
	{
		return attribute(condition, Attributes.SEPARATOR);
	}


	public QuasarList dark()
	{
		return attribute(Attributes.DARK);
	}


	public QuasarList dark(boolean condition)
	{
		return attribute(condition, Attributes.DARK);
	}


	public QuasarList bordered()
	{
		return attribute(Attributes.BORDERED);
	}


	public QuasarList bordered(boolean condition)
	{
		return attribute(condition, Attributes.BORDERED);
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template DENSE = new HtmlAttribute.Template("dense");
		final public static HtmlAttribute.Template SEPARATOR = new HtmlAttribute.Template("separator");
		final public static HtmlAttribute.Template DARK = new HtmlAttribute.Template("dark");
		final public static HtmlAttribute.Template BORDERED = new HtmlAttribute.Template("bordered");
	}

}
