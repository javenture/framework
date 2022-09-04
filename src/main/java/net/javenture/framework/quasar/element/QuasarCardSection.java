package net.javenture.framework.quasar.element;


import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;


/*
	2019/05/02
 */
final public class QuasarCardSection
	extends AHtmlContent<QuasarCardSection>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "q-card-section");


	//
	public QuasarCardSection()
	{
	}


	public QuasarCardSection(AHtmlElement parent)
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
