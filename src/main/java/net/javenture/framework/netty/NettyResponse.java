package net.javenture.framework.netty;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.annotation.SingleThread;
import net.javenture.framework.log.Log;
import net.javenture.framework.utf8.IUtf8StreamableEntry;
import net.javenture.framework.utf8.Utf8Util;
import net.javenture.framework.util.ByteInputStream;

import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.ServerCookieEncoder;

import java.io.InputStream;
import java.time.LocalDateTime;


/*
	2020/10/09
 */
@SingleThread
final public class NettyResponse
{
	//
	final private static Log LOG = Log.instance(NettyResponse.class);

	//
	final @NullDisallow NettyStatus STATUS;
	final @NullDisallow Media MEDIA;
	final @NullAllow InputStream CONTENT;
	final @NullDisallow NettyHeaders HEADERS;
	final @NullDisallow NettyCookies COOKIES;


	//
	public NettyResponse(@NullDisallow NettyStatus status, boolean description)
	{
		this(status, NettyDefault.MEDIA, description ? status.STREAM : ByteInputStream.BLANK);
	}


	public NettyResponse(@NullDisallow NettyStatus status, @NullAllow byte[] content)
	{
		this(status, NettyDefault.MEDIA, content);
	}


	public NettyResponse(@NullDisallow NettyStatus status, @NullAllow CharSequence content)
	{
		this(status, NettyDefault.MEDIA, content);
	}


	public NettyResponse(@NullDisallow NettyStatus status, @NullAllow IUtf8StreamableEntry content)
	{
		this(status, NettyDefault.MEDIA, content);
	}


	public NettyResponse(@NullDisallow NettyStatus status, @NullAllow InputStream content)
	{
		this(status, NettyDefault.MEDIA, content);
	}


	public NettyResponse(@NullDisallow NettyStatus status, @NullDisallow Media media, @NullDisallow byte[] content)
	{
		STATUS = status;
		MEDIA = media;
		CONTENT = new ByteInputStream(content);
		HEADERS = new NettyHeaders();
		COOKIES = new NettyCookies();
	}


	public NettyResponse(@NullDisallow NettyStatus status, @NullDisallow Media media, @NullDisallow CharSequence content)
	{
		STATUS = status;
		MEDIA = media;
		CONTENT = Utf8Util.stream(content);
		HEADERS = new NettyHeaders();
		COOKIES = new NettyCookies();
	}


	public NettyResponse(@NullDisallow NettyStatus status, @NullDisallow Media media, @NullAllow IUtf8StreamableEntry content)
	{
		STATUS = status;
		MEDIA = media;
		CONTENT = Utf8Util.stream(content);
		HEADERS = new NettyHeaders();
		COOKIES = new NettyCookies();
	}


	public NettyResponse(@NullDisallow NettyStatus status, @NullDisallow Media media, @NullAllow InputStream content)
	{
		STATUS = status;
		MEDIA = media;
		CONTENT = content;
		HEADERS = new NettyHeaders();
		COOKIES = new NettyCookies();
	}


	//
	public @NullDisallow NettyStatus status()
	{
		return STATUS;
	}


	public @NullDisallow Media media()
	{
		return MEDIA;
	}


	public @NullAllow InputStream content()
	{
		return CONTENT;
	}


	public @NullDisallow NettyHeaders headers()
	{
		return HEADERS;
	}


	public @NullDisallow NettyCookies cookies()
	{
		return COOKIES;
	}


