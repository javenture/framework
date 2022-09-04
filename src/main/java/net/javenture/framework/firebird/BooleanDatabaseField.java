package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.log.LogAttachment;
import net.javenture.framework.util.BooleanUtil;
import net.javenture.framework.model.BooleanModel;


/*
	2020/01/05
 */
final public class BooleanDatabaseField
	extends ADatabaseField<Boolean>
{
	//
	final private @NullAllow Boolean INIT;
	final private BooleanModel MODEL;
	final private @NullDisallow IValidator<Boolean> VALIDATOR_NULLABLE;
	final private @NullDisallow IValidator<Boolean> VALIDATOR_STRICT;
	final private @NullDisallow IValidator<Boolean> VALIDATOR_CUSTOM;


	//
	public BooleanDatabaseField
	(
		@NullDisallow String table,
		int number,
		@NullDisallow String name,
		@NullAllow Boolean init,
		Option... options
	)
	{
		this(table, number, name, init, null, options);
	}


	@SuppressWarnings("unchecked")
	public BooleanDatabaseField
	(
		@NullDisallow String table,
		int number,
		@NullDisallow String name,
		@NullAllow Boolean init,
		@NullAllow IValidator<Boolean> validator,
		Option... options
	)
	{
		super(table, number, name, options);

		//
		INIT = init;
		MODEL = new BooleanModel(this, false, false);

		VALIDATOR_NULLABLE =
			NULLABLE
			? IValidator.BLANK
			: value -> { if (value == null) throw new Exception("[FIELD] (" + table + "." + name + ") disallow null value"); };

		VALIDATOR_STRICT =
			STRICT
			? value -> { if (BooleanUtil.equal(INIT, value)) throw new Exception("[FIELD] (" + table + "." + name + ") is strict"); }
			: IValidator.BLANK;

		VALIDATOR_CUSTOM = validator != null ? validator : IValidator.BLANK;
	}


	//
	@Override
	public DatabaseType type()
	{
		return DatabaseType.BOOLEAN;
	}


	@Override
	public @NullAllow Boolean init()
	{
		return INIT;
	}


	@Override
	public boolean defined(@NullAllow Boolean value)
	{
		return !BooleanUtil.equal(INIT, value);
	}


	@Override
	public @NullAllow Boolean prepare(@NullAllow Boolean value)
	{
		return value;
	}


	@Override
	public void validate(@NullAllow Boolean value)
		throws Exception
	{
		VALIDATOR_NULLABLE.validate(value);
		VALIDATOR_STRICT.validate(value);
		VALIDATOR_CUSTOM.validate(value);
	}


	@Override
	public boolean equal(@NullAllow Boolean value0, @NullAllow Boolean value1)
	{
		return BooleanUtil.equal(value0, value1);
	}


	@Override
	public boolean instance(@NullAllow Object value)
	{
		return value instanceof Boolean;
	}


	@Override
	public Factories<Boolean> factories()
	{
		return BooleanUtil.FACTORIES;
	}


	@Override
	public BooleanModel model()
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
