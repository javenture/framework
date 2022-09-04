package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.factory.IFactoryObject;
import net.javenture.framework.util.ByteArrayUtil;
import net.javenture.framework.util.TimestampUtil;

import java.sql.Timestamp;


/*
	2020/01/05
 */
final class ArrayRow
	implements IRow
{
	//
	final Object[] ARRAY;


	//
	@SuppressWarnings("AssignmentOrReturnOfFieldWithMutableType")
	ArrayRow(Object[] array)
	{
		ARRAY = array;
	}


	//
	@Override
	public @NullAllow Object get(int column)
	{
		return ARRAY[column];
	}


	@Override
	@SuppressWarnings("unchecked")
	public @NullAllow <V> V get(ADatabaseField<V> field)
	{
		return (V) get(field.NUMBER);
	}


	@Override
	@SuppressWarnings("unchecked")
	public @NullAllow <V> V get(ADatabaseField<V> field, @NullAllow V init)
	{
		Object object = get(field.NUMBER);

		if (object == null) return null;
		else if (field.instance(object)) return (V) object;
		else return field.factories().getCopyFactory().copy(init);
	}


	@Override
	@SuppressWarnings("unchecked")
	public @NullAllow <V> V get(int column, ADatabaseField<V> field)
	{
		return (V) get(column);
	}


	@Override
	@SuppressWarnings("unchecked")
	public @NullAllow <V> V get(int column, ADatabaseField<V> field, @NullAllow V init)
	{
		Object object = get(column);

		if (object == null) return null;
		else if (field.instance(object)) return (V) object;
		else return field.factories().getCopyFactory().copy(init);
	}


	@Override
	public boolean isNull(int column)
	{
		return get(column) == null;
	}


	@Override
	public @NullAllow Boolean getBoolean(int column)
	{
		Object object = get(column);

		return (Boolean) object;
	}


	@Override
	public boolean getBoolean(int column, boolean init)
	{
		Object object = get(column);

		return object instanceof Boolean ? (boolean) object : init;
	}


	@Override
	public @NullAllow Boolean getBoolean(int column, @NullAllow Boolean init)
	{
		Object object = get(column);

		if (object == null) return null;
		else if (object instanceof Boolean) return (Boolean) object;
		else return init;
	}


	@Override
	public @NullAllow Short getShort(int column)
	{
		Object object = get(column);

		return (Short) object;
	}


	@Override
	public short getShort(int column, short init)
	{
		Object object = get(column);

		return object instanceof Short ? (short) object : init;
	}


	@Override
	public @NullAllow Short getShort(int column, @NullAllow Short init)
	{
		Object object = get(column);

		if (object == null) return null;
		else if (object instanceof Short) return (Short) object;
		else return init;
	}


	@Override
	public @NullAllow Integer getInt(int column)
	{
		Object object = get(column);

		return (Integer) object;
	}


	@Override
	public int getInt(int column, int init)
	{
		Object object = get(column);

		return object instanceof Integer ? (int) object : init;
	}


	@Override
	public @NullAllow Integer getInt(int column, @NullAllow Integer init)
	{
		Object object = get(column);

		if (object == null) return null;
		else if (object instanceof Integer) return (Integer) object;
		else return init;
	}


	@Override
	public @NullAllow Long getLong(int column)
	{
		Object object = get(column);

		return (Long) object;
	}


	@Override
	public long getLong(int column, long init)
	{
		Object object = get(column);

		return object instanceof Long ? (long) object : init;
	}


	@Override
	public @NullAllow Long getLong(int column, @NullAllow Long init)
	{
		Object object = get(column);

		if (object == null) return null;
		else if (object instanceof Long) return (Long) object;
		else return init;
	}


	@Override
	public @NullAllow Float getFloat(int column)
	{
		Object object = get(column);

		return (Float) object;
	}


	@Override
	public float getFloat(int column, float init)
	{
		Object object = get(column);

		return object instanceof Float ? (float) object : init;
	}


	@Override
	public @NullAllow Float getFloat(int column, @NullAllow Float init)
	{
		Object object = get(column);

		if (object == null) return null;
		else if (object instanceof Float) return (Float) object;
		else return init;
	}


	@Override
	public @NullAllow Double getDouble(int column)
	{
		Object object = get(column);

		return (Double) object;
	}


	@Override
	public double getDouble(int column, double init)
	{
		Object object = get(column);

		return object instanceof Double ? (double) object : init;
	}


	@Override
	public @NullAllow Double getDouble(int column, @NullAllow Double init)
	{
		Object object = get(column);

		if (object == null) return null;
		else if (object instanceof Double) return (Double) object;
		else return init;
	}


	@Override
	public @NullAllow String getString(int column)
	{
		Object object = get(column);

		return (String) object;
	}


	@Override
	public @NullAllow String getString(int column, @NullAllow String init)
	{
		Object object = get(column);

		if (object == null) return null;
		else if (object instanceof String) return (String) object;
		else return init;
	}


	@Override
	public @NullAllow byte[] getBytes(int column)
	{
		Object object = get(column);

		return (byte[]) object;
	}


	@Override
	public @NullAllow byte[] getBytes(int column, @NullAllow byte[] init)
	{
		Object object = get(column);

		if (object == null) return null;
		else if (object instanceof byte[]) return (byte[]) object;
		else return ByteArrayUtil.FACTORY_COPY.copy(init);
	}


	@Override
	public @NullAllow Timestamp getTimestamp(int column)
	{
		Object object = get(column);

		return (Timestamp) object;
	}


	@Override
	public @NullAllow Timestamp getTimestamp(int column, @NullAllow Timestamp init)
	{
		Object object = get(column);

		if (object == null) return null;
		else if (object instanceof Timestamp) return (Timestamp) object;
		else return TimestampUtil.FACTORY_COPY.copy(init);
	}


	@Override
	public @NullAllow <T extends IFactoryObject<T>> T getFactoryObject(int column, @NullDisallow Factories<T> factories)
		throws Exception
	{
		Object object = get(column);

		return object == null
			? null
			: factories.getDatabaseFactory()
				.fromDatabase(object);
	}


	@Override
	public @NullAllow <T extends IFactoryObject<T>> T getFactoryObject(int column, @NullDisallow Factories<T> factories, @NullAllow T init)
	{
		Object object = get(column);

		if (object == null) return null;

		try
		{
			return factories.getDatabaseFactory()
				.fromDatabase(object);
		}
		catch (Exception ignore)
		{
		}

		return factories.getCopyFactory()
			.copy(init);
	}


	@Override
	public void toArray(Object[] destination)
	{
		System.arraycopy(ARRAY, 0, destination, 0, ARRAY.length);
	}

}
