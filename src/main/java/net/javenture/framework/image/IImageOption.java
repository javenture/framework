package net.javenture.framework.image;


import net.javenture.framework.annotation.NullAllow;


/*
	2020/10/19
 */
public interface IImageOption
{
	//
	enum Type
	{
		READ,
		WRITE
	}


	//
	Type type();
	ImageFormat format();


	//
	@SuppressWarnings("unchecked")
	static @NullAllow <T extends IImageOption> T search(Type type, ImageFormat format, IImageOption[] options)
	{
		for (IImageOption option : options)
		{
			if (option.type() == type && option.format() == format) return (T) option;
		}

		return null;
	}


	static @NullAllow <T extends IImageOption> T search(Type type, ImageFormat format, IImageOption[] options, @NullAllow T init)
	{
		T result = search(type, format, options);

		return result != null ? result : init;
	}

}
