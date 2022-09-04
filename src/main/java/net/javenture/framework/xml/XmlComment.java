package net.javenture.framework.xml;


/*
	OK: 2017/05/17
 */
final public class XmlComment extends AXmlEntry
{
	final char[] VALUE;


	//
	public XmlComment(String value)
	{
		super(Type.COMMENT);

		VALUE = value.toCharArray();
	}

}
