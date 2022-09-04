package net.javenture.framework.util;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.factory.IByteFactory;
import net.javenture.framework.factory.ICopyFactory;
import net.javenture.framework.factory.IMurmur3HashFactory;
import net.javenture.framework.hash.Murmur3Hash;

import java.io.InputStream;
import java.io.OutputStream;


/*
	2019/12/04
 */
final public class BooleanArrayUtil
{
	//
	final public static boolean[] BLANK = new boolean[0];
	final public static ICopyFactory<boolean[]> FACTORY_COPY = object -> object != null ? object.clone() : null;

	final public static IByteFactory<boolean[]> FACTORY_BYTE = new IByteFactory<>()
	{
		@Override
		public void toStream(@NullAllow boolean[] object, OutputStream destination)
			throws Exception
		{
			if (IByteFactory.setNullHeader(object, destination)) toStream0(object, 0, object.length, destination);
		}

		@Override
		public void toStream(@NullAllow boolean[] object, int from, int to, OutputStream destination)
			throws Exception
		{
			if (IByteFactory.setNullHeader(object, destination)) toStream0(object, from, to, destination);
		}

		private void toStream0(@NullDisallow boolean[] object, int from, int to, OutputStream destination)
			throws Exception
		{
			BitHolder holder = new BitHolder(object, from, to);
			BitHolder.FACTORY_BYTE.toStream(holder, destination);
		}

		@Override
		public @NullAllow boolean[] fromStream(InputStream source)
			throws Exception
		{
			if (IByteFactory.getNullHeader(source))
			{
				BitHolder holder = BitHolder.FACTORY_BYTE.fromStream(source);

				return holder.array();
			}
			else
			{
				return null;
			}
		}
	};

	final public static IMurmur3HashFactory<boolean[]> FACTORY_MURMUR3HASH = new IMurmur3HashFactory<>()
	{
		@Override
		public void update(@NullAllow boolean[] object, Murmur3Hash destination)
		{
			if (IMurmur3HashFactory.updateNullHeader(object, destination)) update0(object, 0, object.length, destination);
		}

		@Override
		public void update(@NullAllow boolean[] object, int from, int to, Murmur3Hash destination)
		{
			if (IMurmur3HashFactory.updateNullHeader(object, destination)) update0(object, from, to, destination);
		}

		private void update0(@NullDisallow boolean[] object, int from, int to, Murmur3Hash destination)
		{
			int length = to - from;
			IntUtil.murmur3hash(length, destination);

			if (length != 0)
			{
				for (int i = from; i < to; i++) BooleanUtil.murmur3hash(object[i], destination);
			}
		}
	};

	final public static Factories<boolean[]> FACTORIES = new Factories<>(FACTORY_COPY, FACTORY_BYTE, FACTORY_MURMUR3HASH);


	//
	private BooleanArrayUtil()
	{
	}


	//
	@SuppressWarnings("ArrayEquality")
	public static boolean equal(@NullAllow boolean[] object0, @NullAllow boolean[] object1)
	{
		if (object0 == object1) return true;
		else if (object0 == null || object1 == null) return false;

		return
			object0.length == object1.length
			&&
			equal(object0, object1, 0, object0.length);
	}


	public static boolean equal(@NullDisallow boolean[] object0, @NullDisallow boolean[] object1, int from, int to)
	{
		for (int i = from; i < to; i++)
		{
			if (object0[i] != object1[i]) return false;
		}

		return true;
	}

}
