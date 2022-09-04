package net.javenture.framework.netty;


import net.javenture.framework.utf8.Utf8Util;
import net.javenture.framework.util.ByteInputStream;

import io.netty.handler.codec.http.HttpResponseStatus;


/*
	2020/10/09
 */
public enum NettyStatus                                                           // XXX: byte factory ?
{
	/*
		https://en.wikipedia.org/wiki/List_of_HTTP_status_codes
	 */
	CONTINUE_100(100),

	OK_200(200),

	MOVED_PERMANENTLY_301(301),
	FOUND_302(302),
	NOT_MODIFIED_304(304),

	FORBIDDEN_403(403),
	NOT_FOUND_404(404),
	METHOD_NOT_ALLOWED_405(405),

	INTERNAL_SERVER_ERROR_500(500),
	SERVICE_UNAVAILABLE_503(503),
	;


	//
	final public int CODE;
	final public String DESCRIPTION;
	final HttpResponseStatus VALUE;
	final ByteInputStream STREAM;


	//
	NettyStatus(int code)
	{
		HttpResponseStatus value = HttpResponseStatus.valueOf(code);
		String description = "" + code + ": " + value.reasonPhrase();

		CODE = code;
		DESCRIPTION = description;
		VALUE = value;
		STREAM = Utf8Util.stream(description);
	}

}
