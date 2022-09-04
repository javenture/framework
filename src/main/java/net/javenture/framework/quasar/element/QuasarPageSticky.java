package net.javenture.framework.quasar.element;


import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;


/*
	2019/07/02
 */
final public class QuasarPageSticky
	extends AHtmlElement<QuasarPageSticky>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "q-page-sticky");


	//
	public QuasarPageSticky()
	{
	}


	public QuasarPageSticky(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	public QuasarPageSticky position(Position value)
	{
		return attribute(value);
	}


	public QuasarPageSticky expand()
	{
		return attribute(Attributes.EXPAND);
	}


	public QuasarPageSticky expand(boolean condition)
	{
		return attribute(condition, Attributes.EXPAND);
	}


	public QuasarPageSticky offset(Offset value)
	{
		return attribute(value);
	}


	public QuasarPageSticky offset(int x, int y)
	{
		return vAttribute(Attributes.OFFSET, "[" + x + ", " + y + "]");
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template EXPAND = new HtmlAttribute.Template("expand");
		final public static HtmlAttribute.Template OFFSET = new HtmlAttribute.Template("offset", true);
		final public static HtmlAttribute.Template POSITION = new HtmlAttribute.Template("position");
	}


	final public static class Position
		extends HtmlAttribute.Preset
	{
		//
		private Position(String s)
		{
			super(Attributes.POSITION, s);
		}

		//
		final public static Position TOP = new Position("top");
		final public static Position RIGHT = new Position("right");
		final public static Position BOTTOM = new Position("bottom");
		final public static Position LEFT = new Position("left");
		final public static Position TOP_LEFT = new Position("top-left");
		final public static Position TOP_RIGHT = new Position("top-right");
		final public static Position BOTTOM_LEFT = new Position("bottom-left");
		final public static Position BOTTOM_RIGHT = new Position("bottom-right");
	}


	final public static class Offset
		extends HtmlAttribute.Preset
	{
		//
		private Offset(int i)
		{
			super(Attributes.OFFSET, "[" + i + ", " + i + "]", true);
		}

		//
		final public static Offset XS = new Offset(4);
		final public static Offset SM = new Offset(8);
		final public static Offset MD = new Offset(16);
		final public static Offset LG = new Offset(24);
		final public static Offset XL = new Offset(48);
	}

}
