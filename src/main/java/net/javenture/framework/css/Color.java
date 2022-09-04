package net.javenture.framework.css;


import net.javenture.framework.util.Validator;


final public class Color                                                           // XXX: RgbColor ?
{
	//
	final public int RED;
	final public int GREEN;
	final public int BLUE;
	final public float ALPHA;


	//
	public Color(Hex value)
	{
		this(value, 1);
	}


	public Color(Hex value, float alpha)
	{
		RED = value.RED;
		GREEN = value.GREEN;
		BLUE = value.BLUE;
		ALPHA = alpha;
	}


	public Color(Preset preset)
	{
		this(preset.HEX, 1);
	}


	public Color(Preset preset, float alpha)
	{
		this(preset.HEX, alpha);
	}


	public Color(String hex)
	{
		this(hex, 1);
	}


	public Color(String hex, float alpha)
	{
		int i = Integer.decode(hex);

		RED = (i >> 16) & 0xFF;
		GREEN = (i >> 8) & 0xFF;
		BLUE = i & 0xFF;
		ALPHA = alpha;
	}


	public Color(int red, int green, int blue)
	{
		this(red, green, blue, 1);
	}


	public Color(int red, int green, int blue, float alpha)
	{
		Validator.argument(red >= 0 && red <= 255, "[red] (", red, ")");
		Validator.argument(green >= 0 && green <= 255, "[green] (", green, ")");
		Validator.argument(blue >= 0 && blue <= 255, "[blue] (", blue, ")");
		Validator.argument(alpha >= 0 && alpha <= 1, "[alpha] (", alpha, ")");

		//
		RED = red;
		GREEN = green;
		BLUE = blue;
		ALPHA = alpha;
	}


	//
	public String rgb()
	{
		return "rgb(" + RED + ", " + GREEN + ", " + BLUE + ")";
	}


	public String rgba()
	{
		return "rgba(" + RED + ", " + GREEN + ", " + BLUE + ", " + ALPHA + ")";
	}


	//
	final public static class Hex                                                                                      // XXX: HexColor
	{
		final public int RED;
		final public int GREEN;
		final public int BLUE;

		public Hex(String value)
		{
			Validator.notNull(value, "[value]");
			Validator.argument(value.length() == 7, "[value.length] (", value.length(), ") != 7");
			Validator.argument(value.charAt(0) == '#', "[value] (", value, ") has wrong format");

			//
			int i = Integer.decode(value);
			RED = (i >> 16) & 0xFF;
			GREEN = (i >> 8) & 0xFF;
			BLUE = i & 0xFF;
		}

		// XXX: DB import/export (HEX <-> INTEGER)
	}


	public static class Preset
	{
		final public Hex HEX;
		final public String NAME;

		//
		public Preset(Hex value, String name)
		{
			HEX = value;
			NAME = name;
		}

		public Preset(String hex, String name)
		{
			HEX = new Hex(hex);
			NAME = name;
		}
	}


	final public static class W3C
	{
		final public static Preset BLACK = new Preset("#000000", "Black");
		final public static Preset WHITE = new Preset("#FFFFFF", "White");

	}

}
