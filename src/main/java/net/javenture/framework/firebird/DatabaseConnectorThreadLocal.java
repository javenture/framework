package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.util.Validator;


/*
	2019/08/21
 */
final public class DatabaseConnectorThreadLocal
{
	//
	final private static ThreadLocal<DatabaseConnector> CONNECTOR = new ThreadLocal<>();


	//
	private DatabaseConnectorThreadLocal()
	{
	}


	//
	public static @NullAllow DatabaseConnector get()
	{
		return CONNECTOR.get();
	}


	public static void set(@NullDisallow DatabaseConnector connector)
	{
		Validator.notNull(connector, "[connector]");
		CONNECTOR.set(connector);
	}


	public static @NullAllow DatabaseConnector clear()
	{
		DatabaseConnector result = CONNECTOR.get();
		CONNECTOR.set(null);

		return result;
	}

}
