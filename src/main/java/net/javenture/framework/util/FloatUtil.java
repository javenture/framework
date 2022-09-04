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
final public class FloatUtil
{
	//
	final public static ICopyFactory<Float> FACTORY_COPY = object -> object;

	final public static IByteFactory<Float> FACTORY_BYTE = new IByteFactory<>()
	{
		@Override
		public void toStream(@NullAllow Float object, OutputStream destination)
			throws Exception
		{
			if (IByteFactory.setNullHeader(object, destination)) bytes(object, destination);
		}

		@Override
		public @NullAllow Float fromStream(InputStream source)
			throws Exception
		{
			return IByteFactory.getNullHeader(source) ? parse(source) : null;
		}
	};

	final public static IStringFactory<Float> FACTORY_STRING = new IStringFactory<>()
	{
		@Override
		public @NullDisallow String toString(@NullAllow Float object)
		{
			return object != null ? "" + object : NullUtil.STRING;
		}

		@Override
		public @NullDisallow FloatParser parser(@NullAllow Float init, boolean nullable, boolean trim)
		{
			return new FloatParser(init, nullable, trim);
		}
	};

	final public static IMurmur3HashFactory<Float> FACTORY_MURMUR3HASH = (object, destination) ->
	{
		if (IMurmur3HashFactory.updateNullHeader(object, destination)) murmur3hash(object, destination);
	};

	final public static Factories<Float> FACTORIES = new Factories<>(FACTORY_COPY, FACTORY_BYTE, FACTORY_STRING, FACTORY_MURMUR3HASH);

	final private static int DIGITS = 7;
	final private static float[] POWER = { 1.0e0F, 1.0e1F, 1.0e2F, 1.0e3F, 1.0e4F, 1.0e5F, 1.0e6F, 1.0e7F };


	//
	private FloatUtil()
	{
	}


	/*
	 * float -> byte[]
	 */
	public static byte[] bytes(float value)
	{
		return IntUtil.bytes(Float.floatToRawIntBits(value));
	}


	public static void bytes(float value, byte[] destination, int index)
	{
		IntUtil.bytes(Float.floatToRawIntBits(value), destination, index);
	}


	public static void bytes(float value, OutputStream destination)
		throws Exception
	{
		IntUtil.bytes(Float.floatToRawIntBits(value), destination);
	}


	/*
	 * byte[] -> float
	 */
	public static float parse(byte b0, byte b1, byte b2, byte b3)
	{
		return Float.intBitsToFloat(IntUtil.parse(b0, b1, b2, b3));
	}


	public static float parse(byte[] source, int index)
		throws Exception
	{
		return Float.intBitsToFloat(IntUtil.parse(source, index));
	}


	public static float parse(InputStream source)
		throws Exception
	{
		return Float.intBitsToFloat(IntUtil.parse(source));
	}


	/*
	 * CharSequence -> float
	 */
	public static @NullAllow Float parse(@NullDisallow CharSequence sequence)
		throws Exception
	{
		return parse(sequence, false);
	}


