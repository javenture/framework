package net.javenture.framework.html;


/*
	2017/11/10
 */
final public class HtmlId
{
	final static HtmlAttribute.Template TEMPLATE = new HtmlAttribute.Template("id");


	//
	final public String VALUE;

	final HtmlAttribute ATTRIBUTE;


	//
	public HtmlId(String value)
	{
		// XXX: check chars (a...z | A...Z | 0...9 | _) ?


		VALUE = value;
		ATTRIBUTE = new HtmlAttribute(TEMPLATE, value);
	}


	//
	@Override
	public String toString()
	{
		return VALUE;
	}

}
