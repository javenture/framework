package net.javenture.framework.quasar.element;


import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;
import net.javenture.framework.quasar.AQuasarElement;
import net.javenture.framework.quasar.QuasarTheme;
import net.javenture.framework.quasar.QuasarTransition;


/*
	2019/07/03
 */
final public class QuasarTabPanels
	extends AQuasarElement<QuasarTabPanels>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "q-tab-panels");


	//
	public QuasarTabPanels()
	{
	}


	public QuasarTabPanels(AHtmlElement parent)
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


	public QuasarTabPanels keepAlive()
	{
		return attribute(Attributes.KEEP_ALIVE);
	}


	public QuasarTabPanels keepAlive(boolean condition)
	{
		return attribute(condition, Attributes.KEEP_ALIVE);
	}


	public QuasarTabPanels animated()
	{
		return attribute(Attributes.ANIMATED);
	}


	public QuasarTabPanels animated(boolean condition)
	{
		return attribute(condition, Attributes.ANIMATED);
	}


	public QuasarTabPanels infinite()
	{
		return attribute(Attributes.INFINITE);
	}


	public QuasarTabPanels infinite(boolean condition)
	{
		return attribute(condition, Attributes.INFINITE);
	}


	public QuasarTabPanels swipeable()
	{
		return attribute(Attributes.SWIPEABLE);
	}


	public QuasarTabPanels swipeable(boolean condition)
	{
		return attribute(condition, Attributes.SWIPEABLE);
	}


	public QuasarTabPanels transitionPrev(QuasarTransition value)
	{
		return attribute(Attributes.TRANSITION_PREV, value);
	}


	public QuasarTabPanels transitionNext(QuasarTransition value)
	{
		return attribute(Attributes.TRANSITION_NEXT, value);
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template KEEP_ALIVE = new HtmlAttribute.Template("keep-alive");
		final public static HtmlAttribute.Template ANIMATED = new HtmlAttribute.Template("animated");
		final public static HtmlAttribute.Template INFINITE = new HtmlAttribute.Template("infinite");
		final public static HtmlAttribute.Template SWIPEABLE = new HtmlAttribute.Template("swipeable");
		final public static HtmlAttribute.Template TRANSITION_PREV = new HtmlAttribute.Template("transition-prev");
		final public static HtmlAttribute.Template TRANSITION_NEXT = new HtmlAttribute.Template("transition-next");
	}

}
