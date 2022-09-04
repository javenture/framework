package net.javenture.framework.quasar.element;


import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;


/*
	2019/06/18
 */
final public class QuasarToolbar
	extends AHtmlContent<QuasarToolbar>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "q-toolbar");


	//
	public QuasarToolbar()
	{
	}


	public QuasarToolbar(AHtmlElement parent)
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
