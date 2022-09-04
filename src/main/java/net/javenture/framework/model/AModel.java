package net.javenture.framework.model;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.firebird.ADatabaseField;
import net.javenture.framework.json.JsonDocument;
import net.javenture.framework.json.JsonObject;
import net.javenture.framework.netty.NettyParameters;
import net.javenture.framework.script.IScriptEntry;
import net.javenture.framework.script.ScriptObject;
import net.javenture.framework.script.ScriptProperty;
import net.javenture.framework.util.IParser;
import net.javenture.framework.util.StringArrayUtil;
import net.javenture.framework.util.StringFragmenter;
import net.javenture.framework.util.Validator;

import java.util.List;


/*
	2019/11/08
 */
abstract public class AModel<V>
{
	//
	final private @NullAllow ADatabaseField<V> FIELD;
	final protected @NullDisallow String NAME;
	final protected @NullDisallow List<String> OBJECTS;
	final protected @NullDisallow String ALIAS;


	//
	protected AModel(@NullDisallow ADatabaseField<V> field)
	{
		Validator.notNull(field, "[field]");

		FIELD = field;
		NAME = field.name();
		OBJECTS = List.of(field.table());
		ALIAS = field.alias();
	}


	protected AModel(@NullDisallow String name, @NullDisallow String... objects)
	{
		Validator.notNull(name, "[name]");
		Validator.argument(!name.isEmpty(), "[name] is empty");

		//
		FIELD = null;
		NAME = name;

		if (objects.length != 0)
		{
			StringFragmenter fragmenter = new StringFragmenter(".");

			for (String object : objects)
			{
				Validator.notNull(object, "[object]");
				Validator.argument(!object.isEmpty(), "[object] is empty");
				fragmenter.add(object);
			}

			fragmenter.add(name);
			String alias = fragmenter.toString();

			//
			OBJECTS = List.of(objects);
			ALIAS = alias;
		}
		else
		{
			OBJECTS = List.of();
			ALIAS = name;
		}
	}


	protected AModel(@NullDisallow String name, @NullDisallow List<String> objects)
	{
		Validator.notNull(name, "[name]");
		Validator.argument(!name.isEmpty(), "[name] is empty");

		//
		StringFragmenter fragmenter = new StringFragmenter(".");

		for (String object : objects)
		{
			Validator.notNull(object, "[object]");
			Validator.argument(!object.isEmpty(), "[object] is empty");
			fragmenter.add(object);
		}

		fragmenter.add(name);

		//
		FIELD = null;
		NAME = name;
		OBJECTS = List.of(objects.toArray(StringArrayUtil.BLANK));
		ALIAS = fragmenter.toString();
	}


	//
	abstract public boolean nullable();
	abstract public boolean quotation();
	abstract public boolean trim();
	abstract public @NullAllow V init();
	abstract public @NullDisallow IScriptEntry toScriptEntry();
	abstract public @NullDisallow IScriptEntry toScriptEntry(@NullAllow V value);
	abstract public @NullDisallow void toNettyParameter(NettyParameters destination);
	abstract public @NullDisallow void toNettyParameter(@NullAllow V value, NettyParameters destination);
	abstract public @NullDisallow IParser.Report<V> parse(@NullAllow CharSequence source);
	abstract public @NullDisallow IParser.Report<V> parse(@NullAllow JsonObject source);
	abstract public boolean defined(@NullAllow V value);


	//
	final public @NullAllow ADatabaseField<V> field()
	{
		return FIELD;
	}


	final public @NullDisallow String name()
	{
		return NAME;
	}


	final public @NullDisallow List<String> objects()
	{
		return OBJECTS;
	}


	final public @NullDisallow String alias()
	{
		return ALIAS;
	}


	final public @NullDisallow IScriptEntry toScriptStructure()
	{
		IScriptEntry entry = toScriptEntry();

		return structure(new ScriptProperty(NAME, entry));
	}


	final public @NullDisallow IScriptEntry toScriptStructure(@NullAllow V value)
	{
		IScriptEntry entry = toScriptEntry(value);

		return structure(new ScriptProperty(NAME, entry));

	}


	final public @NullDisallow IParser.Report<V> parse(@NullDisallow NettyParameters parameters)
	{
		return parse(parameters.getString(ALIAS));
	}


	final public @NullDisallow IParser.Report<V> parse(@NullDisallow JsonDocument document)
	{
		return parse(document.getJsonObject(OBJECTS));
	}


	final public @NullDisallow IScriptEntry structure(@NullDisallow IScriptEntry entry)
	{
		int size = OBJECTS.size();

		if (size != 0)
		{
			ScriptObject result = new ScriptObject(OBJECTS.get(0));
			ScriptObject object = result;

			for (int i = 1; i < size; i++)
			{
				ScriptObject object2 = new ScriptObject(OBJECTS.get(i));
				object.add(object2);
				object = object2;
			}

			object.add(entry);

			return result;
		}
		else
		{
			return entry;
		}
	}

}
