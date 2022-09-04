package net.javenture.framework.util;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.factory.IInstanceFactory;
import net.javenture.framework.firebird.AConstDatabaseField;
import net.javenture.framework.model.AConstModel;


/*
	2020/01/05
 */
public enum Currency
	implements IConst<Currency>
{
	/*
		https://en.wikipedia.org/wiki/ISO_4217
		https://www.iso.org/iso-4217-currency-codes.html
	 */
	UNKNOWN(-1, "?"),
	UAH(0, "UAH"),                                                   // XXX: 0 -> 980
	USD(1, "USD"),
	EUR(2, "EUR"),
	RUB(3, "RUB");


	//
	final public static Factories<Currency> FACTORIES = ConstUtil.factories(values(), object -> object instanceof Currency);


	//
	final private int ID;
	final private String DESCRIPTION;                                                       // XXX: alias ? brief ? detail ?


	//
	Currency(int id, String description)
	{
		ID = id;
		DESCRIPTION = description;
	}


	//
	@Override
	public boolean defined()
	{
		return this.ID != UNKNOWN.ID;
	}


	@Override
	public int id()
	{
		return ID;
	}


	public String description()
	{
		return DESCRIPTION;
	}


	@Override
	public Factories<Currency> factories()
	{
		return FACTORIES;
	}


	@Override
	public boolean equals(@NullAllow Currency object)
	{
		return this == object;
	}


	@Override
	public String toString()
	{
		return "" + ID;
	}


	//
	final public static class DatabaseField
		extends AConstDatabaseField<Currency>
	{
		//
		final private Model MODEL;

		//
		public DatabaseField(String table, int number, String name, @NullAllow Currency init, Option... options)
		{
			super(table, number, name, init, FACTORIES, options);

			MODEL = new Model(this, true, false);
		}

		//
		@Override
		public Model model()
		{
			return MODEL;
		}
	}


	final public static class Model
		extends AConstModel<Currency>
	{
		//
		public Model(DatabaseField field, boolean quotation, boolean trim)
		{
			super(field, quotation, trim);
		}

		public Model(@NullDisallow String name, boolean nullable, boolean quotation, boolean trim, @NullAllow Currency init, @NullDisallow String... objects)
		{
			super(name, nullable, quotation, trim, init, FACTORIES, objects);
		}
	}


	final public static class Parser
	{
		final public static ConstParser<Currency> UNKNOWN = new ConstParser<>(values(), Currency.UNKNOWN, false, false);
	}

}
