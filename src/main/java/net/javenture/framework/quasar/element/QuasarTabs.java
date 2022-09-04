package net.javenture.framework.quasar.element;


import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;
import net.javenture.framework.icon.IIcon;
import net.javenture.framework.quasar.AQuasarElement;
import net.javenture.framework.quasar.IQuasarColor;
import net.javenture.framework.quasar.QuasarTheme;


/*
	2019/07/05
 */
final public class QuasarTabs
	extends AQuasarElement<QuasarTabs>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "q-tabs");


	//
	public QuasarTabs()
	{
	}


	public QuasarTabs(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	@Override
	public void theme(QuasarTheme value)
	{
		// XXX


		throw new UnsupportedOperationException();
	}


	public QuasarTabs breakpoint(int value)
	{
		return vAttribute(Attributes.BREAKPOINT, value);
	}


	public QuasarTabs vertical()
	{
		return attribute(Attributes.VERTICAL);
	}


	public QuasarTabs vertical(boolean condition)
	{
		return attribute(condition, Attributes.VERTICAL);
	}


	public QuasarTabs align(Align value)
	{
		return attribute(value);
	}


	public QuasarTabs leftIcon(IIcon value)
	{
		return attribute(Attributes.LEFT_ICON, value);
	}


	public QuasarTabs leftIcon(String value)
	{
		return attribute(Attributes.LEFT_ICON, value);
	}


	public QuasarTabs leftIcon(boolean condition, IIcon value)
	{
		return attribute(condition, Attributes.LEFT_ICON, value);
	}


	public QuasarTabs leftIcon(boolean condition, String value)
	{
		return attribute(condition, Attributes.LEFT_ICON, value);
	}


	public QuasarTabs rightIcon(IIcon value)
	{
		return attribute(Attributes.RIGHT_ICON, value);
	}


	public QuasarTabs rightIcon(String value)
	{
		return attribute(Attributes.RIGHT_ICON, value);
	}


	public QuasarTabs rightIcon(boolean condition, IIcon value)
	{
		return attribute(condition, Attributes.RIGHT_ICON, value);
	}


	public QuasarTabs rightIcon(boolean condition, String value)
	{
		return attribute(condition, Attributes.RIGHT_ICON, value);
	}


	public QuasarTabs shrink()
	{
		return attribute(Attributes.SHRINK);
	}


	public QuasarTabs shrink(boolean condition)
	{
		return attribute(condition, Attributes.SHRINK);
	}


	public QuasarTabs switchIndicator()
	{
		return attribute(Attributes.SWITCH_INDICATOR);
	}


	public QuasarTabs switchIndicator(boolean condition)
	{
		return attribute(condition, Attributes.SWITCH_INDICATOR);
	}


	public QuasarTabs narrowIndicator()
	{
		return attribute(Attributes.NARROW_INDICATOR);
	}


	public QuasarTabs narrowIndicator(boolean condition)
	{
		return attribute(condition, Attributes.NARROW_INDICATOR);
	}


	public QuasarTabs inlineLabel()
	{
		return attribute(Attributes.INLINE_LABEL);
	}


	public QuasarTabs inlineLabel(boolean condition)
	{
		return attribute(condition, Attributes.INLINE_LABEL);
	}


	public QuasarTabs noCaps()
	{
		return attribute(Attributes.NO_CAPS);
	}


	public QuasarTabs noCaps(boolean condition)
	{
		return attribute(condition, Attributes.NO_CAPS);
	}


	public QuasarTabs dense()
	{
		return attribute(Attributes.DENSE);
	}


	public QuasarTabs dense(boolean condition)
	{
		return attribute(condition, Attributes.DENSE);
	}


	public QuasarTabs activeColor(IQuasarColor value)
	{
		return attribute(Attributes.ACTIVE_COLOR, value);
	}


	public QuasarTabs activeColor(String value)
	{
		return attribute(Attributes.ACTIVE_COLOR, value);
	}


	public QuasarTabs activeBgColor(IQuasarColor value)
	{
		return attribute(Attributes.ACTIVE_BG_COLOR, value);
	}


	public QuasarTabs activeBgColor(String value)
	{
		return attribute(Attributes.ACTIVE_BG_COLOR, value);
	}


	public QuasarTabs indicatorColor(IQuasarColor value)
	{
		return attribute(Attributes.INDICATOR_COLOR, value);
	}


	public QuasarTabs indicatorColor(String value)
	{
		return attribute(Attributes.INDICATOR_COLOR, value);
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template VERTICAL = new HtmlAttribute.Template("vertical");
		final public static HtmlAttribute.Template SHRINK = new HtmlAttribute.Template("shrink");
		final public static HtmlAttribute.Template SWITCH_INDICATOR = new HtmlAttribute.Template("switch-indicator");
		final public static HtmlAttribute.Template NARROW_INDICATOR = new HtmlAttribute.Template("narrow-indicator");
		final public static HtmlAttribute.Template INLINE_LABEL = new HtmlAttribute.Template("inline-label");
		final public static HtmlAttribute.Template NO_CAPS = new HtmlAttribute.Template("no-caps");
		final public static HtmlAttribute.Template DENSE = new HtmlAttribute.Template("dense");
		final public static HtmlAttribute.Template LEFT_ICON = new HtmlAttribute.Template("left-icon");
		final public static HtmlAttribute.Template RIGHT_ICON = new HtmlAttribute.Template("right-icon");
		final public static HtmlAttribute.Template ACTIVE_COLOR = new HtmlAttribute.Template("active-color");
		final public static HtmlAttribute.Template ACTIVE_BG_COLOR = new HtmlAttribute.Template("active-bg-color");
		final public static HtmlAttribute.Template INDICATOR_COLOR = new HtmlAttribute.Template("indicator-color");
		final public static HtmlAttribute.Template BREAKPOINT = new HtmlAttribute.Template("breakpoint");
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
		final public static Align JUSTIFY = new Align("justify");
	}

}
