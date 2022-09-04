package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.annotation.SingleThread;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.factory.IFactoryObject;

import java.sql.Timestamp;


/*
	2019/12/29
 */
@SingleThread
public interface IDataset
{
	// XXX: IByteFactory ?


	//
	int columns();
	DatabaseType type(int column);
	String label(int column);
	int rows();
	boolean exists();
	boolean first();
	boolean next();
	void rewind();

	@NullAllow Object get(int column);

	@NullAllow <V> V get(ADatabaseField<V> field);
	@NullAllow <V> V get(ADatabaseField<V> field, @NullAllow V init);
	@NullAllow <V> V get(int column, ADatabaseField<V> field);
	@NullAllow <V> V get(int column, ADatabaseField<V> field, @NullAllow V init);

	boolean isNull(int column);

	@NullAllow Boolean getBoolean(int column);
	boolean getBoolean(int column, boolean init);

	@NullAllow Short getShort(int column);
	short getShort(int column, short init);

	@NullAllow Integer getInt(int column);
	int getInt(int column, int init);

	@NullAllow Long getLong(int column);
	long getLong(int column, long init);

	@NullAllow Float getFloat(int column);
	float getFloat(int column, float init);

	@NullAllow Double getDouble(int column);
	double getDouble(int column, double init);

	@NullAllow String getString(int column);
	@NullAllow String getString(int column, @NullAllow String init);

	@NullAllow byte[] getBytes(int column);
	@NullAllow byte[] getBytes(int column, @NullAllow byte[] init);

	@NullAllow Timestamp getTimestamp(int column);
	@NullAllow Timestamp getTimestamp(int column, @NullAllow Timestamp init);

	@NullAllow <T extends IFactoryObject<T>> T getFactoryObject(int column, @NullDisallow Factories<T> factories) throws Exception; // ! <T extends IFactoryObject<T>>
	@NullAllow <T extends IFactoryObject<T>> T getFactoryObject(int column, @NullDisallow Factories<T> factories, @NullAllow T init); // ! <T extends IFactoryObject<T>>

	void toArray(@NullDisallow Object[] destination);

	void process(IRowSqlProcessor postprocessor) throws Exception;
	void process(int column, IColumnSqlProcessor postprocessor) throws Exception;
	void process(IColumnSqlProcessor... postprocessors) throws Exception;

}
