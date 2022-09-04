package net.javenture.framework.util;


import net.javenture.framework.annotation.NullDisallow;


/*
	2019/07/31
 */
@FunctionalInterface
public interface IEnumAlias<E extends Enum> // ! <E extends Enum>
{
	@NullDisallow String get(@NullDisallow E value);

}
