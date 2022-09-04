package net.javenture.framework.quasar.element;


import net.javenture.framework.css.ICssClass;
import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;
import net.javenture.framework.model.AModel;
import net.javenture.framework.vue.VueEvent;


/*
	2019/07/16
 */
final public class QuasarDrawer
	extends AHtmlContent<QuasarDrawer>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "q-drawer");


	//
	public QuasarDrawer()
	{
	}


	public QuasarDrawer(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	public QuasarDrawer side(Side value)
	{
		return attribute(value);
	}


	public QuasarDrawer overlay()
	{
		return attribute(Attributes.OVERLAY);
	}


	public QuasarDrawer overlay(boolean condition)
	{
		return attribute(condition, Attributes.OVERLAY);
	}


	public QuasarDrawer overlay(AModel model)
	{
		return vAttribute(Attributes.OVERLAY, model);
	}


	public QuasarDrawer breakpoint(int value)
	{
		return attribute(Attributes.BREAKPOINT, value);
	}


	public QuasarDrawer breakpoint(AModel model)
	{
		return vAttribute(Attributes.BREAKPOINT, model);
	}


	public QuasarDrawer behavior(Behavior value)
	{
		return attribute(value);
	}


	public QuasarDrawer behavior(AModel model)
	{
		return vAttribute(Attributes.BEHAVIOR, model);
	}


	public QuasarDrawer noSwipeOpen()
	{
		return attribute(Attributes.NO_SWIPE_OPEN);
	}


	public QuasarDrawer noSwipeOpen(boolean condition)
	{
		return attribute(condition, Attributes.NO_SWIPE_OPEN);
	}


	public QuasarDrawer noSwipeClose()
	{
		return attribute(Attributes.NO_SWIPE_CLOSE);
	}


	public QuasarDrawer noSwipeClose(boolean condition)
	{
		return attribute(condition, Attributes.NO_SWIPE_CLOSE);
	}


	public QuasarDrawer mini()
	{
		return attribute(Attributes.MINI);
	}


	public QuasarDrawer mini(boolean condition)
	{
		return attribute(condition, Attributes.MINI);
	}


	public QuasarDrawer mini(AModel model)
	{
		return vAttribute(Attributes.MINI, model);
	}


	public QuasarDrawer contentClass(ICssClass value)
	{
		return attribute(Attributes.CONTENT_CLASS, value);
	}


	public QuasarDrawer contentClass(String value)
	{
		return attribute(Attributes.CONTENT_CLASS, value);
	}


	public QuasarDrawer contentClass(ICssClass... values)
	{
		if (values.length != 0)
		{
			HtmlAttribute attribute = new HtmlAttribute(Attributes.CONTENT_CLASS);

			for (ICssClass value : values)
			{
				attribute.delimiter(HtmlAttribute.Delimiter.SPACE);
				attribute.value(value);
			}

			attribute(attribute);
		}

		return this;
	}


	public QuasarDrawer contentClass(String... values)
	{
		if (values.length != 0)
		{
			HtmlAttribute attribute = new HtmlAttribute(Attributes.CONTENT_CLASS);

			for (String value : values)
			{
				attribute.delimiter(HtmlAttribute.Delimiter.SPACE);
				attribute.value(value);
			}

			attribute(attribute);
		}

		return this;
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template OVERLAY = new HtmlAttribute.Template("overlay");
		final public static HtmlAttribute.Template NO_SWIPE_OPEN = new HtmlAttribute.Template("no-swipe-open");
		final public static HtmlAttribute.Template NO_SWIPE_CLOSE = new HtmlAttribute.Template("no-swipe-close");
		final public static HtmlAttribute.Template MINI = new HtmlAttribute.Template("mini");
		final public static HtmlAttribute.Template BREAKPOINT = new HtmlAttribute.Template("breakpoint");
		final public static HtmlAttribute.Template CONTENT_CLASS = new HtmlAttribute.Template("content-class");
		final public static HtmlAttribute.Template BEHAVIOR = new HtmlAttribute.Template("behavior");
		final public static HtmlAttribute.Template SIDE = new HtmlAttribute.Template("side");

		// XXX: mini-width ?
	}


	final public static class Side
		extends HtmlAttribute.Preset
	{
		//
		private Side(String s)
		{
			super(Attributes.SIDE, s);
		}

		//
		final public static Side LEFT = new Side("left");
		final public static Side RIGHT = new Side("right");
	}


	final public static class Behavior
		extends HtmlAttribute.Preset
	{
		//
		private Behavior(String s)
		{
			super(Attributes.BEHAVIOR, s);
		}

		//
		final public static Behavior DEFAULT = new Behavior("default");
		final public static Behavior DESKTOP = new Behavior("desktop");
		final public static Behavior MOBILE = new Behavior("mobile");
	}


	final public static class Events
	{
		final public static VueEvent MOUSEENTER = VueEvent.MOUSEENTER;
		final public static VueEvent MOUSELEAVE = VueEvent.MOUSELEAVE;
	}

}
