package net.javenture.framework.util;


import java.time.Month;
import java.time.Year;
import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;


/*
	2019/04/29
 */
final public class DateTimeUtil
{
	//
	private DateTimeUtil()
	{
	}


	//
	public static int validateYear(int value)
	{
		ValueRange range = ChronoField.YEAR.range();

		if (value < range.getMinimum()) return (int) range.getMinimum();
		else if (value > range.getMaximum()) return (int) range.getMaximum();
		else return value;
	}


	public static int validateMonthOfYear(int value)
	{
		ValueRange range = ChronoField.MONTH_OF_YEAR.range();

		if (value < range.getMinimum()) return (int) range.getMinimum();
		else if (value > range.getMaximum()) return (int) range.getMaximum();
		else return value;
	}


	public static int validateDayOfMonth(int value, int month, int year)
	{
		ValueRange range = ChronoField.DAY_OF_MONTH.range();
		int minimum = (int) range.getMinimum();
		int maximum = Month.of(month).length(Year.of(year).isLeap());

		if (value < minimum) return minimum;
		else if (value > maximum) return maximum;
		else return value;
	}


	public static int validateHourOfDay(int value)
	{
		ValueRange range = ChronoField.HOUR_OF_DAY.range();

		if (value < range.getMinimum()) return (int) range.getMinimum();
		else if (value > range.getMaximum()) return (int) range.getMaximum();
		else return value;
	}


	public static int validateMinuteOfHour(int value)
	{
		ValueRange range = ChronoField.MINUTE_OF_HOUR.range();

		if (value < range.getMinimum()) return (int) range.getMinimum();
		else if (value > range.getMaximum()) return (int) range.getMaximum();
		else return value;
	}


	public static int validateSecondOfMinute(int value)
	{
		ValueRange range = ChronoField.SECOND_OF_MINUTE.range();

		if (value < range.getMinimum()) return (int) range.getMinimum();
		else if (value > range.getMaximum()) return (int) range.getMaximum();
		else return value;
	}


	public static int validateNanoOfSecond(int value)
	{
		ValueRange range = ChronoField.NANO_OF_SECOND.range();

		if (value < range.getMinimum()) return (int) range.getMinimum();
		else if (value > range.getMaximum()) return (int) range.getMaximum();
		else return value;
	}

}
