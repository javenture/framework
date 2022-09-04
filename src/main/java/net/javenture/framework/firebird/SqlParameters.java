package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.util.Chain;
import net.javenture.framework.util.Validator;

import java.util.Iterator;


/*
	2019/12/01
 */
final public class SqlParameters
	implements Iterable<Object[]>
{
	//
	final private Chain<Object[]> CHAIN;

	private int length; // -1: undefined


	//
	public SqlParameters()
	{
		CHAIN = new Chain<>();
		length = -1;
	}


	public SqlParameters(Object... values) // ! not Object
	{
		this();

		add(values);
	}


	//
	public SqlParameters add(Object... values) // ! not Object
	{
		Validator.argument(values.length != 0, "[values] is empty");

		if (this.length == -1) this.length = values.length;
		else Validator.argument(values.length == this.length, "[values.length] (", values.length, ") != [this.length] (", this.length, ")");

		CHAIN.add(values);

		return this;
	}


	public boolean exists()
	{
		return CHAIN.exists();
	}


	public Object[] first()
	{
		return CHAIN.first();
	}


	@Override
	public Iterator<Object[]> iterator()
	{
		return CHAIN.iterator();
	}


	//
	static @NullAllow SqlParameters prepare(Object... values)
	{
		return values.length != 0 ? new SqlParameters(values) : null;
	}

}
