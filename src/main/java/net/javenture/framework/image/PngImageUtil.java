package net.javenture.framework.image;


import net.javenture.framework.log.Log;
import net.javenture.framework.util.FileContent;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;


/*
	2020/10/19
 */
final public class PngImageUtil
{
	//
	final private static Log LOG = Log.instance(PngImageUtil.class);
	final private static ImageFormat FORMAT = ImageFormat.PNG;
	final public static float COMPRESSION_NONE = 1;
	final public static float COMPRESSION_MAX = 0;


	//
	private PngImageUtil()
	{
	}


	//
	public static FileContent<BufferedImage> read(File file)
	{
		return BufferedImageUtil.read(file);
	}


	public static FileContent<BufferedImage> write(BufferedImage source, File destination, float compression)
	{
		Iterator<ImageWriter> iterator = ImageIO.getImageWritersByFormatName(FORMAT.NAME);

		if (iterator.hasNext())
		{
			ImageWriter writer = iterator.next();

			return BufferedImageUtil.write(source, destination, writer, compression);
		}
		else
		{
			return new FileContent<>(FileContent.Status.ERROR, new Exception("ImageIO writer not found: " + FORMAT));
		}
	}


	static void write0(BufferedImage source, File destination, float compression)                                                         // ???
	{
		FileContent<BufferedImage> content = write(source, destination, compression);

		if (content.STATUS != FileContent.Status.OK) LOG.error(content.STATUS, content.EXCEPTION);
	}

}
