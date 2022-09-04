package net.javenture.framework.util;


import java.util.ArrayList;
import java.util.Iterator;


/*
	2018/04/05
 */
final public class Pagination<T>
	implements Iterable<T>
{
	//
	public enum Start
	{
		ZERO,
		ONE
	}


	//
	final private ArrayList<T> LIST;
	final private boolean EXIST;
	final private int FIRST;
	final private int LAST;
	final private int CURRENT;
	final private int FROM;
	final private int TO;


	//
	public Pagination(ArrayList<T> list, int page, int step)
	{
		this(list, page, step, Start.ZERO);
	}


	@SuppressWarnings("AssignmentOrReturnOfFieldWithMutableType")
	public Pagination(ArrayList<T> list, int page, int step, Start start)
	{
		Validator.argument(step == -1 || step > 1, "[step] (", step, ") has illegal value");

		//
		boolean exist;
		int first;
		int last;
		int current;
		int from;
		int to;

		//
		int count = list.size();

		if (count != 0)
		{
			exist = true;

			if (step != -1)
			{
				int total = count / step;

				if (count - total * step > 0) total++;

				switch (start)
				{
					case ZERO:
					{
						first = 0;
						last = total - 1;

						if (page < first) current = first;
						else if (page > last) current = last;
						else current = page;

						from = current * step;
						to = from + step < count ? from + step : count;

						break;
					}

					case ONE:
					{
						first = 1;
						last = total;

						if (page < first) current = first;
						else if (page > last) current = last;
						else current = page;

						from = (current - 1) * step;
						to = from + step < count ? from + step : count;

						break;
					}

					default: throw new UnsupportedOperationException();
				}
			}
			else
			{
				switch (start)
				{
					case ZERO:
					{
						first = 0;
						last = 0;
						current = 0;

						break;
					}

					case ONE:
					{
						first = 1;
						last = 1;
						current = 1;

						break;
					}

					default: throw new UnsupportedOperationException();
				}

				from = 0;
				to = count;
			}
		}
		else
		{
			exist = false;
			first = -1;
			last = -1;
			current = -1;
			from = 0;
			to = 0;
		}

		//
		LIST = list;
		EXIST = exist;
		FIRST = first;
		LAST = last;
		CURRENT = current;
		FROM = from;
		TO = to;
	}


	//
	@Override
	public Iterator<T> iterator()
	{
		return new Iterator<T>()
		{
			//
			private int index = FROM - 1;

			@Override
			public boolean hasNext()
			{
				return index + 1 < TO;
			}

			@Override
			public T next()
			{
				index++;

				if (index >= FROM && index < TO) return LIST.get(index);
				throw new IndexOutOfBoundsException();
			}
		};
	}


	@SuppressWarnings("AssignmentOrReturnOfFieldWithMutableType")
	public ArrayList<T> list()
	{
		return LIST;
	}


	public boolean exist()
	{
		return EXIST;
	}


	public int first()
	{
		return FIRST;
	}


	public int last()
	{
		return LAST;
	}


	public int current()
	{
		return CURRENT;
	}


	public int from()
	{
		return FROM;
	}


	public int to()
	{
		return TO;
	}

}
