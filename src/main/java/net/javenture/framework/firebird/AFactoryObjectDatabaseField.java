package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.factory.FactoryObjectUtil;
import net.javenture.framework.factory.IFactoryObject;
import net.javenture.framework.log.LogAttachment;
import net.javenture.framework.util.Validator;

import java.util.Objects;


/*
	2020/01/05
 */
abstract public class AFactoryObjectDatabaseField<V extends IFactoryObject> // ! abstract, <V extends IFactoryObject>
	extends ADatabaseField<V>
{
	//
	final private @NullAllow V INIT;
	final private @NullDisallow Factories<V> FACTORIES;
	final private @NullDisallow IValidator<V> VALIDATOR_NULLABLE;
	final private @NullDisallow IValidator<V> VALIDATOR_STRICT;
	final private @NullDisallow IValidator<V> VALIDATOR_CUSTOM;


	//
	protected AFactoryObjectDatabaseField
	(
		@NullDisallow String table,
		int number,
		@NullDisallow String name,
		@NullAllow V init,
		@NullDisallow Factories<V> factories,
		Option... options
	)
	{
		this(table, number, name, init, factories, null, options);
	}


	@SuppressWarnings("unchecked")
	protected AFactoryObjectDatabaseField
	(
		@NullDisallow String table,
		int number,
		@NullDisallow String name,
		@NullAllow V init,
		@NullDisallow Factories<V> factories,
		@NullAllow IValidator<V> validator,
		Option... options
	)
	{
		super(table, number, name, options);

		Validator.notNull(factories, "[factories]");

		//
		INIT = factories.getCopyFactory()
			.copy(init);

		FACTORIES = factories;

		VALIDATOR_NULLABLE =
			NULLABLE
			? IValidator.BLANK
			: value -> { if (value == null) throw new Exception("[FIELD] (" + table + "." + name + ") disallow null value"); };

		VALIDATOR_STRICT =
			STRICT
			? value -> { if (FactoryObjectUtil.equal(INIT, value)) throw new Exception("[FIELD] (" + table + "." + name + ") is strict"); }
			: IValidator.BLANK;

		VALIDATOR_CUSTOM = validator != null
			? validator
			: IValidator.BLANK;
	}


	//
	@Override
	final public DatabaseType type()
	{
		return FACTORIES.getDatabaseFactory()
			.getDatabaseType();
	}


	@Override
	final public @NullAllow V init()
	{
		return FACTORIES.getCopyFactory()
			.copy(INIT);
	}


	@Override
	final public boolean defined(@NullAllow V value)
	{
		return !Objects.equals(INIT, value);
	}


	@Override
	final public @NullAllow V prepare(@NullAllow V value)
	{
		return value;
	}


	@Override
	final public void validate(@NullAllow V value)
		throws Exception
	{
		VALIDATOR_NULLABLE.validate(value);
		VALIDATOR_STRICT.validate(value);
		VALIDATOR_CUSTOM.validate(value);
	}


	@Override
	final public boolean equal(@NullAllow V value0, @NullAllow V value1)
	{
		return FactoryObjectUtil.equal(value0, value1);
	}


	@Override
	public boolean instance(@NullAllow Object value)
	{
		return FACTORIES.getInstanceFactory()
			.validate(value);
	}


	@Override
	final public Factories<V> factories()
	{
		return FACTORIES;
	}


	@Override
	final public LogAttachment log()
	{
		return
			new LogAttachment()
			.add(TABLE, "TABLE")
			.add(NUMBER, "NUMBER")
			.add(NAME, "NAME")
			.add(KEY, "KEY")
			.add(NULLABLE, "NULLABLE")
			.add(STRICT, "STRICT")
			.add(INIT, "INIT");
	}

}
