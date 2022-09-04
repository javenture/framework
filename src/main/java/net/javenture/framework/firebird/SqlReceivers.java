package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.factory.IDatabaseFactory;
import net.javenture.framework.util.Chain;
import net.javenture.framework.util.Primitives;
import net.javenture.framework.util.TimestampUtil;
import net.javenture.framework.util.TrafficCounter;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;


/*
	2021/05/28
 */
final public class SqlReceivers
{
	//
	final private static Entry[] ENTRIES0 = new Entry[0];


	//
	final private int COUNT;
	final private Entry[] ENTRIES;


	//
	private SqlReceivers(int count)
	{
		COUNT = count;
		ENTRIES = new Entry[count];
	}


	SqlReceivers(List<Entry> list)
	{
		COUNT = list.size();
		ENTRIES = list.toArray(ENTRIES0);
	}


	SqlReceivers(ResultSetMetaData data)
		throws SQLException
	{
		int count = data.getColumnCount();
		COUNT = count;
		ENTRIES = new Entry[count];

		for (int i = 0; i < count; i++) ENTRIES[i] = entry(i, data.getColumnType(i + 1));
	}


	//
	public int count()
	{
		return COUNT;
	}


	IDataset receive(ResultSet source, @NullAllow DatabaseType[] types, @NullAllow String[] labels, TrafficCounter counter)
		throws Exception
	{
		if (COUNT == 1)
		{
			Entry entry = ENTRIES[0]; // ! 0
			Chain<ObjectRow> rows = new Chain<>();

			while (source.next())
			{
				Object object = entry.GETTER.get(source, counter);
				rows.add(new ObjectRow(object));
			}

			return new ObjectDataset(types, labels, rows);
		}
		else
		{
			Chain<ArrayRow> rows = new Chain<>();

			while (source.next())
			{
				Object[] objects = new Object[COUNT];

				for (int i = 0; i < COUNT; i++) objects[i] = ENTRIES[i].GETTER.get(source, counter);

				rows.add(new ArrayRow(objects));
			}

			return new ArrayDataset(COUNT, types, labels, rows);
		}
	}


	IDataset receive(ResultSet source, @NullAllow DatabaseType[] types, @NullAllow String[] labels, IRowSqlProcessor preprocessor, TrafficCounter counter)
		throws Exception
	{
		if (COUNT == 1)
		{
			Entry entry = ENTRIES[0]; // ! 0

			while (source.next()) preprocessor.process(entry.GETTER.get(source, counter));

			return new ObjectDataset(types, labels);
		}
		else
		{
			while (source.next())
			{
				Object[] objects = new Object[COUNT];

				for (int i = 0; i < COUNT; i++) objects[i] = ENTRIES[i].GETTER.get(source, counter);

				preprocessor.process(objects);
			}

			return new ArrayDataset(COUNT, types, labels);
		}
	}


	IDataset receive(ResultSet source, @NullAllow DatabaseType[] types, @NullAllow String[] labels, IColumnSqlProcessor[] preprocessors, TrafficCounter counter)
		throws Exception
	{
		if (COUNT == 1)
		{
			Entry entry = ENTRIES[0]; // ! 0
			IColumnSqlProcessor preprocessor = preprocessors[0]; // ! 0

			while (source.next()) preprocessor.process(entry.GETTER.get(source, counter));

			return new ObjectDataset(types, labels);
		}
		else
		{
			while (source.next())
			{
				for (int i = 0; i < COUNT; i++) preprocessors[i].process(ENTRIES[i].GETTER.get(source, counter));
			}

			return new ArrayDataset(COUNT, types, labels);
		}
	}


	//
	public static Entry entry(int number, DatabaseType type)
	{
		return new Entry(number, type);
	}


	public static Entry entry(int number, DatabaseType type, String content)
	{
		return new Entry(number, type, content);
	}


	public static Entry entry(int number, ADatabaseField field)
	{
		return new Entry(number, field);
	}


	public static Entry entry(int number, ADatabaseField field, String content)
	{
		return new Entry(number, field, content);
	}


	public static Entry entry(int number, ADatabaseField field, boolean raw)
	{
		return new Entry(number, field, raw);
	}


