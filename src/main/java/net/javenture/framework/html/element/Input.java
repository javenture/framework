package net.javenture.framework.html.element;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;


/*
	2019/07/05

	https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input
 */
final public class Input
	extends AHtmlElement<Input>
{
	//
	final public static Config CONFIG = new Config(AHtmlElement.Type.SINGLE, "input");


	//
	public Input()
	{
	}


	public Input(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	public Input type(Type value)
	{
		return attribute(value);
	}


	public Input name(String value)
	{
		return attribute(Attributes.NAME, value);
	}


	public Input value(boolean value)
	{
		return attribute(Attributes.VALUE, value);
	}


	public Input value(@NullAllow Boolean value)
	{
		return attribute(Attributes.VALUE, value);
	}


	public Input value(short value)
	{
		return attribute(Attributes.VALUE, value);
	}


	public Input value(int value)
	{
		return attribute(Attributes.VALUE, value);
	}


	public Input value(long value)
	{
		return attribute(Attributes.VALUE, value);
	}


	public Input value(float value)
	{
		return attribute(Attributes.VALUE, value);
	}


	public Input value(double value)
	{
		return attribute(Attributes.VALUE, value);
	}


	public Input value(@NullAllow Number value)
	{
		return attribute(Attributes.VALUE, value);
	}


	public Input value(@NullAllow String value)
	{
		return attribute(Attributes.VALUE, value);
	}


	public Input checked()
	{
		return attribute(Attributes.CHECKED);
	}


	public Input checked(boolean condition)
	{
		return attribute(condition, Attributes.CHECKED);
	}


	public Input disabled()
	{
		return attribute(Attributes.DISABLED);
	}


	public Input disabled(boolean condition)
	{
		return attribute(condition, Attributes.DISABLED);
	}


	public Input title(String value)
	{
		return attribute(Attributes.TITLE, value);
	}


	public Input alt(String value)
	{
		return attribute(Attributes.ALT, value);
	}


	public Input min(int value)
	{
		return attribute(Attributes.MIN, value);
	}


	public Input max(int value)
	{
		return attribute(Attributes.MAX, value);
	}


	public Input step(int value)
	{
		return attribute(Attributes.STEP, value);
	}


	public Input minLength(int value)
	{
		return attribute(Attributes.MINLENGTH, value);
	}


	public Input maxLength(int value)
	{
		return attribute(Attributes.MAXLENGTH, value);
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template CHECKED = new HtmlAttribute.Template("checked");
		final public static HtmlAttribute.Template DISABLED = new HtmlAttribute.Template("disabled");
		final public static HtmlAttribute.Template NAME = new HtmlAttribute.Template("name");
		final public static HtmlAttribute.Template VALUE = new HtmlAttribute.Template("value");
		final public static HtmlAttribute.Template TITLE = new HtmlAttribute.Template("title");
		final public static HtmlAttribute.Template ALT = new HtmlAttribute.Template("alt");
		final public static HtmlAttribute.Template MIN = new HtmlAttribute.Template("min");
		final public static HtmlAttribute.Template MAX = new HtmlAttribute.Template("max");
		final public static HtmlAttribute.Template STEP = new HtmlAttribute.Template("step");
		final public static HtmlAttribute.Template MINLENGTH = new HtmlAttribute.Template("minlength");
		final public static HtmlAttribute.Template MAXLENGTH = new HtmlAttribute.Template("maxlength");
		final public static HtmlAttribute.Template TYPE = new HtmlAttribute.Template("type");
		final public static HtmlAttribute.Template AUTOCOMPLETE = new HtmlAttribute.Template("autocomplete");
	}


	final public static class Type
		extends HtmlAttribute.Preset
	{
		//
		private Type(String s)
		{
			super(Attributes.TYPE, s);
		}

		//
		final public static Type CHECKBOX = new Type("checkbox");
		final public static Type HIDDEN = new Type("hidden");
		final public static Type IMAGE = new Type("image");
		final public static Type PASSWORD = new Type("password");
		final public static Type RADIO = new Type("radio");
		final public static Type SUBMIT = new Type("submit");
		final public static Type TEXT = new Type("text");
		final public static Type BUTTON = new Type("button");
	}


	final public static class Autocomplete
		extends HtmlAttribute.Preset
	{
		//
		private Autocomplete(String s)
		{
			super(Attributes.AUTOCOMPLETE, s);
		}

		//
		final public static Autocomplete OFF = new Autocomplete("off");
		final public static Autocomplete ON = new Autocomplete("on");
		final public static Autocomplete NAME = new Autocomplete("name"); // Full name
		final public static Autocomplete HONORIFIC_PREFIX = new Autocomplete("honorific-prefix"); // Prefix or title (e.g. "Mr.", "Ms.", "Dr.", "Mlle")
		final public static Autocomplete GIVEN_NAME = new Autocomplete("given-name"); // First name
		final public static Autocomplete ADDITIONAL_NAME = new Autocomplete("additional-name"); // Middle name
		final public static Autocomplete FAMILY_NAME = new Autocomplete("family-name"); // Last name
		final public static Autocomplete HONORIFIC_SUFFIX = new Autocomplete("honorific-suffix"); // Suffix (e.g. "Jr.", "B.Sc.", "MBASW", "II")
		final public static Autocomplete NICKNAME = new Autocomplete("nickname");
		final public static Autocomplete EMAIL = new Autocomplete("email");
		final public static Autocomplete USERNAME = new Autocomplete("username");
		final public static Autocomplete NEW_PASSWORD = new Autocomplete("new-password"); // A new password (e.g. when creating an account or changing a password)
		final public static Autocomplete CURRENT_PASSWORD = new Autocomplete("current-password");
		final public static Autocomplete ORGANIZATION_TITLE = new Autocomplete("organization-title"); // Job title (e.g. "Software Engineer", "Senior Vice President", "Deputy Managing Director")
		final public static Autocomplete ORGANIZATION = new Autocomplete("organization");
		final public static Autocomplete STREET_ADDRESS = new Autocomplete("street-address");
		final public static Autocomplete ADDRESS_LINE1 = new Autocomplete("address-line1");
		final public static Autocomplete ADDRESS_LINE2 = new Autocomplete("address-line2");
		final public static Autocomplete ADDRESS_LINE3 = new Autocomplete("address-line3");
		final public static Autocomplete ADDRESS_LEVEL4 = new Autocomplete("address-level4");
		final public static Autocomplete ADDRESS_LEVEL3 = new Autocomplete("address-level3");
		final public static Autocomplete ADDRESS_LEVEL2 = new Autocomplete("address-level2");
		final public static Autocomplete ADDRESS_LEVEL1 = new Autocomplete("address-level1");
		final public static Autocomplete COUNTRY = new Autocomplete("country");
		final public static Autocomplete COUNTRY_NAME = new Autocomplete("country-name");
		final public static Autocomplete POSTAL_CODE = new Autocomplete("postal-code");
		final public static Autocomplete CC_NAME = new Autocomplete("cc-name"); // Full name as given on the payment instrument
		final public static Autocomplete CC_GIVEN_NAME = new Autocomplete("cc-given-name");
		final public static Autocomplete CC_ADDITIONAL_NAME = new Autocomplete("cc-additional-name");
		final public static Autocomplete CC_FAMILY_NAME = new Autocomplete("cc-family-name");
		final public static Autocomplete CC_NUMBER = new Autocomplete("cc-number"); // Code identifying the payment instrument (e.g. the credit card number)
		final public static Autocomplete CC_EXP = new Autocomplete("cc-exp"); // Expiration date of the payment instrument
		final public static Autocomplete CC_EXP_MONTH = new Autocomplete("cc-exp-month");
		final public static Autocomplete CC_EXP_YEAR = new Autocomplete("cc-exp-year");
		final public static Autocomplete CC_CSC = new Autocomplete("cc-csc"); // Security code for the payment instrument
		final public static Autocomplete CC_TYPE = new Autocomplete("cc-type"); // Type of payment instrument (e.g. Visa)
		final public static Autocomplete TRANSACTION_CURRENCY = new Autocomplete("transaction-currency");
		final public static Autocomplete TRANSACTION_AMOUNT = new Autocomplete("transaction-amount");
		final public static Autocomplete LANGUAGE = new Autocomplete("language"); // Preferred language; a valid BCP 47 language tag
		final public static Autocomplete BDAY = new Autocomplete("bday"); // birthday
		final public static Autocomplete BDAY_DAY = new Autocomplete("bday-day");
		final public static Autocomplete BDAY_MONTH = new Autocomplete("bday-month");
		final public static Autocomplete BDAY_YEAR = new Autocomplete("bday-year");
		final public static Autocomplete SEX = new Autocomplete("sex"); // Gender identity (e.g. Female, Fa'afafine), free-form text, no newlines
		final public static Autocomplete TEL = new Autocomplete("tel"); // full telephone number, including country code
		final public static Autocomplete TEL_COUNTRY_CODE = new Autocomplete("tel-country-code");
		final public static Autocomplete TEL_NATIONAL = new Autocomplete("tel-national");
		final public static Autocomplete TEL_AREA_CODE = new Autocomplete("tel-area-code");
		final public static Autocomplete TEL_LOCAL = new Autocomplete("tel-local");
		final public static Autocomplete TEL_LOCAL_PREFIX = new Autocomplete("tel-local-prefix");
		final public static Autocomplete TEL_LOCAL_SUFFIX = new Autocomplete("tel-local-suffix");
		final public static Autocomplete TEL_EXTENSION = new Autocomplete("tel-extension");
		final public static Autocomplete URL = new Autocomplete("url");
		final public static Autocomplete PHOTO = new Autocomplete("photo");
	}

}
