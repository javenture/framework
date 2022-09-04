package net.javenture.framework.vue;


import net.javenture.framework.html.HtmlAttribute;
import net.javenture.framework.html.HtmlText;
import net.javenture.framework.html.IHtmlEntry;
import net.javenture.framework.model.AModel;
import net.javenture.framework.utf8.IUtf8StreamableEntry;
import net.javenture.framework.utf8.Utf8Byte;


/*
	2019/09/29
 */
final public class Vue
{
	//
	private Vue()
	{
	}


	//
	public static IHtmlEntry mustache(AModel model)
	{
		return mustache(model.alias());
	}


	public static IHtmlEntry mustache(CharSequence model)
	{
		IUtf8StreamableEntry entry = destination ->
		{
			destination.write(Utf8Byte.LEFT_CURLY_BRACKET, Utf8Byte.LEFT_CURLY_BRACKET, Utf8Byte.SPACE);
			destination.write(model);
			destination.write(Utf8Byte.SPACE, Utf8Byte.RIGHT_CURLY_BRACKET, Utf8Byte.RIGHT_CURLY_BRACKET);
		};

		return new HtmlText(entry);
	}


	//
	final public static class HtmlAttributes
	{
		final public static HtmlAttribute.Template REF = new HtmlAttribute.Template("ref");
		final public static HtmlAttribute.Template KEY = new HtmlAttribute.Template("key");
		final public static HtmlAttribute.Template V_TEXT = new HtmlAttribute.Template("v-text");
		final public static HtmlAttribute.Template V_HTML = new HtmlAttribute.Template("v-html");
		final public static HtmlAttribute.Template V_IF = new HtmlAttribute.Template("v-if");
		final public static HtmlAttribute.Template V_ELSE_IF = new HtmlAttribute.Template("v-else-if");
		final public static HtmlAttribute.Template V_ELSE = new HtmlAttribute.Template("v-else");
		final public static HtmlAttribute.Template V_FOR = new HtmlAttribute.Template("v-for");
		final public static HtmlAttribute.Template V_SHOW = new HtmlAttribute.Template("v-show");
		final public static HtmlAttribute.Template V_ONCE = new HtmlAttribute.Template("v-once");
		final public static HtmlAttribute.Template V_PRE = new HtmlAttribute.Template("v-pre");
		final public static HtmlAttribute.Template V_BIND = new HtmlAttribute.Template("v-bind");
		final public static HtmlAttribute.Template V_MODEL = new HtmlAttribute.Template("v-model");
	}

}
