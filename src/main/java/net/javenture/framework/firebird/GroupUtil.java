package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.util.ByteInputStream;
import net.javenture.framework.util.ByteOutputStream;
import net.javenture.framework.util.ConstUtil;
import net.javenture.framework.util.IntUtil;

import java.util.ArrayList;


/*
	2019/12/05
 */
final class GroupUtil
{
	//
	static <E extends AGroup.AEntry, G extends AGroup<E, G>> void validate(@NullDisallow AGroup<E, G> group, @NullDisallow ArrayList<E> entries) // ! G extends AGroup<E, G>
		throws Exception
	{
		ADatabaseField[] fields = group.config().table().FIELDS0;

		for (E entry : entries)
		{
			for (ADatabaseField field : fields) field.validate(entry.get(field));
		}
	}


	static @NullDisallow <E extends AGroup.AEntry, G extends AGroup<E, G>> byte[] toBytes // ! G extends AGroup<E, G>
	(
		@NullDisallow AGroup<E, G> group,
		@NullDisallow ASingleGroup.Modification modification,
		@NullAllow ArrayList<E> deletion,
		@NullAllow ArrayList<E> addition
	)
		throws Exception
	{
		ADatabaseField[] fields = group.config().table().FIELDS0;

		try (ByteOutputStream stream = new ByteOutputStream())
		{
			// modification
			ConstUtil.bytes(modification, stream);

			// deletion
			if (deletion != null && !deletion.isEmpty())
			{
				// size
				int size = deletion.size();
				IntUtil.bytes(size, stream);

				// entries
				for (E entry : deletion)
				{
					for (ADatabaseField field : fields)
					{
						Object value = entry.get(field);

						field.factories()
							.getByteFactory()
							.toStream(value, stream);
					}
				}
			}
			else
			{
				// size
				IntUtil.bytes(0, stream);
			}

			// addition
			if (addition != null && !addition.isEmpty())
			{
				// size
				int size = addition.size();
				IntUtil.bytes(size, stream);

				// entries
				for (E entry : addition)
				{
					for (ADatabaseField field : fields)
					{
						Object value = entry.get(field);

						field.factories()
							.getByteFactory()
							.toStream(value, stream);
					}
				}
			}
			else
			{
				// size
				IntUtil.bytes(0, stream);
			}

			return stream.toBytes();
		}
	}


	static @NullDisallow <E extends AGroup.AEntry, G extends AGroup<E, G>> ASingleGroup.Modification fromBytes // ! G extends AGroup<E, G>
	(
		@NullDisallow AGroup<E, G> group,
		@NullDisallow byte[] source,
		@NullDisallow ArrayList<E> deletion,
		@NullDisallow ArrayList<E> addition
	)
		throws Exception
	{
		ADatabaseField[] fields = group.config().table().FIELDS0;

		try (ByteInputStream stream = new ByteInputStream(source))
		{
			// modification
			ASingleGroup.Modification modification = ConstUtil.parse(stream, ASingleGroup.Modification.FACTORIES);

			// deletion
			{
				int size = IntUtil.parse(stream);

				if (size != 0)
				{
					deletion.ensureCapacity(size);

					for (int i = 0; i < size; i++)
					{
						E entry = group.entry();

						for (ADatabaseField field : fields)
						{
							Object value = field.factories()
								.getByteFactory()
								.fromStream(stream);

							entry.set(field, value);
						}

						deletion.add(entry);
					}
				}
			}

			// addition
			{
				int size = IntUtil.parse(stream);

				if (size != 0)
				{
					addition.ensureCapacity(size);

					for (int i = 0; i < size; i++)
					{
						E entry = group.entry();

						for (ADatabaseField field : fields)
						{
							Object value = field.factories()
								.getByteFactory()
								.fromStream(stream);

							entry.set(field, value);
						}

						addition.add(entry);
					}
				}
			}

			return modification;
		}
	}


	/*
		entries0 - source
		entries  - destination
	 */
	static <E extends AGroup.AEntry> void conversion(ArrayList<E> entries0, ArrayList<E> entries, ArrayList<E> deletion, ArrayList<E> addition)
	{
		int size = entries.size();
		int size0 = entries0.size();

		// deletion
		{
			boolean[] check = new boolean[size];

			for (E entry0 : entries0)
			{
				boolean found0 = false;

				for (int i = 0; i < size; i++)
				{
					if (!check[i])
					{
						E entry = entries.get(i);

						if (entry.match(entry0))
						{
							found0 = true;
							check[i] = true;

							break;
						}
					}
				}

				if (!found0) deletion.add(entry0);
			}
		}

		// addition
		{
			boolean[] check0 = new boolean[size0];

			for (E entry : entries)
			{
				boolean found = false;

				for (int i = 0; i < size0; i++)
				{
					if (!check0[i])
					{
						E entry0 = entries0.get(i);

						if (entry0.match(entry))
						{
							found = true;
							check0[i] = true;

							break;
						}
					}
				}

				if (!found) addition.add(entry);
			}
		}
	}

}
