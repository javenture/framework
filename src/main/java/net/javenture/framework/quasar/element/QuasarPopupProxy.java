package net.javenture.framework.quasar.element;


import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.vue.VueEvent;


/*
	2019/06/28
 */
final public class QuasarPopupProxy
	extends AHtmlContent<QuasarPopupProxy>
{
	//
	final public static Config CONFIG = new Config(AHtmlElement.Type.PAIR, "q-popup-proxy");


	//
	public QuasarPopupProxy()
	{
	}


	public QuasarPopupProxy(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	//
	final public static class Events
	{
		final public static VueEvent INPUT = new VueEvent("input");
		final public static VueEvent BEFORE_SHOW = new VueEvent("before-show");
		final public static VueEvent SHOW = new VueEvent("show");
		final public static VueEvent BEFORE_HIDE = new VueEvent("before-hide");
		final public static VueEvent HIDE = new VueEvent("hide");
	}

}
