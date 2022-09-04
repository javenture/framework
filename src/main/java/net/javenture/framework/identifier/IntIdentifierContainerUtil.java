package net.javenture.framework.identifier;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.util.IntUtil;
import net.javenture.framework.util.StringFragmenter;
import net.javenture.framework.util.StringSplitter;
import net.javenture.framework.util.Validator;


/*
	2019/09/27
 */
final public class IntIdentifierContainerUtil
{
	//
	private IntIdentifierContainerUtil()
	{
	}


	//
	public static String toString(IntIdentifierContainer container, char delimiter)
	{
		StringFragmenter fragmenter = new StringFragmenter(container.size(), delimiter);
		fragmenter.add(container);

		return fragmenter.toString();
	}


	public static String toString(IntIdentifierContainer container, CharSequence delimiter)
	{
		StringFragmenter fragmenter = new StringFragmenter(container.size(), delimiter);
		fragmenter.add(container);

		return fragmenter.toString();
	}


	public static IntIdentifierContainer fromString(@NullDisallow CharSequence source, char delimiter)
		throws Exception
	{
		IntIdentifierContainer result = new IntIdentifierContainer();
		fromString(source, delimiter, result);

		return result;
	}


	public static IntIdentifierContainer fromString(@NullDisallow CharSequence source, char delimiter, IntIdentifier init)
	{
		IntIdentifierContainer result = new IntIdentifierContainer();
		fromString(source, delimiter, result, init);

		return result;
	}


	public static void fromString(@NullDisallow CharSequence source, char delimiter, @NullDisallow IntIdentifierContainer destination)
		throws Exception
	{
		Validator.notNull(source, "[source]");
		Validator.notNull(destination, "[destination]");

		StringSplitter.IUnsafeProcessor processor = (sequence, from, to) ->
		{
			Integer value = IntUtil.primitive(sequence, from, to);

			if (value != null) destination.add(value);
			else throw new Exception("parse failed: " + sequence.subSequence(from, to));
		};

		StringSplitter.split(source, delimiter, processor);
	}


	public static void fromString(@NullDisallow CharSequence source, char delimiter, @NullDisallow IntIdentifierContainer destination, IntIdentifier init)
	{
		Validator.notNull(source, "[source]");
		Validator.notNull(destination, "[destination]");
		int value0 = init.VALUE;

		StringSplitter.ISafeProcessor processor = (sequence, from, to) ->
		{
			Integer value = IntUtil.primitive(sequence, from, to);
			destination.add(value != null ? value : value0);
		};

		StringSplitter.split(source, delimiter, processor);
	}

}
