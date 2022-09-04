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
import net.javenture.framework.util.IntContainer;
import net.javenture.framework.hash.Murmur3Hash;
import net.javenture.framework.util.Validator;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;


/*
	2021/05/30
 */
final public class IntIdentifierContainer
	extends AContainer<IntIdentifier, IntIdentifierContainer>
{
	//
	final private static int CAPACITY = 1024;                                                                                                                     // XXX: 1024 ?
	final public static ICopyFactory<IntIdentifierContainer> FACTORY_COPY = object -> object != null ? new IntIdentifierContainer(object.CONTAINER) : null;

	final public static IByteFactory<IntIdentifierContainer> FACTORY_BYTE = new IByteFactory<>()
	{
		@Override
		public void toStream(@NullAllow IntIdentifierContainer object, OutputStream destination)
			throws Exception
		{
			IntContainer.FACTORY_BYTE.toStream(object != null ? object.CONTAINER : null, destination);
		}

		@Override
		public @NullAllow IntIdentifierContainer fromStream(InputStream source)
			throws Exception
		{
			IntContainer container = IntContainer.FACTORY_BYTE.fromStream(source);

			return container != null ? new IntIdentifierContainer(container, true) : null;
		}
	};

	final public static IMurmur3HashFactory<IntIdentifierContainer> FACTORY_MURMUR3HASH = (object, destination) ->
	{
		if (IMurmur3HashFactory.updateNullHeader(object, destination)) IntContainer.FACTORY_MURMUR3HASH.update(object.CONTAINER, destination);
	};

	final public static IDatabaseFactory<IntIdentifierContainer, byte[]> FACTORY_DATABASE = new IDatabaseFactory<>()
	{
		@Override
		public DatabaseType getDatabaseType()
		{
			return DatabaseType.BYTES;
		}

		@Override
		public @NullAllow byte[] toDatabase(@NullAllow IntIdentifierContainer object)
			throws Exception
		{
			return object != null ? IntContainer.FACTORY_DATABASE.toDatabase(object.CONTAINER) : null;
		}

		@Override
		public @NullAllow IntIdentifierContainer fromDatabase(@NullAllow byte[] object)
			throws Exception
		{
			IntContainer container = IntContainer.FACTORY_DATABASE.fromDatabase(object);

			return container != null ? new IntIdentifierContainer(container, true) : null;
		}
	};

	final public static Factories<IntIdentifierContainer> FACTORIES = new Factories<>(FACTORY_COPY, FACTORY_BYTE, FACTORY_MURMUR3HASH, FACTORY_DATABASE);


	//
	final private @NullDisallow IntContainer CONTAINER;


	//
	public IntIdentifierContainer()
	{
		this(CAPACITY);
	}


	public IntIdentifierContainer(int capacity)
	{
		CONTAINER = new IntContainer(capacity);
	}


	private IntIdentifierContainer(@NullDisallow IntContainer container)
	{
		this(container, false);
	}


	private IntIdentifierContainer(@NullDisallow IntContainer container, boolean wrap)
	{
		Validator.notNull(container, "[array]");

		CONTAINER = wrap ? container : IntContainer.FACTORY_COPY.copy(container);
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
	public boolean contains(IntIdentifier id)
	{
		return CONTAINER.contains(id.VALUE);
	}


	@Override
	public int index(IntIdentifier id)
	{
		return CONTAINER.index(id.VALUE);
	}


	@Override
	public IntIdentifier get(int index)
	{
		return new IntIdentifier(CONTAINER.get(index));
	}


	@Override
	public void add(IntIdentifier id)
	{
		add(id.VALUE);
	}


	public void add(IntIdentifier id, boolean unique)
	{
		if (!unique || !contains(id)) add(id.VALUE);
	}


	public void add(IntIdentifierContainer container)
	{
		CONTAINER.add(container.CONTAINER);
	}


	public void add(List<IntIdentifier> list)
	{
		ensure(list.size());

		for (IntIdentifier id : list) add(id.VALUE);
	}


	void add(int value)
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
	public Factories<IntIdentifierContainer> factories()
	{
		return FACTORIES;
	}


	@Override
	public Iterator<IntIdentifier> iterator()
	{
		return new Iterator<IntIdentifier>()
		{
			final private int SIZE = size();

			private int index = -1;

			@Override
			public boolean hasNext()
			{
				return index + 1 < SIZE;
			}

			@Override
			public IntIdentifier next()
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
	public boolean equals(@NullAllow IntIdentifierContainer object)
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
	public static IntIdentifierContainer wrap(IntContainer container)
	{
		return new IntIdentifierContainer(container, true);
	}

}
