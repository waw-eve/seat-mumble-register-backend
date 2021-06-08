package com.waw_eve.seat.mumble.http;

import static org.junit.Assert.assertNotNull;

import java.nio.charset.Charset;

import org.junit.Test;

import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;

public class HttpHandlerTests {

    @Test
    public void httpHandlerTest() {
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new HttpHandler());
        assertNotNull(embeddedChannel);
        embeddedChannel.writeInbound(
                Unpooled.copiedBuffer("vNDY5B9qmXJLqK57vYQ+PF5eZWzQPMoO2RBt40oLZE29r7Pd40NOodcQjZ7vGZvgUMOuf79BfB0=",
                        Charset.defaultCharset()));
        assertNotNull(embeddedChannel.readInbound());
        embeddedChannel.finish();
    }
}
