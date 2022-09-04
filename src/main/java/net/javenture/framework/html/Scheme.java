package net.javenture.framework.html;


import net.javenture.framework.css.Color;

import static net.javenture.framework.html.Palette.*;


@Deprecated
final public class Scheme                                                    // XXX: Scheme -> MaterialScheme
{
//	final public static Scheme LIGHT_DEFAULT = new Scheme(Theme.LIGHT);
//	final public static Scheme DARK_DEFAULT = new Scheme(Theme.DARK);


	//
	final private Theme THEME;
	final private Color.Hex PRIMARY;
	final private Color.Hex SECONDARY;
	// tertiary
	final private Color.Hex ACCENT;

	// XXX: ERROR


	//
	public Scheme(Theme theme, Color.Hex primary, Color.Hex secondary, Color.Hex accent)
	{
		THEME = theme;
		PRIMARY = primary;
		SECONDARY = secondary;
		ACCENT = accent;
	}


	//
	public Color getMaterialBackgroud()
	{
		switch (THEME)
		{
			case LIGHT: return Light.BACKGROUND_MATERIAL;
			case DARK: return Dark.BACKGROUND_MATERIAL;
		}

		throw new RuntimeException();
	}


	public Color getCardBackgroud()
	{
		switch (THEME)
		{
			case LIGHT: return Light.BACKGROUND_CARD;
			case DARK: return Dark.BACKGROUND_CARD;
		}

		throw new RuntimeException();
	}


	public Color getDialogBackgroud()
	{
		switch (THEME)
		{
			case LIGHT: return Light.BACKGROUND_DIALOG;
			case DARK: return Dark.BACKGROUND_DIALOG;
		}

		throw new RuntimeException();
	}


	public Color getPrimaryText()
	{
		switch (THEME)
		{
			case LIGHT: return Light.TEXT_PRIMARY;
			case DARK: return Dark.TEXT_PRIMARY;
		}

		throw new RuntimeException();
	}


	public Color getSecondaryText()
	{
		switch (THEME)
		{
			case LIGHT: return Light.TEXT_SECONDARY;
			case DARK: return Dark.TEXT_SECONDARY;
		}

		throw new RuntimeException();
	}


	public Color getHintText()
	{
		switch (THEME)
		{
			case LIGHT: return Light.TEXT_HINT;
			case DARK: return Dark.TEXT_HINT;
		}

		throw new RuntimeException();
	}


	public Color getDisabledText()
	{
		switch (THEME)
		{
			case LIGHT: return Light.TEXT_DISABLED;
			case DARK: return Dark.TEXT_DISABLED;
		}

		throw new RuntimeException();
	}


	public Color getActiveIcon()
	{
		switch (THEME)
		{
			case LIGHT: return Light.ICON_ACTIVE;
			case DARK: return Dark.ICON_ACTIVE;
		}

		throw new RuntimeException();
	}


	public Color getInactiveIcon()
	{
		switch (THEME)
		{
			case LIGHT: return Light.ICON_INACTIVE;
			case DARK: return Dark.ICON_INACTIVE;
		}

		throw new RuntimeException();
	}


	public Color getDivider()
	{
		switch (THEME)
		{
			case LIGHT: return Light.DIVIDER;
			case DARK: return Dark.DIVIDER;
		}

		throw new RuntimeException();
	}


	//
	public enum Theme
	{
		LIGHT,
		DARK;

		// XXX: DB import/export
	}


	final private static class Light
	{
		final public static Color BACKGROUND_BAR = new Color(PAPER_GREY_100, 1); // min!                           // #f5f5f5
		final public static Color BACKGROUND_MATERIAL = new Color(PAPER_GREY_50, 1);                               // #fafafa
		final public static Color BACKGROUND_CARD = new Color(Color.W3C.WHITE, 1);
		final public static Color BACKGROUND_DIALOG = new Color(Color.W3C.WHITE, 1);

		final public static Color TEXT_PRIMARY = new Color(Color.W3C.BLACK, 0.87F);
		final public static Color TEXT_SECONDARY = new Color(Color.W3C.BLACK, 0.54F);
		final public static Color TEXT_HINT = new Color(Color.W3C.BLACK, 0.38F);
		final public static Color TEXT_DISABLED = new Color(Color.W3C.BLACK, 0.38F);

		final public static Color ICON_ACTIVE = new Color(Color.W3C.BLACK, 0.54F);
		final public static Color ICON_INACTIVE = new Color(Color.W3C.BLACK, 0.26F);

		final public static Color DIVIDER = new Color(Color.W3C.BLACK, 0.12F);
	}


	final private static class Dark
	{
		final public static Color BACKGROUND_BAR = new Color(PAPER_GREY_900, 1); // max!                          // #212121
		final public static Color BACKGROUND_MATERIAL = new Color("#303030", 1);
		final public static Color BACKGROUND_CARD = new Color(PAPER_GREY_800, 1);                                 // #424242
		final public static Color BACKGROUND_DIALOG = new Color(PAPER_GREY_800, 1);                               // #424242

		final public static Color TEXT_PRIMARY = new Color(Color.W3C.WHITE, 1);
		final public static Color TEXT_SECONDARY = new Color(Color.W3C.WHITE, 0.70F);
		final public static Color TEXT_HINT = new Color(Color.W3C.WHITE, 0.50F);
		final public static Color TEXT_DISABLED = new Color(Color.W3C.WHITE, 0.50F);

		final public static Color ICON_ACTIVE = new Color(Color.W3C.WHITE, 1);
		final public static Color ICON_INACTIVE = new Color(Color.W3C.WHITE, 0.30F);

		final public static Color DIVIDER = new Color(Color.W3C.WHITE, 0.12F);
	}

}
