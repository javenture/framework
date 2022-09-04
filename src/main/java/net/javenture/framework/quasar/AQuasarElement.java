package net.javenture.framework.quasar;


import net.javenture.framework.html.AHtmlContent;
import net.javenture.framework.html.AHtmlElement;


/*
	2019/05/05
 */
abstract public class AQuasarElement<E extends AQuasarElement>
	extends AHtmlContent<E>
{
	//
	protected enum TYPE                                                                                   // XXX: ?
	{
		INPUT,
		SELECT
	}


	//
	protected AQuasarElement()
	{
	}


	protected AQuasarElement(AHtmlElement parent)
	{
		super(parent);
	}


	//
	abstract public void theme(QuasarTheme value);

}
