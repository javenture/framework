package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.log.LogAttachment;
import net.javenture.framework.util.ShortUtil;
import net.javenture.framework.model.ShortModel;


/*
	2020/01/05
 */
final public class ShortDatabaseField
	extends ADatabaseField<Short>
{
	//
	final private @NullAllow Short INIT;
	final private ShortModel MODEL;
	final private @NullDisallow IValidator<Short> VALIDATOR_NULLABLE;
	final private @NullDisallow IValidator<Short> VALIDATOR_STRICT;
	final private @NullDisallow IValidator<Short> VALIDATOR_CUSTOM;


	//
	public ShortDatabaseField
	(
		@NullDisallow String table,
		int number,
		@NullDisallow String name,
		@NullAllow Short init,
		Option... options
	)
	{
		this(table, number, name, init, null, options);
	}


	@SuppressWarnings("unchecked")
	public ShortDatabaseField
	(
		@NullDisallow String table,
		int number,
		@NullDisallow String name,
		@NullAllow Short init,
		@NullAllow IValidator<Short> validator,
		Option... options
	)
	{
		super(table, number, name, options);

		//
		INIT = init;
		MODEL = new ShortModel(this, true, false);

		VALIDATOR_NULLABLE =
			NULLABLE
			? IValidator.BLANK
			: value -> { if (value == null) throw new Exception("[FIELD] (" + table + "." + name + ") disallow null value"); };

		VALIDATOR_STRICT =
			STRICT
			? value -> { if (ShortUtil.equal(INIT, value)) throw new Exception("[FIELD] (" + table + "." + name + ") is strict"); }
			: IValidator.BLANK;

		VALIDATOR_CUSTOM = validator != null ? validator : IValidator.BLANK;
	}


	//
	@Override
	public DatabaseType type()
	{
		return DatabaseType.SHORT;
	}


	@Override
	public @NullAllow Short init()
	{
		return INIT;
	}


	@Override
	public boolean defined(@NullAllow Short value)
	{
		return !ShortUtil.equal(INIT, value);
	}


	@Override
	public @NullAllow Short prepare(@NullAllow Short value)
	{
		return value;
	}


	@Override
	public void validate(@NullAllow Short value)
		throws Exception
	{
		VALIDATOR_NULLABLE.validate(value);
		VALIDATOR_STRICT.validate(value);
		VALIDATOR_CUSTOM.validate(value);
	}


	@Override
	public boolean equal(@NullAllow Short value0, @NullAllow Short value1)
	{
		return ShortUtil.equal(value0, value1);
	}


	@Override
	public boolean instance(@NullAllow Object value)
	{
		return value instanceof Short;
	}


	@Override
	public Factories<Short> factories()
	{
		return ShortUtil.FACTORIES;
	}


	@Override
	public ShortModel model()
	{
		return MODEL;
	}


	@Override
	public LogAttachment log()
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
