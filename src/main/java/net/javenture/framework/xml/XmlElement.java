package net.javenture.framework.xml;


import net.javenture.framework.util.Validator;

import java.util.ArrayList;


class XmlElement extends AXmlEntry
{
	//
	final char[] NAME;
	final ArrayList<XmlAttribute> ATTRIBUTES;                   // XXX: ArrayList ?
	final ArrayList<AXmlEntry> CHILDS;                           // XXX: ArrayList ?


	//
	private XmlElement(Type type, char[] name)
	{
		super(type);

		//
		NAME = name;
		ATTRIBUTES = new ArrayList<>();
		CHILDS = new ArrayList<>();
	}


	XmlElement(Type type, Preset preset)
	{
		super(type);

		NAME = preset.NAME;
		ATTRIBUTES = new ArrayList<>();
		CHILDS = new ArrayList<>();
	}


	XmlElement(Type type, String name)
	{
		super(type);

		//
		Validator.notNull(name, "[name]");
		char[] array = name.toCharArray();
		Checker.name(array);

		//
		NAME = array;
		ATTRIBUTES = new ArrayList<>();
		CHILDS = new ArrayList<>();
	}


	XmlElement(Type type, Preset preset, XmlElement child)
	{
		this(type, preset);

		child(child);
	}


	XmlElement(Type type, String name, XmlElement child)
	{
		this(type, name);

		child(child);
	}


	XmlElement(Type type, Preset preset, Object child)
	{
		this(type, preset);

		child(child);
	}


	XmlElement(Type type, String name, Object child)
	{
		this(type, name);

		child(child);
	}


	/*
		attributes
	 */
	final public XmlElement attribute(XmlAttribute.Preset preset, XmlAttribute.Constant constant)
	{
		ATTRIBUTES.add(new XmlAttribute(preset, constant));

		return this;
	}


	final public XmlElement attribute(XmlAttribute.Preset preset, String value)
	{
		ATTRIBUTES.add(new XmlAttribute(preset, value));

		return this;
	}


	final public XmlElement attribute(XmlAttribute.Preset preset, int value)
	{
		ATTRIBUTES.add(new XmlAttribute(preset, value));

		return this;
	}


	final public XmlElement attribute(XmlAttribute.Preset preset, long value)
	{
		ATTRIBUTES.add(new XmlAttribute(preset, value));

		return this;
	}


	final public XmlElement attribute(XmlAttribute.Preset preset, float value)
	{
		ATTRIBUTES.add(new XmlAttribute(preset, value));

		return this;
	}


	final public XmlElement attribute(XmlAttribute.Preset preset, double value)
	{
		ATTRIBUTES.add(new XmlAttribute(preset, value));

		return this;
	}


	final public XmlElement attribute(String name, String value)
	{
		ATTRIBUTES.add(new XmlAttribute(name, value));

		return this;
	}


	final public XmlElement attribute(String name, int value)
	{
		ATTRIBUTES.add(new XmlAttribute(name, value));

		return this;
	}


	final public XmlElement attribute(String name, long value)
	{
		ATTRIBUTES.add(new XmlAttribute(name, value));

		return this;
	}


	final public XmlElement attribute(String name, float value)
	{
		ATTRIBUTES.add(new XmlAttribute(name, value));

		return this;
	}


	final public XmlElement attribute(String name, double value)
	{
		ATTRIBUTES.add(new XmlAttribute(name, value));

		return this;
	}


	/*
		childs
	 */
	public XmlElement child(AXmlEntry entry)
	{
		switch (this.TYPE)
		{
			case MARKUP:
			{
				if (entry.TYPE == Type.MARKUP || entry.TYPE == Type.CONTENT || entry.TYPE == Type.COMMENT) CHILDS.add(entry);
				else throw new IllegalArgumentException("Structure.MARKUP allow ELEMENT or COMMENT childs only");

				break;
			}

			case CONTENT:
			{
				if (entry.TYPE == Type.TEXT || entry.TYPE == Type.ENTITY || entry.TYPE == Type.REFERENCE) CHILDS.add(entry);
				else throw new IllegalArgumentException("Structure.MARKUP allow only TEXT, ENTITY or REFERENCE childs");

				break;
			}
		}

		return this;
	}


	public XmlElement child(AXmlEntry... entries)
	{
		CHILDS.ensureCapacity(CHILDS.size() + entries.length);

		for (AXmlEntry entry : entries) child(entry);

		return this;
	}


	public XmlElement child(Object object)
	{
		if (object != null)
		{
			AXmlEntry entry;

			if (object instanceof AXmlEntry)
			{
				entry = (AXmlEntry) object;
			}
			else
			{
				Class<?> cl = object.getClass();

				if (cl == String.class) entry = new XmlText((String) object);
				else if (cl == Integer.class) entry = new XmlText((int) object);
				else if (cl == Long.class) entry = new XmlText((long) object);
				else if (cl == Float.class) entry = new XmlText((float) object);
				else if (cl == Double.class) entry = new XmlText((double) object);
				else entry = new XmlText(object.toString());
			}

			child(entry);
		}

		return this;
	}


	public XmlElement child(Object ... objects)
	{
		CHILDS.ensureCapacity(CHILDS.size() + objects.length);

		for (Object object : objects) child(object);

		return this;
	}


	//
	final private static class Checker
	{
		/*
			https://https://en.wikipedia.org/wiki/XML#Well-formedness_and_error-handling
			https://en.wikipedia.org/wiki/Valid_characters_in_XML
		 */
		final private static String ILLEGAL_CHARACTER_1 = "-.0123456789";
		final private static String ILLEGAL_CHARACTER_2 = " \t!\"#$%&'()*+,/;<=>?@[\\]^`{|}~";

		//
		private static void name(char[] array)
		{
			Validator.argument(array.length != 0, "[name] is empty");

			char c0 = array[0];
			Validator.argument(ILLEGAL_CHARACTER_1.indexOf(c0) == -1, "[name] starts with illegal character '", c0 ,"'");

			for (char c : array) Validator.argument(ILLEGAL_CHARACTER_2.indexOf(c) == -1, "[name] contain illegal character '", c ,"'");
		}
	}


	//
	protected static class Preset
	{
		final char[] NAME;

		//
		Preset(char[] name)
		{
			Validator.notNull(name, "[name]");
			char[] array = name.clone();
			Checker.name(array);

			//
			NAME = array;
		}

		Preset(String name)
		{
			Validator.notNull(name, "[name]");
			char[] array = name.toCharArray();
			Checker.name(array);

			//
			NAME = array;
		}
	}

}
