package net.javenture.framework.quasar.element;


import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;


/*
	2019/05/08
 */
final public class QuasarSpace
	extends AHtmlContent<QuasarSpace>
{
	//
	final public static Config CONFIG = new Config(AHtmlElement.Type.PAIR, "q-space");


	//
	public QuasarSpace()
	{
	}


	public QuasarSpace(AHtmlElement parent)
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
