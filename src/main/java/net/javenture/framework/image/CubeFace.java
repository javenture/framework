package net.javenture.framework.image;


import java.util.List;


/*
	2021/02/17
 */
public enum CubeFace
{
	//
	FRONT("front"), // x+
	BACK("back"), // x-
	LEFT("left"), // y+
	RIGHT("right"), // y-
	TOP("top"), // z+
	BOTTOM("bottom"); // z-


	//
	final public String ALIAS;


	//
	CubeFace(String alias)
	{
		ALIAS = alias;
	}


	//
	final static List<CubeFace> VALUES = List.of(values());
	final static int SIZE = VALUES.size();

}
