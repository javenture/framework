package net.javenture.framework.quasar;


/*
	2018/06/07
 */
final public class TouchSwipeQuasarDirective
{
	//
	public enum Modifier
	{
		//
		HORIZONTAL("horizontal"),
		VERTICAL("vertical"),
		UP("up"),
		RIGHT("right"),
		DOWN("down"),
		LEFT("left"),
		NO_MOUSE("noMouse");

		//
		final private String VALUE;

		//
		Modifier(String value)
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


	//








}
