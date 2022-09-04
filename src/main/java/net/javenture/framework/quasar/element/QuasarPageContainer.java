package net.javenture.framework.quasar.element;


import net.javenture.framework.html.AHtmlElement;


/*
	2018/02/23
 */
final public class QuasarPageContainer
	extends AHtmlElement<QuasarPageContainer>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "q-page-container");


	//
	public QuasarPageContainer()
	{
	}


	public QuasarPageContainer(AHtmlElement parent)
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
