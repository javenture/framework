package net.javenture.framework.test;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.util.ATempFileGenerator;

import java.io.File;


@Deprecated
final public class TempFileUtil                                                               // ???
{
	//
	final private static TempFileGenerator GENERATOR  = new TempFileGenerator();


	//
	private TempFileUtil()
	{
	}


	//
	public static @NullAllow File create()
	{
		return GENERATOR.get();
	}


	//
	final private static class TempFileGenerator
		extends ATempFileGenerator
	{
		//
		final private static String PARENT = "D:\\Web\\framework\\temp";                              // ???: linux

		//
		@Override
		public String parent()
		{
			return PARENT;
		}
	}

}
