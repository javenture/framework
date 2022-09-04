package net.javenture.framework.quasar;


import net.javenture.framework.html.HtmlDocument;
import net.javenture.framework.html.element.Body;
import net.javenture.framework.html.element.Head;
import net.javenture.framework.utf8.IUtf8StreamableEntry;
import net.javenture.framework.utf8.Utf8OutputStream;
import net.javenture.framework.utf8.Utf8Util;
import net.javenture.framework.util.Language;

import java.io.IOException;


/*
	2019/08/01
 */
final public class QuasarDocument                                  // XXX: QuasarContainer ?
	implements IUtf8StreamableEntry                                                                               // ???: IHtmlStreamableEntry
{
	//
	final private HtmlDocument DOCUMENT;


	//
	public QuasarDocument(Language language)
	{
		DOCUMENT = new HtmlDocument(language);

		//
/*
		Head head = DOCUMENT.head();

		// fonts
		IFont[] fonts = QuasarConfig.Font.get0();

		if (fonts != null)
		{
			for (IFont font : fonts) head.child(font.link());
		}
*/
	}


	//
	public Head head()
	{
		return DOCUMENT.head();
	}


	public Body body()
	{
		return DOCUMENT.body();
	}


/*
	public void font(IFont... fonts)
	{
		Head head = DOCUMENT.head();

		for (IFont font : fonts) head.child(font.link());
	}
*/


	@Override
	public void toUtf8Stream(Utf8OutputStream destination)
		throws IOException
	{
		// script
		// XXX: ?


		// XXX:
		// set text color / backgroud color / light or dark theme
		// set ThreadLocal values
		// protected AHtmlElement.prepare() - run before toStream()
		// on QuasarElement: set attributes for color and etc (if not set !)


		//
		DOCUMENT.toUtf8Stream(destination);


		// XXX:
		// set ThreadLocal null


	}


	@Override
	public String toString()
	{
		return Utf8Util.toString(this);
	}

}
