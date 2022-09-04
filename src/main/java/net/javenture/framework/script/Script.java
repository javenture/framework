package net.javenture.framework.script;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.IFactoryObject;
import net.javenture.framework.model.AModel;
import net.javenture.framework.utf8.IUtf8StreamableEntry;
import net.javenture.framework.utf8.Utf8Byte;
import net.javenture.framework.util.IConst;


/*
	2020/10/09
 */
final public class Script
{
	//
	private Script()
	{
	}


	//
	public static <T> IUtf8StreamableEntry init(AModel<T> model)                                                           // ???
	{
		return destination ->
		{
			destination.write(model.alias());
			destination.write(Utf8Byte.SPACE, Utf8Byte.EQUAL, Utf8Byte.SPACE);
			destination.write(model.toScriptEntry());
		};
	}


	public static <T> IUtf8StreamableEntry assignment(AModel<T> model, T value)
	{
		return destination ->
		{
			destination.write(model.alias());
			destination.write(Utf8Byte.SPACE, Utf8Byte.EQUAL, Utf8Byte.SPACE);
			destination.write(model.toScriptEntry(value));
		};
	}


	public static <T> IUtf8StreamableEntry assignment(AModel<T> model0, AModel<T> model1)
	{
		return destination ->
		{
			destination.write(model0.alias());
			destination.write(Utf8Byte.SPACE, Utf8Byte.EQUAL, Utf8Byte.SPACE);
			destination.write(model1.alias());
		};
	}


	public static IUtf8StreamableEntry toggle(AModel<Boolean> model)
	{
		return destination ->
		{
			destination.write(model.alias());
			destination.write(Utf8Byte.SPACE, Utf8Byte.EQUAL, Utf8Byte.SPACE, Utf8Byte.EXCLAMATION);
			destination.write(model.alias());
		};
	}











	//
	public static ScriptObject object()
	{
		return new ScriptObject();
	}


	public static ScriptObject object(IScriptEntry... entries)
	{
		return new ScriptObject(entries);
	}


	public static ScriptObject object(String name)
	{
		return new ScriptObject(name);
	}


	public static ScriptObject object(String name, IScriptEntry... entries)
	{
		return new ScriptObject(name, entries);
	}


	public static ScriptArray array()
	{
		return new ScriptArray();
	}


	public static ScriptArray array(IScriptEntry... entries)
	{
		return new ScriptArray(entries);
	}


	public static ScriptArray array(String name)
	{
		return new ScriptArray(name);
	}


	public static ScriptArray array(String name, IScriptEntry... entries)
	{
		return new ScriptArray(name, entries);
	}


	public static ScriptProperty property(@NullDisallow String name, @NullDisallow IScriptEntry entry)
	{
		return new ScriptProperty(name, entry);
	}


	public static ScriptProperty property(@NullDisallow String name, @NullDisallow AScriptValue value)
	{
		return new ScriptProperty(name, value);
	}


	public static ScriptProperty property(@NullDisallow String name, boolean value)
	{
		return new ScriptProperty(name, value);
	}


	public static ScriptProperty property(@NullDisallow String name, boolean value, boolean quotation)
	{
		return new ScriptProperty(name, value, quotation);
	}


	public static ScriptProperty property(@NullDisallow String name, @NullAllow Boolean value)
	{
		return new ScriptProperty(name, value);
	}


	public static ScriptProperty property(@NullDisallow String name, @NullAllow Boolean value, boolean quotation)
	{
		return new ScriptProperty(name, value, quotation);
	}


	public static ScriptProperty property(@NullDisallow String name, short value)
	{
		return new ScriptProperty(name, value);
	}


	public static ScriptProperty property(@NullDisallow String name, short value, boolean quotation)
	{
		return new ScriptProperty(name, value, quotation);
	}


	public static ScriptProperty property(@NullDisallow String name, @NullAllow Short value)
	{
		return new ScriptProperty(name, value);
	}


	public static ScriptProperty property(@NullDisallow String name, @NullAllow Short value, boolean quotation)
	{
		return new ScriptProperty(name, value, quotation);
	}


	public static ScriptProperty property(@NullDisallow String name, int value)
	{
		return new ScriptProperty(name, value);
	}


