package net.javenture.framework.util;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.factory.IByteFactory;
import net.javenture.framework.factory.ICopyFactory;
import net.javenture.framework.factory.IMurmur3HashFactory;
import net.javenture.framework.factory.IStringFactory;
import net.javenture.framework.hash.Murmur3Hash;

import java.io.InputStream;
import java.io.OutputStream;


/*
	2021/05/28
 */
final public class DoubleUtil
{
	//
	final public static ICopyFactory<Double> FACTORY_COPY = object -> object;

	final public static IByteFactory<Double> FACTORY_BYTE = new IByteFactory<>()
	{
		@Override
		public void toStream(@NullAllow Double object, OutputStream destination)
			throws Exception
		{
			if (IByteFactory.setNullHeader(object, destination)) bytes(object, destination);
		}

		@Override
		public @NullAllow Double fromStream(InputStream source)
			throws Exception
		{
			return IByteFactory.getNullHeader(source) ? parse(source) : null;
		}
	};

	final public static IStringFactory<Double> FACTORY_STRING = new IStringFactory<>()
	{
		@Override
		public @NullDisallow String toString(@NullAllow Double object)
		{
			return object != null ? "" + object : NullUtil.STRING;
		}

		@Override
		public @NullDisallow DoubleParser parser(@NullAllow Double init, boolean nullable, boolean trim)
		{
			return new DoubleParser(init, nullable, trim);
		}
	};

	final public static IMurmur3HashFactory<Double> FACTORY_MURMUR3HASH = (object, destination) ->
	{
		if (IMurmur3HashFactory.updateNullHeader(object, destination)) murmur3hash(object, destination);
	};

	final public static Factories<Double> FACTORIES = new Factories<>(FACTORY_COPY, FACTORY_BYTE, FACTORY_STRING, FACTORY_MURMUR3HASH);
	final private static int DIGITS = 15;
	final private static double[] POWER = { 1.0e0, 1.0e1, 1.0e2, 1.0e3, 1.0e4, 1.0e5, 1.0e6, 1.0e7, 1.0e8, 1.0e9, 1.0e10, 1.0e11, 1.0e12, 1.0e13, 1.0e14, 1.0e15 };


	//
	private DoubleUtil()
	{
	}


	/*
	 * double -> byte[]
	 */
	public static byte[] bytes(double value)
	{
		return LongUtil.bytes(Double.doubleToRawLongBits(value));
	}


	public static void bytes(double value, byte[] destination, int index)
	{
		LongUtil.bytes(Double.doubleToRawLongBits(value), destination, index);
	}


	public static void bytes(double value, OutputStream destination)
		throws Exception
	{
		LongUtil.bytes(Double.doubleToRawLongBits(value), destination);
	}


	/*
	 * byte[] -> double
	 */
	public static double parse(byte b0, byte b1, byte b2, byte b3, byte b4, byte b5, byte b6, byte b7)
	{
		return Double.longBitsToDouble(LongUtil.parse(b0, b1, b2, b3, b4, b5, b6, b7));
	}


	public static double parse(byte[] source, int index)
		throws Exception
	{
		return Double.longBitsToDouble(LongUtil.parse(source, index));
	}


	public static double parse(InputStream source)
		throws Exception
	{
		return Double.longBitsToDouble(LongUtil.parse(source));
	}


	/*
	 * CharSequence -> double
	 */
	public static @NullAllow Double parse(@NullDisallow CharSequence sequence)
		throws Exception
	{
		return parse(sequence, false);
	}


