package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.factory.IFactoryObject;

import java.sql.Timestamp;


/*
	2019/12/29
 */
final class BlankDataset
	implements IDataset
{
	//
	private BlankDataset()
	{
	}


	//
	@Override
	public int columns()
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public DatabaseType type(int column)
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public String label(int column)
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public int rows()
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public boolean exists()
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public boolean first()
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public boolean next()
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public void rewind()
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public @NullAllow Object get(int column)
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public @NullAllow <V> V get(ADatabaseField<V> field)
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public @NullAllow <V> V get(ADatabaseField<V> field, V init)
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public @NullAllow <V> V get(int column, ADatabaseField<V> field)
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public @NullAllow <V> V get(int column, ADatabaseField<V> field, V init)
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public boolean isNull(int column)
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public @NullAllow Boolean getBoolean(int column)
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public boolean getBoolean(int column, boolean init)
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public @NullAllow Short getShort(int column)
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public short getShort(int column, short init)
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public @NullAllow Integer getInt(int column)
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public int getInt(int column, int init)
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public @NullAllow Long getLong(int column)
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public long getLong(int column, long init)
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public @NullAllow Float getFloat(int column)
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public float getFloat(int column, float init)
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public @NullAllow Double getDouble(int column)
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public double getDouble(int column, double init)
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public @NullAllow String getString(int column)
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public @NullAllow String getString(int column, @NullAllow String init)
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public @NullAllow byte[] getBytes(int column)
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public @NullAllow byte[] getBytes(int column, @NullAllow byte[] init)
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public @NullAllow
	Timestamp getTimestamp(int column)
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public @NullAllow Timestamp getTimestamp(int column, @NullAllow Timestamp init)
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public @NullAllow <T extends IFactoryObject<T>> T getFactoryObject(int column, @NullDisallow Factories<T> factories)
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public @NullAllow <T extends IFactoryObject<T>> T getFactoryObject(int column, @NullDisallow Factories<T> factories, @NullAllow T init)
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public void toArray(Object[] destination)
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public void process(IRowSqlProcessor postprocessor)
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public void process(int column, IColumnSqlProcessor postprocessor)
	{
		throw new UnsupportedOperationException();
	}


	@Override
	public void process(IColumnSqlProcessor... postprocessors)
	{
		throw new UnsupportedOperationException();
	}


	//
	final static BlankDataset INSTANCE = new BlankDataset();

}
