package net.javenture.framework.quasar.element;


import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;


/*
	2019/07/04
 */
final public class QuasarTooltip
	extends AHtmlContent<QuasarTooltip>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "q-tooltip");


	//
	public QuasarTooltip()
	{
	}


	public QuasarTooltip(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	//
	public QuasarTooltip anchor(Anchor value)
	{
		return attribute(value);
	}


	public QuasarTooltip self(Self value)
	{
		return attribute(value);
	}


	public QuasarTooltip offset(int horizontal, int vertical)
	{
		return vAttribute(Attributes.OFFSET, "[" + horizontal + ", " + vertical + "]");
	}


	public QuasarTooltip maxHeight(int value)
	{
		return attribute(Attributes.MAX_HEIGHT, "" + value + "px");
	}


	public QuasarTooltip disable()
	{
		return attribute(Attributes.DISABLE);
	}


	public QuasarTooltip disable(boolean condition)
	{
		return attribute(condition, Attributes.DISABLE);
	}


	public QuasarTooltip delay(int millisecond)
	{
		return attribute(Attributes.DELAY, millisecond);
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template DISABLE = new HtmlAttribute.Template("disable");
		final public static HtmlAttribute.Template MAX_HEIGHT = new HtmlAttribute.Template("max-height");
		final public static HtmlAttribute.Template DELAY = new HtmlAttribute.Template("delay");
		final public static HtmlAttribute.Template OFFSET = new HtmlAttribute.Template("offset");                                     // XXX: vue
		final public static HtmlAttribute.Template SELF = new HtmlAttribute.Template("self");
		final public static HtmlAttribute.Template ANCHOR = new HtmlAttribute.Template("anchor");
	}


	final public static class Anchor
		extends HtmlAttribute.Preset
	{
		//
		private Anchor(String s)
		{
			super(Attributes.ANCHOR, s);
		}

		//
		final public static Anchor TOP_LEFT = new Anchor("top left");
		final public static Anchor TOP_MIDDLE = new Anchor("top middle");
		final public static Anchor TOP_RIGHT = new Anchor("top right");
		final public static Anchor CENTER_LEFT = new Anchor("center left");
		final public static Anchor CENTER_MIDDLE = new Anchor("center middle");
		final public static Anchor CENTER_RIGHT = new Anchor("center right");
		final public static Anchor BOTTOM_LEFT = new Anchor("bottom left");
		final public static Anchor BOTTOM_MIDDLE = new Anchor("bottom middle");
		final public static Anchor BOTTOM_RIGHT = new Anchor("bottom right");
	}


	final public static class Self
		extends HtmlAttribute.Preset
	{
		//
		private Self(String s)
		{
			super(Attributes.SELF, s);
		}

		//
		final public static Self TOP_LEFT = new Self("top left");
		final public static Self TOP_MIDDLE = new Self("top middle");
		final public static Self TOP_RIGHT = new Self("top right");
		final public static Self CENTER_LEFT = new Self("center left");
		final public static Self CENTER_MIDDLE = new Self("center middle");
		final public static Self CENTER_RIGHT = new Self("center right");
		final public static Self BOTTOM_LEFT = new Self("bottom left");
		final public static Self BOTTOM_MIDDLE = new Self("bottom middle");
		final public static Self BOTTOM_RIGHT = new Self("bottom right");
	}

}
