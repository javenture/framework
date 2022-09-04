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
final class ObjectRow
	implements IRow
{
	//
	final Object OBJECT;


	//
	ObjectRow(Object object)
	{
		OBJECT = object;
	}


	//
	@Override
	public @NullAllow Object get(int column)
	{
		return OBJECT;
	}


	@Override
	@SuppressWarnings("unchecked")
	public @NullAllow <V> V get(ADatabaseField<V> field)
	{
		return (V) OBJECT;
	}


	@Override
	@SuppressWarnings("unchecked")
	public @NullAllow <V> V get(ADatabaseField<V> field, @NullAllow V init)
	{
		if (OBJECT == null) return null;
		else if (field.instance(OBJECT)) return (V) OBJECT;
		else return field.factories().getCopyFactory().copy(init);
	}


	@Override
	@SuppressWarnings("unchecked")
	public @NullAllow <V> V get(int column, ADatabaseField<V> field)
	{
		return (V) OBJECT;
	}


	@Override
	@SuppressWarnings("unchecked")
	public @NullAllow <V> V get(int column, ADatabaseField<V> field, @NullAllow V init)
	{
		if (OBJECT == null) return null;
		else if (field.instance(OBJECT)) return (V) OBJECT;
		else return field.factories().getCopyFactory().copy(init);
	}


	@Override
	public boolean isNull(int column)
	{
		return OBJECT == null;
	}


	@Override
	public @NullAllow Boolean getBoolean(int column)
	{
		return (Boolean) OBJECT;
	}


	@Override
	public boolean getBoolean(int column, boolean init)
	{
		return OBJECT instanceof Boolean ? (boolean) OBJECT : init;
	}


	@Override
	public @NullAllow Boolean getBoolean(int column, @NullAllow Boolean init)
	{
		if (OBJECT == null) return null;
		else if (OBJECT instanceof Boolean) return (Boolean) OBJECT;
		else return init;
	}


	@Override
	public @NullAllow Short getShort(int column)
	{
		return (Short) OBJECT;
	}


	@Override
	public short getShort(int column, short init)
	{
		return OBJECT instanceof Short ? (short) OBJECT : init;
	}


	@Override
	public @NullAllow Short getShort(int column, @NullAllow Short init)
	{
		if (OBJECT == null) return null;
		else if (OBJECT instanceof Short) return (Short) OBJECT;
		else return init;
	}


	@Override
	public @NullAllow Integer getInt(int column)
	{
		return (Integer) OBJECT;
	}


	@Override
	public int getInt(int column, int init)
	{
		return OBJECT instanceof Integer ? (int) OBJECT : init;
	}


	@Override
	public @NullAllow Integer getInt(int column, @NullAllow Integer init)
	{
		if (OBJECT == null) return null;
		else if (OBJECT instanceof Integer) return (Integer) OBJECT;
		else return init;
	}


	@Override
	public @NullAllow Long getLong(int column)
	{
		return (Long) OBJECT;
	}


	@Override
	public long getLong(int column, long init)
	{
		return OBJECT instanceof Long ? (long) OBJECT : init;
	}


	@Override
	public @NullAllow Long getLong(int column, @NullAllow Long init)
	{
		if (OBJECT == null) return null;
		else if (OBJECT instanceof Long) return (Long) OBJECT;
		else return init;
	}


	@Override
	public @NullAllow Float getFloat(int column)
	{
		return (Float) OBJECT;
	}


	@Override
	public float getFloat(int column, float init)
	{
		return OBJECT instanceof Float ? (float) OBJECT : init;
	}


	@Override
	public @NullAllow Float getFloat(int column, @NullAllow Float init)
	{
		if (OBJECT == null) return null;
		else if (OBJECT instanceof Float) return (Float) OBJECT;
		else return init;
	}


	@Override
	public @NullAllow Double getDouble(int column)
	{
		return (Double) OBJECT;
	}


	@Override
	public double getDouble(int column, double init)
	{
		return OBJECT instanceof Double ? (double) OBJECT : init;
	}


	@Override
	public @NullAllow Double getDouble(int column, @NullAllow Double init)
	{
		if (OBJECT == null) return null;
		else if (OBJECT instanceof Double) return (Double) OBJECT;
		else return init;
	}



	@Override
	public @NullAllow String getString(int column)
	{
		return (String) OBJECT;
	}


	@Override
	public @NullAllow String getString(int column, @NullAllow String init)
	{
		if (OBJECT == null) return null;
		else if (OBJECT instanceof String) return (String) OBJECT;
		else return init;
	}


	@Override
	public @NullAllow byte[] getBytes(int column)
	{
		return (byte[]) OBJECT;
	}


	@Override
	public @NullAllow byte[] getBytes(int column, @NullAllow byte[] init)
	{
		if (OBJECT == null) return null;
		else if (OBJECT instanceof byte[]) return (byte[]) OBJECT;
		else return ByteArrayUtil.FACTORY_COPY.copy(init);
	}


	@Override
	public @NullAllow Timestamp getTimestamp(int column)
	{
		return (Timestamp) OBJECT;
	}


	@Override
	public @NullAllow Timestamp getTimestamp(int column, @NullAllow Timestamp init)
	{
		if (OBJECT == null) return null;
		else if (OBJECT instanceof Timestamp) return (Timestamp) OBJECT;
		else return TimestampUtil.FACTORY_COPY.copy(init);
	}


	@Override
	public @NullAllow <T extends IFactoryObject<T>> T getFactoryObject(int column, @NullDisallow Factories<T> factories)
		throws Exception
	{
		return OBJECT == null
			? null
			: factories.getDatabaseFactory()
				.fromDatabase(OBJECT);
	}


	@Override
	public @NullAllow <T extends IFactoryObject<T>> T getFactoryObject(int column, @NullDisallow Factories<T> factories, @NullAllow T init)
	{
		if (OBJECT == null) return null;

		try
		{
			return factories.getDatabaseFactory()
				.fromDatabase(OBJECT);
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
		destination[0] = OBJECT;
	}

}
