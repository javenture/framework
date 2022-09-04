package net.javenture.framework.netty;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;

import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;


/*
	2020/10/04
 */
public enum NettyMethod
{
	//
	UNKNOWN(),
	GET(HttpMethod.GET),
	POST(HttpMethod.POST),
	OPTIONS(HttpMethod.OPTIONS),
	HEAD(HttpMethod.HEAD),
	PUT(HttpMethod.PUT),
	PATCH(HttpMethod.PATCH),
	DELETE(HttpMethod.DELETE),
	TRACE(HttpMethod.TRACE),
	CONNECT(HttpMethod.CONNECT);


	//
	final private static NettyMethod[] VALUES = { GET, POST, OPTIONS, HEAD, PUT, PATCH, DELETE, TRACE, CONNECT };


	//
	final private @NullAllow HttpMethod VALUE;


	//
	NettyMethod()
	{
		VALUE = null;
	}


	NettyMethod(HttpMethod method)
	{
		VALUE = method;
	}


	//
	public static NettyMethod parse(@NullDisallow HttpRequest request)
	{
		HttpMethod method0 = request.method();

		for (NettyMethod method : VALUES)
		{
			if (method.VALUE == method0) return method;
		}

		return UNKNOWN;
	}

}
