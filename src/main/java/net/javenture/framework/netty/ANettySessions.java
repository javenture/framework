package net.javenture.framework.netty;


import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.util.IParser;
import net.javenture.framework.util.Task;
import net.javenture.framework.util.UtcTimeUtil;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.cookie.ServerCookieEncoder;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.DefaultCookie;

import java.util.Iterator;
import java.util.concurrent.ConcurrentSkipListMap;


/*
	2020/09/30
 */
abstract public class ANettySessions<I extends INettySessionId>
	implements INettySessions<I>
{
	//
	final private ConcurrentSkipListMap<I, NettySession<I>> MAP;
	final private Scheduler SCHEDULER;


	//
	protected ANettySessions()
	{
		MAP = new ConcurrentSkipListMap<>();
		SCHEDULER = new Scheduler();

		SCHEDULER.start();
	}


	//
	abstract protected @NullDisallow NettySession<I> create();
	abstract protected IParser<I> parser();
	abstract protected void created(NettySession<I> session);
	abstract protected void destroyed(NettySession<I> session);


	//
	@Override
	final public NettySession<I> from(@NullDisallow NettyRequest request)
	{
		NettySession<I> result = null;
		NettyCookies cookies = request.cookies();
		NettyCookies.Entry entry = cookies.get(cookie());

		if (entry != null)
		{
			IParser.Report<I> report = parser().parse(entry.VALUE);

			if (report.DEFINED)
			{
				NettySession<I> session = MAP.get(report.VALUE);

				if (session != null)
				{
					session.confirm();
					session.refresh();
					session.increment();
					result = session;
				}
			}
		}

		if (result == null)
		{
			while (true)
			{
				result = create();

				if (MAP.putIfAbsent(result.id(), result) == null)
				{
					created(result);

					break;
				}
			}
		}

		return result;
	}


	@Override
	final public void to(@NullDisallow NettyResponse response, @NullDisallow NettySession<I> session)
	{
		if (session.unknown())
		{
			I id = session.id();
			Cookie cookie = new DefaultCookie(cookie(), id.toString());
			cookie.setHttpOnly(true);
			response.HEADERS.add(HttpHeaderNames.SET_COOKIE, ServerCookieEncoder.STRICT.encode(cookie));
		}
	}


	//
	final private class Scheduler
		extends Task
	{
		//
		final private static String NAME = "ANettySessions.Scheduler";
		final private static long PERIODITY = 60 * 1000;

		//
		private Scheduler()
		{
			super(NAME, PERIODITY);
		}

		//
		@Override
		public void execute()
		{
			long now = UtcTimeUtil.ms();
			Iterator<NettySession<I>> iterator = MAP.values().iterator();

			while (iterator.hasNext())
			{
				NettySession<I> session = iterator.next();

				if
				(
					!session.activity()
					||
					session.last() + session.duration() < now
				)
				{
					iterator.remove();
					destroyed(session);
				}
			}
		}

		@Override
		public void exit()
		{
		}
	}

}
