package net.javenture.framework.font;


import net.javenture.framework.html.HtmlPreset;
import net.javenture.framework.html.element.Link;

import java.util.concurrent.atomic.AtomicReference;


/*
	2017/11/11
 */
public interface IFont
{
	//
	String alias();

	HtmlPreset link();


	//
	static HtmlPreset link(String dir, String file)
	{
		// XXX: check if file exist

		String root = Config.root();

		return new HtmlPreset
		(
			new Link()
				.rel(Link.Rel.STYLESHEET)
				.href(root + "/" + dir + "/" + file + ".css")
		);
	}


	//
	final class Config
	{
		final private static String DEFAULT = "/font";
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
