package net.javenture.framework.example;


import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.util.Map;


public class InitServerHandler extends ChannelInboundHandlerAdapter
{
    @Override
    public void channelReadComplete(ChannelHandlerContext context)
    {
        context.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext context, Object message)
    {
        if (message instanceof HttpRequest)
        {
        	System.out.println("remoteAddress: " + context.channel().remoteAddress());


			HttpRequest request = (HttpRequest) message;
            HttpMethod method = request.method();
            String uri = request.uri();

            System.out.println("method: " + method + "; uri: " + uri);                // XXX

            //
			ChannelPipeline pipeline = context.pipeline();

			//
			for (Map.Entry<String, ChannelHandler> entry : pipeline)
			{
				System.out.println("entry: " + entry.getValue());                       // XXX
			}


			//
            if (uri.endsWith(".jpg") || uri.endsWith(".png"))
			{
				pipeline.addLast(new HttpObjectAggregator(65536));
				pipeline.addLast(new ChunkedWriteHandler());
				pipeline.addLast(new HttpStaticFileServerHandler());

			}
			else
			{
		        pipeline.addLast(new HttpHelloWorldServerHandler());

			}

	        pipeline.remove(this);


            context.fireChannelRead(message);
        }


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause)
    {
        cause.printStackTrace();
        context.close();
    }

}
