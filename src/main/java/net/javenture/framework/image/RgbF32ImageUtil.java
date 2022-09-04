package net.javenture.framework.image;


import net.javenture.framework.util.FileContent;

import java.awt.image.BufferedImage;
import java.io.File;


/*
	2022/02/10
 */
final public class RgbF32ImageUtil
{
	//
	private RgbF32ImageUtil()
	{
	}


	//
	public static FileContent<RgbF32Image> read(File file, IImageOption... options)
	{
		ImageFormat format = ImageFormat.parse(file);

		return read(file, format, options);
	}


	public static FileContent<RgbF32Image> read(File file, ImageFormat format, IImageOption... options)
	{
		switch (format)
		{
			case DNG:
			{
				DngReadImageOption option = IImageOption.search(IImageOption.Type.READ, ImageFormat.DNG, options);

				if (option != null)
				{
					FileContent<IBinaryPpmImage> content = LibrawUtil.convert(file, option.PRESET);

					return content.STATUS == FileContent.Status.OK
						? new FileContent<>(FileContent.Status.OK, ImageUtil.convert(content.VALUE, RgbF32Image.INSTANCE))
						: new FileContent<>(content);
				}
				else
				{
					// ???


					throw new UnsupportedOperationException();
				}
			}

			case CR2:
			{
				Cr2ReadImageOption option = IImageOption.search(IImageOption.Type.READ, ImageFormat.CR2, options);

				if (option != null)
				{
					FileContent<IBinaryPpmImage> content = LibrawUtil.convert(file, option.PRESET);

					return content.STATUS == FileContent.Status.OK
						? new FileContent<>(FileContent.Status.OK, ImageUtil.convert(content.VALUE, RgbF32Image.INSTANCE))
						: new FileContent<>(content);
				}
				else
				{
					// ???


					throw new UnsupportedOperationException();
				}
			}

			case GPR:
			{
				GprReadImageOption option = IImageOption.search(IImageOption.Type.READ, ImageFormat.GPR, options);

				if (option != null)
				{
					FileContent<IBinaryPpmImage> content = GprUtil.convert(file, option.PRESET);

					return content.STATUS == FileContent.Status.OK
						? new FileContent<>(FileContent.Status.OK, ImageUtil.convert(content.VALUE, RgbF32Image.INSTANCE))
						: new FileContent<>(content);
				}
				else
				{
					// ???


					throw new UnsupportedOperationException();
				}
			}

			case JPEG:
			{
				FileContent<BufferedImage> content = JpegImageUtil.read(file);

				return content.STATUS == FileContent.Status.OK
					? new FileContent<>(FileContent.Status.OK, ImageUtil.convert(content.VALUE, RgbF32Image.INSTANCE))
					: new FileContent<>(content);
			}

			case PNG:
			{
				FileContent<BufferedImage> content = PngImageUtil.read(file);

				return content.STATUS == FileContent.Status.OK
					? new FileContent<>(FileContent.Status.OK, ImageUtil.convert(content.VALUE, RgbF32Image.INSTANCE))
					: new FileContent<>(content);
			}

			case PPM:
			{
				FileContent<IBinaryPpmImage> content = BinaryPpmImageUtil.read(file);

				return content.STATUS == FileContent.Status.OK
					? new FileContent<>(FileContent.Status.OK, ImageUtil.convert(content.VALUE, RgbF32Image.INSTANCE))
					: new FileContent<>(content);
			}

			default: return new FileContent<>(FileContent.Status.ERROR_CONTENT);
		}
	}


	public static FileContent<RgbF32Image> write(RgbF32Image source, File destination, IImageOption... options)
	{
		ImageFormat format = ImageFormat.parse(destination);

		return write(source, destination, format, options);
	}


	public static FileContent<RgbF32Image> write(RgbF32Image source, File destination, ImageFormat format, IImageOption... options)
	{
		switch (format)
		{
			case JPEG:
			{
				JpegWriteImageOption option = IImageOption.search(IImageOption.Type.WRITE, ImageFormat.JPEG, options, JpegWriteImageOption.DEFAULT);
				BufferedImage imageBuffered = ImageUtil.convert(source, BufferedImageUtil.INSTANCE);
				FileContent<BufferedImage> content = JpegImageUtil.write(imageBuffered, destination, option.QUALITY);

				return content.STATUS == FileContent.Status.OK
					? new FileContent<>(FileContent.Status.OK, source)
					: new FileContent<>(content);
			}

			case PNG:
			{
				PngWriteImageOption option = IImageOption.search(IImageOption.Type.WRITE, ImageFormat.PNG, options, PngWriteImageOption.DEFAULT);
				BufferedImage imageBuffered = ImageUtil.convert(source, BufferedImageUtil.INSTANCE);
				FileContent<BufferedImage> content = PngImageUtil.write(imageBuffered, destination, option.COMPRESSION);

				return content.STATUS == FileContent.Status.OK
					? new FileContent<>(FileContent.Status.OK, source)
					: new FileContent<>(content);
			}

			default: return new FileContent<>(FileContent.Status.ERROR_CONTENT);
		}
	}


}
