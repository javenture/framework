package net.javenture.framework.factory;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.util.BooleanUtil;
import net.javenture.framework.util.ByteInputStream;
import net.javenture.framework.util.ByteOutputStream;
import net.javenture.framework.util.IntUtil;

import java.io.InputStream;
import java.io.OutputStream;


/*
	2019/12/06
 */
public interface IByteFactory<T>
	extends IFactory<T>
{
	//
	FactoryType<IByteFactory> TYPE = new FactoryType<>("BYTE");


	//
	void toStream(@NullAllow T object, OutputStream destination) throws Exception;
	@NullAllow T fromStream(InputStream source) throws Exception;


	//
	@Override
	default FactoryType<IByteFactory> type()
	{
		return TYPE;
	}


	default void toStream(@NullAllow T object, int from, int to, OutputStream destination)
		throws Exception
	{
		throw new UnsupportedOperationException();
	}


	default byte[] toBytes(@NullAllow T object)
		throws Exception
	{
		try (ByteOutputStream destination = new ByteOutputStream())
		{
			toStream(object, destination);

			return destination.toBytes();
		}
	}


	default @NullAllow T fromBytes(byte[] bytes)
		throws Exception
	{
		try (ByteInputStream source = new ByteInputStream(bytes))
		{
			return fromStream(source);
		}
	}


	//
	static boolean getNullHeader(InputStream source) // ! static
		throws Exception
	{
		int i = source.read(); // !

		if (i == BooleanUtil.True.INT) return true;
		else if (i == BooleanUtil.False.INT) return false;
		else throw new Exception("parse failed: " + i);
	}


	static <T> boolean setNullHeader(@NullAllow T object, OutputStream destination) // ! static
		throws Exception
	{
		if (object != null)
		{
			destination.write(BooleanUtil.True.INT); // !

			return true;
		}
		else
		{
			destination.write(BooleanUtil.False.INT); // !

			return false;
		}
	}

}
