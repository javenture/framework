package net.javenture.framework.firebird;


import net.javenture.framework.annotation.Immutable;
import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.log.ILog;
import net.javenture.framework.util.ObjectArrayUtil;
import net.javenture.framework.util.Validator;
import net.javenture.framework.model.AModel;


/*
	2020/01/05
 */
@Immutable
abstract public class ADatabaseField<V>
	implements ILog
{
	//
	public enum Option
	{
		KEY,
		NULLABLE,
		STRICT

		// XXX: PRIMARY ?         (Constraints Primary Key | Constrainst Foreign Key)
	}


	//
	final @NullDisallow String TABLE;
	final int NUMBER;
	final @NullDisallow String NAME;
	final boolean KEY;
	final boolean NULLABLE;
	final boolean STRICT;
	final @NullDisallow String ALIAS;


	//
	protected ADatabaseField
	(
		@NullDisallow String table,
		int number,
		@NullDisallow String name,
		Option... options
	)
	{
		Validator.notNull(table, "[table]");
		Validator.argument(!table.isEmpty(), "[table] is empty");
		Validator.argument(number >= 0, "[number] (", number, ") < 0");
		Validator.notNull(name, "[name]");
		Validator.argument(!name.isEmpty(), "[name] is empty");

		// XXX: check name chars (AZ01_$), upper case only
		// XXX: if (KEY) => !NULLABLE

		//
		TABLE = table;
		NUMBER = number;
		NAME = name;
		KEY = ObjectArrayUtil.contains(options, Option.KEY);
		NULLABLE = ObjectArrayUtil.contains(options, Option.NULLABLE);
		STRICT = ObjectArrayUtil.contains(options, Option.STRICT);
		ALIAS = table + "." + name;
	}


	//
	abstract public DatabaseType type();
	abstract public @NullAllow V init(); // default value
	abstract public boolean defined(@NullAllow V value);
	abstract public @NullAllow V prepare(@NullAllow V value);
	abstract public void validate(@NullAllow V value) throws Exception;
	abstract public boolean equal(@NullAllow V value0, @NullAllow V value1);
	abstract public boolean instance(@NullAllow Object value);
	abstract public Factories<V> factories();
	abstract public AModel<V> model();


	//
	final public String table()
	{
		return TABLE;
	}


	final public int number()
	{
		return NUMBER;
	}


	final public String name()
	{
		return NAME;
	}


	final public boolean key()
	{
		return KEY;
	}


	final public boolean nullable()
	{
		return NULLABLE;
	}


	final public boolean strict()
	{
		return STRICT;
	}


	final public String alias()
	{
		return ALIAS;
	}


	final public SqlSenders.Entry sender()
	{
		return SqlSenders.entry(NUMBER, this);
	}


	final public SqlSenders.Entry sender(int number)
	{
		return SqlSenders.entry(number, this);
	}


	final public SqlSenders.Entry sender(String content)
	{
		return SqlSenders.entry(NUMBER, this, content);
	}


	final public SqlSenders.Entry sender(int number, String content)
	{
		return SqlSenders.entry(number, this, content);
	}


	final public SqlReceivers.Entry receiver()
	{
		return SqlReceivers.entry(NUMBER, this);
	}


	final public SqlReceivers.Entry receiver(int number)
	{
		return SqlReceivers.entry(number, this);
	}


	final public SqlReceivers.Entry receiver(String content)
	{
		return SqlReceivers.entry(NUMBER, this, content);
	}


	final public SqlReceivers.Entry receiver(int number, boolean raw)
	{
		return SqlReceivers.entry(number, this, raw);
	}


	final public SqlReceivers.Entry receiver(int number, String content)
	{
		return SqlReceivers.entry(number, this, content);
	}


	final public SqlReceivers.Entry receiver(int number, boolean raw, String content)
	{
		return SqlReceivers.entry(number, this, raw, content);
	}


	//
	@Override
	final public String toString()
	{
		return ALIAS;
	}


	@Override
	final public int hashCode()
	{
		return super.hashCode(); // implementation disallowed
	}


	@Override
	final public boolean equals(Object object)
	{
		return super.equals(object); // implementation disallowed
	}

}
