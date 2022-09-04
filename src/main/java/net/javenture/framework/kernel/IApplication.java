package net.javenture.framework.kernel;


public interface IApplication
{


	interface IInteraction
	{
		void init(ClassLoader loader) throws Exception;

		void start();

		void stop();

	}

}
