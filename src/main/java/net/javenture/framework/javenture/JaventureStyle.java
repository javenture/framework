package net.javenture.framework.javenture;


import net.javenture.framework.css.ICssClass;
import net.javenture.framework.utf8.IUtf8StreamableEntry;
import net.javenture.framework.utf8.Utf8OutputStream;

import java.io.IOException;


/*
	2018/03/05
 */
final public class JaventureStyle
{
	//
	private JaventureStyle()
	{
	}


	//
	public enum Scheme
		implements ICssClass
	{
		//
		GOOGLE("j-scheme-google"),                                                                                // XXX: del
		;

		//
		final private IUtf8StreamableEntry ENTRY;

		//
		Scheme(String value)
		{
			ENTRY = IUtf8StreamableEntry.entry(value, true);
		}

		//
		@Override
		final public void toUtf8Stream(Utf8OutputStream destination) throws IOException
		{
			ENTRY.toUtf8Stream(destination);
		}
	}


	public enum Font
		implements ICssClass
	{
		//
		ROBOTO_SLAB("j-font-robotoslab"),
		ROBOTO_MONO("j-font-robotomono"),
		;

		//
		final private IUtf8StreamableEntry ENTRY;

		//
		Font(String value)
		{
			ENTRY = IUtf8StreamableEntry.entry(value, true);
		}

		//
		@Override
		final public void toUtf8Stream(Utf8OutputStream destination) throws IOException
		{
			ENTRY.toUtf8Stream(destination);
		}
	}


	public enum Text
		implements ICssClass
	{
		//
		HEADLINE("j-text-headline"),
		TITLE("j-text-title"),
		;

		//
		final private IUtf8StreamableEntry ENTRY;

		//
		Text(String value)
		{
			ENTRY = IUtf8StreamableEntry.entry(value, true);
		}

		//
		@Override
		final public void toUtf8Stream(Utf8OutputStream destination) throws IOException
		{
			ENTRY.toUtf8Stream(destination);
		}
	}


	public enum Table
		implements ICssClass
	{
		//
		TABLE("j-table"), // required
		COMPACT("j-table-compact"),
		LOOSE("j-table-loose"),
		BORDERED("j-table-bordered"),
		HORIZONTAL_SEPARATOR("j-table-horizontal-separator"),
		VERTICAL_SEPARATOR("j-table-vertical-separator"),
		STRIPED("j-table-striped"),
		HIGHLIGHT("j-table-highlight"),
		RESPONSIVE("j-table-responsive"),
		RESPONSIVE_TITLE("j-table-responsive-title"),
		RESPONSIVE_LABEL("j-table-responsive-label"),
		RESPONSIVE_CAPTION("j-table-responsive-caption"),
		;

		//
		final private IUtf8StreamableEntry ENTRY;

		//
		Table(String value)
		{
			ENTRY = IUtf8StreamableEntry.entry(value, true);
		}

		//
		@Override
		final public void toUtf8Stream(Utf8OutputStream destination) throws IOException
		{
			ENTRY.toUtf8Stream(destination);
		}
	}


	public enum Figure
		implements ICssClass
	{
		//
		TABLE("j-figure-table"),
		;

		//
		final private IUtf8StreamableEntry ENTRY;

		//
		Figure(String value)
		{
			ENTRY = IUtf8StreamableEntry.entry(value, true);
		}

		//
		@Override
		final public void toUtf8Stream(Utf8OutputStream destination) throws IOException
		{
			ENTRY.toUtf8Stream(destination);
		}
	}

}