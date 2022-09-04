package net.javenture.framework.factory;


/*
	2017/10/01
 */
public interface IFactory<T>
{
	//
	FactoryType<? extends IFactory> type();

}
