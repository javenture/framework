package net.javenture.framework.factory;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.firebird.DatabaseType;


/*
	A - Application
	D - Database

	2019/08/27
 */
public interface IDatabaseFactory<A, D>
	extends IFactory<A>
{
	//
	FactoryType<IDatabaseFactory> TYPE = new FactoryType<>("DATABASE");


	//
	DatabaseType getDatabaseType();
	@NullAllow D toDatabase(@NullAllow A object) throws Exception; // ! throws Exception
	@NullAllow A fromDatabase(@NullAllow D object) throws Exception; // ! throws Exception


	//
	@Override
	default FactoryType<IDatabaseFactory> type()
	{
		return TYPE;
	}

}
