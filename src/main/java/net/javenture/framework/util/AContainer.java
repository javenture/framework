package net.javenture.framework.util;


import net.javenture.framework.annotation.NullAllow;


/*
	2018/12/20
 */
abstract public class AContainer<V, C extends AContainer>
	implements IContainer<V, C>
{
	//
	final private static int CHUNK = 1024;
	final private static float FACTOR = 1.5f;                          // XXX: not load factor -> pages !


	//
	@Override
	abstract public int hashCode();


	@Override
	abstract public boolean equals(C object);


	//
	@Override
	final public boolean exists()
	{
		return size() != 0;
	}


	@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
	final public boolean equals(@NullAllow Object object)
	{
		//noinspection unchecked
		return equals((C) object);
	}


	@SuppressWarnings("NumericCastThatLosesPrecision")
	protected int calculate(int capacity)                                                  // XXX: 128 -> 512 -> 2048 -> 16K (*n)
	{
		int resize = (int) (capacity * FACTOR);
		int count = resize / CHUNK;

		if (count * CHUNK < resize) count++;

		return count * CHUNK;
	}

}
