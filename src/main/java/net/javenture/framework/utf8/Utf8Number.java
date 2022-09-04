package net.javenture.framework.utf8;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.util.NullUtil;

import java.io.IOException;


/*
	2019/07/19
 */
final public class Utf8Number
{
	//
	private Utf8Number()
	{
	}


	//
	public static IUtf8StreamableEntry entry(short value)
	{
		return destination -> toStream(value, destination);
	}


	public static IUtf8StreamableEntry entry(@NullAllow Short value)
	{
		return destination -> toStream(value, destination);
	}


	public static IUtf8StreamableEntry entry(int value)
	{
		return destination -> toStream(value, destination);
	}


	public static IUtf8StreamableEntry entry(@NullAllow Integer value)
	{
		return destination -> toStream(value, destination);
	}


	public static IUtf8StreamableEntry entry(long value)
	{
		return destination -> toStream(value, destination);
	}


	public static IUtf8StreamableEntry entry(@NullAllow Long value)
	{
		return destination -> toStream(value, destination);
	}


	public static IUtf8StreamableEntry entry(float value)
	{
		return destination -> toStream(value, destination);
	}


	public static IUtf8StreamableEntry entry(@NullAllow Float value)
	{
		return destination -> toStream(value, destination);
	}


	public static IUtf8StreamableEntry entry(double value)
	{
		return destination -> toStream(value, destination);
	}


	public static IUtf8StreamableEntry entry(@NullAllow Double value)
	{
		return destination -> toStream(value, destination);
	}


	public static IUtf8StreamableEntry entry(@NullAllow Number value)
	{
		return destination -> toStream(value, destination);
	}


	public static IUtf8StreamableEntry entry(@NullAllow CharSequence value)
	{
		return destination -> toStream(value, destination);
	}


	public static void toStream(short value, Utf8OutputStream destination)
		throws IOException
	{
		String s = Integer.toString(value); // Integer !

		for (int i = 0; i < s.length(); i++)
		{
			switch (s.charAt(i))
			{
				case '-': destination.write(Utf8Byte.MINUS); break;
				case '0': destination.write(Utf8Byte.DIGIT_ZERO); break;
				case '1': destination.write(Utf8Byte.DIGIT_ONE); break;
				case '2': destination.write(Utf8Byte.DIGIT_TWO); break;
				case '3': destination.write(Utf8Byte.DIGIT_THREE); break;
				case '4': destination.write(Utf8Byte.DIGIT_FOUR); break;
				case '5': destination.write(Utf8Byte.DIGIT_FIVE); break;
				case '6': destination.write(Utf8Byte.DIGIT_SIX); break;
				case '7': destination.write(Utf8Byte.DIGIT_SEVEN); break;
				case '8': destination.write(Utf8Byte.DIGIT_EIGHT); break;
				case '9': destination.write(Utf8Byte.DIGIT_NINE); break;
				default: throw new UnsupportedOperationException("[value] (" + s + "); unknown char at position " + i);
			}
		}
	}


	public static void toStream(@NullAllow Short value, Utf8OutputStream destination)
		throws IOException
	{
		if (value != null) toStream((short) value, destination);
		else NullUtil.ENTRY.toUtf8Stream(destination);
	}


	public static void toStream(int value, Utf8OutputStream destination)
		throws IOException
	{
		String s = Integer.toString(value);

		for (int i = 0; i < s.length(); i++)
		{
			switch (s.charAt(i))
			{
				case '-': destination.write(Utf8Byte.MINUS); break;
				case '0': destination.write(Utf8Byte.DIGIT_ZERO); break;
				case '1': destination.write(Utf8Byte.DIGIT_ONE); break;
				case '2': destination.write(Utf8Byte.DIGIT_TWO); break;
				case '3': destination.write(Utf8Byte.DIGIT_THREE); break;
				case '4': destination.write(Utf8Byte.DIGIT_FOUR); break;
				case '5': destination.write(Utf8Byte.DIGIT_FIVE); break;
				case '6': destination.write(Utf8Byte.DIGIT_SIX); break;
				case '7': destination.write(Utf8Byte.DIGIT_SEVEN); break;
				case '8': destination.write(Utf8Byte.DIGIT_EIGHT); break;
				case '9': destination.write(Utf8Byte.DIGIT_NINE); break;
				default: throw new UnsupportedOperationException("[value] (" + s + "); unknown char at position " + i);
			}
		}
	}


	public static void toStream(@NullAllow Integer value, Utf8OutputStream destination)
		throws IOException
	{
		if (value != null) toStream((int) value, destination);
		else NullUtil.ENTRY.toUtf8Stream(destination);
	}


