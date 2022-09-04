package net.javenture.framework.html.element;


import net.javenture.framework.html.AHtmlElement;


/*
	2019/07/05
 */
final public class Ol
	extends AHtmlElement<Ol>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "ol");


	//
	public Ol()
	{
	}


	public Ol(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	public Ol child(Li... entries)
	{
		return super.child(entries);
	}

}
