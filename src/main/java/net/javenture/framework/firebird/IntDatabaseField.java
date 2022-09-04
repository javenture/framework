package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.log.LogAttachment;
import net.javenture.framework.util.IntUtil;
import net.javenture.framework.model.IntModel;


/*
	2020/01/05
 */
final public class IntDatabaseField
	extends ADatabaseField<Integer>
{
	//
	final private @NullAllow Integer INIT;
	final private IntModel MODEL;
	final private @NullDisallow IValidator<Integer> VALIDATOR_NULLABLE;
	final private @NullDisallow IValidator<Integer> VALIDATOR_STRICT;
	final private @NullDisallow IValidator<Integer> VALIDATOR_CUSTOM;


	//
	public IntDatabaseField
	(
		@NullDisallow String table,
		int number,
		@NullDisallow String name,
		@NullAllow Integer init,
		Option... options
	)
	{
		this(table, number, name, init, null, options);
	}


	@SuppressWarnings("unchecked")
	public IntDatabaseField
	(
		@NullDisallow String table,
		int number,
		@NullDisallow String name,
		@NullAllow Integer init,
		@NullAllow IValidator<Integer> validator,
		Option... options
	)
	{
		super(table, number, name, options);

		//
		INIT = init;
		MODEL = new IntModel(this, true, false);

		VALIDATOR_NULLABLE =
			NULLABLE
			? IValidator.BLANK
			: value -> { if (value == null) throw new Exception("[FIELD] (" + table + "." + name + ") disallow null value"); };

		VALIDATOR_STRICT =
			STRICT
			? value -> { if (IntUtil.equal(INIT, value)) throw new Exception("[FIELD] (" + table + "." + name + ") is strict"); }
			: IValidator.BLANK;

		VALIDATOR_CUSTOM = validator != null ? validator : IValidator.BLANK;
	}


	//
	@Override
	public DatabaseType type()
	{
		return DatabaseType.INT;
	}


	@Override
	public @NullAllow Integer init()
	{
		return INIT;
	}


	@Override
	public boolean defined(@NullAllow Integer value)
	{
		return !IntUtil.equal(INIT, value);
	}


	@Override
	public @NullAllow Integer prepare(@NullAllow Integer value)
	{
		return value;
	}


	@Override
	public void validate(@NullAllow Integer value)
		throws Exception
	{
		VALIDATOR_NULLABLE.validate(value);
		VALIDATOR_STRICT.validate(value);
		VALIDATOR_CUSTOM.validate(value);
	}


	@Override
	public boolean equal(@NullAllow Integer value0, @NullAllow Integer value1)
	{
		return IntUtil.equal(value0, value1);
	}


	@Override
	public boolean instance(@NullAllow Object value)
	{
		return value instanceof Integer;
	}


	@Override
	public Factories<Integer> factories()
	{
		return IntUtil.FACTORIES;
	}


	@Override
	public IntModel model()
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