	public static @NullAllow Float parse(@NullDisallow CharSequence sequence, boolean trim)
		throws Exception
	{
		if (sequence != null)
		{
			if (NullUtil.notNull(sequence, trim))
			{
				Float result = primitive(sequence, trim);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		throw new Exception("parse failed: " + StringUtil.toString(sequence, "\""));
	}


	public static float parse(@NullAllow CharSequence sequence, float init)
	{
		return parse(sequence, init, false);
	}


	public static float parse(@NullAllow CharSequence sequence, float init, boolean trim)
	{
		if (sequence != null)
		{
			Float result = primitive(sequence, trim);

			if (result != null) return result;
		}

		return init;
	}


	public static @NullAllow Float parse(@NullAllow CharSequence sequence, @NullAllow Float init)
	{
		return parse(sequence, init, false);
	}


	public static @NullAllow Float parse(@NullAllow CharSequence sequence, @NullAllow Float init, boolean trim)
	{
		if (sequence != null)
		{
			if (NullUtil.notNull(sequence, trim))
			{
				Float result = primitive(sequence, trim);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		return init;
	}


	public static @NullAllow Float parse(@NullDisallow CharSequence sequence, int from, int to)
		throws Exception
	{
		return parse(sequence, from, to, false);
	}


	public static @NullAllow Float parse(@NullDisallow CharSequence sequence, int from, int to, boolean trim)
		throws Exception
	{
		if (sequence != null)
		{
			if (NullUtil.notNull(sequence, from, to, trim))
			{
				Float result = primitive(sequence, from, to, trim);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		throw new Exception("parse failed: " + StringUtil.toString(sequence, "\""));
	}


	public static float parse(@NullAllow CharSequence sequence, int from, int to, float init)
	{
		return parse(sequence, from, to, init, false);
	}


	public static float parse(@NullAllow CharSequence sequence, int from, int to, float init, boolean trim)
	{
		if (sequence != null)
		{
			Float result = primitive(sequence, from, to, trim);

			if (result != null) return result;
		}

		return init;
	}


	public static @NullAllow Float parse(@NullAllow CharSequence sequence, int from, int to, @NullAllow Float init)
	{
		return parse(sequence, from, to, init, false);
	}


	public static @NullAllow Float parse(@NullAllow CharSequence sequence, int from, int to, @NullAllow Float init, boolean trim)
	{
		if (sequence != null)
		{
			if (NullUtil.notNull(sequence, from, to, trim))
			{
				Float result = primitive(sequence, from, to, trim);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		return init;
	}


	public static @NullAllow Float primitive(@NullDisallow CharSequence sequence)
	{
		return primitive(sequence, 0, sequence.length(), false);
	}


	public static @NullAllow Float primitive(@NullDisallow CharSequence sequence, boolean trim)
	{
		return primitive(sequence, 0, sequence.length(), trim);
	}


	public static @NullAllow Float primitive(@NullDisallow CharSequence sequence, int from, int to)
	{
		return primitive(sequence, from, to, false);
	}


	public static @NullAllow Float primitive(@NullDisallow CharSequence sequence, int from, int to, boolean trim)
	{
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
			int value = 0;
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
					? (float) -value / POWER[power]
					: (float)  value / POWER[power];
			}
			else
			{
				String s = sequence.subSequence(from, to)
					.toString();

				try
				{
					return Float.parseFloat(TextUtil.replace(s, ',', '.'));
				}
				catch (Exception ignore)
				{
				}
			}
		}

		return null;
	}


	/*
	 * char[] -> float
	 */
	public static @NullAllow Float parse(@NullDisallow char[] array)
		throws Exception
	{
		return parse(array, false);
	}


	public static @NullAllow Float parse(@NullDisallow char[] array, boolean trim)
		throws Exception
	{
		if (array != null)
		{
			if (NullUtil.notNull(array, trim))
			{
				Float result = primitive(array, trim);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		throw new Exception("parse failed: " + CharArrayUtil.toString(array, "\""));
	}


	public static float parse(@NullAllow char[] array, float init)
	{
		return parse(array, init, false);
	}


	public static float parse(@NullAllow char[] array, float init, boolean trim)
	{
		if (array != null)
		{
			Float result = primitive(array, trim);

			if (result != null) return result;
		}

		return init;
	}


	public static @NullAllow Float parse(@NullAllow char[] array, @NullAllow Float init)
	{
		return parse(array, init, false);
	}


	public static @NullAllow Float parse(@NullAllow char[] array, @NullAllow Float init, boolean trim)
	{
		if (array != null)
		{
			if (NullUtil.notNull(array, trim))
			{
				Float result = primitive(array, trim);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		return init;
	}


	public static @NullAllow Float parse(@NullDisallow char[] array, int from, int to)
		throws Exception
	{
		return parse(array, from, to, false);
	}


	public static @NullAllow Float parse(@NullDisallow char[] array, int from, int to, boolean trim)
		throws Exception
	{
		if (array != null)
		{
			if (NullUtil.notNull(array, from, to, trim))
			{
				Float result = primitive(array, from, to, trim);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		throw new Exception("parse failed: " + CharArrayUtil.toString(array, "\""));
	}


	public static float parse(@NullAllow char[] array, int from, int to, float init)
	{
		return parse(array, from, to, init, false);
	}


	public static float parse(@NullAllow char[] array, int from, int to, float init, boolean trim)
	{
		if (array != null)
		{
			Float result = primitive(array, from, to, trim);

			if (result != null) return result;
		}

		return init;
	}


	public static @NullAllow Float parse(@NullAllow char[] array, int from, int to, @NullAllow Float init)
	{
		return parse(array, from, to, init, false);
	}


	public static @NullAllow Float parse(@NullAllow char[] array, int from, int to, @NullAllow Float init, boolean trim)
	{
		if (array != null)
		{
			if (NullUtil.notNull(array, from, to, trim))
			{
				Float result = primitive(array, from, to, trim);

				if (result != null) return result;
			}
			else
			{
				return null;
			}
		}

		return init;
	}


	public static @NullAllow Float primitive(@NullDisallow char[] array)
	{
		return primitive(array, 0, array.length, false);
	}


	public static @NullAllow Float primitive(@NullDisallow char[] array, boolean trim)
	{
		return primitive(array, 0, array.length, trim);
	}


	public static @NullAllow Float primitive(@NullDisallow char[] array, int from, int to)
	{
		return primitive(array, from, to, false);
	}


	public static @NullAllow Float primitive(@NullDisallow char[] array, int from, int to, boolean trim)
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
			int value = 0;
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
					? (float) -value / POWER[power]
					: (float)  value / POWER[power];
			}
			else
			{
				String s = new String(array, from, to - from);

				try
				{
					return Float.parseFloat(TextUtil.replace(s, ',', '.'));
				}
				catch (Exception ignore)
				{
				}
			}
		}

		return null;
	}


	/*
	 * float -> hashcode
	 */
	public static int hashcode(float value)
	{
		return Float.hashCode(value);
	}


	public static int hashcode(@NullAllow Float value)
	{
		return value != null ? Float.hashCode(value) : 0;
	}


	/*
	 * float -> murmur3hash
	 */
	public static Murmur3Hash murmur3hash(float value)
	{
		Murmur3Hash result = new Murmur3Hash();
		murmur3hash(value, result);

		return result;
	}


	public static void murmur3hash(float value, Murmur3Hash destination)
	{
		IntUtil.murmur3hash(Float.floatToRawIntBits(value), destination);
	}


	/*
	 * equal
	 */
	@SuppressWarnings("NumberEquality")
	public static boolean equal(@NullAllow Float value0, @NullAllow Float value1)
	{
		return
			value0 == value1
			||
			value0 != null
			&&
			value1 != null
			&&
			value0.floatValue() == value1.floatValue();
	}


	//
	public static float min(float value0, float value1, float value2)
	{
		return Math.min(value0, Math.min(value1, value2));
	}


	public static float max(float value0, float value1, float value2)
	{
		return Math.max(value0, Math.max(value1, value2));
	}

}
