package net.javenture.framework.quasar;


import net.javenture.framework.model.IntModel;
import net.javenture.framework.script.AScriptFunction;


/*
	2018/07/30
 */
final public class QuasarScroll
{
	//
	final public static IntModel MODEL = new IntModel( "$SCROLL", false, false, false, 0);
	final public static GetVueFunction FUNCTION_GET = new GetVueFunction();
	final public static SetVueFunction FUNCTION_SET = new SetVueFunction();


	//
	final public static class GetVueFunction                                                    // XXX: VueReference ???              // XXX: -> QuasarUtil
		extends AScriptFunction
	{
		@Override
		protected String _name()
		{
			return "Quasar.utils.scroll.getScrollPosition";
		}

		@Override
		protected String _content()
		{
			return null;
		}

		@Override
		public Invoke invoke()
		{
			return super.invoke()
				.argument("window", false);
		}
	}


	final public static class SetVueFunction
		extends AScriptFunction
	{
		@Override
		protected String _name()
		{
			return "Quasar.utils.scroll.setScrollPosition";
		}

		@Override
		protected String _content()
		{
			return null;
		}

		public Invoke invoke(int value)
		{
			return super.invoke()
				.argument("window", false)
				.argument(value);
		}
	}

}
