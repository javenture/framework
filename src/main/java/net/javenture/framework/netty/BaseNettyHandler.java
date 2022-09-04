package net.javenture.framework.netty;


import net.javenture.framework.annotation.NullDisallow;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpResponse;


/*
	2020/10/08
 */
final public class BaseNettyHandler
	extends SimpleChannelInboundHandler<HttpObject>
	implements INettyHandler
{
	//
	final private @NullDisallow NettyResponse RESPONSE;


	//
	public BaseNettyHandler(@NullDisallow NettyResponse response)
	{
		RESPONSE = response;
	}


	//
	@Override
	public void register(ChannelHandlerContext context)
	{
		ChannelPipeline pipeline = context.pipeline();
		pipeline.addLast(this);
	}


	@Override
	public void deregister(ChannelHandlerContext context)
	{
		ChannelPipeline pipeline = context.pipeline();
		pipeline.remove(this);
	}


	@Override
	protected void channelRead0(ChannelHandlerContext context, HttpObject message)
	{
		write(context);
	}


	void write(ChannelHandlerContext context)
	{
		HttpResponse response0 = NettyResponse.toHttpResponse(RESPONSE);
		context.writeAndFlush(response0);
	}

}
