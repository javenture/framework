package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.log.LogAttachment;
import net.javenture.framework.util.TimestampUtil;
import net.javenture.framework.model.TimestampModel;

import java.sql.Timestamp;


/*
	2020/01/05
 */
final public class TimestampDatabaseField
	extends ADatabaseField<Timestamp>
{
	//
	final private @NullAllow Timestamp INIT;
	final private TimestampModel MODEL;
	final private @NullDisallow IValidator<Timestamp> VALIDATOR_NULLABLE;
	final private @NullDisallow IValidator<Timestamp> VALIDATOR_STRICT;
	final private @NullDisallow IValidator<Timestamp> VALIDATOR_CUSTOM;


	//
	public TimestampDatabaseField
	(
		@NullDisallow String table,
		int number,
		@NullDisallow String name,
		@NullAllow Timestamp init,
		Option... options
	)
	{
		this(table, number, name, init, null, options);
	}


	@SuppressWarnings("unchecked")
	public TimestampDatabaseField
	(
		@NullDisallow String table,
		int number,
		@NullDisallow String name,
		@NullAllow Timestamp init,
		@NullAllow IValidator<Timestamp> validator,
		Option... options
	)
	{
		super(table, number, name, options);

		//
		INIT = TimestampUtil.FACTORY_COPY.copy(init);
		MODEL = new TimestampModel(this, true, false);

		VALIDATOR_NULLABLE = NULLABLE
			? IValidator.BLANK
			: value -> { if (value == null) throw new Exception("[FIELD] (" + table + "." + name + ") disallow null value"); };

		VALIDATOR_STRICT = STRICT
			? value -> { if (TimestampUtil.equal(INIT, value)) throw new Exception("[FIELD] (" + table + "." + name + ") is strict"); }
			: IValidator.BLANK;

		VALIDATOR_CUSTOM = validator != null ? validator : IValidator.BLANK;
	}


	//
	@Override
	public DatabaseType type()
	{
		return DatabaseType.TIMESTAMP;
	}


	@Override
	public @NullAllow Timestamp init()
	{
		return TimestampUtil.FACTORY_COPY.copy(INIT);
	}


	@Override
	public boolean defined(@NullAllow Timestamp value)
	{
		return !TimestampUtil.equal(INIT, value);
	}


	@Override
	public @NullAllow Timestamp prepare(@NullAllow Timestamp value)
	{
		return value;
	}


	@Override
	public void validate(@NullAllow Timestamp value)
		throws Exception
	{
		VALIDATOR_NULLABLE.validate(value);
		VALIDATOR_STRICT.validate(value);
		VALIDATOR_CUSTOM.validate(value);
	}


	@Override
	public boolean equal(@NullAllow Timestamp value0, @NullAllow Timestamp value1)
	{
		return TimestampUtil.equal(value0, value1);
	}


	@Override
	public boolean instance(@NullAllow Object value)
	{
		return value instanceof Timestamp;
	}


	@Override
	public Factories<Timestamp> factories()
	{
		return TimestampUtil.FACTORIES;
	}


	@Override
	public TimestampModel model()
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
