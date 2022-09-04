package net.javenture.framework.util;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.firebird.AConstDatabaseField;
import net.javenture.framework.model.AConstModel;


/*
	2019/10/04
 */
public enum Language
	implements IConst<Language>
{
	/*
		https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes
		https://en.wikipedia.org/wiki/Language_code
		https://en.wikipedia.org/wiki/IETF_language_tag
	 */
	UNKNOWN(-1, "", "", ""),
	ENGLISH(0, "en", "eng", "English"),                                  // XXX: ISO !
	UKRAINIAN(1, "uk", "ukr", "Українська"),
	RUSSIAN(2, "ru", "rus", "Русский")
	;


	// XXX: Language ('en' only); Translation ('en' + 'us' = 'en-us')


	//
	final public static Factories<Language> FACTORIES = ConstUtil.factories(values(), object -> object instanceof Language);


	//
	final public int ID;                                                                               // XXX: NAME2 | NAME3 -> bytes -> int
	final public String NAME2;
	final public String NAME3;
	final public String ORIGIN;


	//
	Language(int id, String name2, String name3, String origin)
	{
		ID = id;
		NAME2 = name2;
		NAME3 = name3;
		ORIGIN = origin;
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


	@Override
	public Factories<Language> factories()
	{
		return FACTORIES;
	}


	@Override
	public boolean equals(@NullAllow Language object)
	{
		return this == object;
	}


	@Override
	public String toString()
	{
		return "" + ID;
	}


	public String name2()
	{
		return NAME2;
	}


	public String name3()
	{
		return NAME3;
	}


	public String origin()
	{
		return ORIGIN;
	}


	//
	final public static Language[] VALUES = { ENGLISH, UKRAINIAN, RUSSIAN };


	//
	final public static class DatabaseField
		extends AConstDatabaseField<Language>                                                                     // XXX: Octet !
	{
		//
		final private Model MODEL;

		//
		public DatabaseField(String table, int number, String name, @NullAllow Language init, Option... options)
		{
			super(table, number, name, init, FACTORIES, options);

			MODEL = new Model(this, true, false);
		}

		@Override
		public Model model()
		{
			return MODEL;
		}
	}


	final public static class Model
		extends AConstModel<Language>
	{
		//
		public Model(DatabaseField field, boolean quotation, boolean trim)
		{
			super(field, quotation, trim);
		}

		public Model(@NullDisallow String name, boolean nullable, boolean quotation, boolean trim, @NullAllow Language init, @NullDisallow String... objects)
		{
			super(name, nullable, quotation, trim, init, FACTORIES, objects);
		}
	}

}
