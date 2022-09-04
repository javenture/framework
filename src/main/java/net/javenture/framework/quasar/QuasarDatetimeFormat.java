package net.javenture.framework.quasar;


/*
	2019/07/19
 */
public enum QuasarDatetimeFormat
{
	// Year
	YY, // 70 71 … 29 30
	YYYY, // 1970 1971 … 2029 2030

	// Month
	M, // 1 2 … 11 12
	MM, // 01 02 … 11 12
	MMM, // Jan Feb … Nov Dec
	MMMM, // January February … November December

	// Quarter
	Q, // Quarter number 1 2 3 4
	Qo, // Quarter number 1st 2nd 3rd 4th

	// DayOfMonth
	D, // 1 2 … 30 31
	Do, // 1st 2nd … 30th 31st
	DD, // 01 02 … 30 31

	// DayOfYear
	DDD, // 1 2 … 364 365
	DDDD, // 001 002 … 364 365

	// DayOfWeek
	d, // 0 1 … 5 6
	dd, // Su Mo … Fr Sa
	ddd, // Sun Mon … Fri Sat
	dddd, // Sunday Monday … Friday Saturday

	// DayOfWeekISO
	E, // 1 2 … 6 7

	// WeekOfYear
	w, // 1 2 … 52 53
	ww, // 01 02 … 52 53

	// Hour
	H, // 0 1 … 22 23
	HH, // 00 01 … 22 23
	h, // 0 … 11 12
	hh, // 01 02 … 11 12

	// Minute
	m, // 0 1 … 58 59
	mm, // 00 01 … 58 59

	// Second
	s, // 0 1 … 58 59
	ss, // 00 01 … 58 59

	// FractionalSecond
	S, // 0 1 … 8 9
	SS, // 00 01 … 98 99
	SSS, // 000 001 … 998 999

	// TimezoneOffset
	Z, // -07:00 -06:00 … +06:00 +07:00
	ZZ, // -0700 -0600 … +0600 +0700

	// AmPm
	A, // AM, PM
	a, // am, pm
	aa, // a.m, p.m

	// UnixTimestamp
	X, // 1360013296
	x // 1360013296123

}
