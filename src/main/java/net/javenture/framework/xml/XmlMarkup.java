package net.javenture.framework.xml;


final public class XmlMarkup extends XmlElement
{
	//
	public XmlMarkup(Preset preset)
	{
		super(Type.MARKUP, preset);
	}


	public XmlMarkup(Preset preset, XmlElement child)
	{
		super(Type.MARKUP, preset, child);
	}


	public XmlMarkup(Preset preset, Object child)
	{
		super(Type.MARKUP, preset, child);
	}


	public XmlMarkup(String name)
	{
		super(Type.MARKUP, name);
	}


	public XmlMarkup(String name, XmlElement child)
	{
		super(Type.MARKUP, name, child);
	}


	public XmlMarkup(String name, Object child)
	{
		super(Type.MARKUP, name, child);
	}


	//
	final public static class Preset extends XmlElement.Preset
	{
		public Preset(String name)
		{
			super(name);
		}
	}

}
