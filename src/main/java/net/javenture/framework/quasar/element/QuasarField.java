package net.javenture.framework.quasar.element;


import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;


/*
	2019/05/06
 */
final public class QuasarField
	extends AHtmlContent<QuasarField>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "q-field");


	//
	public QuasarField()
	{
	}


	public QuasarField(AHtmlElement parent)
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
