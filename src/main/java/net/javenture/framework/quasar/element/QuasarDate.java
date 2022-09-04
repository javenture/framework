package net.javenture.framework.quasar.element;


import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;
import net.javenture.framework.quasar.IQuasarColor;
import net.javenture.framework.vue.VueEvent;


/*
	2019/07/19
 */
final public class QuasarDate
	extends AHtmlContent<QuasarDate>
{
	//
	final public static Config CONFIG = new Config(AHtmlElement.Type.PAIR, "q-date");


	//
	public QuasarDate()
	{
	}


	public QuasarDate(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	public QuasarDate firstDayOfWeek(FirstDayOfWeek value)
	{
		return attribute(value);
	}


	public QuasarDate color(IQuasarColor value)
	{
		return attribute(IQuasarColor.HtmlAttributes.TEMPLATE_COLOR, value);
	}


	public QuasarDate color(String value)
	{
		return attribute(IQuasarColor.HtmlAttributes.TEMPLATE_COLOR, value);
	}


	public QuasarDate textColor(IQuasarColor value)
	{
		return attribute(IQuasarColor.HtmlAttributes.TEMPLATE_TEXT_COLOR, value);
	}


	public QuasarDate textColor(String value)
	{
		return attribute(IQuasarColor.HtmlAttributes.TEMPLATE_TEXT_COLOR, value);
	}


	public QuasarDate dark()
	{
		return attribute(Attributes.DARK);
	}


	public QuasarDate dark(boolean condition)
	{
		return attribute(condition, Attributes.DARK);
	}


	public QuasarDate todayBtn()
	{
		return attribute(Attributes.TODAY_BTN);
	}


	public QuasarDate todayBtn(boolean condition)
	{
		return attribute(condition, Attributes.TODAY_BTN);
	}


	public QuasarDate minimal()
	{
		return attribute(Attributes.MINIMAL);
	}


	public QuasarDate minimal(boolean condition)
	{
		return attribute(condition, Attributes.MINIMAL);
	}


	public QuasarDate defaultView(DefaultView value)
	{
		return attribute(value);
	}


	public QuasarDate value(String s)
	{
		return attribute(Attributes.VALUE, s);
	}


	public QuasarDate defaultYearMonth(int year, int month)
	{
		return attribute(Attributes.DEFAULT_YEAR_MONTH, year + "/" + (month < 10 ? "0" : "") + month);
	}


	public QuasarDate readonly()
	{
		return attribute(Attributes.READONLY);
	}


	public QuasarDate readonly(boolean condition)
	{
		return attribute(condition, Attributes.READONLY);
	}


	public QuasarDate disable()
	{
		return attribute(Attributes.DISABLE);
	}


	public QuasarDate disable(boolean condition)
	{
		return attribute(condition, Attributes.DISABLE);
	}


	public QuasarDate mask(String value)
	{
		return attribute(Attributes.MASK, value);
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template DARK = new HtmlAttribute.Template("dark");
		final public static HtmlAttribute.Template TODAY_BTN = new HtmlAttribute.Template("today-btn");
		final public static HtmlAttribute.Template MINIMAL = new HtmlAttribute.Template("minimal");
		final public static HtmlAttribute.Template READONLY = new HtmlAttribute.Template("readonly");
		final public static HtmlAttribute.Template DISABLE = new HtmlAttribute.Template("disable");
		final public static HtmlAttribute.Template VALUE = new HtmlAttribute.Template("value");
		final public static HtmlAttribute.Template DEFAULT_YEAR_MONTH = new HtmlAttribute.Template("default-year-month");
		final public static HtmlAttribute.Template CALENDAR = new HtmlAttribute.Template("calendar");
		final public static HtmlAttribute.Template DEFAULT_VIEW = new HtmlAttribute.Template("default-view");
		final public static HtmlAttribute.Template FIRST_DAY_OF_WEEK = new HtmlAttribute.Template("first-day-of-week");                               // XXX: vue only
		final public static HtmlAttribute.Template MASK = new HtmlAttribute.Template("mask");
	}


	final public static class Calendar
		extends HtmlAttribute.Preset
	{
		//
		private Calendar(String s)
		{
			super(Attributes.CALENDAR, s);
		}

		//
		final public static Calendar GREGORIAN = new Calendar("gregorian");
		final public static Calendar PERSIAN = new Calendar("persian");
	}


	final public static class DefaultView
		extends HtmlAttribute.Preset
	{
		//
		private DefaultView(String s)
		{
			super(Attributes.DEFAULT_VIEW, s);
		}

		//
		final public static DefaultView CALENDAR = new DefaultView("Calendar");
		final public static DefaultView MONTHS = new DefaultView("Months");
		final public static DefaultView YEARS = new DefaultView("Years");
	}


	final public static class FirstDayOfWeek
		extends HtmlAttribute.Preset
	{
		//
		private FirstDayOfWeek(int i)
		{
			super(Attributes.FIRST_DAY_OF_WEEK, "" + i, true);
		}

		//
		final public static FirstDayOfWeek SUNDAY = new FirstDayOfWeek(0);
		final public static FirstDayOfWeek MONDAY = new FirstDayOfWeek(1);
		final public static FirstDayOfWeek TUESDAY = new FirstDayOfWeek(2);
		final public static FirstDayOfWeek WEDNESDAY = new FirstDayOfWeek(3);
		final public static FirstDayOfWeek THURSDAY = new FirstDayOfWeek(4);
		final public static FirstDayOfWeek FRIDAY = new FirstDayOfWeek(5);
		final public static FirstDayOfWeek SATURDAY = new FirstDayOfWeek(6);
	}


	final public static class Events
	{
		final public static VueEvent INPUT = new VueEvent("input");
	}

}
