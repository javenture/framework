package net.javenture.framework.vue;


import net.javenture.framework.util.CharContainer;


/*
	2019/07/22
 */
final public class VueEvent
{
	//
	final private static char PREFIX = '@';


	//
	final private String VALUE;


	//
	public VueEvent(String name)
	{
		VALUE = PREFIX + name;
	}


	public VueEvent(String name, VueModifier modifier)
	{
		VALUE = PREFIX + name + modifier.VALUE;
	}


	public VueEvent(String name, VueModifier... modifiers)
	{
		int capacity = name.length() + 1;

		for (VueModifier modifier : modifiers) capacity += modifier.VALUE.length();

		CharContainer container = new CharContainer(capacity);
		container.add(PREFIX);
		container.add(name);

		for (VueModifier modifier : modifiers) container.add(modifier.VALUE);

		VALUE = container.string();
	}


	private VueEvent(String name, boolean prefix)
	{
		VALUE = prefix ? PREFIX + name : name;
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


	public VueEvent with(VueModifier modifier)
	{
		return new VueEvent(VALUE + modifier.VALUE, false);
	}


	public VueEvent with(VueModifier... modifiers)
	{
		int capacity = VALUE.length();

		for (VueModifier modifier : modifiers) capacity += modifier.VALUE.length();

		CharContainer container = new CharContainer(capacity);
		container.add(VALUE);

		for (VueModifier modifier : modifiers) container.add(modifier.VALUE);

		return new VueEvent(container.string(), false);
	}


	//
	final public static VueEvent BLUR = new VueEvent("blur");
	final public static VueEvent CHANGE = new VueEvent("change");
	final public static VueEvent CLICK = new VueEvent("click");
	final public static VueEvent CONTEXTMENU = new VueEvent("contextmenu");
	final public static VueEvent FOCUS = new VueEvent("focus");
	final public static VueEvent INPUT = new VueEvent("input");
	final public static VueEvent KEYDOWN = new VueEvent("keydown");
	final public static VueEvent KEYUP = new VueEvent("keyup");
	final public static VueEvent KEYPRESS = new VueEvent("keypress");
	final public static VueEvent MOUSEENTER = new VueEvent("mouseenter");
	final public static VueEvent MOUSELEAVE = new VueEvent("mouseleave");
	final public static VueEvent MOUSEOVER = new VueEvent("mouseover");
	final public static VueEvent MOUSEOUT = new VueEvent("mouseout");
	final public static VueEvent MOUSEDOWN = new VueEvent("mousedown");
	final public static VueEvent MOUSEUP = new VueEvent("mouseup");
	final public static VueEvent MOUSEMOVE = new VueEvent("mousemove");
	final public static VueEvent RESIZE = new VueEvent("resize");
	final public static VueEvent SUBMIT = new VueEvent("submit");
	final public static VueEvent SCROLL = new VueEvent("scroll");
	final public static VueEvent TOUCHSTART = new VueEvent("touchstart");
	final public static VueEvent TOUCHEND = new VueEvent("touchend");
	final public static VueEvent TOUCHMOVE = new VueEvent("touchmove");
	final public static VueEvent TOUCHCANCEL = new VueEvent("touchcancel");

}
