package net.javenture.framework.html.element;


import net.javenture.framework.html.AHtmlContent;


/*
	2019/06/19
 */
final public class Tr
	extends AHtmlContent<Tr>
{
	//
	final public static Config CONFIG = new Config(Type.PAIR, "tr");


	//
	public Tr()
	{
	}


	public Tr(Table parent)
	{
		super(parent);
	}


	public Tr(Thead parent)
	{
		super(parent);
	}


	public Tr(Tbody parent)
	{
		super(parent);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}

}
