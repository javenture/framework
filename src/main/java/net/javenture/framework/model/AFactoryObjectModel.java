package net.javenture.framework.model;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.factory.Factories;
import net.javenture.framework.factory.FactoryObjectUtil;
import net.javenture.framework.factory.IFactoryObject;
import net.javenture.framework.firebird.AFactoryObjectDatabaseField;
import net.javenture.framework.netty.NettyParameters;
import net.javenture.framework.util.Validator;


/*
	2019/10/03
 */
abstract public class AFactoryObjectModel<V extends IFactoryObject> // ! abstract
	extends AModel<V>
{
	//
	final protected boolean NULLABLE;
	final protected boolean QUOTATION;
	final protected boolean TRIM;
	final protected @NullAllow V INIT;
	final private @NullDisallow Factories<V> FACTORIES;


	//
	protected AFactoryObjectModel(@NullDisallow AFactoryObjectDatabaseField<V> field, boolean quotation, boolean trim)
	{
		super(field);

		V init = field.init();
		boolean nullable = field.nullable();
		Validator.argument(nullable || init != null, "[init] null value disallowed");
		Factories<V> factories = field.factories();

		NULLABLE = nullable;
		QUOTATION = quotation;
		TRIM = trim;
		INIT = init;
		FACTORIES = factories;
	}


	protected AFactoryObjectModel
	(
		@NullDisallow String name,
		boolean nullable,
		boolean quotation,
		boolean trim,
		@NullAllow V init,
		@NullDisallow Factories<V> factories,
		@NullDisallow String... objects
	)
	{
		super(name, objects);

		Validator.argument(nullable || init != null, "[init] null value disallowed");
		Validator.notNull(factories, "[factories]");

		NULLABLE = nullable;
		QUOTATION = quotation;
		TRIM = trim;

		INIT = factories.getCopyFactory()
			.copy(init);

		FACTORIES = factories;
	}


	//
	@Override
	final public boolean nullable()
	{
		return NULLABLE;
	}


	@Override
	final public boolean quotation()
	{
		return QUOTATION;
	}


	@Override
	final public boolean trim()
	{
		return TRIM;
	}


	@Override
	final public @NullAllow V init()
	{
		return FACTORIES.getCopyFactory()
			.copy(INIT);
	}


	@Override
	final public void toNettyParameter(NettyParameters destination)
	{
		String s = FACTORIES.getStringFactory()
			.toString(INIT);

		destination.add(ALIAS, s);
	}


	@Override
	final public void toNettyParameter(@NullAllow V value, NettyParameters destination)
	{
		Validator.argument(NULLABLE || value != null, "[value] null value disallowed");

		String s = FACTORIES.getStringFactory()
			.toString(value);

		destination.add(ALIAS, s);
	}


	@Override
	final public boolean defined(@NullAllow V value)
	{
		Validator.argument(NULLABLE || value != null, "[value] null value disallowed");

		return !FactoryObjectUtil.equal(value, INIT);
	}

}
