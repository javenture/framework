package net.javenture.framework.xml;


import net.javenture.framework.util.CharArrayUtil;


/*
	OK: 2017/05/17
 */
final public class XmlText extends AXmlEntry
{
	final public static XmlText SPACE = new XmlText(" ", false);
	final public static XmlText TAB = new XmlText("\t", false);
	final public static XmlText LF = new XmlText("\n", false);
	final public static XmlText CR = new XmlText("\r", false);


	//
	final char[] VALUE;
	final boolean SECURE;


	//
	private XmlText(char[] value, boolean secure)
	{
		super(Type.TEXT);

		VALUE = value;
		SECURE = secure;
	}


	XmlText(String value, boolean secure)
	{
		this(value.toCharArray(), secure);
	}


	public XmlText(String value)
	{
		this(value, true);
	}


	public XmlText(int value)
	{
		this("" + value, false);
	}


	public XmlText(long value)
	{
		this("" + value, false);
	}


	public XmlText(float value)
	{
		this("" + value, false);
	}


	public XmlText(double value)
	{
		this("" + value, false);
	}


	public XmlText(Object value)
	{
		this(value != null ? value.toString().toCharArray() : CharArrayUtil.BLANK, true);
	}

}
