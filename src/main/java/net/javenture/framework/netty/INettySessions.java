package net.javenture.framework.netty;


import net.javenture.framework.annotation.NullDisallow;


/*
	2020/09/30
 */
public interface INettySessions<I extends INettySessionId>
{
	//
	String COOKIE_DEFAULT = "SESSION";


	//
	NettySession<I> from(@NullDisallow NettyRequest request);
	void to(@NullDisallow NettyResponse response, @NullDisallow NettySession<I> session);


	//
	default String cookie()
	{
		return COOKIE_DEFAULT;
	}


	//
	@FunctionalInterface
	public interface IGenerator<I extends INettySessionId>
	{
		I get();
	}

}
