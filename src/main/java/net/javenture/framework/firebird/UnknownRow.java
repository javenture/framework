package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.factory.IFactoryObject;

import java.sql.Timestamp;


/*
	2020/01/05
 */
final class UnknownRow
	implements IRow
{
	//
	UnknownRow()
	{
	}


	//
	@Override
	public @NullAllow Object get(int column)
	{
		throw new IllegalStateException("unknown row");
	}


	@Override
	public @NullAllow <V> V get(ADatabaseField<V> field)
	{
		throw new IllegalStateException("unknown row");
	}


	@Override
	public @NullAllow <V> V get(ADatabaseField<V> field, V init)
	{
		throw new IllegalStateException("unknown row");
	}


	@Override
	public @NullAllow <V> V get(int column, ADatabaseField<V> field)
	{
		throw new IllegalStateException("unknown row");
	}


	@Override
	public @NullAllow <V> V get(int column, ADatabaseField<V> field, V init)
	{
		throw new IllegalStateException("unknown row");
	}


	@Override
	public boolean isNull(int column)
	{
		throw new IllegalStateException("unknown row");
	}


	@Override
	public @NullAllow Boolean getBoolean(int column)
	{
		throw new IllegalStateException("unknown row");
	}


	@Override
	public boolean getBoolean(int column, boolean init)
	{
		throw new IllegalStateException("unknown row");
	}


	@Override
	public @NullAllow Boolean getBoolean(int column, @NullAllow Boolean init)
	{
		throw new IllegalStateException("unknown row");
	}


	@Override
	public @NullAllow Short getShort(int column)
	{
		throw new IllegalStateException("unknown row");
	}


	@Override
	public short getShort(int column, short init)
	{
		throw new IllegalStateException("unknown row");
	}


	@Override
	public @NullAllow Short getShort(int column, @NullAllow Short init)
	{
		throw new IllegalStateException("unknown row");
	}


	@Override
	public @NullAllow Integer getInt(int column)
	{
		throw new IllegalStateException("unknown row");
	}


	@Override
	public int getInt(int column, int init)
	{
		throw new IllegalStateException("unknown row");
	}


	@Override
	public @NullAllow Integer getInt(int column, @NullAllow Integer init)
	{
		throw new IllegalStateException("unknown row");
	}


	@Override
	public @NullAllow Long getLong(int column)
	{
		throw new IllegalStateException("unknown row");
	}


	@Override
	public long getLong(int column, long init)
	{
		throw new IllegalStateException("unknown row");
	}


	@Override
	public @NullAllow Long getLong(int column, @NullAllow Long init)
	{
		throw new IllegalStateException("unknown row");
	}


	@Override
	public @NullAllow Float getFloat(int column)
	{
		throw new IllegalStateException("unknown row");
	}


	@Override
	public float getFloat(int column, float init)
	{
		throw new IllegalStateException("unknown row");
	}


	@Override
	public @NullAllow Float getFloat(int column, @NullAllow Float init)
	{
		throw new IllegalStateException("unknown row");
	}


	@Override
	public @NullAllow Double getDouble(int column)
	{
		throw new IllegalStateException("unknown row");
	}


	@Override
	public double getDouble(int column, double init)
	{
		throw new IllegalStateException("unknown row");
	}


	@Override
	public @NullAllow Double getDouble(int column, @NullAllow Double init)
	{
		throw new IllegalStateException("unknown row");
	}


	@Override
	public @NullAllow String getString(int column)
	{
		throw new IllegalStateException("unknown row");
	}


	@Override
	public @NullAllow String getString(int column, @NullAllow String init)
	{
		throw new IllegalStateException("unknown row");
	}


	@Override
	public @NullAllow byte[] getBytes(int column)
	{
		throw new IllegalStateException("unknown row");
	}


	@Override
	public @NullAllow byte[] getBytes(int column, @NullAllow byte[] init)
	{
		throw new IllegalStateException("unknown row");
	}


	@Override
	public @NullAllow
	Timestamp getTimestamp(int column)
	{
		throw new IllegalStateException("unknown row");
	}


	@Override
	public @NullAllow Timestamp getTimestamp(int column, @NullAllow Timestamp init)
	{
		throw new IllegalStateException("unknown row");
	}


	@Override
	public @NullAllow <T extends IFactoryObject<T>> T getFactoryObject(int column, @NullDisallow Factories<T> factories)
	{
		throw new IllegalStateException("unknown row");
	}


	@Override
	public @NullAllow <T extends IFactoryObject<T>> T getFactoryObject(int column, @NullDisallow Factories<T> factories, @NullAllow T init)
	{
		throw new IllegalStateException("unknown row");
	}


	@Override
	public void toArray(Object[] destination)
	{
		throw new IllegalStateException("unknown row");
	}

}
