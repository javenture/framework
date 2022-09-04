package net.javenture.framework.html.element;


import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;
import net.javenture.framework.netty.NettyUrl;


/*
	2019/07/05
 */
final public class Img
	extends AHtmlElement<Img>
{
	//
	final public static Config CONFIG = new Config(Type.SINGLE, "img");


	//
	public Img()
	{
	}


	public Img(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	public Img src(String s)
	{
		return attribute(Attributes.SRC, s);
	}


	public Img src(NettyUrl url)
	{
		return attribute(Attributes.SRC, url);
	}


	public Img width(int i)
	{
		return attribute(Attributes.WIDTH, i);
	}


	public Img height(int i)
	{
		return attribute(Attributes.HEIGHT, i);
	}


	public Img title(String s)
	{
		return attribute(Attributes.TITLE, s);
	}


	public Img alt(String s)
	{
		return attribute(Attributes.ALT, s);
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template SRC = new HtmlAttribute.Template("src");
		final public static HtmlAttribute.Template WIDTH = new HtmlAttribute.Template("width");
		final public static HtmlAttribute.Template HEIGHT = new HtmlAttribute.Template("height");
		final public static HtmlAttribute.Template TITLE = new HtmlAttribute.Template("title");
		final public static HtmlAttribute.Template ALT = new HtmlAttribute.Template("alt");
	}

}
