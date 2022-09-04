package net.javenture.framework.util;


import net.javenture.framework.annotation.NullDisallow;

import java.util.Iterator;


/*
	2020/09/27
 */
final public class StringSplitter
	implements Iterable<CharSequence>
{
	//
	final private Chain<CharSequence> CHAIN;


	//
	public StringSplitter(@NullDisallow CharSequence source, char delimiter)
	{
		Validator.notNull(source, "[source]");
		CHAIN = new Chain<>();

		//
		ISafeProcessor processor = (s, from, to) -> CHAIN.add(to - from != 0 ? new StringFragment(s, from, to) : StringFragment.BLANK);
		split(source, delimiter, processor);
	}


	//
	@Override
	public Iterator<CharSequence> iterator()
	{
		return CHAIN.iterator();
	}


	public int size()
	{
		return CHAIN.size();
	}


	public String[] array()
	{
		String[] array = new String[CHAIN.size()];
		int i = 0;

		for (CharSequence sequence : CHAIN) array[i++] = sequence.toString();

		return array;
	}


	//
	public static void split(@NullDisallow CharSequence source, char delimiter, ISafeProcessor processor)
	{
		Validator.notNull(source, "[source]");

		//
		int length = source.length();

		if (length != 0)
		{
			int from = 0;
			int to = 0;

			while (from < length)
			{
				to = TextUtil.index0(source, delimiter, from, length);

				if (to >= 0)
				{
					processor.process(source, from, to);

					from = to + 1;
				}
				else
				{
					processor.process(source, from, length);

					break;
				}
			}
		}
	}


	public static void split(@NullDisallow CharSequence source, char delimiter, IUnsafeProcessor processor)
		throws Exception
	{
		Validator.notNull(source, "[source]");

		//
		int length = source.length();

		if (length != 0)
		{
			int from = 0;
			int to = 0;

			while (from < length)
			{
				to = TextUtil.index0(source, delimiter, from, length);

				if (to >= 0)
				{
					processor.process(source, from, to);

					from = to + 1;
				}
				else
				{
					processor.process(source, from, length);

					break;
				}
			}
		}
	}


	public static void split(@NullDisallow CharSequence source, @NullDisallow CharSequence delimiter, ISafeProcessor processor)
	{
		Validator.notNull(source, "[source]");
		Validator.notNull(delimiter, "[delimiter]");


		throw new UnsupportedOperationException();
	}


	//
	@FunctionalInterface
	public interface ISafeProcessor
	{
		void process(@NullDisallow CharSequence s, int from, int to);
	}


	@FunctionalInterface
	public interface IUnsafeProcessor
	{
		void process(@NullDisallow CharSequence s, int from, int to) throws Exception;
	}

}
