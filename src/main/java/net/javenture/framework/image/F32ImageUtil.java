package net.javenture.framework.image;


/*
	2021/06/28
 */
final public class F32ImageUtil
{
	//
	private F32ImageUtil()
	{
	}


	//
	public static
	<C extends AF32Color<Float, CH>, CH extends IF32ColorChannel, I extends AF32Image<Float, C, CH>>
	void
	fill(I image, C color)
	{
		int width = image.width();
		int height = image.height();

		for (int j = 0; j < height; j++)
		{
			for (int i = 0; i < width; i++) image.set0(i, j, color);
		}
	}


	/*
		    | 01 |
		--------------
		 10 | 11 | 12
		--------------
		    | 21 |
	 */
	public static
	<C extends AF32Color<Float, CH>, CH extends IF32ColorChannel, I extends AF32Image<Float, C, CH>>
	void
	sharpen4(I source, I destination, IColor.IColorInstance<C> instance, CH[] channels)
	{
		int width = source.width();
		int height = source.height();
		C color = instance.create();
		C color01 = instance.create();
		C color10 = instance.create();
		C color11 = instance.create();
		C color12 = instance.create();
		C color21 = instance.create();

		//
		{
			int iFrom = 1;
			int iTo = width - 1;
			int jFrom = 1;
			int jTo = height - 1;

			for (int j = jFrom; j < jTo; j++)
			{
				for (int i = iFrom; i < iTo; i++)
				{
					source.get0(i, j - 1, color01);
					source.get0(i - 1, j, color10);
					source.get0(i, j, color11);
					source.get0(i + 1, j, color12);
					source.get0(i, j + 1, color21);

					for (CH channel : channels)
					{
						double v01 = color01.get(channel);
						double v10 = color10.get(channel);
						double v11 = color11.get(channel);
						double v12 = color12.get(channel);
						double v21 = color21.get(channel);
						double value = 5 * v11 - (v01 + v10 + v12 + v21);
						color.set0(channel, value); // ! .set0()
					}

					destination.set0(i, j, color); // ! .set0()
				}
			}
		}

		// top left
		{
			int i = 0;
			int j = 0;

			source.get0(i, j, color11);
			source.get0(i + 1, j, color12);
			source.get0(i, j + 1, color21);

			for (CH channel : channels)
			{
				double v11 = color11.get(channel);
				double v12 = color12.get(channel);
				double v21 = color21.get(channel);
				double value = 3 * v11 - (v12 + v21);
				color.set0(channel, value); // ! .set0()
			}

			destination.set0(i, j, color); // ! .set0()
		}

		// top
		{
			int iFrom = 1;
			int iTo = width - 1;
			int j = 0;

			for (int i = iFrom; i < iTo; i++)
			{
				source.get0(i - 1, j, color10);
				source.get0(i, j, color11);
				source.get0(i + 1, j, color12);
				source.get0(i, j + 1, color21);

				for (CH channel : channels)
				{
					double v10 = color10.get(channel);
					double v11 = color11.get(channel);
					double v12 = color12.get(channel);
					double v21 = color21.get(channel);
					double value = 4 * v11 - (v10 + v12 + v21);
					color.set0(channel, value); // ! .set0()
				}

				destination.set0(i, j, color); // ! .set0()
			}
		}

		// top right
		{
			int i = width - 1;
			int j = 0;

			source.get0(i - 1, j, color10);
			source.get0(i, j, color11);
			source.get0(i, j + 1, color21);

			for (CH channel : channels)
			{
				double v10 = color10.get(channel);
				double v11 = color11.get(channel);
				double v21 = color21.get(channel);
				double value = 3 * v11 - (v10 + v21);
				color.set0(channel, value); // ! .set0()
			}

			destination.set0(i, j, color); // ! .set0()
		}

		// right
		{
			int jFrom = 1;
			int jTo = height - 1;
			int i = width - 1;

			for (int j = jFrom; j < jTo; j++)
			{
				source.get0(i, j - 1, color01);
				source.get0(i - 1, j, color10);
				source.get0(i, j, color11);
				source.get0(i, j + 1, color21);

				for (CH channel : channels)
				{
					double v01 = color01.get(channel);
					double v10 = color10.get(channel);
					double v11 = color11.get(channel);
					double v21 = color21.get(channel);
					double value = 4 * v11 - (v01 + v10 + v21);
					color.set0(channel, value); // ! .set0()
				}

				destination.set0(i, j, color); // ! .set0()
			}
		}

		// bottom right
		{
			int i = width - 1;
			int j = height - 1;

			source.get0(i, j - 1, color01);
			source.get0(i - 1, j, color10);
			source.get0(i, j, color11);

			for (CH channel : channels)
			{
				double v01 = color01.get(channel);
				double v10 = color10.get(channel);
				double v11 = color11.get(channel);
				double value = 3 * v11 - (v01 + v10);
				color.set0(channel, value); // ! .set0()
			}

			destination.set0(i, j, color); // ! .set0()
		}

		// bottom
		{
			int iFrom = 1;
			int iTo = width - 1;
			int j = height - 1;

			for (int i = iFrom; i < iTo; i++)
			{
				source.get0(i, j - 1, color01);
				source.get0(i - 1, j, color10);
				source.get0(i, j, color11);
				source.get0(i + 1, j, color12);

				for (CH channel : channels)
				{
					double v01 = color01.get(channel);
					double v10 = color10.get(channel);
					double v11 = color11.get(channel);
					double v12 = color12.get(channel);
					double value = 4 * v11 - (v01 + v10 + v12);
					color.set0(channel, value); // ! .set0()
				}

				destination.set0(i, j, color); // ! .set0()
			}
		}

		// bottom left
		{
			int i = 0;
			int j = height - 1;

			source.get0(i, j - 1, color01);
			source.get0(i, j, color11);
			source.get0(i + 1, j, color12);

			for (CH channel : channels)
			{
				double v01 = color01.get(channel);
				double v11 = color11.get(channel);
				double v12 = color12.get(channel);
				double value = 3 * v11 - (v01 + v12);
				color.set0(channel, value); // ! .set0()
			}

			destination.set0(i, j, color); // ! .set0()
		}

		// left
		{
			int jFrom = 1;
			int jTo = height - 1;
			int i = 0;

			for (int j = jFrom; j < jTo; j++)
			{
				source.get0(i, j - 1, color01);
				source.get0(i, j, color11);
				source.get0(i + 1, j, color12);
				source.get0(i, j + 1, color21);

				for (CH channel : channels)
				{
					double v01 = color01.get(channel);
					double v11 = color11.get(channel);
					double v12 = color12.get(channel);
					double v21 = color21.get(channel);
					double value = 4 * v11 - (v01 + v12 + v21);
					color.set0(channel, value); // ! .set0()
				}

				destination.set0(i, j, color); // ! .set0()
			}
		}
	}


