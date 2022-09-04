package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.IByteFactory;
import net.javenture.framework.log.ILog;
import net.javenture.framework.log.LogAttachment;
import net.javenture.framework.util.IntUtil;
import net.javenture.framework.util.Validator;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;


/*
	2019/12/04
 */
final public class DatabaseTable
	implements ILog
{
	//
	final private static String IDENTIFICATOR = "$_0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	final private static ADatabaseField[] BLANK = new ADatabaseField[0];




	// XXX: abstract T generator()


	//
	final String NAME;
	final int COLUMN;
	final ADatabaseField[] FIELDS;
	final ADatabaseField[] FIELDS0;
	final ADatabaseField[] KEYS;
	final IByteFactory<Object[]> FACTORY;


	// XXX: constraints
	// XXX: indexes


	//
	public DatabaseTable(@NullDisallow String name, DatabaseFields fields)
	{
		Validator.notNull(name, "[name]");
		Validator.argument(!name.isEmpty(), "[name] is empty");

		int column = fields.size();
		Validator.argument(column != 0, "[fields] is empty");

		for (int i = 0; i < column; i++)
		{
			int number = fields.get(i).NUMBER;
			Validator.argument(number == i, "field at position (", i, ") has wrong [NUMBER] (", number, ")");
		}

		//
		IByteFactory<Object[]> factory = new IByteFactory<>()
		{
			final private int COLUMN = column;

			@Override
			public void toStream(@NullDisallow Object[] values, OutputStream destination)
				throws Exception
			{
				Validator.notNull(values, "[values]");
				Validator.argument(values.length == COLUMN, "[values.length] (", values.length, ") != [COLUMN] (", COLUMN, ")");

				// column
				IntUtil.bytes(COLUMN, destination);

				// values
				for (ADatabaseField field : FIELDS)
				{
					// type
					DatabaseType type = field.type();

					DatabaseType.FACTORIES.getByteFactory()
						.toStream(type, destination);

					// value
					field.factories()
						.getByteFactory()
						.toStream(values[field.NUMBER], destination);
				}
			}

			@Override
			public @NullDisallow Object[] fromStream(InputStream source)
				throws Exception
			{
				Object[] result = new Object[COLUMN];

				// column
				int column = IntUtil.parse(source);
				Validator.condition(column == COLUMN, "[column] (", column, ") != [COLUMN] (", COLUMN, ")");

				// values
				for (ADatabaseField field : FIELDS)
				{
					// type
					DatabaseType type = DatabaseType.FACTORIES.getByteFactory()
						.fromStream(source);

					Validator.condition(type == field.type(), "[type] (", type, ") != [field.type()] (", field.type(), ")");

					// value
					result[field.NUMBER] = field.factories()
						.getByteFactory()
						.fromStream(source);
				}

				return result;
			}
		};

		//
		int key = 0;

		for (ADatabaseField field : fields)
		{
			if (field.KEY) key++;
		}

		//
		NAME = name;
		COLUMN = column;
		FIELDS = fields.array();
		FACTORY = factory;

		if (key != 0)
		{
			FIELDS0 = new ADatabaseField[column - key];
			KEYS = new ADatabaseField[key];

			// fields0
			{
				int i = 0;

				for (ADatabaseField field : fields)
				{
					if (!field.KEY) FIELDS0[i++] = field;
				}
			}

			// keys
			{
				int i = 0;

				for (ADatabaseField field : fields)
				{
					if (field.KEY) KEYS[i++] = field;
				}
			}
		}
		else
		{
			FIELDS0 = fields.array();
			KEYS = BLANK;
		}
	}


	public DatabaseTable(String name, List<ADatabaseField> fields, List<DatabaseIndex> indexes)
	{
		// XXX: ???

		throw new RuntimeException();
	}


	//
	public String name()
	{
		return NAME;
	}


	public int column()
	{
		return COLUMN;
	}


	Object[] init()
	{
		Object[] result = new Object[COLUMN];

		for (ADatabaseField field : FIELDS) result[field.NUMBER] = field.init(); // ! not System.arraycopy()

		return result;
	}


	void init(Object[] values)
	{
		for (ADatabaseField field : FIELDS) values[field.NUMBER] = field.init();
	}


	void init(Object[] values, ADatabaseField... fields)
	{
		for (ADatabaseField field : fields) values[field.NUMBER] = field.init();
	}


	//
	private static boolean check(String name)
	{
		int l = name.length();

		for (int i = 0; i < l; i++)
		{
			char c = name.charAt(i);

			if (IDENTIFICATOR.indexOf(c) == -1) return false;
		}

		return true;
	}


	@Override
	public LogAttachment log()
	{
		LogAttachment result = new LogAttachment()
			.add(NAME, "NAME")
			.add(COLUMN, "COLUMN");

		//
		for (int i = 0; i < COLUMN; i++) result.add(FIELDS[i], "FIELD [" + i + "]");

		return result;
	}


	@Override
	public String toString()
	{
		return NAME;
	}


	@Override
	final public int hashCode()
	{
		return super.hashCode(); // implementation disallowed
	}


	@Override
	final public boolean equals(Object object)
	{
		return super.equals(object); // implementation disallowed
	}

}
