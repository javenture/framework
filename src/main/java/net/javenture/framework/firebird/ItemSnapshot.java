package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullDisallow;


/*
	2019/12/02
 */
class ItemSnapshot
	implements ISnapshot
{
	//
	final private DatabaseTable TABLE;
	final private @NullDisallow IValuesConnector CONNECTOR;
	final private @NullDisallow Object[] VALUES;


	//
	ItemSnapshot(@NullDisallow IItem item, @NullDisallow IValuesConnector connector)
	{
		DatabaseTable table = item.config().table();
		int column = table.COLUMN;
		ADatabaseField[] fields = table.FIELDS;
		Object[] values = connector.get();

		//
		TABLE = table;
		CONNECTOR = connector;
		VALUES = new Object[column];

		for (int i = 0; i < column; i++)
		{
			VALUES[i] = fields[i].factories()
				.getCopyFactory()
				.copy(values[i]);
		}
	}


	//
	@Override
	public boolean change()
	{
		Object[] values = CONNECTOR.get();

		for (ADatabaseField field : TABLE.FIELDS)
		{
			if (!field.equal(values[field.NUMBER], VALUES[field.NUMBER])) return true;
		}

		return false;
	}


	//
	@FunctionalInterface
	interface IValuesConnector
	{
		@NullDisallow Object[] get();
	}

}
