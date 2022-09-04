package net.javenture.framework.html.element;


import net.javenture.framework.html.AHtmlElement;


/*
	2018/02/07
 */
final public class Form
	extends AHtmlElement<Form>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "form");


	//
	public Form()
	{
	}


	public Form(AHtmlElement parent)
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
