package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.factory.IDatabaseFactory;
import net.javenture.framework.factory.IFactoryObject;
import net.javenture.framework.util.Primitives;
import net.javenture.framework.util.TimestampUtil;
import net.javenture.framework.util.TrafficCounter;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;


/*
	2021/05/28
 */
final public class SqlSenders
{
	//
	final private static Entry[] ENTRIES0 = new Entry[0];


	//
	final private int COUNT;
	final private Entry[] ENTRIES;


	//
	private SqlSenders(int count)
	{
		COUNT = count;
		ENTRIES = new Entry[count];
	}


	SqlSenders(List<Entry> list)
	{
		COUNT = list.size();
		ENTRIES = list.toArray(ENTRIES0);
	}


	SqlSenders(Object[] values)
	{
		int length = values.length;
		COUNT = length;
		ENTRIES = new Entry[length];

		for (int i = 0; i < length; i++) ENTRIES[i] = new Entry(i, values[i]);
	}


	//
	public int count()
	{
		return COUNT;
	}


	void send(PreparedStatement statement, Object[] values, TrafficCounter counter)
		throws Exception
	{
		for (int i = 0; i < COUNT; i++) ENTRIES[i].SETTER.set(statement, values[i], counter);
	}


	//
	public static Entry entry(int number, @NullAllow Object parameter)
	{
		return new Entry(number, parameter);
	}


	public static Entry entry(int number, ADatabaseField field)
	{
		return new Entry(number, field);
	}


	public static Entry entry(int number, ADatabaseField field, String content)
	{
		return new Entry(number, field, content);
	}


	private static ISetter setter(int column, @NullAllow Object parameter)
	{
		if (parameter == null)
		{
			int index = column + 1;

			return (statement, value, counter) -> statement.setNull(index, Types.NULL);
		}
		else if (parameter instanceof IFactoryObject)
		{
			IDatabaseFactory factory = ((IFactoryObject) parameter).factories()
				.getDatabaseFactory();

			return setter(column, factory);
		}
		else
		{
			DatabaseType type = DatabaseType.relation(parameter.getClass());

			return setter(column, type);
		}
	}


	private static ISetter setter(int column, ADatabaseField field)
	{
		if (field instanceof AFactoryObjectDatabaseField)
		{
			IDatabaseFactory factory = field.factories()
				.getDatabaseFactory();

			return setter(column, factory);
		}
		else
		{
			DatabaseType type = field.type();

			return setter(column, type);
		}
	}


	private static ISetter setter(int column, DatabaseType type)
	{
		int index = column + 1;

		switch (type)
		{
			case BOOLEAN:
			{
				return (ISetter<Boolean>) (statement, value, counter) ->
				{
					if (value != null)
					{
						statement.setBoolean(index, value);
						counter.update(Primitives.BOOLEAN.DIMENSION);
					}
					else
					{
						statement.setNull(index, Types.NULL);
					}
				};
			}

			case SHORT:
			{
				return (ISetter<Short>) (statement, value, counter) ->
				{
					if (value != null)
					{
						statement.setShort(index, value);
						counter.update(Primitives.SHORT.DIMENSION);
					}
					else
					{
						statement.setNull(index, Types.NULL);
					}
				};
			}

			case INT:
			{
				return (ISetter<Integer>) (statement, value, counter) ->
				{
					if (value != null)
					{
						statement.setInt(index, value);
						counter.update(Primitives.INT.DIMENSION);
					}
					else
					{
						statement.setNull(index, Types.NULL);
					}
				};
			}

			case LONG:
			{
				return (ISetter<Long>) (statement, value, counter) ->
				{
					if (value != null)
					{
						statement.setLong(index, value);
						counter.update(Primitives.LONG.DIMENSION);
					}
					else
					{
						statement.setNull(index, Types.NULL);
					}
				};
			}

			case FLOAT:
			{
				return (ISetter<Float>) (statement, value, counter) ->
				{
					if (value != null)
					{
						statement.setFloat(index, value);
						counter.update(Primitives.FLOAT.DIMENSION);
					}
					else
					{
						statement.setNull(index, Types.NULL);
					}
				};
			}

			case DOUBLE:
			{
				return (ISetter<Double>) (statement, value, counter) ->
				{
					if (value != null)
					{
						statement.setDouble(index, value);
						counter.update(Primitives.DOUBLE.DIMENSION);
					}
					else
					{
						statement.setNull(index, Types.NULL);
					}
				};
			}

			case STRING:
			{
				return (ISetter<String>) (statement, value, counter) ->
				{
					if (value != null)
					{
						statement.setString(index, value);
						counter.update(value.length() * Primitives.CHAR.DIMENSION);
					}
					else
					{
						statement.setNull(index, Types.NULL);
					}
				};
			}

			case OCTETS:
			case BYTES:
			{
				return (ISetter<byte[]>) (statement, value, counter) ->
				{
					if (value != null)
					{
						statement.setBytes(index, value);
						counter.update(value.length);
					}
					else
					{
						statement.setNull(index, Types.NULL);
					}
				};
			}

			case TIMESTAMP:
			{
				return (ISetter<Timestamp>) (statement, value, counter) ->
				{
					if (value != null)
					{
						statement.setTimestamp(index, value);
						counter.update(TimestampUtil.DIMENSION);
					}
					else
					{
						statement.setNull(index, Types.NULL);
					}
				};
			}

			default:
			{
				return (statement, value, counter) ->
				{
					if (value != null)
					{
						statement.setObject(index, value);
						counter.update(0);
					}
					else
					{
						statement.setNull(index, Types.NULL);
					}
				};
			}
		}
	}


