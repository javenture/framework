package net.javenture.framework.factory;


import net.javenture.framework.annotation.NullAllow;


/*
	2020/01/05
 */
@FunctionalInterface
public interface IInstanceFactory<T>
	extends IFactory<T>
{
	//
	FactoryType<IInstanceFactory> TYPE = new FactoryType<>("INSTANCE");


	//
	boolean validate(@NullAllow Object object);


	//
	@Override
	default FactoryType<IInstanceFactory> type()
	{
		return TYPE;
	}

}
