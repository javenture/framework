package net.javenture.framework.example;
/*
 * Copyright 2013 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import net.javenture.framework.utf8.Utf8InputStream;
import net.javenture.framework.utf8.Utf8OutputStream;

import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.*;


public class HttpHelloWorldServerHandler extends ChannelInboundHandlerAdapter
{
    private static final byte[] CONTENT = {'H', 'e', 'l', 'l', 'o', ' ', 'W', 'o', 'r', 'l', 'd'};


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
            HttpRequest request = (HttpRequest) message;
            boolean keepAlive = false; // XXX: HttpUtil.isKeepAlive(request);
            FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(CONTENT));
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());

            // XXX: del
            try
            {
                Utf8OutputStream output = new Utf8OutputStream();
                Utf8InputStream input = output.toInputStream();
                response.content().writeBytes(input, input.available());
            }
            catch (Exception e)
            {
            }



            if (!keepAlive)
            {
                context.write(response).addListener(ChannelFutureListener.CLOSE);
            }
            else
            {
                response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
                context.write(response);
            }
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause)
    {
        cause.printStackTrace();
        context.close();
    }
}
