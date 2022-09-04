package net.javenture.framework.util;


/*
    2021/05/28
 */
public enum Primitives
{
	//
	BOOLEAN(1),
	BYTE(1),
	CHAR(Character.SIZE / Byte.SIZE),
	SHORT(Short.SIZE / Byte.SIZE),
	INT(Integer.SIZE / Byte.SIZE),
	LONG(Long.SIZE / Byte.SIZE),
	FLOAT(Float.SIZE / Byte.SIZE),
	DOUBLE(Double.SIZE / Byte.SIZE);


	//
	final public int DIMENSION;


	//
	Primitives(int dimension)
	{
		DIMENSION = dimension;
	}

}
