package net.javenture.framework.vue;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.IFactoryObject;
import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;
import net.javenture.framework.html.HtmlPreset;
import net.javenture.framework.html.element.Style;
import net.javenture.framework.model.AModel;
import net.javenture.framework.script.ScriptArray;
import net.javenture.framework.script.AScriptFunction;
import net.javenture.framework.script.ScriptObject;
import net.javenture.framework.script.ScriptProperty;
import net.javenture.framework.script.ScriptUtil;
import net.javenture.framework.utf8.IUtf8StreamableEntry;
import net.javenture.framework.utf8.Utf8Byte;
import net.javenture.framework.utf8.Utf8OutputStream;
import net.javenture.framework.util.Chain;
import net.javenture.framework.util.IConst;
import net.javenture.framework.util.Validator;

import java.io.IOException;


/*
	2019/10/04
 */
final public class VueApp
	implements IUtf8StreamableEntry
{
	//
	final private static String NAME_DEFAULT = "app";

	final private static AScriptFunction FUNCTION_MOUNTED = new AScriptFunction()
	{
		@Override
		protected String _name()
		{
			return "mounted";
		}

		@Override
		protected String _content()
		{
			return
				"function ()" +
				"{" +
					"var app = this;" +                                                // XXX: del
					"var el = this.$el;" +
					"" +
					"setInterval(function() { app.ping(); }, 60000);" +                // XXX: del
					"" +
					"this.$nextTick" +
					"(" +
						"function ()" +
						"{" +
							"var url = Javenture.url.parse(window.location.href);" +                                          // XXX
							"var scroll = Javenture.url.parameter(url, '$SCROLL');" +
							"" +
							"if (typeof scroll !== 'undefined') Javenture.quasar.setScroll(scroll);" +
							"" +
							"el.removeAttribute('" + HtmlAttributes.NAME_VUE_UNRESOLVED + "');" +
						"}" +
					");" +
				"}";
		}
	};

	final public static HtmlPreset STYLE = new HtmlPreset
	(
		new Style()
			.content
			(
				"[" + HtmlAttributes.NAME_VUE_APPLICATION + "]" +
				"{" +
					"opacity: 1;" +
					"transition: opacity ease-in 0.3s;" +
				"}" +
				"" +
				"[" + HtmlAttributes.NAME_VUE_APPLICATION + "][" + HtmlAttributes.NAME_VUE_UNRESOLVED + "]" +
				"{" +
					"opacity: 0;" +
				"}"
			)
	);


	//
	final private String NAME;
	final private String EL;
	final private ScriptObject DATA;
	final private Chain<AScriptFunction> METHODS;
	final private Chain<AScriptFunction> COMPUTED;
	final private Chain<AScriptFunction> WATCH;

	private AScriptFunction mounted;                                                                         // XXX


	//
	public VueApp(String el)
	{
		this(NAME_DEFAULT, el);
	}


	public VueApp(String name, String el)
	{
		Validator.argument(!name.isEmpty(), "[name] is empty");
		Validator.argument(!el.isEmpty(), "[el] is empty");

		NAME = name;
		EL = el;
		DATA = new ScriptObject();
		METHODS = new Chain<>();
		COMPUTED = new Chain<>();
		WATCH = new Chain<>();

		mounted = null;
	}


	//
	@Override
	public void toUtf8Stream(Utf8OutputStream destination)
		throws IOException
	{
		destination.write(Utf8Byte.V_SMALL, Utf8Byte.A_SMALL, Utf8Byte.R_SMALL, Utf8Byte.SPACE);
		destination.write(NAME);
		destination.write(Utf8Byte.SPACE, Utf8Byte.EQUAL, Utf8Byte.SPACE);
		destination.write(Utf8Byte.N_SMALL, Utf8Byte.E_SMALL, Utf8Byte.W_SMALL, Utf8Byte.SPACE, Utf8Byte.V_CAPITAL, Utf8Byte.U_SMALL, Utf8Byte.E_SMALL);
		destination.write(Utf8Byte.LEFT_PARENTHESIS, Utf8Byte.LEFT_CURLY_BRACKET, Utf8Byte.SPACE);
		destination.write(Utf8Byte.E_SMALL, Utf8Byte.L_SMALL, Utf8Byte.COLON, Utf8Byte.SPACE, Utf8Byte.APOSTROPHE, Utf8Byte.HASHTAG);
		destination.write(EL);
		destination.write(Utf8Byte.APOSTROPHE);

		// data
		if (DATA.exists())
		{
			destination.write(Utf8Byte.COMMA, Utf8Byte.SPACE, Utf8Byte.D_SMALL, Utf8Byte.A_SMALL, Utf8Byte.T_SMALL, Utf8Byte.A_SMALL, Utf8Byte.COLON, Utf8Byte.SPACE);
			ScriptUtil.toStream(destination, DATA.compose());
		}

		// methods
		if (METHODS.exists())
		{
			destination.write(Utf8Byte.COMMA, Utf8Byte.SPACE);
			destination.write(Utf8Byte.M_SMALL, Utf8Byte.E_SMALL, Utf8Byte.T_SMALL, Utf8Byte.H_SMALL, Utf8Byte.O_SMALL, Utf8Byte.D_SMALL, Utf8Byte.S_SMALL);
			destination.write(Utf8Byte.COLON, Utf8Byte.SPACE, Utf8Byte.LEFT_CURLY_BRACKET);
			ScriptUtil.toStream(destination, METHODS);
			destination.write(Utf8Byte.RIGHT_CURLY_BRACKET);
		}

		// computed
		if (COMPUTED.exists())
		{
			destination.write(Utf8Byte.COMMA, Utf8Byte.SPACE);
			destination.write(Utf8Byte.C_SMALL, Utf8Byte.O_SMALL, Utf8Byte.M_SMALL, Utf8Byte.P_SMALL, Utf8Byte.U_SMALL, Utf8Byte.T_SMALL, Utf8Byte.E_SMALL, Utf8Byte.D_SMALL);
			destination.write(Utf8Byte.COLON, Utf8Byte.SPACE, Utf8Byte.LEFT_CURLY_BRACKET);
			ScriptUtil.toStream(destination, COMPUTED);
			destination.write(Utf8Byte.RIGHT_CURLY_BRACKET);
		}

		// watch
		if (WATCH.exists())
		{
			destination.write(Utf8Byte.COMMA, Utf8Byte.SPACE);
			destination.write(Utf8Byte.W_SMALL, Utf8Byte.A_SMALL, Utf8Byte.T_SMALL, Utf8Byte.C_SMALL, Utf8Byte.H_SMALL);
			destination.write(Utf8Byte.COLON, Utf8Byte.SPACE, Utf8Byte.LEFT_CURLY_BRACKET);
			ScriptUtil.toStream(destination, WATCH);
			destination.write(Utf8Byte.RIGHT_CURLY_BRACKET);
		}

		// mounted
		if (mounted != null)                                                                                                        // XXX
		{
			destination.write(Utf8Byte.COMMA, Utf8Byte.SPACE);
			ScriptUtil.toStream(destination, mounted);
		}
		else
		{
			destination.write(Utf8Byte.COMMA, Utf8Byte.SPACE);
			ScriptUtil.toStream(destination, FUNCTION_MOUNTED);
		}

		//
		destination.write(Utf8Byte.SPACE, Utf8Byte.RIGHT_CURLY_BRACKET, Utf8Byte.RIGHT_PARENTHESIS, Utf8Byte.SEMICOLON);
	}


	public String name()
	{
		return NAME;
	}


	public String el()
	{
		return EL;
	}


	public VueApp data(AModel model)
	{
		DATA.add(model.toScriptStructure());

		return this;
	}


	public <T> VueApp data(AModel<T> model, T value)
	{
		DATA.add(model.toScriptStructure(value));

		return this;
	}


	public VueApp data(AModel... models)
	{
		for (AModel model : models) DATA.add(model.toScriptStructure());

		return this;
	}


	public VueApp data(Iterable<AModel> models)
	{
		for (AModel model : models) DATA.add(model.toScriptStructure());

		return this;
	}


	public VueApp data(ScriptObject object)
	{
		DATA.add(object);

		return this;
	}


	public VueApp data(ScriptObject... objects)
	{
		DATA.add(objects);

		return this;
	}


	public VueApp data(ScriptArray array)
	{
		DATA.add(array);

		return this;
	}


	public VueApp data(ScriptArray... arrays)
	{
		DATA.add(arrays);

		return this;
	}


	public VueApp data(ScriptProperty property)
	{
		DATA.add(property);

		return this;
	}


	public VueApp data(ScriptProperty... properties)
	{
		DATA.add(properties);

		return this;
	}


	public VueApp data(@NullDisallow String name, boolean value)
	{
		DATA.add(new ScriptProperty(name, value));

		return this;
	}


	public VueApp data(@NullDisallow String name, boolean value, boolean quotation)
	{
		DATA.add(new ScriptProperty(name, value, quotation));

		return this;
	}


	public VueApp data(@NullDisallow String name, @NullAllow Boolean value)
	{
		DATA.add(new ScriptProperty(name, value));

		return this;
	}


	public VueApp data(@NullDisallow String name, @NullAllow Boolean value, boolean quotation)
	{
		DATA.add(new ScriptProperty(name, value, quotation));

		return this;
	}


	public VueApp data(@NullDisallow String name, short value)
	{
		DATA.add(new ScriptProperty(name, value));

		return this;
	}


	public VueApp data(@NullDisallow String name, short value, boolean quotation)
	{
		DATA.add(new ScriptProperty(name, value, quotation));

		return this;
	}


	public VueApp data(@NullDisallow String name, @NullAllow Short value)
	{
		DATA.add(new ScriptProperty(name, value));

		return this;
	}


	public VueApp data(@NullDisallow String name, @NullAllow Short value, boolean quotation)
	{
		DATA.add(new ScriptProperty(name, value, quotation));

		return this;
	}


	public VueApp data(@NullDisallow String name, int value)
	{
		DATA.add(new ScriptProperty(name, value));

		return this;
	}


	public VueApp data(@NullDisallow String name, int value, boolean quotation)
	{
		DATA.add(new ScriptProperty(name, value, quotation));

		return this;
	}


	public VueApp data(@NullDisallow String name, @NullAllow Integer value)
	{
		DATA.add(new ScriptProperty(name, value));

		return this;
	}


	public VueApp data(@NullDisallow String name, @NullAllow Integer value, boolean quotation)
	{
		DATA.add(new ScriptProperty(name, value, quotation));

		return this;
	}


	public VueApp data(@NullDisallow String name, long value)
	{
		DATA.add(new ScriptProperty(name, value));

		return this;
	}


	public VueApp data(@NullDisallow String name, long value, boolean quotation)
	{
		DATA.add(new ScriptProperty(name, value, quotation));

		return this;
	}


	public VueApp data(@NullDisallow String name, @NullAllow Long value)
	{
		DATA.add(new ScriptProperty(name, value));

		return this;
	}


	public VueApp data(@NullDisallow String name, @NullAllow Long value, boolean quotation)
	{
		DATA.add(new ScriptProperty(name, value, quotation));

		return this;
	}


	public VueApp data(@NullDisallow String name, float value)
	{
		DATA.add(new ScriptProperty(name, value));

		return this;
	}


	public VueApp data(@NullDisallow String name, float value, boolean quotation)
	{
		DATA.add(new ScriptProperty(name, value, quotation));

		return this;
	}


	public VueApp data(@NullDisallow String name, @NullAllow Float value)
	{
		DATA.add(new ScriptProperty(name, value));

		return this;
	}


	public VueApp data(@NullDisallow String name, @NullAllow Float value, boolean quotation)
	{
		DATA.add(new ScriptProperty(name, value, quotation));

		return this;
	}


	public VueApp data(@NullDisallow String name, double value)
	{
		DATA.add(new ScriptProperty(name, value));

		return this;
	}


	public VueApp data(@NullDisallow String name, double value, boolean quotation)
	{
		DATA.add(new ScriptProperty(name, value, quotation));

		return this;
	}


	public VueApp data(@NullDisallow String name, @NullAllow Double value)
	{
		DATA.add(new ScriptProperty(name, value));

		return this;
	}


	public VueApp data(@NullDisallow String name, @NullAllow Double value, boolean quotation)
	{
		DATA.add(new ScriptProperty(name, value, quotation));

		return this;
	}


	public VueApp data(@NullDisallow String name, @NullAllow Number value)
	{
		DATA.add(new ScriptProperty(name, value));

		return this;
	}


	public VueApp data(@NullDisallow String name, @NullAllow Number value, boolean quotation)
	{
		DATA.add(new ScriptProperty(name, value, quotation));

		return this;
	}


	public VueApp data(@NullDisallow String name, @NullAllow CharSequence value)
	{
		DATA.add(new ScriptProperty(name, value));

		return this;
	}


	public VueApp data(@NullDisallow String name, @NullAllow CharSequence value, boolean quotation)
	{
		DATA.add(new ScriptProperty(name, value, quotation));

		return this;
	}


	public VueApp data(@NullDisallow String name, @NullDisallow IConst value)
	{
		DATA.add(new ScriptProperty(name, value));

		return this;
	}


	public VueApp data(@NullDisallow String name, @NullDisallow IConst value, boolean quotation)
	{
		DATA.add(new ScriptProperty(name, value, quotation));

		return this;
	}


	public VueApp data(@NullDisallow String name, @NullDisallow IFactoryObject value)
	{
		DATA.add(new ScriptProperty(name, value));

		return this;
	}


	public VueApp data(@NullDisallow String name, @NullDisallow IFactoryObject value, boolean quotation)
	{
		DATA.add(new ScriptProperty(name, value, quotation));

		return this;
	}


	public VueApp method(AScriptFunction value)
	{
		METHODS.add(value);

		return this;
	}


	public VueApp method(AScriptFunction... values)
	{
		METHODS.add(values);

		return this;
	}


	public VueApp computed(AScriptFunction value)
	{
		COMPUTED.add(value);

		return this;
	}


	public VueApp computed(AScriptFunction... values)
	{
		COMPUTED.add(values);

		return this;
	}


	public VueApp watch(AScriptFunction value)
	{
		WATCH.add(value);

		return this;
	}


	public VueApp watch(AScriptFunction... values)
	{
		WATCH.add(values);

		return this;
	}


	public VueApp mounted(AScriptFunction value)                                                                            // XXX
	{
		mounted = value;

		return this;
	}


	public void container(AHtmlElement container)                                         // XXX: boolean unresolved ?
	{
		container
			.id(EL)
			.attribute(HtmlAttributes.PRESET_VUE_APPLICATION)
			.attribute(HtmlAttributes.PRESET_VUE_UNRESOLVED);
	}


	//
	final private static class HtmlAttributes
	{
		final private static String NAME_VUE_APPLICATION = "vue-application";
		final private static String NAME_VUE_UNRESOLVED = "vue-unresolved";

		final private static HtmlAttribute.Template PRESET_VUE_APPLICATION = new HtmlAttribute.Template(NAME_VUE_APPLICATION);
		final private static HtmlAttribute.Template PRESET_VUE_UNRESOLVED = new HtmlAttribute.Template(NAME_VUE_UNRESOLVED);
	}

}
