package net.javenture.framework.quasar;


import net.javenture.framework.html.HtmlAttribute;


/*
	2019/03/30
 */
final public class QuasarDirective                                                      // XXX: extends VueDirective ?
{
	//
	private QuasarDirective()
	{
	}


	//
	final public static class VueAttributes
	{
		final public static HtmlAttribute.Template PRESET_V_RIPPLE = new HtmlAttribute.Template("v-ripple");
		final public static HtmlAttribute.Template PRESET_V_CLOSE_POPUP = new HtmlAttribute.Template("v-close-popup");
	}

}
