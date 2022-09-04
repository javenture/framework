package net.javenture.framework.firebird;


import net.javenture.framework.identifier.IntIdentifier;
import net.javenture.framework.identifier.LongIdentifier;

import java.util.concurrent.ThreadLocalRandom;


/*
	2019/12/01
 */
public interface IKeyGenerator<T>
{
	//

	// XXX: number

	boolean activity();
	void enable();
	void disable();
	void init(T value);
	T next();


	//
	static int getInt()
	{
		return ThreadLocalRandom.current()
			.nextInt();
	}


	static int getInt(int bound)
	{
		return ThreadLocalRandom.current()
			.nextInt(bound);
	}


	static int getInt(int origin, int bound)
	{
		return ThreadLocalRandom.current()
			.nextInt(origin, bound);
	}


	static long getLong()
	{
		return ThreadLocalRandom.current()
			.nextLong();
	}


	static long getLong(long bound)
	{
		return ThreadLocalRandom.current()
			.nextLong(bound);
	}


	static long getLong(long origin, long bound)
	{
		return ThreadLocalRandom.current()
			.nextLong(origin, bound);
	}


	//
	IKeyGenerator<Integer> INT = new IKeyGenerator<>()
	{
		@Override
		public boolean activity()
		{
			return true;
		}

		@Override
		public void enable()
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public void disable()
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public void init(Integer value)
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public Integer next()
		{
			return IKeyGenerator.getInt();
		}
	};


	IKeyGenerator<Long> LONG = new IKeyGenerator<>()
	{
		@Override
		public boolean activity()
		{
			return true;
		}

		@Override
		public void enable()
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public void disable()
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public void init(Long value)
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public Long next()
		{
			return IKeyGenerator.getLong();
		}
	};


	IKeyGenerator<IntIdentifier> IDENTIFIER_INT = new IKeyGenerator<>()
	{
		@Override
		public boolean activity()
		{
			return true;
		}

		@Override
		public void enable()
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public void disable()
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public void init(IntIdentifier value)
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public IntIdentifier next()
		{
			return new IntIdentifier(IKeyGenerator.getInt());
		}
	};


	IKeyGenerator<LongIdentifier> IDENTIFIER_LONG = new IKeyGenerator<>()
	{
		@Override
		public boolean activity()
		{
			return true;
		}

		@Override
		public void enable()
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public void disable()
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public void init(LongIdentifier value)
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public LongIdentifier next()
		{
			return new LongIdentifier(IKeyGenerator.getLong());
		}
	};

}
