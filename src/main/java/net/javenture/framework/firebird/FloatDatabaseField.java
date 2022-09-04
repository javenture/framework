package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.log.LogAttachment;
import net.javenture.framework.util.FloatUtil;
import net.javenture.framework.model.FloatModel;


/*
	2020/01/05
 */
final public class FloatDatabaseField
	extends ADatabaseField<Float>
{
	//
	final private @NullAllow Float INIT;
	final private FloatModel MODEL;
	final private @NullDisallow IValidator<Float> VALIDATOR_NULLABLE;
	final private @NullDisallow IValidator<Float> VALIDATOR_STRICT;
	final private @NullDisallow IValidator<Float> VALIDATOR_CUSTOM;


	//
	public FloatDatabaseField
	(
		@NullDisallow String table,
		int number,
		@NullDisallow String name,
		@NullAllow Float init,
		Option... options
	)
	{
		this(table, number, name, init, null, options);
	}


	@SuppressWarnings("unchecked")
	public FloatDatabaseField
	(
		@NullDisallow String table,
		int number,
		@NullDisallow String name,
		@NullAllow Float init,
		@NullAllow IValidator<Float> validator,
		Option... options
	)
	{
		super(table, number, name, options);

		//
		INIT = init;
		MODEL = new FloatModel(this, true, false);

		VALIDATOR_NULLABLE =
			NULLABLE
			? IValidator.BLANK
			: value -> { if (value == null) throw new Exception("[FIELD] (" + table + "." + name + ") disallow null value"); };

		VALIDATOR_STRICT =
			STRICT
			? value -> { if (FloatUtil.equal(INIT, value)) throw new Exception("[FIELD] (" + table + "." + name + ") is strict"); }
			: IValidator.BLANK;

		VALIDATOR_CUSTOM = validator != null ? validator : IValidator.BLANK;
	}


	//
	@Override
	public DatabaseType type()
	{
		return DatabaseType.FLOAT;
	}


	@Override
	public @NullAllow Float init()
	{
		return INIT;
	}


	@Override
	public boolean defined(@NullAllow Float value)
	{
		return !FloatUtil.equal(INIT, value);
	}


	@Override
	public @NullAllow Float prepare(@NullAllow Float value)
	{
		return value;
	}


	@Override
	public void validate(@NullAllow Float value)
		throws Exception
	{
		VALIDATOR_NULLABLE.validate(value);
		VALIDATOR_STRICT.validate(value);
		VALIDATOR_CUSTOM.validate(value);
	}


	@Override
	public boolean equal(@NullAllow Float value0, @NullAllow Float value1)
	{
		return FloatUtil.equal(value0, value1);
	}


	@Override
	public boolean instance(@NullAllow Object value)
	{
		return value instanceof Float;
	}


	@Override
	public Factories<Float> factories()
	{
		return FloatUtil.FACTORIES;
	}


	@Override
	public FloatModel model()
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
