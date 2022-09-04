package net.javenture.framework.xml;


import net.javenture.framework.log.Log;
import net.javenture.framework.utf8.Utf8InputStream;
import net.javenture.framework.utf8.Utf8OutputStream;

import java.io.IOException;


final public class XmlDocument
{
	final private static Log LOG = Log.instance(XmlDocument.class);


	//
	final private XmlMarkup ROOT;


	//
	public XmlDocument(XmlMarkup.Preset root)
	{
		ROOT = new XmlMarkup(root);
	}


	public XmlDocument(String root)
	{
		ROOT = new XmlMarkup(root);
	}


	//
	public XmlMarkup root()
	{
		return ROOT;
	}


	public void write(Utf8OutputStream stream) throws IOException
	{
		XmlBuilder.execute(ROOT, stream);
	}


	@Override
	public String toString()
	{
		try
		{
			Utf8OutputStream output = new Utf8OutputStream();
			this.write(output);
			Utf8InputStream input = new Utf8InputStream(output.toBytes());

			return input.toString();
		}
		catch (IOException e)
		{
			LOG.error(e);

			return "";
		}
	}

}
