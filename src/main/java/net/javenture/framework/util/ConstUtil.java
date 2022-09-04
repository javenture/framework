package net.javenture.framework.util;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.factory.IByteFactory;
import net.javenture.framework.factory.IConstFactory;
import net.javenture.framework.factory.ICopyFactory;
import net.javenture.framework.factory.IDatabaseFactory;
import net.javenture.framework.factory.IInstanceFactory;
import net.javenture.framework.factory.IStringFactory;
import net.javenture.framework.firebird.DatabaseType;

import java.io.InputStream;
import java.io.OutputStream;


/*
	2021/05/28
 */
final public class ConstUtil
{


	//
	private ConstUtil()
	{
	}


	//
	public static <C extends IConst<C>> Factories<C> factories(C[] values, IInstanceFactory<C> FACTORY_INSTANCE) // ! <C extends IConst<C>>
	{
		// validation
		{
			IntContainer container = new IntContainer(values.length);

			for (C value : values)
			{
				int id = value.id();

				if (!container.contains(id)) container.add(id);
				else throw new IllegalArgumentException("[id] (" + id + ") is duplicated");

				// toString()
				try
				{
					Integer id2 = IntUtil.parse(value.toString());

					if (id2 == null || id2 != id) throw new Exception();
				}
				catch (Exception ignore)
				{
					throw new IllegalArgumentException("[id] (" + id + ") doesn't match with toString()");
				}
			}
		}

		//
		final ICopyFactory<C> FACTORY_COPY = object -> object;

		final IConstFactory<C> FACTORY_CONST = new IConstFactory<>()
		{
			@Override
			public int toInt(@NullDisallow C object)
			{
				return object.id();
			}

			@Override
			public @NullDisallow C fromInt(int id)
				throws IllegalArgumentException
			{
				for (C c : values)
				{
					if (c.id() == id) return c;
				}

				throw new IllegalArgumentException("[id] (" + id + ") is illegal");
			}

			@Override
			public @NullAllow C fromInt(int id, @NullAllow C init)
			{
				for (C c : values)
				{
					if (c.id() == id) return c;
				}

				return init;
			}
		};

		final IStringFactory<C> FACTORY_STRING = new IStringFactory<>()
		{
			@Override
			public @NullDisallow String toString(@NullAllow C object)
			{
				return object != null
					? "" + object.id()
					: NullUtil.STRING;
			}

			@Override
			public @NullDisallow ConstParser<C> parser(@NullAllow C init, boolean nullable, boolean trim)
			{
				return new ConstParser<>(values, init, nullable, trim);
			}
		};

		final IByteFactory<C> FACTORY_BYTE = new IByteFactory<>()
		{
			@Override
			public void toStream(@NullAllow C object, OutputStream destination)
				throws Exception
			{
				if (IByteFactory.setNullHeader(object, destination)) IntUtil.bytes(FACTORY_CONST.toInt(object), destination);
			}

			@Override
			public @NullAllow C fromStream(InputStream source)
				throws Exception
			{
				return IByteFactory.getNullHeader(source)
					? FACTORY_CONST.fromInt(IntUtil.parse(source))
					: null;
			}
		};

		final IDatabaseFactory<C, Integer> FACTORY_DATABASE = new IDatabaseFactory<>()
		{
			@Override
			public DatabaseType getDatabaseType()
			{
				return DatabaseType.INT;
			}

			@Override
			public @NullAllow Integer toDatabase(@NullAllow C object)
			{
				return object != null
					? FACTORY_CONST.toInt(object)
					: null;
			}

			@Override
			public @NullAllow C fromDatabase(@NullAllow Integer object)
				throws Exception
			{
				return object != null
					? FACTORY_CONST.fromInt(object)
					: null;
			}
		};

		return new Factories<>(FACTORY_INSTANCE, FACTORY_COPY, FACTORY_CONST, FACTORY_STRING, FACTORY_BYTE, FACTORY_DATABASE);
	}


