package net.javenture.framework.html.element;


import net.javenture.framework.html.AHtmlContent;


/*
	2019/06/19
 */
final public class Thead
	extends AHtmlContent<Thead>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "thead");


	//
	public Thead()
	{
	}


	public Thead(Table parent)
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
