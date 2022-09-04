package net.javenture.framework.netty;


import net.javenture.framework.log.Log;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelProgressiveFuture;
import io.netty.channel.ChannelProgressiveFutureListener;
import io.netty.channel.ChannelProgressivePromise;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpChunkedInput;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedFile;
import io.netty.handler.stream.ChunkedWriteHandler;
import net.javenture.framework.util.ByteInputStream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;


/*
	2020/10/09
 */
final public class FileNettyHandler
	extends SimpleChannelInboundHandler<FullHttpRequest>
	implements INettyHandler
{
	//
	final private static Log LOG = Log.instance(FileNettyHandler.class);
	final public static int HTTP_CACHE_SECONDS = 600;                                       // XXX: options


	//
	final private IFile FILE;
	final private HttpObjectAggregator HANDLER0;
	final private ChunkedWriteHandler HANDLER1;


	//
	public FileNettyHandler(String path)
	{
		this(() -> new File(path));
	}


	public FileNettyHandler(String parent, String child)
	{
		this(() -> new File(parent, child));
	}


	public FileNettyHandler(String parent, NettyUrl url)
	{
		this(() -> url.toFile(parent));
	}


	private FileNettyHandler(IFile file)
	{
		FILE = file;
		HANDLER0 = new HttpObjectAggregator(1024);
		HANDLER1 = new ChunkedWriteHandler();
	}


	//
	@Override
	public void register(ChannelHandlerContext context)
	{
		ChannelPipeline pipeline = context.pipeline();
		pipeline.addLast(HANDLER0);
		pipeline.addLast(HANDLER1);
		pipeline.addLast(this);
	}


	@Override
	public void deregister(ChannelHandlerContext context)
	{
		ChannelPipeline pipeline = context.pipeline();
		pipeline.remove(HANDLER0);
		pipeline.remove(HANDLER1);
		pipeline.remove(this);
	}


	@Override
	protected void channelRead0(ChannelHandlerContext context, FullHttpRequest request0)
		throws Exception
	{
		File file = FILE.get();


		// DEL
		System.out.println("PATH: " + file.getPath());



		if (!file.exists() || !file.isFile() || file.isHidden())
		{
			NettyResponse response = new NettyResponse(NettyStatus.NOT_FOUND_404, true);
			BaseNettyHandler handler = new BaseNettyHandler(response);
			handler.write(context);

			return;
		}

		//
		long lLastModified = file.lastModified();
		String sLastModified = NettyUtil.format(lLastModified);
		String sIfModifiedSince = request0.headers().get(HttpHeaderNames.IF_MODIFIED_SINCE);

		if (sLastModified.equals(sIfModifiedSince))
		{
			NettyResponse response = new NettyResponse(NettyStatus.NOT_MODIFIED_304, false);
			BaseNettyHandler handler = new BaseNettyHandler(response);
			handler.write(context);

			return;
		}

		//
		RandomAccessFile fileRandomAccess;

		try
		{
			fileRandomAccess = new RandomAccessFile(file, "r");
		}
		catch (FileNotFoundException e)
		{
			LOG.error(e);

			NettyResponse response = new NettyResponse(NettyStatus.INTERNAL_SERVER_ERROR_500, true);
			BaseNettyHandler handler = new BaseNettyHandler(response);
			handler.write(context);

			return;
		}

		//
		long length = file.length();
		Media media = Media.parse(file);

		NettyResponse response = new NettyResponse(NettyStatus.OK_200, ByteInputStream.NULL);
		response.HEADERS.add(HttpHeaderNames.CONTENT_LENGTH, length);
		response.HEADERS.add(HttpHeaderNames.CONTENT_TYPE, media.HEADER);
		response.HEADERS.add(HttpHeaderNames.LAST_MODIFIED, NettyUtil.format(lLastModified));
		//response.HEADERS.add(HttpHeaderNames.CACHE_CONTROL, "private, max-age=" + HTTP_CACHE_SECONDS);                                     // ???

		//
		HttpResponse response0 = NettyResponse.toHttpResponse(response);
		context.write(response0);
		ChannelFuture futureSendFile;
		ChannelFuture futureLastContent;

		if (context.pipeline().get(SslHandler.class) != null)
		{
			HttpChunkedInput message = new HttpChunkedInput(new ChunkedFile(fileRandomAccess, 0, length, 8192));                  // ??? ChunkedFile -> FileRegion
			ChannelProgressivePromise promise = context.newProgressivePromise();
			futureSendFile = context.writeAndFlush(message, promise);
			futureLastContent = futureSendFile;
		}
		else
		{
			DefaultFileRegion message = new DefaultFileRegion(fileRandomAccess.getChannel(), 0, length);
			ChannelProgressivePromise promise = context.newProgressivePromise();
			futureSendFile = context.write(message, promise);
			futureLastContent = context.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
		}

		//
		ChannelProgressiveFutureListener listener = new ChannelProgressiveFutureListener()
		{
			@Override
			public void operationProgressed(ChannelProgressiveFuture future, long progress, long total)
			{
				if (total < 0)
				{
					// total unknown
					System.out.println(future.channel() + " Transfer progress: " + progress);
				}
				else
				{
					System.out.println(future.channel() + " Transfer progress: " + progress + " / " + total);
				}
			}

			@Override
			public void operationComplete(ChannelProgressiveFuture future)
			{
				// DEL
				System.out.println(future.channel() + " Transfer complete.");


				// ??? raf.close()
			}
		};

		futureSendFile.addListener(listener);
	}


	private void write(ChannelHandlerContext context)
		throws Exception
	{
	}


	//
	@FunctionalInterface
	private interface IFile
	{
		File get();
	}

}
