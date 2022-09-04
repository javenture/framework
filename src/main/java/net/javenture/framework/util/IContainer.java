package net.javenture.framework.util;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.IFactoryObject;

import java.util.Objects;


/*
	2019/09/28
 */
public interface IContainer<V, C extends IContainer>
	extends IFactoryObject<C>, Iterable<V>
{
	//
	int capacity();
	int size();
	boolean exists();
	void clear();
	boolean contains(@NullDisallow V value);
	int index(@NullDisallow V value);
	@NullDisallow V get(int index);
	void add(@NullDisallow V value);
	void remove(int index);
	void ensure(int count);
	void sort();


	//
	static <V, C extends IContainer<V, C>> boolean equal(@NullAllow IContainer<V, C> object0, @NullAllow IContainer<V, C> object1) // ! <V, C extends IContainer<V, C>>
	{
		return Objects.equals(object0, object1);
	}

}
