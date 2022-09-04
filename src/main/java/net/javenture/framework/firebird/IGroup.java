package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.log.Log;

import java.util.Comparator;


/*
	2019/12/13
 */
public interface IGroup<E extends IGroup.IEntry, G extends IGroup>
	extends IDatabaseEntity<G>, Iterable<E>
{
	//
	@Override IConfig<E> config();
	@Override boolean defined();
	@Override void clear();
	@Override ISnapshot snapshot();

	@NullDisallow E entry();
	int size();
	boolean contains(@NullDisallow E entry);
	int index(@NullDisallow E entry);
	@NullDisallow E get(int index);
	void add(@NullDisallow E entry);
	boolean remove(@NullDisallow E entry);
	void sort();


	//
	interface IConfig<E extends IEntry>
		extends IDatabaseEntity.IConfig
	{
		@Override Log log();
		@Override DatabaseTable table();
		@Override boolean journal();
		@Override boolean cache();

		@NullAllow Comparator<E> comparator();
	}


	interface IEntry<E extends IEntry>
	{
		void set(@NullDisallow E entry);
		boolean match(@NullDisallow E entry);
	}

}
