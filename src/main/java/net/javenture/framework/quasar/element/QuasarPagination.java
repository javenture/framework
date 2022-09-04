package net.javenture.framework.quasar.element;


import net.javenture.framework.css.CssUnit;
import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;
import net.javenture.framework.quasar.IQuasarColor;


/*
	2019/07/04
 */
final public class QuasarPagination
	extends AHtmlContent<QuasarPagination>
{
	//
	final public static Config CONFIG = new Config(AHtmlElement.Type.PAIR, "q-pagination");


	//
	public QuasarPagination()
	{
	}


	public QuasarPagination(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	public QuasarPagination min(int value)
	{
		return vAttribute(Attributes.MIN, value);
	}


	public QuasarPagination max(int value)
	{
		return vAttribute(Attributes.MAX, value);
	}


	public QuasarPagination color(IQuasarColor value)
	{
		return attribute(IQuasarColor.HtmlAttributes.TEMPLATE_COLOR, value);
	}


	public QuasarPagination color(String value)
	{
		return attribute(IQuasarColor.HtmlAttributes.TEMPLATE_COLOR, value);
	}


	public QuasarPagination textColor(IQuasarColor value)
	{
		return attribute(IQuasarColor.HtmlAttributes.TEMPLATE_TEXT_COLOR, value);
	}


	public QuasarPagination textColor(String value)
	{
		return attribute(IQuasarColor.HtmlAttributes.TEMPLATE_TEXT_COLOR, value);
	}


	public QuasarPagination size(Size value)
	{
		return attribute(value);
	}


	public QuasarPagination size(int value, CssUnit unit)                                                                                          // XXX: ?
	{
		HtmlAttribute attribute = new HtmlAttribute(Attributes.SIZE, value);

		if (value != 0) attribute.value(unit);

		return attribute(attribute);
	}


	public QuasarPagination maxPages(int value)
	{
		return vAttribute(Attributes.MAX_PAGES, value);
	}


	public QuasarPagination input()
	{
		return attribute(Attributes.INPUT);
	}


	public QuasarPagination input(boolean condition)
	{
		return attribute(condition, Attributes.INPUT);
	}


	public QuasarPagination boundaryLinks()
	{
		return attribute(Attributes.BOUNDARY_LINKS);
	}


	public QuasarPagination boundaryLinks(boolean condition)
	{
		return attribute(condition, Attributes.BOUNDARY_LINKS);
	}


	public QuasarPagination boundaryNumbers()
	{
		return attribute(Attributes.BOUNDARY_NUMBERS);
	}


	public QuasarPagination boundaryNumbers(boolean condition)
	{
		return attribute(condition, Attributes.BOUNDARY_NUMBERS);
	}


	public QuasarPagination directionLinks()
	{
		return attribute(Attributes.DIRECTION_LINKS);
	}


	public QuasarPagination directionLinks(boolean condition)
	{
		return attribute(condition, Attributes.DIRECTION_LINKS);
	}


	public QuasarPagination ellipses()
	{
		return attribute(Attributes.ELLIPSES);
	}


	public QuasarPagination ellipses(boolean condition)
	{
		return attribute(condition, Attributes.ELLIPSES);
	}


	public QuasarPagination disable()
	{
		return attribute(Attributes.DISABLE);
	}


	public QuasarPagination disable(boolean condition)
	{
		return attribute(condition, Attributes.DISABLE);
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template INPUT = new HtmlAttribute.Template("input");
		final public static HtmlAttribute.Template BOUNDARY_LINKS = new HtmlAttribute.Template("boundary-links");
		final public static HtmlAttribute.Template BOUNDARY_NUMBERS = new HtmlAttribute.Template("boundary-numbers");
		final public static HtmlAttribute.Template DIRECTION_LINKS = new HtmlAttribute.Template("direction-links");
		final public static HtmlAttribute.Template ELLIPSES = new HtmlAttribute.Template("ellipses");
		final public static HtmlAttribute.Template DISABLE = new HtmlAttribute.Template("disable");
		final public static HtmlAttribute.Template SIZE = new HtmlAttribute.Template("size");
		final public static HtmlAttribute.Template MIN = new HtmlAttribute.Template("min");
		final public static HtmlAttribute.Template MAX = new HtmlAttribute.Template("max");
		final public static HtmlAttribute.Template MAX_PAGES = new HtmlAttribute.Template("max-pages");
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

}
