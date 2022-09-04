package net.javenture.framework.script;


import net.javenture.framework.html.HtmlPreset;
import net.javenture.framework.html.element.Script;


/*
	2018/12/20
 */
final public class ScriptFile
{
	//
	final public String DIRECTORY;
	final public String FILE;


	//
	public ScriptFile(String directory, String file)
	{
		DIRECTORY = directory;
		FILE = file;
	}


	//
	public HtmlPreset preset()
	{
		return new HtmlPreset
		(
			new Script()
				.src(DIRECTORY + "/" + FILE)
		);
	}

}
