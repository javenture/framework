package net.javenture.framework.quasar.element;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.css.CssUnit;
import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;
import net.javenture.framework.icon.IIcon;
import net.javenture.framework.quasar.IQuasarColor;
import net.javenture.framework.model.AModel;
import net.javenture.framework.vue.VueEvent;


/*
	2019/07/05
 */
final public class QuasarButton
	extends AHtmlContent<QuasarButton>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "q-btn");


	//
	public QuasarButton()
	{
	}


	public QuasarButton(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	public QuasarButton color(IQuasarColor value)
	{
		return attribute(IQuasarColor.HtmlAttributes.TEMPLATE_COLOR, value);
	}


	public QuasarButton color(String value)
	{
		return attribute(IQuasarColor.HtmlAttributes.TEMPLATE_COLOR, value);
	}


	public QuasarButton textColor(IQuasarColor value)
	{
		return attribute(IQuasarColor.HtmlAttributes.TEMPLATE_TEXT_COLOR, value);
	}


	public QuasarButton textColor(String value)
	{
		return attribute(IQuasarColor.HtmlAttributes.TEMPLATE_TEXT_COLOR, value);
	}


	public QuasarButton icon(IIcon value)
	{
		return attribute(Attributes.ICON, value);
	}


	public QuasarButton icon(String value)
	{
		return attribute(Attributes.ICON, value);
	}


	public QuasarButton icon(boolean condition, IIcon value)
	{
		return attribute(condition, Attributes.ICON, value);
	}


	public QuasarButton icon(boolean condition, String value)
	{
		return attribute(condition, Attributes.ICON, value);
	}


	public QuasarButton iconRight(IIcon value)
	{
		return attribute(Attributes.ICON_RIGHT, value);
	}


	public QuasarButton iconRight(String value)
	{
		return attribute(Attributes.ICON_RIGHT, value);
	}


	public QuasarButton iconRight(boolean condition, IIcon value)
	{
		return attribute(condition, Attributes.ICON_RIGHT, value);
	}


	public QuasarButton iconRight(boolean condition, String value)
	{
		return attribute(condition, Attributes.ICON_RIGHT, value);
	}


	public QuasarButton loading()
	{
		return attribute(Attributes.LOADING);
	}


	public QuasarButton loading(boolean condition)
	{
		return attribute(condition, Attributes.LOADING);
	}


	public QuasarButton loading(AModel model)
	{
		return vAttribute(Attributes.LOADING, model);
	}


	public QuasarButton percentage(AModel model)
	{
		return vAttribute(Attributes.PERCENTAGE, model);
	}


	public QuasarButton darkPercentage()
	{
		return attribute(Attributes.DARK_PERCENTAGE);
	}


	public QuasarButton darkPercentage(boolean condition)
	{
		return attribute(condition, Attributes.DARK_PERCENTAGE);
	}


	public QuasarButton round()
	{
		return attribute(Attributes.ROUND);
	}


	public QuasarButton round(boolean condition)
	{
		return attribute(condition, Attributes.ROUND);
	}


	public QuasarButton rounded()
	{
		return attribute(Attributes.ROUNDED);
	}


	public QuasarButton rounded(boolean condition)
	{
		return attribute(condition, Attributes.ROUNDED);
	}


	public QuasarButton outline()
	{
		return attribute(Attributes.OUTLINE);
	}


	public QuasarButton outline(boolean condition)
	{
		return attribute(condition, Attributes.OUTLINE);
	}


	public QuasarButton flat()
	{
		return attribute(Attributes.FLAT);
	}


	public QuasarButton flat(boolean condition)
	{
		return attribute(condition, Attributes.FLAT);
	}


	public QuasarButton push()
	{
		return attribute(Attributes.PUSH);
	}


	public QuasarButton push(boolean condition)
	{
		return attribute(condition, Attributes.PUSH);
	}


	public QuasarButton glossy()
	{
		return attribute(Attributes.GLOSSY);
	}


	public QuasarButton glossy(boolean condition)
	{
		return attribute(condition, Attributes.GLOSSY);
	}


	public QuasarButton dense()
	{
		return attribute(Attributes.DENSE);
	}


	public QuasarButton dense(boolean condition)
	{
		return attribute(condition, Attributes.DENSE);
	}


	public QuasarButton fab()
	{
		return attribute(Attributes.FAB);
	}


	public QuasarButton fab(boolean condition)
	{
		return attribute(condition, Attributes.FAB);
	}


	public QuasarButton fabMini()
	{
		return attribute(Attributes.FAB_MINI);
	}


	public QuasarButton fabMini(boolean condition)
	{
		return attribute(condition, Attributes.FAB_MINI);
	}


	public QuasarButton disable()
	{
		return attribute(Attributes.DISABLE);
	}


	public QuasarButton disable(boolean condition)
	{
		return attribute(condition, Attributes.DISABLE);
	}


	public QuasarButton disable(AModel model)
	{
		return vAttribute(Attributes.DISABLE, model);
	}


	public QuasarButton noWrap()
	{
		return attribute(Attributes.NO_WRAP);
	}


	public QuasarButton noWrap(boolean condition)
	{
		return attribute(condition, Attributes.NO_WRAP);
	}


	public QuasarButton noCaps()
	{
		return attribute(Attributes.NO_CAPS);
	}


	public QuasarButton noCaps(boolean condition)
	{
		return attribute(condition, Attributes.NO_CAPS);
	}


	public QuasarButton noRipple()
	{
		return attribute(Attributes.NO_RIPPLE);
	}


	public QuasarButton noRipple(boolean condition)
	{
		return attribute(condition, Attributes.NO_RIPPLE);
	}


	public QuasarButton waitForRipple()
	{
		return attribute(Attributes.WAIT_FOR_RIPPLE);
	}


	public QuasarButton waitForRipple(boolean condition)
	{
		return attribute(condition, Attributes.WAIT_FOR_RIPPLE);
	}


	public QuasarButton label(@NullAllow String value)
	{
		return attribute(Attributes.LABEL, value);
	}


	public QuasarButton label(@NullAllow Number value)
	{
		return attribute(Attributes.LABEL, value);
	}


	public QuasarButton tabindex(int value)
	{
		return attribute(Attributes.TABINDEX, value);
	}


	public QuasarButton size(Size value)
	{
		return attribute(value);
	}


	public QuasarButton size(int value, CssUnit unit)
	{
		HtmlAttribute attribute = new HtmlAttribute(Attributes.SIZE, value);

		if (value != 0) attribute.value(unit);

		return attribute(attribute);
	}


	public QuasarButton align(Align value)
	{
		return attribute(value);
	}


	public QuasarButton repeatTimeout(int value)
	{
		return vAttribute(Attributes.REPEAT_TIMEOUT, value);
	}


	//
	final public static class Attributes                                                                                                        // XXX: enum ?
	{
		final public static HtmlAttribute.Template LOADING = new HtmlAttribute.Template("loading");
		final public static HtmlAttribute.Template DARK_PERCENTAGE = new HtmlAttribute.Template("dark-percentage");
		final public static HtmlAttribute.Template ROUND = new HtmlAttribute.Template("round");
		final public static HtmlAttribute.Template ROUNDED = new HtmlAttribute.Template("rounded");
		final public static HtmlAttribute.Template OUTLINE = new HtmlAttribute.Template("outline");
		final public static HtmlAttribute.Template FLAT = new HtmlAttribute.Template("flat");
		final public static HtmlAttribute.Template PUSH = new HtmlAttribute.Template("push");
		final public static HtmlAttribute.Template GLOSSY = new HtmlAttribute.Template("glossy");
		final public static HtmlAttribute.Template DENSE = new HtmlAttribute.Template("dense");
		final public static HtmlAttribute.Template FAB = new HtmlAttribute.Template("fab");
		final public static HtmlAttribute.Template FAB_MINI = new HtmlAttribute.Template("fab-mini");
		final public static HtmlAttribute.Template DISABLE = new HtmlAttribute.Template("disable");
		final public static HtmlAttribute.Template NO_WRAP = new HtmlAttribute.Template("no-wrap");
		final public static HtmlAttribute.Template NO_CAPS = new HtmlAttribute.Template("no-caps");
		final public static HtmlAttribute.Template NO_RIPPLE = new HtmlAttribute.Template("no-ripple");
		final public static HtmlAttribute.Template WAIT_FOR_RIPPLE = new HtmlAttribute.Template("wait-for-ripple");
		final public static HtmlAttribute.Template SIZE = new HtmlAttribute.Template("size");
		final public static HtmlAttribute.Template ICON = new HtmlAttribute.Template("icon");
		final public static HtmlAttribute.Template ICON_RIGHT = new HtmlAttribute.Template("icon-right");
		final public static HtmlAttribute.Template LABEL = new HtmlAttribute.Template("label");
		final public static HtmlAttribute.Template TABINDEX = new HtmlAttribute.Template("tabindex");
		final public static HtmlAttribute.Template PERCENTAGE = new HtmlAttribute.Template("percentage");
		final public static HtmlAttribute.Template REPEAT_TIMEOUT = new HtmlAttribute.Template("repeat-timeout");
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
		final public static Align AROUND = new Align("around");
		final public static Align BETWEEN = new Align("between");
	}


	final public static class Size
		extends HtmlAttribute.Preset
	{
		//
		private Size(String s)
		{
			super(Attributes.SIZE, s);
		}

		//
		final public static Size XS = new Size("xs");
		final public static Size SM = new Size("sm");
		final public static Size MD = new Size("md");
		final public static Size LG = new Size("lg");
		final public static Size XL = new Size("xl");

		final public static Size FORM = new Size("form");
		final public static Size FORM_INVERTED = new Size("form-inverted");
		final public static Size FORM_HIDE_UNDERLINE = new Size("form-hide-underline");

		final public static Size FORM_LABEL = new Size("form-label");
		final public static Size FORM_LABEL_INVERTED = new Size("form-label-inverted");
		final public static Size FORM_LABEL_HIDE_UNDERLINE = new Size("form-label-hide-underline");
	}


	final public static class Events
	{
		final public static VueEvent CLICK = VueEvent.CLICK;
	}

}
