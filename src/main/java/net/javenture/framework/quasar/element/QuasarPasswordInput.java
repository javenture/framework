package net.javenture.framework.quasar.element;


import net.javenture.framework.html.AHtmlElement;


/*
	2019/03/29
 */
final public class QuasarPasswordInput
	extends QuasarInput<QuasarPasswordInput>
{
	//
	public QuasarPasswordInput()
	{
		type(Type.PASSWORD);
	}


	public QuasarPasswordInput(AHtmlElement parent)
	{
		super(parent);

		type(Type.PASSWORD);
	}

}
