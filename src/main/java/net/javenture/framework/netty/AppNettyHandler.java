package net.javenture.framework.netty;


import io.netty.handler.codec.http.*;
import net.javenture.framework.annotation.NullAllow;
import net.javenture.framework.annotation.NullDisallow;
import net.javenture.framework.annotation.SingleThread;
import net.javenture.framework.log.Log;
import net.javenture.framework.test.TempFileUtil;
import net.javenture.framework.util.Validator;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.DiskAttribute;
import io.netty.handler.codec.http.multipart.DiskFileUpload;
import io.netty.handler.codec.http.multipart.FileUpload;
import io.netty.handler.codec.http.multipart.HttpData;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;

import java.io.File;
import java.io.IOException;


/*
	2020/10/08
 */
@SingleThread
final public class AppNettyHandler
	extends SimpleChannelInboundHandler<HttpObject>
	implements INettyHandler
{
	//
	final private static Log LOG = Log.instance(AppNettyHandler.class);
	final private static HttpDataFactory FACTORY = new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE);


	static
	{
		DiskFileUpload.deleteOnExitTemporaryFile = true; // should delete file on exit (in normal exit)
		DiskFileUpload.baseDirectory = null; // system temp directory
		DiskAttribute.deleteOnExitTemporaryFile = true; // should delete file on exit (in normal exit)
		DiskAttribute.baseDirectory = null; // system temp directory
	}


	//
	final private @NullDisallow INettyResource RESOURCE;
	private @NullAllow NettyRequest request;
	private HttpPostRequestDecoder decoder;
	private HttpData partial;
	private boolean error;


	//
	public AppNettyHandler(@NullDisallow INettyResource resource)
	{
		Validator.notNull(resource, "[resource]");

		RESOURCE = resource;
		request = null;
		decoder = null;
		partial = null;
		error = false;


		// DEL
		System.out.println("new AppNettyHandler");
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
		throws Exception
	{
		if (message instanceof HttpRequest)
		{
			// DEL
			System.out.println("AppNettyHandler.HttpRequest");


			HttpRequest request0 = (HttpRequest) message;
			request = new NettyRequest(request0);



			// ???
			if (HttpUtil.is100ContinueExpected(request0))
			{
				// DEL
				System.out.println("AppNettyHandler.100ContinueExpected");


				//FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, CONTINUE, Unpooled.EMPTY_BUFFER);
				//ctx.write(response);
			}



			//
			if (request.METHOD == NettyMethod.GET) return; // !

			//
			try
			{
				decoder = new HttpPostRequestDecoder(FACTORY, request0);
			}
			catch (HttpPostRequestDecoder.ErrorDataDecoderException e)
			{
				error = true;
				write(context);
				LOG.error(e);

				return;
			}
		}

		if (decoder != null)
		{
			// DEL
			System.out.println("AppNettyHandler.decoder");


			if (message instanceof HttpContent)
			{
				HttpContent chunk = (HttpContent) message;

				try
				{
					decoder.offer(chunk);
				}
				catch (HttpPostRequestDecoder.ErrorDataDecoderException e)
				{
					error = true;
					write(context);
					LOG.error(e);

					return;
				}

				try
				{
					while (decoder.hasNext())
					{
						InterfaceHttpData data = decoder.next();

						if (data != null)
						{
							if (partial == data)
							{
								//logger.info(" 100% (FinalSize: " + partialContent.length() + ")");
								partial = null;
							}

							if (data.getHttpDataType() == InterfaceHttpData.HttpDataType.Attribute)
							{
								// DEL
								//System.out.println("InterfaceHttpData.HttpDataType.Attribute");


/*
								if (options.allowAttribute)
								{

								}
								else
								{

								}
*/



								Attribute attribute = (Attribute) data;
								String name = attribute.getName();
								String value;

								try
								{
									value = attribute.getValue();
								}
								catch (IOException ignore)
								{
									// XXX: error


									return;
								}

								if (value != null) request.PARAMETERS.add(name, value);
							}
							else if (data.getHttpDataType() == InterfaceHttpData.HttpDataType.FileUpload)
							{
								// DEL
								//System.out.println("InterfaceHttpData.HttpDataType.FileUpload");


/*
								if (options.allowfile)
								{

								}
								else
								{

								}
*/



								FileUpload fileUpload = (FileUpload) data;

								if (fileUpload.isCompleted())
								{
									File file = TempFileUtil.create();                                                   // XXX: custom TempFileUtil

									if (file != null)
									{
										String path = file.getPath();
										String name = fileUpload.getFilename();
										long length = fileUpload.length();
										String type = fileUpload.getContentType();

										if (fileUpload.renameTo(file)) request.UPLOADS.add(path, name, length, type);
										else ; // XXX: error
									}
									else
									{
										// XXX: error


									}

									decoder.removeHttpDataFromClean(fileUpload);                   // ???
								}
								else
								{
									// XXX: error


									// File to be continued but should not!
								}
							}
						}
					}

					//
					InterfaceHttpData data = decoder.currentPartialHttpData();

					if (data != null)
					{
						if (partial == null)
						{
							partial = (HttpData) data;

/*
							if (partialContent instanceof FileUpload)
							{
								// "Start FileUpload: "
								// ((FileUpload) partialContent).getFilename()
							}
							else
							{
								// "Start Attribute: "
								// partialContent.getName()
							}

							//
							// "DefinedSize: "
							// partialContent.definedLength()
*/
						}

						if (partial.definedLength() > 0)
						{
/*
							// partialContent.length() * 100 / partialContent.definedLength()
							// "%"
*/
						}
						else
						{
/*
							// partialContent.length()
*/
						}
					}
				}
				catch (HttpPostRequestDecoder.EndOfDataDecoderException ignore)
				{
					// ! blank
				}

				//
				if (chunk instanceof LastHttpContent)                // ??? move up
				{
					decoder.destroy();                                                     // XXX: "io.netty.util.IllegalReferenceCountException: refCnt: 0, decrement: 1" ???
					decoder = null;
					write(context);
				}
			}
		}
		else
		{
			// DEL
			System.out.println("AppNettyHandler.LastHttpContent: " + (message instanceof LastHttpContent));


			write(context);
		}
	}


	@Override
	public void channelInactive(ChannelHandlerContext context)
	{
		if (decoder != null) decoder.cleanFiles();
	}


	@Override
	public void exceptionCaught(ChannelHandlerContext context, Throwable cause)
	{
		// ???


		//LOG.warning(responseContent.toString(), cause);
		context.close();
	}


	private void write(ChannelHandlerContext context)
	{
		NettyResponse response = null;

		if (!error)
		{
			try
			{
				response = INettyResource.process(RESOURCE, request);
			}
			catch (Exception e)
			{
				LOG.error(e);
			}
		}

		//
		HttpResponse response0 = NettyResponse.toHttpResponse(response);
		context.writeAndFlush(response0);
	}

}
