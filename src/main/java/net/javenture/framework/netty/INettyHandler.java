package net.javenture.framework.netty;


import io.netty.channel.ChannelHandlerContext;


/*
	2020/10/05
 */
public interface INettyHandler
{
	void register(ChannelHandlerContext context);
	void deregister(ChannelHandlerContext context);

}
