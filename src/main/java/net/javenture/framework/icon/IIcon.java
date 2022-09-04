package net.javenture.framework.icon;


import net.javenture.framework.html.IHtmlEntry;
import net.javenture.framework.utf8.IUtf8StreamableEntry;


/*
	2018/02/19
 */
@Deprecated
public interface IIcon<E extends Enum>
	extends IUtf8StreamableEntry
{
	//
	String value();

	IHtmlEntry entry();

}
