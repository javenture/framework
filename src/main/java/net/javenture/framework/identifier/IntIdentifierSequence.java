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
import net.javenture.framework.hash.Murmur3Hash;
import net.javenture.framework.script.IScriptEntry;
import net.javenture.framework.script.IntScriptArray;
import net.javenture.framework.script.NullScriptValue;
import net.javenture.framework.util.ASequence;
import net.javenture.framework.util.IParser;
import net.javenture.framework.util.IntSequence;
import net.javenture.framework.util.IntUtil;
import net.javenture.framework.util.Validator;
import net.javenture.framework.model.AFactoryObjectModel;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;


/*
	2020/01/05
 */
final public class IntIdentifierSequence
	extends ASequence<IntIdentifier, IntIdentifierSequence>
{
	//
	final private static int CAPACITY = 1024;                                                                                                                // XXX: 1024 ?
	final public static IInstanceFactory<IntIdentifierSequence> FACTORY_INSTANCE = object -> object instanceof IntIdentifierSequence;
	final public static ICopyFactory<IntIdentifierSequence> FACTORY_COPY = object -> object != null ? new IntIdentifierSequence(object.SEQUENCE) : null;

	final public static IByteFactory<IntIdentifierSequence> FACTORY_BYTE = new IByteFactory<>()
	{
		@Override
		public void toStream(@NullAllow IntIdentifierSequence object, OutputStream destination)
			throws Exception
		{
			IntSequence.FACTORY_BYTE.toStream(object != null ? object.SEQUENCE : null, destination);
		}

		@Override
		public @NullAllow IntIdentifierSequence fromStream(InputStream source)
			throws Exception
		{
			IntSequence sequence = IntSequence.FACTORY_BYTE.fromStream(source);

			return sequence != null ? new IntIdentifierSequence(sequence, true) : null;
		}
	};

	final public static IMurmur3HashFactory<IntIdentifierSequence> FACTORY_MURMUR3HASH = (object, destination) ->
	{
		if (IMurmur3HashFactory.updateNullHeader(object, destination)) IntSequence.FACTORY_MURMUR3HASH.update(object.SEQUENCE, destination);
	};

	final public static IDatabaseFactory<IntIdentifierSequence, byte[]> FACTORY_DATABASE = new IDatabaseFactory<>()
	{
		@Override
		public DatabaseType getDatabaseType()
		{
			return DatabaseType.BYTES;
		}

		@Override
		public @NullAllow byte[] toDatabase(@NullAllow IntIdentifierSequence object)
			throws Exception
		{
			return object != null ? IntSequence.FACTORY_DATABASE.toDatabase(object.SEQUENCE) : null;
		}

		@Override
		public @NullAllow IntIdentifierSequence fromDatabase(@NullAllow byte[] object)
			throws Exception
		{
			IntSequence sequence = IntSequence.FACTORY_DATABASE.fromDatabase(object);

			return sequence != null ? new IntIdentifierSequence(sequence, true) : null;
		}
	};

	final public static Factories<IntIdentifierSequence> FACTORIES = new Factories<>(FACTORY_INSTANCE, FACTORY_COPY, FACTORY_BYTE, FACTORY_MURMUR3HASH, FACTORY_DATABASE);


	//
	final @NullDisallow IntSequence SEQUENCE;


	//
	public IntIdentifierSequence()
	{
		this(CAPACITY);
	}


	public IntIdentifierSequence(int capacity)
	{
		SEQUENCE = new IntSequence(capacity);
	}


	private IntIdentifierSequence(@NullDisallow IntSequence sequence)
	{
		this(sequence, false);
	}


	private IntIdentifierSequence(@NullDisallow IntSequence sequence, boolean wrap)
	{
		Validator.notNull(sequence, "[sequence]");

		SEQUENCE = wrap ? sequence : IntSequence.FACTORY_COPY.copy(sequence);
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
	public boolean contains(IntIdentifier id)
	{
		return SEQUENCE.contains(id.VALUE);
	}


	@Override
	public int index(IntIdentifier id)
	{
		return SEQUENCE.index(id.VALUE);
	}


	@Override
	public IntIdentifier get(int index)
	{
		return new IntIdentifier(SEQUENCE.get(index));
	}


	@Override
	public boolean add(@NullDisallow IntIdentifier value)
	{
		return SEQUENCE.add(value.VALUE);
	}


	boolean add(int value)
	{
		return SEQUENCE.add(value);
	}


	public void add(List<IntIdentifier> list)
	{
		ensure(list.size());

		for (IntIdentifier id : list) SEQUENCE.add(id.VALUE);
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
	public Factories<IntIdentifierSequence> factories()
	{
		return FACTORIES;
	}


	@Override
	public Iterator<IntIdentifier> iterator()
	{
		return iterator(false);
	}


	public Iterator<IntIdentifier> iterator(boolean descend)
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
				public IntIdentifier next()
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
	}


	@Override
	public int hashCode()
	{
		Murmur3Hash result = new Murmur3Hash();
		FACTORY_MURMUR3HASH.update(this, result);

		return result.getInt();
	}


	@Override
	public boolean equals(@NullAllow IntIdentifierSequence object)
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
	public static IntIdentifierSequence wrap(IntSequence array)
	{
		return new IntIdentifierSequence(array, true);
	}


	//
	final public static class DatabaseField
		extends AFactoryObjectDatabaseField<IntIdentifierSequence>
	{
		//
		final private Model MODEL;

		//
		public DatabaseField(String table, int number, String name, @NullAllow IntIdentifierSequence init, Option... options)
		{
			super(table, number, name, init, FACTORIES, options);

			MODEL = new Model(this, true, false);
		}

		@Override
		public Model model()
		{
			return MODEL;
		}
	}


	final public static class Model
		extends AFactoryObjectModel<IntIdentifierSequence>
	{
		//
		final private Parser PARSER;

		//
		public Model(@NullDisallow DatabaseField field, boolean quotation, boolean trim)
		{
			super(field, quotation, trim);

			PARSER = new Parser(field.init(), field.nullable(), trim);
		}

		public Model(@NullDisallow String name, boolean nullable, boolean quotation, boolean trim, @NullAllow IntIdentifierSequence init, @NullDisallow String... objects)
		{
			super(name, nullable, quotation, trim, init, FACTORIES, objects);

			PARSER = new Parser(init, nullable, trim);
		}

		//
		@Override
		public @NullDisallow IScriptEntry toScriptEntry()
		{
			if (INIT != null)
			{
				IntScriptArray array = new IntScriptArray(QUOTATION);
				array.add(INIT.SEQUENCE);

				return array;
			}
			else
			{
				return NullScriptValue.INSTANCE;
			}
		}

		@Override
		public @NullDisallow IScriptEntry toScriptEntry(@NullAllow IntIdentifierSequence value)
		{
			Validator.argument(NULLABLE || value != null, "[value] null value disallowed");

			if (value != null)
			{
				IntScriptArray array = new IntScriptArray(QUOTATION);
				array.add(value.SEQUENCE);

				return array;
			}
			else
			{
				return NullScriptValue.INSTANCE;
			}
		}

		@Override
		public @NullDisallow IParser.Report<IntIdentifierSequence> parse(@NullAllow CharSequence source)
		{
			return PARSER.parse(source);
		}

		@Override
		public @NullDisallow IParser.Report<IntIdentifierSequence> parse(@NullAllow JsonObject source)
		{
			if (source != null)
			{
				IJsonEntry entry0 = source.get(NAME);

				if (entry0 != null)
				{
					if (entry0.type() == IJsonEntry.Type.ARRAY)
					{
						JsonArray array = (JsonArray) entry0;
						IntIdentifierSequence sequence = new IntIdentifierSequence(array.size());
						boolean error = false;

						if (QUOTATION)
						{
							for (IJsonEntry entry : array)
							{
								if (entry.type() == IJsonEntry.Type.STRING)
								{
									Integer value = IntUtil.primitive((StringJsonValue) entry, TRIM);

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
									Integer value = IntUtil.primitive((NumberJsonValue) entry); // ! no TRIM

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
		implements IParser<IntIdentifierSequence>
	{
		//
		final private @NullAllow IntIdentifierSequence INIT;
		final private boolean NULLABLE;
		final private boolean TRIM;
		final private Report<IntIdentifierSequence> REPORT_NULL;
		final private IntSequence.Parser PARSER;

		//
		public Parser(@NullAllow IntIdentifierSequence init, boolean nullable, boolean trim)
		{
			Validator.argument(nullable || init != null, "[init] null value disallowed");

			INIT = FACTORY_COPY.copy(init);
			NULLABLE = nullable;
			TRIM = trim;
			REPORT_NULL = new Report<>(true, init != null, null);
			PARSER = new IntSequence.Parser(init != null ? init.SEQUENCE : null, nullable, trim);
		}

		//
		@Override
		public @NullAllow IntIdentifierSequence init()
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
		public Report<IntIdentifierSequence> parse(@NullAllow IntIdentifierSequence value)
		{
			if (value != null) return new Report<>(true, !FactoryObjectUtil.equal(value, INIT), value);
			else if (NULLABLE) return REPORT_NULL;

			return new Report<>(false, false, FACTORY_COPY.copy(INIT));
		}

		@Override
		public Report<IntIdentifierSequence> parse(@NullAllow CharSequence source)
		{
			Report<IntSequence> report = PARSER.parse(source);

			if (report.EXISTS)
			{
				if (report.DEFINED)
				{
					if (report.VALUE != null) return new Report<>(true, true, IntIdentifierSequence.wrap(report.VALUE));
					else return REPORT_NULL;
				}
				else
				{
					return new IParser.Report<>(true, INIT == null || INIT.size() != 0, new IntIdentifierSequence(0));
				}
			}
			else
			{
				return new Report<>(false, false, FACTORY_COPY.copy(INIT));
			}
		}

		//
		final public static Parser BLANK = new Parser(new IntIdentifierSequence(0), false, false);
		final public static Parser NULL = new Parser(null, true, false);
	}

}
