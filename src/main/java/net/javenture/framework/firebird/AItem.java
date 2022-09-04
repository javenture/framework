package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;


/*
	2019/09/05
 */
abstract class AItem<I extends AItem>
	implements IItem<I>
{
	//
	abstract protected <V> V get(@NullDisallow ADatabaseField<V> field);
	abstract protected <V> void set(@NullDisallow ADatabaseField<V> field, @NullAllow V value);

}