	public static @NullAllow Double parse(@NullDisallow CharSequence sequence, boolean trim)
		throws Exception
	{
		if (sequence != null)
		{
			if (NullUtil.notNull(sequence, trim))
			{
				Double result = primitive(sequence, trim);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		throw new Exception("parse failed: " + StringUtil.toString(sequence, "\""));
	}


	public static double parse(@NullAllow CharSequence sequence, double init)
	{
		return parse(sequence, init, false);
	}


	public static double parse(@NullAllow CharSequence sequence, double init, boolean trim)
	{
		if (sequence != null)
		{
			Double result = primitive(sequence, trim);

			if (result != null) return result;
		}

		return init;
	}


	public static @NullAllow Double parse(@NullAllow CharSequence sequence, @NullAllow Double init)
	{
		return parse(sequence, init, false);
	}


	public static @NullAllow Double parse(@NullAllow CharSequence sequence, @NullAllow Double init, boolean trim)
	{
		if (sequence != null)
		{
			if (NullUtil.notNull(sequence, trim))
			{
				Double result = primitive(sequence, trim);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		return init;
	}


	public static @NullAllow Double parse(@NullDisallow CharSequence sequence, int from, int to)
		throws Exception
	{
		return parse(sequence, from, to, false);
	}


	public static @NullAllow Double parse(@NullDisallow CharSequence sequence, int from, int to, boolean trim)
		throws Exception
	{
		if (sequence != null)
		{
			if (NullUtil.notNull(sequence, from, to, trim))
			{
				Double result = primitive(sequence, from, to, trim);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		throw new Exception("parse failed: " + StringUtil.toString(sequence, "\""));
	}


	public static double parse(@NullAllow CharSequence sequence, int from, int to, double init)
	{
		return parse(sequence, from, to, init, false);
	}


	public static double parse(@NullAllow CharSequence sequence, int from, int to, double init, boolean trim)
	{
		if (sequence != null)
		{
			Double result = primitive(sequence, from, to, trim);

			if (result != null) return result;
		}

		return init;
	}


	public static @NullAllow Double parse(@NullAllow CharSequence sequence, int from, int to, @NullAllow Double init)
	{
		return parse(sequence, from, to, init, false);
	}


	public static @NullAllow Double parse(@NullAllow CharSequence sequence, int from, int to, @NullAllow Double init, boolean trim)
	{
		if (sequence != null)
		{
			if (NullUtil.notNull(sequence, from, to, trim))
			{
				Double result = primitive(sequence, from, to, trim);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		return init;
	}


	public static @NullAllow Double primitive(@NullDisallow CharSequence sequence)
	{
		return primitive(sequence, 0, sequence.length(), false);
	}


	public static @NullAllow Double primitive(@NullDisallow CharSequence sequence, boolean trim)
	{
		return primitive(sequence, 0, sequence.length(), trim);
	}


	public static @NullAllow Double primitive(@NullDisallow CharSequence sequence, int from, int to)
	{
		return primitive(sequence, from, to, false);
	}


	public static @NullAllow Double primitive(@NullDisallow CharSequence sequence, int from, int to, boolean trim)
	{
		// https://www.exploringbinary.com/how-strtod-works-and-sometimes-doesnt/

		if (trim)
		{
			while (from < to && sequence.charAt(from) <= ' ') from++;

			while (from < to && sequence.charAt(to - 1) <= ' ') to--;
		}

		if (from < to)
		{
			boolean negative = sequence.charAt(from) == '-';
			boolean positive = sequence.charAt(from) == '+';
			int index = negative || positive ? from + 1 : from;

			if (index == to) return null;

			//
			long value = 0;
			int digits = 0;
			int delimiter = -1;
			boolean error = false;

			while (index < to && !error)
			{
				char c = sequence.charAt(index++); // ! index++

				switch (c)
				{
					case ',':
					case '.':
					{
						if (delimiter == -1) delimiter = index; // ! index
						else error = true;

						break;
					}

					case '0':
					case '1':
					case '2':
					case '3':
					case '4':
					case '5':
					case '6':
					case '7':
					case '8':
					case '9':
					{
						digits++;
						value = value * 10 + ((int) c - (int) '0');

						break;
					}

					default: error = true;
				}

				if (digits > DIGITS)
				{
					error = true;

					break;
				}
			}

			//
			if (!error)
			{
				int power = delimiter != -1 ? to - from - delimiter : 0;

				return negative
					? (double) -value / POWER[power]
					: (double)  value / POWER[power];
			}
			else
			{
				String s = sequence.subSequence(from, to)
					.toString();

				try
				{
					return Double.parseDouble(TextUtil.replace(s, ',', '.'));
				}
				catch (Exception ignore)
				{
				}
			}
		}

		return null;
	}


	/*
	 * char[] -> double
	 */
	public static @NullAllow Double parse(@NullDisallow char[] array)
		throws Exception
	{
		return parse(array, false);
	}


	public static @NullAllow Double parse(@NullDisallow char[] array, boolean trim)
		throws Exception
	{
		if (array != null)
		{
			if (NullUtil.notNull(array, trim))
			{
				Double result = primitive(array, trim);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		throw new Exception("parse failed: " + CharArrayUtil.toString(array, "\""));
	}


	public static double parse(@NullAllow char[] array, double init)
	{
		return parse(array, init, false);
	}


	public static double parse(@NullAllow char[] array, double init, boolean trim)
	{
		if (array != null)
		{
			Double result = primitive(array, trim);

			if (result != null) return result;
		}

		return init;
	}


	public static @NullAllow Double parse(@NullAllow char[] array, @NullAllow Double init)
	{
		return parse(array, init, false);
	}


	public static @NullAllow Double parse(@NullAllow char[] array, @NullAllow Double init, boolean trim)
	{
		if (array != null)
		{
			if (NullUtil.notNull(array, trim))
			{
				Double result = primitive(array, trim);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		return init;
	}


	public static @NullAllow Double parse(@NullDisallow char[] array, int from, int to)
		throws Exception
	{
		return parse(array, from, to, false);
	}


	public static @NullAllow Double parse(@NullDisallow char[] array, int from, int to, boolean trim)
		throws Exception
	{
		if (array != null)
		{
			if (NullUtil.notNull(array, from, to, trim))
			{
				Double result = primitive(array, from, to, trim);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		throw new Exception("parse failed: " + CharArrayUtil.toString(array, "\""));
	}


	public static double parse(@NullAllow char[] array, int from, int to, double init)
	{
		return parse(array, from, to, init, false);
	}


	public static double parse(@NullAllow char[] array, int from, int to, double init, boolean trim)
	{
		if (array != null)
		{
			Double result = primitive(array, from, to, trim);

			if (result != null) return result;
		}

		return init;
	}


	public static @NullAllow Double parse(@NullAllow char[] array, int from, int to, @NullAllow Double init)
	{
		return parse(array, from, to, init, false);
	}


	public static @NullAllow Double parse(@NullAllow char[] array, int from, int to, @NullAllow Double init, boolean trim)
	{
		if (array != null)
		{
			if (NullUtil.notNull(array, from, to, trim))
			{
				Double result = primitive(array, from, to, trim);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		return init;
	}


	public static @NullAllow Double primitive(@NullDisallow char[] array)
	{
		return primitive(array, 0, array.length, false);
	}


	public static @NullAllow Double primitive(@NullDisallow char[] array, boolean trim)
	{
		return primitive(array, 0, array.length, trim);
	}


	public static @NullAllow Double primitive(@NullDisallow char[] array, int from, int to)
	{
		return primitive(array, from, to, false);
	}


	public static @NullAllow Double primitive(@NullDisallow char[] array, int from, int to, boolean trim)
	{
		if (trim)
		{
			while (from < to && array[from] <= ' ') from++;

			while (from < to && array[to - 1] <= ' ') to--;
		}

		if (from < to)
		{
			boolean negative = array[from] == '-';
			boolean positive = array[from] == '+';
			int index = negative || positive ? from + 1 : from;

			if (index == to) return null;

			//
			long value = 0;
			int digits = 0;
			int delimiter = -1;
			boolean error = false;

			while (index < to && !error)
			{
				char c = array[index++]; // ! index++

				switch (c)
				{
					case ',':
					case '.':
					{
						if (delimiter == -1) delimiter = index; // ! index
						else error = true;

						break;
					}

					case '0':
					case '1':
					case '2':
					case '3':
					case '4':
					case '5':
					case '6':
					case '7':
					case '8':
					case '9':
					{
						digits++;
						value = value * 10 + ((int) c - (int) '0');

						break;
					}

					default: error = true;
				}

				if (digits > DIGITS)
				{
					error = true;

					break;
				}
			}

			//
			if (!error)
			{
				int power = delimiter != -1 ? to - from - delimiter : 0;

				return negative
					? (double) -value / POWER[power]
					: (double)  value / POWER[power];
			}
			else
			{
				String s = new String(array, from, to - from);

				try
				{
					return Double.parseDouble(TextUtil.replace(s, ',', '.'));
				}
				catch (Exception ignore)
				{
				}
			}
		}

		return null;
	}


	/*
	 * double -> hashcode
	 */
	public static int hashcode(double value)
	{
		return Double.hashCode(value);
	}


	public static int hashcode(@NullAllow Double value)
	{
		return value != null ? Double.hashCode(value) : 0;
	}


	/*
	 * double -> murmur3hash
	 */
	public static Murmur3Hash murmur3hash(double value)
	{
		Murmur3Hash result = new Murmur3Hash();
		murmur3hash(value, result);

		return result;
	}


	public static void murmur3hash(double value, Murmur3Hash destination)
	{
		LongUtil.murmur3hash(Double.doubleToRawLongBits(value), destination);
	}


	/*
	 * equal
	 */
	@SuppressWarnings("NumberEquality")
	public static boolean equal(@NullAllow Double value0, @NullAllow Double value1)
	{
		return
			value0 == value1
			||
			value0 != null
			&&
			value1 != null
			&&
			value0.doubleValue() == value1.doubleValue();
	}


	public static boolean equal(double value0, double value1, double threshold)
	{
		return Math.abs(Math.abs(value0) - Math.abs(value1)) <= threshold;
	}


	//
	public static double min(double value0, double value1, double value2)
	{
		return Math.min(value0, Math.min(value1, value2));
	}


	public static double max(double value0, double value1, double value2)
	{
		return Math.max(value0, Math.max(value1, value2));
	}

}
