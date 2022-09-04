package net.javenture.framework.identifier;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.factory.FactoryObjectUtil;
import net.javenture.framework.factory.IByteFactory;
import net.javenture.framework.factory.ICopyFactory;
import net.javenture.framework.factory.IDatabaseFactory;
import net.javenture.framework.factory.IInstanceFactory;
import net.javenture.framework.factory.IMurmur3HashFactory;
import net.javenture.framework.firebird.AFactoryObjectDatabaseField;
import net.javenture.framework.firebird.DatabaseType;
import net.javenture.framework.json.IJsonEntry;
import net.javenture.framework.json.JsonArray;
import net.javenture.framework.json.JsonObject;
import net.javenture.framework.json.NumberJsonValue;
import net.javenture.framework.json.StringJsonValue;
import net.javenture.framework.model.AFactoryObjectModel;
import net.javenture.framework.hash.Murmur3Hash;
import net.javenture.framework.script.IScriptEntry;
import net.javenture.framework.script.LongScriptArray;
import net.javenture.framework.script.NullScriptValue;
import net.javenture.framework.util.ASequence;
import net.javenture.framework.util.IParser;
import net.javenture.framework.util.LongSequence;
import net.javenture.framework.util.LongUtil;
import net.javenture.framework.util.Validator;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;


/*
	2020/01/05
 */
