package net.javenture.framework.json;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.util.StringUtil;
import net.javenture.framework.util.Validator;

import java.util.ArrayList;
import java.util.Iterator;


/*
	2019/07/26
 */
final public class JsonObject
	implements IJsonEntry, Iterable<JsonObject.Pair>
{
	//
	final private ArrayList<Pair> PAIRS; // ! ArrayList


	//
	JsonObject()
	{
		PAIRS = new ArrayList<>();
	}


	//
	@Override
	public Type type()
	{
		return Type.OBJECT;
	}


	@Override
	public Iterator<Pair> iterator()
	{
		return PAIRS.iterator();
	}


	public int size()
	{
		return PAIRS.size();
	}


	void add(@NullDisallow CharSequence name, @NullDisallow IJsonEntry value)
	{
		PAIRS.add(new Pair(name, value));
	}


	public @NullAllow IJsonEntry get(@NullDisallow CharSequence name)
	{
		Validator.notNull(name, "[name]");

		for (Pair pair : PAIRS)
		{
			if (StringUtil.equal(pair.NAME, name)) return pair.VALUE;
		}

		return null;
	}


	public @NullAllow JsonObject getJsonObject(@NullDisallow CharSequence name)
	{
		IJsonEntry entry = get(name);

		return entry != null && entry.type() == Type.OBJECT
			? (JsonObject) entry
			: null;
	}


	public @NullAllow JsonArray getJsonArray(@NullDisallow CharSequence name)
	{
		IJsonEntry entry = get(name);

		return entry != null && entry.type() == Type.ARRAY
			? (JsonArray) entry
			: null;
	}


	public @NullAllow IJsonValue getJsonValue(@NullDisallow CharSequence name)
	{
		IJsonEntry entry = get(name);

		return
			entry != null
			&&
			(
				entry.type() == Type.STRING
				||
				entry.type() == Type.NUMBER
				||
				entry.type() == Type.BOOLEAN
				||
				entry.type() == Type.NULL
			)
				? (IJsonValue) entry
				: null;
	}


	public @NullAllow StringJsonValue getJsonString(@NullDisallow CharSequence name)
	{
		IJsonEntry entry = get(name);

		return entry != null && entry.type() == Type.STRING
			? (StringJsonValue) entry
			: null;
	}


	public @NullAllow NumberJsonValue getJsonNumber(@NullDisallow CharSequence name)
	{
		IJsonEntry entry = get(name);

		return entry != null && entry.type() == Type.NUMBER
			? (NumberJsonValue) entry
			: null;
	}


	public @NullAllow BooleanJsonValue getJsonBoolean(@NullDisallow CharSequence name)
	{
		IJsonEntry entry = get(name);

		return entry != null && entry.type() == Type.BOOLEAN
			? (BooleanJsonValue) entry
			: null;
	}


	public boolean isJsonNull(@NullDisallow CharSequence name)
	{
		IJsonEntry entry = get(name);

		return entry != null && entry.type() == Type.NULL;
	}


	//
	final public static class Pair
	{
		//
		final private @NullDisallow CharSequence NAME;
		final private @NullDisallow IJsonEntry VALUE;

		//
		private Pair(CharSequence name, IJsonEntry value)
		{
			NAME = name;
			VALUE = value;
		}

		//
		public CharSequence name()
		{
			return NAME;
		}

		public IJsonEntry value()
		{
			return VALUE;
		}
	}

}
