package net.javenture.framework.factory;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.util.BooleanUtil;
import net.javenture.framework.hash.Murmur3Hash;


/*
	2019/09/16
 */
@FunctionalInterface
public interface IMurmur3HashFactory<T>
	extends IFactory<T>
{
	//
	FactoryType<IMurmur3HashFactory> TYPE = new FactoryType<>("MURMUR3HASH");


	//
	void update(@NullAllow T object, @NullDisallow Murmur3Hash destination);


	//
	@Override
	default FactoryType<IMurmur3HashFactory> type()
	{
		return TYPE;
	}


	default Murmur3Hash hash(@NullAllow T object)
	{
		Murmur3Hash result = new Murmur3Hash();
		update(object, result);

		return result;
	}


	default void update(@NullAllow T object, int from, int to, @NullDisallow Murmur3Hash destination)
	{
		throw new UnsupportedOperationException();
	}


	//
	static <T> boolean updateNullHeader(@NullAllow T object, @NullDisallow Murmur3Hash destination) // ! static
	{
		if (object != null)
		{
			destination.update(BooleanUtil.True.BYTE);

			return true;
		}
		else
		{
			destination.update(BooleanUtil.False.BYTE);

			return false;
		}
	}

}
