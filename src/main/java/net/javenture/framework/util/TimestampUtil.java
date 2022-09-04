package net.javenture.framework.util;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.factory.IByteFactory;
import net.javenture.framework.factory.ICopyFactory;
import net.javenture.framework.factory.IMurmur3HashFactory;
import net.javenture.framework.factory.IStringFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;


/*
	2019/12/04
 */
final public class TimestampUtil
{
	//
	final public static int DIMENSION = Double.SIZE / Byte.SIZE;
	final public static ICopyFactory<Timestamp> FACTORY_COPY = object -> object != null ? new Timestamp(object.getTime()) : null;

	final public static IByteFactory<Timestamp> FACTORY_BYTE = new IByteFactory<>()
	{
		@Override
		public void toStream(@NullAllow Timestamp object, OutputStream destination)
			throws Exception
		{
			if (IByteFactory.setNullHeader(object, destination)) bytes(object, destination);
		}

		@Override
		public @NullAllow Timestamp fromStream(InputStream source)
			throws Exception
		{
			return IByteFactory.getNullHeader(source) ? parse(source) : null;
		}
	};

	final public static IStringFactory<Timestamp> FACTORY_STRING = new IStringFactory<>()
	{
		@Override
		public @NullDisallow String toString(@NullAllow Timestamp object)
		{
			return StringUtil.toString(object);
		}

		@Override
		public @NullDisallow TimestampParser parser(@NullAllow Timestamp init, boolean nullable, boolean trim)
		{
			return new TimestampParser(init, nullable, trim);
		}
	};

	final public static IMurmur3HashFactory<Timestamp> FACTORY_MURMUR3HASH = (object, destination) ->
	{
		if (IMurmur3HashFactory.updateNullHeader(object, destination)) LongUtil.murmur3hash(object.getTime(), destination);
	};

	final public static Factories<Timestamp> FACTORIES = new Factories<>(FACTORY_COPY, FACTORY_BYTE, FACTORY_STRING, FACTORY_MURMUR3HASH);


	//
	private TimestampUtil()
	{
	}


	public static Timestamp now()
	{
		return new Timestamp(UtcTimeUtil.ms());
	}


	/*
	 * Timestamp -> byte[]
	 */
	public static byte[] bytes(@NullDisallow Timestamp timestamp)
	{
		return LongUtil.bytes(timestamp.getTime());
	}


	public static void bytes(@NullDisallow Timestamp timestamp, OutputStream destination)
		throws Exception
	{
		LongUtil.bytes(timestamp.getTime(), destination);
	}


	/*
	 * byte[] -> Timestamp
	 */
	public static Timestamp parse(byte b0, byte b1, byte b2, byte b3, byte b4, byte b5, byte b6, byte b7)
	{
		return new Timestamp(LongUtil.parse(b0, b1, b2, b3, b4, b5, b6, b7));
	}


	public static Timestamp parse(InputStream source)
		throws Exception
	{
		return new Timestamp(LongUtil.parse(source));
	}


	/*
	 * String -> Timestamp
	 */
	public static @NullAllow Timestamp parse(@NullDisallow CharSequence sequence)
		throws Exception // !
	{
		Validator.notNull(sequence, "[sequence]");

		return NullUtil.notNull(sequence)
			? Timestamp.valueOf(sequence.toString())
			: null;
	}


	public static @NullAllow Timestamp parse(@NullAllow CharSequence sequence, @NullAllow Timestamp init)
	{
		if (sequence != null)
		{
			try
			{
				return NullUtil.notNull(sequence)
					? Timestamp.valueOf(sequence.toString())
					: null;
			}
			catch (Exception ignore)
			{
			}
		}

		return init;
	}


	static @NullAllow Timestamp parse0(@NullDisallow CharSequence sequence)
	{
		try
		{
			return Timestamp.valueOf(sequence.toString());
		}
		catch (Exception ignore)
		{
		}

		return null;
	}


	static @NullAllow Timestamp parse0(@NullDisallow char[] array)
	{
		try
		{
			return Timestamp.valueOf(new String(array));
		}
		catch (Exception ignore)
		{
		}

		return null;
	}


	/*
		DateTime -> Timestamp
	 */
	public static @NullAllow Timestamp convert(@NullAllow ZonedDateTime datetime)
	{
		return datetime != null
			? convert0(datetime)
			: null;
	}


	public static @NullAllow Timestamp convert(@NullAllow LocalDateTime datetime, ZoneId zone)
	{
		return datetime != null
			? convert0(datetime.atZone(zone))
			: null;
	}


	public static @NullAllow Timestamp convert(@NullAllow LocalDate date, ZoneId zone)
	{
		return date != null
			? convert0(date.atStartOfDay().atZone(zone))
			: null;
	}


	public static @NullAllow Timestamp convert(@NullAllow LocalDate date, @NullAllow LocalTime time, ZoneId zone)
	{
		return date != null && time != null
			? convert0(date.atTime(time).atZone(zone))
			: null;
	}


	private static @NullDisallow Timestamp convert0(@NullDisallow ZonedDateTime datetime)
	{
		return new Timestamp(datetime.toInstant().toEpochMilli());
	}


	/*
	 * equal
	 */
	public static boolean equal(@NullAllow Timestamp timestamp0, @NullAllow Timestamp timestamp1)
	{
		return
			timestamp0 == timestamp1
			||
			timestamp0 != null
			&&
			timestamp1 != null
			&&
			timestamp0.getTime() == timestamp1.getTime();
	}

}
