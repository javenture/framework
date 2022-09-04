package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.log.LogAttachment;
import net.javenture.framework.util.StringUtil;
import net.javenture.framework.model.StringModel;


/*
	2020/01/05
 */
final public class StringDatabaseField
	extends ADatabaseField<String>
{
	//
	final private @NullAllow String INIT;
	final private int LENGTH;
	final private StringModel MODEL;
	final private @NullDisallow IValidator<String> VALIDATOR_NULLABLE;
	final private @NullDisallow IValidator<String> VALIDATOR_STRICT;
	final private @NullDisallow IValidator<String> VALIDATOR_CUSTOM;


	//
	public StringDatabaseField
	(
		@NullDisallow String table,
		int number,
		@NullDisallow String name,
		@NullAllow String init,
		int length,
		Option... options
	)
	{
		this(table, number, name, init, length, null, options);
	}


	@SuppressWarnings("unchecked")
	public StringDatabaseField
	(
		@NullDisallow String table,
		int number,
		@NullDisallow String name,
		@NullAllow String init,
		int length,
		@NullAllow IValidator<String> validator,
		Option... options
	)
	{
		super(table, number, name, options);

		//
		INIT = init;
		LENGTH = length;
		MODEL = new StringModel(this, true, false);

		VALIDATOR_NULLABLE =
			NULLABLE
			? IValidator.BLANK
			: value -> { if (value == null) throw new Exception("[FIELD] (" + table + "." + name + ") disallow null value"); };

		VALIDATOR_STRICT =
			STRICT
			? value -> { if (StringUtil.equal(INIT, value)) throw new Exception("[FIELD] (" + table + "." + name + ") is strict"); }
			: IValidator.BLANK;

		VALIDATOR_CUSTOM = validator != null ? validator : IValidator.BLANK;
	}


	//
	@Override
	public DatabaseType type()
	{
		return DatabaseType.STRING;
	}


	@Override
	public @NullAllow String init()
	{
		return INIT;
	}


	@Override
	public boolean defined(@NullAllow String value)
	{
		return !StringUtil.equal(INIT, value);
	}


	@Override
	public @NullAllow String prepare(@NullAllow String value)
	{
		return value != null && value.length() > LENGTH
			? value.substring(0, LENGTH)
			: value;
	}


	@Override
	public void validate(@NullAllow String value)
		throws Exception
	{
		VALIDATOR_NULLABLE.validate(value);
		VALIDATOR_STRICT.validate(value);
		VALIDATOR_CUSTOM.validate(value);
	}


	@Override
	public boolean equal(@NullAllow String value0, @NullAllow String value1)
	{
		return StringUtil.equal(value0, value1);
	}


	@Override
	public boolean instance(@NullAllow Object value)
	{
		return value instanceof String;
	}


	@Override
	public Factories<String> factories()
	{
		return StringUtil.FACTORIES;
	}


	@Override
	public StringModel model()
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
			.add(INIT, "INIT")
			.add(LENGTH, "LENGTH");
	}


	public int length()
	{
		return LENGTH;
	}

}
