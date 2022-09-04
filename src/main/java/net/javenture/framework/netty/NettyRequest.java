package net.javenture.framework.netty;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.annotation.SingleThread;

import io.netty.handler.codec.http.HttpRequest;


/*
	2020/10/04
 */
@SingleThread
final public class NettyRequest
{
	//
	final @NullDisallow NettyProtocol PROTOCOL;
	final @NullDisallow NettyMethod METHOD;
	final @NullDisallow NettyUrl URL;
	final @NullDisallow NettyHeaders HEADERS;
	final @NullDisallow NettyCookies COOKIES;
	final @NullDisallow NettyParameters PARAMETERS;
	final @NullDisallow NettyUploads UPLOADS;


	//
	public NettyRequest(@NullDisallow HttpRequest request)
	{
		PROTOCOL = NettyProtocol.parse(request);
		METHOD = NettyMethod.parse(request);
		URL = new NettyUrl(request);
		HEADERS = new NettyHeaders(request);
		COOKIES = new NettyCookies(request);
		PARAMETERS = new NettyParameters();
		UPLOADS = new NettyUploads();
	}


	//
	public @NullDisallow NettyProtocol protocol()
	{
		return PROTOCOL;
	}


	public @NullDisallow NettyMethod method()
	{
		return METHOD;
	}


	public @NullDisallow NettyUrl url()
	{
		return URL;
	}


	public @NullDisallow NettyHeaders headers()
	{
		return HEADERS;
	}


	public @NullDisallow NettyCookies cookies()
	{
		return COOKIES;
	}


	public @NullDisallow NettyParameters parameters()
	{
		return PARAMETERS;
	}


	public @NullDisallow NettyUploads uploads()
	{
		return UPLOADS;
	}

}
