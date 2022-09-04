package net.javenture.framework.identifier;


import net.javenture.framework.annotation.NullAllow;


/*
	2019/11/21
 */
abstract class AIdentifier<V, I extends AIdentifier>
	implements IIdentifier<V, I>
{
	//
	@Override
	abstract public int hashCode();


	@Override
	abstract public boolean equals(@NullAllow I object);


	@Override
	abstract public String toString();


	//
	@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
	final public boolean equals(@NullAllow Object object)
	{
		//noinspection unchecked
		return equals((I) object);
	}

}
