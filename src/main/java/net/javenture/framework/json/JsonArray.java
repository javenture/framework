package net.javenture.framework.json;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;

import java.util.ArrayList;
import java.util.Iterator;


/*
	2019/07/26
 */
final public class JsonArray
	implements IJsonEntry, Iterable<IJsonEntry>
{
	//
	final private ArrayList<IJsonEntry> ENTRIES; // ! ArrayList


	//
	JsonArray()
	{
		ENTRIES = new ArrayList<>();
	}


	//
	@Override
	public Type type()
	{
		return Type.ARRAY;
	}


	@Override
	public Iterator<IJsonEntry> iterator()
	{
		return ENTRIES.iterator();
	}


	public int size()
	{
		return ENTRIES.size();
	}


	void add(@NullDisallow IJsonEntry value)
	{
		ENTRIES.add(value);
	}


	public @NullAllow IJsonEntry get(int index)
	{
		return index >= 0 && index < size()
			? ENTRIES.get(index)
			: null;
	}


	public @NullAllow JsonObject getJsonObject(int index)
	{
		IJsonEntry entry = get(index);

		return entry != null && entry.type() == Type.OBJECT
			? (JsonObject) entry
			: null;
	}


	public @NullAllow JsonArray getJsonArray(int index)
	{
		IJsonEntry entry = get(index);

		return entry != null && entry.type() == Type.ARRAY
			? (JsonArray) entry
			: null;
	}


	public @NullAllow IJsonValue getJsonValue(int index)
	{
		IJsonEntry entry = get(index);

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


	public @NullDisallow StringJsonValue getJsonString(int index)
	{
		IJsonEntry entry = get(index);

		return entry != null && entry.type() == Type.STRING
			? (StringJsonValue) entry
			: null;
	}


	public @NullDisallow NumberJsonValue getJsonNumber(int index)
	{
		IJsonEntry entry = get(index);

		return entry != null && entry.type() == Type.NUMBER
			? (NumberJsonValue) entry
			: null;
	}


	public @NullDisallow BooleanJsonValue getJsonBoolean(int index)
	{
		IJsonEntry entry = get(index);

		return entry != null && entry.type() == Type.BOOLEAN
			? (BooleanJsonValue) entry
			: null;
	}


	public boolean isJsonNull(int index)
	{
		IJsonEntry entry = get(index);

		return entry != null && entry.type() == Type.NULL;
	}

}
