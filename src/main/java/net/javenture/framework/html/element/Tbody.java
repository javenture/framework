package net.javenture.framework.html.element;


import net.javenture.framework.html.AHtmlContent;


/*
	2019/06/19
 */
final public class Tbody
	extends AHtmlContent<Tbody>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "tbody");


	//
	public Tbody()
	{
	}


	public Tbody(Table parent)
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
