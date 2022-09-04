package net.javenture.framework.netty;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.annotation.SingleThread;
import net.javenture.framework.exception.NotImplementedException;
import net.javenture.framework.factory.IByteFactory;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.factory.IFactoryObject;
import net.javenture.framework.hex.HexUtil;
import net.javenture.framework.util.BooleanUtil;
import net.javenture.framework.util.Chain;
import net.javenture.framework.util.DoubleUtil;
import net.javenture.framework.util.FloatUtil;
import net.javenture.framework.util.IParser;
import net.javenture.framework.util.IntUtil;
import net.javenture.framework.util.LongUtil;
import net.javenture.framework.util.ShortUtil;
import net.javenture.framework.util.StringUtil;
import net.javenture.framework.util.Validator;
import net.javenture.framework.model.AModel;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;


/*
	2020/09/25
 */
@SingleThread
final public class NettyParameters
	implements IFactoryObject<NettyParameters>, Iterable<NettyParameters.Entry>
{
	//
	final public static IByteFactory<NettyParameters> FACTORY_BYTE = new IByteFactory<>()
	{
		@Override
		public void toStream(@NullAllow NettyParameters object, OutputStream destination)
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
					StringUtil.FACTORY_BYTE.toStream(entry.NAME, destination);
					StringUtil.FACTORY_BYTE.toStream(entry.VALUE, destination);
				}
			}
		}

		@Override
		public @NullAllow NettyParameters fromStream(InputStream source)
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
						String name = StringUtil.FACTORY_BYTE.fromStream(source);
						String value = StringUtil.FACTORY_BYTE.fromStream(source);
						entries.add(new Entry(name, value));
					}

					return new NettyParameters(entries);
				}
				else
				{
					return new NettyParameters();
				}
			}
			else
			{
				return null;
			}
		}
	};

	final public static Factories<NettyParameters> FACTORIES = new Factories<>(FACTORY_BYTE);


	//
	final private @NullDisallow Chain<Entry> ENTRIES; // ! Chain


	//
	public NettyParameters()
	{
		ENTRIES = new Chain<>();
	}


	public NettyParameters(NettyParameters source)
	{
		ENTRIES = new Chain<>(source.ENTRIES);
	}


	private NettyParameters(Chain<Entry> entries)
	{
		ENTRIES = entries;
	}


	//
	@Override
	public Factories<NettyParameters> factories()
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
	public boolean equals(NettyParameters object)
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


	public boolean contains(@NullDisallow String name)
	{
		Validator.notNull(name, "[name]");

		for (Entry entry : ENTRIES)
		{
			if (entry.NAME.equals(name)) return true;
		}

		return false;
	}


	public boolean contains(@NullDisallow String name, @NullDisallow String value)
	{
		Validator.notNull(name, "[name]");
		Validator.notNull(value, "[value]");

		for (Entry entry : ENTRIES)
		{
			if (entry.NAME.equals(name) && entry.VALUE.equals(value)) return true;
		}

		return false;
	}


	public void add(NettyParameters parameters)
	{
		this.ENTRIES.add(parameters.ENTRIES);
	}


	public void add(Entry entry)
	{
		ENTRIES.add(entry);
	}


	public void add(Chain<Entry> entries)
	{
		ENTRIES.add(entries);
	}


	public void add(@NullDisallow String name, @NullDisallow String value)
	{
		ENTRIES.add(new Entry(name, value));
	}


	public <V> void add(AModel<V> model, @NullAllow V value)
	{
		model.toNettyParameter(value, this);
	}


	public @NullAllow String getString(@NullDisallow String name)
	{
		Validator.notNull(name, "[name]");

		for (Entry entry : ENTRIES)
		{
			if (entry.NAME.equals(name)) return entry.VALUE;
		}

		return null;
	}


	public @NullAllow String getString(@NullDisallow String name, boolean trim)
	{
		Validator.notNull(name, "[name]");

		for (Entry entry : ENTRIES)
		{
			if (entry.NAME.equals(name)) return trim ? entry.VALUE.trim() : entry.VALUE;
		}

		return null;
	}


	public @NullAllow String getString(@NullDisallow String name, @NullAllow String init)
	{
		String s = getString(name);

		return s != null ? s : init;
	}


	public @NullAllow String getString(@NullDisallow String name, @NullAllow String init, boolean trim)
	{
		String s = getString(name, trim);

		return s != null ? s : init;
	}


	public @NullAllow Boolean getBoolean(@NullDisallow String name)
		throws Exception
	{
		String s = getString(name);

		return BooleanUtil.parse(s);
	}


	public boolean getBoolean(@NullDisallow String name, boolean init)
	{
		String s = getString(name);

		return BooleanUtil.parse(s, init, false);
	}


	public boolean getBoolean(@NullDisallow String name, boolean init, boolean trim)
	{
		String s = getString(name);

		return BooleanUtil.parse(s, init, trim);
	}


	public @NullAllow Boolean getBoolean(@NullDisallow String name, @NullAllow Boolean init)
	{
		String s = getString(name);

		return BooleanUtil.parse(s, init, false);
	}


	public @NullAllow Boolean getBoolean(@NullDisallow String name, @NullAllow Boolean init, boolean trim)
	{
		String s = getString(name);

		return BooleanUtil.parse(s, init, trim);
	}


	public @NullAllow Short getShort(@NullDisallow String name)
		throws Exception
	{
		String s = getString(name);

		return ShortUtil.parse(s, false);
	}


	public @NullAllow Short getShort(@NullDisallow String name, boolean trim)
		throws Exception
	{
		String s = getString(name);

		return ShortUtil.parse(s, trim);
	}


	public short getShort(@NullDisallow String name, short init)
	{
		String s = getString(name);

		return ShortUtil.parse(s, init, false);
	}


	public short getShort(@NullDisallow String name, short init, boolean trim)
	{
		String s = getString(name);

		return ShortUtil.parse(s, init, trim);
	}


	public @NullAllow Short getShort(@NullDisallow String name, @NullAllow Short init)
	{
		String s = getString(name);

		return ShortUtil.parse(s, init, false);
	}


	public @NullAllow Short getShort(@NullDisallow String name, @NullAllow Short init, boolean trim)
	{
		String s = getString(name);

		return ShortUtil.parse(s, init, trim);
	}


	public @NullAllow Integer getInt(@NullDisallow String name)
		throws Exception
	{
		String s = getString(name);

		return IntUtil.parse(s, false);
	}


	public @NullAllow Integer getInt(@NullDisallow String name, boolean trim)
		throws Exception
	{
		String s = getString(name);

		return IntUtil.parse(s, trim);
	}


	public int getInt(@NullDisallow String name, int init)
	{
		String s = getString(name);

		return IntUtil.parse(s, init, false);
	}


	public int getInt(@NullDisallow String name, int init, boolean trim)
	{
		String s = getString(name);

		return IntUtil.parse(s, init, trim);
	}


	public @NullAllow Integer getInt(@NullDisallow String name, @NullAllow Integer init)
	{
		String s = getString(name);

		return IntUtil.parse(s, init, false);
	}


	public @NullAllow Integer getInt(@NullDisallow String name, @NullAllow Integer init, boolean trim)
	{
		String s = getString(name);

		return IntUtil.parse(s, init, trim);
	}


	public @NullAllow Long getLong(@NullDisallow String name)
		throws Exception
	{
		String s = getString(name);

		return LongUtil.parse(s, false);
	}


	public @NullAllow Long getLong(@NullDisallow String name, boolean trim)
		throws Exception
	{
		String s = getString(name);

		return LongUtil.parse(s, trim);
	}


	public long getLong(@NullDisallow String name, long init)
	{
		String s = getString(name);

		return LongUtil.parse(s, init, false);
	}


	public long getLong(@NullDisallow String name, long init, boolean trim)
	{
		String s = getString(name);

		return LongUtil.parse(s, init, trim);
	}


	public @NullAllow Long getLong(@NullDisallow String name, @NullAllow Long init)
	{
		String s = getString(name);

		return LongUtil.parse(s, init, false);
	}


	public @NullAllow Long getLong(@NullDisallow String name, @NullAllow Long init, boolean trim)
	{
		String s = getString(name);

		return LongUtil.parse(s, init, trim);
	}


	public @NullAllow Float getFloat(@NullDisallow String name)
		throws Exception
	{
		String s = getString(name);

		return FloatUtil.parse(s, false);
	}


	public @NullAllow Float getFloat(@NullDisallow String name, boolean trim)
		throws Exception
	{
		String s = getString(name);

		return FloatUtil.parse(s, trim);
	}


	public float getFloat(@NullDisallow String name, float init)
	{
		String s = getString(name);

		return FloatUtil.parse(s, init, false);
	}


	public float getFloat(@NullDisallow String name, float init, boolean trim)
	{
		String s = getString(name);

		return FloatUtil.parse(s, init, trim);
	}


	public @NullAllow Float getFloat(@NullDisallow String name, @NullAllow Float init)
	{
		String s = getString(name);

		return FloatUtil.parse(s, init, false);
	}


	public @NullAllow Float getFloat(@NullDisallow String name, @NullAllow Float init, boolean trim)
	{
		String s = getString(name);

		return FloatUtil.parse(s, init, trim);
	}


	public @NullAllow Double getDouble(@NullDisallow String name)
		throws Exception
	{
		String s = getString(name);

		return DoubleUtil.parse(s, false);
	}


	public @NullAllow Double getDouble(@NullDisallow String name, boolean trim)
		throws Exception
	{
		String s = getString(name);

		return DoubleUtil.parse(s, trim);
	}


	public double getDouble(@NullDisallow String name, double init)
	{
		String s = getString(name);

		return DoubleUtil.parse(s, init, false);
	}


	public double getDouble(@NullDisallow String name, double init, boolean trim)
	{
		String s = getString(name);

		return DoubleUtil.parse(s, init, trim);
	}


	public @NullAllow Double getDouble(@NullDisallow String name, @NullAllow Double init)
	{
		String s = getString(name);

		return DoubleUtil.parse(s, init, false);
	}


	public @NullAllow Double getDouble(@NullDisallow String name, @NullAllow Double init, boolean trim)
	{
		String s = getString(name);

		return DoubleUtil.parse(s, init, trim);
	}


	public @NullAllow byte[] getBytes(@NullDisallow String name)
		throws Exception
	{
		String s = getString(name);

		return HexUtil.bytes(s, false);
	}


	public @NullAllow byte[] getBytes(@NullDisallow String name, boolean trim)
		throws Exception
	{
		String s = getString(name);

		return HexUtil.bytes(s, trim);
	}


	public @NullAllow byte[] getBytes(@NullDisallow String name, @NullAllow byte[] init)
	{
		String s = getString(name);

		return HexUtil.bytes(s, init, false);
	}


	public @NullAllow byte[] getBytes(@NullDisallow String name, @NullAllow byte[] init, boolean trim)
	{
		String s = getString(name);

		return HexUtil.bytes(s, init, trim);
	}


	public @NullAllow <V extends IFactoryObject<V>> V getFactoryObject(@NullDisallow String name, @NullAllow V init, boolean nullable, boolean trim, @NullDisallow Factories<V> factories)
	{
		String s = getString(name);

		return factories.getStringFactory()
			.parser(init, nullable, trim)
			.parse(s)
			.VALUE;
	}


	public @NullDisallow <V> IParser.Report<V> parse(@NullDisallow String name, @NullDisallow IParser<V> parser)
	{
		String s = getString(name);

		return parser.parse(s);
	}


	public @NullDisallow <V> IParser.Report<V> parse(@NullDisallow AModel<V> model)
	{
		String s = getString(model.alias());

		return model.parse(s);
	}


	public void clear()
	{
		ENTRIES.clear();
	}


	//
	final public static class Entry
	{
		//
		final public @NullDisallow String NAME; // ! String
		final public @NullDisallow String VALUE; // ! String

		//
		public Entry(@NullDisallow String name, @NullDisallow String value)
		{
			Validator.notNull(name, "[name]");
			Validator.notNull(value, "[value]");

			NAME = name;
			VALUE = value;
		}

		//
		public @NullDisallow String name()
		{
			return NAME;
		}

		public @NullDisallow String value()
		{
			return VALUE;
		}
	}

}
