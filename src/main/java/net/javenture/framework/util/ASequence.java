package net.javenture.framework.util;


import net.javenture.framework.annotation.NullAllow;


/*
	2019/07/13
 */
abstract public class ASequence<V, S extends ASequence>
	implements ISequence<V, S>
{
	//
	final private static int CHUNK = 1024;
	final private static float FACTOR = 1.5f;                          // XXX: not load factor -> pages !


	//
	@Override
	abstract public int hashCode();


	@Override
	abstract public boolean equals(S object);


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
		return equals((S) object);
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
