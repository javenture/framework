package net.javenture.framework.factory;


import net.javenture.framework.annotation.NullAllow;

import java.util.Objects;


/*
	2019/07/20
 */
final public class FactoryObjectUtil
{
	//
	private FactoryObjectUtil()
	{
	}


	//
	public static <T extends IFactoryObject<T>> boolean equal(@NullAllow T object0, @NullAllow T object1) // ! <T extends IFactoryObject<T>>
	{
		return Objects.equals(object0, object1);
	}

}
