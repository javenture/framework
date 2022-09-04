package net.javenture.framework.factory;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.exception.NotImplementedException;
import net.javenture.framework.util.Validator;


/*
	2020/01/05
 */
final public class Factories<T> // ! not <T extends IFactoryObject>
{
	// XXX: JSON ?


	//
	final private @NullDisallow IEntry<IInstanceFactory<T>> INSTANCE;
	final private @NullDisallow IEntry<ICopyFactory<T>> COPY;
	final private @NullDisallow IEntry<IByteFactory<T>> BYTE;
	final private @NullDisallow IEntry<IStringFactory<T>> STRING;
	final private @NullDisallow IEntry<IMurmur3HashFactory<T>> MURMUR3HASH;
	final private @NullDisallow IEntry<IConstFactory<T>> CONST;
	final private @NullDisallow IEntry<IDatabaseFactory<T, Object>> DATABASE;


	//
	@SafeVarargs
	public Factories(IFactory<T>... factories)
	{
		for (IFactory<T> factory : factories) Validator.notNull(factory, "[factory]");

		// instance
		{
			IInstanceFactory<T> f;

			{
				IInstanceFactory<T> f0 = null;

				for (IFactory<T> factory : factories)
				{
					if (factory.type() == IInstanceFactory.TYPE)
					{
						f0 = (IInstanceFactory<T>) factory;

						break;
					}
				}

				f = f0;
			}

			//
			INSTANCE = f != null
				? () -> f
				: () -> { throw new NotImplementedException(); };
		}

		// copy
		{
			ICopyFactory<T> f;

			{
				ICopyFactory<T> f0 = null;

				for (IFactory<T> factory : factories)
				{
					if (factory.type() == ICopyFactory.TYPE)
					{
						f0 = (ICopyFactory<T>) factory;

						break;
					}
				}

				f = f0;
			}

			//
			COPY = f != null
				? () -> f
				: () -> { throw new NotImplementedException(); };
		}

		// byte
		{
			IByteFactory<T> f;

			{
				IByteFactory<T> f0 = null;

				for (IFactory<T> factory : factories)
				{
					if (factory.type() == IByteFactory.TYPE)
					{
						f0 = (IByteFactory<T>) factory;

						break;
					}
				}

				f = f0;
			}

			//
			BYTE = f != null
				? () -> f
				: () -> { throw new NotImplementedException(); };
		}

		// string
		{
			IStringFactory<T> f;

			{
				IStringFactory<T> f0 = null;

				for (IFactory<T> factory : factories)
				{
					if (factory.type() == IStringFactory.TYPE)
					{
						f0 = (IStringFactory<T>) factory;

						break;
					}
				}

				f = f0;
			}

			//
			STRING = f != null
				? () -> f
				: () -> { throw new NotImplementedException(); };
		}

		// murmur3hash
		{
			IMurmur3HashFactory<T> f;

			{
				IMurmur3HashFactory<T> f0 = null;

				for (IFactory<T> factory : factories)
				{
					if (factory.type() == IMurmur3HashFactory.TYPE)
					{
						f0 = (IMurmur3HashFactory<T>) factory;

						break;
					}
				}

				f = f0;
			}

			//
			MURMUR3HASH = f != null
				? () -> f
				: () -> { throw new NotImplementedException(); };
		}

		// const
		{
			IConstFactory<T> f;

			{
				IConstFactory<T> f0 = null;

				for (IFactory<T> factory : factories)
				{
					if (factory.type() == IConstFactory.TYPE)
					{
						f0 = (IConstFactory<T>) factory;

						break;
					}
				}

				f = f0;
			}

			//
			CONST = f != null
				? () -> f
				: () -> { throw new NotImplementedException(); };
		}

		// database
		{
			IDatabaseFactory<T, Object> f;

			{
				IDatabaseFactory<T, Object> f0 = null;

				for (IFactory<T> factory : factories)
				{
					if (factory.type() == IDatabaseFactory.TYPE)
					{
						f0 = (IDatabaseFactory<T, Object>) factory;

						break;
					}
				}

				f = f0;
			}

			//
			DATABASE = f != null
				? () -> f
				: () -> { throw new NotImplementedException(); };
		}
	}


	//
	public @NullDisallow IInstanceFactory<T> getInstanceFactory()
	{
		return INSTANCE.get();
	}


	public @NullDisallow ICopyFactory<T> getCopyFactory()
	{
		return COPY.get();
	}


	public @NullDisallow IByteFactory<T> getByteFactory()
	{
		return BYTE.get();
	}


	public @NullDisallow IStringFactory<T> getStringFactory()
	{
		return STRING.get();
	}


	public @NullDisallow IMurmur3HashFactory<T> getMurmur3HashFactory()
	{
		return MURMUR3HASH.get();
	}


	public @NullDisallow IConstFactory<T> getConstFactory()
	{
		return CONST.get();
	}


	public @NullDisallow IDatabaseFactory<T, Object> getDatabaseFactory()
	{
		return DATABASE.get();
	}


	//
	@FunctionalInterface
	private interface IEntry<E extends IFactory>
	{
		E get();
	}

}
