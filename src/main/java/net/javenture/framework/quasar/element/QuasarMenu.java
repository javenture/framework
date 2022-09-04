package net.javenture.framework.quasar.element;


import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;


/*
	2019/07/04
 */
final public class QuasarMenu
	extends AHtmlContent<QuasarMenu>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "q-menu");


	//
	public QuasarMenu()
	{
	}


	public QuasarMenu(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	public QuasarMenu anchor(Anchor value)
	{
		attribute(value);

		return this;
	}


	public QuasarMenu self(Self value)
	{
		attribute(value);

		return this;
	}


	public QuasarMenu maxHeight(int value)
	{
		attribute(Attributes.MAX_HEIGHT, "" + value + "px");                                                 // XXX: 0px ?

		return this;
	}


	public QuasarMenu touchPosition()
	{
		attribute(Attributes.TOUCH_POSITION);

		return this;
	}


	public QuasarMenu touchPosition(boolean condition)
	{
		if (condition) attribute(Attributes.TOUCH_POSITION);

		return this;
	}


	public QuasarMenu fit()
	{
		attribute(Attributes.FIT);

		return this;
	}


	public QuasarMenu fit(boolean condition)
	{
		if (condition) attribute(Attributes.FIT);

		return this;
	}


	public QuasarMenu disable()
	{
		attribute(Attributes.DISABLE);

		return this;
	}


	public QuasarMenu disable(boolean condition)
	{
		if (condition) attribute(Attributes.DISABLE);

		return this;
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template TOUCH_POSITION = new HtmlAttribute.Template("touch-position");
		final public static HtmlAttribute.Template FIT = new HtmlAttribute.Template("fit");
		final public static HtmlAttribute.Template DISABLE = new HtmlAttribute.Template("disable");
		final public static HtmlAttribute.Template MAX_HEIGHT = new HtmlAttribute.Template("max-height");
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