	public static Entry entry(int number, ADatabaseField field, boolean raw, String content)
	{
		return new Entry(number, field, raw, content);
	}


	public static Entry entry(int number, int type)
	{
		return new Entry(number, type);
	}


	private static IGetter getter(int column, ADatabaseField field, boolean raw)
	{
		if (field instanceof AFactoryObjectDatabaseField)
		{
			IDatabaseFactory factory = field.factories()
				.getDatabaseFactory();

			return raw
				? getter(column, factory.getDatabaseType())
				: getter(column, factory);
		}
		else
		{
			return getter(column, field.type());
		}
	}


	private static IGetter getter(int column, DatabaseType type)
	{
		int index = column + 1;

		switch (type)
		{
			case BOOLEAN:
			{
				return (IGetter<Boolean>) (set, counter) ->
				{
					boolean b = set.getBoolean(index);

					if (!set.wasNull())
					{
						counter.update(Primitives.BOOLEAN.DIMENSION);

						return b;
					}
					else
					{
						return null;
					}
				};
			}

			case SHORT:
			{
				return (IGetter<Short>) (set, counter) ->
				{
					short s = set.getShort(index);

					if (!set.wasNull())
					{
						counter.update(Primitives.SHORT.DIMENSION);

						return s;
					}
					else
					{
						return null;
					}
				};
			}

			case INT:
			{
				return (IGetter<Integer>) (set, counter) ->
				{
					int i = set.getInt(index);

					if (!set.wasNull())
					{
						counter.update(Primitives.INT.DIMENSION);

						return i;
					}
					else
					{
						return null;
					}
				};
			}

			case LONG:
			{
				return (IGetter<Long>) (set, counter) ->
				{
					long l = set.getLong(index);

					if (!set.wasNull())
					{
						counter.update(Primitives.LONG.DIMENSION);

						return l;
					}
					else
					{
						return null;
					}
				};
			}

			case FLOAT:
			{
				return (IGetter<Float>) (set, counter) ->
				{
					float f = set.getFloat(index);

					if (!set.wasNull())
					{
						counter.update(Primitives.FLOAT.DIMENSION);

						return f;
					}
					else
					{
						return null;
					}
				};
			}

			case DOUBLE:
			{
				return (IGetter<Double>) (set, counter) ->
				{
					double d = set.getDouble(index);

					if (!set.wasNull())
					{
						counter.update(Primitives.DOUBLE.DIMENSION);

						return d;
					}
					else
					{
						return null;
					}
				};
			}

			case STRING:
			{
				return (IGetter<String>) (set, counter) ->
				{
					String s = set.getString(index);

					if (!set.wasNull())
					{
						counter.update(Primitives.CHAR.DIMENSION * s.length());

						return s;
					}
					else
					{
						return null;
					}
				};
			}

			case OCTETS:
			case BYTES:
			{
				return (IGetter<byte[]>) (set, counter) ->
				{
					byte[] array = set.getBytes(index);

					if (!set.wasNull())
					{
						counter.update(array.length);

						return array;
					}
					else
					{
						return null;
					}
				};
			}

			case TIMESTAMP:
			{
				return (IGetter<Timestamp>) (set, counter) ->
				{
					Timestamp timestamp = set.getTimestamp(index);

					if (!set.wasNull())
					{
						counter.update(TimestampUtil.DIMENSION);

						return timestamp;
					}
					else
					{
						return null;
					}
				};
			}

			default:
			{
				return (set, counter) ->
				{
					Object object = set.getObject(index);

					if (!set.wasNull())
					{
						counter.update(0);

						return object;
					}
					else
					{
						return null;
					}
				};
			}
		}
	}