	/*
		 00 | 01 | 02
		--------------
		 10 | 11 | 12
		--------------
		 20 | 21 | 22
	 */
/*
	public static
	<C extends AF32Color<Float, CH>, CH extends IF32ColorChannel, I extends AF32Image<Float, C, CH>>
	void
	sharpen8(I source, I destination, IColor.IColorInstance<C> instance, CH[] channels)
	{
		int width = source.width();
		int height = source.height();
		int iFrom = 1;
		int iTo = width - 1;
		int jFrom = 1;
		int jTo = height - 1;
		C color = instance.create();
		C color00 = instance.create();
		C color01 = instance.create();
		C color02 = instance.create();
		C color10 = instance.create();
		C color11 = instance.create();
		C color12 = instance.create();
		C color20 = instance.create();
		C color21 = instance.create();
		C color22 = instance.create();

		//
		{
			for (int j = jFrom; j < jTo; j++)
			{
				for (int i = iFrom; i < iTo; i++)
				{
					source.get0(i - 1, j - 1, color00);
					source.get0(i, j - 1, color01);
					source.get0(i + 1, j - 1, color02);
					source.get0(i - 1, j, color10);
					source.get0(i, j, color11);
					source.get0(i + 1, j, color12);
					source.get0(i - 1, j + 1, color20);
					source.get0(i, j + 1, color21);
					source.get0(i + 1, j + 1, color22);

					for (CH channel : channels)
					{
						double v00 = color00.get(channel);
						double v01 = color01.get(channel);
						double v02 = color02.get(channel);
						double v10 = color10.get(channel);
						double v11 = color11.get(channel);
						double v12 = color12.get(channel);
						double v20 = color20.get(channel);
						double v21 = color21.get(channel);
						double v22 = color22.get(channel);
						double value = 9 * v11 - (v00 + v01 + v02 + v10 + v12 + v20 + v21 + v22);
						color.set0(channel, value);


						// XXX: range
					}

					destination.set0(i, j, color);
				}
			}
		}




		// top left
		{

		}

		// top right
		{

		}

		// bottom right
		{

		}

		// bottom left
		{

		}

		// top
		{

		}

		// right
		{

		}

		// bottom
		{

		}

		// left
		{

		}
	}
*/

}
