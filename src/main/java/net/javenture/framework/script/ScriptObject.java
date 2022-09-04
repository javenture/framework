package net.javenture.framework.script;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.json.JsonUtil;
import net.javenture.framework.utf8.Utf8Byte;
import net.javenture.framework.utf8.Utf8OutputStream;
import net.javenture.framework.util.StringUtil;
import net.javenture.framework.util.Validator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


/*
	2020/09/27
 */
public class ScriptObject                                                    // XXX: final
	implements IScriptEntry
{
	//
	final private @NullAllow String NAME;
	final private ArrayList<IScriptEntry> ENTRIES; // ! ArrayList


	//
	public ScriptObject(@NullDisallow ScriptObject object)
	{
		this.NAME = object.NAME;
		this.ENTRIES = new ArrayList<>(object.ENTRIES);
	}


	public ScriptObject()
	{
		NAME = null;
		ENTRIES = new ArrayList<>();
	}


	public ScriptObject(int capacity)
	{
		NAME = null;
		ENTRIES = new ArrayList<>(capacity);
	}


	public ScriptObject(IScriptEntry... entries)
	{
		NAME = null;
		ENTRIES = new ArrayList<>(entries.length);

		add(entries);
	}


	public ScriptObject(@NullAllow String name)
	{
		NAME = name;
		ENTRIES = new ArrayList<>();
	}


	public ScriptObject(@NullAllow String name, int capacity)
	{
		NAME = name;
		ENTRIES = new ArrayList<>(capacity);
	}


	public ScriptObject(@NullAllow String name, IScriptEntry... entries)
	{
		NAME = name;
		ENTRIES = new ArrayList<>(entries.length);

		add(entries);
	}


	//
	@Override
	final public Type type()
	{
		return Type.OBJECT;
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
			destination.write(Utf8Byte.LEFT_CURLY_BRACKET, Utf8Byte.SPACE);
			ScriptUtil.toStream(destination, ENTRIES);
			destination.write(Utf8Byte.SPACE, Utf8Byte.RIGHT_CURLY_BRACKET);
		}
		else
		{
			destination.write(Utf8Byte.LEFT_CURLY_BRACKET, Utf8Byte.RIGHT_CURLY_BRACKET);
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
			destination.write(Utf8Byte.LEFT_CURLY_BRACKET, Utf8Byte.SPACE);
			ScriptUtil.toStream(destination, ENTRIES);
			destination.write(Utf8Byte.SPACE, Utf8Byte.RIGHT_CURLY_BRACKET);
		}
		else
		{
			destination.write(Utf8Byte.LEFT_CURLY_BRACKET, Utf8Byte.RIGHT_CURLY_BRACKET);
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
			destination.write(Utf8Byte.LEFT_CURLY_BRACKET, Utf8Byte.SPACE);
			JsonUtil.toStream(destination, ENTRIES);
			destination.write(Utf8Byte.SPACE, Utf8Byte.RIGHT_CURLY_BRACKET);
		}
		else
		{
			destination.write(Utf8Byte.LEFT_CURLY_BRACKET, Utf8Byte.RIGHT_CURLY_BRACKET);
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


	final public boolean exists()
	{
		return !ENTRIES.isEmpty();
	}


	final public int size()
	{
		return ENTRIES.size();
	}


	final public ScriptObject add(@NullDisallow IScriptEntry entry)
	{
		Validator.notNull(entry, "[entry]");
		ENTRIES.add(entry);

		return this;
	}


	final public ScriptObject add(IScriptEntry... entries)
	{
		for (IScriptEntry entry : entries)
		{
			Validator.notNull(entry, "[entry]");
			ENTRIES.add(entry);
		}

		return this;
	}


	final public void ensure(int count)
	{
		ENTRIES.ensureCapacity(size() + count);
	}


	final public void union(@NullDisallow ScriptObject object)
	{
		Validator.argument(StringUtil.equal(this.NAME, object.NAME), "[this.NAME] (", this.NAME, ") != [object.NAME] (", object.NAME, ")");
		this.ENTRIES.addAll(object.ENTRIES);
	}


	private void union0(@NullDisallow ScriptObject object)
	{
		this.ENTRIES.addAll(object.ENTRIES);
	}


	final public ScriptObject compose()
	{
		ScriptObject result = new ScriptObject(NAME);
		int size = size();
		boolean[] exclude = new boolean[size];

		for (int i = 0; i < size; i++)
		{
			if (!exclude[i])
			{
				IScriptEntry entry = ENTRIES.get(i);

				if (entry.type() == Type.OBJECT)
				{
					String name = entry.name();

					if (name != null)
					{
						ScriptObject object = new ScriptObject(name, size);

						{
							ScriptObject object2 = (ScriptObject) entry;
							object.ENTRIES.addAll(object2.ENTRIES);
						}

						//
						boolean change = false;

						for (int j = i + 1; j < size; j++)
						{
							IScriptEntry entry2 = ENTRIES.get(j);

							if (entry2.type() == Type.OBJECT)
							{
								String name2 = entry2.name();

								if (name.equals(name2))
								{
									exclude[j] = true;

									{
										ScriptObject object2 = (ScriptObject) entry2;
										object.ENTRIES.addAll(object2.ENTRIES);
									}

									change = true;
								}
							}
						}

						result.add(change ? object.compose() : object);
					}
				}
				else
				{
					result.add(entry);
				}
			}
		}

		return result;
	}

}
