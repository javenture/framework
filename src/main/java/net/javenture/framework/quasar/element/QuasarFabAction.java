package net.javenture.framework.quasar.element;


import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;
import net.javenture.framework.icon.IIcon;


/*
	2019/07/04
 */
final public class QuasarFabAction
	extends AHtmlElement<QuasarFabAction>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "q-fab-action");


	//
	public QuasarFabAction()
	{
	}


	public QuasarFabAction(QuasarFab parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	public QuasarFabAction icon(IIcon icon)
	{
		return attribute(Attributes.ICON, icon);
	}


	public QuasarFabAction icon(String icon)
	{
		return attribute(Attributes.ICON, icon);
	}


	public QuasarFabAction outline()
	{
		return attribute(Attributes.OUTLINE);
	}


	public QuasarFabAction outline(boolean condition)
	{
		return attribute(condition, Attributes.OUTLINE);
	}


	public QuasarFabAction flat()
	{
		return attribute(Attributes.FLAT);
	}


	public QuasarFabAction flat(boolean condition)
	{
		return attribute(condition, Attributes.FLAT);
	}


	public QuasarFabAction push()
	{
		return attribute(Attributes.PUSH);
	}


	public QuasarFabAction push(boolean condition)
	{
		return attribute(condition, Attributes.PUSH);
	}


	public QuasarFabAction glossy()
	{
		return attribute(Attributes.GLOSSY);
	}


	public QuasarFabAction glossy(boolean condition)
	{
		return attribute(condition, Attributes.GLOSSY);
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template OUTLINE = new HtmlAttribute.Template("outline");
		final public static HtmlAttribute.Template FLAT = new HtmlAttribute.Template("flat");
		final public static HtmlAttribute.Template PUSH = new HtmlAttribute.Template("push");
		final public static HtmlAttribute.Template GLOSSY = new HtmlAttribute.Template("glossy");
		final public static HtmlAttribute.Template ICON = new HtmlAttribute.Template("icon");
	}

}
