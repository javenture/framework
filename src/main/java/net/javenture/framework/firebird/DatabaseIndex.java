package net.javenture.framework.firebird;


/*
	2017/10/30
 */
final public class DatabaseIndex
{
	//
	public enum Type
	{
		NONE,
		ASCENDING,
		DESCENDING
	}


	//
	final private String NAME;
	final private boolean UNIQUE;
	final private Type TYPE;
	final private ADatabaseField[] FIELDS;


	//
	public DatabaseIndex(String name, boolean unique, Type type, ADatabaseField... fields)
	{
		NAME = name;
		UNIQUE = unique;
		TYPE = type;
		FIELDS = fields;
	}

}
