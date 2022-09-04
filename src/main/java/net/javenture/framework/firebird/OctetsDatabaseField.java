package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.log.LogAttachment;
import net.javenture.framework.util.ByteArrayUtil;
import net.javenture.framework.model.BytesModel;

import java.util.Arrays;


/*
	2020/01/05
 */
final public class OctetsDatabaseField
	extends ADatabaseField<byte[]>
{
	//
	final private @NullAllow byte[] INIT;
	final private int LENGTH;
	final private BytesModel MODEL;
	final private @NullDisallow IValidator<byte[]> VALIDATOR_NULLABLE;
	final private @NullDisallow IValidator<byte[]> VALIDATOR_STRICT;
	final private @NullDisallow IValidator<byte[]> VALIDATOR_CUSTOM;


	//
	public OctetsDatabaseField
	(
		@NullDisallow String table,
		int number,
		@NullDisallow String name,
		@NullAllow byte[] init,
		int length,
		Option... options
	)
	{
		this(table, number, name, init, length, null, options);
	}


	@SuppressWarnings("unchecked")
	public OctetsDatabaseField
	(
		@NullDisallow String table,
		int number,
		@NullDisallow String name,
		@NullAllow byte[] init,
		int length,
		@NullAllow IValidator<byte[]> validator,
		Option... options
	)
	{
		super(table, number, name, options);

		//
		INIT = ByteArrayUtil.FACTORY_COPY.copy(init);
		LENGTH = length;
		MODEL = new BytesModel(this, true, false);

		VALIDATOR_NULLABLE =
			NULLABLE
			? IValidator.BLANK
			: value -> { if (value == null) throw new Exception("[FIELD] (" + table + "." + name + ") disallow null value"); };

		VALIDATOR_STRICT =
			STRICT
			? value -> { if (ByteArrayUtil.equal(INIT, value)) throw new Exception("[FIELD] (" + table + "." + name + ") is strict"); }
			: IValidator.BLANK;

		VALIDATOR_CUSTOM = validator != null ? validator : IValidator.BLANK;
	}


	//
	@Override
	public DatabaseType type()
	{
		return DatabaseType.OCTETS;
	}


	@Override
	public @NullAllow byte[] init()
	{
		return ByteArrayUtil.FACTORY_COPY.copy(INIT);
	}


	@Override
	public boolean defined(@NullAllow byte[] value)
	{
		return !ByteArrayUtil.equal(INIT, value);
	}


	@Override
	public @NullAllow byte[] prepare(@NullAllow byte[] value)
	{
		return value != null && value.length > LENGTH
			? Arrays.copyOf(value, LENGTH)
			: value;
	}


	@Override
	public void validate(@NullAllow byte[] value)
		throws Exception
	{
		VALIDATOR_NULLABLE.validate(value);
		VALIDATOR_STRICT.validate(value);
		VALIDATOR_CUSTOM.validate(value);
	}


	@Override
	public boolean equal(@NullAllow byte[] value0, @NullAllow byte[] value1)
	{
		return ByteArrayUtil.equal(value0, value1);
	}


	@Override
	public boolean instance(@NullAllow Object value)
	{
		return value instanceof byte[];
	}


	@Override
	public Factories<byte[]> factories()
	{
		return ByteArrayUtil.FACTORIES;
	}


	@Override
	public BytesModel model()
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