	public @NullDisallow HttpResponse toHttpResponse()                                                                                                          // XXX: config
		throws Exception
	{
		HttpResponse result = CONTENT != null
			? new DefaultFullHttpResponse(NettyDefault.PROTOCOL.VERSION, STATUS.VALUE)
			: new DefaultHttpResponse(NettyDefault.PROTOCOL.VERSION, STATUS.VALUE);

		LocalDateTime now = NettyUtil.now();
		HttpHeaders headers = result.headers();

		// headers
		headers.set(HttpHeaderNames.SERVER, "netty");                                                                                        // XXX: config
		headers.set(HttpHeaderNames.DATE, NettyUtil.format(now));

		if (HEADERS.exists())
		{
			for (NettyHeaders.Entry entry : HEADERS) headers.set(entry.NAME, entry.VALUE);
		}

		// cookies
		if (COOKIES.exists())
		{
			for (NettyCookies.Entry entry : COOKIES)
			{
				Cookie cookie = entry.cookie();
				headers.add(HttpHeaderNames.SET_COOKIE, ServerCookieEncoder.STRICT.encode(cookie));
			}
		}

		// content
		if (CONTENT != null)
		{
			int available = CONTENT.available();
			headers.setInt(HttpHeaderNames.CONTENT_LENGTH, available);

			if (available != 0)
			{
				headers.set(HttpHeaderNames.CONTENT_TYPE, MEDIA.HEADER);

				DefaultFullHttpResponse response = (DefaultFullHttpResponse) result;
				response.content().writeBytes(CONTENT, available);
			}
		}

		return result;
	}


	//
	public static @NullDisallow HttpResponse toHttpResponse(@NullAllow NettyResponse response)
	{
		HttpResponse result = null;

		if (response != null)
		{
			try
			{
				result = response.toHttpResponse();
			}
			catch (Exception e)
			{
				LOG.error(e);
			}
		}

		if (result == null)
		{
			try
			{
				NettyStatus status = NettyStatus.INTERNAL_SERVER_ERROR_500;
				result = new NettyResponse(status, status.DESCRIPTION).toHttpResponse();
			}
			catch (Exception e)
			{
				throw new RuntimeException(e); // !
			}
		}

		return result;
	}


	public static NettyResponse redirect301MovedPermanently(@NullDisallow String url)
	{
		return redirect301MovedPermanently(new NettyUrl(url));
	}


	public static NettyResponse redirect301MovedPermanently(@NullDisallow NettyUrl url)                                                                 // XXX: base
	{
		NettyUrl.Scheme scheme = url.SCHEME.exists() ? url.SCHEME : NettyUrl.Scheme.HTTP;
		NettyUrl.Host host = url.HOST.exists() ? url.HOST : NettyUrl.Host.LOCALHOST;

		return redirect0(NettyStatus.MOVED_PERMANENTLY_301, scheme, host, url.PORT, url.PATH, url.FILE, url.QUERY, url.FRAGMENT);
	}


	public static NettyResponse redirect302Found(@NullDisallow String url)
	{
		return redirect302Found(new NettyUrl(url));
	}


	public static NettyResponse redirect302Found(@NullDisallow NettyUrl url)
	{
		NettyUrl.Scheme scheme = url.SCHEME.exists() ? url.SCHEME : NettyUrl.Scheme.HTTP;
		NettyUrl.Host host = url.HOST.exists() ? url.HOST : NettyUrl.Host.LOCALHOST;

		return redirect0(NettyStatus.FOUND_302, scheme, host, url.PORT, url.PATH, url.FILE, url.QUERY, url.FRAGMENT);
	}


	private static NettyResponse redirect0
	(
		@NullDisallow NettyStatus status,
		@NullDisallow NettyUrl.Scheme scheme,
		@NullDisallow NettyUrl.Host host,
		@NullDisallow NettyUrl.Port port,
		@NullDisallow NettyUrl.Path path,
		@NullDisallow NettyUrl.File file,
		@NullDisallow NettyUrl.Query query,
		@NullDisallow NettyUrl.Fragment fragment
	)
	{
		String location = NettyUrl.toString(scheme, host, port, path, file, query, fragment);

		NettyResponse result = new NettyResponse(status, false);
		result.HEADERS.add(HttpHeaderNames.LOCATION, location);

		return result;
	}

}
