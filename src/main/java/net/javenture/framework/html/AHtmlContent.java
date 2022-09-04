package net.javenture.framework.html;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.css.ICssClass;
import net.javenture.framework.util.Validator;


/*
	2019/09/29
 */
abstract public class AHtmlContent<E extends AHtmlContent>
	extends AHtmlElement<E>
{
	//
	protected AHtmlContent()
	{
	}


	protected AHtmlContent(@NullDisallow AHtmlElement parent)
	{
		super(parent);
	}


	//
	final public E content(IHtmlEntry entry)
	{
		return super.child(entry);
	}


	final public E content(IHtmlEntry... entries)
	{
		return super.child(entries);
	}


	final public E content(char value)
	{
		return super.child(new HtmlText(value));
	}


	final public E content(@NullDisallow char[] array)
	{
		return super.child(new HtmlText(array));
	}


	final public E content(boolean value)
	{
		return super.child(new HtmlText(value));
	}


	final public E content(@NullAllow Boolean value)
	{
		return super.child(new HtmlText(value));
	}


	final public E content(short value)
	{
		return super.child(new HtmlText(value));
	}


	final public E content(@NullAllow Short value)
	{
		return super.child(new HtmlText(value));
	}


	final public E content(int value)
	{
		return super.child(new HtmlText(value));
	}


	final public E content(@NullAllow Integer value)
	{
		return super.child(new HtmlText(value));
	}


	final public E content(long value)
	{
		return super.child(new HtmlText(value));
	}


	final public E content(@NullAllow Long value)
	{
		return super.child(new HtmlText(value));
	}


	final public E content(float value)
	{
		return super.child(new HtmlText(value));
	}


	final public E content(@NullAllow Float value)
	{
		return super.child(new HtmlText(value));
	}


	final public E content(double value)
	{
		return super.child(new HtmlText(value));
	}


	final public E content(@NullAllow Double value)
	{
		return super.child(new HtmlText(value));
	}


	final public E content(@NullAllow Number value)
	{
		return super.child(new HtmlText(value));
	}


	final public E content(@NullAllow CharSequence value)
	{
		return super.child(new HtmlText(value));
	}


	final public E content(boolean condition, IHtmlEntry entry)
	{
		return super.child(condition, entry);
	}


	final public E content(boolean condition, IHtmlEntry... entries)
	{
		return super.child(condition, entries);
	}


	final public E content(boolean condition, char value)
	{
		return super.child(condition, new HtmlText(value));
	}


	final public E content(boolean condition, @NullDisallow char[] array)
	{
		return super.child(condition, new HtmlText(array));
	}


	final public E content(boolean condition, boolean value)
	{
		return super.child(condition, new HtmlText(value));
	}


	final public E content(boolean condition, @NullAllow Boolean value)
	{
		return super.child(condition, new HtmlText(value));
	}


	final public E content(boolean condition, short value)
	{
		return super.child(condition, new HtmlText(value));
	}


	final public E content(boolean condition, @NullAllow Short value)
	{
		return super.child(condition, new HtmlText(value));
	}


	final public E content(boolean condition, int value)
	{
		return super.child(condition, new HtmlText(value));
	}


	final public E content(boolean condition, @NullAllow Integer value)
	{
		return super.child(condition, new HtmlText(value));
	}


	final public E content(boolean condition, long value)
	{
		return super.child(condition, new HtmlText(value));
	}


	final public E content(boolean condition, @NullAllow Long value)
	{
		return super.child(condition, new HtmlText(value));
	}


	final public E content(boolean condition, float value)
	{
		return super.child(condition, new HtmlText(value));
	}


	final public E content(boolean condition, @NullAllow Float value)
	{
		return super.child(condition, new HtmlText(value));
	}


	final public E content(boolean condition, double value)
	{
		return super.child(condition, new HtmlText(value));
	}


	final public E content(boolean condition, @NullAllow Double value)
	{
		return super.child(condition, new HtmlText(value));
	}


	final public E content(boolean condition, @NullAllow Number value)
	{
		return super.child(condition, new HtmlText(value));
	}


	final public E content(boolean condition, @NullAllow CharSequence value)
	{
		return super.child(condition, new HtmlText(value));
	}


	final public E content(IHtmlEntry entry, Config wrapper)
	{
		create(wrapper, this)
			.child(entry);

		return THIS;
	}


	final public E content(char value, Config wrapper)
	{
		create(wrapper, this)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(@NullDisallow char[] value, Config wrapper)
	{
		create(wrapper, this)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(boolean value, Config wrapper)
	{
		create(wrapper, this)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(@NullAllow Boolean value, Config wrapper)
	{
		create(wrapper, this)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(short value, Config wrapper)
	{
		create(wrapper, this)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(@NullAllow Short value, Config wrapper)
	{
		create(wrapper, this)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(int value, Config wrapper)
	{
		create(wrapper, this)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(@NullAllow Integer value, Config wrapper)
	{
		create(wrapper, this)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(long value, Config wrapper)
	{
		create(wrapper, this)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(@NullAllow Long value, Config wrapper)
	{
		create(wrapper, this)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(float value, Config wrapper)
	{
		create(wrapper, this)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(@NullAllow Float value, Config wrapper)
	{
		create(wrapper, this)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(double value, Config wrapper)
	{
		create(wrapper, this)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(@NullAllow Double value, Config wrapper)
	{
		create(wrapper, this)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(@NullAllow Number value, Config wrapper)
	{
		create(wrapper, this)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(@NullAllow CharSequence value, Config wrapper)
	{
		create(wrapper, this)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(boolean condition, IHtmlEntry entry, Config wrapper)
	{
		if (condition)
		{
			create(wrapper, this)
				.child(entry);
		}

		return THIS;
	}


	final public E content(boolean condition, char value, Config wrapper)
	{
		if (condition)
		{
			create(wrapper, this)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(boolean condition, @NullDisallow char[] value, Config wrapper)
	{
		if (condition)
		{
			create(wrapper, this)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(boolean condition, boolean value, Config wrapper)
	{
		if (condition)
		{
			create(wrapper, this)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(boolean condition, @NullAllow Boolean value, Config wrapper)
	{
		if (condition)
		{
			create(wrapper, this)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(boolean condition, short value, Config wrapper)
	{
		if (condition)
		{
			create(wrapper, this)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(boolean condition, @NullAllow Short value, Config wrapper)
	{
		if (condition)
		{
			create(wrapper, this)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(boolean condition, int value, Config wrapper)
	{
		if (condition)
		{
			create(wrapper, this)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(boolean condition, @NullAllow Integer value, Config wrapper)
	{
		if (condition)
		{
			create(wrapper, this)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(boolean condition, long value, Config wrapper)
	{
		if (condition)
		{
			create(wrapper, this)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(boolean condition, @NullAllow Long value, Config wrapper)
	{
		if (condition)
		{
			create(wrapper, this)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(boolean condition, float value, Config wrapper)
	{
		if (condition)
		{
			create(wrapper, this)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(boolean condition, @NullAllow Float value, Config wrapper)
	{
		if (condition)
		{
			create(wrapper, this)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(boolean condition, double value, Config wrapper)
	{
		if (condition)
		{
			create(wrapper, this)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(boolean condition, @NullAllow Double value, Config wrapper)
	{
		if (condition)
		{
			create(wrapper, this)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(boolean condition, @NullAllow Number value, Config wrapper)
	{
		if (condition)
		{
			create(wrapper, this)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(boolean condition, @NullAllow CharSequence value, Config wrapper)
	{
		if (condition)
		{
			create(wrapper, this)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(IHtmlEntry entry, Config wrapper, ICssClass... css)
	{
		create(wrapper, this)
			.cl(css)
			.child(entry);

		return THIS;
	}


	final public E content(char value, Config wrapper, ICssClass... css)
	{
		create(wrapper, this)
			.cl(css)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(@NullDisallow char[] value, Config wrapper, ICssClass... css)
	{
		create(wrapper, this)
			.cl(css)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(boolean value, Config wrapper, ICssClass... css)
	{
		create(wrapper, this)
			.cl(css)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(@NullAllow Boolean value, Config wrapper, ICssClass... css)
	{
		create(wrapper, this)
			.cl(css)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(short value, Config wrapper, ICssClass... css)
	{
		create(wrapper, this)
			.cl(css)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(@NullAllow Short value, Config wrapper, ICssClass... css)
	{
		create(wrapper, this)
			.cl(css)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(int value, Config wrapper, ICssClass... css)
	{
		create(wrapper, this)
			.cl(css)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(@NullAllow Integer value, Config wrapper, ICssClass... css)
	{
		create(wrapper, this)
			.cl(css)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(long value, Config wrapper, ICssClass... css)
	{
		create(wrapper, this)
			.cl(css)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(@NullAllow Long value, Config wrapper, ICssClass... css)
	{
		create(wrapper, this)
			.cl(css)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(float value, Config wrapper, ICssClass... css)
	{
		create(wrapper, this)
			.cl(css)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(@NullAllow Float value, Config wrapper, ICssClass... css)
	{
		create(wrapper, this)
			.cl(css)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(double value, Config wrapper, ICssClass... css)
	{
		create(wrapper, this)
			.cl(css)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(@NullAllow Double value, Config wrapper, ICssClass... css)
	{
		create(wrapper, this)
			.cl(css)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(@NullAllow Number value, Config wrapper, ICssClass... css)
	{
		create(wrapper, this)
			.cl(css)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(@NullAllow CharSequence value, Config wrapper, ICssClass... css)
	{
		create(wrapper, this)
			.cl(css)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(boolean condition, IHtmlEntry entry, Config wrapper, ICssClass... css)
	{
		if (condition)
		{
			create(wrapper, this)
				.cl(css)
				.child(entry);
		}

		return THIS;
	}


	final public E content(boolean condition, char value, Config wrapper, ICssClass... css)
	{
		if (condition)
		{
			create(wrapper, this)
				.cl(css)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(boolean condition, @NullDisallow char[] value, Config wrapper, ICssClass... css)
	{
		if (condition)
		{
			create(wrapper, this)
				.cl(css)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(boolean condition, boolean value, Config wrapper, ICssClass... css)
	{
		if (condition)
		{
			create(wrapper, this)
				.cl(css)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(boolean condition, @NullAllow Boolean value, Config wrapper, ICssClass... css)
	{
		if (condition)
		{
			create(wrapper, this)
				.cl(css)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(boolean condition, short value, Config wrapper, ICssClass... css)
	{
		if (condition)
		{
			create(wrapper, this)
				.cl(css)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(boolean condition, @NullAllow Short value, Config wrapper, ICssClass... css)
	{
		if (condition)
		{
			create(wrapper, this)
				.cl(css)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(boolean condition, int value, Config wrapper, ICssClass... css)
	{
		if (condition)
		{
			create(wrapper, this)
				.cl(css)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(boolean condition, @NullAllow Integer value, Config wrapper, ICssClass... css)
	{
		if (condition)
		{
			create(wrapper, this)
				.cl(css)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(boolean condition, long value, Config wrapper, ICssClass... css)
	{
		if (condition)
		{
			create(wrapper, this)
				.cl(css)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(boolean condition, @NullAllow Long value, Config wrapper, ICssClass... css)
	{
		if (condition)
		{
			create(wrapper, this)
				.cl(css)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(boolean condition, float value, Config wrapper, ICssClass... css)
	{
		if (condition)
		{
			create(wrapper, this)
				.cl(css)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(boolean condition, @NullAllow Float value, Config wrapper, ICssClass... css)
	{
		if (condition)
		{
			create(wrapper, this)
				.cl(css)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(boolean condition, double value, Config wrapper, ICssClass... css)
	{
		if (condition)
		{
			create(wrapper, this)
				.cl(css)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(boolean condition, @NullAllow Double value, Config wrapper, ICssClass... css)
	{
		if (condition)
		{
			create(wrapper, this)
				.cl(css)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(boolean condition, @NullAllow Number value, Config wrapper, ICssClass... css)
	{
		if (condition)
		{
			create(wrapper, this)
				.cl(css)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(boolean condition, @NullAllow CharSequence value, Config wrapper, ICssClass... css)
	{
		if (condition)
		{
			create(wrapper, this)
				.cl(css)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(IHtmlEntry entry, Config wrapper, String... css)
	{
		create(wrapper, this)
			.cl(css)
			.child(entry);

		return THIS;
	}


	final public E content(char value, Config wrapper, String... css)
	{
		create(wrapper, this)
			.cl(css)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(@NullDisallow char[] value, Config wrapper, String... css)
	{
		create(wrapper, this)
			.cl(css)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(boolean value, Config wrapper, String... css)
	{
		create(wrapper, this)
			.cl(css)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(@NullAllow Boolean value, Config wrapper, String... css)
	{
		create(wrapper, this)
			.cl(css)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(short value, Config wrapper, String... css)
	{
		create(wrapper, this)
			.cl(css)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(@NullAllow Short value, Config wrapper, String... css)
	{
		create(wrapper, this)
			.cl(css)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(int value, Config wrapper, String... css)
	{
		create(wrapper, this)
			.cl(css)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(@NullAllow Integer value, Config wrapper, String... css)
	{
		create(wrapper, this)
			.cl(css)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(long value, Config wrapper, String... css)
	{
		create(wrapper, this)
			.cl(css)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(@NullAllow Long value, Config wrapper, String... css)
	{
		create(wrapper, this)
			.cl(css)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(float value, Config wrapper, String... css)
	{
		create(wrapper, this)
			.cl(css)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(@NullAllow Float value, Config wrapper, String... css)
	{
		create(wrapper, this)
			.cl(css)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(double value, Config wrapper, String... css)
	{
		create(wrapper, this)
			.cl(css)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(@NullAllow Double value, Config wrapper, String... css)
	{
		create(wrapper, this)
			.cl(css)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(@NullAllow Number value, Config wrapper, String... css)
	{
		create(wrapper, this)
			.cl(css)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(@NullAllow CharSequence value, Config wrapper, String... css)
	{
		create(wrapper, this)
			.cl(css)
			.child(new HtmlText(value));

		return THIS;
	}


	final public E content(boolean condition, IHtmlEntry entry, Config wrapper, String... css)
	{
		if (condition)
		{
			create(wrapper, this)
				.cl(css)
				.child(entry);
		}

		return THIS;
	}


	final public E content(boolean condition, char value, Config wrapper, String... css)
	{
		if (condition)
		{
			create(wrapper, this)
				.cl(css)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(boolean condition, @NullDisallow char[] value, Config wrapper, String... css)
	{
		if (condition)
		{
			create(wrapper, this)
				.cl(css)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(boolean condition, boolean value, Config wrapper, String... css)
	{
		if (condition)
		{
			create(wrapper, this)
				.cl(css)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(boolean condition, @NullAllow Boolean value, Config wrapper, String... css)
	{
		if (condition)
		{
			create(wrapper, this)
				.cl(css)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(boolean condition, short value, Config wrapper, String... css)
	{
		if (condition)
		{
			create(wrapper, this)
				.cl(css)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(boolean condition, @NullAllow Short value, Config wrapper, String... css)
	{
		if (condition)
		{
			create(wrapper, this)
				.cl(css)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(boolean condition, int value, Config wrapper, String... css)
	{
		if (condition)
		{
			create(wrapper, this)
				.cl(css)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(boolean condition, @NullAllow Integer value, Config wrapper, String... css)
	{
		if (condition)
		{
			create(wrapper, this)
				.cl(css)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(boolean condition, long value, Config wrapper, String... css)
	{
		if (condition)
		{
			create(wrapper, this)
				.cl(css)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(boolean condition, @NullAllow Long value, Config wrapper, String... css)
	{
		if (condition)
		{
			create(wrapper, this)
				.cl(css)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(boolean condition, float value, Config wrapper, String... css)
	{
		if (condition)
		{
			create(wrapper, this)
				.cl(css)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(boolean condition, @NullAllow Float value, Config wrapper, String... css)
	{
		if (condition)
		{
			create(wrapper, this)
				.cl(css)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(boolean condition, double value, Config wrapper, String... css)
	{
		if (condition)
		{
			create(wrapper, this)
				.cl(css)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(boolean condition, @NullAllow Double value, Config wrapper, String... css)
	{
		if (condition)
		{
			create(wrapper, this)
				.cl(css)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(boolean condition, @NullAllow Number value, Config wrapper, String... css)
	{
		if (condition)
		{
			create(wrapper, this)
				.cl(css)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E content(boolean condition, @NullAllow CharSequence value, Config wrapper, String... css)
	{
		if (condition)
		{
			create(wrapper, this)
				.cl(css)
				.child(new HtmlText(value));
		}

		return THIS;
	}


	final public E markup(@NullAllow CharSequence value)
	{
		return super.child(new HtmlMarkup(value));
	}


	final public E markup(boolean condition, @NullAllow CharSequence value)
	{
		return super.child(condition, new HtmlMarkup(value));
	}


	//
	public static AHtmlContent create(@NullDisallow Config config)
	{
		Validator.notNull(config, "[config]");

		return new AHtmlContent()
		{
			//
			@Override
			public Config config()
			{
				return config;
			}
		};
	}


	public static AHtmlContent create(@NullDisallow Config config, @NullDisallow AHtmlElement parent)
	{
		Validator.notNull(config, "[config]");

		return new AHtmlContent(parent)
		{
			@Override
			public Config config()
			{
				return config;
			}
		};
	}


	public static AHtmlContent create(@NullDisallow Type type, @NullDisallow String name)
	{
		return new AHtmlContent()
		{
			//
			final private Config CONFIG = new Config(type, name);

			//
			@Override
			public Config config()
			{
				return CONFIG;
			}
		};
	}


	public static AHtmlContent create(@NullDisallow Type type, @NullDisallow String name, @NullDisallow AHtmlElement parent)
	{
		return new AHtmlContent(parent)
		{
			//
			final private Config CONFIG = new Config(type, name);

			//
			@Override
			public Config config()
			{
				return CONFIG;
			}
		};
	}

}