final public class LongIdentifierSequence
	extends ASequence<LongIdentifier, LongIdentifierSequence>
{
	//
	final private static int CAPACITY = 512;                                                                                                                // XXX: 512 ?
	final public static IInstanceFactory<LongIdentifierSequence> FACTORY_INSTANCE = object -> object instanceof LongIdentifierSequence;
	final public static ICopyFactory<LongIdentifierSequence> FACTORY_COPY = object -> object != null ? new LongIdentifierSequence(object.SEQUENCE) : null;

	final public static IByteFactory<LongIdentifierSequence> FACTORY_BYTE = new IByteFactory<>()
	{
		@Override
		public void toStream(@NullAllow LongIdentifierSequence object, OutputStream destination)
			throws Exception
		{
			LongSequence.FACTORY_BYTE.toStream(object != null ? object.SEQUENCE : null, destination);
		}

		@Override
		public @NullAllow LongIdentifierSequence fromStream(InputStream source)
			throws Exception
		{
			LongSequence sequence = LongSequence.FACTORY_BYTE.fromStream(source);

			return sequence != null ? new LongIdentifierSequence(sequence, true) : null;
		}
	};

	final public static IMurmur3HashFactory<LongIdentifierSequence> FACTORY_MURMUR3HASH = (object, destination) ->
	{
		if (IMurmur3HashFactory.updateNullHeader(object, destination)) LongSequence.FACTORY_MURMUR3HASH.update(object.SEQUENCE, destination);
	};

	final public static IDatabaseFactory<LongIdentifierSequence, byte[]> FACTORY_DATABASE = new IDatabaseFactory<>()
	{
		@Override
		public DatabaseType getDatabaseType()
		{
			return DatabaseType.BYTES;
		}

		@Override
		public @NullAllow byte[] toDatabase(@NullAllow LongIdentifierSequence object)
			throws Exception
		{
			return object != null ? LongSequence.FACTORY_DATABASE.toDatabase(object.SEQUENCE) : null;
		}

		@Override
		public @NullAllow LongIdentifierSequence fromDatabase(@NullAllow byte[] object)
			throws Exception
		{
			LongSequence sequence = LongSequence.FACTORY_DATABASE.fromDatabase(object);

			return sequence != null ? new LongIdentifierSequence(sequence, true) : null;
		}
	};

	final public static Factories<LongIdentifierSequence> FACTORIES = new Factories<>(FACTORY_INSTANCE, FACTORY_COPY, FACTORY_BYTE, FACTORY_MURMUR3HASH, FACTORY_DATABASE);


	//
	final @NullDisallow LongSequence SEQUENCE;


	//
	public LongIdentifierSequence()
	{
		this(CAPACITY);
	}


	public LongIdentifierSequence(int capacity)
	{
		SEQUENCE = new LongSequence(capacity);
	}


	private LongIdentifierSequence(@NullDisallow LongSequence sequence)
	{
		this(sequence, false);
	}


	private LongIdentifierSequence(@NullDisallow LongSequence sequence, boolean wrap)
	{
		Validator.notNull(sequence, "[sequence]");

		SEQUENCE = wrap ? sequence : LongSequence.FACTORY_COPY.copy(sequence);
	}


	//
	@Override
	public int capacity()
	{
		return SEQUENCE.capacity();
	}


	@Override
	public int size()
	{
		return SEQUENCE.size();
	}


	@Override
	public void clear()
	{
		SEQUENCE.clear();
	}


	@Override
	public boolean contains(LongIdentifier id)
	{
		return SEQUENCE.contains(id.VALUE);
	}


	@Override
	public int index(LongIdentifier id)
	{
		return SEQUENCE.index(id.VALUE);
	}


	@Override
	public LongIdentifier get(int index)
	{
		return new LongIdentifier(SEQUENCE.get(index));
	}


	@Override
	public boolean add(@NullDisallow LongIdentifier value)
	{
		return SEQUENCE.add(value.VALUE);
	}


	boolean add(long value)
	{
		return SEQUENCE.add(value);
	}


	public void add(List<LongIdentifier> list)
	{
		ensure(list.size());

		for (LongIdentifier id : list) SEQUENCE.add(id.VALUE);
	}


	@Override
	public void remove(int index)
	{
		SEQUENCE.remove(index);
	}


	@Override
	public boolean valid()
	{
		return SEQUENCE.valid();
	}


	@Override
	public void sort()
	{
		SEQUENCE.sort();
	}


	@Override
	public void ensure(int count)
	{
		SEQUENCE.ensure(count);
	}


	@Override
	public Factories<LongIdentifierSequence> factories()
	{
		return FACTORIES;
	}


	@Override
	public Iterator<LongIdentifier> iterator()
	{
		return iterator(false);
	}


	public Iterator<LongIdentifier> iterator(boolean descend)
	{
		if (descend)
		{
			return new Iterator<>()
			{
				private int index = size();

				@Override
				public boolean hasNext()
				{
					return index - 1 >= 0;
				}

				@Override
				public LongIdentifier next()
				{
					return get(--index);
				}

				@Override
				public void remove()
				{
					throw new UnsupportedOperationException();
				}
			};
		}
		else
		{
			return new Iterator<>()
			{
				final private int SIZE = size();

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
	}


	@Override
	public int hashCode()
	{
		Murmur3Hash result = new Murmur3Hash();
		FACTORY_MURMUR3HASH.update(this, result);

		return result.getInt();
	}


	@Override
	public boolean equals(@NullAllow LongIdentifierSequence object)
	{
		return
			this == object
			||
			object != null
			&&
			this.SEQUENCE.equals(object.SEQUENCE);
	}


	@Override
	public String toString()
	{
		return SEQUENCE.toString();
	}


	//
	public static LongIdentifierSequence wrap(LongSequence array)
	{
		return new LongIdentifierSequence(array, true);
	}


	//
	final public static class DatabaseField
		extends AFactoryObjectDatabaseField<LongIdentifierSequence>
	{
		//
		final private LongIdentifierSequence.Model MODEL;

		//
		public DatabaseField(String table, int number, String name, @NullAllow LongIdentifierSequence init, Option... options)
		{
			super(table, number, name, init, FACTORIES, options);

			MODEL = new LongIdentifierSequence.Model(this, true, false);
		}

		@Override
		public LongIdentifierSequence.Model model()
		{
			return MODEL;
		}
	}


	final public static class Model
		extends AFactoryObjectModel<LongIdentifierSequence>
	{
		//
		final private LongIdentifierSequence.Parser PARSER;

		//
		public Model(@NullDisallow LongIdentifierSequence.DatabaseField field, boolean quotation, boolean trim)
		{
			super(field, quotation, trim);

			PARSER = new LongIdentifierSequence.Parser(field.init(), field.nullable(), trim);
		}

		public Model(@NullDisallow String name, boolean nullable, boolean quotation, boolean trim, @NullAllow LongIdentifierSequence init, @NullDisallow String... objects)
		{
			super(name, nullable, quotation, trim, init, FACTORIES, objects);

			PARSER = new LongIdentifierSequence.Parser(init, nullable, trim);
		}

		//
		@Override
		public @NullDisallow IScriptEntry toScriptEntry()
		{
			if (INIT != null)
			{
				LongScriptArray array = new LongScriptArray(QUOTATION);
				array.add(INIT.SEQUENCE);

				return array;
			}
			else
			{
				return NullScriptValue.INSTANCE;
			}
		}

		@Override
		public @NullDisallow IScriptEntry toScriptEntry(@NullAllow LongIdentifierSequence value)
		{
			Validator.argument(NULLABLE || value != null, "[value] null value disallowed");

			if (value != null)
			{
				LongScriptArray array = new LongScriptArray(QUOTATION);
				array.add(value.SEQUENCE);

				return array;
			}
			else
			{
				return NullScriptValue.INSTANCE;
			}
		}

		@Override
		public @NullDisallow IParser.Report<LongIdentifierSequence> parse(@NullAllow CharSequence source)
		{
			return PARSER.parse(source);
		}

		@Override
		public @NullDisallow IParser.Report<LongIdentifierSequence> parse(@NullAllow JsonObject source)
		{
			if (source != null)
			{
				IJsonEntry entry0 = source.get(NAME);

				if (entry0 != null)
				{
					if (entry0.type() == IJsonEntry.Type.ARRAY)
					{
						JsonArray array = (JsonArray) entry0;
						LongIdentifierSequence sequence = new LongIdentifierSequence(array.size());
						boolean error = false;

						if (QUOTATION)
						{
							for (IJsonEntry entry : array)
							{
								if (entry.type() == IJsonEntry.Type.STRING)
								{
									Long value = LongUtil.primitive((StringJsonValue) entry, TRIM);

									if (value != null)
									{
										sequence.add(value);
									}
									else
									{
										error = true;

										break;
									}
								}
								else
								{
									error = true;

									break;
								}
							}
						}
						else
						{
							for (IJsonEntry entry : array)
							{
								if (entry.type() == IJsonEntry.Type.NUMBER)
								{
									Long value = LongUtil.primitive((NumberJsonValue) entry); // ! no TRIM

									if (value != null)
									{
										sequence.add(value);
									}
									else
									{
										error = true;

										break;
									}
								}
								else
								{
									error = true;

									break;
								}
							}
						}

						//
						if (!error) return new IParser.Report<>(true, !FactoryObjectUtil.equal(sequence, INIT), sequence);
					}
					else if (entry0.type() == IJsonEntry.Type.NULL && NULLABLE)
					{
						return PARSER.REPORT_NULL;
					}
				}
			}

			return new IParser.Report<>(false, false, FACTORY_COPY.copy(INIT));
		}
	}


	final public static class Parser
		implements IParser<LongIdentifierSequence>
	{
		//
		final private @NullAllow LongIdentifierSequence INIT;
		final private boolean NULLABLE;
		final private boolean TRIM;
		final private Report<LongIdentifierSequence> REPORT_NULL;
		final private LongSequence.Parser PARSER;

		//
		public Parser(@NullAllow LongIdentifierSequence init, boolean nullable, boolean trim)
		{
			Validator.argument
			(
				nullable || init != null,
				"[init] null value disallowed"
			);

			INIT = FACTORY_COPY.copy(init);
			NULLABLE = nullable;
			TRIM = trim;
			REPORT_NULL = new Report<>(true, init != null, null);
			PARSER = new LongSequence.Parser(init != null ? init.SEQUENCE : null, nullable, trim);
		}

		//
		@Override
		public @NullAllow LongIdentifierSequence init()
		{
			return FACTORY_COPY.copy(INIT);
		}

		@Override
		public boolean nullable()
		{
			return NULLABLE;
		}

		@Override
		public boolean trim()
		{
			return TRIM;
		}

		@Override
		public Report<LongIdentifierSequence> parse(@NullAllow LongIdentifierSequence value)
		{
			if (value != null) return new Report<>(true, !FactoryObjectUtil.equal(value, INIT), value);
			else if (NULLABLE) return REPORT_NULL;

			return new Report<>(false, false, FACTORY_COPY.copy(INIT));
		}

		@Override
		public Report<LongIdentifierSequence> parse(@NullAllow CharSequence source)
		{
			Report<LongSequence> report = PARSER.parse(source);

			if (report.EXISTS)
			{
				if (report.DEFINED)
				{
					if (report.VALUE != null) return new Report<>(true, true, LongIdentifierSequence.wrap(report.VALUE));
					else return REPORT_NULL;
				}
				else
				{
					return new IParser.Report<>(true, INIT == null || INIT.size() != 0, new LongIdentifierSequence(0));
				}
			}
			else
			{
				return new Report<>(false, false, FACTORY_COPY.copy(INIT));
			}
		}

		//
		final public static LongIdentifierSequence.Parser BLANK = new LongIdentifierSequence.Parser(new LongIdentifierSequence(0), false, false);
		final public static LongIdentifierSequence.Parser NULL = new LongIdentifierSequence.Parser(null, true, false);
	}

}
