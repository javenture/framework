/*
 * Copyright (C) 2014-2016 Markus Junginger, greenrobot (http://greenrobot.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package net.javenture.framework.hash;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.IFactoryObject;
import net.javenture.framework.hex.Hex;
import net.javenture.framework.util.BooleanUtil;
import net.javenture.framework.util.CharUtil;
import net.javenture.framework.util.DoubleUtil;
import net.javenture.framework.util.FloatUtil;
import net.javenture.framework.util.IConst;
import net.javenture.framework.util.IntUtil;
import net.javenture.framework.util.LongUtil;
import net.javenture.framework.util.ShortUtil;
import net.javenture.framework.util.Validator;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;


/**
 * Murmur3F (MurmurHash3_x64_128)
 * https://github.com/greenrobot/essentials/blob/master/java-essentials/src/main/java/org/greenrobot/essentials/hash/Murmur3F.java
 * 1c17ef1 on Sep 26, 2016
 *
 * 2021/06/02
 */
final public class Murmur3Hash
	implements IHash
{
	/*
		"1" = '1' = [ '1' ]
	 */

	//
	final private static long C1 = 0x87c37b91114253d5L;
	final private static long C2 = 0x4cf5ad432745937fL;


	//
	final private long SEED;

	private long h1;
	private long h2;
	private int count;
	private int partialPos;
	private long partialK1;
	private long partialK2;
	private boolean finished;
	private long finishedH1;
	private long finishedH2;


	//
	public Murmur3Hash()
	{
		SEED = 0;
	}


	public Murmur3Hash(int seed)
	{
		SEED = seed & 0xffffffffL; // unsigned 32 bit -> long
		h1 = h2 = SEED;
	}


	//
	@Override
	public String toString()
	{
		return getLittleEndianString();
	}


	@Override
	public void update(byte b)
	{
		finished = false;

		switch (partialPos)
		{
			case 0:  partialK1  = (0xff  & b); break;
			case 1:  partialK1 |= (0xff  & b) <<  8; break;
			case 2:  partialK1 |= (0xff  & b) << 16; break;
			case 3:  partialK1 |= (0xffL & b) << 24; break;
			case 4:  partialK1 |= (0xffL & b) << 32; break;
			case 5:  partialK1 |= (0xffL & b) << 40; break;
			case 6:  partialK1 |= (0xffL & b) << 48; break;
			case 7:  partialK1 |= (0xffL & b) << 56; break;
			case 8:  partialK2  = (0xff  & b); break;
			case 9:  partialK2 |= (0xff  & b) <<  8; break;
			case 10: partialK2 |= (0xff  & b) << 16; break;
			case 11: partialK2 |= (0xffL & b) << 24; break;
			case 12: partialK2 |= (0xffL & b) << 32; break;
			case 13: partialK2 |= (0xffL & b) << 40; break;
			case 14: partialK2 |= (0xffL & b) << 48; break;
			case 15: partialK2 |= (0xffL & b) << 56; break;
		}

		partialPos++;

		if (partialPos == 16)
		{
			applyKs(partialK1, partialK2);
			partialPos = 0;
		}

		count++;
	}


	@Override
	public void update(byte... array)
	{
		update(array, 0, array.length);
	}


	@Override
	public void update(byte[] array, int offset, int length)
	{
		if (length != 0)
		{
			finished = false;

			while (partialPos != 0 && length > 0)
			{
				update(array[offset]);
				offset++;
				length--;
			}

			int remainder = length & 0xf;
			int stop = offset + length - remainder;

			for (int i = offset; i < stop; i += 16)
			{
				long k1 = getLittleEndianLong(array, i);
				long k2 = getLittleEndianLong(array, i + 8);
				applyKs(k1, k2);
			}

			count += stop - offset;

			for (int i = 0; i < remainder; i++) update(array[stop + i]);
		}
	}


	public void update(boolean value)
	{
		BooleanUtil.murmur3hash(value, this);
	}


	public void update(char value)
	{
		CharUtil.murmur3hash(value, this);
	}


	public void update(short value)
	{
		ShortUtil.murmur3hash(value, this);
	}


	public void update(int value)
	{
		IntUtil.murmur3hash(value, this);
	}


	public void update(long value)
	{
		LongUtil.murmur3hash(value, this);
	}


	public void update(float value)
	{
		FloatUtil.murmur3hash(value, this);
	}


	public void update(double value)
	{
		DoubleUtil.murmur3hash(value, this);
	}


	public void update(@NullDisallow CharSequence sequence)
	{
		Validator.notNull(sequence, "[sequence]");

		//
		int length = sequence.length();

		for (int i = 0; i < length; i++) update(sequence.charAt(i));
	}


	public void update(@NullDisallow IConst value)
	{
		Validator.notNull(value, "[value]");

		update(value.id());
	}


	public void update(@NullDisallow IFactoryObject object)
	{
		Validator.notNull(object, "[object]");

		object.factories()
			.getMurmur3HashFactory()
			.update(object, this);
	}


	public void update(@NullDisallow File file)
		throws Exception
	{
		try (FileInputStream stream = new FileInputStream(file))
		{
			byte[] array = new byte[64 * 1024];

			while (true)
			{
				int count = stream.read(array);

				if (count == -1) break;
				else update(array, 0, count);
			}
		}
	}


	public void update(@NullDisallow InputStream stream)
		throws Exception
	{
		byte[] array = new byte[64 * 1024];

		while (true)
		{
			int count = stream.read(array);

			if (count == -1) break;
			else update(array, 0, count);
		}
	}


	@Override
	public String value()
	{
		checkFinished();

		return new Hex(getBytes())
			.toString(true);
	}


	public int getInt()
	{
		checkFinished();

		return (int) finishedH1;
	}


	public long getLong()
	{
		return getLowerLong();
	}


	/**
	 * Returns the lower 64 bits of the 128 bit hash (you can use just this value as a 64 bit hash).
	 */
	public long getLowerLong()
	{
		checkFinished();

		return finishedH1;
	}


	/**
	 * Returns the higher 64 bits of the 128 bit hash.
	 */
	public long getHigherLong()
	{
		checkFinished();

		return finishedH2;
	}


	public byte[] getBytes()
	{
		return getLittleEndianBytes();
	}


	public byte[] getLittleEndianBytes()
	{
		checkFinished();
		byte[] bytes = new byte[16];

		for (int i = 0; i < 8; i++) bytes[i] = (byte) ((finishedH1 >>> (i * 8)) & 0xff);

		for (int i = 0; i < 8; i++) bytes[8 + i] = (byte) ((finishedH2 >>> (i * 8)) & 0xff);

		return bytes;
	}


	public byte[] getBigEndianBytes()
	{
		checkFinished();
		byte[] bytes = new byte[16];

		for (int i = 0; i < 8; i++) bytes[i] = (byte) ((finishedH2 >>> (56 - i * 8)) & 0xff);

		for (int i = 0; i < 8; i++) bytes[8 + i] = (byte) ((finishedH1 >>> (56 - i * 8)) & 0xff);

		return bytes;
	}


	public String getLittleEndianString()
	{
		return Hex.toString(getLittleEndianBytes());
	}


	public String getBigEndianString()
	{
		return Hex.toString(getBigEndianBytes());
	}


	public void reset()
	{
		h1 = h2 = SEED;
		count = 0;
		partialPos = 0;
		finished = false;

		// The remainder is not really necessary, but looks nicer when debugging
		partialK1 = partialK2 = 0;
		finishedH1 = finishedH2 = 0;
	}


	private long getLittleEndianLong(byte[] array, int index)
	{
		return
			(array[index] & 0xff)
			|
			((array[index + 1] & 0xff) << 8)
			|
			((array[index + 2] & 0xff) << 16)
			|
			((array[index + 3] & 0xffL) << 24)
			|
			((array[index + 4] & 0xffL) << 32)
			|
			((array[index + 5] & 0xffL) << 40)
			|
			((array[index + 6] & 0xffL) << 48)
			|
			(((long) array[index + 7]) << 56);
	}


	private void applyKs(long k1, long k2)
	{
		k1 *= C1;
		k1 = Long.rotateLeft(k1, 31);
		k1 *= C2;
		h1 ^= k1;

		h1 = Long.rotateLeft(h1, 27);
		h1 += h2;
		h1 = h1 * 5 + 0x52dce729;

		k2 *= C2;
		k2 = Long.rotateLeft(k2, 33);
		k2 *= C1;
		h2 ^= k2;

		h2 = Long.rotateLeft(h2, 31);
		h2 += h1;
		h2 = h2 * 5 + 0x38495ab5;
	}


	private void checkFinished()
	{
		if (!finished)
		{
			finished = true;
			finishedH1 = h1;
			finishedH2 = h2;

			if (partialPos > 0)
			{
				if (partialPos > 8)
				{
					long k2 = partialK2 * C2;
					k2 = Long.rotateLeft(k2, 33);
					k2 *= C1;
					finishedH2 ^= k2;
				}

				long k1 = partialK1 * C1;
				k1 = Long.rotateLeft(k1, 31);
				k1 *= C2;
				finishedH1 ^= k1;
			}

			finishedH1 ^= count;
			finishedH2 ^= count;

			finishedH1 += finishedH2;
			finishedH2 += finishedH1;

			finishedH1 = fmix64(finishedH1);
			finishedH2 = fmix64(finishedH2);

			finishedH1 += finishedH2;
			finishedH2 += finishedH1;
		}
	}


	private long fmix64(long k)
	{
		k ^= k >>> 33;
		k *= 0xff51afd7ed558ccdL;
		k ^= k >>> 33;
		k *= 0xc4ceb9fe1a85ec53L;
		k ^= k >>> 33;

		return k;
	}

}