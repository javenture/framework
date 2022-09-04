package net.javenture.framework.util;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;

import java.io.File;


/*
	2020/09/26
 */
public interface ITempFileGenerator
{
	//
	@NullDisallow String parent();
	@NullAllow File get();


	//
	default String name()
	{
		return "" + Uuid.generate();
	}


	default String prefix()
	{
		return "";
	}


	default String suffix()
	{
		return "";
	}

}