	private static IGetter getter(int column, IDatabaseFactory factory)
	{
		int index = column + 1;

		switch (factory.getDatabaseType())
		{
			case BOOLEAN:
			{
				return (set, counter) ->
				{
					boolean b = set.getBoolean(index);

					if (!set.wasNull())
					{
						counter.update(Primitives.BOOLEAN.DIMENSION);

						return factory.fromDatabase(b);
					}
					else
					{
						return factory.fromDatabase(null);
					}
				};
			}

			case SHORT:
			{
				return (set, counter) ->
				{
					short s = set.getShort(index);

					if (!set.wasNull())
					{
						counter.update(Primitives.SHORT.DIMENSION);

						return factory.fromDatabase(s);
					}
					else
					{
						return factory.fromDatabase(null);
					}
				};
			}

			case INT:
			{
				return (set, counter) ->
				{
					int i = set.getInt(index);

					if (!set.wasNull())
					{
						counter.update(Primitives.INT.DIMENSION);

						return factory.fromDatabase(i);
					}
					else
					{
						return factory.fromDatabase(null);
					}
				};
			}

			case LONG:
			{
				return (set, counter) ->
				{
					long l = set.getLong(index);

					if (!set.wasNull())
					{
						counter.update(Primitives.LONG.DIMENSION);

						return factory.fromDatabase(l);
					}
					else
					{
						return factory.fromDatabase(null);
					}
				};
			}

			case FLOAT:
			{
				return (set, counter) ->
				{
					float f = set.getFloat(index);

					if (!set.wasNull())
					{
						counter.update(Primitives.FLOAT.DIMENSION);

						return factory.fromDatabase(f);
					}
					else
					{
						return factory.fromDatabase(null);
					}
				};
			}

			case DOUBLE:
			{
				return (set, counter) ->
				{
					double d = set.getDouble(index);

					if (!set.wasNull())
					{
						counter.update(Primitives.DOUBLE.DIMENSION);

						return factory.fromDatabase(d);
					}
					else
					{
						return factory.fromDatabase(null);
					}
				};
			}

			case STRING:
			{
				return (set, counter) ->
				{
					String s = set.getString(index);

					if (!set.wasNull())
					{
						counter.update(s.length() * Primitives.CHAR.DIMENSION);

						return factory.fromDatabase(s);
					}
					else
					{
						return factory.fromDatabase(null);
					}
				};
			}

			case OCTETS:
			case BYTES:
			{
				return (set, counter) ->
				{
					byte[] array = set.getBytes(index);

					if (!set.wasNull())
					{
						counter.update(array.length);

						return factory.fromDatabase(array);
					}
					else
					{
						return factory.fromDatabase(null);
					}
				};
			}

			case TIMESTAMP:
			{
				return (set, counter) ->
				{
					Timestamp timestamp = set.getTimestamp(index);

					if (!set.wasNull())
					{
						counter.update(TimestampUtil.DIMENSION);

						return factory.fromDatabase(timestamp);
					}
					else
					{
						return factory.fromDatabase(null);
					}
				};
			}

			default:
			{
				return (set, counter) ->
				{
					Object object = set.getObject(index);

					if (!set.wasNull())
					{
						counter.update(0);

						return factory.fromDatabase(object);
					}
					else
					{
						return factory.fromDatabase(null);
					}
				};
			}
		}
	}


	//
	final public static class Entry
	{
		//
		final int NUMBER; // !
		final String CONTENT;

		final private IGetter GETTER;

		//
		private Entry(int number, DatabaseType type)
		{
			this(number, type, "");
		}

		private Entry(int number, DatabaseType type, String content)
		{
			NUMBER = number;
			CONTENT = content;
			GETTER = getter(number, type);
		}

		private Entry(int number, ADatabaseField field)
		{
			this(number, field, false);
		}

		private Entry(int number, ADatabaseField field, String content)
		{
			this(number, field, false, content);
		}

		private Entry(int number, ADatabaseField field, boolean raw)
		{
			NUMBER = number;
			CONTENT = field.ALIAS;
			GETTER = getter(number, field, raw);
		}

		private Entry(int number, ADatabaseField field, boolean raw, String content)
		{
			NUMBER = number;
			CONTENT = content;
			GETTER = getter(number, field, raw);
		}

		private Entry(int number, int type)
		{
			this(number, DatabaseType.relation(type), "");
		}

		//
		@Override
		public String toString()
		{
			throw new UnsupportedOperationException(); // !
		}
	}


	//
	@FunctionalInterface
	private interface IGetter<T>
	{
		T get(ResultSet set, TrafficCounter counter) throws Exception;
	}


	//
	final static SqlReceivers BLANK = new SqlReceivers(0);

}
