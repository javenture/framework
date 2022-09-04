package net.javenture.framework.css;


import net.javenture.framework.html.HtmlPreset;

import java.util.concurrent.atomic.AtomicReference;


/*
	2017/11/11
 */
final public class CssFile
{
	//
	final private HtmlPreset LINK;


	//
	public CssFile(String dir, String file)
	{
		// XXX


		throw new RuntimeException();
	}


	//
	HtmlPreset link()
	{
		return LINK;
	}


	//
	final public static class Config
	{
		final private static String DEFAULT = "/css";
		final private static AtomicReference<String> ROOT = new AtomicReference<>(DEFAULT);

		//
		public static String root()
		{
			return ROOT.get();
		}

		public static void root(String value)
		{
			ROOT.set(value);
		}
	}

}
