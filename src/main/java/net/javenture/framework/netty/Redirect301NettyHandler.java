package net.javenture.framework.netty;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpObject;


/*
	2020/10/08
 */
final public class Redirect301NettyHandler
	extends SimpleChannelInboundHandler<HttpObject>
	implements INettyHandler
{
	//
	final private NettyUrl URL;


	//
	public Redirect301NettyHandler(NettyUrl url)
	{
		URL = url;
	}


	public Redirect301NettyHandler(String url)
	{
		URL = new NettyUrl(url);
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
		NettyResponse response = NettyResponse.redirect301MovedPermanently(URL);
		HttpResponse response0 = NettyResponse.toHttpResponse(response);
		context.writeAndFlush(response0);
	}

}
