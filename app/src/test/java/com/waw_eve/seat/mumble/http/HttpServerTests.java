package com.waw_eve.seat.mumble.http;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class HttpServerTests {

    @Test
    public void httpServerTest() {
        var httpServer = new HttpServer(80);
        assertNotNull(httpServer);
    }
}
