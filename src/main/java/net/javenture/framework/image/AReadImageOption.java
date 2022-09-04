package net.javenture.framework.image;

/*
	2020/10/19
 */
abstract public class AReadImageOption
	implements IImageOption
{
	//
	@Override
	final public Type type()
	{
		return Type.READ;
	}

}
