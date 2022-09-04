package net.javenture.framework.boofcv;


import net.javenture.framework.image.AF32Color;
import net.javenture.framework.image.AF32Image;
import net.javenture.framework.image.IF32ColorChannel;

import boofcv.struct.image.GrayF32;


/*
	2020/05/01
 */
final public class BoofcvGrayF32Util
{
	//
	private BoofcvGrayF32Util()
	{
	}


	//
	public static GrayF32 convert(float[][] source, float scale)
	{
		int height = source.length;
		int width = height != 0 ? source[0].length : 0;

		GrayF32 result = new GrayF32(width, height);
		convert(source, scale, result);

		return result;
	}


	public static void convert(float[][] source, float scale, GrayF32 destination)
	{
		int width = destination.width;
		int height = destination.height;

		if (scale != 1)
		{
			for (int j = 0; j < height; j++)
			{
				float[] source2 = source[j];

				for (int i = 0; i < width; i++) destination.unsafe_set(i, j, source2[i] * scale);
			}
		}
		else
		{
			for (int j = 0; j < height; j++)
			{
				float[] array2 = source[j];

				for (int i = 0; i < width; i++) destination.unsafe_set(i, j, array2[i]);
			}
		}
	}


	public static
	<V extends Float, C extends AF32Color<V, H>, H extends IF32ColorChannel, I extends AF32Image<V, C, H>>
	GrayF32
	convert(I image, H channel, float scale)
	{
		GrayF32 result = new GrayF32(image.width(), image.height());
		convert(image, channel, scale, result);

		return result;
	}


	public static
	<V extends Float, C extends AF32Color<V, H>, H extends IF32ColorChannel, I extends AF32Image<V, C, H>>
	void
	convert(I source, H channel, float scale, GrayF32 destination)
	{
		int width = destination.width;
		int height = destination.height;

		if (scale != 1)
		{
			for (int j = 0; j < height; j++)
			{
				for (int i = 0; i < width; i++)
				{
					float value = source.get(i, j, channel);
					destination.unsafe_set(i, j, value * scale);
				}
			}
		}
		else
		{
			for (int j = 0; j < height; j++)
			{
				for (int i = 0; i < width; i++)
				{
					float value = source.get(i, j, channel);
					destination.unsafe_set(i, j, value);
				}
			}
		}
	}


	public static void convert(GrayF32 source, float[][] destination)
	{
		int width = source.width;
		int height = source.height;

		for (int j = 0; j < height; j++)
		{
			float[] destination2 = destination[j];

			for (int i = 0; i < width; i++) destination2[i] = source.unsafe_get(i, j);
		}
	}


	public static
	<V extends Float, C extends AF32Color<V, H>, H extends IF32ColorChannel, I extends AF32Image<V, C, H>>
	void
	convert(GrayF32 source, I destination, H channel)
	{
		int width = source.width;
		int height = source.height;

		for (int j = 0; j < height; j++)
		{
			for (int i = 0; i < width; i++)
			{
				float value = source.unsafe_get(i, j);
				destination.set0(i, j, channel, value);
			}
		}
	}

}
