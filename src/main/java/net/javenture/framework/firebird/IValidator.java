package net.javenture.framework.firebird;


/*
	2018/12/21
 */
@FunctionalInterface
public interface IValidator<T>
{
	//
	void validate(T value) throws Exception;


	//
	IValidator BLANK = value -> {};

}
