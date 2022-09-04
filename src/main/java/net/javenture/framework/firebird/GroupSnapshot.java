package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullDisallow;

import java.util.ArrayList;


/*
	2019/12/02
 */
class GroupSnapshot<G extends IGroup, E extends IGroup.IEntry>
	implements ISnapshot
{
	//
	final private @NullDisallow IEntriesConnector<E> CONNECTOR;
	final private @NullDisallow ArrayList<E> ENTRIES;


	//
	GroupSnapshot(@NullDisallow IGroup<E, G> group, IEntriesConnector<E> connector)
	{
		ArrayList<E> entries = connector.get();
		int size = entries.size();

		//
		CONNECTOR = connector;
		ENTRIES = new ArrayList<>(size);

		for (E entry : entries)
		{
			E entry0 = group.entry();
			entry0.set(entry);
			ENTRIES.add(entry0);
		}
	}


	//
	@Override
	public boolean change()
	{
		int size = ENTRIES.size();
		ArrayList<E> entries = CONNECTOR.get();

		if (entries.size() != size)
		{
			return true;
		}
		else if (size != 0)
		{
			boolean[] check = new boolean[size];

			for (E entry0 : ENTRIES)
			{
				boolean found = false;

				for (int i = 0; i < size; i++)
				{
					if (!check[i])
					{
						E entry = entries.get(i);

						if (entry0.match(entry))
						{
							found = true;
							check[i] = true;

							break;
						}
					}
				}

				if (!found) return true;
			}
		}

		return false;
	}


	//
	@FunctionalInterface
	interface IEntriesConnector<E>
	{
		@NullDisallow ArrayList<E> get();
	}

}