	private static ISetter setter(int column, IDatabaseFactory factory)
	{
		int index = column + 1;

		switch (factory.getDatabaseType())
		{
			case BOOLEAN:
			{
				return (statement, value, counter) ->
				{
					Object object = factory.toDatabase(value);

					if (object != null)
					{
						statement.setBoolean(index, (Boolean) object);
						counter.update(Primitives.BOOLEAN.DIMENSION);
					}
					else
					{
						statement.setNull(index, Types.NULL);
					}
				};
			}

			case SHORT:
			{
				return (statement, value, counter) ->
				{
					Object object = factory.toDatabase(value);

					if (object != null)
					{
						statement.setShort(index, (Short) object);
						counter.update(Primitives.SHORT.DIMENSION);
					}
					else
					{
						statement.setNull(index, Types.NULL);
					}
				};
			}

			case INT:
			{
				return (statement, value, counter) ->
				{
					Object object = factory.toDatabase(value);

					if (object != null)
					{
						statement.setInt(index, (Integer) object);
						counter.update(Primitives.INT.DIMENSION);
					}
					else
					{
						statement.setNull(index, Types.NULL);
					}
				};
			}

			case LONG:
			{
				return (statement, value, counter) ->
				{
					Object object = factory.toDatabase(value);

					if (object != null)
					{
						statement.setLong(index, (Long) object);
						counter.update(Primitives.LONG.DIMENSION);
					}
					else
					{
						statement.setNull(index, Types.NULL);
					}
				};
			}

			case FLOAT:
			{
				return (statement, value, counter) ->
				{
					Object object = factory.toDatabase(value);

					if (object != null)
					{
						statement.setFloat(index, (Float) object);
						counter.update(Primitives.FLOAT.DIMENSION);
					}
					else
					{
						statement.setNull(index, Types.NULL);
					}
				};
			}

			case DOUBLE:
			{
				return (statement, value, counter) ->
				{
					Object object = factory.toDatabase(value);

					if (object != null)
					{
						statement.setDouble(index, (Double) object);
						counter.update(Primitives.DOUBLE.DIMENSION);
					}
					else
					{
						statement.setNull(index, Types.NULL);
					}
				};
			}

			case STRING:
			{
				return (statement, value, counter) ->
				{
					Object object = factory.toDatabase(value);

					if (object != null)
					{
						String s = (String) object;
						statement.setString(index, s);
						counter.update(s.length() * Primitives.CHAR.DIMENSION);
					}
					else
					{
						statement.setNull(index, Types.NULL);
					}
				};
			}

			case OCTETS:
			case BYTES:
			{
				return (statement, value, counter) ->
				{
					Object object = factory.toDatabase(value);

					if (object != null)
					{
						byte[] array = (byte[]) object;
						statement.setBytes(index, array);
						counter.update(array.length);
					}
					else
					{
						statement.setNull(index, Types.NULL);
					}
				};
			}

			case TIMESTAMP:
			{
				return (statement, value, counter) ->
				{
					Object object = factory.toDatabase(value);

					if (object != null)
					{
						statement.setTimestamp(index, (Timestamp) object);
						counter.update(TimestampUtil.DIMENSION);
					}
					else
					{
						statement.setNull(index, Types.NULL);
					}
				};
			}

			default:
			{
				return (statement, value, counter) ->
				{
					Object object = factory.toDatabase(value);

					if (object != null)
					{
						statement.setObject(index, object);
						counter.update(0);
					}
					else
					{
						statement.setNull(index, Types.NULL);
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

		final private ISetter SETTER;

		//
		private Entry(int number, @NullAllow Object parameter)
		{
			NUMBER = number;
			CONTENT = "";
			SETTER = setter(number, parameter);
		}

		private Entry(int number, ADatabaseField field)
		{
			NUMBER = number;
			CONTENT = field.ALIAS;
			SETTER = setter(number, field);
		}

		private Entry(int number, ADatabaseField field, String content)
		{
			NUMBER = number;
			CONTENT = content;
			SETTER = setter(number, field);
		}

		//
		@Override
		public String toString()
		{
			throw new UnsupportedOperationException(); // !
		}
	}


	@FunctionalInterface
	private interface ISetter<T>
	{
		void set(PreparedStatement statement, T value, TrafficCounter counter) throws Exception;
	}


	//
	final static SqlSenders BLANK = new SqlSenders(0);

}
