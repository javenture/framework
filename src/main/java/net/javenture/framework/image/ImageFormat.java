package net.javenture.framework.image;


import java.io.File;
import java.util.List;


/*
	2022/01/22
 */
public enum ImageFormat
{
	//
	UNKNOWN(), // !
	AVIF("avif"),
	CR2("cr2"),
	DNG("dng"),
	GPR("gpr"),
	JPEG("jpeg", "jpg"),
	PNG("png"),
	PPM("ppm"),
	TIFF("tiff", "tif"),
	WEBP("webp");


	//
	final private static ImageFormat[] VALUES = values();
	final private static int COUNT = VALUES.length;


	//
	final public String NAME;
	final public List<String> NAMES;
	final public String EXTENSION;


	//
	ImageFormat()
	{
		NAME = "";
		NAMES = List.of();
		EXTENSION = "." + NAME; // ! "."
	}


	ImageFormat(String... name)
	{
		NAME = name[0];
		NAMES = List.of(name);
		EXTENSION = "." + NAME; // ! "."
	}


	//
	public static ImageFormat parse(File file)
	{
		String name = file.getName();
		int index = name.lastIndexOf('.');

		if (index != -1)
		{
			String extension = name.substring(index + 1)
				.toLowerCase();

			for (int i = 1; i < COUNT; i++)
			{
				ImageFormat format = VALUES[i];

				for (String s : format.NAMES)
				{
					if (extension.equals(s)) return format;
				}
			}
		}

		return UNKNOWN;
	}

}
