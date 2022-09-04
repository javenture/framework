package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.factory.IDatabaseFactory;
import net.javenture.framework.factory.IFactoryObject;
import net.javenture.framework.util.Chain;
import net.javenture.framework.util.IContainer;
import net.javenture.framework.util.ISequence;

import java.util.List;
import java.util.Set;


/*
	2019/12/14
 */
@FunctionalInterface
public interface IColumnSqlProcessor<T>
	extends ISqlProcessor
{
	//
	void process(T object) throws Exception;


	//
	static <V, C extends IContainer> IColumnSqlProcessor<V> fill(IContainer<V, C> container)
	{
		return container::add;
	}


	static <O extends IFactoryObject, C extends IContainer> IColumnSqlProcessor fill(IContainer<O, C> container, @NullDisallow Factories<O> factories)
	{
		IDatabaseFactory<O, Object> factory = factories.getDatabaseFactory();

		return object -> container.add(factory.fromDatabase(object));
	}


	static <V, S extends ISequence> IColumnSqlProcessor<V> fill(ISequence<V, S> sequence)
	{
		return sequence::add;
	}


	static <O extends IFactoryObject, S extends ISequence> IColumnSqlProcessor fill(ISequence<O, S> sequence, @NullDisallow Factories<O> factories)
	{
		IDatabaseFactory<O, Object> factory = factories.getDatabaseFactory();

		return object -> sequence.add(factory.fromDatabase(object));
	}


	static <V> IColumnSqlProcessor<V> fill(Chain<V> chain)
	{
		return chain::add;
	}


	static <O extends IFactoryObject> IColumnSqlProcessor fill(Chain<O> chain, @NullDisallow Factories<O> factories)
	{
		IDatabaseFactory<O, Object> factory = factories.getDatabaseFactory();

		return object -> chain.add(factory.fromDatabase(object));
	}


	static <V> IColumnSqlProcessor<V> fill(List<V> list)
	{
		return list::add;
	}


	static <O extends IFactoryObject> IColumnSqlProcessor fill(List<O> list, @NullDisallow Factories<O> factories)
	{
		IDatabaseFactory<O, Object> factory = factories.getDatabaseFactory();

		return object -> list.add(factory.fromDatabase(object));
	}


	static <V> IColumnSqlProcessor<V> fill(Set<V> set)
	{
		return set::add;
	}


	static <O extends IFactoryObject> IColumnSqlProcessor fill(Set<O> set, @NullDisallow Factories<O> factories)
	{
		IDatabaseFactory<O, Object> factory = factories.getDatabaseFactory();

		return object -> set.add(factory.fromDatabase(object));
	}

}
