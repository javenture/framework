package net.javenture.framework.image;


import net.javenture.framework.util.FileContent;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;


/*
	2022/02/10
 */
final public class BufferedImageUtil
{
	//
	final public static IBufferedImageInstance INSTANCE = BufferedImage::new;


	//
	private BufferedImageUtil()
	{
	}


	//
	public static FileContent<BufferedImage> read(File file)
	{
		FileContent<BufferedImage> result;

		try
		{
			result = file.exists()
				? new FileContent<>(FileContent.Status.OK, ImageIO.read(file))
				: new FileContent<>(FileContent.Status.NOT_FOUND);
		}
		catch (Exception e)
		{
			result = new FileContent<>(FileContent.Status.ERROR, e);
		}

		return result;
	}


	public static FileContent<BufferedImage> write(BufferedImage source, File destination, ImageWriter writer, float quality)
	{
		FileContent<BufferedImage> result;

		try
		{
			IIOImage image = new IIOImage(source, null, null);

			ImageWriteParam parameter = writer.getDefaultWriteParam();
			parameter.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			parameter.setCompressionQuality(quality);

			try (ImageOutputStream stream = ImageIO.createImageOutputStream(destination))
			{
				writer.setOutput(stream);
				writer.write(null, image, parameter);
				writer.dispose();
				stream.flush();
			}

			result = new FileContent<>(FileContent.Status.OK, source);
		}
		catch (Exception e)
		{
			result = new FileContent<>(FileContent.Status.ERROR, e);
		}

		return result;
	}


	//
	@FunctionalInterface
	public interface IBufferedImageInstance
	{
		BufferedImage create(int width, int height, int type);
	}

}
