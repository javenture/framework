package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.util.ByteInputStream;
import net.javenture.framework.util.ByteOutputStream;
import net.javenture.framework.util.Chain;
import net.javenture.framework.util.IntUtil;
import net.javenture.framework.util.Validator;

import java.io.OutputStream;


/*
	2019/12/06
 */
final class ItemUtil
{
	//
	static void validate(@NullDisallow ADatabaseField[] fields, @NullDisallow Object[] values)
		throws Exception
	{
		for (ADatabaseField field : fields) field.validate(values[field.NUMBER]);
	}


	static @NullDisallow Chain<ADatabaseField> difference(@NullDisallow ADatabaseField[] fields, @NullDisallow Object[] values0, @NullDisallow Object[] values1)
	{
		Chain<ADatabaseField> result = new Chain<>();

		for (ADatabaseField field : fields)
		{
			int number = field.NUMBER;

			if (!field.equal(values0[number], values1[number])) result.add(field);
		}

		return result;
	}


	static @NullDisallow byte[] toBytes(@NullDisallow Object[] source, @NullDisallow ADatabaseField[] fields)
		throws Exception
	{
		try (ByteOutputStream stream = new ByteOutputStream())
		{
			toBytes(source, fields, stream);

			return stream.toBytes();
		}
	}


	static void toBytes(@NullDisallow Object[] source, @NullDisallow ADatabaseField[] fields, OutputStream destination)
		throws Exception
	{
		// count
		int count = fields.length;
		IntUtil.bytes(count, destination);

		// fields
		for (ADatabaseField field : fields)
		{
			Validator.condition(!field.KEY, "usage of KEY field disallowed");

			// number
			int number = field.NUMBER;
			IntUtil.bytes(number, destination);

			// value
			field.factories()
				.getByteFactory()
				.toStream(source[number], destination);
		}
	}


	static @NullDisallow byte[] toBytes(@NullDisallow Object[] source, @NullDisallow Chain<ADatabaseField> fields)
		throws Exception
	{
		try (ByteOutputStream stream = new ByteOutputStream())
		{
			toBytes(source, fields, stream);

			return stream.toBytes();
		}
	}


	static void toBytes(@NullDisallow Object[] source, @NullDisallow Chain<ADatabaseField> fields, OutputStream destination)
		throws Exception
	{
		// count
		int count = fields.size();
		IntUtil.bytes(count, destination);

		// fields
		for (ADatabaseField field : fields)
		{
			Validator.condition(!field.KEY, "usage of KEY field disallowed");

			// number
			int number = field.NUMBER;
			IntUtil.bytes(number, destination);

			// value
			field.factories()
				.getByteFactory()
				.toStream(source[number], destination);
		}
	}


	static void fromBytes(@NullDisallow byte[] array, @NullDisallow ADatabaseField[] fields, @NullDisallow Object[] destination)
		throws Exception
	{
		try (ByteInputStream stream = new ByteInputStream(array))
		{
			fromBytes(stream, fields, destination);
		}
	}


	static void fromBytes(@NullDisallow ByteInputStream source, @NullDisallow ADatabaseField[] fields, @NullDisallow Object[] destination)
		throws Exception
	{
		// count
		int count = IntUtil.parse(source);

		// fields
		for (int i = 0; i < count; i++)
		{
			// number
			int number = IntUtil.parse(source);

			// value
			boolean found = false;

			for (ADatabaseField field : fields)
			{
				if (field.NUMBER == number)
				{
					Validator.condition(!field.KEY, "modification of KEY field disallowed");
					found = true;

					destination[number] = field.factories()
						.getByteFactory()
						.fromStream(source);

					break;
				}
			}

			if (!found) throw new Exception("modification of field with [number] (" + number + ") disallowed");
		}
	}

}
