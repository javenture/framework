package net.javenture.framework.util;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;


/*
	2020/10/19
 */
final public class FileContent<T>
{
	//
	final public @NullDisallow Status STATUS;
	final public @NullAllow T VALUE;
	final public @NullAllow Exception EXCEPTION;


	//
	@SuppressWarnings("CopyConstructorMissesField")
	public FileContent(@NullDisallow FileContent source)
	{
		this(source.STATUS, null, source.EXCEPTION);
	}


	public FileContent(@NullDisallow Status status)
	{
		this(status, null, null);
	}


	public FileContent(@NullDisallow Status status, @NullAllow T value)
	{
		this(status, value, null);
	}


	public FileContent(@NullDisallow Status status, @NullAllow Exception exception)
	{
		this(status, null, exception);
	}


	public FileContent(@NullDisallow Status status, @NullAllow T value, @NullAllow Exception exception)
	{
		Validator.notNull(status, "[status]");

		STATUS = status;
		VALUE = value;
		EXCEPTION = exception;
	}


	//
	public @NullDisallow Status status()
	{
		return STATUS;
	}


	public @NullAllow T value()
	{
		return VALUE;
	}


	public @NullAllow Exception exception()
	{
		return EXCEPTION;
	}


	//
	public enum Status
	{
		UNKNOWN,
		OK,
		NOT_FOUND,
		ERROR,
		ERROR_LIMIT,
		ERROR_SOURCE,
		ERROR_DESTINATION,                       // ???
		ERROR_CONTENT
	}

}
