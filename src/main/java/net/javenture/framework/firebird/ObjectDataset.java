package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.factory.IFactoryObject;
import net.javenture.framework.util.Chain;
import net.javenture.framework.util.Validator;

import java.sql.Timestamp;
import java.util.Iterator;


/*
	2019/12/29
 */
final class ObjectDataset
	implements IDataset
{
	//
	final private static int COLUMNS = 1;
	final private static UnknownRow ROW_UNKNOWN = new UnknownRow();

	final private static Iterator<ObjectRow> ITERATOR_UNKNOWN = new Iterator<>()
	{
		@Override
		public boolean hasNext()
		{
			return false;
		}

		@Override
		public ObjectRow next()
		{
			throw new IllegalStateException();
		}
	};


	//
	final private @NullAllow DatabaseType[] TYPES;
	final private @NullAllow String[] LABELS;
	final private Chain<ObjectRow> ROWS;

	private IRow row;
	private Iterator<ObjectRow> iterator;


	//
	@SuppressWarnings("AssignmentOrReturnOfFieldWithMutableType")
	ObjectDataset(@NullAllow DatabaseType[] types, @NullAllow String[] labels)
	{
		TYPES = types;
		LABELS = labels;
		ROWS = new Chain<>();
		row = ROW_UNKNOWN;
		iterator = ITERATOR_UNKNOWN;
	}


	@SuppressWarnings("AssignmentOrReturnOfFieldWithMutableType")
	ObjectDataset(@NullAllow DatabaseType[] types, @NullAllow String[] labels, Chain<ObjectRow> rows)
	{
		TYPES = types;
		LABELS = labels;
		ROWS = rows;
		row = ROW_UNKNOWN;
		iterator = rows.iterator();
	}


	//
	@Override
	public int columns()
	{
		return COLUMNS;
	}


	@Override
	public @NullDisallow DatabaseType type(int column)
	{
		if (TYPES != null) return TYPES[column];
		else throw new IllegalStateException("metadata not available");
	}


	@Override
	public @NullDisallow String label(int column)
	{
		if (LABELS != null) return LABELS[column];
		else throw new IllegalStateException("metadata not available");
	}


	@Override
	public int rows()
	{
		return ROWS.size();
	}


	@Override
	public boolean exists()
	{
		return ROWS.exists();
	}


	@Override
	public boolean first()
	{
		rewind();

		return next();
	}


	@Override
	public boolean next()
	{
		if (iterator.hasNext())
		{
			row = iterator.next();

			return true;
		}
		else
		{
			row = ROW_UNKNOWN;

			return false;
		}
	}


	@Override
	public void rewind()
	{
		iterator = ROWS.iterator();
		row = ROW_UNKNOWN;
	}


	@Override
	public @NullAllow Object get(int column)
	{
		return row.get(column);
	}


	@Override
	public @NullAllow <V> V get(ADatabaseField<V> field)
	{
		return row.get(field);
	}


	@Override
	public @NullAllow <V> V get(ADatabaseField<V> field, @NullAllow V init)
	{
		return row.get(field, init);
	}


	@Override
	public @NullAllow <V> V get(int column, ADatabaseField<V> field)
	{
		return row.get(column, field);
	}


	@Override
	public @NullAllow <V> V get(int column, ADatabaseField<V> field, @NullAllow V init)
	{
		return row.get(column, field, init);
	}


	@Override
	public boolean isNull(int column)
	{
		return row.isNull(column);
	}


	@Override
	public @NullAllow Boolean getBoolean(int column)
	{
		return row.getBoolean(column);
	}


	@Override
	public boolean getBoolean(int column, boolean init)
	{
		return row.getBoolean(column, init);
	}


	@Override
	public @NullAllow Short getShort(int column)
	{
		return row.getShort(column);
	}


	@Override
	public short getShort(int column, short init)
	{
		return row.getShort(column, init);
	}


	@Override
	public @NullAllow Integer getInt(int column)
	{
		return row.getInt(column);
	}


	@Override
	public int getInt(int column, int init)
	{
		return row.getInt(column, init);
	}


	@Override
	public @NullAllow Long getLong(int column)
	{
		return row.getLong(column);
	}


	@Override
	public long getLong(int column, long init)
	{
		return row.getLong(column, init);
	}


	@Override
	public @NullAllow Float getFloat(int column)
	{
		return row.getFloat(column);
	}


	@Override
	public float getFloat(int column, float init)
	{
		return row.getFloat(column, init);
	}


	@Override
	public @NullAllow Double getDouble(int column)
	{
		return row.getDouble(column);
	}


	@Override
	public double getDouble(int column, double init)
	{
		return row.getDouble(column, init);
	}


	@Override
	public @NullAllow String getString(int column)
	{
		return row.getString(column);
	}


	@Override
	public @NullAllow String getString(int column, @NullAllow String init)
	{
		return row.getString(column, init);
	}


	@Override
	public @NullAllow byte[] getBytes(int column)
	{
		return row.getBytes(column);
	}


	@Override
	public @NullAllow byte[] getBytes(int column, @NullAllow byte[] init)
	{
		return row.getBytes(column, init);
	}


	@Override
	public @NullAllow Timestamp getTimestamp(int column)
	{
		return row.getTimestamp(column);
	}


	@Override
	public @NullAllow Timestamp getTimestamp(int column, @NullAllow Timestamp init)
	{
		return row.getTimestamp(column, init);
	}


	@Override
	public @NullAllow <T extends IFactoryObject<T>> T getFactoryObject(int column, @NullDisallow Factories<T> factories)
		throws Exception
	{
		return row.getFactoryObject(column, factories);
	}


	@Override
	public @NullAllow <T extends IFactoryObject<T>> T getFactoryObject(int column, @NullDisallow Factories<T> factories, @NullAllow T init)
	{
		return row.getFactoryObject(column, factories, init);
	}


	@Override
	public void toArray(Object[] destination)
	{
		row.toArray(destination);
	}


	@Override
	public void process(IRowSqlProcessor postprocessor)
		throws Exception
	{
		for (ObjectRow row : ROWS) postprocessor.process(row.OBJECT);
	}


	@Override
	public void process(int column, IColumnSqlProcessor postprocessor)
		throws Exception
	{
		Validator.argument(column == 0, "[column] (", column, ") != 0");

		for (ObjectRow row : ROWS) postprocessor.process(row.OBJECT);
	}


	@Override
	public void process(IColumnSqlProcessor... postprocessors)
		throws Exception
	{
		Validator.argument(postprocessors.length == COLUMNS, "[postprocessor.length] (", postprocessors.length, ") != [COLUMN] (", COLUMNS, ")");
		IColumnSqlProcessor processor = postprocessors[0];

		for (ObjectRow row : ROWS) processor.process(row.OBJECT);
	}

}
