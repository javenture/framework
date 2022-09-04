package net.javenture.framework.identifier;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.factory.IByteFactory;
import net.javenture.framework.factory.ICopyFactory;
import net.javenture.framework.factory.IDatabaseFactory;
import net.javenture.framework.factory.IMurmur3HashFactory;
import net.javenture.framework.firebird.DatabaseType;
import net.javenture.framework.util.AContainer;
import net.javenture.framework.util.LongContainer;
import net.javenture.framework.hash.Murmur3Hash;
import net.javenture.framework.util.Validator;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;


/*
	2019/12/04
 */
final public class LongIdentifierContainer
	extends AContainer<LongIdentifier, LongIdentifierContainer>
{
	//
	final private static int CAPACITY = 512;                                                       // XXX: 512
	final public static ICopyFactory<LongIdentifierContainer> FACTORY_COPY = object -> object != null ? new LongIdentifierContainer(object.CONTAINER) : null;

	final public static IByteFactory<LongIdentifierContainer> FACTORY_BYTE = new IByteFactory<>()
	{
		@Override
		public void toStream(@NullAllow LongIdentifierContainer object, OutputStream destination)
			throws Exception
		{
			LongContainer.FACTORY_BYTE.toStream(object != null ? object.CONTAINER : null, destination);
		}

		@Override
		public @NullAllow LongIdentifierContainer fromStream(InputStream source)
			throws Exception
		{
			LongContainer container = LongContainer.FACTORY_BYTE.fromStream(source);

			return container != null ? new LongIdentifierContainer(container, true) : null;
		}
	};

	final public static IMurmur3HashFactory<LongIdentifierContainer> FACTORY_MURMUR3HASH = (object, destination) ->
	{
		if (IMurmur3HashFactory.updateNullHeader(object, destination)) LongContainer.FACTORY_MURMUR3HASH.update(object.CONTAINER, destination);
	};

	final public static IDatabaseFactory<LongIdentifierContainer, byte[]> FACTORY_DATABASE = new IDatabaseFactory<>()
	{
		@Override
		public DatabaseType getDatabaseType()
		{
			return DatabaseType.BYTES;
		}

		@Override
		public @NullAllow byte[] toDatabase(@NullAllow LongIdentifierContainer object)
			throws Exception
		{
			return object != null ? LongContainer.FACTORY_DATABASE.toDatabase(object.CONTAINER) : null;
		}

		@Override
		public @NullAllow LongIdentifierContainer fromDatabase(@NullAllow byte[] object)
			throws Exception
		{
			LongContainer container = LongContainer.FACTORY_DATABASE.fromDatabase(object);

			return container != null ? new LongIdentifierContainer(container, true) : null;
		}
	};

	final public static Factories<LongIdentifierContainer> FACTORIES = new Factories<>(FACTORY_COPY, FACTORY_BYTE, FACTORY_MURMUR3HASH, FACTORY_DATABASE);


	//
	final private @NullDisallow LongContainer CONTAINER;


	//
	public LongIdentifierContainer()
	{
		this(CAPACITY);
	}


	public LongIdentifierContainer(int capacity)
	{
		CONTAINER = new LongContainer(capacity);
	}


	private LongIdentifierContainer(@NullDisallow LongContainer container)
	{
		this(container, false);
	}


	private LongIdentifierContainer(@NullDisallow LongContainer container, boolean wrap)
	{
		Validator.notNull(container, "[array]");

		CONTAINER = wrap ? container : LongContainer.FACTORY_COPY.copy(container);
	}


	//
	@Override
	public int capacity()
	{
		return CONTAINER.capacity();
	}


	@Override
	public int size()
	{
		return CONTAINER.size();
	}


	@Override
	public void clear()
	{
		CONTAINER.clear();
	}


	@Override
	public boolean contains(LongIdentifier id)
	{
		return CONTAINER.contains(id.VALUE);
	}


	@Override
	public int index(LongIdentifier id)
	{
		return CONTAINER.index(id.VALUE);
	}


	@Override
	public LongIdentifier get(int index)
	{
		return new LongIdentifier(CONTAINER.get(index));
	}


	@Override
	public void add(LongIdentifier id)
	{
		add(id.VALUE);
	}


	public void add(LongIdentifierContainer container)
	{
		CONTAINER.add(container.CONTAINER);
	}


	public void add(List<LongIdentifier> list)
	{
		ensure(list.size());

		for (LongIdentifier id : list) add(id.VALUE);
	}


	private void add(long value)
	{
		CONTAINER.add(value);
	}


	@Override
	public void remove(int index)
	{
		CONTAINER.remove(index);
	}


	@Override
	public void ensure(int count)
	{
		CONTAINER.ensure(count);
	}


	@Override
	public void sort()
	{
		CONTAINER.sort();
	}


	@Override
	public Factories<LongIdentifierContainer> factories()
	{
		return FACTORIES;
	}


	@Override
	public Iterator<LongIdentifier> iterator()
	{
		return new Iterator<LongIdentifier>()
		{
			final int SIZE = size();

			int index = -1;

			@Override
			public boolean hasNext()
			{
				return index + 1 < SIZE;
			}

			@Override
			public LongIdentifier next()
			{
				return get(++index);
			}

			@Override
			public void remove()
			{
				throw new UnsupportedOperationException();
			}
		};
	}


	@Override
	public int hashCode()
	{
		Murmur3Hash result = new Murmur3Hash();
		FACTORY_MURMUR3HASH.update(this, result);

		return result.getInt();
	}


	@Override
	public boolean equals(@NullAllow LongIdentifierContainer object)
	{
		return
			this == object
			||
			object != null
			&&
			this.CONTAINER.equals(object.CONTAINER);
	}


	@Override
	public String toString()
	{
		return CONTAINER.toString();
	}


	//
	public static LongIdentifierContainer wrap(LongContainer container)
	{
		return new LongIdentifierContainer(container, true);
	}

}
