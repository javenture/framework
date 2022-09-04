package net.javenture.framework.util;


/*
	2020/10/18
 */
@FunctionalInterface
public interface IPlatformObject<T>
{
	T value(Platform platform);

}
