package net.javenture.framework.factory;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.Required;


/*
	2019/09/09
 */
public interface IFactoryObject<O extends IFactoryObject> // ! <O extends IFactoryObject>
{
	//
	Factories<O> factories();
	@Required int hashCode();
	@Required boolean equals(@NullAllow Object object);
	@Required boolean equals(@NullAllow O object);

}