	public static void toStream(long value, Utf8OutputStream destination)
		throws IOException
	{
		String s = Long.toString(value);

		for (int i = 0; i < s.length(); i++)
		{
			switch (s.charAt(i))
			{
				case '-': destination.write(Utf8Byte.MINUS); break;
				case '0': destination.write(Utf8Byte.DIGIT_ZERO); break;
				case '1': destination.write(Utf8Byte.DIGIT_ONE); break;
				case '2': destination.write(Utf8Byte.DIGIT_TWO); break;
				case '3': destination.write(Utf8Byte.DIGIT_THREE); break;
				case '4': destination.write(Utf8Byte.DIGIT_FOUR); break;
				case '5': destination.write(Utf8Byte.DIGIT_FIVE); break;
				case '6': destination.write(Utf8Byte.DIGIT_SIX); break;
				case '7': destination.write(Utf8Byte.DIGIT_SEVEN); break;
				case '8': destination.write(Utf8Byte.DIGIT_EIGHT); break;
				case '9': destination.write(Utf8Byte.DIGIT_NINE); break;
				default: throw new UnsupportedOperationException("[value] (" + s + "); unknown char at position " + i);
			}
		}
	}


	public static void toStream(@NullAllow Long value, Utf8OutputStream destination)
		throws IOException
	{
		if (value != null) toStream((long) value, destination);
		else NullUtil.ENTRY.toUtf8Stream(destination);
	}


	public static void toStream(float value, Utf8OutputStream destination)
		throws IOException
	{
		if (Float.isNaN(value))
		{
			destination.write(Utf8Byte.N_CAPITAL, Utf8Byte.A_SMALL, Utf8Byte.N_CAPITAL);
		}
		else if (value == Float.POSITIVE_INFINITY)
		{
			destination.write(Utf8Byte.I_CAPITAL, Utf8Byte.N_SMALL, Utf8Byte.F_SMALL, Utf8Byte.I_SMALL, Utf8Byte.N_SMALL, Utf8Byte.I_SMALL, Utf8Byte.T_SMALL, Utf8Byte.Y_SMALL);
		}
		else if (value == Float.NEGATIVE_INFINITY)
		{
			destination.write(Utf8Byte.MINUS, Utf8Byte.I_CAPITAL, Utf8Byte.N_SMALL, Utf8Byte.F_SMALL, Utf8Byte.I_SMALL, Utf8Byte.N_SMALL, Utf8Byte.I_SMALL, Utf8Byte.T_SMALL, Utf8Byte.Y_SMALL);
		}
		else
		{
			String s = Float.toString(value);

			for (int i = 0; i < s.length(); i++)
			{
				switch (s.charAt(i))
				{
					case '-': destination.write(Utf8Byte.MINUS); break;
					case '.': destination.write(Utf8Byte.DOT); break;
					case '0': destination.write(Utf8Byte.DIGIT_ZERO); break;
					case '1': destination.write(Utf8Byte.DIGIT_ONE); break;
					case '2': destination.write(Utf8Byte.DIGIT_TWO); break;
					case '3': destination.write(Utf8Byte.DIGIT_THREE); break;
					case '4': destination.write(Utf8Byte.DIGIT_FOUR); break;
					case '5': destination.write(Utf8Byte.DIGIT_FIVE); break;
					case '6': destination.write(Utf8Byte.DIGIT_SIX); break;
					case '7': destination.write(Utf8Byte.DIGIT_SEVEN); break;
					case '8': destination.write(Utf8Byte.DIGIT_EIGHT); break;
					case '9': destination.write(Utf8Byte.DIGIT_NINE); break;
					case 'E': destination.write(Utf8Byte.E_CAPITAL); break;
					default: throw new UnsupportedOperationException("[value] (" + s + "); unknown char at position " + i);
				}
			}
		}
	}


	public static void toStream(@NullAllow Float value, Utf8OutputStream destination)
		throws IOException
	{
		if (value != null) toStream((float) value, destination);
		else NullUtil.ENTRY.toUtf8Stream(destination);
	}


