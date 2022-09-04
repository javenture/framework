package net.javenture.framework.quasar.element;


import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;
import net.javenture.framework.html.element.Input;


/*
	2018/05/21
 */
final public class QuasarNumberInput
	extends QuasarInput<QuasarNumberInput>
{
	//
	public QuasarNumberInput()
	{
		type(Type.NUMBER);
	}


	public QuasarNumberInput(AHtmlElement parent)
	{
		super(parent);

		type(Type.NUMBER);
	}


	//
	public QuasarNumberInput numericKeyboardToggle()
	{
		attribute(QuasarNumberInput.Attributes.NUMERIC_KEYBOARD_TOGGLE);

		return this;
	}


	public QuasarNumberInput numericKeyboardToggle(boolean condition)
	{
		if (condition) attribute(QuasarNumberInput.Attributes.NUMERIC_KEYBOARD_TOGGLE);

		return this;
	}


	public QuasarNumberInput decimals(int value)
	{
		attribute(QuasarNumberInput.Attributes.DECIMALS, value);

		return this;
	}


	public QuasarNumberInput min(int value)
	{
		attribute(Input.Attributes.MIN, value);

		return this;
	}


	public QuasarNumberInput max(int value)
	{
		attribute(Input.Attributes.MAX, value);

		return this;
	}


	public QuasarNumberInput step(int value)
	{
		attribute(Input.Attributes.STEP, value);

		return this;
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template NUMERIC_KEYBOARD_TOGGLE = new HtmlAttribute.Template("numeric-keyboard-toggle");
		final public static HtmlAttribute.Template DECIMALS = new HtmlAttribute.Template("decimals");
	}

}
