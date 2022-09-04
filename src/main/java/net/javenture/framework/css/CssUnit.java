package net.javenture.framework.css;


import net.javenture.framework.utf8.IUtf8StreamableEntry;
import net.javenture.framework.utf8.Utf8OutputStream;

import java.io.IOException;


/*
	2019/07/05
 */
public enum CssUnit
	implements IUtf8StreamableEntry
{
	//
	PERCENT("%"),
	EM("em"),
	EX("ex"),
	PX("px"),
	CM("cm"),
	MM("mm"),
	IN("in"),
	PT("pt"),
	PC("pc"),
	CH("ch"),
	REM("rem"),
	VH("vh"),
	VW("vw"),
	VMIN("vmin"),
	VMAX("vmax"),
	;


	//
	final private String VALUE;
	final private IUtf8StreamableEntry ENTRY;


	//
	CssUnit(String value)
	{
		VALUE = value;
		ENTRY = IUtf8StreamableEntry.entry(value, true);
	}


	//
	@Override
	final public void toUtf8Stream(Utf8OutputStream destination)
		throws IOException
	{
		ENTRY.toUtf8Stream(destination);
	}


	public String value()
	{
		return VALUE;
	}

}
