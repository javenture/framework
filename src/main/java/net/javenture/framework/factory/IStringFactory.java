package net.javenture.framework.factory;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.util.IParser;


/*
	2019/10/01
 */
 public interface IStringFactory<T>
	extends IFactory<T>
{
	//
	FactoryType<IStringFactory> TYPE = new FactoryType<>("STRING");


	//
	@NullDisallow String toString(@NullAllow T object);
	@NullDisallow IParser<T> parser(@NullAllow T init, boolean nullable, boolean trim);


	//
	@Override
	default FactoryType<IStringFactory> type()
	{
		return TYPE;
	}

}
