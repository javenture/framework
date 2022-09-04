package net.javenture.framework.script;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.IFactoryObject;
import net.javenture.framework.model.AModel;
import net.javenture.framework.utf8.IUtf8StreamableEntry;
import net.javenture.framework.utf8.Utf8Byte;
import net.javenture.framework.utf8.Utf8OutputStream;
import net.javenture.framework.util.IConst;
import net.javenture.framework.util.Validator;

import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;


/*
	2019/12/17
 */
final public class ScriptProperty
	implements IScriptEntry
{
	//
	final private @NullDisallow String NAME;
	final private @NullDisallow IScriptEntry ENTRY; // IScriptEntry | AScriptValue


	//
	public ScriptProperty(@NullDisallow String name, @NullDisallow IScriptEntry entry)
	{
		Validator.notNull(name, "[name]");
		Validator.notNull(entry, "[entry]");
		Validator.argument(entry.name() == null, "entry with name is illegal");

		NAME = name;
		ENTRY = entry;
	}


	public ScriptProperty(@NullDisallow String name, @NullDisallow AScriptValue value)
	{
		Validator.notNull(name, "[name]");
		Validator.notNull(value, "[value]");

		NAME = name;
		ENTRY = value;
	}


	public ScriptProperty(@NullDisallow String name, boolean value)
	{
		Validator.notNull(name, "[name]");

		NAME = name;
		ENTRY = new BooleanScriptValue(value);
	}


	public ScriptProperty(@NullDisallow String name, boolean value, boolean quotation)
	{
		Validator.notNull(name, "[name]");

		NAME = name;
		ENTRY = new BooleanScriptValue(value, quotation);
	}


	public ScriptProperty(@NullDisallow String name, @NullAllow Boolean value)
	{
		Validator.notNull(name, "[name]");

		NAME = name;
		ENTRY = new BooleanScriptValue(value);
	}


	public ScriptProperty(@NullDisallow String name, @NullAllow Boolean value, boolean quotation)
	{
		Validator.notNull(name, "[name]");

		NAME = name;
		ENTRY = new BooleanScriptValue(value, quotation);
	}


	public ScriptProperty(@NullDisallow String name, short value)
	{
		Validator.notNull(name, "[name]");

		NAME = name;
		ENTRY = new ShortScriptValue(value);
	}


	public ScriptProperty(@NullDisallow String name, short value, boolean quotation)
	{
		Validator.notNull(name, "[name]");

		NAME = name;
		ENTRY = new ShortScriptValue(value, quotation);
	}


	public ScriptProperty(@NullDisallow String name, @NullAllow Short value)
	{
		Validator.notNull(name, "[name]");

		NAME = name;
		ENTRY = new ShortScriptValue(value);
	}


	public ScriptProperty(@NullDisallow String name, @NullAllow Short value, boolean quotation)
	{
		Validator.notNull(name, "[name]");

		NAME = name;
		ENTRY = new ShortScriptValue(value, quotation);
	}


	public ScriptProperty(@NullDisallow String name, int value)
	{
		Validator.notNull(name, "[name]");

		NAME = name;
		ENTRY = new IntScriptValue(value);
	}


	public ScriptProperty(@NullDisallow String name, int value, boolean quotation)
	{
		Validator.notNull(name, "[name]");

		NAME = name;
		ENTRY = new IntScriptValue(value, quotation);
	}


	public ScriptProperty(@NullDisallow String name, @NullAllow Integer value)
	{
		Validator.notNull(name, "[name]");

		NAME = name;
		ENTRY = new IntScriptValue(value);
	}


	public ScriptProperty(@NullDisallow String name, @NullAllow Integer value, boolean quotation)
	{
		Validator.notNull(name, "[name]");

		NAME = name;
		ENTRY = new IntScriptValue(value, quotation);
	}


	public ScriptProperty(@NullDisallow String name, long value)
	{
		Validator.notNull(name, "[name]");

		NAME = name;
		ENTRY = new LongScriptValue(value);
	}


	public ScriptProperty(@NullDisallow String name, long value, boolean quotation)
	{
		Validator.notNull(name, "[name]");

		NAME = name;
		ENTRY = new LongScriptValue(value, quotation);
	}


	public ScriptProperty(@NullDisallow String name, @NullAllow Long value)
	{
		Validator.notNull(name, "[name]");

		NAME = name;
		ENTRY = new LongScriptValue(value);
	}


	public ScriptProperty(@NullDisallow String name, @NullAllow Long value, boolean quotation)
	{
		Validator.notNull(name, "[name]");

		NAME = name;
		ENTRY = new LongScriptValue(value, quotation);
	}


	public ScriptProperty(@NullDisallow String name, float value)
	{
		Validator.notNull(name, "[name]");

		NAME = name;
		ENTRY = new FloatScriptValue(value);
	}


	public ScriptProperty(@NullDisallow String name, float value, boolean quotation)
	{
		Validator.notNull(name, "[name]");

		NAME = name;
		ENTRY = new FloatScriptValue(value, quotation);
	}


	public ScriptProperty(@NullDisallow String name, @NullAllow Float value)
	{
		Validator.notNull(name, "[name]");

		NAME = name;
		ENTRY = new FloatScriptValue(value);
	}


	public ScriptProperty(@NullDisallow String name, @NullAllow Float value, boolean quotation)
	{
		Validator.notNull(name, "[name]");

		NAME = name;
		ENTRY = new FloatScriptValue(value, quotation);
	}


	public ScriptProperty(@NullDisallow String name, double value)
	{
		Validator.notNull(name, "[name]");

		NAME = name;
		ENTRY = new DoubleScriptValue(value);
	}


	public ScriptProperty(@NullDisallow String name, double value, boolean quotation)
	{
		Validator.notNull(name, "[name]");

		NAME = name;
		ENTRY = new DoubleScriptValue(value, quotation);
	}


	public ScriptProperty(@NullDisallow String name, @NullAllow Double value)
	{
		Validator.notNull(name, "[name]");

		NAME = name;
		ENTRY = new DoubleScriptValue(value);
	}


	public ScriptProperty(@NullDisallow String name, @NullAllow Double value, boolean quotation)
	{
		Validator.notNull(name, "[name]");

		NAME = name;
		ENTRY = new DoubleScriptValue(value, quotation);
	}


	public ScriptProperty(@NullDisallow String name, @NullAllow Number value)
	{
		Validator.notNull(name, "[name]");

		NAME = name;
		ENTRY = new NumberScriptValue(value);
	}


	public ScriptProperty(@NullDisallow String name, @NullAllow Number value, boolean quotation)
	{
		Validator.notNull(name, "[name]");

		NAME = name;
		ENTRY = new NumberScriptValue(value, quotation);
	}


	public ScriptProperty(@NullDisallow String name, @NullAllow byte[] value)
	{
		Validator.notNull(name, "[name]");

		NAME = name;
		ENTRY = new BytesScriptValue(value);
	}


	public ScriptProperty(@NullDisallow String name, @NullAllow byte[] value, boolean quotation)
	{
		Validator.notNull(name, "[name]");

		NAME = name;
		ENTRY = new BytesScriptValue(value, quotation);
	}


	public ScriptProperty(@NullDisallow String name, @NullAllow CharSequence value)
	{
		Validator.notNull(name, "[name]");

		NAME = name;
		ENTRY = new StringScriptValue(value);
	}


	public ScriptProperty(@NullDisallow String name, @NullAllow CharSequence value, boolean quotation)
	{
		Validator.notNull(name, "[name]");

		NAME = name;
		ENTRY = new StringScriptValue(value, quotation);
	}


	public ScriptProperty(@NullDisallow String name, @NullDisallow IConst value)
	{
		Validator.notNull(name, "[name]");

		NAME = name;
		ENTRY = new IntScriptValue(value.id());
	}


	public ScriptProperty(@NullDisallow String name, @NullDisallow IConst value, boolean quotation)
	{
		Validator.notNull(name, "[name]");

		NAME = name;
		ENTRY = new IntScriptValue(value.id(), quotation);
	}


	public ScriptProperty(@NullDisallow String name, @NullAllow IFactoryObject value)
	{
		Validator.notNull(name, "[name]");

		NAME = name;
		ENTRY = new FactoryObjectScriptValue(value);
	}


	public ScriptProperty(@NullDisallow String name, @NullAllow IFactoryObject value, boolean quotation)
	{
		Validator.notNull(name, "[name]");

		NAME = name;
		ENTRY = new FactoryObjectScriptValue(value, quotation);
	}


	public ScriptProperty(@NullDisallow String name, @NullDisallow IUtf8StreamableEntry value)
	{
		Validator.notNull(name, "[name]");

		NAME = name;
		ENTRY = new Utf8StreamableScriptValue(value);
	}


	public ScriptProperty(@NullDisallow String name, @NullDisallow IUtf8StreamableEntry value, boolean quotation)
	{
		Validator.notNull(name, "[name]");

		NAME = name;
		ENTRY = new Utf8StreamableScriptValue(value, quotation);
	}


	public <V> ScriptProperty(@NullDisallow AModel<V> model, @NullAllow V value)
	{
		NAME = model.name();
		ENTRY = model.toScriptEntry(value);
	}


	//
	@Override
	public Type type()
	{
		return Type.PROPERTY;
	}


	@Override
	public @NullDisallow String name()
	{
		return NAME;
	}


	@Override
	public void toUtf8Stream(Utf8OutputStream destination)
		throws IOException
	{
		destination.write(NAME);
		destination.write(Utf8Byte.COLON, Utf8Byte.SPACE);
		ENTRY.toScriptStream(destination);
	}


	@Override
	public void toScriptStream(Utf8OutputStream destination)
		throws IOException
	{
		destination.write(NAME);
		destination.write(Utf8Byte.COLON, Utf8Byte.SPACE);
		ENTRY.toScriptStream(destination);
	}


	@Override
	public void toJsonStream(Utf8OutputStream destination)
		throws IOException
	{
		destination.write(Utf8Byte.QUOTATION);
		destination.write(NAME);
		destination.write(Utf8Byte.QUOTATION, Utf8Byte.COLON, Utf8Byte.SPACE);
		ENTRY.toJsonStream(destination);
	}


	@Override
	public Iterator<IScriptEntry> iterator()
	{
		return new Iterator<>()
		{
			//
			private boolean next = true;

			//
			@Override
			public boolean hasNext()
			{
				return next;
			}

			@Override
			public IScriptEntry next()
			{
				if (next)
				{
					next = false;

					return ENTRY;
				}
				else
				{
					throw new NoSuchElementException();
				}
			}
		};
	}


	@Override
	public String toString()
	{
		return ScriptUtil.toString(this);
	}

}
