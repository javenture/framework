package net.javenture.framework.script;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.json.IJsonStreamableEntry;
import net.javenture.framework.utf8.IUtf8StreamableEntry;


/*
	2019/07/19
 */
public interface IScriptEntry
	extends IUtf8StreamableEntry, IScriptStreamableEntry, IJsonStreamableEntry, Iterable<IScriptEntry>
{
	//
	Type type();
	@NullAllow String name();


	//
	enum Type
	{
		OBJECT,
		ARRAY,
		PROPERTY,
		VALUE
	}

}
