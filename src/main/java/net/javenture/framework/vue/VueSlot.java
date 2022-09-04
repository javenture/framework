package net.javenture.framework.vue;


import net.javenture.framework.html.HtmlAttribute;


/*
	2019/05/16
 */
final public class VueSlot
{
	//
	final private String VALUE;
	final private HtmlAttribute.Template ATTRIBUTE;


	//
	public VueSlot(String name)
	{
		VALUE = "v-slot:" + name;
		ATTRIBUTE = new HtmlAttribute.Template(VALUE);
	}


	//
	@Override
	public String toString()
	{
		return VALUE;
	}


	public String value()
	{
		return VALUE;
	}


	public HtmlAttribute.Template attribute()
	{
		return ATTRIBUTE;
	}

}
