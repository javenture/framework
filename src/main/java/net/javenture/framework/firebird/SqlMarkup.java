package net.javenture.framework.firebird;


import net.javenture.framework.util.Chain;
import net.javenture.framework.util.StringFragmenter;

import java.util.Iterator;


/*
	2019/09/03
 */
final public class SqlMarkup
	implements Iterable<Object>
{
	//
	final static String DELIMITER = " "; // !


	//
	final private Chain<Object> CHAIN;


	//
	public SqlMarkup()
	{
		CHAIN = new Chain<>();
	}


	public SqlMarkup(SqlMarkup markup)
	{
		CHAIN = new Chain<>(markup.CHAIN);
	}


	public SqlMarkup(Object object)
	{
		CHAIN = new Chain<>(object);
	}


	public SqlMarkup(Object... objects)
	{
		CHAIN = new Chain<>(objects);
	}


	//
	@Override
	public Iterator<Object> iterator()
	{
		return CHAIN.iterator();
	}


	@Override
	public String toString()
	{
		StringFragmenter fragmenter = new StringFragmenter(DELIMITER);

		for (Object object : CHAIN) fragmenter.add(object);

		return fragmenter.toString();
	}


	public void add(SqlMarkup markup)
	{
		CHAIN.add(markup.CHAIN);
	}


	public void add(Object object)
	{
		CHAIN.add(object);
	}


	public void add(Object... objects)
	{
		CHAIN.add(objects);
	}


	public void add(boolean condition, Object object)
	{
		if (condition) CHAIN.add(object);
	}


	public void add(boolean condition, Object... objects)
	{
		if (condition) CHAIN.add(objects);
	}

}
