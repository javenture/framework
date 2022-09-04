package net.javenture.framework.xml;


final public class XmlContent extends XmlElement
{
	//
	public XmlContent(Preset preset)
	{
		super(Type.CONTENT, preset);
	}


	public XmlContent(Preset preset, XmlElement child)
	{
		super(Type.CONTENT, preset, child);
	}


	public XmlContent(Preset preset, Object child)
	{
		super(Type.CONTENT, preset, child);
	}


	public XmlContent(String name)
	{
		super(Type.CONTENT, name);
	}


	public XmlContent(String name, XmlElement child)
	{
		super(Type.CONTENT, name, child);
	}


	public XmlContent(String name, Object child)
	{
		super(Type.CONTENT, name, child);
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
