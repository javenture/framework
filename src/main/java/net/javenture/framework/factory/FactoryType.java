package net.javenture.framework.factory;


/*
	2017/09/29
 */
final public class FactoryType<F extends IFactory>
{
	//
	final private String NAME;


	//
	public FactoryType(String name)
	{
		NAME = name;
	}


	//
	public String name()
	{
		return NAME;
	}


	@Override
	public String toString()
	{
		return NAME;
	}

}
