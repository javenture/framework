package net.javenture.framework.html;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.css.ICssClass;
import net.javenture.framework.script.ScriptMethod;
import net.javenture.framework.utf8.IUtf8StreamableEntry;
import net.javenture.framework.utf8.Utf8Byte;
import net.javenture.framework.utf8.Utf8OutputStream;
import net.javenture.framework.utf8.Utf8Util;
import net.javenture.framework.util.Chain;
import net.javenture.framework.util.Validator;
import net.javenture.framework.script.AScriptFunction;
import net.javenture.framework.model.AModel;
import net.javenture.framework.model.BooleanModel;
import net.javenture.framework.vue.Vue;
import net.javenture.framework.vue.VueApp;
import net.javenture.framework.vue.VueEvent;
import net.javenture.framework.vue.VueRef;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;


/*
	2019/11/08
 */
abstract public class AHtmlElement<E extends AHtmlElement>
	implements IHtmlEntry
{
	// https://vuejs.org/v2/api/#v-bind
	// https://vuejs.org/v2/guide/class-and-style.html
	// XXX: vClass
	// XXX: vStyle



	//
	final private Attributes ATTRIBUTES;                                            // XXX: проверка на дублирование свойств
	final private Classes CLASSES;
	final private Styles STYLES;
	final private Childs CHILDS;

	final protected E THIS;

	private boolean activity;
	private boolean self;


	//
	@SuppressWarnings("unchecked")
	protected AHtmlElement()
	{
		ATTRIBUTES = new Attributes();
		CLASSES = new Classes();
		STYLES = new Styles();
		CHILDS = new Childs();
		THIS = (E) this;
		activity = true;
		self = true;
	}


	@SuppressWarnings("unchecked")
	protected AHtmlElement(@NullDisallow AHtmlElement parent)
	{
		Validator.notNull(parent, "[parent]");

		ATTRIBUTES = new Attributes();
		CLASSES = new Classes();
		STYLES = new Styles();
		CHILDS = new Childs();
		THIS = (E) this;
		activity = true;
		self = true;

		parent.CHILDS.add(this);
	}


	//
	abstract public Config config(); // ! public


	//
	@Override
	final public void toUtf8Stream(Utf8OutputStream destination)
		throws IOException
	{
		if (activity)
		{
			Config config = config();

			if (config.START && self)
			{
				destination.write(Utf8Byte.LESS_THAN);
				destination.write(config.TAG);
				destination.write(ATTRIBUTES);
				destination.write(CLASSES);
				destination.write(STYLES);
				destination.write(Utf8Byte.GREATER_THAN);
			}

			if (config.CHILDS) destination.write(CHILDS);

			if (config.END && self)
			{
				destination.write(Utf8Byte.LESS_THAN, Utf8Byte.SLASH);
				destination.write(config.TAG);
				destination.write(Utf8Byte.GREATER_THAN);
			}
		}
	}


	@Override
	final public String toString()
	{
		return Utf8Util.toString(this);
	}


	final public E activity(boolean value)
	{
		activity = value;

		return THIS;
	}


	final public E self(boolean value)
	{
		self = value;

		return THIS;
	}


	/*
		attribute
	 */
	final public E attribute(@NullDisallow HtmlAttribute attribute)
	{
		Validator.notNull(attribute, "[attribute]");
		ATTRIBUTES.add(attribute);

		return THIS;
	}


	final public E attribute(@NullDisallow HtmlAttribute.Template template)
	{
		ATTRIBUTES.add(template.ATTRIBUTE);

		return THIS;
	}


	final public E attribute(@NullDisallow HtmlAttribute.Preset preset)
	{
		ATTRIBUTES.add(preset.ATTRIBUTE);

		return THIS;
	}


	final public E attribute(@NullDisallow CharSequence name)
	{
		ATTRIBUTES.add(new HtmlAttribute(name));

		return THIS;
	}


	final public E attribute(HtmlAttribute.Template template, @NullDisallow IUtf8StreamableEntry value)
	{
		ATTRIBUTES.add(new HtmlAttribute(template, value));

		return THIS;
	}


	final public E attribute(HtmlAttribute.Template template, char value)
	{
		ATTRIBUTES.add(new HtmlAttribute(template, value));

		return THIS;
	}


	final public E attribute(HtmlAttribute.Template template, boolean value)
	{
		ATTRIBUTES.add(new HtmlAttribute(template, value));

		return THIS;
	}


	final public E attribute(HtmlAttribute.Template template, short value)
	{
		ATTRIBUTES.add(new HtmlAttribute(template, value));

		return THIS;
	}


	final public E attribute(HtmlAttribute.Template template, int value)
	{
		ATTRIBUTES.add(new HtmlAttribute(template, value));

		return THIS;
	}


	final public E attribute(HtmlAttribute.Template template, long value)
	{
		ATTRIBUTES.add(new HtmlAttribute(template, value));

		return THIS;
	}


	final public E attribute(HtmlAttribute.Template template, float value)
	{
		ATTRIBUTES.add(new HtmlAttribute(template, value));

		return THIS;
	}


	final public E attribute(HtmlAttribute.Template template, double value)
	{
		ATTRIBUTES.add(new HtmlAttribute(template, value));

		return THIS;
	}


	final public E attribute(HtmlAttribute.Template template, @NullDisallow Number value)
	{
		ATTRIBUTES.add(new HtmlAttribute(template, value));

		return THIS;
	}


	final public E attribute(HtmlAttribute.Template template, @NullDisallow CharSequence value)
	{
		ATTRIBUTES.add(new HtmlAttribute(template, value));

		return THIS;
	}


	final public E attribute(HtmlAttribute.Template template, @NullDisallow AModel value)
	{
		String alias = value.alias();
		ATTRIBUTES.add(new HtmlAttribute(template, alias));

		return THIS;
	}


	final public E attribute(@NullDisallow CharSequence name, @NullDisallow IUtf8StreamableEntry value)
	{
		ATTRIBUTES.add(new HtmlAttribute(name, value));

		return THIS;
	}


	final public E attribute(@NullDisallow CharSequence name, char value)
	{
		ATTRIBUTES.add(new HtmlAttribute(name, value));

		return THIS;
	}


	final public E attribute(@NullDisallow CharSequence name, boolean value)
	{
		ATTRIBUTES.add(new HtmlAttribute(name, value));

		return THIS;
	}


	final public E attribute(@NullDisallow CharSequence name, short value)
	{
		ATTRIBUTES.add(new HtmlAttribute(name, value));

		return THIS;
	}


	final public E attribute(@NullDisallow CharSequence name, int value)
	{
		ATTRIBUTES.add(new HtmlAttribute(name, value));

		return THIS;
	}


	final public E attribute(@NullDisallow CharSequence name, long value)
	{
		ATTRIBUTES.add(new HtmlAttribute(name, value));

		return THIS;
	}


	final public E attribute(@NullDisallow CharSequence name, float value)
	{
		ATTRIBUTES.add(new HtmlAttribute(name, value));

		return THIS;
	}


	final public E attribute(@NullDisallow CharSequence name, double value)
	{
		ATTRIBUTES.add(new HtmlAttribute(name, value));

		return THIS;
	}


	final public E attribute(@NullDisallow CharSequence name, @NullDisallow Number value)
	{
		ATTRIBUTES.add(new HtmlAttribute(name, value));

		return THIS;
	}


	final public E attribute(@NullDisallow CharSequence name, @NullDisallow CharSequence value)
	{
		ATTRIBUTES.add(new HtmlAttribute(name, value));

		return THIS;
	}


	final public E attribute(@NullDisallow CharSequence name, @NullDisallow AModel value)
	{
		String alias = value.alias();
		ATTRIBUTES.add(new HtmlAttribute(name, alias));

		return THIS;
	}


	final public E attribute(boolean condition, @NullDisallow HtmlAttribute attribute)
	{
		if (condition)
		{
			Validator.notNull(attribute, "[attribute]");
			ATTRIBUTES.add(attribute);
		}

		return THIS;
	}


	final public E attribute(boolean condition, @NullDisallow HtmlAttribute.Template template)
	{
		if (condition) ATTRIBUTES.add(template.ATTRIBUTE);

		return THIS;
	}


	final public E attribute(boolean condition, @NullDisallow HtmlAttribute.Preset preset)
	{
		if (condition) ATTRIBUTES.add(preset.ATTRIBUTE);

		return THIS;
	}


	final public E attribute(boolean condition, @NullDisallow CharSequence name)
	{
		if (condition) ATTRIBUTES.add(new HtmlAttribute(name));

		return THIS;
	}


	final public E attribute(boolean condition, @NullDisallow HtmlAttribute.Template template, @NullDisallow IUtf8StreamableEntry value)
	{
		if (condition) ATTRIBUTES.add(new HtmlAttribute(template, value));

		return THIS;
	}


	final public E attribute(boolean condition, @NullDisallow HtmlAttribute.Template template, char value)
	{
		if (condition) ATTRIBUTES.add(new HtmlAttribute(template, value));

		return THIS;
	}


	final public E attribute(boolean condition, @NullDisallow HtmlAttribute.Template template, boolean value)
	{
		if (condition) ATTRIBUTES.add(new HtmlAttribute(template, value));

		return THIS;
	}


	final public E attribute(boolean condition, @NullDisallow HtmlAttribute.Template template, short value)
	{
		if (condition) ATTRIBUTES.add(new HtmlAttribute(template, value));

		return THIS;
	}


	final public E attribute(boolean condition, @NullDisallow HtmlAttribute.Template template, int value)
	{
		if (condition) ATTRIBUTES.add(new HtmlAttribute(template, value));

		return THIS;
	}


	final public E attribute(boolean condition, @NullDisallow HtmlAttribute.Template template, long value)
	{
		if (condition) ATTRIBUTES.add(new HtmlAttribute(template, value));

		return THIS;
	}


	final public E attribute(boolean condition, @NullDisallow HtmlAttribute.Template template, float value)
	{
		if (condition) ATTRIBUTES.add(new HtmlAttribute(template, value));

		return THIS;
	}


	final public E attribute(boolean condition, @NullDisallow HtmlAttribute.Template template, double value)
	{
		if (condition) ATTRIBUTES.add(new HtmlAttribute(template, value));

		return THIS;
	}


	final public E attribute(boolean condition, @NullDisallow HtmlAttribute.Template template, @NullDisallow Number value)
	{
		if (condition) ATTRIBUTES.add(new HtmlAttribute(template, value));

		return THIS;
	}


	final public E attribute(boolean condition, @NullDisallow HtmlAttribute.Template template, @NullDisallow CharSequence value)
	{
		if (condition) ATTRIBUTES.add(new HtmlAttribute(template, value));

		return THIS;
	}


	final public E attribute(boolean condition, @NullDisallow HtmlAttribute.Template template, @NullDisallow AModel value)
	{
		if (condition)
		{
			String alias = value.alias();
			ATTRIBUTES.add(new HtmlAttribute(template, alias));
		}

		return THIS;
	}


	final public E attribute(boolean condition, @NullDisallow CharSequence name, @NullDisallow IUtf8StreamableEntry value)
	{
		if (condition) ATTRIBUTES.add(new HtmlAttribute(name, value));

		return THIS;
	}


	final public E attribute(boolean condition, @NullDisallow CharSequence name, char value)
	{
		if (condition) ATTRIBUTES.add(new HtmlAttribute(name, value));

		return THIS;
	}


	final public E attribute(boolean condition, @NullDisallow CharSequence name, boolean value)
	{
		if (condition) ATTRIBUTES.add(new HtmlAttribute(name, value));

		return THIS;
	}


	final public E attribute(boolean condition, @NullDisallow CharSequence name, short value)
	{
		if (condition) ATTRIBUTES.add(new HtmlAttribute(name, value));

		return THIS;
	}


	final public E attribute(boolean condition, @NullDisallow CharSequence name, int value)
	{
		if (condition) ATTRIBUTES.add(new HtmlAttribute(name, value));

		return THIS;
	}


	final public E attribute(boolean condition, @NullDisallow CharSequence name, long value)
	{
		if (condition) ATTRIBUTES.add(new HtmlAttribute(name, value));

		return THIS;
	}


	final public E attribute(boolean condition, @NullDisallow CharSequence name, float value)
	{
		if (condition) ATTRIBUTES.add(new HtmlAttribute(name, value));

		return THIS;
	}


	final public E attribute(boolean condition, @NullDisallow CharSequence name, double value)
	{
		if (condition) ATTRIBUTES.add(new HtmlAttribute(name, value));

		return THIS;
	}


	final public E attribute(boolean condition, @NullDisallow CharSequence name, @NullDisallow Number value)
	{
		if (condition) ATTRIBUTES.add(new HtmlAttribute(name, value));

		return THIS;
	}


	final public E attribute(boolean condition, @NullDisallow CharSequence name, @NullDisallow CharSequence value)
	{
		if (condition) ATTRIBUTES.add(new HtmlAttribute(name, value));

		return THIS;
	}


	final public E attribute(boolean condition, @NullDisallow CharSequence name, @NullDisallow AModel value)
	{
		if (condition)
		{
			String alias = value.alias();
			ATTRIBUTES.add(new HtmlAttribute(name, alias));
		}

		return THIS;
	}


	/*
		classes
	 */
	final public E cl(ICssClass value)
	{
		CLASSES.add(value);

		return THIS;
	}


	final public E cl(ICssClass... values)
	{
		CLASSES.add(values);

		return THIS;
	}


	final public E cl(CharSequence value)
	{
		CLASSES.add(value);

		return THIS;
	}


	final public E cl(CharSequence... values)
	{
		CLASSES.add(values);

		return THIS;
	}


	final public E cl(boolean condition, ICssClass value)
	{
		if (condition) CLASSES.add(value);

		return THIS;
	}


	final public E cl(boolean condition, ICssClass... values)
	{
		if (condition) CLASSES.add(values);

		return THIS;
	}


	final public E cl(boolean condition, CharSequence value)
	{
		if (condition) CLASSES.add(value);

		return THIS;
	}


	final public E cl(boolean condition, CharSequence... values)
	{
		if (condition) CLASSES.add(values);

		return THIS;
	}


	/*
		id
	 */
	final public E id(HtmlId value)
	{
		attribute(value.ATTRIBUTE);

		return THIS;
	}


	final public E id(CharSequence s)
	{
		attribute(HtmlId.TEMPLATE, s);

		return THIS;
	}


	/*
		child
	 */
	final public E child(IHtmlEntry entry)
	{
		CHILDS.add(entry);

		return THIS;
	}


	final public E child(IHtmlEntry... entries)
	{
		CHILDS.add(entries);

		return THIS;
	}


	final public E child(boolean condition, IHtmlEntry entry)
	{
		if (condition) CHILDS.add(entry);

		return THIS;
	}


	final public E child(boolean condition, IHtmlEntry... entries)
	{
		if (condition) CHILDS.add(entries);

		return THIS;
	}


	/*
		vue
	 */
	final public E vAttribute(HtmlAttribute.Template template, @NullDisallow AModel value)
	{
		ATTRIBUTES.add(new HtmlAttribute(true, template, value));

		return THIS;
	}


	final public E vAttribute(HtmlAttribute.Template template, boolean value)
	{
		ATTRIBUTES.add(new HtmlAttribute(true, template, value));

		return THIS;
	}


	final public E vAttribute(HtmlAttribute.Template template, short value)
	{
		ATTRIBUTES.add(new HtmlAttribute(true, template, value));

		return THIS;
	}


	final public E vAttribute(HtmlAttribute.Template template, int value)
	{
		ATTRIBUTES.add(new HtmlAttribute(true, template, value));

		return THIS;
	}


	final public E vAttribute(HtmlAttribute.Template template, long value)
	{
		ATTRIBUTES.add(new HtmlAttribute(true, template, value));

		return THIS;
	}


	final public E vAttribute(HtmlAttribute.Template template, float value)
	{
		ATTRIBUTES.add(new HtmlAttribute(true, template, value));

		return THIS;
	}


	final public E vAttribute(HtmlAttribute.Template template, double value)
	{
		ATTRIBUTES.add(new HtmlAttribute(true, template, value));

		return THIS;
	}


	final public E vAttribute(HtmlAttribute.Template template, @NullDisallow Number value)
	{
		ATTRIBUTES.add(new HtmlAttribute(true, template, value));

		return THIS;
	}


	final public E vAttribute(HtmlAttribute.Template template, @NullDisallow CharSequence value)
	{
		ATTRIBUTES.add(new HtmlAttribute(true, template, value));

		return THIS;
	}


	final public E vAttribute(@NullDisallow CharSequence name, AModel value)
	{
		ATTRIBUTES.add(new HtmlAttribute(true, name, value));

		return THIS;
	}


	final public E vAttribute(@NullDisallow CharSequence name, boolean value)
	{
		ATTRIBUTES.add(new HtmlAttribute(true, name, value));

		return THIS;
	}


	final public E vAttribute(@NullDisallow CharSequence name, short value)
	{
		ATTRIBUTES.add(new HtmlAttribute(true, name, value));

		return THIS;
	}


	final public E vAttribute(@NullDisallow CharSequence name, int value)
	{
		ATTRIBUTES.add(new HtmlAttribute(true, name, value));

		return THIS;
	}


	final public E vAttribute(@NullDisallow CharSequence name, long value)
	{
		ATTRIBUTES.add(new HtmlAttribute(true, name, value));

		return THIS;
	}


	final public E vAttribute(@NullDisallow CharSequence name, float value)
	{
		ATTRIBUTES.add(new HtmlAttribute(true, name, value));

		return THIS;
	}


	final public E vAttribute(@NullDisallow CharSequence name, double value)
	{
		ATTRIBUTES.add(new HtmlAttribute(true, name, value));

		return THIS;
	}


	final public E vAttribute(@NullDisallow CharSequence name, @NullDisallow Number value)
	{
		ATTRIBUTES.add(new HtmlAttribute(true, name, value));

		return THIS;
	}


	final public E vAttribute(@NullDisallow CharSequence name, @NullDisallow CharSequence value)
	{
		ATTRIBUTES.add(new HtmlAttribute(true, name, value));

		return THIS;
	}


	final public E vAttribute(boolean condition, @NullDisallow HtmlAttribute.Template template, AModel value)
	{
		if (condition) ATTRIBUTES.add(new HtmlAttribute(true, template, value));

		return THIS;
	}


	final public E vAttribute(boolean condition, @NullDisallow HtmlAttribute.Template template, boolean value)
	{
		if (condition) ATTRIBUTES.add(new HtmlAttribute(true, template, value));

		return THIS;
	}


	final public E vAttribute(boolean condition, @NullDisallow HtmlAttribute.Template template, short value)
	{
		if (condition) ATTRIBUTES.add(new HtmlAttribute(true, template, value));

		return THIS;
	}


	final public E vAttribute(boolean condition, @NullDisallow HtmlAttribute.Template template, int value)
	{
		if (condition) ATTRIBUTES.add(new HtmlAttribute(true, template, value));

		return THIS;
	}


	final public E vAttribute(boolean condition, @NullDisallow HtmlAttribute.Template template, long value)
	{
		if (condition) ATTRIBUTES.add(new HtmlAttribute(true, template, value));

		return THIS;
	}


	final public E vAttribute(boolean condition, @NullDisallow HtmlAttribute.Template template, float value)
	{
		if (condition) ATTRIBUTES.add(new HtmlAttribute(true, template, value));

		return THIS;
	}


	final public E vAttribute(boolean condition, @NullDisallow HtmlAttribute.Template template, double value)
	{
		if (condition) ATTRIBUTES.add(new HtmlAttribute(true, template, value));

		return THIS;
	}


	final public E vAttribute(boolean condition, @NullDisallow HtmlAttribute.Template template, @NullDisallow Number value)
	{
		if (condition) ATTRIBUTES.add(new HtmlAttribute(true, template, value));

		return THIS;
	}


	final public E vAttribute(boolean condition, @NullDisallow HtmlAttribute.Template template, @NullDisallow CharSequence value)
	{
		if (condition) ATTRIBUTES.add(new HtmlAttribute(true, template, value));

		return THIS;
	}


	final public E vAttribute(boolean condition, @NullDisallow CharSequence name, AModel value)
	{
		if (condition) ATTRIBUTES.add(new HtmlAttribute(true, name, value));

		return THIS;
	}


	final public E vAttribute(boolean condition, @NullDisallow CharSequence name, boolean value)
	{
		if (condition) ATTRIBUTES.add(new HtmlAttribute(true, name, value));

		return THIS;
	}


	final public E vAttribute(boolean condition, @NullDisallow CharSequence name, short value)
	{
		if (condition) ATTRIBUTES.add(new HtmlAttribute(true, name, value));

		return THIS;
	}


	final public E vAttribute(boolean condition, @NullDisallow CharSequence name, int value)
	{
		if (condition) ATTRIBUTES.add(new HtmlAttribute(true, name, value));

		return THIS;
	}


	final public E vAttribute(boolean condition, @NullDisallow CharSequence name, long value)
	{
		if (condition) ATTRIBUTES.add(new HtmlAttribute(true, name, value));

		return THIS;
	}


	final public E vAttribute(boolean condition, @NullDisallow CharSequence name, float value)
	{
		if (condition) ATTRIBUTES.add(new HtmlAttribute(true, name, value));

		return THIS;
	}


	final public E vAttribute(boolean condition, @NullDisallow CharSequence name, double value)
	{
		if (condition) ATTRIBUTES.add(new HtmlAttribute(true, name, value));

		return THIS;
	}


	final public E vAttribute(boolean condition, @NullDisallow CharSequence name, @NullDisallow Number value)
	{
		if (condition) ATTRIBUTES.add(new HtmlAttribute(true, name, value));

		return THIS;
	}


	final public E vAttribute(boolean condition, @NullDisallow CharSequence name, @NullDisallow CharSequence value)
	{
		if (condition) ATTRIBUTES.add(new HtmlAttribute(true, name, value));

		return THIS;
	}


	final public E vCl(@NullDisallow AModel value)                            // XXX: + boolean reference
	{
		CLASSES.add(value);

		return THIS;
	}


	final public E vCl(@NullDisallow ICssClass value, @NullDisallow BooleanModel model)
	{
		CLASSES.add(value, model);

		return THIS;
	}


	final public E vCl(@NullDisallow CharSequence value, @NullDisallow BooleanModel model)
	{
		CLASSES.add(value, model);

		return THIS;
	}


	final public E vRef(@NullDisallow VueRef value)
	{
		return attribute(Vue.HtmlAttributes.REF, value);
	}


	final public E vRef(@NullDisallow CharSequence value)
	{
		return attribute(Vue.HtmlAttributes.REF, value);
	}


	final public E vKey(@NullDisallow AModel value)
	{
		return vAttribute(Vue.HtmlAttributes.KEY, value);
	}


	final public E vKey(@NullDisallow CharSequence value)
	{
		Validator.notNull(value, "[value]");

		return attribute(Vue.HtmlAttributes.KEY, value);
	}


	final public E vText(AModel value)
	{
		return attribute(Vue.HtmlAttributes.V_TEXT, value);
	}


	final public E vHtml(AModel value)
	{
		return attribute(Vue.HtmlAttributes.V_HTML, value);
	}


	final public E vIf(AModel value)
	{
		return attribute(Vue.HtmlAttributes.V_IF, value);
	}


	final public E vIf(IUtf8StreamableEntry value)
	{
		return attribute(Vue.HtmlAttributes.V_IF, value);
	}


	final public E vIf(CharSequence value)
	{
		return attribute(Vue.HtmlAttributes.V_IF, value);
	}


	final public E vElseIf(AModel value)
	{
		return attribute(Vue.HtmlAttributes.V_ELSE_IF, value);
	}


	final public E vElseIf(IUtf8StreamableEntry value)
	{
		return attribute(Vue.HtmlAttributes.V_ELSE_IF, value);
	}


	final public E vElseIf(CharSequence value)
	{
		return attribute(Vue.HtmlAttributes.V_ELSE_IF, value);
	}


	final public E vElse()
	{
		return attribute(Vue.HtmlAttributes.V_ELSE);
	}


	final public E vFor(CharSequence value)
	{
		return attribute(Vue.HtmlAttributes.V_FOR, value);
	}


	final public E vShow(AModel value)
	{
		return attribute(Vue.HtmlAttributes.V_SHOW, value);
	}


	final public E vShow(IUtf8StreamableEntry value)
	{
		return attribute(Vue.HtmlAttributes.V_SHOW, value);
	}


	final public E vShow(CharSequence value)
	{
		return attribute(Vue.HtmlAttributes.V_SHOW, value);
	}


	final public E vOnce()
	{
		return attribute(Vue.HtmlAttributes.V_ONCE);
	}


	final public E vPre()
	{
		return attribute(Vue.HtmlAttributes.V_PRE);
	}


	final public E vBind(AModel value)                                                                      // XXX: AVueModel -> ScriptObject => ScriptObject.NAME ?
	{
		return attribute(Vue.HtmlAttributes.V_BIND, value);
	}


	final public E vModel(AModel value)
	{
		return attribute(Vue.HtmlAttributes.V_MODEL, value);
	}


	final public E vModel(VueApp app, AModel model)
	{
		app.data(model);

		return attribute(Vue.HtmlAttributes.V_MODEL, model);
	}


	final public <V> E vModel(VueApp app, AModel<V> model, V value)
	{
		app.data(model, value);

		return attribute(Vue.HtmlAttributes.V_MODEL, model);
	}


	final public E vModel(AScriptFunction value)
	{
		AScriptFunction.Invoke invoke = value.invoke();
		invoke.parenthesis(false);

		return attribute(Vue.HtmlAttributes.V_MODEL, invoke);
	}


	final public E vModel(CharSequence value)
	{
		return attribute(Vue.HtmlAttributes.V_MODEL, value);
	}


	final public E vEvent(VueEvent event)
	{
		return attribute(event.value());
	}


	final public E vEvent(VueEvent event, IUtf8StreamableEntry value)
	{
		return attribute(event.value(), value);
	}


	final public E vEvent(VueEvent event, AScriptFunction function)
	{
		return attribute(event.value(), function.invoke());
	}


	final public E vEvent(VueEvent event, AScriptFunction.Invoke invoke)
	{
		return attribute(event.value(), invoke);
	}


	final public E vEvent(VueEvent event, AScriptFunction.Invoke... invokes)
	{
		if (invokes.length != 0)
		{
			HtmlAttribute attribute = new HtmlAttribute(event.value());

			for (AScriptFunction.Invoke invoke : invokes)
			{
				attribute.delimiter(HtmlAttribute.Delimiter.SPACE);
				attribute.value(invoke);
			}

			attribute(attribute);
		}

		return THIS;
	}


	final public E vEvent(VueEvent event, VueRef ref, ScriptMethod method)
	{
		return attribute(event.value(), ref.invoke(method));
	}


	final public E vEvent(VueEvent event, @NullDisallow CharSequence value)
	{
		return attribute(event.value(), value);
	}


	final public E vEvent(boolean condition, VueEvent event)
	{
		return attribute(condition, event.value());
	}


	final public E vEvent(boolean condition, VueEvent event, AScriptFunction function)
	{
		return attribute(condition, event.value(), function.invoke());
	}


	final public E vEvent(boolean condition, VueEvent event, AScriptFunction.Invoke invoke)
	{
		return attribute(condition, event.value(), invoke);
	}


	final public E vEvent(boolean condition, VueEvent event, AScriptFunction.Invoke... invokes)
	{
		if (condition && invokes.length != 0)
		{
			HtmlAttribute attribute = new HtmlAttribute(event.value());

			for (AScriptFunction.Invoke invoke : invokes)
			{
				attribute.delimiter(HtmlAttribute.Delimiter.SPACE);
				attribute.value(invoke);
			}

			attribute(attribute);
		}

		return THIS;
	}


	final public E vEvent(boolean condition, VueEvent event, VueRef ref, ScriptMethod method)
	{
		return attribute(condition, event.value(), ref.invoke(method));
	}


	final public E vEvent(boolean condition, VueEvent event, @NullDisallow CharSequence value)
	{
		return attribute(condition, event.value(), value);
	}


	//
	public static AHtmlElement create(@NullDisallow Config config)
	{
		Validator.notNull(config, "[config]");

		return new AHtmlElement()
		{
			//
			@Override
			public Config config()
			{
				return config;
			}
		};
	}


	public static AHtmlElement create(@NullDisallow Config config, @NullDisallow AHtmlElement parent)
	{
		Validator.notNull(config, "[config]");

		return new AHtmlElement(parent)
		{
			//
			@Override
			public Config config()
			{
				return config;
			}
		};
	}


	public static AHtmlElement create(@NullDisallow Type type, @NullDisallow String name)
	{
		return new AHtmlElement()
		{
			final private Config CONFIG = new Config(type, name);

			//
			@Override
			public Config config()
			{
				return CONFIG;
			}
		};
	}


	public static AHtmlElement create(@NullDisallow Type type, @NullDisallow String name, @NullDisallow AHtmlElement parent)
	{
		return new AHtmlElement(parent)
		{
			final private Config CONFIG = new Config(type, name);

			//
			@Override
			public Config config()
			{
				return CONFIG;
			}
		};
	}


	//
	public enum Type
	{
		CONTAINER,
		PAIR,
		SINGLE
	}


	final public static class Config
	{
		//
		final private static AtomicInteger GENERATOR_ID = new AtomicInteger(0);

		//
		final private int ID;
		final private Type TYPE;
		final private HtmlTag TAG;
		final private boolean START;
		final private boolean CHILDS;
		final private boolean END;

		//
		public Config(@NullDisallow Type type)
		{
			this(type, HtmlTag.BLANK);
		}

		public Config(@NullDisallow Type type, @NullDisallow String name)
		{
			this(type, new HtmlTag(name));
		}

		private Config(@NullDisallow Type type, @NullDisallow HtmlTag tag)
		{
			Validator.notNull(type, "[type]");
			Validator.notNull(tag, "[tag]");

			//
			boolean start;
			boolean childs;
			boolean end;

			switch (type)
			{
				case CONTAINER:
				{
					start = false;
					childs = true;
					end = false;

					break;
				}

				case PAIR:
				{
					start = true;
					childs = true;
					end = true;

					break;
				}

				case SINGLE:
				{
					start = true;
					childs = false;
					end = false;

					break;
				}

				default: throw new UnsupportedOperationException("[Type] (" + type + ") is unknown");
			}

			//
			ID = GENERATOR_ID.getAndIncrement();
			TYPE = type;
			TAG = tag;
			START = start;
			CHILDS = childs;
			END = end;
		}

		//
		public int id()
		{
			return ID;
		}

		public Type type()
		{
			return TYPE;
		}

		public HtmlTag tag()
		{
			return TAG;
		}
	}


	final private static class Attributes
		implements IUtf8StreamableEntry
	{
		//
		final private Chain<HtmlAttribute> CHAIN;

		//
		private Attributes()
		{
			CHAIN = new Chain<>();
		}

		//
		@Override
		public void toUtf8Stream(Utf8OutputStream destination)
			throws IOException
		{
			if (CHAIN.exists())
			{
				for (HtmlAttribute attribute : CHAIN)
				{
					destination.write(Utf8Byte.SPACE);
					destination.write(attribute);
				}
			}
		}

		private void add(HtmlAttribute attribute)
		{
			CHAIN.add(attribute);
		}
	}


	final private static class Classes
		implements IUtf8StreamableEntry
	{
		//
		final private Chain<Entry> CHAIN;

		private boolean vue;

		//
		private Classes()
		{
			CHAIN = new Chain<>();
			vue = false;
		}

		//
		@Override
		public void toUtf8Stream(Utf8OutputStream destination)
			throws IOException
		{
			if (CHAIN.exists())
			{
				if (vue)
				{
/*
					if () // reference
					{

					}
					else if () // object
					{

					}
					else
*/



					{
						destination.write(Utf8Byte.SPACE, Utf8Byte.COLON, Utf8Byte.C_SMALL, Utf8Byte.L_SMALL, Utf8Byte.A_SMALL, Utf8Byte.S_SMALL, Utf8Byte.S_SMALL, Utf8Byte.EQUAL, Utf8Byte.QUOTATION, Utf8Byte.LEFT_SQUARE_BRACKET);
						int i = 0;

						for (Entry entry : CHAIN)
						{
							if (i++ != 0) destination.write(Utf8Byte.COMMA, Utf8Byte.SPACE);

							if (entry.TYPE == VueType.NONE)
							{
								destination.write(Utf8Byte.APOSTROPHE);
								destination.write(entry.VALUE);
								destination.write(Utf8Byte.APOSTROPHE);
							}
							else
							{
								destination.write(entry.VALUE);
							}
						}

						destination.write(Utf8Byte.RIGHT_SQUARE_BRACKET, Utf8Byte.QUOTATION);
					}
				}
				else
				{
					destination.write(Utf8Byte.SPACE, Utf8Byte.C_SMALL, Utf8Byte.L_SMALL, Utf8Byte.A_SMALL, Utf8Byte.S_SMALL, Utf8Byte.S_SMALL, Utf8Byte.EQUAL, Utf8Byte.QUOTATION);
					int i = 0;

					for (Entry entry : CHAIN)
					{
						if (i++ != 0) destination.write(Utf8Byte.SPACE);

						destination.write(entry.VALUE);
					}

					destination.write(Utf8Byte.QUOTATION);
				}
			}
		}

		private void add(ICssClass value)
		{
			CHAIN.add(new Entry(value));
		}

		private void add(ICssClass... values)
		{
			for (ICssClass value : values) CHAIN.add(new Entry(value));
		}

		private void add(CharSequence value)
		{
			if (value.length() != 0) CHAIN.add(new Entry(value));
		}

		private void add(CharSequence... values)
		{
			for (CharSequence value : values)
			{
				if (value.length() != 0) CHAIN.add(new Entry(value));
			}
		}

/*
		private void add(boolean vue, CharSequence value)
		{
			if (value.length() != 0) CHAIN.add(new Entry(value, vue));

			if (vue) this.vue = true;
		}

		private void add(boolean vue, CharSequence... values)
		{
			for (CharSequence value : values)
			{
				if (value.length() != 0) CHAIN.add(new Entry(value, vue));
			}

			if (vue) this.vue = true;
		}
*/





		private void add(ICssClass value, BooleanModel model)
		{
			CHAIN.add(new Entry(value, model));
			vue = true;
		}

		private void add(CharSequence value, BooleanModel model)
		{
			CHAIN.add(new Entry(value, model));
			vue = true;
		}

		private void add(AModel value)
		{
			CHAIN.add(new Entry(value));
			vue = true;
		}

		private void add(AModel... values)
		{
			for (AModel value : values) CHAIN.add(new Entry(value));

			vue = true;
		}

		//
		final private static class Entry
		{
			//
			final private @NullDisallow VueType TYPE;
			final private @NullDisallow IUtf8StreamableEntry VALUE;

			//
			private Entry(ICssClass cl)
			{
				TYPE = VueType.NONE;
				VALUE = cl;
			}

/*
			private Entry(IUtf8StreamableEntry value, boolean vue)
			{
				if (vue)
				{
					TYPE = VueType.REFERENCE;
					CLASS = value;
					MODEL = null;
				}
				else
				{
					TYPE = VueType.UNKNOWN;
					CLASS = value;
					MODEL = null;
				}

			}
*/

			private Entry(CharSequence cl)
			{
				TYPE = VueType.NONE;
				VALUE = destination -> destination.write(cl);
			}

/*
			private Entry(CharSequence cl, boolean vue)
			{
				TYPE = VueType.REFERENCE;
				CLASS = destination -> destination.write(cl);
				MODEL = null;
			}
*/

			private Entry(AModel model)
			{
				TYPE = VueType.REFERENCE;
				VALUE = destination -> destination.write(model.alias());
			}

			private Entry(ICssClass cl, BooleanModel model)
			{
				TYPE = VueType.OBJECT;

				VALUE = destination ->
				{
					destination.write(Utf8Byte.LEFT_CURLY_BRACKET, Utf8Byte.SPACE, Utf8Byte.APOSTROPHE);
					destination.write(cl);
					destination.write(Utf8Byte.APOSTROPHE, Utf8Byte.COMMA, Utf8Byte.SPACE);
					destination.write(model.alias());
					destination.write(Utf8Byte.SPACE, Utf8Byte.RIGHT_CURLY_BRACKET);
				};
			}

			private Entry(CharSequence cl, BooleanModel model)
			{
				TYPE = VueType.OBJECT;

				VALUE = destination ->
				{
					destination.write(Utf8Byte.LEFT_CURLY_BRACKET, Utf8Byte.SPACE, Utf8Byte.APOSTROPHE);
					destination.write(cl);
					destination.write(Utf8Byte.APOSTROPHE, Utf8Byte.COMMA, Utf8Byte.SPACE);
					destination.write(model.alias());
					destination.write(Utf8Byte.SPACE, Utf8Byte.RIGHT_CURLY_BRACKET);
				};
			}
		}

		enum VueType
		{
			NONE,
			REFERENCE,
			OBJECT
		}
	}


	final private static class Styles
		implements IUtf8StreamableEntry
	{
		//
		final private Chain<Entry> CHAIN;

		//
		private Styles()
		{
			CHAIN = new Chain<>();
		}

		//
		@Override
		public void toUtf8Stream(Utf8OutputStream destination)
			throws IOException
		{
			if (CHAIN.exists())
			{
				// XXX: ?


			}
		}

		//
		final private static class Entry
		{
		}
	}


	final private static class Childs
		implements IUtf8StreamableEntry
	{
		//
		final private Chain<IHtmlEntry> CHAIN;

		//
		private Childs()
		{
			CHAIN = new Chain<>();
		}

		//
		@Override
		public void toUtf8Stream(Utf8OutputStream destination)
			throws IOException
		{
			if (CHAIN.exists())
			{
				for (IHtmlEntry entry : CHAIN) destination.write(entry);
			}
		}

		private void add(IHtmlEntry entry)
		{
			CHAIN.add(entry);
		}

		private void add(IHtmlEntry... entries)
		{
			CHAIN.add(entries);
		}
	}

}
