package net.javenture.framework.quasar;


import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;


/*
	2019/07/04
 */
final public class QuasarItemSection
	extends AHtmlContent<QuasarItemSection>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "q-item-section");


	//
	public QuasarItemSection()
	{
	}


	public QuasarItemSection(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	public QuasarItemSection avatar()
	{
		return attribute(Attributes.AVATAR);
	}


	public QuasarItemSection avatar(boolean condition)
	{
		return attribute(condition, Attributes.AVATAR);
	}


	public QuasarItemSection thumbnail()
	{
		return attribute(Attributes.THUMBNAIL);
	}


	public QuasarItemSection thumbnail(boolean condition)
	{
		return attribute(condition, Attributes.THUMBNAIL);
	}


	public QuasarItemSection side()
	{
		return attribute(Attributes.SIDE);
	}


	public QuasarItemSection side(boolean condition)
	{
		return attribute(condition, Attributes.SIDE);
	}


	public QuasarItemSection top()
	{
		return attribute(Attributes.TOP);
	}


	public QuasarItemSection top(boolean condition)
	{
		return attribute(condition, Attributes.TOP);
	}


	public QuasarItemSection noWrap()
	{
		return attribute(Attributes.NO_WRAP);
	}


	public QuasarItemSection noWrap(boolean condition)
	{
		return attribute(condition, Attributes.NO_WRAP);
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template AVATAR = new HtmlAttribute.Template("avatar");
		final public static HtmlAttribute.Template THUMBNAIL = new HtmlAttribute.Template("thumbnail");
		final public static HtmlAttribute.Template SIDE = new HtmlAttribute.Template("side");
		final public static HtmlAttribute.Template TOP = new HtmlAttribute.Template("top");
		final public static HtmlAttribute.Template NO_WRAP = new HtmlAttribute.Template("no-wrap");
	}

}
