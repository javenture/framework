package net.javenture.framework.util;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.exception.NotImplementedException;


/*
	2020/08/26
 */
final public class StringFragment
	implements CharSequence
{
	//
	final private CharSequence SOURCE;
	final private int FROM;
	final private int TO;
	final private int LENGTH;


	//
	public StringFragment(@NullDisallow CharSequence source, int from, int to)
	{
		SOURCE = source;
		FROM = from;
		TO = to;
		LENGTH = to - from;
	}


	//
	@Override
	public int length()
	{
		return LENGTH;
	}


	@Override
	public char charAt(int index)
	{
		Validator.argument(index >= 0 && index < LENGTH, "[index] (", index, ") is illegal");

		return SOURCE.charAt(FROM + index);
	}


	@Override
	public CharSequence subSequence(int start, int end)
	{
		Validator.argument(start >= 0, "");                                        // XXX: ""
		Validator.argument(start <= end, "");
		Validator.argument(end <= LENGTH, "");

		return SOURCE.subSequence(FROM + start, FROM + end);
	}


	@Override
	public String toString()
	{
		return LENGTH != 0
			? SOURCE.subSequence(FROM, TO).toString()
			: "";
	}


	//
	final public static StringFragment BLANK = new StringFragment("", 0, 0);

}
