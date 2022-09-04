package net.javenture.framework.script;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullContains;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.json.JsonUtil;
import net.javenture.framework.utf8.Utf8Byte;
import net.javenture.framework.utf8.Utf8OutputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;


/*
	2020/09/27
 */
public class ScriptArray
	implements IScriptEntry
{
	//
	final private @NullAllow String NAME;
	final private @NullContains ArrayList<IScriptEntry> ENTRIES; // ! ArrayList


	//
	public ScriptArray(@NullDisallow ScriptArray array)
	{
		NAME = array.NAME;
		ENTRIES = new ArrayList<>(array.ENTRIES);
	}


	public ScriptArray()
	{
		NAME = null;
		ENTRIES = new ArrayList<>();
	}


	public ScriptArray(int capacity)
	{
		NAME = null;
		ENTRIES = new ArrayList<>(capacity);
	}


	public ScriptArray(IScriptEntry... entries)
	{
		NAME = null;
		ENTRIES = new ArrayList<>(entries.length);

		add(entries);
	}


	public ScriptArray(@NullAllow String name)
	{
		NAME = name;
		ENTRIES = new ArrayList<>();
	}


	public ScriptArray(@NullAllow String name, int capacity)
	{
		NAME = name;
		ENTRIES = new ArrayList<>(capacity);
	}


	public ScriptArray(@NullAllow String name, IScriptEntry... entries)
	{
		NAME = name;
		ENTRIES = new ArrayList<>(entries.length);

		add(entries);
	}


	//
	@Override
	final public Type type()
	{
		return Type.ARRAY;
	}


	@Override
	final public @NullAllow String name()
	{
		return NAME;
	}


	@Override
	final public void toUtf8Stream(Utf8OutputStream destination)
		throws IOException
	{
		if (NAME != null)
		{
			destination.write(NAME);
			destination.write(Utf8Byte.COLON, Utf8Byte.SPACE);
		}

		if (!ENTRIES.isEmpty())
		{
			destination.write(Utf8Byte.LEFT_SQUARE_BRACKET);
			ScriptUtil.toStream(destination, ENTRIES);
			destination.write(Utf8Byte.RIGHT_SQUARE_BRACKET);
		}
		else
		{
			destination.write(Utf8Byte.LEFT_SQUARE_BRACKET, Utf8Byte.RIGHT_SQUARE_BRACKET);
		}
	}


	@Override
	final public void toScriptStream(Utf8OutputStream destination)
		throws IOException
	{
		if (NAME != null)
		{
			destination.write(NAME);
			destination.write(Utf8Byte.COLON, Utf8Byte.SPACE);
		}

		if (!ENTRIES.isEmpty())
		{
			destination.write(Utf8Byte.LEFT_SQUARE_BRACKET);
			ScriptUtil.toStream(destination, ENTRIES);
			destination.write(Utf8Byte.RIGHT_SQUARE_BRACKET);
		}
		else
		{
			destination.write(Utf8Byte.LEFT_SQUARE_BRACKET, Utf8Byte.RIGHT_SQUARE_BRACKET);
		}
	}


	@Override
	final public void toJsonStream(Utf8OutputStream destination)
		throws IOException
	{
		if (NAME != null)
		{
			destination.write(Utf8Byte.QUOTATION);
			destination.write(NAME);
			destination.write(Utf8Byte.QUOTATION, Utf8Byte.COLON, Utf8Byte.SPACE);
		}

		if (!ENTRIES.isEmpty())
		{
			destination.write(Utf8Byte.LEFT_SQUARE_BRACKET);
			JsonUtil.toStream(destination, ENTRIES);
			destination.write(Utf8Byte.RIGHT_SQUARE_BRACKET);
		}
		else
		{
			destination.write(Utf8Byte.LEFT_SQUARE_BRACKET, Utf8Byte.RIGHT_SQUARE_BRACKET);
		}
	}


	@Override
	final public Iterator<IScriptEntry> iterator()
	{
		return ENTRIES.iterator();
	}


	@Override
	final public String toString()
	{
		return ScriptUtil.toString(this);
	}


	final public boolean exist()
	{
		return !ENTRIES.isEmpty();
	}


	final public int size()
	{
		return ENTRIES.size();
	}


	final public ScriptArray add(@NullAllow IScriptEntry entry)
	{
		ENTRIES.add(entry);

		return this;
	}


	final public ScriptArray add(@NullContains IScriptEntry... entries)
	{
		ENTRIES.addAll(Arrays.asList(entries));

		return this;
	}


	final public void ensure(int count)
	{
		ENTRIES.ensureCapacity(size() + count);
	}

}
