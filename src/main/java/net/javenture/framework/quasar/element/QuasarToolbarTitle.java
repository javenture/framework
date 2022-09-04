package net.javenture.framework.quasar.element;


import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;


/*
	2018/06/08
 */
final public class QuasarToolbarTitle
	extends AHtmlContent<QuasarToolbarTitle>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "q-toolbar-title");


	//
	public QuasarToolbarTitle()
	{
	}


	public QuasarToolbarTitle(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}

}
