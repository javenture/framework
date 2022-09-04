package net.javenture.framework.netty;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.exception.NotImplementedException;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.factory.IByteFactory;
import net.javenture.framework.factory.IFactoryObject;
import net.javenture.framework.util.Chain;
import net.javenture.framework.util.IntUtil;
import net.javenture.framework.util.Validator;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;


/*
	2020/09/27
 */
final public class NettyUploads
	implements IFactoryObject<NettyUploads>, Iterable<NettyUploads.Entry>
{
	//
	final public static IByteFactory<NettyUploads> FACTORY_BYTE = new IByteFactory<>()
	{
		@Override
		public void toStream(@NullAllow NettyUploads object, OutputStream destination)
			throws Exception
		{
			if (IByteFactory.setNullHeader(object, destination))
			{
				// size
				int size = object.ENTRIES.size();
				IntUtil.bytes(size, destination);

				// entries
				for (Entry entry : object.ENTRIES)
				{
					// XXX
					throw new UnsupportedOperationException();


					//StringUtil.FACTORY_BYTE.toStream(entry.NAME, destination);
					//StringUtil.FACTORY_BYTE.toStream(entry.VALUE, destination);
				}
			}
		}

		@Override
		public @NullAllow NettyUploads fromStream(InputStream source)
			throws Exception
		{
			if (IByteFactory.getNullHeader(source))
			{
				// size
				int size = IntUtil.parse(source);

				// entries
				if (size != 0)
				{
					Chain<Entry> entries = new Chain<>();

					for (int i = 0; i < size; i++)
					{
						// XXX
						throw new UnsupportedOperationException();


						//String name = StringUtil.FACTORY_BYTE.fromStream(source);
						//String value = StringUtil.FACTORY_BYTE.fromStream(source);
						//entries.add(new Entry(name, value));
					}

					return new NettyUploads(entries);
				}
				else
				{
					return new NettyUploads();
				}
			}
			else
			{
				return null;
			}
		}
	};

	final public static Factories<NettyUploads> FACTORIES = new Factories<>(FACTORY_BYTE);


	//
	final private @NullDisallow Chain<Entry> ENTRIES; // ! Chain


	//
	public NettyUploads()
	{
		ENTRIES = new Chain<>();
	}


	public NettyUploads(NettyUploads source)
	{
		ENTRIES = new Chain<>(source.ENTRIES);
	}


	private NettyUploads(Chain<Entry> entries)
	{
		ENTRIES = entries;
	}


	//
	@Override
	public Factories<NettyUploads> factories()
	{
		return FACTORIES;
	}


	@Override
	public Iterator<Entry> iterator()
	{
		return ENTRIES.iterator();
	}


	@Override
	public int hashCode()
	{
		throw new NotImplementedException();
	}


	@Override
	public boolean equals(NettyUploads object)
	{
		throw new NotImplementedException();
	}


	public boolean exists()
	{
		return ENTRIES.exists();
	}


	public int size()
	{
		return ENTRIES.size();
	}


	public void add(@NullDisallow String path, @NullDisallow String original, long length, @NullAllow String type)
	{
		ENTRIES.add(new Entry(path, original, length, type));
	}


	//
	final public static class Entry
	{
		//

		// XXX: STATUS


		final @NullDisallow String PATH; // ! String
		final @NullDisallow String ORIGINAL; // ! String
		final long LENGTH;
		final @NullAllow String TYPE; // ! String

		//
		public Entry(@NullDisallow String path, @NullDisallow String original, long length, @NullAllow String type)
		{
			Validator.notNull(path, "[path]");
			Validator.notNull(original, "[original]");

			PATH = path;
			ORIGINAL = original;
			LENGTH = length;
			TYPE = type;
		}

		//
		public @NullDisallow String path()
		{
			return PATH;
		}

		public @NullDisallow String original()
		{
			return ORIGINAL;
		}

		public long length()
		{
			return LENGTH;
		}

		public @NullAllow String type()
		{
			return TYPE;
		}
	}

}
