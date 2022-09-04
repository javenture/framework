package net.javenture.framework.util;


import net.javenture.framework.annotation.NullDisallow;


/*
	2019/12/13
 */
final public class IntSequenceUtil
{
	//
	private IntSequenceUtil()
	{
	}


	//
	public static String toString(IntSequence sequence, char delimiter)
	{
		StringFragmenter fragmenter = new StringFragmenter(sequence.size(), delimiter);
		fragmenter.add(sequence);

		return fragmenter.toString();
	}


	public static String toString(IntSequence sequence, CharSequence delimiter)
	{
		StringFragmenter fragmenter = new StringFragmenter(sequence.size(), delimiter);
		fragmenter.add(sequence);

		return fragmenter.toString();
	}


	public static IntSequence fromString(@NullDisallow CharSequence source, char delimiter)
		throws Exception
	{
		IntSequence result = new IntSequence();
		fromString(source, delimiter, result);

		return result;
	}


	public static IntSequence fromString(@NullDisallow CharSequence source, char delimiter, int init)
	{
		IntSequence result = new IntSequence();
		fromString(source, delimiter, result, init);

		return result;
	}


	public static void fromString(@NullDisallow CharSequence source, char delimiter, @NullDisallow IntSequence destination)
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


	public static void fromString(@NullDisallow CharSequence source, char delimiter, @NullDisallow IntSequence destination, int init)
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


	public static IntSequence intersection(IntSequence sequence0, IntSequence sequence1)
	{
		IntSequence result = new IntSequence(Math.min(sequence0.size, sequence1.size));

		//
		int from0 = 0;
		int to0 = sequence0.size;

		int from1 = 0;
		int to1 = sequence1.size;

		//
		while (from0 < to0 && from1 < to1)
		{
			int value0 = sequence0.array[from0];
			int value1 = sequence1.array[from1];

			if (value0 < value1)
			{
				int first0 = sequence0.index0(value1, from0, to0);
				int next0 = first0 + 1;

				if (first0 >= 0)
				{
					result.add(value1);
					from0 = next0;
				}
				else
				{
					from0 = -next0;
				}

				from1++;
			}
			else if (value0 > value1)
			{
				int first1 = sequence1.index0(value0, from1, to1);
				int next1 = first1 + 1;

				if (first1 >= 0)
				{
					result.add(value0);
					from1 = next1;
				}
				else
				{
					from1 = -next1;
				}

				from0++;
			}
			else
			{
				result.add(value0);
				from0++;
				from1++;
			}
		}

		return result;
	}


	public static IntSequence union(IntSequence sequence0, IntSequence sequence1)
	{
		IntSequence result = new IntSequence(sequence0.size + sequence1.size);

		//
		int from0 = 0;
		int to0 = sequence0.size;

		int from1 = 0;
		int to1 = sequence1.size;

		//
		while (true)
		{
			if (from0 < to0 && from1 < to1)
			{
				int value0 = sequence0.array[from0];
				int value1 = sequence1.array[from1];

				if (value0 < value1)
				{
					result.add(value0);
					from0++;
				}
				else if (value0 > value1)
				{
					result.add(value1);
					from1++;
				}
				else
				{
					result.add(value0);
					from0++;
					from1++;
				}
			}
			else if (from0 < to0)
			{
				for (int i = from0; i < to0; i++) result.add(sequence0.array[i]);

				break;
			}
			else if (from1 < to1)
			{
				for (int i = from1; i < to1; i++) result.add(sequence1.array[i]);

				break;
			}
			else
			{
				break;
			}
		}

		return result;
	}


	//
	/*
	 *  [1, 2, 3, 5, 7] -> [2, 3, 4, 5]
	 *
	 *  deletion: [1, 7]
	 *  addition: [4]
	 */
	final public static class Conversion
	{
		//
		final public boolean REQUIRED;
		final public IntSequence DELETION;
		final public IntSequence ADDITION;

		//
		public Conversion(IntSequence source, IntSequence destination)
		{
			IntSequence deletion = new IntSequence(source.size);
			IntSequence addition = new IntSequence(destination.size);

			// deletion
			{
				int from = 0;
				int to = destination.size;

				for (int i = 0; i < source.size; i++)
				{
					int value = source.array[i];
					int index = destination.index0(value, from, to);

					if (index >= 0)
					{
						from = index + 1;
					}
					else
					{
						deletion.add(value);
						from = -(index + 1);
					}

					if (from == to)
					{
						deletion.add(source.array, i + 1, source.size);

						break;
					}
				}
			}

			// addition
			{
				int from = 0;
				int to = source.size;

				for (int i = 0; i < destination.size; i++)
				{
					int value = destination.array[i];
					int index = source.index0(value, from, to);

					if (index >= 0)
					{
						from = index + 1;
					}
					else
					{
						addition.add(value);
						from = -(index + 1);
					}

					if (from == to)
					{
						addition.add(destination.array, i + 1, destination.size);

						break;
					}
				}
			}

			//
			REQUIRED = deletion.exists() || addition.exists();
			DELETION = deletion;
			ADDITION = addition;
		}

		//
		public boolean required()
		{
			return REQUIRED;
		}

		public IntSequence deletion()
		{
			return DELETION;
		}

		public IntSequence addition()
		{
			return ADDITION;
		}
	}

}
