package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.util.ConstUtil;
import net.javenture.framework.util.IConst;


/*
	2020/01/05
 */
public interface IDatabaseOperation                                                                 // XXX: IDatabaseAction | IAppOperation ?
{
	//
	boolean exists() throws Exception;
	boolean exists(DatabaseTransaction transaction) throws Exception;

	void create() throws Exception;
	void create(DatabaseTransaction transaction) throws Exception;

	void load() throws Exception;
	void load(DatabaseTransaction transaction) throws Exception;

	boolean loadIfExists() throws Exception;
	boolean loadIfExists(DatabaseTransaction transaction) throws Exception;

	void save() throws Exception;
	void save(DatabaseTransaction transaction) throws Exception;

	void recycle() throws Exception;
	void recycle(DatabaseTransaction transaction) throws Exception;

	boolean recycleIfExists() throws Exception;
	boolean recycleIfExists(DatabaseTransaction transaction) throws Exception;

	void delete() throws Exception;
	void delete(DatabaseTransaction transaction) throws Exception;

	boolean deleteIfExists() throws Exception;
	boolean deleteIfExists(DatabaseTransaction transaction) throws Exception;


	//
	enum Type                                                        // XXX: DatabaseOperation + up level ?
		implements IConst<Type>
	{
		//
		UNKNOWN(-1),
		EXISTS(0),
		CREATE(1),
		LOAD(2),
		SAVE(3),
		RECYCLE(4),
		DELETE(5);

		//
		final public static Factories<Type> FACTORIES = ConstUtil.factories(values(), object -> object instanceof Type);

		//
		final private int ID;

		//
		Type(int id)
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
		public Factories<Type> factories()
		{
			return FACTORIES;
		}

		@Override
		public boolean equals(@NullAllow Type type)
		{
			return this == type;
		}

		@Override
		public String toString()
		{
			return "" + ID;
		}
	}

}
