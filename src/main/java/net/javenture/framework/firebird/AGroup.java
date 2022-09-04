package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;


/*
	2019/09/05
 */
abstract class AGroup<E extends AGroup.AEntry, G extends AGroup>
	implements IGroup<E, G>
{
	//
	abstract static class AEntry<E extends AEntry>
		implements IGroup.IEntry<E>
	{
		abstract protected <V> V get(@NullDisallow ADatabaseField<V> field);
		abstract protected <V> void set(@NullDisallow ADatabaseField<V> field, @NullAllow V value);
	}

}
