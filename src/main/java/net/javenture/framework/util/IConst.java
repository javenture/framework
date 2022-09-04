package net.javenture.framework.util;


import net.javenture.framework.annotation.Immutable;
import net.javenture.framework.annotation.Required;
import net.javenture.framework.factory.IFactoryObject;


/*
	2019/07/30
 */
@Immutable
public interface IConst<C extends IConst> // ! <C extends IConst>
	extends IFactoryObject<C>
{
	//
	boolean defined();
	int id();
	@Required String toString();
	int ordinal();

}
