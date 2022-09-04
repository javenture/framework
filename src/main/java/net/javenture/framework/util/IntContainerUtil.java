package net.javenture.framework.util;


import net.javenture.framework.annotation.NullDisallow;


/*
	2019/09/27
 */
final public class IntContainerUtil
{
	//
	private IntContainerUtil()
	{
	}


	//
	public static String toString(IntContainer container, char delimiter)
	{
		StringFragmenter fragmenter = new StringFragmenter(container.size(), delimiter);
		fragmenter.add(container);

		return fragmenter.toString();
	}


	public static String toString(IntContainer container, CharSequence delimiter)
	{
		StringFragmenter fragmenter = new StringFragmenter(container.size(), delimiter);
		fragmenter.add(container);

		return fragmenter.toString();
	}


	public static IntContainer fromString(@NullDisallow CharSequence source, char delimiter)
		throws Exception
	{
		IntContainer result = new IntContainer();
		fromString(source, delimiter, result);

		return result;
	}


	public static IntContainer fromString(@NullDisallow CharSequence source, char delimiter, int init)
	{
		IntContainer result = new IntContainer();
		fromString(source, delimiter, result, init);

		return result;
	}


	public static void fromString(@NullDisallow CharSequence source, char delimiter, @NullDisallow IntContainer destination)
		throws Exception
	{
		Validator.notNull(source, "[source]");
		Validator.notNull(destination, "[destination]");

		StringSplitter.IUnsafeProcessor processor = (s, from, to) ->
		{
			Integer value = IntUtil.primitive(s, from, to);

			if (value != null) destination.add(value);
			else throw new Exception("parse failed: " + s.subSequence(from, to));
		};

		StringSplitter.split(source, delimiter, processor);
	}


	public static void fromString(@NullDisallow CharSequence source, char delimiter, @NullDisallow IntContainer destination, int init)
	{
		Validator.notNull(source, "[source]");
		Validator.notNull(destination, "[destination]");

		StringSplitter.ISafeProcessor processor = (s, from, to) ->
		{
			Integer value = IntUtil.primitive(s, from, to);
			destination.add(value != null ? value : init);
		};

		StringSplitter.split(source, delimiter, processor);
	}

}
