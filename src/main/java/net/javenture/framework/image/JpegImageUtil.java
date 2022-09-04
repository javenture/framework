package net.javenture.framework.image;


import net.javenture.framework.util.FileContent;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;


/*
	2020/10/19
 */
final public class JpegImageUtil
{
	//
	final private static ImageFormat FORMAT = ImageFormat.JPEG;


	//
	private JpegImageUtil()
	{
	}


	//
	public static FileContent<BufferedImage> read(File file)
	{
		return BufferedImageUtil.read(file);
	}


	public static FileContent<BufferedImage> write(BufferedImage source, File destination, float quality)
	{
		Iterator<ImageWriter> iterator = ImageIO.getImageWritersByFormatName(FORMAT.NAME);

		if (iterator.hasNext())
		{
			ImageWriter writer = iterator.next();

			return BufferedImageUtil.write(source, destination, writer, quality);
		}
		else
		{
			return new FileContent<>(FileContent.Status.ERROR, new Exception("ImageIO writer not found: " + FORMAT));
		}
	}

}
