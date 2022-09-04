package net.javenture.framework.util;


import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;


/*
	2019/04/29
 */
final public class ZonedDateTimeUtil
{
	//
	private ZonedDateTimeUtil()
	{
	}


	//
	public static ZonedDateTime instance(ZoneId zone)
	{
		Instant instant = Instant.now();

		return ZonedDateTime.ofInstant(instant, zone);
	}


	public static ZonedDateTime instance(long ms, ZoneId zone)
	{
		Instant instant = Instant.ofEpochMilli(ms);

		return ZonedDateTime.ofInstant(instant, zone);
	}


	public static ZonedDateTime instance(Timestamp value, ZoneId zone)
	{
		Instant instant = Instant.ofEpochMilli(value.getTime());

		return ZonedDateTime.ofInstant(instant, zone);
	}


	public static ZonedDateTime instance(int year, int month, int day, ZoneId zone, boolean correction)
	{
		return instance(year, month, day, 0, 0, 0, 0, zone, correction);
	}


	public static ZonedDateTime instance(int year, int month, int day, int hour, int minute, ZoneId zone, boolean correction)
	{
		return instance(year, month, day, hour, minute, 0, 0, zone, correction);
	}


	public static ZonedDateTime instance(int year, int month, int day, int hour, int minute, int second, int nano, ZoneId zone, boolean correction)
	{
		return
			correction
			? ZonedDateTime.of
			(
				DateTimeUtil.validateYear(year),
				DateTimeUtil.validateMonthOfYear(month),
				DateTimeUtil.validateDayOfMonth(day, month, year),
				DateTimeUtil.validateHourOfDay(hour),
				DateTimeUtil.validateMinuteOfHour(minute),
				DateTimeUtil.validateSecondOfMinute(second),
				DateTimeUtil.validateNanoOfSecond(nano),
				zone
			)
			: ZonedDateTime.of(year, month, day, hour, minute, second, nano, zone);
	}

}
