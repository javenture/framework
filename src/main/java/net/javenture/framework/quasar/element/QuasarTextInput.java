package net.javenture.framework.quasar.element;


import net.javenture.framework.html.AHtmlElement;


/*
	2018/01/23
 */
final public class QuasarTextInput
	extends QuasarInput<QuasarTextInput>
{
	//
	public QuasarTextInput()
	{
		type(Type.TEXT);
	}


	public QuasarTextInput(AHtmlElement parent)
	{
		super(parent);

		type(Type.TEXT);
	}

}
