package net.javenture.framework.factory;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.util.IConst;


/*
	2019/07/14
 */
public interface IConstFactory<T>
	extends IFactory<T>
{
	//
	FactoryType<IConstFactory> TYPE = new FactoryType<>("CONST");


	//
	int toInt(@NullDisallow T object);
	@NullDisallow T fromInt(int value) throws Exception;


	//
	@Override
	default FactoryType<IConstFactory> type()
	{
		return TYPE;
	}


	default @NullAllow T fromInt(int value, @NullAllow T init)
	{
		try
		{
			return fromInt(value);
		}
		catch (Exception ignore)
		{
			return init;
		}
	}

}
