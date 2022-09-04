package net.javenture.framework.quasar.element;


import net.javenture.framework.html.AHtmlElement;


/*
	2018/01/23
 */
final public class QuasarUrlInput
	extends QuasarInput<QuasarUrlInput>
{
	//
	public QuasarUrlInput()
	{
		type(Type.URL);
	}


	public QuasarUrlInput(AHtmlElement parent)
	{
		super(parent);

		type(Type.URL);
	}

}
