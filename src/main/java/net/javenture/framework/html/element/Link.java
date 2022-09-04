package net.javenture.framework.html.element;


import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;
import net.javenture.framework.netty.NettyUrl;


/*
	2019/08/05
 */
final public class Link
	extends AHtmlElement<Link>
{
	//
	final public static Config CONFIG = new Config(AHtmlElement.Type.SINGLE, "link");


	//
	public Link()
	{
	}


	public Link(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	public Link rel(Rel r)
	{
		return attribute(r);
	}


	public Link type(Type t)
	{
		return attribute(t);
	}


	public Link href(String s)
	{
		return attribute(Attributes.HREF, s);
	}


	public Link href(NettyUrl url)
	{
		return attribute(Attributes.HREF, url);
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template HREF = new HtmlAttribute.Template("href");
		final public static HtmlAttribute.Template REL = new HtmlAttribute.Template("rel");
		final public static HtmlAttribute.Template TYPE = new HtmlAttribute.Template("type");
	}


	final public static class Rel
		extends HtmlAttribute.Preset
	{
		//
		private Rel(String s)
		{
			super(Attributes.REL, s);
		}

		//
		final public static Rel IMPORT = new Rel("import");
		final public static Rel MANIFEST = new Rel("manifest");
		final public static Rel STYLESHEET = new Rel("stylesheet");
	}


	final public static class Type
		extends HtmlAttribute.Preset
	{
		//
		private Type(String s)
		{
			super(Attributes.TYPE, s);
		}

		//
		final public static Type TEXT_CSS = new Type("text/css");
	}

}
