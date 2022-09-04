package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.log.LogAttachment;
import net.javenture.framework.util.DoubleUtil;
import net.javenture.framework.model.DoubleModel;


/*
	2020/01/05
 */
final public class DoubleDatabaseField
	extends ADatabaseField<Double>
{
	//
	final private @NullAllow Double INIT;
	final private DoubleModel MODEL;
	final private @NullDisallow IValidator<Double> VALIDATOR_NULLABLE;
	final private @NullDisallow IValidator<Double> VALIDATOR_STRICT;
	final private @NullDisallow IValidator<Double> VALIDATOR_CUSTOM;


	//
	public DoubleDatabaseField
	(
		@NullDisallow String table,
		int number,
		@NullDisallow String name,
		@NullAllow Double init,
		Option... options
	)
	{
		this(table, number, name, init, null, options);
	}


	@SuppressWarnings("unchecked")
	public DoubleDatabaseField
	(
		@NullDisallow String table,
		int number,
		@NullDisallow String name,
		@NullAllow Double init,
		@NullAllow IValidator<Double> validator,
		Option... options
	)
	{
		super(table, number, name, options);

		//
		INIT = init;
		MODEL = new DoubleModel(this, true, false);

		VALIDATOR_NULLABLE =
			NULLABLE
			? IValidator.BLANK
			: value -> { if (value == null) throw new Exception("[FIELD] (" + table + "." + name + ") disallow null value"); };

		VALIDATOR_STRICT =
			STRICT
			? value -> { if (DoubleUtil.equal(INIT, value)) throw new Exception("[FIELD] (" + table + "." + name + ") is strict"); }
			: IValidator.BLANK;

		VALIDATOR_CUSTOM = validator != null ? validator : IValidator.BLANK;
	}


	//
	@Override
	public DatabaseType type()
	{
		return DatabaseType.DOUBLE;
	}


	@Override
	public @NullAllow Double init()
	{
		return INIT;
	}


	@Override
	public boolean defined(@NullAllow Double value)
	{
		return !DoubleUtil.equal(INIT, value);
	}


	@Override
	public @NullAllow Double prepare(@NullAllow Double value)
	{
		return value;
	}


	@Override
	public void validate(@NullAllow Double value)
		throws Exception
	{
		VALIDATOR_NULLABLE.validate(value);
		VALIDATOR_STRICT.validate(value);
		VALIDATOR_CUSTOM.validate(value);
	}


	@Override
	public boolean equal(@NullAllow Double value0, @NullAllow Double value1)
	{
		return DoubleUtil.equal(value0, value1);
	}


	@Override
	public boolean instance(@NullAllow Object value)
	{
		return value instanceof Double;
	}


	@Override
	public Factories<Double> factories()
	{
		return DoubleUtil.FACTORIES;
	}


	@Override
	public DoubleModel model()
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
