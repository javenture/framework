package net.javenture.framework.netty;


import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.annotation.SingleThread;
import net.javenture.framework.util.Chain;
import net.javenture.framework.util.Validator;

import io.netty.handler.codec.http.cookie.DefaultCookie;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;

import java.util.Iterator;
import java.util.Set;


/*
	2020/09/29
 */
@SingleThread
final public class NettyCookies
	implements Iterable<NettyCookies.Entry>
{
	//
	final private Chain<Entry> ENTRIES;


	//
	public NettyCookies()
	{
		ENTRIES = new Chain<>();
	}


	public NettyCookies(HttpRequest request)
	{
		ENTRIES = new Chain<>();

		//
		String s = request.headers().get(HttpHeaderNames.COOKIE);

		if (s != null)
		{
			Set<Cookie> set = ServerCookieDecoder.STRICT.decode(s);

			for (Cookie cookie : set) ENTRIES.add(new Entry(cookie));
		}
	}


	//
	public boolean exists()
	{
		return ENTRIES.exists();
	}


	public void add(@NullDisallow Entry value)
	{
		Validator.notNull(value, "[value]");

		ENTRIES.add(value);
	}


	public @NullAllow Entry get(@NullDisallow String name)
	{
		Validator.notNull(name, "[name]");

		for (Entry entry : ENTRIES)
		{
			if (entry.NAME.equals(name)) return entry;
		}

		return null;
	}


	public boolean contains(@NullDisallow String name, @NullDisallow String value)
	{
		Validator.notNull(name, "[name]");
		Validator.notNull(value, "[value]");

		for (Entry entry : ENTRIES)
		{
			if (entry.NAME.equals(name) && entry.VALUE.equals(value)) return true;
		}

		return false;
	}


	public void clear()
	{
		ENTRIES.clear();
	}


	@Override
	public Iterator<Entry> iterator()
	{
		return ENTRIES.iterator();
	}


	//
	final public static class Entry
	{
		//
		final public @NullDisallow String NAME;
		final public @NullDisallow String VALUE;
		public boolean wrap;
		public String domain;
		public String path;
		public long age;
		public boolean secure;
		public boolean http;

		//
		public Entry(@NullDisallow String name, @NullDisallow String value)
		{
			Validator.notNull(name, "[name]");
			Validator.notNull(value, "[value]");

			NAME = name;
			VALUE = value;
			wrap = false;
			domain = null;
			path = null;
			age = -1;
			secure = false;
			http = false;
		}

		private Entry(Cookie cookie)
		{
			NAME = cookie.name();
			VALUE = cookie.value();
			wrap = cookie.wrap();
			domain = cookie.domain();
			path = cookie.path();
			age = cookie.maxAge();
			secure = cookie.isSecure();
			http = cookie.isHttpOnly();
		}

		//
		public Cookie cookie()
		{
			Cookie result = new DefaultCookie(NAME, VALUE);
			result.setWrap(wrap);
			result.setDomain(domain);
			result.setPath(path);
			result.setMaxAge(age);
			result.setSecure(secure);
			result.setHttpOnly(http);

			return result;
		}

		public @NullDisallow String name()
		{
			return NAME;
		}

		public @NullDisallow String value()
		{
			return VALUE;
		}

		public boolean getWrap()
		{
			return wrap;
		}

		public void setWrap(boolean value)
		{
			wrap = value;
		}

		public String getDomain()
		{
			return domain;
		}

		public void setDomain(String value)
		{
			domain = value;
		}

		public String getPath()
		{
			return path;
		}

		public void setPath(String value)
		{
			path = value;
		}

		public long getAge()
		{
			return age;
		}

		public void setAge(long value)
		{
			age = value;
		}

		public boolean getSecure()
		{
			return secure;
		}

		public void setSecure(boolean value)
		{
			secure = value;
		}

		public boolean getHttp()
		{
			return http;
		}

		public void setHttp(boolean value)
		{
			http = value;
		}
	}

}
