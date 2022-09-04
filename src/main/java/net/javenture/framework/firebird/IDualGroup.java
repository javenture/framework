package net.javenture.framework.firebird;


import net.javenture.framework.log.ILog;


/*
	2017/12/01
 */
public interface IDualGroup<K0, K1, E extends IDualGroup.IEntry>
	extends IDatabaseOperation, ILog, Iterable<E>
{
	//
	K0 key0();
	K1 key1();

	int size();

	void add(E entry);

	void clear();

	void sort();


	//
	interface IEntry
	{
	}

}