	public static void toStream(double value, Utf8OutputStream destination)
		throws IOException
	{
		if (Double.isNaN(value))
		{
			destination.write(Utf8Byte.N_CAPITAL, Utf8Byte.A_SMALL, Utf8Byte.N_CAPITAL);
		}
		else if (value == Double.POSITIVE_INFINITY)
		{
			destination.write(Utf8Byte.I_CAPITAL, Utf8Byte.N_SMALL, Utf8Byte.F_SMALL, Utf8Byte.I_SMALL, Utf8Byte.N_SMALL, Utf8Byte.I_SMALL, Utf8Byte.T_SMALL, Utf8Byte.Y_SMALL);
		}
		else if (value == Double.NEGATIVE_INFINITY)
		{
			destination.write(Utf8Byte.MINUS, Utf8Byte.I_CAPITAL, Utf8Byte.N_SMALL, Utf8Byte.F_SMALL, Utf8Byte.I_SMALL, Utf8Byte.N_SMALL, Utf8Byte.I_SMALL, Utf8Byte.T_SMALL, Utf8Byte.Y_SMALL);
		}
		else
		{
			String s = Double.toString(value);

			for (int i = 0; i < s.length(); i++)
			{
				switch (s.charAt(i))
				{
					case '-': destination.write(Utf8Byte.MINUS); break;
					case '.': destination.write(Utf8Byte.DOT); break;
					case '0': destination.write(Utf8Byte.DIGIT_ZERO); break;
					case '1': destination.write(Utf8Byte.DIGIT_ONE); break;
					case '2': destination.write(Utf8Byte.DIGIT_TWO); break;
					case '3': destination.write(Utf8Byte.DIGIT_THREE); break;
					case '4': destination.write(Utf8Byte.DIGIT_FOUR); break;
					case '5': destination.write(Utf8Byte.DIGIT_FIVE); break;
					case '6': destination.write(Utf8Byte.DIGIT_SIX); break;
					case '7': destination.write(Utf8Byte.DIGIT_SEVEN); break;
					case '8': destination.write(Utf8Byte.DIGIT_EIGHT); break;
					case '9': destination.write(Utf8Byte.DIGIT_NINE); break;
					case 'E': destination.write(Utf8Byte.E_CAPITAL); break;
					default: throw new UnsupportedOperationException("[value] (" + s + "); unknown char at position " + i);
				}
			}
		}
	}


	public static void toStream(@NullAllow Double value, Utf8OutputStream destination)
		throws IOException
	{
		if (value != null) toStream((double) value, destination);
		else NullUtil.ENTRY.toUtf8Stream(destination);
	}


	public static void toStream(@NullAllow Number value, Utf8OutputStream destination)
		throws IOException
	{
		if (value != null) toStream0(value.toString(), destination);
		else NullUtil.ENTRY.toUtf8Stream(destination);
	}


	public static void toStream(@NullAllow CharSequence value, Utf8OutputStream destination)
		throws IOException
	{
		if (value != null) toStream0(value, destination);
		else NullUtil.ENTRY.toUtf8Stream(destination);
	}


	private static void toStream0(@NullDisallow CharSequence value, Utf8OutputStream destination)
		throws IOException
	{
		for (int i = 0; i < value.length(); i++)
		{
			switch (value.charAt(i))
			{
				case '-': destination.write(Utf8Byte.MINUS); break;
				case '.': destination.write(Utf8Byte.DOT); break;
				case '0': destination.write(Utf8Byte.DIGIT_ZERO); break;
				case '1': destination.write(Utf8Byte.DIGIT_ONE); break;
				case '2': destination.write(Utf8Byte.DIGIT_TWO); break;
				case '3': destination.write(Utf8Byte.DIGIT_THREE); break;
				case '4': destination.write(Utf8Byte.DIGIT_FOUR); break;
				case '5': destination.write(Utf8Byte.DIGIT_FIVE); break;
				case '6': destination.write(Utf8Byte.DIGIT_SIX); break;
				case '7': destination.write(Utf8Byte.DIGIT_SEVEN); break;
				case '8': destination.write(Utf8Byte.DIGIT_EIGHT); break;
				case '9': destination.write(Utf8Byte.DIGIT_NINE); break;
				case 'E': destination.write(Utf8Byte.E_CAPITAL); break;
				case 'I': destination.write(Utf8Byte.I_CAPITAL); break;
				case 'N': destination.write(Utf8Byte.N_CAPITAL); break;
				case 'a': destination.write(Utf8Byte.A_SMALL); break;
				case 'f': destination.write(Utf8Byte.F_SMALL); break;
				case 'i': destination.write(Utf8Byte.I_SMALL); break;
				case 'n': destination.write(Utf8Byte.N_SMALL); break;
				case 't': destination.write(Utf8Byte.T_SMALL); break;
				case 'y': destination.write(Utf8Byte.Y_SMALL); break;
				default: throw new UnsupportedOperationException("[sequence] (" + value + "); unknown char at position " + i);
			}
		}
	}

}
