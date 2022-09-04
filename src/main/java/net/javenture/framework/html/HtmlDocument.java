package net.javenture.framework.html;


import net.javenture.framework.html.element.Body;
import net.javenture.framework.html.element.Head;
import net.javenture.framework.html.element.Meta;
import net.javenture.framework.utf8.IUtf8StreamableEntry;
import net.javenture.framework.utf8.Utf8Byte;
import net.javenture.framework.utf8.Utf8OutputStream;
import net.javenture.framework.utf8.Utf8Util;
import net.javenture.framework.util.Language;

import java.io.IOException;


/*
	2018/09/25
 */
final public class HtmlDocument                                                // XXX: HtmlContainer ?
	implements IUtf8StreamableEntry
{
	//
	final private Language LANGUAGE;                                       // XXX: final ?
	final private Head HEAD;
	final private Body BODY;


	//
	public HtmlDocument(Language language)
	{
		LANGUAGE = language;
		HEAD = new Head();
		BODY = new Body();

		//
		HEAD.child(Meta.CHARSET_UTF8);
	}


	//
	@Override
	public void toUtf8Stream(Utf8OutputStream destination)
		throws IOException
	{
		// XXX: Content-Type: text/html; charset=UTF-8 -> NettyResponse

		// https://msdn.microsoft.com/en-us/library/jj676915.aspx
		// HTML tag (<meta http-equiv="x-ua-compatible" content="IE=edge">) | HTTP header (X-UA-Compatible: IE=edge) = avoid

		// <!doctype html>
		destination.write(Utf8Byte.LESS_THAN, Utf8Byte.EXCLAMATION, Utf8Byte.D_SMALL, Utf8Byte.O_SMALL, Utf8Byte.C_SMALL, Utf8Byte.T_SMALL, Utf8Byte.Y_SMALL, Utf8Byte.P_SMALL);
		destination.write(Utf8Byte.E_SMALL, Utf8Byte.SPACE, Utf8Byte.H_SMALL, Utf8Byte.T_SMALL, Utf8Byte.M_SMALL, Utf8Byte.L_SMALL, Utf8Byte.GREATER_THAN);

		// <html lang="...">
		if (LANGUAGE.defined())
		{
			destination.write(Utf8Byte.LESS_THAN, Utf8Byte.H_SMALL, Utf8Byte.T_SMALL, Utf8Byte.M_SMALL, Utf8Byte.L_SMALL);
			destination.write(Utf8Byte.SPACE, Utf8Byte.L_SMALL, Utf8Byte.A_SMALL, Utf8Byte.N_SMALL, Utf8Byte.G_SMALL, Utf8Byte.EQUAL, Utf8Byte.QUOTATION);
			destination.write(LANGUAGE.NAME2);
			destination.write(Utf8Byte.QUOTATION, Utf8Byte.GREATER_THAN);
		}
		else
		{
			destination.write(Utf8Byte.LESS_THAN, Utf8Byte.H_SMALL, Utf8Byte.T_SMALL, Utf8Byte.M_SMALL, Utf8Byte.L_SMALL, Utf8Byte.GREATER_THAN);
		}

		//
		destination.write(HEAD);
		destination.write(BODY);

		// </html>
		destination.write(Utf8Byte.LESS_THAN, Utf8Byte.SLASH, Utf8Byte.H_SMALL, Utf8Byte.T_SMALL, Utf8Byte.M_SMALL, Utf8Byte.L_SMALL, Utf8Byte.GREATER_THAN);
	}


	@Override
	public String toString()
	{
		return Utf8Util.toString(this);
	}


	public Language language()
	{
		return LANGUAGE;
	}


	public Head head()
	{
		return HEAD;
	}


	public Body body()
	{
		return BODY;
	}

}