	/*
	 * IConst -> byte[]
	 */
	public static <C extends IConst<C>> byte[] bytes(@NullDisallow C value)
	{
		return IntUtil.bytes(value.id());
	}


	public static <C extends IConst<C>> void bytes(@NullDisallow C value, byte[] destination, int index)
	{
		IntUtil.bytes(value.id(), destination, index);
	}


	public static <C extends IConst<C>> void bytes(@NullDisallow C value, OutputStream destination)
		throws Exception
	{
		IntUtil.bytes(value.id(), destination);
	}


	/*
	 * byte[] -> IConst
	 */
	public static <C extends IConst<C>> C parse(byte b0, byte b1, byte b2, byte b3, Factories<C> factories)
		throws Exception
	{
		int id = IntUtil.parse(b0, b1, b2, b3);

		return factories.getConstFactory()
			.fromInt(id);
	}


	public static <C extends IConst<C>> C parse(byte[] source, int index, Factories<C> factories)
		throws Exception
	{
		int id = IntUtil.parse(source, index);

		return factories.getConstFactory()
			.fromInt(id);
	}


	public static @NullDisallow <C extends IConst<C>> C parse(byte[] source, int index, @NullDisallow C init, Factories<C> factories)
	{
		Validator.notNull(init, "[init]");
		int id = IntUtil.parse(source, index, init.id());

		return factories.getConstFactory()
			.fromInt(id, init);
	}


	public static <C extends IConst<C>> C parse(InputStream source, Factories<C> factories)
		throws Exception
	{
		int id = IntUtil.parse(source);

		return factories.getConstFactory()
			.fromInt(id);
	}


	public static @NullDisallow <C extends IConst<C>> C parse(InputStream source, @NullDisallow C init, Factories<C> factories)
	{
		Validator.notNull(init, "[init]");
		int id = IntUtil.parse(source, init.id());

		return factories.getConstFactory()
			.fromInt(id, init);
	}


	/*
	 * CharSequence -> IConst
	 */
	public static @NullAllow <C extends IConst<C>> C parse(@NullDisallow CharSequence sequence, Factories<C> factories)
		throws Exception
	{
		return parse(sequence, false, factories);
	}


	public static @NullAllow <C extends IConst<C>> C parse(@NullDisallow CharSequence sequence, boolean trim, Factories<C> factories)
		throws Exception
	{
		if (sequence != null)
		{
			if (NullUtil.notNull(sequence, trim))
			{
				Integer value = IntUtil.primitive(sequence, trim);

				if (value != null) return factories.getConstFactory().fromInt(value);
			}
			else
			{
				return null;
			}
		}

		throw new Exception("parse failed: " + StringUtil.toString(sequence, "\""));
	}


	public static @NullAllow <C extends IConst<C>> C parse(@NullAllow CharSequence sequence, @NullAllow C init, Factories<C> factories)
	{
		return parse(sequence, init, false, factories);
	}


	public static @NullAllow <C extends IConst<C>> C parse(@NullAllow CharSequence sequence, @NullAllow C init, boolean trim, Factories<C> factories)
	{
		if (sequence != null)
		{
			if (NullUtil.notNull(sequence, trim))
			{
				Integer value = IntUtil.primitive(sequence, trim);

				if (value != null) return factories.getConstFactory().fromInt(value, init);
			}
			else
			{
				return null;
			}
		}

		return init;
	}


	public static @NullAllow <C extends IConst<C>> C parse(@NullDisallow CharSequence sequence, int from, int to, Factories<C> factories)
		throws Exception
	{
		return parse(sequence, from, to, false, factories);
	}


