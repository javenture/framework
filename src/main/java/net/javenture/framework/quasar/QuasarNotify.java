package net.javenture.framework.quasar;


import net.javenture.framework.icon.IIcon;
import net.javenture.framework.utf8.IUtf8StreamableEntry;
import net.javenture.framework.utf8.Utf8OutputStream;
import net.javenture.framework.script.ScriptArray;
import net.javenture.framework.script.ScriptObject;
import net.javenture.framework.script.ScriptProperty;

import java.io.IOException;


/*
	2018/05/01
 */
final public class QuasarNotify
	extends ScriptObject                                                               // XXX: del
{
	//
	final private static String PROPERTY_MESSAGE = "message";
	final private static String PROPERTY_TIMEOUT = "timeout";
	final private static String PROPERTY_TYPE = "type";
	final private static String PROPERTY_COLOR = "color";
	final private static String PROPERTY_TEXT_COLOR = "textColor";
	final private static String PROPERTY_ICON = "icon";
	final private static String PROPERTY_AVATAR = "avatar";
	final private static String PROPERTY_DETAIL = "detail";
	final private static String PROPERTY_POSITION = "position";
	final private static String PROPERTY_ACTIONS = "actions";


	//
	public QuasarNotify()
	{
	}


	//
	public QuasarNotify message(String value)
	{
		super.add(new ScriptProperty(PROPERTY_MESSAGE, value, true));

		return this;
	}


	public QuasarNotify timeout(int millisecond)
	{
		super.add(new ScriptProperty(PROPERTY_TIMEOUT, millisecond, false));

		return this;
	}


	public QuasarNotify type(Type value)
	{
		super.add(new ScriptProperty(PROPERTY_TYPE, value, true));

		return this;
	}


	public QuasarNotify color(QuasarColor value)
	{
		super.add(new ScriptProperty(PROPERTY_COLOR, value, true));

		return this;
	}


	public QuasarNotify textColor(QuasarColor value)
	{
		super.add(new ScriptProperty(PROPERTY_TEXT_COLOR, value, true));

		return this;
	}


	public QuasarNotify icon(String value)
	{
		super.add(new ScriptProperty(PROPERTY_ICON, value, true));

		return this;
	}


	public QuasarNotify avatar(String value)                                          // XXX: NettyUrl ?
	{
		super.add(new ScriptProperty(PROPERTY_AVATAR, value, true));

		return this;
	}


	public QuasarNotify detail(String value)
	{
		super.add(new ScriptProperty(PROPERTY_DETAIL, value, true));

		return this;
	}


	public QuasarNotify position(Position value)
	{
		super.add(new ScriptProperty(PROPERTY_POSITION, value, true));

		return this;
	}


/*
	public QuasarNotify actions(Action... values)
	{
		super.add(new ScriptArray(PROPERTY_ACTIONS).add(values));

		return this;
	}
*/


	//
	public enum Type
		implements IUtf8StreamableEntry
	{
		//
		POSITIVE("positive"),
		NEGATIVE("negative"),
		WARNING("warning"),
		INFO("info");

		//
		final private IUtf8StreamableEntry ENTRY;

		//
		Type(String value)
		{
			ENTRY = IUtf8StreamableEntry.entry(value, true);
		}

		//
		@Override
		public void toUtf8Stream(Utf8OutputStream destination) throws IOException
		{
			ENTRY.toUtf8Stream(destination);
		}
	}


	public enum Position
		implements IUtf8StreamableEntry
	{
		//
		CENTER("center"),
		TOP("top"),
		RIGHT("right"),
		BOTTOM("bottom"),
		LEFT("left"),
		TOP_LEFT("top-left"),
		TOP_RIGHT("top-right"),
		BOTTOM_LEFT("bottom-left"),
		BOTTOM_RIGHT("bottom-right");

		//
		final private IUtf8StreamableEntry ENTRY;

		//
		Position(String value)
		{
			ENTRY = IUtf8StreamableEntry.entry(value, true);
		}

		//
		@Override
		public void toUtf8Stream(Utf8OutputStream destination) throws IOException
		{
			ENTRY.toUtf8Stream(destination);
		}
	}



}
