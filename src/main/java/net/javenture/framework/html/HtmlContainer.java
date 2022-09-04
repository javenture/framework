package net.javenture.framework.html;


/*
	2018/02/05
 */
final public class HtmlContainer
	extends AHtmlContent<HtmlContainer>
{
	//
	final public static Config CONFIG = new Config(Type.CONTAINER);


	//
	public HtmlContainer()
	{
	}


	public HtmlContainer(AHtmlElement parent)
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
