package net.javenture.framework.identifier;


import net.javenture.framework.annotation.Immutable;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.IFactoryObject;


/*
	2019/12/02
 */
@Immutable
public interface IIdentifier<V, I extends IIdentifier>
	extends IFactoryObject<I>, Comparable<I>
{
	//
	boolean defined();
	@NullDisallow V value();
	boolean contains(@NullDisallow V value);

}
