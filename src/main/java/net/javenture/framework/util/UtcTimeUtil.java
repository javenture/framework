package net.javenture.framework.util;


import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;


/*
	2018/06/29
 */
final public class UtcTimeUtil
{
	//
	final public static ZoneId ZONE_UTC = ZoneOffset.UTC;


	//
	private UtcTimeUtil()
	{
	}


	//
	public static long ms()
	{
		return System.currentTimeMillis();
	}


	public static long ns()
	{
		return System.nanoTime();
	}


	public static Timestamp timestamp()                                                                             // XXX: del ?
	{
		return new Timestamp(ms());
	}


	public static ZonedDateTime instance()
	{
		return ZonedDateTimeUtil.instance(ZONE_UTC);
	}


	public static ZonedDateTime instance(long ms)
	{
		return ZonedDateTimeUtil.instance(ms, ZONE_UTC);
	}


	public static ZonedDateTime instance(Timestamp value)                                                           // XXX: del ?
	{
		return ZonedDateTimeUtil.instance(value, ZONE_UTC);
	}

}
