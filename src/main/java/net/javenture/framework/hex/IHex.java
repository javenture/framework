package net.javenture.framework.hex;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.IFactoryObject;
import net.javenture.framework.hash.IMurmur3Hashable;


/*
	2019/08/02
 */
public interface IHex<V, H extends IHex>
	extends IFactoryObject<H>, IMurmur3Hashable
{
	//
	int capacity();
	@NullDisallow V value();
	String toString(boolean capitalize);


	//
	@FunctionalInterface
	interface IInstance<H extends IHex>
	{
		H get(@NullDisallow byte[] array);
	}

}
