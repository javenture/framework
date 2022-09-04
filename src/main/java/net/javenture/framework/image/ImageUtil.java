package net.javenture.framework.image;


import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;


/*
	2022/02/10
 */
final public class ImageUtil
{
	//
	private ImageUtil()
	{
	}


	//
	public static void init(BufferedImage image, RgbI8Color color)                                                                   // XXX: BufferedImage -> RgbaI8BufferedImage
	{
		int rgba = color.rgb();
		DataBuffer buffer = image.getRaster().getDataBuffer();
		int type = buffer.getDataType();

		if (type == DataBuffer.TYPE_INT)
		{
			DataBufferInt buffer0 = (DataBufferInt) buffer;
			int[] data = buffer0.getData();
			int length = data.length;

			for (int i = 0; i < length; i++) data[i] = rgba;
		}
		else
		{
			throw new UnsupportedOperationException("type: " + type);
		}
	}


	public static RgbF32Image convert(BufferedImage source, RgbF32Image.IRgbF32ImageInstance instance)
	{
		int width = source.getWidth();
		int height = source.getHeight();
		RgbF32Image result = instance.create(width, height);
		convert(source, result);

		return result;
	}


	public static void convert(BufferedImage source, RgbF32Image destination)
	{
		// XXX: validation


		int width = source.getWidth();
		int height = source.getHeight();
		ColorConverter converter = new ColorConverter();
		RgbI8Color colorRgbI8 = new RgbI8Color();
		RgbF32Color colorRgbF32 = new RgbF32Color();

		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				colorRgbI8.rgb(source.getRGB(x, y));                                                        // XXX: DataBuffer
				converter.convert(colorRgbI8, colorRgbF32);
				destination.set0(x, y, colorRgbF32);
			}
		}
	}


	public static BufferedImage convert(RgbF32Image source, BufferedImageUtil.IBufferedImageInstance instance)
	{
		int width = source.width();
		int height = source.height();
		BufferedImage result = instance.create(width, height, BufferedImage.TYPE_INT_RGB);
		convert(source, result);

		return result;
	}


	public static void convert(RgbF32Image source, BufferedImage destination)
	{
		// XXX: validation


		int width = source.width();
		int height = source.height();
		ColorConverter converter = new ColorConverter();
		RgbF32Color colorRgbF32 = new RgbF32Color();
		RgbI8Color colorRgbI8 = new RgbI8Color();

		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				source.get0(x, y, colorRgbF32);
				converter.convert(colorRgbF32, colorRgbI8);
				destination.setRGB(x, y, colorRgbI8.rgb());
			}
		}
	}


	public static void convert(BufferedImage source, RgbI8Image destination)
	{
		// XXX: validation


		int width = source.getWidth();
		int height = source.getHeight();
		RgbI8Color colorRgbI8 = new RgbI8Color();

		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				colorRgbI8.rgb(source.getRGB(x, y));
				destination.set0(x, y, colorRgbI8);
			}
		}
	}


	public static void convert(RgbI8Image source, BufferedImage destination)
	{
		// XXX: validation


		int width = source.width();
		int height = source.height();
		RgbI8Color colorRgbaI8 = new RgbI8Color();

		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				source.get0(x, y, colorRgbaI8);
				destination.setRGB(x, y, colorRgbaI8.rgb());
			}
		}
	}


	public static LabF32Image convert(BufferedImage source, LabF32Image.ILabF32ImageInstance instance, RgbColorSpace space)
	{
		int width = source.getWidth();
		int height = source.getHeight();
		LabF32Image result = instance.create(width, height);
		convert(source, result, space);

		return result;
	}


	public static void convert(BufferedImage source, LabF32Image destination, RgbColorSpace space)
	{
		// XXX: validation


		int width = source.getWidth();
		int height = source.getHeight();
		ColorConverter converter = new ColorConverter();
		RgbI8Color colorRgbI8 = new RgbI8Color();
		LabF32Color colorLabF32 = new LabF32Color();

		for (int j = 0; j < height; j++)
		{
			for (int i = 0; i < width; i++)
			{
				colorRgbI8.rgb(source.getRGB(i, j));                                  // XXX: DataBuffer
				converter.convert(colorRgbI8, colorLabF32, space);
				destination.set0(i, j, colorLabF32);
			}
		}
	}


	public static BufferedImage convert(LabF32Image source, BufferedImageUtil.IBufferedImageInstance instance, RgbColorSpace space)
	{
		int width = source.width();
		int height = source.height();
		BufferedImage result = instance.create(width, height, BufferedImage.TYPE_INT_RGB);
		convert(source, result, space);

		return result;
	}


	public static void convert(LabF32Image source, BufferedImage destination, RgbColorSpace space)
	{
		// XXX: validation


		int width = source.width();
		int height = source.height();
		ColorConverter converter = new ColorConverter();
		LabF32Color colorLabF32 = new LabF32Color();
		RgbI8Color colorRgbI8 = new RgbI8Color();

		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				source.get0(x, y, colorLabF32);
				converter.convert(colorLabF32, colorRgbI8, space);
				destination.setRGB(x, y, colorRgbI8.rgb());                                                            // XXX: DataBuffer
			}
		}
	}







	public static LabF32Image convert(IBinaryPpmImage source, LabF32Image.ILabF32ImageInstance instance, RgbColorSpace space)
	{
		int width = source.width();
		int height = source.height();
		LabF32Image result = instance.create(width, height);
		convert(source, result, space);

		return result;
	}


	public static void convert(IBinaryPpmImage source, LabF32Image destination, RgbColorSpace space)
	{
		switch (source.type())
		{
			case I8:
			{
				convert((BinaryPpmI8Image) source, destination, space);

				break;
			}

			case I16:
			{
				convert((BinaryPpmI16Image) source, destination, space);

				break;
			}

			default: throw new RuntimeException(); // !
		}
	}


	public static LabF32Image convert(BinaryPpmI8Image source, LabF32Image.ILabF32ImageInstance instance, RgbColorSpace space)
	{
		int width = source.width();
		int height = source.height();
		LabF32Image result = instance.create(width, height);
		convert(source, result, space);

		return result;
	}


	public static void convert(BinaryPpmI8Image source, LabF32Image destination, RgbColorSpace space)
	{
		int width = source.width();
		int height = source.height();
		RgbI8Color colorRgbI8 = new RgbI8Color();
		LabF32Color colorLabF32 = new LabF32Color();
		ColorConverter converter = new ColorConverter();

		for (int j = 0; j < height; j++)
		{
			for (int i = 0; i < width; i++)
			{
				source.get0(i, j, colorRgbI8);
				converter.convert(colorRgbI8, colorLabF32, space);
				destination.set0(i, j, colorLabF32);
			}
		}
	}


	public static void convert(LabF32Image source, BinaryPpmI8Image destination, RgbColorSpace space)
	{
		int width = source.width();
		int height = source.height();
		LabF32Color colorLabF32 = new LabF32Color();
		RgbI8Color colorRgbI8 = new RgbI8Color();
		ColorConverter converter = new ColorConverter();

		for (int j = 0; j < height; j++)
		{
			for (int i = 0; i < width; i++)
			{
				source.get0(i, j, colorLabF32);
				converter.convert(colorLabF32, colorRgbI8, space);
				destination.set0(i, j, colorRgbI8);
			}
		}
	}


	public static LabF32Image convert(BinaryPpmI16Image source, LabF32Image.ILabF32ImageInstance instance, RgbColorSpace space)
	{
		int width = source.width();
		int height = source.height();
		LabF32Image result = instance.create(width, height);
		convert(source, result, space);

		return result;
	}


	public static void convert(BinaryPpmI16Image source, LabF32Image destination, RgbColorSpace space)
	{
		int width = source.width();
		int height = source.height();
		RgbI16Color colorRgbI16 = new RgbI16Color();
		LabF32Color colorLabF32 = new LabF32Color();
		ColorConverter converter = new ColorConverter();

		for (int j = 0; j < height; j++)
		{
			for (int i = 0; i < width; i++)
			{
				source.get0(i, j, colorRgbI16);
				converter.convert(colorRgbI16, colorLabF32, space);
				destination.set0(i, j, colorLabF32);
			}
		}
	}


	public static void convert(LabF32Image source, BinaryPpmI16Image destination, RgbColorSpace space)
	{
		int width = source.width();
		int height = source.height();
		LabF32Color colorLabF32 = new LabF32Color();
		RgbI16Color colorRgbI16 = new RgbI16Color();
		ColorConverter converter = new ColorConverter();

		for (int j = 0; j < height; j++)
		{
			for (int i = 0; i < width; i++)
			{
				source.get0(i, j, colorLabF32);
				converter.convert(colorLabF32, colorRgbI16, space);
				destination.set0(i, j, colorRgbI16);
			}
		}
	}


























	public static RgbF32Image convert(IBinaryPpmImage source, RgbF32Image.IRgbF32ImageInstance instance)
	{
		int width = source.width();
		int height = source.height();
		RgbF32Image result = instance.create(width, height);
		convert(source, result);

		return result;
	}


	public static void convert(IBinaryPpmImage source, RgbF32Image destination)
	{
		switch (source.type())
		{
			case I8:
			{
				convert((BinaryPpmI8Image) source, destination);

				break;
			}

			case I16:
			{
				convert((BinaryPpmI16Image) source, destination);

				break;
			}

			default: throw new RuntimeException(); // !
		}
	}


	public static RgbF32Image convert(BinaryPpmI8Image source, RgbF32Image.IRgbF32ImageInstance instance, RgbColorSpace space)
	{
		int width = source.width();
		int height = source.height();
		RgbF32Image result = instance.create(width, height);
		convert(source, result);

		return result;
	}


	public static void convert(BinaryPpmI8Image source, RgbF32Image destination)
	{
		int width = source.width();
		int height = source.height();
		RgbI8Color colorRgbI8 = new RgbI8Color();
		RgbF32Color colorRgbF32 = new RgbF32Color();
		ColorConverter converter = new ColorConverter();

		for (int j = 0; j < height; j++)
		{
			for (int i = 0; i < width; i++)
			{
				source.get0(i, j, colorRgbI8);
				converter.convert(colorRgbI8, colorRgbF32);
				destination.set0(i, j, colorRgbF32);
			}
		}
	}


	public static void convert(RgbF32Image source, BinaryPpmI8Image destination)
	{
		int width = source.width();
		int height = source.height();
		RgbF32Color colorRgbF32 = new RgbF32Color();
		RgbI8Color colorRgbI8 = new RgbI8Color();
		ColorConverter converter = new ColorConverter();

		for (int j = 0; j < height; j++)
		{
			for (int i = 0; i < width; i++)
			{
				source.get0(i, j, colorRgbF32);
				converter.convert(colorRgbF32, colorRgbI8);
				destination.set0(i, j, colorRgbI8);
			}
		}
	}


	public static RgbF32Image convert(BinaryPpmI16Image source, RgbF32Image.IRgbF32ImageInstance instance)
	{
		int width = source.width();
		int height = source.height();
		RgbF32Image result = instance.create(width, height);
		convert(source, result);

		return result;
	}


	public static void convert(BinaryPpmI16Image source, RgbF32Image destination)
	{
		int width = source.width();
		int height = source.height();
		RgbI16Color colorRgbI16 = new RgbI16Color();
		RgbF32Color colorRgbF32 = new RgbF32Color();
		ColorConverter converter = new ColorConverter();

		for (int j = 0; j < height; j++)
		{
			for (int i = 0; i < width; i++)
			{
				source.get0(i, j, colorRgbI16);
				converter.convert(colorRgbI16, colorRgbF32);
				destination.set0(i, j, colorRgbF32);
			}
		}
	}


	public static void convert(RgbF32Image source, BinaryPpmI16Image destination)
	{
		int width = source.width();
		int height = source.height();
		RgbF32Color colorRgbF32 = new RgbF32Color();
		RgbI16Color colorRgbI16 = new RgbI16Color();
		ColorConverter converter = new ColorConverter();

		for (int j = 0; j < height; j++)
		{
			for (int i = 0; i < width; i++)
			{
				source.get0(i, j, colorRgbF32);
				converter.convert(colorRgbF32, colorRgbI16);
				destination.set0(i, j, colorRgbI16);
			}
		}
	}














	public static void convert(BinaryPpmI8Image source, BufferedImage destination)
	{
		int width = source.width();
		int height = source.height();
		RgbI8Color colorRgbI8 = new RgbI8Color();

		for (int j = 0; j < height; j++)
		{
			for (int i = 0; i < width; i++)
			{
				source.get0(i, j, colorRgbI8);
				destination.setRGB(i, j, colorRgbI8.rgb());
			}
		}
	}


	public static void convert(BufferedImage source, BinaryPpmI8Image destination)
	{
		int width = source.getWidth();
		int height = source.getHeight();
		RgbI8Color colorRgbI8 = new RgbI8Color();

		for (int j = 0; j < height; j++)
		{
			for (int i = 0; i < width; i++)
			{
				int rgb = source.getRGB(i, j);
				colorRgbI8.rgb(rgb);
				destination.set0(i, j, colorRgbI8);
			}
		}
	}


	public static void convert(BinaryPpmI16Image source, BufferedImage destination)
	{
		int width = source.width();
		int height = source.height();
		RgbI16Color colorRgbI16 = new RgbI16Color();
		RgbI8Color colorRgbI8 = new RgbI8Color();
		ColorConverter converter = new ColorConverter();

		for (int j = 0; j < height; j++)
		{
			for (int i = 0; i < width; i++)
			{
				source.get0(i, j, colorRgbI16);
				converter.convert(colorRgbI16, colorRgbI8);
				destination.setRGB(i, j, colorRgbI8.rgb());
			}
		}
	}


	public static void convert(BufferedImage source, BinaryPpmI16Image destination)
	{
		int width = source.getWidth();
		int height = source.getHeight();
		RgbI16Color colorRgbI16 = new RgbI16Color();
		RgbI8Color colorRgbI8 = new RgbI8Color();
		ColorConverter converter = new ColorConverter();

		for (int j = 0; j < height; j++)
		{
			for (int i = 0; i < width; i++)
			{
				int rgb = source.getRGB(i, j);
				colorRgbI8.rgb(rgb);
				converter.convert(colorRgbI8, colorRgbI16);
				destination.set0(i, j, colorRgbI16);
			}
		}
	}

}
