package net.javenture.framework.javenture;


import net.javenture.framework.css.ICssClass;
import net.javenture.framework.html.AHtmlElement;
import net.javenture.framework.html.HtmlAttribute;
import net.javenture.framework.html.element.Table;


/*
	2019/07/04
 */
final public class JaventureTable                                                                        // XXX: del ?
	extends Table
{
	//
	public JaventureTable()
	{
		cl(JaventureStyle.Table.TABLE);
	}


	public JaventureTable(AHtmlElement parent)
	{
		super(parent);

		cl(JaventureStyle.Table.TABLE);
	}


	//
	public JaventureTable options(Option... options)
	{
		for (Option option : options) cl(option.CLASS);

		return this;
	}


	//
	public enum Option
	{
		//
		COMPACT(JaventureStyle.Table.COMPACT),
		LOOSE(JaventureStyle.Table.LOOSE),
		BORDERED(JaventureStyle.Table.BORDERED),
		HORIZONTAL_SEPARATOR(JaventureStyle.Table.HORIZONTAL_SEPARATOR),
		VERTICAL_SEPARATOR(JaventureStyle.Table.VERTICAL_SEPARATOR),
		STRIPED(JaventureStyle.Table.STRIPED),
		HIGHLIGHT(JaventureStyle.Table.HIGHLIGHT),
		RESPONSIVE(JaventureStyle.Table.RESPONSIVE),
		RESPONSIVE_TITLE(JaventureStyle.Table.RESPONSIVE_TITLE),
		RESPONSIVE_LABEL(JaventureStyle.Table.RESPONSIVE_LABEL),
		RESPONSIVE_CAPTION(JaventureStyle.Table.RESPONSIVE_CAPTION),

		// XXX: FOOTER
		;

		//
		final private ICssClass CLASS;

		//
		Option(ICssClass cl)
		{
			CLASS = cl;
		}
	}


	final public static class CellHint
		extends HtmlAttribute.Preset
	{
		//
		public CellHint(CharSequence value)
		{
			super(Attributes.NAME_CELL_HINT, value);
		}
	}


	final public static class Attributes
	{
		final private static String NAME_CELL_HINT = "cell-hint";

		final public static HtmlAttribute.Template TEMPLATE_CELL_HINT = new HtmlAttribute.Template(NAME_CELL_HINT);
	}

}
