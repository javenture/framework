package net.javenture.framework.netty;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.util.StringFragmenter;

import io.netty.handler.codec.http.HttpHeaderNames;

import java.util.List;


/*
	2020/10/05
 */
public interface INettyResource
{
	//
	@NullDisallow List<NettyMethod> allow();


	//
	default @NullDisallow NettyResponse get(@NullDisallow NettyRequest request)
		throws Exception // !
	{
		return error405MethodNotAllowed(allow());
	}


	default @NullDisallow NettyResponse post(@NullDisallow NettyRequest request)
		throws Exception // !
	{
		return error405MethodNotAllowed(allow());
	}


	default @NullDisallow NettyResponse options(@NullDisallow NettyRequest request)
		throws Exception // !
	{
		return error405MethodNotAllowed(allow());
	}


	default @NullDisallow NettyResponse head(@NullDisallow NettyRequest request)
		throws Exception // !
	{
		return error405MethodNotAllowed(allow());
	}


	default @NullDisallow NettyResponse put(@NullDisallow NettyRequest request)
		throws Exception // !
	{
		return error405MethodNotAllowed(allow());
	}


	default @NullDisallow NettyResponse patch(@NullDisallow NettyRequest request)
		throws Exception // !
	{
		return error405MethodNotAllowed(allow());
	}


	default @NullDisallow NettyResponse delete(@NullDisallow NettyRequest request)
		throws Exception // !
	{
		return error405MethodNotAllowed(allow());
	}


	default @NullDisallow NettyResponse trace(@NullDisallow NettyRequest request)
		throws Exception // !
	{
		return error405MethodNotAllowed(allow());
	}


	default @NullDisallow NettyResponse connect(@NullDisallow NettyRequest request)
		throws Exception // !
	{
		return error405MethodNotAllowed(allow());
	}


	//
	static @NullDisallow NettyResponse process(@NullDisallow INettyResource resource, @NullDisallow NettyRequest request)
		throws Exception
	{
		switch (request.METHOD)
		{
			case GET: return resource.get(request);
			case POST: return resource.post(request);
			case OPTIONS: return resource.options(request);
			case HEAD: return resource.head(request);
			case PUT: return resource.put(request);
			case PATCH: return resource.patch(request);
			case DELETE: return resource.delete(request);
			case TRACE: return resource.trace(request);
			case CONNECT: return resource.connect(request);
			default: throw new UnsupportedOperationException("method: " + request.METHOD); // !
		}
	}


	private static @NullDisallow NettyResponse error405MethodNotAllowed(List<NettyMethod> allow)
	{
		StringFragmenter fragmenter = new StringFragmenter(allow.size(), ", ");
		fragmenter.add(allow);

		NettyStatus status = NettyStatus.METHOD_NOT_ALLOWED_405;
		NettyResponse response = new NettyResponse(status, status.DESCRIPTION);
		response.HEADERS.add(HttpHeaderNames.ALLOW, fragmenter);

		return response;
	}

}
