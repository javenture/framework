package net.javenture.framework.util;


/*
	2020/10/19
 */
public enum Platform
{
	//
	UNKNOWN(),
	WINDOWS("windows"),
	LINUX(),                                                                         // XXX
	MACOS();                                                                         // XXX


	//
	final private String[] ALIASES;


	//
	Platform()
	{
		ALIASES = StringArrayUtil.BLANK;
	}


	Platform(String... aliases)
	{
		ALIASES = aliases;
	}


	//
	public static Platform detect()
	{
		String name = System.getProperty("os.name").toLowerCase();

		for (Platform value : Platform.values())
		{
			if (value != UNKNOWN)
			{
				for (String alias : value.ALIASES)
				{
					if (name.contains(alias)) return value;
				}
			}
		}

		return UNKNOWN;
	}

}
