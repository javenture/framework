package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.log.LogAttachment;
import net.javenture.framework.util.LongUtil;
import net.javenture.framework.model.LongModel;


/*
	2020/01/05
 */
final public class LongDatabaseField
	extends ADatabaseField<Long>
{
	//
	final private @NullAllow Long INIT;
	final private LongModel MODEL;
	final private @NullDisallow IValidator<Long> VALIDATOR_NULLABLE;
	final private @NullDisallow IValidator<Long> VALIDATOR_STRICT;
	final private @NullDisallow IValidator<Long> VALIDATOR_CUSTOM;


	//
	public LongDatabaseField
	(
		@NullDisallow String table,
		int number,
		@NullDisallow String name,
		@NullAllow Long init,
		Option... options
	)
	{
		this(table, number, name, init, null, options);
	}


	@SuppressWarnings("unchecked")
	public LongDatabaseField
	(
		@NullDisallow String table,
		int number,
		@NullDisallow String name,
		@NullAllow Long init,
		@NullAllow IValidator<Long> validator,
		Option... options
	)
	{
		super(table, number, name, options);

		//
		INIT = init;
		MODEL = new LongModel(this, true, false);

		VALIDATOR_NULLABLE =
			NULLABLE
			? IValidator.BLANK
			: value -> { if (value == null) throw new Exception("[FIELD] (" + table + "." + name + ") disallow null value"); };

		VALIDATOR_STRICT =
			STRICT
			? value -> { if (LongUtil.equal(INIT, value)) throw new Exception("[FIELD] (" + table + "." + name + ") is strict"); }
			: IValidator.BLANK;

		VALIDATOR_CUSTOM = validator != null ? validator : IValidator.BLANK;
	}


	//
	@Override
	public DatabaseType type()
	{
		return DatabaseType.LONG;
	}


	@Override
	public @NullAllow Long init()
	{
		return INIT;
	}


	@Override
	public boolean defined(@NullAllow Long value)
	{
		return !LongUtil.equal(INIT, value);
	}


	@Override
	public @NullAllow Long prepare(@NullAllow Long value)
	{
		return value;
	}


	@Override
	public void validate(@NullAllow Long value)
		throws Exception
	{
		VALIDATOR_NULLABLE.validate(value);
		VALIDATOR_STRICT.validate(value);
		VALIDATOR_CUSTOM.validate(value);
	}


	@Override
	public boolean equal(@NullAllow Long value0, @NullAllow Long value1)
	{
		return LongUtil.equal(value0, value1);
	}


	@Override
	public boolean instance(@NullAllow Object value)
	{
		return value instanceof Long;
	}


	@Override
	public Factories<Long> factories()
	{
		return LongUtil.FACTORIES;
	}


	@Override
	public LongModel model()
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
