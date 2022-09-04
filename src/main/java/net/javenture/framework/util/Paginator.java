package net.javenture.framework.util;


import java.util.ArrayList;


@Deprecated
final public class Paginator
{
	public enum Type { FIRST, LAST, PREVIOUS, NEXT, COMMON, SPACER }


	//
	final public int PAGE_TOTAL;
	final public int PAGE_CURRENT;
	final public int INDEX_FROM;
	final public int INDEX_TO;
	final public ArrayList<Entry> ENTRIES;


	//
	public Paginator(int count, int step, int page)
	{
		int page_total = count / step;

		if (count - page_total * step > 0) page_total++;

		//
		int page_current;

		if (page_total != 0)
		{
			if (page < 1) page_current = 1;
			else if (page > page_total) page_current = page_total;
			else page_current = page;
		}
		else
		{
			page_current = 1;
		}

		//
		int index_from = (page_current - 1) * step;
		int index_to = index_from + step < count ? index_from + step : count;

		//
		ArrayList<Entry> entries = new ArrayList<>();

		if (page_total != 0)
		{
			int page_first = 1;
			int page_last = page_total;

			boolean link_first = false;
			boolean link_last = false;
			boolean link_previous = false;
			boolean link_next = false;
			boolean spacer_left = false;
			boolean spacer_right = false;
			int offset_backward = page_current;
			int offset_forward = page_current;

			//
			if (page_total > 1 && page_current > page_first) link_previous = true;

			if (page_current > page_first)
			{
				link_first = true;

				if (page_current >= page_first + 4)
				{
					offset_backward--;
					spacer_left = true;
				}
				else if (page_current == page_first + 3)
				{
					offset_backward -= 2;
				}
				else if (page_current == page_first + 2)
				{
					offset_backward--;
				}
			}

			if (page_current < page_last)
			{
				link_last = true;

				if (page_current <= page_last - 4)
				{
					offset_forward++;
					spacer_right = true;
				}
				else if (page_current == page_last - 3)
				{
					offset_forward += 2;
				}
				else if (page_current == page_last - 2)
				{
					offset_forward++;
				}
			}

			if (page_total > 1 && page_current < page_last) link_next = true;

			//
			if (link_previous) entries.add(new Entry(Type.PREVIOUS, page_current - 1));

			if (link_first) entries.add(new Entry(Type.FIRST, page_first));

			if (spacer_left) entries.add(new Entry(Type.SPACER));

			for (int i = offset_backward; i <= offset_forward; i++) entries.add(new Entry(Type.COMMON, i));

			if (spacer_right) entries.add(new Entry(Type.SPACER));

			if (link_last) entries.add(new Entry(Type.LAST, page_last));

			if (link_next) entries.add(new Entry(Type.NEXT, page_current + 1));
		}

		//
		PAGE_TOTAL = page_total;
		PAGE_CURRENT = page_current;
		INDEX_FROM = index_from;
		INDEX_TO = index_to;
		ENTRIES = entries;
	}


	//
	public static class Entry
	{
		final public Type TYPE;
		final public int NUMBER;

		private Entry(Type type)
		{
			TYPE = type;
			NUMBER = 0;
		}

		private Entry(Type type, int number)
		{
			TYPE = type;
			NUMBER = number;
		}
	}

}
