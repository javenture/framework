package net.javenture.framework.quasar.element;


import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;


/*
	2019/07/04
 */
public class QuasarItem
	extends AHtmlContent<QuasarItem>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "q-item");


	//
	public QuasarItem()
	{
	}


	public QuasarItem(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	public QuasarItem dense()
	{
		return attribute(Attributes.DENSE);
	}


	public QuasarItem dense(boolean condition)
	{
		return attribute(condition, Attributes.DENSE);
	}


	public QuasarItem dark()
	{
		return attribute(Attributes.DARK);
	}


	public QuasarItem dark(boolean condition)
	{
		return attribute(condition, Attributes.DARK);
	}


	public QuasarItem tag(AHtmlElement.Config config)
	{
		return attribute(Attributes.TAG, config.tag());
	}


	public QuasarItem tag(String value)
	{
		return attribute(Attributes.TAG, value);
	}


	public QuasarItem to(String value)
	{
		return attribute(Attributes.TO, value);
	}


	public QuasarItem exact()
	{
		return attribute(Attributes.EXACT);
	}


	public QuasarItem exact(boolean condition)
	{
		return attribute(condition, Attributes.EXACT);
	}


	public QuasarItem append()
	{
		return attribute(Attributes.APPEND);
	}


	public QuasarItem append(boolean condition)
	{
		return attribute(condition, Attributes.APPEND);
	}


	public QuasarItem replace()
	{
		return attribute(Attributes.REPLACE);
	}


	public QuasarItem replace(boolean condition)
	{
		return attribute(condition, Attributes.REPLACE);
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template DENSE = new HtmlAttribute.Template("dense");
		final public static HtmlAttribute.Template DARK = new HtmlAttribute.Template("dark");
		final public static HtmlAttribute.Template EXACT = new HtmlAttribute.Template("exact");
		final public static HtmlAttribute.Template APPEND = new HtmlAttribute.Template("append");
		final public static HtmlAttribute.Template REPLACE = new HtmlAttribute.Template("replace");
		final public static HtmlAttribute.Template TAG = new HtmlAttribute.Template("tag");
		final public static HtmlAttribute.Template TO = new HtmlAttribute.Template("to");
	}

}
