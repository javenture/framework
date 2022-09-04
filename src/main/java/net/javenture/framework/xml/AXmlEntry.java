package net.javenture.framework.xml;


import net.javenture.framework.util.Validator;


/*
	2017/05/18
 */
abstract public class AXmlEntry // public !
{
	//
	//abstract protected Config config();




	enum Type
	{
		MARKUP,
		CONTENT,
		COMMENT,
		TEXT,
		ENTITY,
		REFERENCE
	}


	//
	final Type TYPE;


	//
	AXmlEntry(Type type)
	{
		TYPE = type;
	}



	//
	final public static class Config                                                     // XXX: package
	{
		final Type TYPE;
		final char[] NAME;

		//
		public Config(Type type)
		{
			this(type, "");
		}

		public Config(Type type, String name)
		{
			boolean element = type == Type.MARKUP || type == Type.CONTENT;
			Validator.argument(!element || !name.isEmpty(), "element without name");

			//
			TYPE = type;
			NAME = name.toCharArray();
		}
	}

}
