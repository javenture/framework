package net.javenture.framework.image;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.ascii.AsciiUtil;
import net.javenture.framework.utf8.Utf8Byte;
import net.javenture.framework.utf8.Utf8Util;
import net.javenture.framework.util.ByteArrayUtil;
import net.javenture.framework.util.FileUtil;
import net.javenture.framework.util.IntUtil;

import java.io.File;
import java.util.Arrays;


/*
	2020/10/17
 */
public interface IBinaryPpmImage<V extends Number, C extends IColor, CH extends IColorChannel>
	extends IImage<V, C, CH>
{
	//
	enum Type
	{
		UNKNOWN(0, 0),
		I8(255, 3),
		I16(65535, 6);

		//
		final public int COLOR;
		final public int DIMENSION;

		//
		Type(int color, int dimension)
		{
			COLOR = color;
			DIMENSION = dimension;
		}
	}


	//
	Type type();
	Header header();


	//
	final class Header
	{
		//
		final private static String PREFIX_STRING = "P6\n";
		final private static byte[] PREFIX_BYTES = {Utf8Byte.P_CAPITAL, Utf8Byte.DIGIT_SIX, Utf8Byte.LINE_FEED};
		final private static int LIMIT = 32;

		//
		final public int WIDTH;
		final public int HEIGHT;
		final public int COLOR;
		final public int OFFSET;
		final byte[] VALUE;

		//
		public Header(int width, int height, int color)
		{
			byte[] value = bytes(width, height, color);

			WIDTH = width;
			HEIGHT = height;
			COLOR = color;
			OFFSET = value.length;
			VALUE = value;
		}

		private Header(int width, int height, int color, int offset, byte[] array)
		{
			WIDTH = width;
			HEIGHT = height;
			COLOR = color;
			OFFSET = offset;
			VALUE = Arrays.copyOf(array, offset);
		}

		//
		public Type type()
		{
			if (COLOR == Type.I8.COLOR) return Type.I8;
			else if (COLOR == Type.I16.COLOR) return Type.I16;
			else return Type.UNKNOWN;
		}

		//
		public static @NullAllow Header parse(@NullDisallow File file)
		{
			byte[] array = new byte[LIMIT];
			FileUtil.read(file, array);

			return parse(array);
		}

		public static @NullAllow Header parse(@NullAllow byte[] array)
		{
			Header result = null;

			if
			(
				array != null
				&&
				array.length >= LIMIT
				&&
				ByteArrayUtil.contains(array, PREFIX_BYTES, 0)
			)
			{
				boolean error = false;
				int width = 0;
				int height = 0;
				int color = 0;

				//
				int offset = PREFIX_BYTES.length;

				// width
				{
					int index2 = ByteArrayUtil.index(array, Utf8Byte.SPACE, offset, LIMIT);

					if (index2 != -1)
					{
						char[] chars = Utf8Util.parse(array, offset, index2);
						width = IntUtil.parse(chars, 0);
						offset = index2 + 1;
					}
					else
					{
						error = true;
					}
				}

				// height
				if (!error)
				{
					int index2 = ByteArrayUtil.index(array, Utf8Byte.LINE_FEED, offset, LIMIT);

					if (index2 != -1)
					{
						char[] chars = Utf8Util.parse(array, offset, index2);
						height = IntUtil.parse(chars, 0);
						offset = index2 + 1;
					}
					else
					{
						error = true;
					}
				}

				// color
				if (!error)
				{
					int index2 = ByteArrayUtil.index(array, Utf8Byte.LINE_FEED, offset, LIMIT);

					if (index2 != -1)
					{
						char[] chars = Utf8Util.parse(array, offset, index2);
						color = IntUtil.parse(chars, 0);
						offset = index2 + 1;
					}
					else
					{
						error = true;
					}
				}

				//
				if (!error) result = new Header(width, height, color, offset, array);
			}

			return result;
		}

		private static byte[] bytes(int width, int height, int color)
		{
			return AsciiUtil.bytes(PREFIX_STRING + width + " " + height + "\n" + color + "\n");
		}
	}

}
