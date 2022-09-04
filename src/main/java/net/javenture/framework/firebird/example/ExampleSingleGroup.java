package net.javenture.framework.firebird.example;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.firebird.ADatabaseField;
import net.javenture.framework.firebird.ASingleGroup;
import net.javenture.framework.firebird.DatabaseFields;
import net.javenture.framework.firebird.DatabaseTable;
import net.javenture.framework.identifier.IntIdentifier;
import net.javenture.framework.log.Log;
import net.javenture.framework.util.IntUtil;

import java.util.Comparator;


final class ExampleSingleGroup
	extends ASingleGroup<IntIdentifier, ExampleSingleGroup.Key, ExampleSingleGroup.Entry, ExampleSingleGroup>
{
	//
	final public static Config CONFIG = new Config();
	final private static Log LOG = Log.instance(ExampleSingleGroup.class);


	//
	public ExampleSingleGroup(IntIdentifier idSignature)
	{
		this(new Key(idSignature));
	}


	private ExampleSingleGroup(Key key)
	{
		super(key);
	}


	//
	@Override
	public Config config()
	{
		return CONFIG;
	}


	@Override
	public @NullDisallow Entry entry()
	{
		return new Entry(id());
	}


	/*
		config
	*/
	final static class Config
		extends AConfig<IntIdentifier, Key, Entry, ExampleSingleGroup>
	{
		//
		final public static String NAME_TABLE = "";

		final public static IntIdentifier.DatabaseField FIELD_ID = new IntIdentifier.DatabaseField                                    // XXX: index, not PK
		(
			NAME_TABLE,
			0,
			"ID",
			IntIdentifier.UNKNOWN,
			ADatabaseField.Option.KEY,
			ADatabaseField.Option.STRICT
		);

		final public static DatabaseFields FIELDS = new DatabaseFields
		(
			FIELD_ID
		);

		final public static DatabaseTable TABLE = new DatabaseTable
		(
			NAME_TABLE,
			FIELDS
		);

		final private static Comparator<Entry> COMPARATOR = null;

		//
		@Override
		public Log log()
		{
			return LOG;
		}

		@Override
		public DatabaseTable table()
		{
			return TABLE;
		}

		@Override
		public boolean journal()
		{
			return false;
		}

		@Override
		public boolean cache()
		{
			return false;
		}

		@Override
		public @NullAllow Comparator<Entry> comparator()
		{
			return COMPARATOR;
		}

		@Override
		protected byte[] toBytes(Key key)
		{
			IntIdentifier id = key.id();

			return IntUtil.bytes(id.VALUE);
		}

		@Override
		protected Key fromBytes(byte[] array)
			throws Exception
		{
			IntIdentifier id = new IntIdentifier(IntUtil.parse(array, 0));

			return new Key(id);
		}

		@Override
		protected ExampleSingleGroup instance(Key key)
		{
			return new ExampleSingleGroup(key);
		}
	}


	final static class Key
		extends ASingleGroup.AKey<IntIdentifier>
	{
		//
		private Key(IntIdentifier value)
		{
			super(value);
		}

		//
		@Override
		protected IntIdentifier.DatabaseField field()
		{
			return Config.FIELD_ID;
		}
	}


	final public static class Entry // ! public
		extends ASingleGroup.AEntry<IntIdentifier, Entry>
	{
		//
		private Entry(IntIdentifier id)
		{
			super(id);
		}

		//
		@Override
		protected Config config()
		{
			return CONFIG;
		}

		//
		IntIdentifier getId()
		{
			return get(Config.FIELD_ID);
		}

	}

}
