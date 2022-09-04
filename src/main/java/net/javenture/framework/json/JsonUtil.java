package net.javenture.framework.json;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullContains;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.log.Log;
import net.javenture.framework.utf8.Utf8Byte;
import net.javenture.framework.utf8.Utf8InputStream;
import net.javenture.framework.utf8.Utf8OutputStream;
import net.javenture.framework.utf8.Utf8Replacement;
import net.javenture.framework.util.Chain;
import net.javenture.framework.util.NullUtil;

import java.io.IOException;
import java.util.List;


/*
	2019/12/16
 */
final public class JsonUtil
{
	//
	final private static Log LOG = Log.instance(JsonUtil.class);


	//
	final public static Utf8Replacement REPLACEMENT = new Utf8Replacement
	(
		new Utf8Replacement.Pair('"', "\\\""),
		new Utf8Replacement.Pair('\r', "\\r"),
		new Utf8Replacement.Pair('\n', "\\n"),
		new Utf8Replacement.Pair('\t', "\\t"),
		new Utf8Replacement.Pair('\b', "\\b"),
		new Utf8Replacement.Pair('\f', "\\f"),
		new Utf8Replacement.Pair('\\', "\\\\")
	);


	//
	private JsonUtil()
	{
	}


	//
	public static <T extends IJsonStreamableEntry> void toStream(Utf8OutputStream destination, @NullAllow T entry)
		throws IOException
	{
		if (entry != null) entry.toJsonStream(destination);
		else NullUtil.ENTRY.toUtf8Stream(destination);
	}


	@SafeVarargs
	public static <T extends IJsonStreamableEntry> void toStream(Utf8OutputStream destination, @NullContains T... entries)
		throws IOException
	{
		int length = entries.length;

		if (length == 1)
		{
			toStream(destination, entries[0], 0);
		}
		else if (length > 1)
		{
			int i = 0;

			for (T entry : entries) toStream(destination, entry, i++);
		}
	}


	public static <T extends IJsonStreamableEntry> void toStream(Utf8OutputStream destination, @NullContains Chain<T> entries)
		throws IOException
	{
		int size = entries.size();

		if (size == 1)
		{
			toStream(destination, entries.first(), 0);
		}
		else if (size > 1)
		{
			int i = 0;

			for (T entry : entries) toStream(destination, entry, i++);
		}
	}


	public static <T extends IJsonStreamableEntry> void toStream(Utf8OutputStream destination, @NullContains List<T> entries)
		throws IOException
	{
		int size = entries.size();

		if (size == 1)
		{
			toStream(destination, entries.get(0), 0);
		}
		else if (size > 1)
		{
			int i = 0;

			for (T entry : entries) toStream(destination, entry, i++);
		}
	}


	public static <T extends IJsonStreamableEntry> void toStream(Utf8OutputStream destination, @NullAllow T entry, int index)
		throws IOException
	{
		if (index != 0) destination.write(Utf8Byte.COMMA, Utf8Byte.SPACE);

		toStream(destination, entry);
	}


	public static String toString(@NullDisallow IJsonStreamableEntry entry)
	{
		try
		(
			JsonOutputStream output = new JsonOutputStream();
		)
		{
			output.write(entry);

			try (Utf8InputStream input = new Utf8InputStream(output))
			{
				return input.toString();
			}
		}
		catch (IOException e)
		{
			LOG.error(e);

			return "";
		}
	}


	public static String toString(IJsonStreamableEntry... entries)
	{
		try
		(
			JsonOutputStream output = new JsonOutputStream();
		)
		{
			output.write(entries);

			try (Utf8InputStream input = new Utf8InputStream(output))
			{
				return input.toString();
			}
		}
		catch (IOException e)
		{
			LOG.error(e);

			return "";
		}
	}

}
