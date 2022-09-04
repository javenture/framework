package net.javenture.framework.firebird;


import net.javenture.framework.annotation.Immutable;
import net.javenture.framework.annotation.NullDisallow;

import java.util.Iterator;


/*
	2018/10/19
 */
@Immutable
final public class DatabaseFields
	implements Iterable<ADatabaseField>
{
	//
	final int SIZE;
	final ADatabaseField[] VALUES;


	//
	public DatabaseFields(@NullDisallow ADatabaseField... values)
	{
		SIZE = values.length;
		VALUES = values.clone();
	}


	//
	@Override
	public Iterator<ADatabaseField> iterator()
	{
		return new Iterator<ADatabaseField>()
		{
			int index = -1;

			@Override
			public boolean hasNext()
			{
				return index + 1 < SIZE;
			}

			@Override
			public ADatabaseField next()
			{
				return VALUES[++index];
			}

			@Override
			public void remove()
			{
				throw new UnsupportedOperationException();
			}
		};
	}


	public int size()
	{
		return SIZE;
	}


	public ADatabaseField get(int index)
	{
		return VALUES[index];
	}


	public ADatabaseField[] array()
	{
		return VALUES.clone();
	}

}
