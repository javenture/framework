package net.javenture.framework.html.element;


import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;
import net.javenture.framework.netty.NettyUrl;
import net.javenture.framework.model.AModel;
import net.javenture.framework.vue.AVueUrl;


/*
	2019/07/05
 */
final public class A
	extends AHtmlContent<A>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "a");


	//
	public A()
	{
	}


	public A(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	public A href(NettyUrl url)
	{
		return super.attribute(Attributes.HREF, url);
	}


	public A href(AVueUrl url)
	{
		return super.attribute(Attributes.HREF, url);
	}


	public A href(String value)
	{
		return super.attribute(Attributes.HREF, value);
	}


	public A href(AModel value)
	{
		return super.vAttribute(Attributes.HREF, value);
	}


	public A title(String value)
	{
		return super.attribute(Attributes.TITLE, value);
	}


	public A tabindex(int value)
	{
		return super.attribute(Attributes.TABINDEX, value);
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template HREF = new HtmlAttribute.Template("href");
		final public static HtmlAttribute.Template TITLE = new HtmlAttribute.Template("title");
		final public static HtmlAttribute.Template TABINDEX = new HtmlAttribute.Template("tabindex");
	}

}
