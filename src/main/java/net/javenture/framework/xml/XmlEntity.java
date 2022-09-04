package net.javenture.framework.xml;


import net.javenture.framework.utf8.Utf8Char;


/*
	OK: 2017/05/17
 */
final public class XmlEntity extends AXmlEntry
{
	final private static Config CONFIG = new Config(Type.ENTITY);


	//
	final public String NAME;
	final public char CHARACTER;

	final char[] VALUE;


	//
	private XmlEntity(String name, char character)
	{
		super(Type.ENTITY);

		//
		NAME = name;
		CHARACTER = character;
		VALUE = ("&" + name + ";").toCharArray();
	}


	//


	//
	final public static XmlEntity QUOT = new XmlEntity("quot", Utf8Char.QUOTATION);
	final public static XmlEntity AMP = new XmlEntity("amp", Utf8Char.AMPERSAND);
	final public static XmlEntity APOS = new XmlEntity("apos", Utf8Char.APOSTROPHE);
	final public static XmlEntity LT = new XmlEntity("lt", Utf8Char.LESS_THEN);
	final public static XmlEntity GT = new XmlEntity("gt", Utf8Char.GREATER_THEN);

}
