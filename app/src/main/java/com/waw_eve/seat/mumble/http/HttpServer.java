package com.waw_eve.seat.mumble.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class HttpServer {
    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);

    private final int port;

    public HttpServer(int port) {
        this.port = port;
    }

    public void start() throws InterruptedException {
        var b = new ServerBootstrap();
        var group = new NioEventLoopGroup();
        b.group(group).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                logger.info("initChannel ch:{}", ch);
                ch.pipeline().addLast("decoder", new HttpRequestDecoder()) // 1
                        .addLast("encoder", new HttpResponseEncoder()) // 2
                        .addLast("aggregator", new HttpObjectAggregator(512 * 1024)) // 3
                        .addLast("handler", new HttpHandler()); // 4
            }
        }).option(ChannelOption.SO_BACKLOG, 128) // determining the number of connections queued
                .childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE);

        b.bind(port).sync();
    }
}