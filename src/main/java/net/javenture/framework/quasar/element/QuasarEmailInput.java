package net.javenture.framework.quasar.element;


import net.javenture.framework.html.AHtmlElement;


/*
	2018/01/23
 */
final public class QuasarEmailInput
	extends QuasarInput<QuasarEmailInput>
{
	//
	public QuasarEmailInput()
	{
		type(Type.EMAIL);
	}


	public QuasarEmailInput(AHtmlElement parent)
	{
		super(parent);

		type(Type.EMAIL);
	}

}
