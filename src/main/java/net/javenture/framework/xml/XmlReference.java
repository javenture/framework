package net.javenture.framework.xml;


/*
	OK: 2017/05/17
 */
final public class XmlReference extends AXmlEntry
{
	/*
	 	https://en.wikipedia.org/wiki/Numeric_character_reference
	 */
	final public static XmlReference HYPHEN = new XmlReference(45); // -


	//
	final char[] VALUE;


	//
	public XmlReference(String hex)
	{
		super(Type.REFERENCE);

		VALUE = ("&#x" + hex + ";").toCharArray();
	}


	public XmlReference(int codepoint)
	{
		super(Type.REFERENCE);

		VALUE = ("&#" + codepoint + ";").toCharArray();
	}

}
