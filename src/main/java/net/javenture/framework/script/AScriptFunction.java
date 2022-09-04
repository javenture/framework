package net.javenture.framework.script;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.IFactoryObject;
import net.javenture.framework.utf8.IUtf8StreamableEntry;
import net.javenture.framework.utf8.Utf8Byte;
import net.javenture.framework.utf8.Utf8OutputStream;
import net.javenture.framework.utf8.Utf8Util;
import net.javenture.framework.util.Chain;
import net.javenture.framework.util.IConst;
import net.javenture.framework.util.Validator;

import java.io.IOException;


/*
	2019/11/21
 */
abstract public class AScriptFunction
	implements IScriptStreamableEntry
{
	//
	final private @NullDisallow IUtf8StreamableEntry NAME;
	final private @NullDisallow IUtf8StreamableEntry CONTENT;


	//
	public AScriptFunction()
	{
		String name = _name();
		String content = _content();                                                  // XXX: not null ! if null -> ScriptMethod
		Validator.notNull(name, "[name]");

		//
		NAME = IUtf8StreamableEntry.entry(name, true);

		CONTENT = content != null && !content.isEmpty()
			? IUtf8StreamableEntry.entry(name + ": " + content,true)
			: IUtf8StreamableEntry.BLANK;
	}


	//
	abstract protected @NullDisallow String _name();
	abstract protected @NullAllow String _content();


	//
	@Override
	final public void toScriptStream(Utf8OutputStream destination)
		throws IOException
	{
		CONTENT.toUtf8Stream(destination);
	}


	public Invoke invoke()
	{
		return new Invoke();
	}


	//
	final public class Invoke
		implements IUtf8StreamableEntry
	{
		//
		final private Chain<IScriptStreamableEntry> ARGUMENTS;

		private boolean parenthesis;
		private boolean semicolon;

		//
		private Invoke()
		{
			ARGUMENTS = new Chain<>();
			parenthesis = true;
			semicolon = true;
		}

		//
		@Override
		public void toUtf8Stream(Utf8OutputStream destination)
			throws IOException
		{
			destination.write(NAME);

			if (parenthesis)
			{
				if (ARGUMENTS.exists())
				{
					destination.write(Utf8Byte.LEFT_PARENTHESIS);
					ScriptUtil.toStream(destination, ARGUMENTS);
					destination.write(Utf8Byte.RIGHT_PARENTHESIS);
				}
				else
				{
					destination.write(Utf8Byte.LEFT_PARENTHESIS, Utf8Byte.RIGHT_PARENTHESIS);
				}

				if (semicolon) destination.write(Utf8Byte.SEMICOLON);
			}
		}

		@Override
		public String toString()
		{
			return Utf8Util.toString(this);
		}

		public void parenthesis(boolean value)
		{
			parenthesis = value;
		}

		public void semicolon(boolean value)
		{
			semicolon = value;
		}

		public Invoke argument(ScriptObject value)
		{
			ARGUMENTS.add(value);

			return this;
		}

		public Invoke argument(ScriptArray value)
		{
			ARGUMENTS.add(value);

			return this;
		}

		public Invoke argument(AScriptValue value)
		{
			ARGUMENTS.add(value);

			return this;
		}

		public Invoke argument(boolean value)
		{
			ARGUMENTS.add(BooleanScriptValue.instance(value));

			return this;
		}

		public Invoke argument(@NullAllow Boolean value)
		{
			ARGUMENTS.add(BooleanScriptValue.instance(value));

			return this;
		}

		public Invoke argument(short value)
		{
			ARGUMENTS.add(new ShortScriptValue(value));

			return this;
		}

		public Invoke argument(@NullAllow Short value)
		{
			ARGUMENTS.add(new ShortScriptValue(value));

			return this;
		}

		public Invoke argument(int value)
		{
			ARGUMENTS.add(new IntScriptValue(value));

			return this;
		}

		public Invoke argument(@NullAllow Integer value)
		{
			ARGUMENTS.add(new IntScriptValue(value));

			return this;
		}

		public Invoke argument(long value)
		{
			ARGUMENTS.add(new LongScriptValue(value));

			return this;
		}

		public Invoke argument(@NullAllow Long value)
		{
			ARGUMENTS.add(new LongScriptValue(value));

			return this;
		}

		public Invoke argument(float value)
		{
			ARGUMENTS.add(new FloatScriptValue(value));

			return this;
		}

		public Invoke argument(@NullAllow Float value)
		{
			ARGUMENTS.add(new FloatScriptValue(value));

			return this;
		}

		public Invoke argument(double value)
		{
			ARGUMENTS.add(new DoubleScriptValue(value));

			return this;
		}

		public Invoke argument(@NullAllow Double value)
		{
			ARGUMENTS.add(new DoubleScriptValue(value));

			return this;
		}

		public Invoke argument(@NullAllow Number value)
		{
			ARGUMENTS.add(new NumberScriptValue(value));

			return this;
		}

		public Invoke argument(@NullAllow CharSequence value)
		{
			ARGUMENTS.add(new StringScriptValue(value));

			return this;
		}

		public Invoke argument(@NullAllow IConst value)
		{
			ARGUMENTS.add(new ConstScriptValue(value));

			return this;
		}

		public Invoke argument(@NullAllow IFactoryObject value)
		{
			ARGUMENTS.add(new FactoryObjectScriptValue(value));

			return this;
		}

		public Invoke argument(boolean value, boolean quotation)
		{
			ARGUMENTS.add(BooleanScriptValue.instance(value, quotation));

			return this;
		}

		public Invoke argument(@NullAllow Boolean value, boolean quotation)
		{
			ARGUMENTS.add(BooleanScriptValue.instance(value, quotation));

			return this;
		}

		public Invoke argument(short value, boolean quotation)
		{
			ARGUMENTS.add(new ShortScriptValue(value, quotation));

			return this;
		}

		public Invoke argument(@NullAllow Short value, boolean quotation)
		{
			ARGUMENTS.add(new ShortScriptValue(value, quotation));

			return this;
		}

		public Invoke argument(int value, boolean quotation)
		{
			ARGUMENTS.add(new IntScriptValue(value, quotation));

			return this;
		}

		public Invoke argument(@NullAllow Integer value, boolean quotation)
		{
			ARGUMENTS.add(new IntScriptValue(value, quotation));

			return this;
		}

		public Invoke argument(long value, boolean quotation)
		{
			ARGUMENTS.add(new LongScriptValue(value, quotation));

			return this;
		}

		public Invoke argument(@NullAllow Long value, boolean quotation)
		{
			ARGUMENTS.add(new LongScriptValue(value, quotation));

			return this;
		}

		public Invoke argument(float value, boolean quotation)
		{
			ARGUMENTS.add(new FloatScriptValue(value, quotation));

			return this;
		}

		public Invoke argument(@NullAllow Float value, boolean quotation)
		{
			ARGUMENTS.add(new FloatScriptValue(value, quotation));

			return this;
		}

		public Invoke argument(double value, boolean quotation)
		{
			ARGUMENTS.add(new DoubleScriptValue(value, quotation));

			return this;
		}

		public Invoke argument(@NullAllow Double value, boolean quotation)
		{
			ARGUMENTS.add(new DoubleScriptValue(value, quotation));

			return this;
		}

		public Invoke argument(@NullAllow Number value, boolean quotation)
		{
			ARGUMENTS.add(new NumberScriptValue(value, quotation));

			return this;
		}

		public Invoke argument(@NullAllow CharSequence value, boolean quotation)
		{
			ARGUMENTS.add(new StringScriptValue(value, quotation));

			return this;
		}

		public Invoke argument(@NullAllow IConst value, boolean quotation)
		{
			ARGUMENTS.add(new ConstScriptValue(value, quotation));

			return this;
		}

		public Invoke argument(@NullAllow IFactoryObject value, boolean quotation)
		{
			ARGUMENTS.add(new FactoryObjectScriptValue(value, quotation));

			return this;
		}

		public AScriptFunction toScriptFunction(String name)
		{
			String content = toString();

			return new AScriptFunction()
			{
				@Override
				protected String _name()
				{
					return name;
				}

				@Override
				protected String _content()
				{
					return "function () { " + content + " }";
				}
			};
		}
	}

}
