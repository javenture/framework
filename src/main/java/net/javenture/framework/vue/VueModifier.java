package net.javenture.framework.vue;


/*
	2019/06/28

	https://vuejs.org/v2/guide/events.html
 */
final public class VueModifier
{
	//
	final private static String PREFIX = ".";


	//
	final String VALUE;


	//
	public VueModifier(String name)
	{
		VALUE = PREFIX + name;
	}


	//
	@Override
	public String toString()
	{
		return VALUE;
	}


	public String value()
	{
		return VALUE;
	}


	//
	final public static VueModifier MODEL_LAZY = new VueModifier("lazy");
	final public static VueModifier MODEL_NUMBER = new VueModifier("number");
	final public static VueModifier MODEL_TRIM = new VueModifier("trim");

	final public static VueModifier EVENT_NATIVE = new VueModifier("native");
	final public static VueModifier EVENT_STOP = new VueModifier("stop");
	final public static VueModifier EVENT_PREVENT = new VueModifier("prevent");
	final public static VueModifier EVENT_CAPTURE = new VueModifier("capture");
	final public static VueModifier EVENT_SELF = new VueModifier("self");
	final public static VueModifier EVENT_ONCE = new VueModifier("once");
	final public static VueModifier EVENT_PASSIVE = new VueModifier("passive");

	final public static VueModifier SYSTEM_EXACT = new VueModifier("exact");
	final public static VueModifier SYSTEM_CTRL = new VueModifier("ctrl");
	final public static VueModifier SYSTEM_ALT = new VueModifier("alt");
	final public static VueModifier SYSTEM_SHIFT = new VueModifier("shift");
	final public static VueModifier SYSTEM_META = new VueModifier("meta");

	final public static VueModifier KEYBOARD_ENTER = new VueModifier("enter");
	final public static VueModifier KEYBOARD_TAB = new VueModifier("tab");
	final public static VueModifier KEYBOARD_DELETE = new VueModifier("delete");
	final public static VueModifier KEYBOARD_ESC = new VueModifier("esc");
	final public static VueModifier KEYBOARD_SPACE = new VueModifier("space");
	final public static VueModifier KEYBOARD_UP = new VueModifier("up");
	final public static VueModifier KEYBOARD_DOWN = new VueModifier("down");
	final public static VueModifier KEYBOARD_LEFT = new VueModifier("left");
	final public static VueModifier KEYBOARD_RIGHT = new VueModifier("right");

	final public static VueModifier MOUSE_LEFT = new VueModifier("left");
	final public static VueModifier MOUSE_RIGHT = new VueModifier("right");
	final public static VueModifier MOUSE_MIDDLE = new VueModifier("middle");

}
