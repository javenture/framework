package net.javenture.framework.hash;


import net.javenture.framework.annotation.NullDisallow;


/*
	2021/06/02
 */
public interface IHash
{
	void update(byte b);
	void update(@NullDisallow byte... array);
	void update(@NullDisallow byte[] array, int offset, int length);
	String value();

}
