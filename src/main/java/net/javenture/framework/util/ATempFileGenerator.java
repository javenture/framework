package net.javenture.framework.util;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.log.Log;

import java.io.File;
import java.io.IOException;


/*
	2020/09/26
 */
abstract public class ATempFileGenerator
	implements ITempFileGenerator
{
	//
	final private static Log LOG = Log.instance(ATempFileGenerator.class);


	//
	@Override
	final public @NullAllow File get()
	{
		try
		{
			while (true)
			{
				File result = new File(parent(), "" + prefix() + name() + suffix());

				if (result.createNewFile()) return result;
			}
		}
		catch (IOException e)
		{
			LOG.error(e);
		}

		return null;
	}

}
