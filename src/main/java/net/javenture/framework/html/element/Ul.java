package net.javenture.framework.html.element;


import net.javenture.framework.html.AHtmlElement;


/*
	2019/07/05
 */
final public class Ul
	extends AHtmlElement<Ul>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "ul");


	//
	public Ul()
	{
	}


	public Ul(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	public Ul child(Li... entries)
	{
		return super.child(entries);
	}

}
