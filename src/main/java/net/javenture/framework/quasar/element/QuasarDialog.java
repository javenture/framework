package net.javenture.framework.quasar.element;


import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;
import net.javenture.framework.quasar.QuasarTransition;


/*
	2019/07/03
 */
final public class QuasarDialog
	extends AHtmlContent<QuasarDialog>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "q-dialog");


	//
	public QuasarDialog()
	{
	}


	public QuasarDialog(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	public QuasarDialog maximized()
	{
		return attribute(Attributes.MAXIMIZED);
	}


	public QuasarDialog maximized(boolean condition)
	{
		return attribute(condition, Attributes.MAXIMIZED);
	}


	public QuasarDialog transitionShow(QuasarTransition value)
	{
		return attribute(Attributes.TRANSITION_SHOW, value);
	}


	public QuasarDialog transitionHide(QuasarTransition value)
	{
		return attribute(Attributes.TRANSITION_HIDE, value);
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template MAXIMIZED = new HtmlAttribute.Template("maximized");
		final public static HtmlAttribute.Template TRANSITION_SHOW = new HtmlAttribute.Template("transition-show");
		final public static HtmlAttribute.Template TRANSITION_HIDE = new HtmlAttribute.Template("transition-hide");
	}

}
