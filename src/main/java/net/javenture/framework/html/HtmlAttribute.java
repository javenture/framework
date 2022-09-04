package net.javenture.framework.html;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.annotation.SingleThread;
import net.javenture.framework.hex.HexUtil;
import net.javenture.framework.utf8.IUtf8StreamableEntry;
import net.javenture.framework.utf8.Utf8Byte;
import net.javenture.framework.utf8.Utf8Number;
import net.javenture.framework.utf8.Utf8OutputStream;
import net.javenture.framework.utf8.Utf8Replacement;
import net.javenture.framework.utf8.Utf8Util;
import net.javenture.framework.util.BooleanUtil;
import net.javenture.framework.util.Chain;
import net.javenture.framework.util.Validator;
import net.javenture.framework.model.AModel;

import java.io.IOException;


/*
	2019/11/21
 */
@SingleThread
final public class HtmlAttribute
	implements IUtf8StreamableEntry
{
	/*
		http://wonko.com/post/html-escaping
		https://www.owasp.org/index.php/XSS_(Cross_Site_Scripting)_Prevention_Cheat_Sheet
	 */

	//
	final private static Utf8Replacement REPLACEMENT = new Utf8Replacement
	(
		new Utf8Replacement.Pair('"', HtmlEntity.QUOT),
		new Utf8Replacement.Pair('&', HtmlEntity.AMP),
		//new Utf8Replacement.Pair('\'', HtmlReference.APOSTROPHE),
		new Utf8Replacement.Pair('<', HtmlEntity.LT),
		new Utf8Replacement.Pair('>', HtmlEntity.GT)
	);


	//
	final private boolean VUE;
	final private @NullDisallow IUtf8StreamableEntry NAME;
	final private @NullDisallow Value VALUE;


	//
	private HtmlAttribute(@NullDisallow byte[] array)
	{
		this(false, array);
	}


	public HtmlAttribute(@NullDisallow Template template)
	{
		this(false, template);
	}


	public HtmlAttribute(@NullDisallow Template template, @NullDisallow IUtf8StreamableEntry value)
	{
		this(false, template, value);
	}


	public HtmlAttribute(@NullDisallow Template template, char value)
	{
		this(false, template, value);
	}


	public HtmlAttribute(@NullDisallow Template template, boolean value)
	{
		this(false, template, value);
	}


	public HtmlAttribute(@NullDisallow Template template, short value)
	{
		this(false, template, value);
	}


	public HtmlAttribute(@NullDisallow Template template, int value)
	{
		this(false, template, value);
	}


	public HtmlAttribute(@NullDisallow Template template, long value)
	{
		this(false, template, value);
	}


	public HtmlAttribute(@NullDisallow Template template, float value)
	{
		this(false, template, value);
	}


	public HtmlAttribute(@NullDisallow Template template, double value)
	{
		this(false, template, value);
	}


	public HtmlAttribute(@NullDisallow Template template, @NullDisallow Number value)
	{
		this(false, template, value);
	}


	public HtmlAttribute(@NullDisallow Template template, @NullDisallow byte[] value)
	{
		this(false, template, value);
	}


	public HtmlAttribute(@NullDisallow Template template, @NullDisallow CharSequence value)
	{
		this(false, template, value);
	}


	public HtmlAttribute(Template template, @NullDisallow AModel value)
	{
		this(false, template, value);
	}


	public HtmlAttribute(@NullDisallow CharSequence name)
	{
		this(false, name);
	}


	public HtmlAttribute(@NullDisallow CharSequence name, @NullDisallow IUtf8StreamableEntry value)
	{
		this(false, name, value);
	}


	public HtmlAttribute(@NullDisallow CharSequence name, char value)
	{
		this(false, name, value);
	}


	public HtmlAttribute(@NullDisallow CharSequence name, boolean value)
	{
		this(false, name, value);
	}


	public HtmlAttribute(@NullDisallow CharSequence name, short value)
	{
		this(false, name, value);
	}


	public HtmlAttribute(@NullDisallow CharSequence name, int value)
	{
		this(false, name, value);
	}


	public HtmlAttribute(@NullDisallow CharSequence name, long value)
	{
		this(false, name, value);
	}


	public HtmlAttribute(@NullDisallow CharSequence name, float value)
	{
		this(false, name, value);
	}


	public HtmlAttribute(@NullDisallow CharSequence name, double value)
	{
		this(false, name, value);
	}


	public HtmlAttribute(@NullDisallow CharSequence name, @NullDisallow Number value)
	{
		this(false, name, value);
	}


	public HtmlAttribute(@NullDisallow CharSequence name, @NullDisallow byte[] value)
	{
		this(false, name, value);
	}


	public HtmlAttribute(@NullDisallow CharSequence name, @NullDisallow CharSequence value)
	{
		this(false, name, value);
	}


	public HtmlAttribute(@NullDisallow CharSequence name, @NullDisallow AModel value)
	{
		this(false, name, value);
	}


	private HtmlAttribute(boolean vue, @NullDisallow byte[] array)
	{
		VUE = vue;
		NAME = IUtf8StreamableEntry.entry(array);
		VALUE = new Value();
	}


	public HtmlAttribute(boolean vue, @NullDisallow Template template)
	{
		VUE = vue || template.VUE;
		NAME = template.ATTRIBUTE.NAME;
		VALUE = new Value();
	}


	public HtmlAttribute(boolean vue, @NullDisallow Template template, @NullDisallow IUtf8StreamableEntry value)
	{
		Validator.notNull(value, "[value]");

		VUE = vue || template.VUE;
		NAME = template.ATTRIBUTE.NAME;
		VALUE = new Value(value);
	}


	public HtmlAttribute(boolean vue, @NullDisallow Template template, char value)
	{
		VUE = vue || template.VUE;
		NAME = template.ATTRIBUTE.NAME;
		VALUE = new Value(value);
	}


	public HtmlAttribute(boolean vue, @NullDisallow Template template, boolean value)
	{
		VUE = vue || template.VUE;
		NAME = template.ATTRIBUTE.NAME;
		VALUE = new Value(value ? BooleanUtil.True.ENTRY : BooleanUtil.False.ENTRY);
	}


	public HtmlAttribute(boolean vue, @NullDisallow Template template, short value)
	{
		VUE = vue || template.VUE;
		NAME = template.ATTRIBUTE.NAME;
		VALUE = new Value(Utf8Number.entry(value));
	}


	public HtmlAttribute(boolean vue, @NullDisallow Template template, int value)
	{
		VUE = vue || template.VUE;
		NAME = template.ATTRIBUTE.NAME;
		VALUE = new Value(Utf8Number.entry(value));
	}


	public HtmlAttribute(boolean vue, @NullDisallow Template template, long value)
	{
		VUE = vue || template.VUE;
		NAME = template.ATTRIBUTE.NAME;
		VALUE = new Value(Utf8Number.entry(value));
	}


	public HtmlAttribute(boolean vue, @NullDisallow Template template, float value)
	{
		VUE = vue || template.VUE;
		NAME = template.ATTRIBUTE.NAME;
		VALUE = new Value(Utf8Number.entry(value));
	}


	public HtmlAttribute(boolean vue, @NullDisallow Template template, double value)
	{
		VUE = vue || template.VUE;
		NAME = template.ATTRIBUTE.NAME;
		VALUE = new Value(Utf8Number.entry(value));
	}


	public HtmlAttribute(boolean vue, @NullDisallow Template template, @NullDisallow Number value)
	{
		Validator.notNull(value, "[value]");

		VUE = vue || template.VUE;
		NAME = template.ATTRIBUTE.NAME;
		VALUE = new Value(Utf8Number.entry(value));
	}


	public HtmlAttribute(boolean vue, @NullDisallow Template template, @NullDisallow byte[] value)
	{
		Validator.notNull(value, "[value]");

		VUE = vue || template.VUE;
		NAME = template.ATTRIBUTE.NAME;
		VALUE = new Value(HexUtil.convert(value));
	}


	public HtmlAttribute(boolean vue, @NullDisallow Template template, @NullDisallow CharSequence value)
	{
		Validator.notNull(value, "[value]");

		VUE = vue || template.VUE;
		NAME = template.ATTRIBUTE.NAME;
		VALUE = new Value(value);
	}


	public HtmlAttribute(boolean vue, @NullDisallow Template template, @NullDisallow AModel value)
	{
		Validator.notNull(value, "[value]");

		VUE = vue || template.VUE;
		NAME = template.ATTRIBUTE.NAME;
		VALUE = new Value(value.alias());
	}


	public HtmlAttribute(boolean vue, @NullDisallow CharSequence name)
	{
		Validator.notNull(name, "[name]");

		VUE = vue;
		NAME = IUtf8StreamableEntry.entry(name);
		VALUE = new Value();
	}


	public HtmlAttribute(boolean vue, @NullDisallow CharSequence name, @NullDisallow IUtf8StreamableEntry value)
	{
		Validator.notNull(name, "[name]");
		Validator.notNull(value, "[value]");

		VUE = vue;
		NAME = IUtf8StreamableEntry.entry(name);
		VALUE = new Value(value);
	}


	public HtmlAttribute(boolean vue, @NullDisallow CharSequence name, char value)
	{
		Validator.notNull(name, "[name]");

		VUE = vue;
		NAME = IUtf8StreamableEntry.entry(name);
		VALUE = new Value(value);
	}


	public HtmlAttribute(boolean vue, @NullDisallow CharSequence name, boolean value)
	{
		Validator.notNull(name, "[name]");

		VUE = vue;
		NAME = IUtf8StreamableEntry.entry(name);
		VALUE = new Value(value ? BooleanUtil.True.ENTRY : BooleanUtil.False.ENTRY);
	}


	public HtmlAttribute(boolean vue, @NullDisallow CharSequence name, short value)
	{
		Validator.notNull(name, "[name]");

		VUE = vue;
		NAME = IUtf8StreamableEntry.entry(name);
		VALUE = new Value(Utf8Number.entry(value));
	}


	public HtmlAttribute(boolean vue, @NullDisallow CharSequence name, int value)
	{
		Validator.notNull(name, "[name]");

		VUE = vue;
		NAME = IUtf8StreamableEntry.entry(name);
		VALUE = new Value(Utf8Number.entry(value));
	}


	public HtmlAttribute(boolean vue, @NullDisallow CharSequence name, long value)
	{
		Validator.notNull(name, "[name]");

		VUE = vue;
		NAME = IUtf8StreamableEntry.entry(name);
		VALUE = new Value(Utf8Number.entry(value));
	}


	public HtmlAttribute(boolean vue, @NullDisallow CharSequence name, float value)
	{
		Validator.notNull(name, "[name]");

		VUE = vue;
		NAME = IUtf8StreamableEntry.entry(name);
		VALUE = new Value(Utf8Number.entry(value));
	}


	public HtmlAttribute(boolean vue, @NullDisallow CharSequence name, double value)
	{
		Validator.notNull(name, "[name]");

		VUE = vue;
		NAME = IUtf8StreamableEntry.entry(name);
		VALUE = new Value(Utf8Number.entry(value));
	}


	public HtmlAttribute(boolean vue, @NullDisallow CharSequence name, @NullDisallow Number value)
	{
		Validator.notNull(name, "[name]");
		Validator.notNull(value, "[value]");

		VUE = vue;
		NAME = IUtf8StreamableEntry.entry(name);
		VALUE = new Value(Utf8Number.entry(value));
	}


	public HtmlAttribute(boolean vue, @NullDisallow CharSequence name, @NullDisallow byte[] value)
	{
		Validator.notNull(name, "[name]");
		Validator.notNull(value, "[value]");

		VUE = vue;
		NAME = IUtf8StreamableEntry.entry(name);
		VALUE = new Value(HexUtil.convert(value));
	}


	public HtmlAttribute(boolean vue, @NullDisallow CharSequence name, @NullDisallow CharSequence value)
	{
		Validator.notNull(name, "[name]");
		Validator.notNull(value, "[value]");

		VUE = vue;
		NAME = IUtf8StreamableEntry.entry(name);
		VALUE = new Value(value);
	}


	public HtmlAttribute(boolean vue, @NullDisallow CharSequence name, @NullDisallow AModel value)
	{
		Validator.notNull(name, "[name]");
		Validator.notNull(value, "[value]");

		VUE = vue;
		NAME = IUtf8StreamableEntry.entry(name);
		VALUE = new Value(value.alias());
	}


	//
	@Override
	public void toUtf8Stream(Utf8OutputStream destination)
		throws IOException
	{
		if (VUE) destination.write(Utf8Byte.COLON);

		NAME.toUtf8Stream(destination);

		if (VALUE.exist())
		{
			destination.write(Utf8Byte.EQUAL, Utf8Byte.QUOTATION);
			VALUE.toUtf8Stream(destination);
			destination.write(Utf8Byte.QUOTATION);
		}
	}


	public boolean exist()
	{
		return VALUE.exist();
	}


	public HtmlAttribute value(@NullDisallow IUtf8StreamableEntry value)
	{
		Validator.notNull(value, "[value]");
		VALUE.add(value);

		return this;
	}


	public HtmlAttribute value(char value)
	{
		VALUE.add(value);

		return this;
	}


	public HtmlAttribute value(boolean value)
	{
		VALUE.add(value ? BooleanUtil.True.ENTRY : BooleanUtil.False.ENTRY);

		return this;
	}


	public HtmlAttribute value(short value)
	{
		VALUE.add(Utf8Number.entry(value));

		return this;
	}


	public HtmlAttribute value(int value)
	{
		VALUE.add(Utf8Number.entry(value));

		return this;
	}


	public HtmlAttribute value(long value)
	{
		VALUE.add(Utf8Number.entry(value));

		return this;
	}


	public HtmlAttribute value(float value)
	{
		VALUE.add(Utf8Number.entry(value));

		return this;
	}


	public HtmlAttribute value(double value)
	{
		VALUE.add(Utf8Number.entry(value));

		return this;
	}


	public HtmlAttribute value(@NullDisallow Number value)
	{
		Validator.notNull(value, "[value]");
		VALUE.add(Utf8Number.entry(value));

		return this;
	}


	public HtmlAttribute value(@NullDisallow byte[] value)
	{
		Validator.notNull(value, "[value]");
		VALUE.add(HexUtil.convert(value));

		return this;
	}


	public HtmlAttribute value(@NullDisallow CharSequence value)
	{
		Validator.notNull(value, "[value]");
		VALUE.add(value);

		return this;
	}


	public HtmlAttribute delimiter(Delimiter value)
	{
		if (VALUE.exist()) VALUE.add(value.ENTRY);

		return this;
	}


	public HtmlAttribute delimiter(char value)
	{
		if (VALUE.exist()) VALUE.add(value);

		return this;
	}


	public HtmlAttribute delimiter(@NullDisallow CharSequence value)
	{
		Validator.notNull(value, "[value]");

		if (VALUE.exist()) VALUE.add(value);

		return this;
	}


	//
	final private static class Value
		implements IUtf8StreamableEntry
	{
		//
		final private Chain<IUtf8StreamableEntry> CHAIN;

		//
		private Value()
		{
			CHAIN = new Chain<>();
		}

		private Value(@NullDisallow IUtf8StreamableEntry entry)
		{
			CHAIN = new Chain<>(entry);
		}

		private Value(char c)
		{
			CHAIN = new Chain<>(REPLACEMENT.entry(c));
		}

		private Value(@NullDisallow char[] array)
		{
			CHAIN = new Chain<>(REPLACEMENT.entry(array));
		}

		private Value(@NullDisallow CharSequence sequence)
		{
			CHAIN = new Chain<>(REPLACEMENT.entry(sequence));
		}

		//
		private boolean exist()
		{
			return CHAIN.exists();
		}

		private void add(@NullDisallow IUtf8StreamableEntry entry)
		{
			CHAIN.add(entry);
		}

		private void add(char c)
		{
			CHAIN.add(REPLACEMENT.entry(c));
		}

		private void add(@NullDisallow char[] array)
		{
			CHAIN.add(REPLACEMENT.entry(array));
		}

		private void add(@NullDisallow CharSequence sequence)
		{
			CHAIN.add(REPLACEMENT.entry(sequence));
		}

		@Override
		public void toUtf8Stream(Utf8OutputStream destination)
			throws IOException
		{
			if (CHAIN.exists())
			{
				for (IUtf8StreamableEntry entry : CHAIN) destination.write(entry);
			}
		}
	}


	public enum Delimiter
	{
		//
		SPACE(Utf8Byte.SPACE),
		DOT(Utf8Byte.DOT),
		COMMA(Utf8Byte.COMMA);

		//
		final private IUtf8StreamableEntry ENTRY;

		//
		Delimiter(byte b)
		{
			ENTRY = destination -> destination.write(b);
		}
	}


	final public static class Template
	{
		// XXX: ELEMENT_CONFIG + ID


		//
		final HtmlAttribute ATTRIBUTE;

		final private boolean VUE;

		//
		public Template(@NullDisallow String name)
		{
			this(name, false);
		}

		public Template(@NullDisallow String name, boolean vue)
		{
			Validator.notNull(name, "[name]");
			byte[] array = Utf8Util.bytes(name);

			ATTRIBUTE = new HtmlAttribute(array);
			VUE = vue;
		}
	}


	public static class Preset
	{
		//
		final HtmlAttribute ATTRIBUTE;

		//
		public Preset(@NullDisallow Template template, @NullDisallow CharSequence value)
		{
			this(template, value, false);
		}

		public Preset(@NullDisallow String name, @NullDisallow CharSequence value)
		{
			this(name, value, false);
		}

		public Preset(@NullDisallow Template template, @NullDisallow CharSequence value, boolean vue)
		{
			byte[] array = Utf8Util.bytes(new HtmlAttribute(vue, template, value));

			ATTRIBUTE = new HtmlAttribute(array);
		}

		public Preset(@NullDisallow String name, @NullDisallow CharSequence value, boolean vue)
		{
			byte[] array = Utf8Util.bytes(new HtmlAttribute(vue, name, value));

			ATTRIBUTE = new HtmlAttribute(array);
		}
	}

}
