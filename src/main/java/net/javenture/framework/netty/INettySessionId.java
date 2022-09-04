package net.javenture.framework.netty;


import net.javenture.framework.factory.IFactoryObject;


/*
	2018/08/22
 */
public interface INettySessionId<I extends INettySessionId>
	extends IFactoryObject<I>, Comparable<I>
{
}
