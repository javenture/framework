package net.javenture.framework.quasar.element;


import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;
import net.javenture.framework.icon.IIcon;
import net.javenture.framework.quasar.IQuasarColor;


/*
	2019/07/04
 */
final public class QuasarChip
	extends AHtmlContent<QuasarChip>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "q-chip");


	//
	public QuasarChip()
	{
	}


	public QuasarChip(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	public QuasarChip floating()
	{
		return attribute(Attributes.FLOATING);
	}


	public QuasarChip floating(boolean condition)
	{
		return attribute(condition, Attributes.FLOATING);
	}


	public QuasarChip tag()
	{
		return attribute(Attributes.TAG);
	}


	public QuasarChip tag(boolean condition)
	{
		return attribute(condition, Attributes.TAG);
	}


	public QuasarChip detail()
	{
		return attribute(Attributes.DETAIL);
	}


	public QuasarChip detail(boolean condition)
	{
		return attribute(condition, Attributes.DETAIL);
	}


	public QuasarChip small()
	{
		return attribute(Attributes.SMALL);
	}


	public QuasarChip small(boolean condition)
	{
		return attribute(condition, Attributes.SMALL);
	}


	public QuasarChip dense()
	{
		return attribute(Attributes.DENSE);
	}


	public QuasarChip dense(boolean condition)
	{
		return attribute(condition, Attributes.DENSE);
	}


	public QuasarChip square()
	{
		return attribute(Attributes.SQUARE);
	}


	public QuasarChip square(boolean condition)
	{
		return attribute(condition, Attributes.SQUARE);
	}


	public QuasarChip closable()
	{
		return attribute(Attributes.CLOSABLE);
	}


	public QuasarChip closable(boolean condition)
	{
		return attribute(condition, Attributes.CLOSABLE);
	}


	public QuasarChip avatar(String value)                            // XXX: NettyUri
	{
		return attribute(Attributes.AVATAR, value);
	}


	public QuasarChip icon(IIcon value)
	{
		return attribute(Attributes.ICON, value);
	}


	public QuasarChip icon(String value)
	{
		return attribute(Attributes.ICON, value);
	}


	public QuasarChip icon(boolean condition, IIcon value)
	{
		return attribute(condition, Attributes.ICON, value);
	}


	public QuasarChip icon(boolean condition, String value)
	{
		return attribute(condition, Attributes.ICON, value);
	}


	public QuasarChip iconRight(IIcon value)
	{
		return attribute(Attributes.ICON_RIGHT, value);
	}


	public QuasarChip iconRight(String value)
	{
		return attribute(Attributes.ICON_RIGHT, value);
	}


	public QuasarChip iconRight(boolean condition, IIcon value)
	{
		return attribute(condition, Attributes.ICON_RIGHT, value);
	}


	public QuasarChip iconRight(boolean condition, String value)
	{
		return attribute(condition, Attributes.ICON_RIGHT, value);
	}


	public QuasarChip pointing(Pointing value)
	{
		return attribute(value);
	}


	public QuasarChip color(IQuasarColor value)
	{
		return attribute(IQuasarColor.HtmlAttributes.TEMPLATE_COLOR, value);
	}


	public QuasarChip color(String value)
	{
		return attribute(IQuasarColor.HtmlAttributes.TEMPLATE_COLOR, value);
	}


	public QuasarChip textColor(IQuasarColor value)
	{
		return attribute(IQuasarColor.HtmlAttributes.TEMPLATE_TEXT_COLOR, value);
	}


	public QuasarChip textColor(String value)
	{
		return attribute(IQuasarColor.HtmlAttributes.TEMPLATE_TEXT_COLOR, value);
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template FLOATING = new HtmlAttribute.Template("floating");
		final public static HtmlAttribute.Template TAG = new HtmlAttribute.Template("tag");
		final public static HtmlAttribute.Template DETAIL = new HtmlAttribute.Template("detail");
		final public static HtmlAttribute.Template SMALL = new HtmlAttribute.Template("small");
		final public static HtmlAttribute.Template DENSE = new HtmlAttribute.Template("dense");
		final public static HtmlAttribute.Template SQUARE = new HtmlAttribute.Template("square");
		final public static HtmlAttribute.Template CLOSABLE = new HtmlAttribute.Template("closable");
		final public static HtmlAttribute.Template ICON = new HtmlAttribute.Template("icon");
		final public static HtmlAttribute.Template ICON_RIGHT = new HtmlAttribute.Template("icon-right");
		final public static HtmlAttribute.Template AVATAR = new HtmlAttribute.Template("avatar");
		final public static HtmlAttribute.Template POINTING = new HtmlAttribute.Template("pointing");
	}


	final public static class Pointing
		extends HtmlAttribute.Preset
	{
		//
		private Pointing(String s)
		{
			super(Attributes.POINTING, s);
		}

		//
		final public static Pointing UP = new Pointing("up");
		final public static Pointing RIGHT = new Pointing("right");
		final public static Pointing DOWN = new Pointing("down");
		final public static Pointing LEFT = new Pointing("left");
	}

}
