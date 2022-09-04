package net.javenture.framework.identifier;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.util.IntSequenceUtil;
import net.javenture.framework.util.IntUtil;
import net.javenture.framework.util.StringFragmenter;
import net.javenture.framework.util.StringSplitter;
import net.javenture.framework.util.Validator;


/*
	2019/12/13
 */
final public class IntIdentifierSequenceUtil
{
	//
	private IntIdentifierSequenceUtil()
	{
	}


	//
	public static String toString(IntIdentifierSequence sequence, char delimiter)
	{
		StringFragmenter fragmenter = new StringFragmenter(sequence.size(), delimiter);
		fragmenter.add(sequence);

		return fragmenter.toString();
	}


	public static String toString(IntIdentifierSequence sequence, CharSequence delimiter)
	{
		StringFragmenter fragmenter = new StringFragmenter(sequence.size(), delimiter);
		fragmenter.add(sequence);

		return fragmenter.toString();
	}


	public static IntIdentifierSequence fromString(@NullDisallow CharSequence source, char delimiter)
		throws Exception
	{
		IntIdentifierSequence result = new IntIdentifierSequence();
		fromString(source, delimiter, result);

		return result;
	}


	public static IntIdentifierSequence fromString(@NullDisallow CharSequence source, char delimiter, IntIdentifier init)
	{
		IntIdentifierSequence result = new IntIdentifierSequence();
		fromString(source, delimiter, result, init);

		return result;
	}


	public static void fromString(@NullDisallow CharSequence source, char delimiter, @NullDisallow IntIdentifierSequence destination)
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


	public static void fromString(@NullDisallow CharSequence source, char delimiter, @NullDisallow IntIdentifierSequence destination, IntIdentifier init)
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


	public static IntIdentifierSequence intersection(IntIdentifierSequence sequence0, IntIdentifierSequence sequence1)
	{
		return IntIdentifierSequence.wrap(IntSequenceUtil.intersection(sequence0.SEQUENCE, sequence1.SEQUENCE));
	}


	public static IntIdentifierSequence union(IntIdentifierSequence sequence0, IntIdentifierSequence sequence1)
	{
		return IntIdentifierSequence.wrap(IntSequenceUtil.union(sequence0.SEQUENCE, sequence1.SEQUENCE));
	}


	//
	final public static class Conversion
	{
		//
		final public boolean REQUIRED;
		final public IntIdentifierSequence DELETION;
		final public IntIdentifierSequence ADDITION;

		//
		public Conversion(IntIdentifierSequence source, IntIdentifierSequence destination)
		{
			IntSequenceUtil.Conversion conversion = new IntSequenceUtil.Conversion(source.SEQUENCE, destination.SEQUENCE);

			REQUIRED = conversion.REQUIRED;
			DELETION = IntIdentifierSequence.wrap(conversion.DELETION);
			ADDITION = IntIdentifierSequence.wrap(conversion.ADDITION);
		}

		//
		public boolean required()
		{
			return REQUIRED;
		}

		public IntIdentifierSequence deletion()
		{
			return DELETION;
		}

		public IntIdentifierSequence addition()
		{
			return ADDITION;
		}
	}

}
