package net.javenture.framework.factory;


import net.javenture.framework.annotation.NullAllow;


/*
	2017/11/22
 */
@FunctionalInterface
public interface ICopyFactory<T>
	extends IFactory<T>
{
	//
	FactoryType<ICopyFactory> TYPE = new FactoryType<>("COPY");


	//
	@NullAllow T copy(@NullAllow T object);


	//
	@Override
	default FactoryType<ICopyFactory> type()
	{
		return TYPE;
	}

}
