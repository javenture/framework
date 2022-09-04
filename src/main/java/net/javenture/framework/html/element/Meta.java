package net.javenture.framework.html.element;


import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;
import net.javenture.framework.html.HtmlPreset;


/*
	2019/07/04
 */
final public class Meta
	extends AHtmlElement<Meta>
{
	//
	final public static Config CONFIG = new Config(Type.SINGLE, "meta");


	//
	final public static HtmlPreset CHARSET_UTF8 = new HtmlPreset
	(
		new Meta()
			.charset(Charset.UTF8)
	);


	//
	public Meta()
	{
	}


	public Meta(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	public Meta charset(Charset c)
	{
		return attribute(c);
	}


	public Meta name(String s)
	{
		return attribute(Attributes.NAME, s);
	}


	public Meta content(String s)
	{
		return attribute(Attributes.CONTENT, s);
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template NAME = new HtmlAttribute.Template("name");
		final public static HtmlAttribute.Template CONTENT = new HtmlAttribute.Template("content");
		final public static HtmlAttribute.Template CHARSET = new HtmlAttribute.Template("charset");
	}


	final public static class Charset
		extends HtmlAttribute.Preset
	{
		//
		private Charset(String s)
		{
			super(Attributes.CHARSET, s);
		}

		//
		final public static Charset UTF8 = new Charset("utf-8");
	}

}
