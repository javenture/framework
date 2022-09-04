package net.javenture.framework.quasar.element;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.IFactoryObject;
import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;
import net.javenture.framework.json.JsonObject;
import net.javenture.framework.netty.NettyParameters;
import net.javenture.framework.quasar.AQuasarElement;
import net.javenture.framework.quasar.QuasarTheme;
import net.javenture.framework.script.IScriptEntry;
import net.javenture.framework.script.ScriptArray;
import net.javenture.framework.script.ScriptObject;
import net.javenture.framework.script.ScriptProperty;
import net.javenture.framework.util.Chain;
import net.javenture.framework.util.IConst;
import net.javenture.framework.util.IParser;
import net.javenture.framework.util.Validator;
import net.javenture.framework.model.AModel;


/*
	2019/11/22
 */
final public class QuasarSelect
	extends AQuasarElement<QuasarSelect>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "q-select");


	//
	public QuasarSelect()
	{
	}


	public QuasarSelect(AHtmlElement parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	@Override
	public void theme(QuasarTheme value)
	{
		// XXX


		throw new UnsupportedOperationException();
	}


	public QuasarSelect options(AModel model)
	{
		return vAttribute(Attributes.OPTIONS, model);
	}


	public QuasarSelect dark()
	{
		return attribute(Attributes.DARK);
	}


	public QuasarSelect dark(boolean condition)
	{
		return attribute(condition, Attributes.DARK);
	}


	public QuasarSelect filled()
	{
		return attribute(Attributes.FILLED);
	}


	public QuasarSelect filled(boolean condition)
	{
		return attribute(condition, Attributes.FILLED);
	}


	public QuasarSelect outlined()
	{
		return attribute(Attributes.OUTLINED);
	}


	public QuasarSelect outlined(boolean condition)
	{
		return attribute(condition, Attributes.OUTLINED);
	}


	public QuasarSelect borderless()
	{
		return attribute(Attributes.BORDERLESS);
	}


	public QuasarSelect borderless(boolean condition)
	{
		return attribute(condition, Attributes.BORDERLESS);
	}


	public QuasarSelect standout()
	{
		return attribute(Attributes.STANDOUT);
	}


	public QuasarSelect standout(boolean condition)
	{
		return attribute(condition, Attributes.STANDOUT);
	}


	public QuasarSelect rounded()
	{
		return attribute(Attributes.ROUNDED);
	}


	public QuasarSelect rounded(boolean condition)
	{
		return attribute(condition, Attributes.ROUNDED);
	}


	public QuasarSelect square()
	{
		return attribute(Attributes.SQUARE);
	}


	public QuasarSelect square(boolean condition)
	{
		return attribute(condition, Attributes.SQUARE);
	}


	public QuasarSelect multiple()
	{
		return attribute(Attributes.MULTIPLE);
	}


	public QuasarSelect multiple(boolean condition)
	{
		return attribute(condition, Attributes.MULTIPLE);
	}


	public QuasarSelect radio()
	{
		return attribute(Attributes.RADIO);
	}


	public QuasarSelect radio(boolean condition)
	{
		return attribute(condition, Attributes.RADIO);
	}


	public QuasarSelect toggle()
	{
		return attribute(Attributes.TOGGLE);
	}


	public QuasarSelect toggle(boolean condition)
	{
		return attribute(condition, Attributes.TOGGLE);
	}


	public QuasarSelect chips()
	{
		return attribute(Attributes.CHIPS);
	}


	public QuasarSelect chips(boolean condition)
	{
		return attribute(condition, Attributes.CHIPS);
	}


	public QuasarSelect separator()
	{
		return attribute(Attributes.SEPARATOR);
	}


	public QuasarSelect separator(boolean condition)
	{
		return attribute(condition, Attributes.SEPARATOR);
	}


	public QuasarSelect clearable()
	{
		return attribute(Attributes.CLEARABLE);
	}


	public QuasarSelect clearable(boolean condition)
	{
		return attribute(condition, Attributes.CLEARABLE);
	}


/*
	public QuasarSelect clearValue(AVueModel model)
	{
		vAttribute(VueAttributes.TEMPLATE_CLEAR_VALUE, model);

		return this;
	}
*/


	public QuasarSelect label(String value)
	{
		return attribute(Attributes.LABEL, value);
	}


	public QuasarSelect stackLabel()
	{
		return attribute(Attributes.STACK_LABEL);
	}


	public QuasarSelect stackLabel(boolean condition)
	{
		return attribute(condition, Attributes.STACK_LABEL);
	}


	public QuasarSelect disable()
	{
		return attribute(Attributes.DISABLE);
	}


	public QuasarSelect disable(boolean condition)
	{
		return attribute(condition, Attributes.DISABLE);
	}


	public QuasarSelect error()
	{
		return attribute(Attributes.ERROR);
	}


	public QuasarSelect error(boolean condition)
	{
		return attribute(condition, Attributes.ERROR);
	}


	public QuasarSelect error(AModel<? extends Boolean> model)
	{
		return vAttribute(Attributes.ERROR, model);
	}


	public QuasarSelect noErrorIcon()
	{
		return attribute(Attributes.NO_ERROR_ICON);
	}


	public QuasarSelect noErrorIcon(boolean condition)
	{
		return attribute(condition, Attributes.NO_ERROR_ICON);
	}


	public QuasarSelect emitValue()
	{
		return attribute(Attributes.EMIT_VALUE);
	}


	public QuasarSelect emitValue(boolean condition)
	{
		return attribute(condition, Attributes.EMIT_VALUE);
	}


	public QuasarSelect mapOptions()
	{
		return attribute(Attributes.MAP_OPTIONS);
	}


	public QuasarSelect mapOptions(boolean condition)
	{
		return attribute(condition, Attributes.MAP_OPTIONS);
	}


	public QuasarSelect optionsDark()
	{
		return attribute(Attributes.OPTIONS_DARK);
	}


	public QuasarSelect optionsDark(boolean condition)
	{
		return attribute(condition, Attributes.OPTIONS_DARK);
	}


	public QuasarSelect hideBottomSpace()
	{
		return attribute(Attributes.HIDE_BOTTOM_SPACE);
	}


	public QuasarSelect hideBottomSpace(boolean condition)
	{
		return attribute(condition, Attributes.HIDE_BOTTOM_SPACE);
	}


	//
	final public static class Options
	{
		//
		final private Chain<Option> CHAIN;

		//
		public Options()
		{
			CHAIN = new Chain<>();
		}

		//
		public Options add(boolean value)
		{
			new Option(this, value);

			return this;
		}

		public Options add(short value)
		{
			new Option(this, value);

			return this;
		}

		public Options add(int value)
		{
			new Option(this, value);

			return this;
		}

		public Options add(long value)
		{
			new Option(this, value);

			return this;
		}

		public Options add(float value)
		{
			new Option(this, value);

			return this;
		}

		public Options add(double value)
		{
			new Option(this, value);

			return this;
		}

		public Options add(@NullDisallow Number value)
		{
			new Option(this, value);

			return this;
		}

		public Options add(@NullDisallow CharSequence value)
		{
			new Option(this, value);

			return this;
		}

		public Options add(@NullDisallow IConst value)
		{
			new Option(this, value);

			return this;
		}

		public Options add(@NullDisallow IFactoryObject value)
		{
			new Option(this, value);

			return this;
		}

		public Options add(boolean value, @NullDisallow String label)
		{
			new Option(this, value, label);

			return this;
		}

		public Options add(short value, @NullDisallow String label)
		{
			new Option(this, value, label);

			return this;
		}

		public Options add(int value, @NullDisallow String label)
		{
			new Option(this, value, label);

			return this;
		}

		public Options add(long value, @NullDisallow String label)
		{
			new Option(this, value, label);

			return this;
		}

		public Options add(float value, @NullDisallow String label)
		{
			new Option(this, value, label);

			return this;
		}

		public Options add(double value, @NullDisallow String label)
		{
			new Option(this, value, label);

			return this;
		}

		public Options add(@NullDisallow Number value, @NullDisallow String label)
		{
			new Option(this, value, label);

			return this;
		}

		public Options add(@NullDisallow CharSequence value, @NullDisallow String label)
		{
			new Option(this, value, label);

			return this;
		}

		public Options add(@NullDisallow IConst value, @NullDisallow String label)
		{
			new Option(this, value, label);

			return this;
		}

		public Options add(@NullDisallow IFactoryObject value, @NullDisallow String label)
		{
			new Option(this, value, label);

			return this;
		}

		public Options add(boolean condition, boolean value, @NullDisallow String label)
		{
			if (condition) new Option(this, value, label);

			return this;
		}

		public Options add(boolean condition, short value, @NullDisallow String label)
		{
			if (condition) new Option(this, value, label);

			return this;
		}

		public Options add(boolean condition, int value, @NullDisallow String label)
		{
			if (condition) new Option(this, value, label);

			return this;
		}

		public Options add(boolean condition, long value, @NullDisallow String label)
		{
			if (condition) new Option(this, value, label);

			return this;
		}

		public Options add(boolean condition, float value, @NullDisallow String label)
		{
			if (condition) new Option(this, value, label);

			return this;
		}

		public Options add(boolean condition, double value, @NullDisallow String label)
		{
			if (condition) new Option(this, value, label);

			return this;
		}

		public Options add(boolean condition, @NullDisallow Number value, @NullDisallow String label)
		{
			if (condition) new Option(this, value, label);

			return this;
		}

		public Options add(boolean condition, @NullDisallow CharSequence value, @NullDisallow String label)
		{
			if (condition) new Option(this, value, label);

			return this;
		}

		public Options add(boolean condition, @NullDisallow IConst value, @NullDisallow String label)
		{
			if (condition) new Option(this, value, label);

			return this;
		}

		public Options add(boolean condition, @NullDisallow IFactoryObject value, @NullDisallow String label)
		{
			if (condition) new Option(this, value, label);

			return this;
		}

		public Options addInit(AModel model, @NullDisallow String label)
		{
			new Option(this, model, label);

			return this;
		}

		public Options addList(String... values)
		{
			for (String value : values) new Option(this, value);

			return this;
		}

		public Options addRange(int min, int max)
		{
			for (int value = min; value <= max; value++) new Option(this, value);

			return this;
		}

		public Options addRange(int min, int max, IIndexedLabel label)
		{
			for (int value = min; value <= max; value++) new Option(this, value, label.get(value));

			return this;
		}

		private void add(@NullDisallow Option option) // ! private
		{
			CHAIN.add(option);
		}

		//
		final public static class Model
			extends AModel<Options>
		{
			//
			final private static String SUFFIX = "$OPTIONS";

			//
			final private AModel MODEL;

			//
			public Model(AModel<?> model)                                                                 // XXX: suffix
			{
				super(model.name() + SUFFIX, model.objects());

				MODEL = model;
			}

			@Override
			public boolean nullable()
			{
				return MODEL.nullable();
			}

			@Override
			public boolean quotation()
			{
				return MODEL.quotation();
			}

			@Override
			public boolean trim()
			{
				return MODEL.trim();
			}

			@Override
			public Options init()
			{
				return new Options();
			}

			@Override
			public @NullDisallow IScriptEntry toScriptEntry()
			{
				return new ScriptArray();
			}

			@Override
			public @NullDisallow IScriptEntry toScriptEntry(@NullDisallow Options value)
			{
				Validator.notNull(value, "[value]");
				// XXX: nullable
				boolean quotation = MODEL.quotation();
				ScriptArray array = new ScriptArray();

				for (Option option : value.CHAIN)
				{
					ScriptObject object = new ScriptObject();
					array.add(object);

					for (IOptionProperty property : option.CHAIN) object.add(property.get(quotation));
				}

				return array;
			}

			@Override
			public void toNettyParameter(NettyParameters destination)
			{
				throw new UnsupportedOperationException();
			}

			@Override
			public void toNettyParameter(Options value, NettyParameters destination)
			{
				throw new UnsupportedOperationException();
			}

			@Override
			public IParser.Report<Options> parse(CharSequence source)
			{
				throw new UnsupportedOperationException();
			}

			@Override
			public IParser.Report<Options> parse(@NullAllow JsonObject source)
			{
				throw new UnsupportedOperationException();
			}

			@Override
			public boolean defined(Options value)
			{
				throw new UnsupportedOperationException();
			}
		}
	}


	final public static class Option
	{
		//
		final private static String PROPERTY_VALUE = "value";
		final private static String PROPERTY_LABEL = "label";
		final private static String PROPERTY_DISABLE = "disable";

		//
		final private Chain<IOptionProperty> CHAIN;

		//
		public Option(Options options, boolean value)
		{
			CHAIN = new Chain<>();
			CHAIN.add(quotation -> new ScriptProperty(PROPERTY_VALUE, value, quotation));
			CHAIN.add(quotation -> new ScriptProperty(PROPERTY_LABEL, value, true));

			options.add(this);
		}

		public Option(Options options, short value)
		{
			CHAIN = new Chain<>();
			CHAIN.add(quotation -> new ScriptProperty(PROPERTY_VALUE, value, quotation));
			CHAIN.add(quotation -> new ScriptProperty(PROPERTY_LABEL, value, true));

			options.add(this);
		}

		public Option(Options options, int value)
		{
			CHAIN = new Chain<>();
			CHAIN.add(quotation -> new ScriptProperty(PROPERTY_VALUE, value, quotation));
			CHAIN.add(quotation -> new ScriptProperty(PROPERTY_LABEL, value, true));

			options.add(this);
		}

		public Option(Options options, long value)
		{
			CHAIN = new Chain<>();
			CHAIN.add(quotation -> new ScriptProperty(PROPERTY_VALUE, value, quotation));
			CHAIN.add(quotation -> new ScriptProperty(PROPERTY_LABEL, value, true));

			options.add(this);
		}

		public Option(Options options, float value)
		{
			CHAIN = new Chain<>();
			CHAIN.add(quotation -> new ScriptProperty(PROPERTY_VALUE, value, quotation));
			CHAIN.add(quotation -> new ScriptProperty(PROPERTY_LABEL, value, true));

			options.add(this);
		}

		public Option(Options options, double value)
		{
			CHAIN = new Chain<>();
			CHAIN.add(quotation -> new ScriptProperty(PROPERTY_VALUE, value, quotation));
			CHAIN.add(quotation -> new ScriptProperty(PROPERTY_LABEL, value, true));

			options.add(this);
		}

		public Option(Options options, @NullDisallow Number value)
		{
			Validator.notNull(value, "[value]");

			CHAIN = new Chain<>();
			CHAIN.add(quotation -> new ScriptProperty(PROPERTY_VALUE, value, quotation));
			CHAIN.add(quotation -> new ScriptProperty(PROPERTY_LABEL, value, true));

			options.add(this);
		}

		public Option(Options options, @NullDisallow CharSequence value)
		{
			Validator.notNull(value, "[value]");

			CHAIN = new Chain<>();
			CHAIN.add(quotation -> new ScriptProperty(PROPERTY_VALUE, value, quotation));
			CHAIN.add(quotation -> new ScriptProperty(PROPERTY_LABEL, value, true));

			options.add(this);
		}

		public Option(Options options, @NullDisallow IConst value)
		{
			Validator.notNull(value, "[value]");

			CHAIN = new Chain<>();
			CHAIN.add(quotation -> new ScriptProperty(PROPERTY_VALUE, value, quotation));
			CHAIN.add(quotation -> new ScriptProperty(PROPERTY_LABEL, value, true));

			options.add(this);
		}

		public Option(Options options, @NullDisallow IFactoryObject value)
		{
			Validator.notNull(value, "[value]");

			CHAIN = new Chain<>();
			CHAIN.add(quotation -> new ScriptProperty(PROPERTY_VALUE, value, quotation));
			CHAIN.add(quotation -> new ScriptProperty(PROPERTY_LABEL, value, true));

			options.add(this);
		}

		public Option(Options options, boolean value, @NullDisallow String label)
		{
			Validator.notNull(label, "[label]");

			CHAIN = new Chain<>();
			CHAIN.add(quotation -> new ScriptProperty(PROPERTY_VALUE, value, quotation));
			CHAIN.add(quotation -> new ScriptProperty(PROPERTY_LABEL, label, true));

			options.add(this);
		}

		public Option(Options options, short value, @NullDisallow String label)
		{
			Validator.notNull(label, "[label]");

			CHAIN = new Chain<>();
			CHAIN.add(quotation -> new ScriptProperty(PROPERTY_VALUE, value, quotation));
			CHAIN.add(quotation -> new ScriptProperty(PROPERTY_LABEL, label, true));

			options.add(this);
		}

		public Option(Options options, int value, @NullDisallow String label)
		{
			Validator.notNull(label, "[label]");

			CHAIN = new Chain<>();
			CHAIN.add(quotation -> new ScriptProperty(PROPERTY_VALUE, value, quotation));
			CHAIN.add(quotation -> new ScriptProperty(PROPERTY_LABEL, label, true));

			options.add(this);
		}

		public Option(Options options, long value, @NullDisallow String label)
		{
			Validator.notNull(label, "[label]");

			CHAIN = new Chain<>();
			CHAIN.add(quotation -> new ScriptProperty(PROPERTY_VALUE, value, quotation));
			CHAIN.add(quotation -> new ScriptProperty(PROPERTY_LABEL, label, true));

			options.add(this);
		}

		public Option(Options options, float value, @NullDisallow String label)
		{
			Validator.notNull(label, "[label]");

			CHAIN = new Chain<>();
			CHAIN.add(quotation -> new ScriptProperty(PROPERTY_VALUE, value, quotation));
			CHAIN.add(quotation -> new ScriptProperty(PROPERTY_LABEL, label, true));

			options.add(this);
		}

		public Option(Options options, double value, @NullDisallow String label)
		{
			Validator.notNull(label, "[label]");

			CHAIN = new Chain<>();
			CHAIN.add(quotation -> new ScriptProperty(PROPERTY_VALUE, value, quotation));
			CHAIN.add(quotation -> new ScriptProperty(PROPERTY_LABEL, label, true));

			options.add(this);
		}

		public Option(Options options, @NullDisallow Number value, @NullDisallow String label)
		{
			Validator.notNull(value, "[value]");
			Validator.notNull(label, "[label]");

			CHAIN = new Chain<>();
			CHAIN.add(quotation -> new ScriptProperty(PROPERTY_VALUE, value, quotation));
			CHAIN.add(quotation -> new ScriptProperty(PROPERTY_LABEL, label, true));

			options.add(this);
		}

		public Option(Options options, @NullDisallow CharSequence value, @NullDisallow String label)
		{
			Validator.notNull(value, "[value]");
			Validator.notNull(label, "[label]");

			CHAIN = new Chain<>();
			CHAIN.add(quotation -> new ScriptProperty(PROPERTY_VALUE, value, quotation));
			CHAIN.add(quotation -> new ScriptProperty(PROPERTY_LABEL, label, true));

			options.add(this);
		}

		public Option(Options options, @NullDisallow IConst value, @NullDisallow String label)
		{
			Validator.notNull(value, "[value]");
			Validator.notNull(label, "[label]");

			CHAIN = new Chain<>();
			CHAIN.add(quotation -> new ScriptProperty(PROPERTY_VALUE, value, quotation));
			CHAIN.add(quotation -> new ScriptProperty(PROPERTY_LABEL, label, true));

			options.add(this);
		}

		public Option(Options options, @NullDisallow IFactoryObject value, @NullDisallow String label)
		{
			Validator.notNull(value, "[value]");
			Validator.notNull(label, "[label]");

			CHAIN = new Chain<>();
			CHAIN.add(quotation -> new ScriptProperty(PROPERTY_VALUE, value, quotation));
			CHAIN.add(quotation -> new ScriptProperty(PROPERTY_LABEL, label, true));

			options.add(this);
		}

		public Option(Options options, @NullDisallow AModel model, @NullDisallow String label)
		{
			Validator.notNull(label, "[label]");
			IScriptEntry entry = model.toScriptEntry();

			CHAIN = new Chain<>();
			CHAIN.add(quotation -> new ScriptProperty(PROPERTY_VALUE, entry));
			CHAIN.add(quotation -> new ScriptProperty(PROPERTY_LABEL, label, true));

			options.add(this);
		}

		//
		public Option disable()
		{
			return disable(true);
		}

		public Option disable(boolean value)
		{
			CHAIN.add(quotation -> new ScriptProperty(PROPERTY_DISABLE, value, false));

			return this;
		}
	}


	@FunctionalInterface
	private interface IOptionProperty
	{
		ScriptProperty get(boolean quotation);                                                                           // XXX: nullable ?
	}


	//
	final public static class Attributes
	{
		final public static HtmlAttribute.Template DARK = new HtmlAttribute.Template("dark");
		final public static HtmlAttribute.Template FILLED = new HtmlAttribute.Template("filled");
		final public static HtmlAttribute.Template OUTLINED = new HtmlAttribute.Template("outlined");
		final public static HtmlAttribute.Template BORDERLESS = new HtmlAttribute.Template("borderless");
		final public static HtmlAttribute.Template STANDOUT = new HtmlAttribute.Template("standout");
		final public static HtmlAttribute.Template ROUNDED = new HtmlAttribute.Template("rounded");
		final public static HtmlAttribute.Template SQUARE = new HtmlAttribute.Template("square");
		final public static HtmlAttribute.Template MULTIPLE = new HtmlAttribute.Template("multiple");
		final public static HtmlAttribute.Template RADIO = new HtmlAttribute.Template("radio");
		final public static HtmlAttribute.Template TOGGLE = new HtmlAttribute.Template("toggle");
		final public static HtmlAttribute.Template CHIPS = new HtmlAttribute.Template("chips");
		final public static HtmlAttribute.Template SEPARATOR = new HtmlAttribute.Template("separator");
		final public static HtmlAttribute.Template STACK_LABEL = new HtmlAttribute.Template("stack-label");
		final public static HtmlAttribute.Template CLEARABLE = new HtmlAttribute.Template("clearable");
		final public static HtmlAttribute.Template DISABLE = new HtmlAttribute.Template("disable");
		final public static HtmlAttribute.Template NO_ERROR_ICON = new HtmlAttribute.Template("no-error-icon");
		final public static HtmlAttribute.Template EMIT_VALUE = new HtmlAttribute.Template("emit-value");
		final public static HtmlAttribute.Template MAP_OPTIONS = new HtmlAttribute.Template("map-options");
		final public static HtmlAttribute.Template OPTIONS_DARK = new HtmlAttribute.Template("options-dark");
		final public static HtmlAttribute.Template HIDE_BOTTOM_SPACE = new HtmlAttribute.Template("hide-bottom-space");
		final public static HtmlAttribute.Template LABEL = new HtmlAttribute.Template("label");
		final public static HtmlAttribute.Template OPTIONS = new HtmlAttribute.Template("options", true);
		final public static HtmlAttribute.Template ERROR = new HtmlAttribute.Template("error");
	}


	@FunctionalInterface
	public interface IIndexedLabel
	{
		String get(int index);
	}

}
