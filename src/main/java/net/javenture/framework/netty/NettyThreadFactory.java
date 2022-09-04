package net.javenture.framework.netty;


import java.util.concurrent.ThreadFactory;


/*
	2019/08/21
 */
final public class NettyThreadFactory
	implements ThreadFactory
{
	/*
		https://stackoverflow.com/questions/5910548/how-to-check-if-thread-is-terminated
		https://www.algosome.com/articles/knowing-when-threads-stop.html
	 */


	//
	@Override
	public NettyThread newThread(Runnable r)
	{
		// XXX
		//System.out.println(r.getClass());


		NettyThread thread = new NettyThread(r);

		// XXX

		return thread;
	}


	//
	private static class NettyThread
		extends Thread
	{
		//
		public NettyThread(Runnable target)
		{
			super(target);

			//System.out.println("create: " + getId() + " (" + ZonedDateTimeUtil.instance(Kernel.ZONE_UA) + ")");
		}

/*
		//
		@Override
		public void run()
		{
			System.out.println("create: " + getId() + " (" + ZonedDateTimeUtil.instance(Kernel.ZONE_UA) + ")");

			try
			{
				super.run();
			}
			catch (Exception e)
			{
				System.out.println("exit: " + getId() + " (" + ZonedDateTimeUtil.instance(Kernel.ZONE_UA) + "); " + e);

			}
		}
*/
	}

}
