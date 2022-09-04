package net.javenture.framework.quasar.element;


import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.element.Textarea;


/*
	2019/05/06
 */
final public class QuasarTextareaInput
	extends QuasarInput<QuasarTextareaInput>
{
	//
	public QuasarTextareaInput()
	{
		type(Type.TEXTAREA);
	}


	public QuasarTextareaInput(AHtmlElement parent)
	{
		super(parent);

		type(Type.TEXTAREA);
	}


	//
	public QuasarTextareaInput rows(int value)
	{
		return attribute(Textarea.Attributes.ROWS, value);
	}

}
