package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.util.ConstUtil;
import net.javenture.framework.util.IConst;
import net.javenture.framework.model.AConstModel;

import java.sql.Timestamp;
import java.sql.Types;


/*
	2020/01/05
 */
public enum DatabaseType
	implements IConst<DatabaseType>
{
	//
	UNKNOWN(-1, Object.class, Types.OTHER, -1, 0),
	BOOLEAN(0, Boolean.class, Types.BOOLEAN, 23, 4),                      // XXX: 4 ?
	SHORT(1, Short.class, Types.SMALLINT, -1, -1),                        // XXX: -1, -1 ?
	INT(2, Integer.class, Types.INTEGER, 8, 4),
	LONG(3, Long.class, Types.BIGINT, 16, 8),
	FLOAT(4, Float.class, Types.FLOAT, 10, 4),
	DOUBLE(5, Double.class, Types.DOUBLE, 27, 8),
	STRING(6, String.class, Types.VARCHAR, 37, 4), // 4 * length
	OCTETS(7, byte[].class, Types.VARBINARY, -1, -1),                      // XXX: -1, -1 ?
	BYTES(8, byte[].class, Types.LONGVARBINARY, 261, 8),
	TIMESTAMP(9, Timestamp.class, Types.TIMESTAMP, 35, 8),
	// XXX: DECIMAL ???
	;


	//
	final public static Factories<DatabaseType> FACTORIES = ConstUtil.factories(values(), object -> object instanceof DatabaseType);


	//
	final public int ID;
	final public Class<?> JAVA_CLASS;
	final public int SQL_TYPE; // java.sql.Types
	final public int FIREBIRD_RDB$TYPE;
	final public int FIREBIRD_RDB$FIELD_LENGTH;


	//
	DatabaseType(int id, Class<?> java, int sql, int firebird_rdb$type, int firebird_rdb$field_length)
	{
		ID = id;
		JAVA_CLASS = java;
		SQL_TYPE = sql;
		FIREBIRD_RDB$TYPE = firebird_rdb$type;
		FIREBIRD_RDB$FIELD_LENGTH = firebird_rdb$field_length;
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
	public Factories<DatabaseType> factories()
	{
		return FACTORIES;
	}


	@Override
	public boolean equals(@NullAllow DatabaseType type)
	{
		return this == type;
	}


	@Override
	public String toString()
	{
		return "" + ID;
	}


	//
	public static DatabaseType relation(int type)
	{
		for (DatabaseType value : VALUES)
		{
			if (value.SQL_TYPE == type) return value;
		}

		return UNKNOWN;
	}


	public static DatabaseType relation(Class<?> c)
	{
		for (DatabaseType value : VALUES)
		{
			if (value.JAVA_CLASS == c) return value;
		}

		return UNKNOWN;
	}


	//
	final private static DatabaseType[] VALUES = { BOOLEAN, SHORT, INT, LONG, FLOAT, DOUBLE, STRING, OCTETS, BYTES, TIMESTAMP };


	//
	final public static class DatabaseField
		extends AConstDatabaseField<DatabaseType>
	{
		//
		final private Model MODEL;

		//
		public DatabaseField(String table, int number, String name, @NullAllow DatabaseType init, Option... options)
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
		extends AConstModel<DatabaseType>
	{
		//
		public Model(DatabaseField field, boolean quotation, boolean trim)
		{
			super(field, quotation, trim);
		}

		public Model(@NullDisallow String name, boolean nullable, boolean quotation, boolean trim, @NullAllow DatabaseType init, @NullDisallow String... objects)
		{
			super(name, nullable, quotation, trim, init, FACTORIES, objects);
		}
	}

}
