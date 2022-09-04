package net.javenture.framework.json;


/*
	2019/07/12
 */
public interface IJsonEntry
{
	//
	Type type();


	//
	enum Type
	{
		OBJECT,
		ARRAY,
		STRING,
		NUMBER,
		BOOLEAN,
		NULL
	}

}
