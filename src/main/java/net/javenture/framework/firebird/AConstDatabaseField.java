package net.javenture.framework.firebird;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.util.IConst;


/*
	2019/07/30
 */
abstract public class AConstDatabaseField<C extends IConst> // ! abstract, <C extends IConst>
	extends AFactoryObjectDatabaseField<C> // !
{
	//
	protected AConstDatabaseField
	(
		@NullDisallow String table,
		int number,
		@NullDisallow String name,
		@NullAllow C init,
		@NullDisallow Factories<C> factories,
		Option... options
	)
	{
		super(table, number, name, init, factories, options);
	}

}
