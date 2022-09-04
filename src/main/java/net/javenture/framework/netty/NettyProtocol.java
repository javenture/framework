package net.javenture.framework.netty;


import net.javenture.framework.annotation.NullDisallow;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;


/*
	2020/10/05
 */
final public class NettyProtocol
{
	//
	final public @NullDisallow HttpVersion VERSION;


	//
	private NettyProtocol(HttpVersion version)
	{
		VERSION = version;
	}


	//
	public static NettyProtocol parse(@NullDisallow HttpRequest request)
	{
		HttpVersion version = request.protocolVersion();

		if (HTTP_11.VERSION == version || HTTP_11.VERSION.equals(version)) return HTTP_11;
		else if (HTTP_10.VERSION == version || HTTP_10.VERSION.equals(version)) return HTTP_10;
		else return new NettyProtocol(version);
	}


	//
	final public static NettyProtocol HTTP_11 = new NettyProtocol(HttpVersion.HTTP_1_1);
	final public static NettyProtocol HTTP_10 = new NettyProtocol(HttpVersion.HTTP_1_0);

}
