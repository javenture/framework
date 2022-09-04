package net.javenture.framework.xml;


import java.util.ArrayList;


/*
	OK: 2017/05/17
 */
final public class XmlAttribute
{
	final char[] NAME;
	final ArrayList<Value> VALUES;


	//
	private XmlAttribute(char[] name, char[] value, boolean secure)
	{
		NAME = name;
		VALUES = new ArrayList<>(1);
		VALUES.add(new Value(value, secure));
	}


	private XmlAttribute(char[] name, String value, boolean secure)
	{
		NAME = name;
		VALUES = new ArrayList<>(1);
		VALUES.add(new Value(value, secure));
	}


	private XmlAttribute(String name, String value, boolean secure)
	{
		NAME = name.toCharArray();
		VALUES = new ArrayList<>(1);
		VALUES.add(new Value(value, secure));
	}


	public XmlAttribute(Preset preset, Constant constant)
	{
		this(preset.NAME, constant.ARRAY, preset.SECURE);
	}


	public XmlAttribute(Preset preset, int value)
	{
		this(preset.NAME, "" + value, false);
	}


	public XmlAttribute(Preset preset, long value)
	{
		this(preset.NAME, "" + value, false);
	}


	public XmlAttribute(Preset preset, float value)
	{
		this(preset.NAME, "" + value, false);
	}


	public XmlAttribute(Preset preset, double value)
	{
		this(preset.NAME, "" + value, false);
	}


	public XmlAttribute(Preset preset, String value)
	{
		this(preset.NAME, value, preset.SECURE);
	}


	public XmlAttribute(String name, int value)
	{
		this(name, "" + value, false);
	}


	public XmlAttribute(String name, long value)
	{
		this(name, "" + value, false);
	}


	public XmlAttribute(String name, float value)
	{
		this(name, "" + value, false);
	}


	public XmlAttribute(String name, double value)
	{
		this(name, "" + value, false);
	}


	public XmlAttribute(String name, String value)
	{
		this(name, value, true);
	}


	public XmlAttribute(String name)
	{
		NAME = name.toCharArray();
		VALUES = new ArrayList<>();
	}


	public XmlAttribute(String name, Object ... values)
	{
		NAME = name.toCharArray();
		VALUES = new ArrayList<>(values.length);

		value(values);
	}


	//
	public XmlAttribute value(int value)
	{
		VALUES.add(new Value("" + value, false));

		return this;
	}


	public XmlAttribute value(long value)
	{
		VALUES.add(new Value("" + value, false));

		return this;
	}


	public XmlAttribute value(float value)
	{
		VALUES.add(new Value("" + value, false));

		return this;
	}


	public XmlAttribute value(double value)
	{
		VALUES.add(new Value("" + value, false));

		return this;
	}


	public XmlAttribute value(String value)
	{
		if (value != null) VALUES.add(new Value(value, true));

		return this;
	}


	public XmlAttribute value(String ... values)
	{
		for (String value : values) value(value);

		return this;
	}


	public XmlAttribute value(XmlEntity entity)
	{
		VALUES.add(new Value(entity.VALUE, false));

		return this;
	}


	public XmlAttribute value(XmlReference reference)
	{
		VALUES.add(new Value(reference.VALUE, false));

		return this;
	}


	public XmlAttribute value(Object ... objects)
	{
		VALUES.ensureCapacity(VALUES.size() + objects.length);

		for (Object value : objects)
		{
			if (value != null)
			{
				Class<?> cl = value.getClass();

				if (cl == String.class)
				{
					String s = (String) value;
					VALUES.add(new Value(s, true));
				}
				else if (cl == Integer.class || cl == Long.class || cl == Float.class || cl == Double.class)
				{
					String s = "" + value;
					VALUES.add(new Value(s, false));
				}
				else if (cl == XmlEntity.class)
				{
					XmlEntity entity = (XmlEntity) value;
					VALUES.add(new Value(entity.VALUE, false));
				}
				else if (cl == XmlReference.class)
				{
					XmlReference reference = (XmlReference) value;
					VALUES.add(new Value(reference.VALUE, false));
				}
				else
				{
					String s = value.toString();
					VALUES.add(new Value(s, true));
				}
			}
		}

		return this;
	}


	//
	final public static class Preset
	{
		final char[] NAME;
		final boolean SECURE;

		public Preset(String name, boolean secure)
		{
			NAME = name.toCharArray();
			SECURE = secure;
		}
	}


	public static class Constant
	{
		final private char[] ARRAY;

		public Constant(String s)
		{
			ARRAY = s.toCharArray();
		}

		public Constant(char[] array)
		{
			ARRAY = array.clone();
		}
	}


	final static class Value
	{
		final char[] ARRAY;
		final boolean SECURE;

		private Value(char[] value, boolean secure)
		{
			ARRAY = value;
			SECURE = secure;
		}

		private Value(String value, boolean secure)
		{
			ARRAY = value.toCharArray();
			SECURE = secure;
		}
	}

}
