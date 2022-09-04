package net.javenture.framework.util;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.IFactoryObject;

import java.util.Objects;


/*
	2019/09/01
 */
public interface ISequence<V, S extends ISequence>
	extends IFactoryObject<S>, Iterable<V>
{
	//
	int capacity();
	int size();
	boolean exists();
	void clear();
	boolean contains(@NullDisallow V value);
	int index(@NullDisallow V value);
	@NullDisallow V get(int index);
	boolean add(@NullDisallow V value);
	void remove(int index);
	void ensure(int count);
	boolean valid();
	void sort();


	//
	static <V, S extends ISequence<V, S>> boolean equal(@NullAllow ISequence<V, S> object0, @NullAllow ISequence<V, S> object1) // ! <V, S extends ISequence<V, S>>
	{
		return Objects.equals(object0, object1);
	}

}
