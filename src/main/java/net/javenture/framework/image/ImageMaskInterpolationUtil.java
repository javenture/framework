package net.javenture.framework.image;


/*
	2020/08/24
 */
final public class ImageMaskInterpolationUtil
{
	//
	private ImageMaskInterpolationUtil()
	{
	}


	//
	@SuppressWarnings("UnnecessaryLocalVariable")
	public static void fill(LabF32Image image, boolean[][] mask)
	{
		int width = image.WIDTH;
		int height = image.HEIGHT;
		LabF32Color color = new LabF32Color();


		// XXX
		int c2 = 0;


		for (int j = 0; j < height; j++)
		{
			for (int i = 0; i < width; i++)
			{
				if (!mask[j][i])
				{
					c2++;

					int count = 0;
					float l = 0;
					float a = 0;
					float b = 0;

					// [0, -1]
					{
						int x = i;
						int y = j - 1;

						if (image.contains(x, y) && mask[y][x])
						{
							count++;
							image.get0(x, y, color);
							l += color.l();
							a += color.a();
							b += color.b();
						}
					}

					// [+1, -1]
					{
						int x = i + 1;
						int y = j - 1;

						if (image.contains(x, y) && mask[y][x])
						{
							count++;
							image.get0(x, y, color);
							l += color.l();
							a += color.a();
							b += color.b();
						}
					}

					// [+1, 0]
					{
						int x = i + 1;
						int y = j;

						if (image.contains(x, y) && mask[y][x])
						{
							count++;
							image.get0(x, y, color);
							l += color.l();
							a += color.a();
							b += color.b();
						}
					}

					// [+1, +1]
					{
						int x = i + 1;
						int y = j + 1;

						if (image.contains(x, y) && mask[y][x])
						{
							count++;
							image.get0(x, y, color);
							l += color.l();
							a += color.a();
							b += color.b();
						}
					}

					// [0, +1]
					{
						int x = i;
						int y = j + 1;

						if (image.contains(x, y) && mask[y][x])
						{
							count++;
							image.get0(x, y, color);
							l += color.l();
							a += color.a();
							b += color.b();
						}
					}

					// [-1, +1]
					{
						int x = i - 1;
						int y = j + 1;

						if (image.contains(x, y) && mask[y][x])
						{
							count++;
							image.get0(x, y, color);
							l += color.l();
							a += color.a();
							b += color.b();
						}
					}

					// [-1, 0]
					{
						int x = i - 1;
						int y = j;

						if (image.contains(x, y) && mask[y][x])
						{
							count++;
							image.get0(x, y, color);
							l += color.l();
							a += color.a();
							b += color.b();
						}
					}

					// [-1, -1]
					{
						int x = i - 1;
						int y = j - 1;

						if (image.contains(x, y) && mask[y][x])
						{
							count++;
							image.get0(x, y, color);
							l += color.l();
							a += color.a();
							b += color.b();
						}
					}

					//
					if (count != 0)
					{
						color.lab0(l / count, a / count, b / count);
						image.set0(i, j, color);
					}
				}
			}
		}


		// XXX
		System.out.println("c2: " + c2);
	}

}
