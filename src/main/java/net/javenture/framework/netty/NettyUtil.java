package net.javenture.framework.netty;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


/*
	2020/10/08
 */
final public class NettyUtil
{
	//
	final public static ZoneId HTTP_DATE_ZONE = ZoneId.of("GMT");
	final public static DateTimeFormatter HTTP_DATE_FORMATTER = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US).withZone(HTTP_DATE_ZONE);


	//
	private NettyUtil()
	{
	}


	//
	public static LocalDateTime now()
	{
		return LocalDateTime.now(HTTP_DATE_ZONE);
	}


	public static @NullDisallow String format(LocalDateTime datetime)
	{
		return HTTP_DATE_FORMATTER.format(datetime);
	}


	public static @NullDisallow String format(long ms)
	{
		return HTTP_DATE_FORMATTER.format(Instant.ofEpochMilli(ms));
	}


	public static @NullAllow LocalDateTime parse(String value)
	{
		try
		{
			return LocalDateTime.parse(value, HTTP_DATE_FORMATTER);
		}
		catch (Exception ignore)
		{
			return null;
		}
	}

}
