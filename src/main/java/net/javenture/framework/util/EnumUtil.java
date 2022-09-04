package net.javenture.framework.util;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.factory.IByteFactory;
import net.javenture.framework.factory.ICopyFactory;
import net.javenture.framework.factory.IDatabaseFactory;
import net.javenture.framework.factory.IConstFactory;
import net.javenture.framework.factory.IStringFactory;
import net.javenture.framework.firebird.DatabaseType;

import java.io.InputStream;
import java.io.OutputStream;


/*
	2019/12/04
 */
final public class EnumUtil
{
	//
	private EnumUtil()
	{
	}


	//
	public static <E extends Enum<E>> Factories<E> factories(E[] values)
	{
		return factories(values, Enum::name);
	}


	public static <E extends Enum<E>> Factories<E> factories(E[] values, @NullDisallow IEnumAlias<E> alias) // ! <E extends Enum<E>>
	{
		// validation
		{
			for (E value : values) Validator.notNull(alias.get(value), "[value] (", value, ") has null alias value");
		}

		//
		final ICopyFactory<E> FACTORY_COPY = object -> object;

		final IConstFactory<E> FACTORY_CONST = new IConstFactory<>()
		{
			@Override
			public int toInt(@NullDisallow E object)
			{
				return object.ordinal();
			}

			@Override
			public @NullDisallow E fromInt(int value)
				throws IllegalArgumentException
			{
				for (E e : values)
				{
					if (e.ordinal() == value) return e;
				}

				throw new IllegalArgumentException("[value] (" + value + ") is illegal");
			}

			@Override
			public @NullAllow E fromInt(int value, @NullAllow E init)
			{
				for (E e : values)
				{
					if (e.ordinal() == value) return e;
				}

				return init;
			}
		};

		final IStringFactory<E> FACTORY_STRING = new IStringFactory<>()
		{
			@Override
			public @NullDisallow String toString(@NullAllow E object)
			{
				return object != null ? alias.get(object) : NullUtil.STRING;
			}

			@Override
			public @NullDisallow EnumParser<E> parser(@NullAllow E init, boolean nullable, boolean trim)
			{
				return new EnumParser<>(values, alias, init, nullable, trim);
			}
		};

		final IByteFactory<E> FACTORY_BYTE = new IByteFactory<>()
		{
			@Override
			public void toStream(@NullAllow E object, OutputStream destination)
				throws Exception
			{
				if (IByteFactory.setNullHeader(object, destination)) IntUtil.bytes(FACTORY_CONST.toInt(object), destination);
			}

			@Override
			public @NullAllow E fromStream(InputStream source)
				throws Exception
			{
				return IByteFactory.getNullHeader(source)
					? FACTORY_CONST.fromInt(IntUtil.parse(source))
					: null;
			}
		};

		final IDatabaseFactory<E, Integer> FACTORY_DATABASE = new IDatabaseFactory<>()
		{
			@Override
			public DatabaseType getDatabaseType()
			{
				return DatabaseType.INT;
			}

			@Override
			public @NullAllow Integer toDatabase(@NullAllow E object)
			{
				return object != null ? FACTORY_CONST.toInt(object) : null;
			}

			@Override
			public @NullAllow E fromDatabase(@NullAllow Integer object)
				throws Exception
			{
				return object != null ? FACTORY_CONST.fromInt(object) : null;
			}
		};

		return new Factories<>(FACTORY_COPY, FACTORY_CONST, FACTORY_STRING, FACTORY_BYTE, FACTORY_DATABASE);
	}

}
