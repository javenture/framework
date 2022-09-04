package net.javenture.framework.vue;


/*
	2018/06/27
 */
public enum SystemVueModifier
{
	// https://vuejs.org/v2/guide/events.html#System-Modifier-Keys
	CTRL("ctrl"),
	ALT("alt"),
	SHIFT("shift"),
	META("meta");


	//
	final private String VALUE;


	//
	SystemVueModifier(String value)
	{
		VALUE = "." + value;
	}


	//
	@Override
	public String toString()
	{
		return VALUE;
	}

}
