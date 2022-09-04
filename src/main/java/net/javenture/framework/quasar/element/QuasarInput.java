package net.javenture.framework.quasar.element;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;
import net.javenture.framework.html.IHtmlEntry;
import net.javenture.framework.html.element.Input;
import net.javenture.framework.html.element.Template;
import net.javenture.framework.quasar.IQuasarColor;
import net.javenture.framework.script.ScriptMethod;
import net.javenture.framework.model.AModel;
import net.javenture.framework.vue.VueEvent;
import net.javenture.framework.vue.VueSlot;


/*
	2019/07/19
 */
public class QuasarInput<T extends QuasarInput>
	extends AHtmlContent<T>
{
	//
	final public static Config CONFIG = new Config(AHtmlElement.Type.PAIR, "q-input");


	//
	final private Template[] SLOTS;


	//
	public QuasarInput()
	{
		SLOTS = new Template[Slot.LENGTH];
	}


	public QuasarInput(@NullDisallow AHtmlElement parent)
	{
		super(parent);

		SLOTS = new Template[Slot.LENGTH];
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	final public T type(Type value)
	{
		return attribute(value);
	}


	final public T color(IQuasarColor value)
	{
		return attribute(IQuasarColor.HtmlAttributes.TEMPLATE_COLOR, value);
	}


	final public T color(String value)
	{
		return attribute(IQuasarColor.HtmlAttributes.TEMPLATE_COLOR, value);
	}


	final public T dark()
	{
		return attribute(Attributes.DARK);
	}


	final public T dark(boolean condition)
	{
		return attribute(condition, Attributes.DARK);
	}


	final public T filled()
	{
		return attribute(Attributes.FILLED);
	}


	final public T filled(boolean condition)
	{
		return attribute(condition, Attributes.FILLED);
	}


	final public T outlined()
	{
		return attribute(Attributes.OUTLINED);
	}


	final public T outlined(boolean condition)
	{
		return attribute(condition, Attributes.OUTLINED);
	}


	final public T borderless()
	{
		return attribute(Attributes.BORDERLESS);
	}


	final public T borderless(boolean condition)
	{
		return attribute(condition, Attributes.BORDERLESS);
	}


	final public T standout()
	{
		return attribute(Attributes.STANDOUT);
	}


	final public T standout(boolean condition)
	{
		return attribute(condition, Attributes.STANDOUT);
	}


	final public T rounded()
	{
		return attribute(Attributes.ROUNDED);
	}


	final public T rounded(boolean condition)
	{
		return attribute(condition, Attributes.ROUNDED);
	}


	final public T square()
	{
		return attribute(Attributes.SQUARE);
	}


	final public T square(boolean condition)
	{
		return attribute(condition, Attributes.SQUARE);
	}


	final public T dense()
	{
		return attribute(Attributes.DENSE);
	}


	final public T dense(boolean condition)
	{
		return attribute(condition, Attributes.DENSE);
	}


	final public T align(Align value)
	{
		return attribute(value);
	}


	final public T readonly()
	{
		return attribute(Attributes.READONLY);
	}


	final public T readonly(boolean condition)
	{
		return attribute(condition, Attributes.READONLY);
	}


	final public T clearable()
	{
		return attribute(Attributes.CLEARABLE);
	}


	final public T clearable(boolean condition)
	{
		return attribute(condition, Attributes.CLEARABLE);
	}


/*
	final public T clearValue(AVueModel model)
	{
		return vAttribute(VueAttributes.TEMPLATE_CLEAR_VALUE, model);
	}
*/


	final public T disable()
	{
		return attribute(Attributes.DISABLE);
	}


	final public T disable(boolean condition)
	{
		return attribute(condition, Attributes.DISABLE);
	}


	final public T autofocus()
	{
		return attribute(Attributes.AUTOFOCUS);
	}


	final public T autofocus(boolean condition)
	{
		return attribute(condition, Attributes.AUTOFOCUS);
	}


	final public T placeholder(String value)
	{
		return attribute(Attributes.PLACEHOLDER, value);
	}


	final public T prefix(String value)
	{
		return attribute(Attributes.PREFIX, value);
	}


	final public T suffix(String value)
	{
		return attribute(Attributes.SUFFIX, value);
	}


	final public T label(String value)
	{
		return attribute(Attributes.LABEL, value);
	}


	final public T hint(String value)
	{
		return attribute(Attributes.HINT, value);
	}


	final public T mask(String value)
	{
		return attribute(Attributes.MASK, value);
	}


	final public T stackLabel()
	{
		return attribute(Attributes.STACK_LABEL);
	}


	final public T stackLabel(boolean condition)
	{
		return attribute(condition, Attributes.STACK_LABEL);
	}


	final public T loading()
	{
		return attribute(Attributes.LOADING);
	}


	final public T loading(boolean condition)
	{
		return attribute(condition, Attributes.LOADING);
	}


	final public T loading(AModel<? extends Boolean> model)
	{
		return vAttribute(Attributes.LOADING, model);
	}


	final public T error()
	{
		return attribute(Attributes.ERROR);
	}


	final public T error(boolean condition)
	{
		return attribute(condition, Attributes.ERROR);
	}


	final public T error(AModel<? extends Boolean> model)
	{
		return vAttribute(Attributes.ERROR, model);
	}


	final public T noErrorIcon()
	{
		return attribute(Attributes.NO_ERROR_ICON);
	}


	final public T noErrorIcon(boolean condition)
	{
		return attribute(condition, Attributes.NO_ERROR_ICON);
	}


	final public T fillMask()
	{
		return attribute(Attributes.FILL_MASK);
	}


	final public T fillMask(boolean condition)
	{
		return attribute(condition, Attributes.FILL_MASK);
	}


	final public T unmaskedValue()
	{
		return attribute(Attributes.UNMASKED_VALUE);
	}


	final public T unmaskedValue(boolean condition)
	{
		return attribute(condition, Attributes.UNMASKED_VALUE);
	}


	final public T autogrow()
	{
		return attribute(Attributes.AUTOGROW);
	}


	final public T autogrow(boolean condition)
	{
		return attribute(condition, Attributes.AUTOGROW);
	}


	final public T bottomSlots()
	{
		return attribute(Attributes.BOTTOM_SLOTS);
	}


	final public T bottomSlots(boolean condition)
	{
		return attribute(condition, Attributes.BOTTOM_SLOTS);
	}


	final public T hideBottomSpace()
	{
		return attribute(Attributes.HIDE_BOTTOM_SPACE);
	}


	final public T hideBottomSpace(boolean condition)
	{
		return attribute(condition, Attributes.HIDE_BOTTOM_SPACE);
	}


	final public T value(boolean value)
	{
		return attribute(Input.Attributes.VALUE, value);
	}


	final public T value(@NullAllow Boolean value)
	{
		return attribute(Input.Attributes.VALUE, value);
	}


	final public T value(short value)
	{
		return attribute(Input.Attributes.VALUE, value);
	}


	final public T value(int value)
	{
		return attribute(Input.Attributes.VALUE, value);
	}


	final public T value(long value)
	{
		return attribute(Input.Attributes.VALUE, value);
	}


	final public T value(float value)
	{
		return attribute(Input.Attributes.VALUE, value);
	}


	final public T value(double value)
	{
		return attribute(Input.Attributes.VALUE, value);
	}


	final public T value(@NullAllow Number value)
	{
		return attribute(Input.Attributes.VALUE, value);
	}


	final public T value(@NullAllow String value)
	{
		return attribute(Input.Attributes.VALUE, value);
	}


	final public T autocomplete(Input.Autocomplete value)
	{
		return attribute(value);
	}


	final public T slot(Slot slot, IHtmlEntry entry)
	{
		slot(slot).content(entry);

		return THIS;
	}


	final public Template slot(Slot slot)
	{
		int index = slot.ordinal();
		Template template = SLOTS[index];

		if (template == null)
		{
			template = new Template(this)
				.attribute(slot.SLOT.attribute());

			SLOTS[index] = template;
		}

		return template;
	}


	//
	public enum Slot
	{
		//
		PREPEND("prepend"),
		APPEND("append"),
		BEFORE("before"),
		AFTER("after"),
		ERROR("error"),
		HINT("hint"),
		COUNTER("counter"),
		LOADING("loading");

		//
		final private static int LENGTH = values().length;

		//
		final private VueSlot SLOT;

		//
		Slot(String name)
		{
			SLOT = new VueSlot(name);
		}
	}


	final public static class Type
		extends HtmlAttribute.Preset
	{
		//
		final private static HtmlAttribute.Template TEMPLATE = new HtmlAttribute.Template("type");

		//
		private Type(String s)
		{
			super(TEMPLATE, s);
		}

		//
		final public static Type TEXT = new Type("text");
		final public static Type PASSWORD = new Type("password");
		final public static Type TEXTAREA = new Type("textarea");
		final public static Type EMAIL = new Type("email");
		final public static Type SEARCH = new Type("search");
		final public static Type TEL = new Type("tel");
		final public static Type FILE = new Type("file");
		final public static Type NUMBER = new Type("number");
		final public static Type URL = new Type("url");
		final public static Type TIME = new Type("time");
		final public static Type DATE = new Type("date");
	}


	final public static class Align
		extends HtmlAttribute.Preset
	{
		//
		final private static HtmlAttribute.Template TEMPLATE = new HtmlAttribute.Template("align");

		//
		private Align(String s)
		{
			super(TEMPLATE, s);
		}

		//
		final public static Align LEFT = new Align("left");
		final public static Align CENTER = new Align("center");
		final public static Align RIGHT = new Align("right");
	}


	final public static class Attributes
	{
		final public static HtmlAttribute.Template DARK = new HtmlAttribute.Template("dark");
		final public static HtmlAttribute.Template FILLED = new HtmlAttribute.Template("filled");
		final public static HtmlAttribute.Template OUTLINED = new HtmlAttribute.Template("outlined");
		final public static HtmlAttribute.Template BORDERLESS = new HtmlAttribute.Template("borderless");
		final public static HtmlAttribute.Template STANDOUT = new HtmlAttribute.Template("standout");
		final public static HtmlAttribute.Template ROUNDED = new HtmlAttribute.Template("rounded");
		final public static HtmlAttribute.Template SQUARE = new HtmlAttribute.Template("square");
		final public static HtmlAttribute.Template DENSE = new HtmlAttribute.Template("dense");
		final public static HtmlAttribute.Template STACK_LABEL = new HtmlAttribute.Template("stack-label");
		final public static HtmlAttribute.Template READONLY = new HtmlAttribute.Template("readonly");
		final public static HtmlAttribute.Template CLEARABLE = new HtmlAttribute.Template("clearable");
		final public static HtmlAttribute.Template DISABLE = new HtmlAttribute.Template("disable");
		final public static HtmlAttribute.Template AUTOFOCUS = new HtmlAttribute.Template("autofocus");
		final public static HtmlAttribute.Template NO_ERROR_ICON = new HtmlAttribute.Template("no-error-icon");
		final public static HtmlAttribute.Template FILL_MASK = new HtmlAttribute.Template("fill-mask");
		final public static HtmlAttribute.Template UNMASKED_VALUE = new HtmlAttribute.Template("unmasked-value");
		final public static HtmlAttribute.Template AUTOGROW = new HtmlAttribute.Template("autogrow");
		final public static HtmlAttribute.Template BOTTOM_SLOTS = new HtmlAttribute.Template("bottom-slots");
		final public static HtmlAttribute.Template HIDE_BOTTOM_SPACE = new HtmlAttribute.Template("hide-bottom-space");
		final public static HtmlAttribute.Template LABEL = new HtmlAttribute.Template("label");
		final public static HtmlAttribute.Template HINT = new HtmlAttribute.Template("hint");
		final public static HtmlAttribute.Template MASK = new HtmlAttribute.Template("mask");
		final public static HtmlAttribute.Template PLACEHOLDER = new HtmlAttribute.Template("placeholder");
		final public static HtmlAttribute.Template PREFIX = new HtmlAttribute.Template("prefix");
		final public static HtmlAttribute.Template SUFFIX = new HtmlAttribute.Template("suffix");
		final public static HtmlAttribute.Template LOADING = new HtmlAttribute.Template("loading");
		final public static HtmlAttribute.Template ERROR = new HtmlAttribute.Template("error");
	}


	final public static class Events
	{
		final public static VueEvent INPUT = VueEvent.INPUT;
		final public static VueEvent FOCUS = VueEvent.FOCUS;
		final public static VueEvent BLUR = VueEvent.BLUR;
		final public static VueEvent KEYDOWN = VueEvent.KEYDOWN;
		final public static VueEvent KEYUP = VueEvent.KEYUP;
		final public static VueEvent KEYPRESS = VueEvent.KEYPRESS;
	}


	final public static class Methods
	{
		final public static ScriptMethod FOCUS = new ScriptMethod("focus");
		final public static ScriptMethod BLUR = new ScriptMethod("blur");
		final public static ScriptMethod SELECT = new ScriptMethod("select");
	}

}
