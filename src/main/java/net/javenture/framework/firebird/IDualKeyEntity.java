package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullDisallow;


/*
	2019/09/05
 */
public interface IDualKeyEntity<T0, T1>
{
	//
	T0 id0();
	T1 id1();


	//
	interface IKey<T0, T1>
	{
		boolean defined();
		@NullDisallow T0 id0();
		@NullDisallow T1 id1();
	}

}
