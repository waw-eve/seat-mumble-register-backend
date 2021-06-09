package com.waw_eve.seat.mumble.http;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.util.HashMap;

import javax.crypto.Cipher;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.waw_eve.seat.mumble.Config;
import com.waw_eve.seat.mumble.MumbleClient;
import com.waw_eve.seat.mumble.model.Request;
import com.waw_eve.seat.mumble.utils.CertUtil;
import com.waw_eve.seat.mumble.utils.CryptUtil;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.text.StringSubstitutor;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.AsciiString;

public class HttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private static final Logger logger = LoggerFactory.getLogger(HttpHandler.class);
    private static Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static MumbleClient mumbleClient = MumbleClient.getInstance();

    private AsciiString contentType = HttpHeaderValues.BASE64;

    private String decode(String incomingMessage) {
        return new String(Base64.decode(CryptUtil.docrypt(incomingMessage, Cipher.DECRYPT_MODE).getBytes()));
    }

    private byte[] encode(ByteArrayOutputStream stream) {
        return CryptUtil.docrypt(Base64.toBase64String(stream.toByteArray()), Cipher.ENCRYPT_MODE).getBytes();
    }

    private String generateMumbleUserName(Request request) {
        var valuesMap = new HashMap<String, String>();
        valuesMap.put("user", request.getName());
        valuesMap.put("corp", request.getCorp());
        var strsub = new StringSubstitutor(valuesMap);
        return strsub.replace(Config.getGlobalConfig().getMumbleUserNampTemplate());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        var req = msg.content().toString(Charset.defaultCharset());
        var data = gson.fromJson(decode(req), Request.class);
        logger.info("received message:{}", data);
        var cert = CertUtil.signCert(data.getName(), data.getEmail(), data.getCorp(), "");
        var certHash = DigestUtils.sha1Hex(cert.getCertificate(data.getName()).getEncoded());
        if (mumbleClient.updateUser(generateMumbleUserName(data), data.getEmail(), certHash)) {
            logger.info("update user with hash:{} seccessful", certHash);
        }
        var stream = new ByteArrayOutputStream();
        cert.store(stream, "".toCharArray());
        var response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
                Unpooled.wrappedBuffer(encode(stream))); // 2

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
