package net.javenture.framework.util;


/*
	2020/05/15
 */
@FunctionalInterface
public interface IByteSequence
{
	/**
	 * @return byte | -1 (end of sequence)
	 */
	int get();

}
