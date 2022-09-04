package net.javenture.framework.quasar.element;


import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;


/*
	2019/07/04
 */
final public class QuasarCardActions
	extends AHtmlContent<QuasarCardActions>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "q-card-actions");


	//
	public QuasarCardActions()
	{
	}


	public QuasarCardActions(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	public QuasarCardActions vertical()
	{
		return attribute(Attributes.VERTICAL);
	}


	public QuasarCardActions vertical(boolean condition)
	{
		return attribute(condition, Attributes.VERTICAL);
	}


	public QuasarCardActions align(Align value)
	{
		return attribute(value);
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template VERTICAL = new HtmlAttribute.Template("vertical");
		final public static HtmlAttribute.Template ALIGN = new HtmlAttribute.Template("align");
	}


	final public static class Align
		extends HtmlAttribute.Preset
	{
		//
		private Align(String s)
		{
			super(Attributes.ALIGN, s);
		}

		//
		final public static Align LEFT = new Align("left");
		final public static Align CENTER = new Align("center");
		final public static Align RIGHT = new Align("right");
		final public static Align BETWEEN = new Align("between");
		final public static Align AROUND = new Align("around");
	}

}
