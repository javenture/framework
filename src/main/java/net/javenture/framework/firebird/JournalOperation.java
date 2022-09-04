package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.model.AConstModel;
import net.javenture.framework.util.ConstUtil;
import net.javenture.framework.util.IConst;


/*
	2020/01/05
 */
enum JournalOperation
	implements IConst<JournalOperation>
{
	//
	UNKNOWN(-1),
	TRANSACTION_COMMIT(0),
	ENTITY_CREATE(1),
	ENTITY_SAVE(2),
	ENTITY_RECYCLE(3),
	ENTITY_RECYCLE_IF_EXISTS(4),
	ENTITY_DELETE(5),
	ENTITY_DELETE_IF_EXISTS(6),
	ENTITY_UNDO(7),
	ENTITY_INIT(8);


	// XXX: TABLE: create
	// XXX: TABLE: drop
	// XXX: TABLE: clear

	// XXX: INDEX: create
	// XXX: INDEX: drop


	//
	final public static Factories<JournalOperation> FACTORIES = ConstUtil.factories(values(), object -> object instanceof JournalOperation);


	//
	final private int ID;


	//
	JournalOperation(int id)
	{
		ID = id;
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
	public Factories<JournalOperation> factories()
	{
		return FACTORIES;
	}


	@Override
	public boolean equals(JournalOperation value)
	{
		return this == value;
	}


	@Override
	public String toString()
	{
		return "" + ID;
	}


	//
	final public static class DatabaseField
		extends AConstDatabaseField<JournalOperation>
	{
		//
		final private Model MODEL;

		//
		DatabaseField(String table, int number, String name, @NullAllow JournalOperation init, Option... options)
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
		extends AConstModel<JournalOperation>
	{
		public Model(DatabaseField field, boolean quotation, boolean trim)
		{
			super(field, quotation, trim);
		}

		public Model(@NullDisallow String name, boolean nullable, boolean quotation, boolean trim, @NullAllow JournalOperation init, @NullDisallow String... objects)
		{
			super(name, nullable, quotation, trim, init, FACTORIES, objects);
		}
	}

}