	public static @NullAllow <C extends IConst<C>> C parse(@NullDisallow CharSequence sequence, int from, int to, boolean trim, Factories<C> factories)
		throws Exception
	{
		if (sequence != null)
		{
			if (NullUtil.notNull(sequence, from, to, trim))
			{
				Integer value = IntUtil.primitive(sequence, from, to, trim);

				if (value != null) return factories.getConstFactory().fromInt(value);
			}
			else
			{
				return null;
			}
		}

		throw new Exception("parse failed: " + StringUtil.toString(sequence, "\""));
	}


	public static @NullAllow <C extends IConst<C>> C parse(@NullAllow CharSequence sequence, int from, int to, @NullAllow C init, Factories<C> factories)
	{
		return parse(sequence, from, to, init, false, factories);
	}


	public static @NullAllow <C extends IConst<C>> C parse(@NullAllow CharSequence sequence, int from, int to, @NullAllow C init, boolean trim, Factories<C> factories)
	{
		if (sequence != null)
		{
			if (NullUtil.notNull(sequence, from, to, trim))
			{
				Integer value = IntUtil.primitive(sequence, from, to, trim);

				if (value != null) return factories.getConstFactory().fromInt(value, init);
			}
			else
			{
				return null;
			}
		}

		return init;
	}


	/*
	 * char[] -> IConst
	 */
	public static @NullAllow <C extends IConst<C>> C parse(@NullDisallow char[] array, Factories<C> factories)
		throws Exception
	{
		return parse(array, false, factories);
	}


	public static @NullAllow <C extends IConst<C>> C parse(@NullDisallow char[] array, boolean trim, Factories<C> factories)
		throws Exception
	{
		if (array != null)
		{
			if (NullUtil.notNull(array, trim))
			{
				Integer value = IntUtil.primitive(array, trim);

				if (value != null) return factories.getConstFactory().fromInt(value);
			}
			else
			{
				return null;
			}
		}

		throw new Exception("parse failed: " + CharArrayUtil.toString(array, "\""));
	}


	public static @NullAllow <C extends IConst<C>> C parse(@NullAllow char[] array, @NullAllow C init, Factories<C> factories)
	{
		return parse(array, init, false, factories);
	}


	public static @NullAllow <C extends IConst<C>> C parse(@NullAllow char[] array, @NullAllow C init, boolean trim, Factories<C> factories)
	{
		if (array != null)
		{
			if (NullUtil.notNull(array, trim))
			{
				Integer value = IntUtil.primitive(array, trim);

				if (value != null) return factories.getConstFactory().fromInt(value, init);
			}
			else
			{
				return null;
			}
		}

		return init;
	}


	public static @NullAllow <C extends IConst<C>> C parse(@NullDisallow char[] array, int from, int to, Factories<C> factories)
		throws Exception
	{
		return parse(array, from, to, false, factories);
	}


	public static @NullAllow <C extends IConst<C>> C parse(@NullDisallow char[] array, int from, int to, boolean trim, Factories<C> factories)
		throws Exception
	{
		if (array != null)
		{
			if (NullUtil.notNull(array, from, to, trim))
			{
				Integer value = IntUtil.primitive(array, from, to, trim);

				if (value != null) return factories.getConstFactory().fromInt(value);
			}
			else
			{
				return null;
			}
		}

		throw new Exception("parse failed: " + CharArrayUtil.toString(array, "\""));
	}


	public static @NullAllow <C extends IConst<C>> C parse(@NullAllow char[] array, int from, int to, @NullAllow C init, Factories<C> factories)
	{
		return parse(array, from, to, init, false, factories);
	}


	public static @NullAllow <C extends IConst<C>> C parse(@NullAllow char[] array, int from, int to, @NullAllow C init, boolean trim, Factories<C> factories)
	{
		if (array != null)
		{
			if (NullUtil.notNull(array, from, to, trim))
			{
				Integer value = IntUtil.primitive(array, from, to, trim);

				if (value != null) return factories.getConstFactory().fromInt(value, init);
			}
			else
			{
				return null;
			}
		}

		return init;
	}

}
