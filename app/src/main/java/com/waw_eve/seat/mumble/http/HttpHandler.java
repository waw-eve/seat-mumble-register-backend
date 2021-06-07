package com.waw_eve.seat.mumble.http;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.waw_eve.seat.mumble.model.Request;
import com.waw_eve.seat.mumble.utils.CertUtil;

import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.AsciiString;

public class HttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private static final Logger logger = LoggerFactory.getLogger(HttpHandler.class);
    private static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private AsciiString contentType = HttpHeaderValues.BASE64;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        var req = msg.content().toString(Charset.defaultCharset());
        var data = gson.fromJson(new String(Base64.decode(req)), Request.class);
        logger.info("received message:{}", data);
        var cert = CertUtil.signCert(data.getName(), data.getEmail(), data.getCorp(), "");
        var stream = new ByteArrayOutputStream();
        cert.store(stream, "".toCharArray());
        var response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
                Unpooled.wrappedBuffer(Base64.encode(stream.toByteArray()))); // 2

        HttpHeaders heads = response.headers();
        heads.add(HttpHeaderNames.CONTENT_TYPE, contentType + "; charset=UTF-8");
        heads.add(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes()); // 3
        heads.add(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);

        ctx.write(response);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.info("channelReadComplete");
        super.channelReadComplete(ctx);
        ctx.flush(); // 4
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (null != cause)
            logger.error("Caught exception", cause);
        if (null != ctx)
            ctx.close();
    }
}
