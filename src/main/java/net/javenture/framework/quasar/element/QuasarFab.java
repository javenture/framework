package net.javenture.framework.quasar.element;


import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;
import net.javenture.framework.icon.IIcon;


/*
	2019/07/04
 */
final public class QuasarFab
	extends AHtmlContent<QuasarFab>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "q-fab");


	//
	public QuasarFab()
	{
	}


	public QuasarFab(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	public QuasarFab child(QuasarFabAction entry)
	{
		return super.child(entry);
	}


	public QuasarFab direction(Direction value)
	{
		return attribute(value);
	}


	public QuasarFab icon(IIcon icon)
	{
		return attribute(Attributes.ICON, icon);
	}


	public QuasarFab icon(String icon)
	{
		return attribute(Attributes.ICON, icon);
	}


	public QuasarFab activeIcon(IIcon icon)
	{
		return attribute(Attributes.ACTIVE_ICON, icon);
	}


	public QuasarFab activeIcon(String icon)
	{
		return attribute(Attributes.ACTIVE_ICON, icon);
	}


	public QuasarFab outline()
	{
		return attribute(Attributes.OUTLINE);
	}


	public QuasarFab outline(boolean condition)
	{
		return attribute(condition, Attributes.OUTLINE);
	}


	public QuasarFab flat()
	{
		return attribute(Attributes.FLAT);
	}


	public QuasarFab flat(boolean condition)
	{
		return attribute(condition, Attributes.FLAT);
	}


	public QuasarFab push()
	{
		return attribute(Attributes.PUSH);
	}


	public QuasarFab push(boolean condition)
	{
		return attribute(condition, Attributes.PUSH);
	}


	public QuasarFab glossy()
	{
		return attribute(Attributes.GLOSSY);
	}


	public QuasarFab glossy(boolean condition)
	{
		return attribute(condition, Attributes.GLOSSY);
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template OUTLINE = new HtmlAttribute.Template("outline");
		final public static HtmlAttribute.Template FLAT = new HtmlAttribute.Template("flat");
		final public static HtmlAttribute.Template PUSH = new HtmlAttribute.Template("push");
		final public static HtmlAttribute.Template GLOSSY = new HtmlAttribute.Template("glossy");
		final public static HtmlAttribute.Template ICON = new HtmlAttribute.Template("icon");
		final public static HtmlAttribute.Template ACTIVE_ICON = new HtmlAttribute.Template("active-icon");
		final public static HtmlAttribute.Template DIRECTION = new HtmlAttribute.Template("direction");
	}


	final public static class Direction
		extends HtmlAttribute.Preset
	{
		//
		private Direction(String s)
		{
			super(Attributes.DIRECTION, s);
		}

		//
		final public static Direction UP = new Direction("up");
		final public static Direction DOWN = new Direction("down");
		final public static Direction LEFT = new Direction("left");
		final public static Direction RIGHT = new Direction("right");
	}

}
