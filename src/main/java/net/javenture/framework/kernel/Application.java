package net.javenture.framework.kernel;


import net.javenture.framework.util.Validator;

import java.util.concurrent.atomic.AtomicReference;


final public class Application
{
	final private static Class<?> CLASS = Application.class;
	final private static AtomicReference<ClassLoader> LOADER = new AtomicReference<>(null);
	final private static Interaction INTERACTION = new Interaction();
	final private static int VERSION = 1;


	//
	private Application()
	{
	}


	//
	public static Interaction interaction()
	{
		return INTERACTION;
	}


	//
	final public static class Interaction implements IApplication.IInteraction
	{
		private Interaction()
		{
		}

		//
		@Override
		@SuppressWarnings("ObjectEquality")
		public void init(ClassLoader loader) throws Exception
		{
			Validator.condition(CLASS.getClassLoader() == loader, "");                                       // XXX: ?
			Validator.condition(LOADER.compareAndSet(null, loader), "");                                       // XXX: ?
		}

		@Override
		public void start()
		{
			// XXX
			// create database pool
			// chech database (exclusive mode)
			// create http pool
			// connect to http server



		}

		@Override
		public void stop()
		{

		}
	}


/*
	public static void main(String[] args) throws Exception
	{

	}
*/

}