	public static ScriptProperty property(@NullDisallow String name, int value, boolean quotation)
	{
		return new ScriptProperty(name, value, quotation);
	}


	public static ScriptProperty property(@NullDisallow String name, @NullAllow Integer value)
	{
		return new ScriptProperty(name, value);
	}


	public static ScriptProperty property(@NullDisallow String name, @NullAllow Integer value, boolean quotation)
	{
		return new ScriptProperty(name, value, quotation);
	}


	public static ScriptProperty property(@NullDisallow String name, long value)
	{
		return new ScriptProperty(name, value);
	}


	public static ScriptProperty property(@NullDisallow String name, long value, boolean quotation)
	{
		return new ScriptProperty(name, value, quotation);
	}


	public static ScriptProperty property(@NullDisallow String name, @NullAllow Long value)
	{
		return new ScriptProperty(name, value);
	}


	public static ScriptProperty property(@NullDisallow String name, @NullAllow Long value, boolean quotation)
	{
		return new ScriptProperty(name, value, quotation);
	}


	public static ScriptProperty property(@NullDisallow String name, float value)
	{
		return new ScriptProperty(name, value);
	}


	public static ScriptProperty property(@NullDisallow String name, float value, boolean quotation)
	{
		return new ScriptProperty(name, value, quotation);
	}


	public static ScriptProperty property(@NullDisallow String name, @NullAllow Float value)
	{
		return new ScriptProperty(name, value);
	}


	public static ScriptProperty property(@NullDisallow String name, @NullAllow Float value, boolean quotation)
	{
		return new ScriptProperty(name, value, quotation);
	}


	public static ScriptProperty property(@NullDisallow String name, double value)
	{
		return new ScriptProperty(name, value);
	}


	public static ScriptProperty property(@NullDisallow String name, double value, boolean quotation)
	{
		return new ScriptProperty(name, value, quotation);
	}


	public static ScriptProperty property(@NullDisallow String name, @NullAllow Double value)
	{
		return new ScriptProperty(name, value);
	}


	public static ScriptProperty property(@NullDisallow String name, @NullAllow Double value, boolean quotation)
	{
		return new ScriptProperty(name, value, quotation);
	}


	public static ScriptProperty property(@NullDisallow String name, @NullAllow Number value)
	{
		return new ScriptProperty(name, value);
	}


	public static ScriptProperty property(@NullDisallow String name, @NullAllow Number value, boolean quotation)
	{
		return new ScriptProperty(name, value, quotation);
	}


	public static ScriptProperty property(@NullDisallow String name, @NullAllow byte[] value)
	{
		return new ScriptProperty(name, value);
	}


	public static ScriptProperty property(@NullDisallow String name, @NullAllow byte[] value, boolean quotation)
	{
		return new ScriptProperty(name, value, quotation);
	}


	public static ScriptProperty property(@NullDisallow String name, @NullAllow CharSequence value)
	{
		return new ScriptProperty(name, value);
	}


	public static ScriptProperty property(@NullDisallow String name, @NullAllow CharSequence value, boolean quotation)
	{
		return new ScriptProperty(name, value, quotation);
	}


	public static ScriptProperty property(@NullDisallow String name, @NullDisallow IConst value)
	{
		return new ScriptProperty(name, value);
	}


	public static ScriptProperty property(@NullDisallow String name, @NullDisallow IConst value, boolean quotation)
	{
		return new ScriptProperty(name, value, quotation);
	}


	public static ScriptProperty property(@NullDisallow String name, @NullAllow IFactoryObject value)
	{
		return new ScriptProperty(name, value);
	}


	public static ScriptProperty property(@NullDisallow String name, @NullAllow IFactoryObject value, boolean quotation)
	{
		return new ScriptProperty(name, value, quotation);
	}


	public static ScriptProperty property(@NullDisallow String name, @NullDisallow IUtf8StreamableEntry value)
	{
		return new ScriptProperty(name, value);
	}


	public static ScriptProperty property(@NullDisallow String name, @NullDisallow IUtf8StreamableEntry value, boolean quotation)
	{
		return new ScriptProperty(name, value, quotation);
	}


	public static <V> ScriptProperty property(@NullDisallow AModel<V> model, @NullAllow V value)
	{
		return new ScriptProperty(model, value);
	}

}
