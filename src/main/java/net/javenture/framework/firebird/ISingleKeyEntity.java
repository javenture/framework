package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullDisallow;


/*
	2019/09/05
 */
public interface ISingleKeyEntity<T>
{
	//
	T id();


	//
	interface IKey<T>                               // XXX: ISingleKey
	{
		boolean defined();
		@NullDisallow T id();
	}

}
