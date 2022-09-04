package net.javenture.framework.quasar.element;


import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;


/*
	2019/07/04
 */
final public class QuasarCard
	extends AHtmlContent<QuasarCard>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "q-card");


	//
	public QuasarCard()
	{
	}


	public QuasarCard(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	public QuasarCard dark()
	{
		return attribute(Attributes.DARK);
	}


	public QuasarCard dark(boolean condition)
	{
		return attribute(condition, Attributes.DARK);
	}


	public QuasarCard square()
	{
		return attribute(Attributes.SQUARE);
	}


	public QuasarCard square(boolean condition)
	{
		return attribute(condition, Attributes.SQUARE);
	}


	public QuasarCard flat()
	{
		return attribute(Attributes.FLAT);
	}


	public QuasarCard flat(boolean condition)
	{
		return attribute(condition, Attributes.FLAT);
	}


	public QuasarCard bordered()
	{
		return attribute(Attributes.BORDERED);
	}


	public QuasarCard bordered(boolean condition)
	{
		return attribute(condition, Attributes.BORDERED);
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template DARK = new HtmlAttribute.Template("dark");
		final public static HtmlAttribute.Template SQUARE = new HtmlAttribute.Template("square");
		final public static HtmlAttribute.Template FLAT = new HtmlAttribute.Template("flat");
		final public static HtmlAttribute.Template BORDERED = new HtmlAttribute.Template("bordered");
	}

}
