package com.waw_eve.seat.mumble.http;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class HttpHandlerTests {

    @Test
    public void httpHandlerTest() {
        var httpHandler = new HttpHandler();
        assertNotNull(httpHandler);
    }
}
